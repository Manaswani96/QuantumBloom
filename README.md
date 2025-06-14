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
  <img src="https://img.shields.io/badge/status-active-development-magenta?style=for-the-badge" />
</p>

---

##  QuantumBloomStudio

> QuantumBloomStudio is an interactive application built with JavaFX, crafted to make the beauty of quantum mechanics tangible and explorable through animated wavefunctions, rich visual effects, and modern UI.

---

## ✨ Features

- **🎨 Live Wavefunction Visualization**  
  Real + Imaginary parts & Probability density

- **🔢 Multi-System Support**  
  - Particle in a Box  
  - Quantum Harmonic Oscillator  
  - Superposition States (n = 1, 2)

- **🧮 Dynamic Controls**  
  Adjust `n`, `L`, time speed, and superposition weights

- **🎬 Time Evolution Animations**  
  With play/pause support and dynamic graph updates

- **📍 Interactive Marker**  
  Drag to inspect specific wavefunction values

- **📦 Save/Load System States**  
  Use `.properties` files to store quantum setups

- **📈 Quantum Extras**  
  Energy levels, momentum space, phase, heatmaps, expectation values

- **💅 Modern Aesthetic**  
  Dark gradient background, glowing plots, soft shadows

---

## 🖼 Screenshots

| Main Interface | Controls Sidebar |
|----------------|------------------|
| ![Main UI](https://via.placeholder.com/800x400.png?text=Wavefunction+Canvas+%28Dark+Theme%29) | ![Sidebar](https://via.placeholder.com/300x400.png?text=Quantum+Controls) |

---

## 🎥 Demo Preview (GIF)

<p align="center">
  <img src="https://via.placeholder.com/700x400.gif?text=Quantum+Animation+Demo+Coming+Soon" alt="Quantum Animation Demo">
</p>

---

## 🛠 Prerequisites

- **Java Development Kit (JDK)**: 17 or later  
- **JavaFX SDK**: 17 or compatible  
- **Git**: To clone the repo  
- **Windows OS**: (or adapt batch files for Mac/Linux)

---

## ⚙️ Setup Instructions

### 🧾 1. Clone the Repo
```bash
git clone https://github.com/your-username/QuantumBloomStudio.git
cd QuantumBloomStudio
```

### 2. Compilation
Edit the JavaFX SDK path as per your local setup:
```bash
:: compiler.bat
javac --module-path "C:\javafx-sdk-17\lib" --add-modules javafx.controls,javafx.fxml QuantumBloomStudio.java
```

### 3. Execution
```bash
:: run.bat
java --module-path "C:\javafx-sdk-17\lib" --add-modules javafx.controls,javafx.fxml QuantumBloomStudio
```
### Project Structure
```
QuantumBloomStudio/
├── QuantumBloomStudio.java
├── compiler.bat
├── run.bat
└── README.md
```
### License
This project is licensed under the MIT License. Feel free to use, modify, and distribute it with credit.

### Author - Mahi.K 

### Collaborators:

Quantum Bloom Studio is designed to blend aesthetics with rigorous simulation. Ideal for physics enthusiasts, educators, and students looking for elegant visual learning tools.


