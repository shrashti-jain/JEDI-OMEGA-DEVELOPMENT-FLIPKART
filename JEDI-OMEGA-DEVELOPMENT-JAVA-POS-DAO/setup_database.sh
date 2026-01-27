#!/bin/bash

# FlipFit MySQL Database Setup Script
# This script will help you set up the database

echo "================================================="
echo "  FlipFit Database Setup"
echo "================================================="
echo ""

# Step 1: Check if MySQL is installed
echo "Step 1: Checking MySQL installation..."
if command -v mysql &> /dev/null; then
    echo "✅ MySQL is installed"
    mysql --version
else
    echo "❌ MySQL is not installed"
    echo "Please install MySQL first: brew install mysql (macOS)"
    exit 1
fi

echo ""

# Step 2: Check if MySQL is running
echo "Step 2: Checking if MySQL is running..."
if mysql -u root -pSql@22 -e "SELECT 1" &> /dev/null; then
    echo "✅ MySQL is running and accessible"
else
    echo "❌ Cannot connect to MySQL"
    echo "Please check:"
    echo "  1. MySQL server is running"
    echo "  2. Password is correct (Sql@22)"
    echo "  3. User 'root' has access"
    exit 1
fi

echo ""

# Step 3: Create database and run schema
echo "Step 3: Creating database and tables..."
echo "Running schema.sql..."

mysql -u root -pSql@22 < database/schema.sql

if [ $? -eq 0 ]; then
    echo "✅ Database created successfully!"
else
    echo "❌ Failed to create database"
    exit 1
fi

echo ""

# Step 4: Verify tables
echo "Step 4: Verifying tables..."
mysql -u root -pSql@22 -e "USE flipfit_db; SHOW TABLES;"

echo ""
echo "================================================="
echo "  Setup Complete!"
echo "================================================="
echo ""
echo "Next steps:"
echo "  1. Download MySQL Connector: ./download_connector.sh"
echo "  2. Compile project: ./compile.sh"
echo "  3. Test connection: ./test_connection.sh"
echo "  4. Run application: ./run.sh"
echo ""
