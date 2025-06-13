# 🌸 Quantum Bloom Studio

**Quantum Bloom Studio** is a modern, interactive application designed to visually and intuitively explore key concepts in quantum mechanics. Featuring a clean GUI with elegant, aesthetic design, it enables users to visualize wavefunctions, probability densities, energy levels, and time evolution for quantum systems such as the Particle in a Box, Quantum Harmonic Oscillator, and Superposition states.

> 🎨 Designed for clarity. Built for curiosity. Powered by JavaFX.

---

## 📌 Overview

Quantum Bloom Studio is an educational and visualization tool crafted with **JavaFX**. It combines mathematical rigor with visual elegance to support students, educators, and curious minds in understanding quantum mechanics through interactive simulations.

- **Created On**: June 13, 2025  
- **Tech Stack**: JavaFX 24, JDK 24  
- **License**: MIT  
- **Platform**: Windows (tested), cross-platform compatibility intended

---

## ✨ Features

### 🎛 Interactive Controls
- Real-time adjustable sliders for:
  - Quantum number `n` (1–10)
  - Box length or oscillator scale `L` (5.0–20.0)
  - Superposition weight between `n=1` and `n=2`

### 📊 Quantum System Visualizations
- 🧱 **Particle in a Box**
- 🌀 **Quantum Harmonic Oscillator**
- 🔗 **Two-state Superposition** (`n=1`, `n=2`)

### 🖼 Visual Output Panels
- Main canvas with:
  - Wavefunction (real & imaginary parts)
  - Probability density
  - Hover-based value readout
- Side panels with:
  - Clickable energy level bars
  - Probability density preview
  - Action log viewer

### 🧠 Educational Integration
- Built-in **Quantum Insights** panel with inline equations and explanations
- "About Project" section for context and background

### 🖌️ Aesthetic Design
- Pastel magenta-violet-teal palette:  
  `#FF80BF`, `#CC99FF`, `#66CCCC`, `#FFF5E6`
- Glassmorphism panels, smooth transitions, and rounded UI
- Font: *Verdana* (replaceable with *Comic Sans MS* or any preferred typeface)

---

## 🚀 Getting Started

### 🔧 Prerequisites

- [JDK 24](https://www.oracle.com/java/technologies/javase/jdk24-archive-downloads.html)
- [JavaFX SDK 24.0.1](https://gluonhq.com/products/javafx/)
- Code Editor (e.g., **VS Code**, **IntelliJ IDEA**)

---

### 📂 Project Setup

1. **Clone or Download the Project**:
   ```bash
   git clone https://github.com/yourusername/QuantumBloomStudio.git


2. **Verify Paths in Scripts**:
Update the following two batch files:

compile.bat

run.bat

Replace placeholders with your local paths:
 ```bash
set JAVA_HOME="C:\Program Files\Java\jdk-24"
set FX="C:\Users\<YourUser>\javafx-sdk-24.0.1\lib"
```

3.**🛠️ How to Compile and Run**
Compile  (click on compile. bat file)
compile.bat
you should see: ✅ Compilation successful.

Run (To run, click on run.bat)
run.bat
The GUI will launch automatically. If not, double-check the path and JavaFX setup.

**📚 Usage Guide**
1. Select a Quantum System
Use the dropdown menu to choose from:

Particle in a Box

Harmonic Oscillator

Superposition (n=1 and n=2)

2. Adjust Parameters
Quantum Number (n): Adjust via slider (1–10)

Length/Scale (L): Set via slider (5–20)

Superposition: Adjust weight of n=1 (rest auto-balances)

3. Interact with Graphs
Hover over wavefunction to read values

Click energy bars to set current n

Toggle gridlines for visual clarity

4. Animate
Press Play Time Evolution to animate

Use Reset to revert all parameters

5. Learn
Open "Quantum Insights" to explore mathematical explanations

View "About Project" for background

**🧪 Future Enhancements**
Planned upgrades:

Multi-state superposition builder

Export to GIF (wavefunction animation)

2D potential well visualizer

Dark mode toggle (with neon accent support)

LaTeX-style equation rendering (via MathJax or similar)

👩‍💻 Author
Manaswani

**🙌 Acknowledgments**
Inspired by the elegance of quantum mechanics and the need for playful, interactive STEM tools

Thanks to open-source physics communities for years of inspiration

