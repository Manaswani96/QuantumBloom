Quantum Bloom Studio 🌟
A modern, interactive GUI to explore quantum physics concepts with a sleek, aesthetic design. Visualize wavefunctions, probability densities, and more for systems like the Particle in a Box, Quantum Harmonic Oscillator, and Superposition states.

Overview
Quantum Bloom Studio is an educational tool built with JavaFX, designed to make quantum mechanics accessible and visually appealing. It features interactive controls, detailed graphs, and educational insights, all wrapped in a modern, magenta-violet aesthetic that appeals to all users.

Created On: June 13, 2025
Tech Stack: JavaFX, JDK 24, JavaFX SDK 24.0.1
Purpose: Visualize quantum systems with interactive controls and educational content.


Features

Interactive Visualizations:
Main canvas displaying wavefunctions (real and imaginary parts) and probability densities.
Hover over the main canvas to see wavefunction values at specific points.


Multiple Quantum Systems:
Particle in a Box
Quantum Harmonic Oscillator
Superposition of states (n=1 and n=2)


Graphs and Plots:
Energy levels (clickable to set quantum number (n))
Probability density over time
Momentum-space wavefunction
Phase plot (Real vs. Imaginary parts)
Expectation value ((\langle x \rangle)) over time
2D heatmap of probability density


Interactivity:
Sliders to adjust quantum number ((n)), box length/scale ((L)), and superposition weights.
Toggle gridlines on graphs for clarity.
Play/pause time evolution animations.


Educational Content:
"Quantum Insights" panel with detailed explanations and equations.
"About This Project" section with project details.


Aesthetic Design:
Modern, sleek look with a magenta (#FF80BF), violet (#CC99FF), teal (#66CCCC), and cream (#FFF5E6) palette.
Glassmorphism effects, rounded corners, and glowing transitions.




Prerequisites
Before running the project, ensure you have the following installed:

JDK 24: Download and install from Oracle.
JavaFX SDK 24.0.1: Download from Gluon. Extract to a directory (e.g., C:\Users\YourUser\OneDrive\Desktop\javafx-sdk-24.0.1).
A code editor (e.g., VS Code, IntelliJ IDEA) to manage the files.


Setup

Clone or Download the Project:

Save the project files (QuantumBloomStudio.java, compile.bat, run.bat) to a directory (e.g., C:\Users\YourUser\Projects\QuantumBloomStudio).


Verify File Paths:

Open compile.bat and run.bat in a text editor.
Update the paths for JAVA_HOME and FX to match your system:
JAVA_HOME: Path to your JDK 24 installation (e.g., C:\Program Files\Java\jdk-24).
FX: Path to your JavaFX SDK lib folder (e.g., C:\Users\YourUser\OneDrive\Desktop\javafx-sdk-24.0.1\lib).



compile.bat:
@echo off
echo 💡 Compiling QuantumBloomStudio.java...
set JAVA_HOME="C:\Program Files\Java\jdk-24"
set FX="C:\Users\YourUser\OneDrive\Desktop\javafx-sdk-24.0.1\lib"
%JAVA_HOME%\bin\javac.exe ^
--module-path %FX% ^
--add-modules javafx.controls ^
QuantumBloomStudio.java
if %errorlevel% neq 0 (
    echo ❌ Compile error. Fix your code and try again.
) else (
    echo ✅ Compilation successful.
)
pause

run.bat:
@echo off
echo 🌸 Launching Quantum Bloom Studio...
set JAVA_HOME="C:\Program Files\Java\jdk-24"
set FX="C:\Users\YourUser\OneDrive\Desktop\javafx-sdk-24.0.1\lib"
%JAVA_HOME%\bin\java.exe ^
--module-path %FX% ^
--add-modules javafx.controls ^
QuantumBloomStudio
pause


Save the Main Code:

Ensure QuantumBloomStudio.java (provided in the project) is in the same directory as the batch files.




How to Run

Compile the Code:

Double-click compile.bat or run it from the command line:compile.bat


If successful, you’ll see "✅ Compilation successful."


Launch the Application:

Double-click run.bat or run it from the command line:run.bat


The Quantum Bloom Studio GUI will launch.




Usage Guide

Select a Quantum System:

Use the dropdown menu to choose between "Particle in a Box," "Quantum Harmonic Oscillator," or "Superposition (n=1,2)."


Adjust Parameters:

Quantum Number ((n)): Use the slider to set (n) (1 to 10). Disabled in Superposition mode.
Box Length/Scale ((L)): Adjust the length or scale (5 to 20).
Superposition Weights: In Superposition mode, adjust the weight of the (n=1) state (the (n=2) weight adjusts automatically).


Interact with Graphs:

Hover over the main canvas to see wavefunction values.
Click on energy levels to set (n).
Toggle gridlines for better readability of graphs.


Animate and Explore:

Click "Play Time Evolution" to animate the wavefunction over time.
Use "Reset" to restore default settings.


Learn More:

Click "Learn More!" to read quantum insights with equations.
Click "About Project" to view project details.




Screenshots
(Screenshots would be added here if available. For now, imagine a sleek GUI with a magenta-violet-teal palette, showing wavefunctions, graphs, and interactive controls.)

License
This project is licensed under the MIT License. See the LICENSE file for details.

Notes

Font: The GUI uses "Verdana" for a modern look. If you prefer a cuter style, replace "Verdana" with "Comic Sans MS" in the code.
Performance: The application is optimized for smooth animations. For slower systems, reduce the number of points in plots by adjusting the loop increments (e.g., change i++ to i+=2 in drawing loops).
Customization: To change the color palette, modify the hex codes in the code (e.g., #FF80BF for magenta, #CC99FF for violet).
Further Enhancements: Want more quantum systems or features? Contact the developer for updates!


Troubleshooting

Compilation Error: Ensure JDK 24 and JavaFX SDK 24.0.1 are correctly installed, and paths in compile.bat are correct.
GUI Not Launching: Check run.bat paths and ensure the code compiled successfully.
Visual Glitches: Adjust your screen resolution or reduce the number of points in plots for better performance.


Acknowledgments
Built with love by a quantum enthusiast on June 13, 2025. Inspired by the beauty of quantum mechanics and the desire to make learning fun and interactive for all. 🌟
Enjoy exploring the quantum world with Quantum Bloom Studio! ✨
