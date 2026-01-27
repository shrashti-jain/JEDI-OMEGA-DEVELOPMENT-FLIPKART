#!/bin/bash

# Download MySQL Connector/J

echo "Downloading MySQL Connector/J..."

mkdir -p lib
cd lib

# Download MySQL Connector
curl -L -O https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.0.33/mysql-connector-j-8.0.33.jar

if [ $? -eq 0 ]; then
    echo "✅ MySQL Connector downloaded successfully!"
    ls -lh mysql-connector-j-8.0.33.jar
else
    echo "❌ Failed to download MySQL Connector"
    echo "Please download manually from:"
    echo "https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.0.33/mysql-connector-j-8.0.33.jar"
    exit 1
fi
