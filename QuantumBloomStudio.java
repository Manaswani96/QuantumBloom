import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import javafx.stage.FileChooser;
import java.io.*;
import java.util.Properties;

public class QuantumBloomStudio extends Application {

    private Canvas mainCanvas, probCanvas, energyCanvas, momentumCanvas, phaseCanvas, expectationCanvas, heatmapCanvas;
    private GraphicsContext mainGc, probGc, energyGc, momentumGc, phaseGc, expectationGc, heatmapGc;
    private TextArea info, knowledgePanel, projectInfo;
    private Slider nSlider, lengthSlider, weightSlider;
    private Button playPauseButton, learnMoreButton, aboutButton, saveButton, loadButton;
    private ComboBox<String> systemComboBox;
    private CheckBox gridToggle;
    private boolean isAnimating = false, showGrid = false;
    private double time = 0;
    private AnimationTimer timer;

    // Quantum parameters
    private int n = 1;
    private double L = 10.0;
    private final double hbar = 1.0;
    private final double m = 1.0;
    private final double omega = 1.0;
    private String currentSystem = "Particle in a Box";
    private double[] superpositionWeights = {1.0 / Math.sqrt(2), 1.0 / Math.sqrt(2)};

    // Interactive features
    private double zoomFactor = 1.0;
    private double panX = 0, panY = 0;
    private double markerX = 0;
    private boolean isDraggingMarker = false;
    private Label wavefunctionLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Quantum Bloom Studio 🌟");

        // Sidebar (Controls)
        VBox sidebar = new VBox(10);
        sidebar.setStyle("-fx-background-color: linear-gradient(#ff80bf, #cc99ff); -fx-padding: 15; -fx-border-color: #b266ff; -fx-border-width: 2; -fx-effect: dropshadow(gaussian, #b266ff, 15, 0.5, 0, 0); -fx-background-radius: 10; -fx-border-radius: 10;");
        sidebar.setPrefWidth(250);

        Label title = new Label("✨ Quantum Controls");
        title.setFont(new Font("Verdana", 18));
        title.setStyle("-fx-text-fill: #fff5e6; -fx-effect: dropshadow(gaussian, #66cccc, 10, 0, 0, 0);");

        systemComboBox = new ComboBox<>();
        systemComboBox.getItems().addAll("Particle in a Box", "Quantum Harmonic Oscillator", "Superposition (n=1,2)");
        systemComboBox.setValue("Particle in a Box");
        styleComboBox(systemComboBox);
        systemComboBox.setOnAction(e -> {
            currentSystem = systemComboBox.getValue();
            if (currentSystem.equals("Superposition (n=1,2)")) {
                nSlider.setDisable(true);
                weightSlider.setDisable(false);
            } else {
                nSlider.setDisable(false);
                weightSlider.setDisable(true);
            }
            FadeTransition fade = new FadeTransition(Duration.millis(300), mainCanvas);
            fade.setFromValue(0.1);
            fade.setToValue(1.0);
            fade.play();
            redrawAll();
            info.appendText("\n🔄 Switched to " + currentSystem);
        });

        Label nLabel = new Label("Quantum Number (n): 1");
        nSlider = new Slider(1, 10, 1);
        styleSlider(nSlider);
        nSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            n = newVal.intValue();
            nLabel.setText("Quantum Number (n): " + n);
            redrawAll();
            info.appendText("\n✨ Set n to " + n);
        });

        Label lengthLabel = new Label("Parameter (L/Scale): 10.0");
        lengthSlider = new Slider(5, 20, 10);
        styleSlider(lengthSlider);
        lengthSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            L = newVal.doubleValue();
            lengthLabel.setText("Parameter (L/Scale): " + String.format("%.1f", L));
            redrawAll();
            info.appendText("\n📏 Set L/Scale to " + String.format("%.1f", L));
        });

        Label weightLabel = new Label("Superposition Weight: 0.5");
        weightSlider = new Slider(0, 1, 0.5);
        styleSlider(weightSlider);
        weightSlider.setDisable(true);
        weightSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            double w = newVal.doubleValue();
            superpositionWeights[0] = Math.sqrt(w);
            superpositionWeights[1] = Math.sqrt(1 - w);
            weightLabel.setText("Superposition Weight: " + String.format("%.2f", w));
            redrawAll();
            info.appendText("\n⚖️ Set superposition weights to " + String.format("%.2f, %.2f", superpositionWeights[0], superpositionWeights[1]));
        });

        playPauseButton = new Button("Play Time Evolution");
        styleButton(playPauseButton);
        playPauseButton.setTooltip(new Tooltip("Animate the wavefunction over time"));
        playPauseButton.setOnAction(e -> toggleAnimation());

        gridToggle = new CheckBox("Show Gridlines");
        gridToggle.setStyle("-fx-font-family: 'Verdana'; -fx-text-fill: #fff5e6;");
        gridToggle.setOnAction(e -> {
            showGrid = gridToggle.isSelected();
            redrawAll();
            info.appendText(showGrid ? "\n📐 Enabled gridlines" : "\n📐 Disabled gridlines");
        });

        learnMoreButton = new Button("Learn More! 📚");
        styleButton(learnMoreButton);
        learnMoreButton.setTooltip(new Tooltip("Discover quantum facts!"));
        learnMoreButton.setOnAction(e -> displayQuantumKnowledge());

        aboutButton = new Button("About Project 🌸");
        styleButton(aboutButton);
        aboutButton.setTooltip(new Tooltip("Learn about Quantum Bloom Studio"));
        aboutButton.setOnAction(e -> displayProjectInfo());

        saveButton = new Button("Save State 💾");
        styleButton(saveButton);
        saveButton.setTooltip(new Tooltip("Save current quantum state"));
        saveButton.setOnAction(e -> saveQuantumState(primaryStage));

        loadButton = new Button("Load State 📂");
        styleButton(loadButton);
        loadButton.setTooltip(new Tooltip("Load a saved quantum state"));
        loadButton.setOnAction(e -> loadQuantumState(primaryStage));

        Button resetButton = new Button("Reset 🌟");
        styleButton(resetButton);
        resetButton.setTooltip(new Tooltip("Reset all parameters"));
        resetButton.setOnAction(e -> {
            systemComboBox.setValue("Particle in a Box");
            nSlider.setValue(1);
            lengthSlider.setValue(10);
            weightSlider.setValue(0.5);
            zoomFactor = 1.0;
            panX = 0;
            panY = 0;
            markerX = 0;
            if (isAnimating) toggleAnimation();
            time = 0;
            redrawAll();
            info.appendText("\n🧹 Reset all parameters");
        });

        sidebar.getChildren().addAll(title, systemComboBox, nLabel, nSlider, lengthLabel, lengthSlider, weightLabel, weightSlider, playPauseButton, gridToggle, learnMoreButton, aboutButton, saveButton, loadButton, resetButton);

        // Main Canvas (Wavefunction)
        mainCanvas = new Canvas(800, 300);
        mainGc = mainCanvas.getGraphicsContext2D();
        StackPane mainCanvasPane = new StackPane(mainCanvas);
        mainCanvasPane.setStyle("-fx-background-color: #f0e6ff; -fx-border-color: #b266ff; -fx-border-width: 2; -fx-effect: dropshadow(gaussian, #b266ff, 15, 0.5, 0, 0); -fx-background-radius: 10; -fx-border-radius: 10;");

        // Wavefunction value display
        wavefunctionLabel = new Label("");
        wavefunctionLabel.setStyle("-fx-font-family: 'Verdana'; -fx-text-fill: #66cccc; -fx-background-color: rgba(255, 245, 230, 0.8); -fx-padding: 5; -fx-background-radius: 5;");
        StackPane.setAlignment(wavefunctionLabel, Pos.TOP_LEFT);
        mainCanvasPane.getChildren().add(wavefunctionLabel);

        // Zoom and pan controls
        mainCanvas.setOnScroll(e -> {
            double delta = e.getDeltaY() > 0 ? 1.1 : 0.9;
            zoomFactor *= delta;
            zoomFactor = Math.max(0.5, Math.min(zoomFactor, 5.0));
            redrawAll();
            info.appendText("\n🔎 Zoomed to " + String.format("%.2f", zoomFactor));
        });

        mainCanvas.setOnMousePressed(e -> {
            if (e.isSecondaryButtonDown()) {
                panX += e.getX();
                panY += e.getY();
            } else if (Math.abs(e.getX() - (50 + (markerX + L) * (750 - 50) / (2 * L))) < 10) {
                isDraggingMarker = true;
            }
        });

        mainCanvas.setOnMouseDragged(e -> {
            if (e.isSecondaryButtonDown()) {
                panX = e.getX() - panX;
                panY = e.getY() - panY;
                redrawAll();
                info.appendText("\n📍 Panned to (" + String.format("%.2f", panX) + ", " + String.format("%.2f", panY) + ")");
            } else if (isDraggingMarker) {
                markerX = (e.getX() - 50) * 2 * L / (750 - 50) - L;
                markerX = Math.max(-L, Math.min(markerX, L));
                redrawAll();
            }
        });

        mainCanvas.setOnMouseReleased(e -> {
            if (e.isSecondaryButtonDown()) {
                panX = e.getX();
                panY = e.getY();
            }
            isDraggingMarker = false;
        });

        mainCanvas.setOnMouseMoved(e -> {
            double x = (e.getX() - 50) * 2 * L / (750 - 50) / zoomFactor - L + panX / zoomFactor;
            double psi = computeWavefunctionAtX(x);
            wavefunctionLabel.setText("x: " + String.format("%.2f", x) + ", ψ: " + String.format("%.2f", psi));
            wavefunctionLabel.setVisible(true);
        });
        mainCanvas.setOnMouseExited(e -> wavefunctionLabel.setVisible(false));

        // Right Panel (Graphs and Knowledge)
        VBox rightPanel = new VBox(10);
        rightPanel.setPrefWidth(250);
        rightPanel.setStyle("-fx-background-color: linear-gradient(#cc99ff, #ff80bf); -fx-padding: 15; -fx-border-color: #b266ff; -fx-border-width: 2; -fx-background-radius: 10; -fx-border-radius: 10;");

        Label energyLabel = new Label("⚡ Energy Levels");
        styleLabel(energyLabel);

        energyCanvas = new Canvas(200, 100);
        energyGc = energyCanvas.getGraphicsContext2D();
        StackPane energyPane = new StackPane(energyCanvas);
        styleCanvasPane(energyPane);
        energyCanvas.setOnMouseClicked(e -> {
            double y = e.getY();
            for (int i = 1; i <= 5; i++) {
                double energy = getEnergy(i);
                double ey = 90 - (energy * 20);
                if (y >= ey && y <= ey + 10 && !currentSystem.equals("Superposition (n=1,2)")) {
                    nSlider.setValue(i);
                    info.appendText("\n⚡ Selected n=" + i);
                }
            }
        });

        Label probLabel = new Label("🌊 Probability Density");
        styleLabel(probLabel);

        probCanvas = new Canvas(200, 100);
        probGc = probCanvas.getGraphicsContext2D();
        StackPane probPane = new StackPane(probCanvas);
        styleCanvasPane(probPane);

        Label momentumLabel = new Label("🚀 Momentum Space");
        styleLabel(momentumLabel);

        momentumCanvas = new Canvas(200, 100);
        momentumGc = momentumCanvas.getGraphicsContext2D();
        StackPane momentumPane = new StackPane(momentumCanvas);
        styleCanvasPane(momentumPane);

        Label phaseLabel = new Label("🌐 Phase Plot");
        styleLabel(phaseLabel);

        phaseCanvas = new Canvas(200, 100);
        phaseGc = phaseCanvas.getGraphicsContext2D();
        StackPane phasePane = new StackPane(phaseCanvas);
        styleCanvasPane(phasePane);

        Label expectationLabel = new Label("📏 Expectation Value");
        styleLabel(expectationLabel);

        expectationCanvas = new Canvas(200, 100);
        expectationGc = expectationCanvas.getGraphicsContext2D();
        StackPane expectationPane = new StackPane(expectationCanvas);
        styleCanvasPane(expectationPane);

        Label heatmapLabel = new Label("🔥 Probability Heatmap");
        styleLabel(heatmapLabel);

        heatmapCanvas = new Canvas(200, 100);
        heatmapGc = heatmapCanvas.getGraphicsContext2D();
        StackPane heatmapPane = new StackPane(heatmapCanvas);
        styleCanvasPane(heatmapPane);

        info = new TextArea("Welcome to Quantum Bloom Studio 🌟\nExplore quantum wonders!");
        info.setWrapText(true);
        info.setPrefHeight(80);
        info.setEditable(false);
        info.setStyle("-fx-control-inner-background: rgba(255, 245, 230, 0.8); -fx-font-family: 'Verdana'; -fx-text-fill: #b266ff; -fx-background-radius: 5;");

        Label knowledgeLabel = new Label("📖 Quantum Insights");
        styleLabel(knowledgeLabel);

        knowledgePanel = new TextArea("Click 'Learn More!' to see quantum facts in a dialog! 🌟");
        knowledgePanel.setWrapText(true);
        knowledgePanel.setPrefHeight(120);
        knowledgePanel.setEditable(false);
        knowledgePanel.setStyle("-fx-control-inner-background: rgba(255, 245, 230, 0.8); -fx-font-family: 'Verdana'; -fx-text-fill: #b266ff; -fx-background-radius: 5;");

        Label projectLabel = new Label("ℹ️ About This Project");
        styleLabel(projectLabel);

        projectInfo = new TextArea("Click 'About Project' to see details in a dialog! 🌸");
        projectInfo.setWrapText(true);
        projectInfo.setPrefHeight(100);
        projectInfo.setEditable(false);
        projectInfo.setStyle("-fx-control-inner-background: rgba(255, 245, 230, 0.8); -fx-font-family: 'Verdana'; -fx-text-fill: #b266ff; -fx-background-radius: 5;");

        rightPanel.getChildren().addAll(energyLabel, energyPane, probLabel, probPane, momentumLabel, momentumPane, phaseLabel, phasePane, expectationLabel, expectationPane, heatmapLabel, heatmapPane, info, knowledgeLabel, knowledgePanel, projectLabel, projectInfo);

        // Layout
        BorderPane layout = new BorderPane();
        layout.setLeft(sidebar);
        layout.setCenter(mainCanvasPane);
        layout.setRight(rightPanel);
        layout.setStyle("-fx-background-color: #f0e6ff;");

        // Initial draw
        redrawAll();

        // Animation timer
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                time += 0.05;
                redrawAll();
            }
        };

        Scene scene = new Scene(layout, 1300, 900);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void styleButton(Button button) {
        button.setStyle("-fx-font-family: 'Verdana'; -fx-background-color: linear-gradient(#ff80bf, #66cccc); -fx-text-fill: #fff5e6; -fx-border-color: #b266ff; -fx-border-width: 1; -fx-background-radius: 15; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #b266ff, 10, 0, 0, 0);");
        button.setOnMouseEntered(e -> button.setStyle("-fx-font-family: 'Verdana'; -fx-background-color: linear-gradient(#ff99cc, #80d4d4); -fx-text-fill: #fff5e6; -fx-border-color: #b266ff; -fx-border-width: 1; -fx-background-radius: 15; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #b266ff, 15, 0, 0, 0);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-font-family: 'Verdana'; -fx-background-color: linear-gradient(#ff80bf, #66cccc); -fx-text-fill: #fff5e6; -fx-border-color: #b266ff; -fx-border-width: 1; -fx-background-radius: 15; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, #b266ff, 10, 0, 0, 0);"));
    }

    private void styleSlider(Slider slider) {
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(slider.getMax() == 10 ? 1 : 5);
        slider.setMinorTickCount(0);
        slider.setSnapToTicks(slider.getMax() == 10);
        slider.setStyle("-fx-control-inner-background: #fff5e6; -fx-accent: #b266ff;");
    }

    private void styleComboBox(ComboBox<String> comboBox) {
        comboBox.setStyle("-fx-font-family: 'Verdana'; -fx-background-color: #fff5e6; -fx-border-color: #b266ff; -fx-background-radius: 10; -fx-border-radius: 10;");
    }

    private void styleLabel(Label label) {
        label.setFont(new Font("Verdana", 14));
        label.setStyle("-fx-text-fill: #fff5e6;");
    }

    private void styleCanvasPane(StackPane pane) {
        pane.setStyle("-fx-background-color: rgba(255, 245, 230, 0.8); -fx-border-color: #b266ff; -fx-border-width: 1; -fx-background-radius: 5; -fx-border-radius: 5;");
    }

    private void toggleAnimation() {
        if (isAnimating) {
            timer.stop();
            playPauseButton.setText("Play Time Evolution");
            info.appendText("\n⏸ Paused animation");
        } else {
            timer.start();
            playPauseButton.setText("Pause Time Evolution");
            info.appendText("\n▶️ Started animation");
        }
        isAnimating = !isAnimating;
    }

    private void saveQuantumState(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Quantum State");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Properties Files", "*.properties"));
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            Properties props = new Properties();
            props.setProperty("system", currentSystem);
            props.setProperty("n", String.valueOf(n));
            props.setProperty("L", String.valueOf(L));
            props.setProperty("weight", String.valueOf(weightSlider.getValue()));
            props.setProperty("time", String.valueOf(time));
            props.setProperty("showGrid", String.valueOf(showGrid));
            try (FileOutputStream out = new FileOutputStream(file)) {
                props.store(out, "Quantum Bloom Studio State");
                info.appendText("\n💾 Saved state to " + file.getName());
            } catch (IOException ex) {
                info.appendText("\n❌ Error saving state: " + ex.getMessage());
            }
        }
    }

    private void loadQuantumState(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Quantum State");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Properties Files", "*.properties"));
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            Properties props = new Properties();
            try (FileInputStream in = new FileInputStream(file)) {
                props.load(in);
                systemComboBox.setValue(props.getProperty("system", "Particle in a Box"));
                nSlider.setValue(Double.parseDouble(props.getProperty("n", "1")));
                lengthSlider.setValue(Double.parseDouble(props.getProperty("L", "10")));
                weightSlider.setValue(Double.parseDouble(props.getProperty("weight", "0.5")));
                time = Double.parseDouble(props.getProperty("time", "0"));
                showGrid = Boolean.parseBoolean(props.getProperty("showGrid", "false"));
                gridToggle.setSelected(showGrid);
                redrawAll();
                info.appendText("\n📂 Loaded state from " + file.getName());
            } catch (IOException ex) {
                info.appendText("\n❌ Error loading state: " + ex.getMessage());
            }
        }
    }

    private void displayQuantumKnowledge() {
        String knowledge = "";
        switch (currentSystem) {
            case "Particle in a Box":
                knowledge = "🌟 Particle in a Box 🌟\n" +
                        "Wavefunction: ψ_n(x) = √(2/L) sin(nπx/L)\n" +
                        "Energy: E_n = (n² π² ℏ²) / (2 m L²)\n" +
                        "This models a particle confined between two walls, showing quantized energy levels. The probability density shows where the particle is likely to be found! 🔬";
                break;
            case "Quantum Harmonic Oscillator":
                knowledge = "🌈 Quantum Harmonic Oscillator 🌈\n" +
                        "Wavefunction: ψ_n(x) ∝ H_n(x) e^(-x²/2)\n" +
                        "Energy: E_n = ℏ ω (n + 1/2)\n" +
                        "This models systems like molecular vibrations. The wavefunction oscillates more with higher n, and energy levels are equally spaced! ⚛️";
                break;
            case "Superposition (n=1,2)":
                knowledge = "✨ Superposition ✨\n" +
                        "Wavefunction: ψ = w₁ψ₁ + w₂ψ₂\n" +
                        "Weights: w₁ = " + String.format("%.2f", superpositionWeights[0]) + ", w₂ = " + String.format("%.2f", superpositionWeights[1]) + "\n" +
                        "Superposition means the particle is in a mix of states. Adjust the weights to see interference patterns in the probability density! 🌊";
                break;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Quantum Insights 📚");
        alert.setHeaderText("Learn More About " + currentSystem);
        alert.setContentText(knowledge);
        alert.getDialogPane().setStyle("-fx-font-family: 'Verdana'; -fx-background-color: #f0e6ff; -fx-text-fill: #b266ff;");
        alert.showAndWait();
        info.appendText("\n📚 Displayed quantum knowledge!");
    }

    private void displayProjectInfo() {
        String projectDetails = "🌸 Quantum Bloom Studio 🌸\n" +
                "Created on: June 13, 2025\n" +
                "Purpose: A modern, interactive GUI to visualize quantum systems like the Particle in a Box, Quantum Harmonic Oscillator, and Superposition.\n" +
                "Features:\n- Interactive wavefunction and probability plots\n- Multiple quantum systems\n- Educational insights with equations\n- Zoom and pan canvas\n- Save/load quantum states\n- Draggable marker for wavefunction values\n- Sleek, aesthetic design for all users\n" +
                "Explore quantum mechanics with a touch of magic! ✨";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Quantum Bloom Studio 🌸");
        alert.setHeaderText("Project Information");
        alert.setContentText(projectDetails);
        alert.getDialogPane().setStyle("-fx-font-family: 'Verdana'; -fx-background-color: #f0e6ff; -fx-text-fill: #b266ff;");
        alert.showAndWait();
        info.appendText("\nℹ️ Displayed project info!");
    }

    private double computeWavefunctionAtX(double x) {
        double psi1 = 0, psi2 = 0;
        double energy1 = 0, energy2 = 0;

        if (currentSystem.equals("Particle in a Box")) {
            psi1 = Math.sqrt(2.0 / L) * Math.sin(n * Math.PI * (x + L) / (2 * L));
            energy1 = (n * n * Math.PI * Math.PI * hbar * hbar) / (2 * m * L * L);
        } else if (currentSystem.equals("Quantum Harmonic Oscillator")) {
            psi1 = hermitePolynomial(n, x) * Math.exp(-x * x / 2.0);
            psi1 /= Math.sqrt(Math.pow(2, n) * factorial(n) * Math.sqrt(Math.PI));
            energy1 = hbar * omega * (n + 0.5);
        }

        if (currentSystem.equals("Superposition (n=1,2)")) {
            psi1 = Math.sqrt(2.0 / L) * Math.sin(1 * Math.PI * (x + L) / (2 * L));
            psi2 = Math.sqrt(2.0 / L) * Math.sin(2 * Math.PI * (x + L) / (2 * L));
            energy1 = (1 * 1 * Math.PI * Math.PI * hbar * hbar) / (2 * m * L * L);
            energy2 = (2 * 2 * Math.PI * Math.PI * hbar * hbar) / (2 * m * L * L);
        }

        double psiRealVal = 0, psiImagVal = 0;
        if (currentSystem.equals("Superposition (n=1,2)")) {
            psiRealVal = superpositionWeights[0] * psi1 * Math.cos(energy1 * time / hbar) +
                         superpositionWeights[1] * psi2 * Math.cos(energy2 * time / hbar);
            psiImagVal = superpositionWeights[0] * psi1 * Math.sin(energy1 * time / hbar) +
                         superpositionWeights[1] * psi2 * Math.sin(energy2 * time / hbar);
        } else {
            psiRealVal = psi1 * Math.cos(energy1 * time / hbar);
            psiImagVal = psi1 * Math.sin(energy1 * time / hbar);
        }

        return Math.sqrt(psiRealVal * psiRealVal + psiImagVal * psiImagVal);
    }

    private void redrawAll() {
        drawMainCanvas();
        drawEnergyLevels();
        drawProbabilityDensity();
        drawMomentumSpace();
        drawPhasePlot();
        drawExpectationValue();
        drawHeatmap();
    }

    private void drawMainCanvas() {
        mainGc.setFill(Color.rgb(240, 230, 255));
        mainGc.fillRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());

        // Apply zoom and pan
        mainGc.save();
        mainGc.translate(panX, panY);
        mainGc.scale(zoomFactor, zoomFactor);

        // Gridlines
        if (showGrid) {
            mainGc.setStroke(Color.rgb(178, 102, 255, 0.2));
            for (int x = 50; x <= 750; x += 50) {
                mainGc.strokeLine(x, 50, x, 250);
            }
            for (int y = 50; y <= 250; y += 50) {
                mainGc.strokeLine(50, y, 750, y);
            }
        }

        // Draw potential
        mainGc.setStroke(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, new Stop(0, Color.rgb(178, 102, 255)), new Stop(1, Color.rgb(255, 128, 191))));
        mainGc.setLineWidth(2);
        if (currentSystem.equals("Particle in a Box")) {
            mainGc.strokeLine(50, 250, 50, 50);
            mainGc.strokeLine(750, 250, 750, 50);
            mainGc.strokeLine(50, 250, 750, 250);
        } else if (currentSystem.equals("Quantum Harmonic Oscillator")) {
            for (int i = 0; i < 200; i++) {
                double x1 = 50 + i * 3.5;
                double x2 = 50 + (i + 1) * 3.5;
                double y1 = 250 - 5 * Math.pow((x1 - 400) / 100, 2);
                double y2 = 250 - 5 * Math.pow((x2 - 400) / 100, 2);
                mainGc.strokeLine(x1, y1, x2, y2);
            }
        }

        // Compute wavefunction
        double dx = (750 - 50) / 200.0;
        double[] psiReal = new double[201];
        double[] psiImag = new double[201];
        double[] prob = new double[201];
        double xScale = (750 - 50) / (2 * L);
        double yScale = 50;

        for (int i = 0; i <= 200; i++) {
            double x = (i - 100) * L / 100.0;
            double psi1 = 0, psi2 = 0;
            double energy1 = 0, energy2 = 0;

            if (currentSystem.equals("Particle in a Box")) {
                psi1 = Math.sqrt(2.0 / L) * Math.sin(n * Math.PI * (x + L) / (2 * L));
                energy1 = (n * n * Math.PI * Math.PI * hbar * hbar) / (2 * m * L * L);
            } else if (currentSystem.equals("Quantum Harmonic Oscillator")) {
                psi1 = hermitePolynomial(n, x) * Math.exp(-x * x / 2.0);
                psi1 /= Math.sqrt(Math.pow(2, n) * factorial(n) * Math.sqrt(Math.PI));
                energy1 = hbar * omega * (n + 0.5);
            }

            if (currentSystem.equals("Superposition (n=1,2)")) {
                psi1 = Math.sqrt(2.0 / L) * Math.sin(1 * Math.PI * (x + L) / (2 * L));
                psi2 = Math.sqrt(2.0 / L) * Math.sin(2 * Math.PI * (x + L) / (2 * L));
                energy1 = (1 * 1 * Math.PI * Math.PI * hbar * hbar) / (2 * m * L * L);
                energy2 = (2 * 2 * Math.PI * Math.PI * hbar * hbar) / (2 * m * L * L);
            }

            double psiRealVal = 0, psiImagVal = 0;
            if (currentSystem.equals("Superposition (n=1,2)")) {
                psiRealVal = superpositionWeights[0] * psi1 * Math.cos(energy1 * time / hbar) +
                             superpositionWeights[1] * psi2 * Math.cos(energy2 * time / hbar);
                psiImagVal = superpositionWeights[0] * psi1 * Math.sin(energy1 * time / hbar) +
                             superpositionWeights[1] * psi2 * Math.sin(energy2 * time / hbar);
            } else {
                psiRealVal = psi1 * Math.cos(energy1 * time / hbar);
                psiImagVal = psi1 * Math.sin(energy1 * time / hbar);
            }

            psiReal[i] = yScale * psiRealVal;
            psiImag[i] = yScale * psiImagVal;
            prob[i] = yScale * (psiRealVal * psiRealVal + psiImagVal * psiImagVal);
        }

        // Wavefunction (Real: Teal, Imag: Violet)
        mainGc.setStroke(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, new Stop(0, Color.rgb(102, 204, 204)), new Stop(1, Color.rgb(51, 153, 153))));
        mainGc.setLineWidth(2);
        for (int i = 0; i < 200; i++) {
            mainGc.strokeLine(50 + i * dx, 150 - psiReal[i], 50 + (i + 1) * dx, 150 - psiReal[i + 1]);
        }
        mainGc.setStroke(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, new Stop(0, Color.rgb(204, 153, 255)), new Stop(1, Color.rgb(153, 102, 204))));
        for (int i = 0; i < 200; i++) {
            mainGc.strokeLine(50 + i * dx, 150 - psiImag[i], 50 + (i + 1) * dx, 150 - psiImag[i + 1]);
        }

        // Probability density (Magenta)
        mainGc.setStroke(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, new Stop(0, Color.rgb(255, 128, 191)), new Stop(1, Color.rgb(204, 102, 153))));
        mainGc.setLineWidth(2);
        for (int i = 0; i < 200; i++) {
            mainGc.strokeLine(50 + i * dx, 150 - prob[i], 50 + (i + 1) * dx, 150 - prob[i + 1]);
        }

        // Draw draggable marker
        double markerCanvasX = 50 + (markerX + L) * (750 - 50) / (2 * L);
        mainGc.setStroke(Color.RED);
        mainGc.setLineWidth(2);
        mainGc.strokeLine(markerCanvasX, 50, markerCanvasX, 250);
        mainGc.setFill(Color.RED);
        mainGc.fillOval(markerCanvasX - 5, 145, 10, 10);
        double psiAtMarker = computeWavefunctionAtX(markerX);
        mainGc.fillText("ψ: " + String.format("%.2f", psiAtMarker), markerCanvasX + 5, 140);

        // Labels
        mainGc.setFill(Color.rgb(178, 102, 255));
        mainGc.setFont(new Font("Verdana", 12));
        mainGc.fillText("Real(ψ)", 50, 30);
        mainGc.fillText("Imag(ψ)", 100, 30);
        mainGc.fillText("Probability", 150, 30);

        mainGc.restore();
    }

    private double hermitePolynomial(int n, double x) {
        if (n == 0) return 1.0;
        if (n == 1) return 2 * x;
        double h0 = 1.0, h1 = 2 * x;
        for (int i = 2; i <= n; i++) {
            double h2 = 2 * x * h1 - 2 * (i - 1) * h0;
            h0 = h1;
            h1 = h2;
        }
        return h1;
    }

    private long factorial(int n) {
        long result = 1;
        for (int i = 1; i <= n; i++) result *= i;
        return result;
    }

    private double getEnergy(int n) {
        if (currentSystem.equals("Particle in a Box") || currentSystem.equals("Superposition (n=1,2)")) {
            return (n * n * Math.PI * Math.PI * hbar * hbar) / (2 * m * L * L);
        } else {
            return hbar * omega * (n + 0.5);
        }
    }

    private void drawEnergyLevels() {
        energyGc.setFill(Color.rgb(255, 245, 230, 0.8));
        energyGc.fillRect(0, 0, energyCanvas.getWidth(), energyCanvas.getHeight());

        if (showGrid) {
            energyGc.setStroke(Color.rgb(178, 102, 255, 0.2));
            for (int y = 10; y <= 90; y += 20) {
                energyGc.strokeLine(40, y, 160, y);
            }
        }

        for (int i = 1; i <= 5; i++) {
            double energy = getEnergy(i);
            double y = 90 - (energy * 20);
            if (currentSystem.equals("Superposition (n=1,2)") && (i == 1 || i == 2)) {
                energyGc.setFill(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, new Stop(0, Color.rgb(255, 128, 191)), new Stop(1, Color.rgb(204, 102, 153))));
            } else if (i == n && !currentSystem.equals("Superposition (n=1,2)")) {
                energyGc.setFill(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, new Stop(0, Color.rgb(255, 128, 191)), new Stop(1, Color.rgb(204, 102, 153))));
            } else {
                energyGc.setFill(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, new Stop(0, Color.rgb(102, 204, 204)), new Stop(1, Color.rgb(51, 153, 153))));
            }
            energyGc.fillRect(40, y, 120, 10);
            energyGc.setFill(Color.rgb(178, 102, 255));
            energyGc.fillText("n=" + i + " E=" + String.format("%.2f", energy), 10, y + 8);
        }
    }

    private void drawProbabilityDensity() {
        probGc.setFill(Color.rgb(255, 245, 230, 0.8));
        probGc.fillRect(0, 0, probCanvas.getWidth(), probCanvas.getHeight());

        if (showGrid) {
            probGc.setStroke(Color.rgb(178, 102, 255, 0.2));
            for (int x = 0; x <= 200; x += 40) {
                probGc.strokeLine(x, 0, x, 100);
            }
            for (int y = 0; y <= 100; y += 20) {
                probGc.strokeLine(0, y, 200, y);
            }
        }

        double dx = probCanvas.getWidth() / 200.0;
        double[] prob = new double[201];
        for (int i = 0; i <= 200; i++) {
            double x = (i - 100) * L / 100.0;
            double psi1 = 0, psi2 = 0;
            double energy1 = 0, energy2 = 0;

            if (currentSystem.equals("Particle in a Box")) {
                psi1 = Math.sqrt(2.0 / L) * Math.sin(n * Math.PI * (x + L) / (2 * L));
                energy1 = (n * n * Math.PI * Math.PI * hbar * hbar) / (2 * m * L * L);
            } else if (currentSystem.equals("Quantum Harmonic Oscillator")) {
                psi1 = hermitePolynomial(n, x) * Math.exp(-x * x / 2.0);
                psi1 /= Math.sqrt(Math.pow(2, n) * factorial(n) * Math.sqrt(Math.PI));
                energy1 = hbar * omega * (n + 0.5);
            }

            if (currentSystem.equals("Superposition (n=1,2)")) {
                psi1 = Math.sqrt(2.0 / L) * Math.sin(1 * Math.PI * (x + L) / (2 * L));
                psi2 = Math.sqrt(2.0 / L) * Math.sin(2 * Math.PI * (x + L) / (2 * L));
                energy1 = (1 * 1 * Math.PI * Math.PI * hbar * hbar) / (2 * m * L * L);
                energy2 = (2 * 2 * Math.PI * Math.PI * hbar * hbar) / (2 * m * L * L);
            }

            double psiRealVal = 0, psiImagVal = 0;
            if (currentSystem.equals("Superposition (n=1,2)")) {
                psiRealVal = superpositionWeights[0] * psi1 * Math.cos(energy1 * time / hbar) +
                             superpositionWeights[1] * psi2 * Math.cos(energy2 * time / hbar);
                psiImagVal = superpositionWeights[0] * psi1 * Math.sin(energy1 * time / hbar) +
                             superpositionWeights[1] * psi2 * Math.sin(energy2 * time / hbar);
            } else {
                psiRealVal = psi1 * Math.cos(energy1 * time / hbar);
                psiImagVal = psi1 * Math.sin(energy1 * time / hbar);
            }

            prob[i] = 50 * (psiRealVal * psiRealVal + psiImagVal * psiImagVal);
        }

        probGc.setStroke(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, new Stop(0, Color.rgb(255, 128, 191)), new Stop(1, Color.rgb(204, 102, 153))));
        probGc.setLineWidth(2);
        for (int i = 0; i < 200; i++) {
            probGc.strokeLine(i * dx, 80 - prob[i], (i + 1) * dx, 80 - prob[i + 1]);
        }
    }

    private void drawMomentumSpace() {
        momentumGc.setFill(Color.rgb(255, 245, 230, 0.8));
        momentumGc.fillRect(0, 0, momentumCanvas.getWidth(), momentumCanvas.getHeight());

        if (showGrid) {
            momentumGc.setStroke(Color.rgb(178, 102, 255, 0.2));
            for (int x = 0; x <= 200; x += 40) {
                momentumGc.strokeLine(x, 0, x, 100);
            }
            for (int y = 0; y <= 100; y += 20) {
                momentumGc.strokeLine(0, y, 200, y);
            }
        }

        double dp = momentumCanvas.getWidth() / 200.0;
        double[] phi = new double[201];
        for (int i = 0; i <= 200; i++) {
            double p = (i - 100) * 0.1;
            double phiVal = 0;

            if (currentSystem.equals("Particle in a Box")) {
                phiVal = (n % 2 == 0 ? 1 : -1) * Math.sqrt(2.0 / L) * (Math.sin(n * Math.PI * (p + L) / (2 * L)));
            } else if (currentSystem.equals("Quantum Harmonic Oscillator")) {
                phiVal = hermitePolynomial(n, p) * Math.exp(-p * p / 2.0);
                phiVal /= Math.sqrt(Math.pow(2, n) * factorial(n) * Math.sqrt(Math.PI));
            } else {
                double phi1 = Math.sqrt(2.0 / L) * Math.sin(1 * Math.PI * (p + L) / (2 * L));
                double phi2 = -Math.sqrt(2.0 / L) * Math.sin(2 * Math.PI * (p + L) / (2 * L));
                phiVal = superpositionWeights[0] * phi1 + superpositionWeights[1] * phi2;
            }

            phi[i] = 50 * phiVal;
        }

        momentumGc.setStroke(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, new Stop(0, Color.rgb(102, 204, 204)), new Stop(1, Color.rgb(51, 153, 153))));
        momentumGc.setLineWidth(2);
        for (int i = 0; i < 200; i++) {
            momentumGc.strokeLine(i * dp, 50 - phi[i], (i + 1) * dp, 50 - phi[i + 1]);
        }
    }

    private void drawPhasePlot() {
        phaseGc.setFill(Color.rgb(255, 245, 230, 0.8));
        phaseGc.fillRect(0, 0, phaseCanvas.getWidth(), phaseCanvas.getHeight());

        if (showGrid) {
            phaseGc.setStroke(Color.rgb(178, 102, 255, 0.2));
            for (int x = 0; x <= 200; x += 40) {
                phaseGc.strokeLine(x, 0, x, 100);
            }
            for (int y = 0; y <= 100; y += 20) {
                phaseGc.strokeLine(0, y, 200, y);
            }
        }

        phaseGc.setStroke(Color.rgb(178, 102, 255));
        phaseGc.strokeLine(100, 10, 100, 90);
        phaseGc.strokeLine(10, 50, 190, 50);

        double x = 0;
        double psi1 = 0, psi2 = 0;
        double energy1 = 0, energy2 = 0;

        if (currentSystem.equals("Particle in a Box")) {
            psi1 = Math.sqrt(2.0 / L) * Math.sin(n * Math.PI * (x + L) / (2 * L));
            energy1 = (n * n * Math.PI * Math.PI * hbar * hbar) / (2 * m * L * L);
        } else if (currentSystem.equals("Quantum Harmonic Oscillator")) {
            psi1 = hermitePolynomial(n, x) * Math.exp(-x * x / 2.0);
            psi1 /= Math.sqrt(Math.pow(2, n) * factorial(n) * Math.sqrt(Math.PI));
            energy1 = hbar * omega * (n + 0.5);
        }

        if (currentSystem.equals("Superposition (n=1,2)")) {
            psi1 = Math.sqrt(2.0 / L) * Math.sin(1 * Math.PI * (x + L) / (2 * L));
            psi2 = Math.sqrt(2.0 / L) * Math.sin(2 * Math.PI * (x + L) / (2 * L));
            energy1 = (1 * 1 * Math.PI * Math.PI * hbar * hbar) / (2 * m * L * L);
            energy2 = (2 * 2 * Math.PI * Math.PI * hbar * hbar) / (2 * m * L * L);
        }

        double psiRealVal = 0, psiImagVal = 0;
        if (currentSystem.equals("Superposition (n=1,2)")) {
            psiRealVal = superpositionWeights[0] * psi1 * Math.cos(energy1 * time / hbar) +
                         superpositionWeights[1] * psi2 * Math.cos(energy2 * time / hbar);
            psiImagVal = superpositionWeights[0] * psi1 * Math.sin(energy1 * time / hbar) +
                         superpositionWeights[1] * psi2 * Math.sin(energy2 * time / hbar);
        } else {
            psiRealVal = psi1 * Math.cos(energy1 * time / hbar);
            psiImagVal = psi1 * Math.sin(energy1 * time / hbar);
        }

        double xPos = 100 + 50 * psiRealVal;
        double yPos = 50 - 50 * psiImagVal;
        phaseGc.setFill(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, new Stop(0, Color.rgb(204, 153, 255)), new Stop(1, Color.rgb(153, 102, 204))));
        phaseGc.fillOval(xPos - 5, yPos - 5, 10, 10);
    }

    private void drawExpectationValue() {
        expectationGc.setFill(Color.rgb(255, 245, 230, 0.8));
        expectationGc.fillRect(0, 0, expectationCanvas.getWidth(), expectationCanvas.getHeight());

        if (showGrid) {
            expectationGc.setStroke(Color.rgb(178, 102, 255, 0.2));
            for (int x = 0; x <= 200; x += 40) {
                expectationGc.strokeLine(x, 0, x, 100);
            }
            for (int y = 0; y <= 100; y += 20) {
                expectationGc.strokeLine(0, y, 200, y);
            }
        }

        double dx = expectationCanvas.getWidth() / 200.0;
        double[] expectation = new double[201];
        for (int i = 0; i <= 200; i++) {
            double t = i * 0.1;
            double xExp = 0;
            for (int j = 0; j <= 200; j++) {
                double x = (j - 100) * L / 100.0;
                double psi1 = 0, psi2 = 0;
                double energy1 = 0, energy2 = 0;

                if (currentSystem.equals("Particle in a Box")) {
                    psi1 = Math.sqrt(2.0 / L) * Math.sin(n * Math.PI * (x + L) / (2 * L));
                    energy1 = (n * n * Math.PI * Math.PI * hbar * hbar) / (2 * m * L * L);
                } else if (currentSystem.equals("Quantum Harmonic Oscillator")) {
                    psi1 = hermitePolynomial(n, x) * Math.exp(-x * x / 2.0);
                    psi1 /= Math.sqrt(Math.pow(2, n) * factorial(n) * Math.sqrt(Math.PI));
                    energy1 = hbar * omega * (n + 0.5);
                }

                if (currentSystem.equals("Superposition (n=1,2)")) {
                    psi1 = Math.sqrt(2.0 / L) * Math.sin(1 * Math.PI * (x + L) / (2 * L));
                    psi2 = Math.sqrt(2.0 / L) * Math.sin(2 * Math.PI * (x + L) / (2 * L));
                    energy1 = (1 * 1 * Math.PI * Math.PI * hbar * hbar) / (2 * m * L * L);
                    energy2 = (2 * 2 * Math.PI * Math.PI * hbar * hbar) / (2 * m * L * L);
                }

                double psiRealVal = 0, psiImagVal = 0;
                if (currentSystem.equals("Superposition (n=1,2)")) {
                    psiRealVal = superpositionWeights[0] * psi1 * Math.cos(energy1 * t / hbar) +
                                 superpositionWeights[1] * psi2 * Math.cos(energy2 * t / hbar);
                    psiImagVal = superpositionWeights[0] * psi1 * Math.sin(energy1 * t / hbar) +
                                 superpositionWeights[1] * psi2 * Math.sin(energy2 * t / hbar);
                } else {
                    psiRealVal = psi1 * Math.cos(energy1 * t / hbar);
                    psiImagVal = psi1 * Math.sin(energy1 * t / hbar);
                }

                double prob = psiRealVal * psiRealVal + psiImagVal * psiImagVal;
                xExp += x * prob * (2 * L / 200.0);
            }
            expectation[i] = 10 * xExp;
        }

        expectationGc.setStroke(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, new Stop(0, Color.rgb(102, 204, 204)), new Stop(1, Color.rgb(51, 153, 153))));
        expectationGc.setLineWidth(2);
        for (int i = 0; i < 200; i++) {
            expectationGc.strokeLine(i * dx, 50 - expectation[i], (i + 1) * dx, 50 - expectation[i + 1]);
        }
    }

    private void drawHeatmap() {
        heatmapGc.setFill(Color.rgb(255, 245, 230, 0.8));
        heatmapGc.fillRect(0, 0, heatmapCanvas.getWidth(), heatmapCanvas.getHeight());

        double dx = heatmapCanvas.getWidth() / 50.0;
        double dt = heatmapCanvas.getHeight() / 50.0;
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                double x = (i - 25) * L / 25.0;
                double t = j * 0.5;
                double psi1 = 0, psi2 = 0;
                double energy1 = 0, energy2 = 0;

                if (currentSystem.equals("Particle in a Box")) {
                    psi1 = Math.sqrt(2.0 / L) * Math.sin(n * Math.PI * (x + L) / (2 * L));
                    energy1 = (n * n * Math.PI * Math.PI * hbar * hbar) / (2 * m * L * L);
                } else if (currentSystem.equals("Quantum Harmonic Oscillator")) {
                    psi1 = hermitePolynomial(n, x) * Math.exp(-x * x / 2.0);
                    psi1 /= Math.sqrt(Math.pow(2, n) * factorial(n) * Math.sqrt(Math.PI));
                    energy1 = hbar * omega * (n + 0.5);
                }

                if (currentSystem.equals("Superposition (n=1,2)")) {
                    psi1 = Math.sqrt(2.0 / L) * Math.sin(1 * Math.PI * (x + L) / (2 * L));
                    psi2 = Math.sqrt(2.0 / L) * Math.sin(2 * Math.PI * (x + L) / (2 * L));
                    energy1 = (1 * 1 * Math.PI * Math.PI * hbar * hbar) / (2 * m * L * L);
                    energy2 = (2 * 2 * Math.PI * Math.PI * hbar * hbar) / (2 * m * L * L);
                }

                double psiRealVal = 0, psiImagVal = 0;
                if (currentSystem.equals("Superposition (n=1,2)")) {
                    psiRealVal = superpositionWeights[0] * psi1 * Math.cos(energy1 * t / hbar) +
                                 superpositionWeights[1] * psi2 * Math.cos(energy2 * t / hbar);
                    psiImagVal = superpositionWeights[0] * psi1 * Math.sin(energy1 * t / hbar) +
                                 superpositionWeights[1] * psi2 * Math.sin(energy2 * t / hbar);
                } else {
                    psiRealVal = psi1 * Math.cos(energy1 * t / hbar);
                    psiImagVal = psi1 * Math.sin(energy1 * t / hbar);
                }

                double prob = psiRealVal * psiRealVal + psiImagVal * psiImagVal;
                heatmapGc.setFill(Color.rgb(255, 128, 191, Math.min(prob * 5, 1.0)));
                heatmapGc.fillRect(i * dx, j * dt, dx, dt);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
