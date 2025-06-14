@echo off
echo 💡 Compiling QuantumBloomStudio.java...
set JAVA_HOME="C:\Program Files\Java\jdk-24"
set FX="C:\Users\katik\OneDrive\Desktop\javafx-sdk-24.0.1\lib"
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
