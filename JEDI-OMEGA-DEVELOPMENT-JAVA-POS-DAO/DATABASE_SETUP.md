# FlipFit Database Setup Guide

## 📋 Prerequisites

1. **MySQL Server** - Make sure MySQL is installed and running
2. **MySQL Connector/J** - JDBC driver for MySQL

## 🚀 Quick Setup Steps

### Step 1: Download MySQL Connector

Download the MySQL Connector/J (JDBC Driver):
- Visit: https://dev.mysql.com/downloads/connector/j/
- Or use direct link: https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.0.33/mysql-connector-j-8.0.33.jar

Save it to your project lib folder or add to classpath.

### Step 2: Create Database and Tables

Open MySQL Command Line or MySQL Workbench and run:

```bash
# Login to MySQL
mysql -u root -p
# Enter password: Sql@22

# Run the schema file
source /path/to/JEDI-OMEGA-DEVELOPMENT-JAVA-POS-DAO/database/schema.sql
```

**OR** Copy and paste the contents of `schema.sql` into MySQL Workbench and execute.

### Step 3: Verify Database Configuration

Check the file: `src/com/flipfit/config/db.properties`

```properties
db.url=jdbc:mysql://localhost:3306/flipfit_db?useSSL=false&serverTimezone=UTC
db.username=root
db.password=Sql@22
db.driver=com.mysql.cj.jdbc.Driver
```

Update if your MySQL settings are different.

### Step 4: Compile the Project

```bash
cd JEDI-OMEGA-DEVELOPMENT-JAVA-POS-DAO

# Create bin directory
mkdir -p bin

# Compile with MySQL connector in classpath
javac -d bin -cp "lib/mysql-connector-j-8.0.33.jar:bin" src/com/flipfit/**/*.java
```

**For Windows:**
```cmd
javac -d bin -cp "lib\mysql-connector-j-8.0.33.jar;bin" src\com\flipfit\**\*.java
```

### Step 5: Test Database Connection

```bash
# macOS/Linux
java -cp "bin:lib/mysql-connector-j-8.0.33.jar" com.flipfit.config.TestDBConnection

# Windows
java -cp "bin;lib\mysql-connector-j-8.0.33.jar" com.flipfit.config.TestDBConnection
```

### Step 6: Run the Application

```bash
# macOS/Linux
java -cp "bin:lib/mysql-connector-j-8.0.33.jar" com.flipfit.client.FlipFitApplication

# Windows
java -cp "bin;lib\mysql-connector-j-8.0.33.jar" com.flipfit.client.FlipFitApplication
```

## 📊 Database Schema

The schema creates the following tables:

1. **users** - Base user information (email, password, role)
2. **gym_customers** - Customer-specific data
3. **gym_owners** - Gym owner information
4. **admins** - Administrator details
5. **gym_centers** - Gym locations and details
6. **trainers** - Trainer information
7. **slots** - Available time slots
8. **bookings** - Customer bookings
9. **waitlist** - Waitlist entries
10. **payments** - Payment records
11. **notifications** - User notifications

## 🔑 Sample Login Credentials

After running schema.sql, you can login with:

**Admin:**
- Email: `admin@flipfit.com`
- Password: `admin123`

**Gym Owner:**
- Email: `john@owner.com`
- Password: `owner123`

**Customer:**
- Email: `alice@customer.com`
- Password: `customer123`

## 🐛 Troubleshooting

### Problem: "Access denied for user 'root'@'localhost'"
**Solution:** Update password in `db.properties`

### Problem: "Unknown database 'flipfit_db'"
**Solution:** Run the `schema.sql` file to create the database

### Problem: "ClassNotFoundException: com.mysql.cj.jdbc.Driver"
**Solution:** Add MySQL Connector JAR to classpath

### Problem: "Connection refused"
**Solution:** Make sure MySQL server is running
```bash
# Check MySQL status
mysql --version
sudo systemctl status mysql  # Linux
brew services list           # macOS
```

## 📁 Project Structure

```
JEDI-OMEGA-DEVELOPMENT-JAVA-POS-DAO/
├── database/
│   └── schema.sql              # Database creation script
├── lib/
│   └── mysql-connector-j-8.0.33.jar  # JDBC driver
├── src/
│   └── com/flipfit/
│       ├── config/
│       │   ├── db.properties          # Database config
│       │   ├── DBConnectionManager.java
│       │   └── TestDBConnection.java
│       ├── bean/              # Entity classes
│       ├── business/          # Business logic
│       ├── dao/               # Data Access Objects
│       └── client/            # Application entry
└── bin/                       # Compiled classes
```

## ✅ Success Indicators

When everything is set up correctly, you should see:
- ✅ MySQL JDBC Driver loaded successfully!
- ✅ Database configuration loaded successfully!
- ✅ Database connection established!
- ✅ Database is ready with sample data!

## 📞 Support

If you encounter any issues:
1. Check MySQL is running
2. Verify credentials in db.properties
3. Ensure schema.sql has been executed
4. Confirm MySQL Connector JAR is in classpath
