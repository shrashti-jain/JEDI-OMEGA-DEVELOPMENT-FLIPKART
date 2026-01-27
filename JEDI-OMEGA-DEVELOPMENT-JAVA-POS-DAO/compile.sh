#!/bin/bash

# Compile FlipFit Project

echo "Compiling FlipFit project..."

# Create bin directory
mkdir -p bin

# Compile all Java files
javac -d bin -cp "lib/mysql-connector-j-8.0.33.jar:bin" \
    src/com/flipfit/bean/*.java \
    src/com/flipfit/dao/*.java \
    src/com/flipfit/config/*.java \
    src/com/flipfit/business/*.java \
    src/com/flipfit/client/*.java

if [ $? -eq 0 ]; then
    echo "✅ Compilation successful!"
    echo ""
    echo "Compiled classes:"
    find bin -name "*.class" | wc -l | xargs echo "  Total classes:"
else
    echo "❌ Compilation failed"
    exit 1
fi
