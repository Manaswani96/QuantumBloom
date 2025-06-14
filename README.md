# Quantum Bloom Studio

![Version](https://img.shields.io/badge/version-1.0.0-6a0dad?style=for-the-badge)
![JavaFX](https://img.shields.io/badge/built%20with-JavaFX-ff69b4?style=for-the-badge)
![Status](https://img.shields.io/badge/status-active-228b22?style=for-the-badge)
![License](https://img.shields.io/badge/license-MIT-blue?style=for-the-badge)
![Platform](https://img.shields.io/badge/platform-Windows%2011-blueviolet?style=for-the-badge)

> **Quantum Bloom Studio** is a sleek, interactive JavaFX application for simulating and visualizing quantum mechanical systems. Designed with precision and style, it brings complex quantum concepts to life.

---

## ✨ Features

- Interactive simulation of wavefunctions (real, imaginary, and probability densities)
- Systems included: Infinite Potential Well, Harmonic Oscillator, Superposition States
- Real-time control of parameters: Energy level `n`, box length `L`, weights
- Animated time evolution with dynamic phase visualization
- Gradient dark-themed UI for a modern physics lab feel
- Save/load configuration via `.properties` file
- Modular plot structure: phase space, energy eigenstates, expectation values

---

## 📷 Screenshots

| Main Visualization Panel | Controls + Settings |
|--------------------------|---------------------|
| ![](./images/screenshot1-dark.png) | ![](./images/screenshot2-dark.png) |

---

## 🕹️ Demo

Demo GIF of the animated wavefunction is under development and will be available in the next release.

---

## 📚 Installation

### Prerequisites

- Java 17+
- JavaFX SDK 17+
- Git
- Any Java IDE (VS Code, IntelliJ, etc.)

### Clone the Repository

```bash
git clone https://github.com/your-username/QuantumBloomStudio.git
cd QuantumBloomStudio
```

### Compile and Run

Update JavaFX SDK path based on your machine:

```bat
:: compiler.bat
javac --module-path "C:\javafx-sdk-17\lib" --add-modules javafx.controls,javafx.fxml QuantumBloomStudio.java
```

```bat
:: run.bat
java --module-path "C:\javafx-sdk-17\lib" --add-modules javafx.controls,javafx.fxml QuantumBloomStudio
```

---

## 🔄 Project Structure

```
QuantumBloomStudio/
├── QuantumBloomStudio.java
├── compiler.bat
├── run.bat
├── images/
│   ├── screenshot1-dark.png
│   └── screenshot2-dark.png
├── LICENSE
└── README.md
```

---

## 👁️ Roadmap

- [ ] Add quantum tunneling visualizations
- [ ] Export wave animations (GIF/MP4)
- [ ] Cross-platform support (Mac/Linux)
- [ ] Toggleable light/dark mode
- [ ] Plugin-based simulation extension

---

## 📅 License

Licensed under the [MIT License](./LICENSE).

---

## 💬 Contact

**Author:** Manaswani (Mahi)  
**GitHub:** [@your-username](https://github.com/your-username)  
**Email:** your.email@example.com

_Quantum Bloom Studio is designed to be a modern gateway to quantum intuition._
