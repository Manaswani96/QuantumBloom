@echo off
echo 🌸 Launching Quantum Bloom Studio...
set JAVA_HOME="C:\Program Files\Java\jdk-24"
set FX="C:\Users\katik\OneDrive\Desktop\javafx-sdk-24.0.1\lib"
%JAVA_HOME%\bin\java.exe ^
--module-path %FX% ^
--add-modules javafx.controls ^
QuantumBloomStudio
pause