#!/bin/bash

# Test Database Connection

echo "Testing database connection..."

java -cp "bin:lib/mysql-connector-j-8.0.33.jar" com.flipfit.config.TestDBConnection
