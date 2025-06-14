<h1 align="center">
  🌌 QuantumBloomStudio
</h1>

<p align="center">
  <em>“Where quantum meets canvas”</em><br>
  <strong>An immersive, interactive JavaFX application for visualizing quantum mechanics.</strong>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/java-17%2B-red?style=for-the-badge&logo=java" />
  <img src="https://img.shields.io/badge/JavaFX-17%2B-blue?style=for-the-badge&logo=java" />
  <img src="https://img.shields.io/badge/license-MIT-purple?style=for-the-badge" />
  <img src="https://img.shields.io/badge/status-active--development-magenta?style=for-the-badge" />
</p>

---

## 🪐 Overview
**QuantumBloomStudio** — A JavaFX tool to interactively visualize wavefunctions, energy levels, and quantum probability densities.

---

## ✨ Features
- **Live Wavefunction Visualization** — Real, Imaginary, and Probability Density
- **Multiple Quantum Systems** — Particle in a Box, Harmonic Oscillator, and Superposition States
- **Time Evolution** — Animate quantum states with pause/resume control
- **Dynamic Control Panel** — Modify quantum number \(n\), box length \(L\), and superposition weights
- **Auxiliary Plots** — Energy levels, phase space, momentum space, expectation values, and heatmaps
- **Save/Load State** — Reuse and share quantum scenarios
- **Educational Dialogs** — Equations and insights explained with visuals
- **Aesthetic UI** — Sleek violet-magenta gradient theme with soft drop shadows

---

## 🖥️ UI Preview

Here's a look at the Quantum Bloom Studio interface:

### 🌌 Wavefunction Controls
![Wavefunction Controls](ui-preview/Screenshot%202025-06-14%20094547.png)

### 📊 Real-time Output Graphs
![Graph Preview](ui-preview/Screenshot%202025-06-14%20094814.png)

---

## 🛠️ Prerequisites
- **Java JDK**: 17+
- **JavaFX SDK**: 17+ (Download from [GluonHQ](https://gluonhq.com/products/javafx/))
- **Windows OS**: Recommended (adjust `.bat` for other OS)

---

## 🚀 Setup & Run Instructions (Windows)

### 🔁 Step 1: Clone the Repository

```bash
git clone https://github.com/Manaswani96/QuantumBloom.git
cd QuantumBloomStudio
```

---

### ⚙️ Step 2: Install Java and JavaFX

Make sure the following are installed:

* ✅ [Java JDK 17 or later](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
* ✅ [JavaFX SDK 17 or later](https://gluonhq.com/products/javafx/)

---

### 🛠️ Step 3: Configure `compiler.bat` and `run.bat`

> These batch files must be edited to match **your system paths**.

#### 📁 Example Folder Structure (Edit as per your system):

```plaintext
C:\Program Files\Java\jdk-17\
C:\javafx-sdk-17\
```

#### 📄 `compiler.bat`

```bat
@echo off
:: Change the paths below to match your system
set JAVA_HOME="C:\Program Files\Java\jdk-17"
set JAVAFX_LIB="C:\javafx-sdk-17\lib"

%JAVA_HOME%\bin\javac --module-path %JAVAFX_LIB% --add-modules javafx.controls,javafx.fxml QuantumBloomStudio.java
```

#### 📄 `run.bat`

```bat
@echo off
:: Make sure the paths match exactly
set JAVA_HOME="C:\Program Files\Java\jdk-17"
set JAVAFX_LIB="C:\javafx-sdk-17\lib"

%JAVA_HOME%\bin\java --module-path %JAVAFX_LIB% --add-modules javafx.controls,javafx.fxml QuantumBloomStudio
```

---

### ▶️ Step 4: Compile and Run

After editing the `.bat` files:

```bash
compiler.bat
run.bat
```

This will compile and launch the **Quantum Bloom Studio** GUI.

---

### 🧠 Pro Tips:

* If Java or JavaFX are installed in a different folder, **double-check using `where java`** in Command Prompt.
* You can also run JavaFX from an IDE like IntelliJ or Eclipse by setting up the VM options and module paths accordingly.

---

## 🧾 Folder Structure
```
QuantumBloomStudio/
├── QuantumBloomStudio.java
├── compiler.bat
├── run.bat
├── ui-preview/
│   ├── image1
│   └── image2
└── README.md
```

---

## 🧪 Roadmap
- [ ] Add dark mode toggle
- [ ] Export wavefunction plots
- [ ] Include quantum tunneling visualization
- [ ] Add support for Linux/Mac launch scripts
- [ ] Add detailed educational popups with LaTeX math

---

## 👩‍💻 Author
**[Mahi.K]** — [GitHub](https://github.com/Manaswani96)

---

## 📄 License
This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.

---

> “Happy quantum exploring!” 🌟
