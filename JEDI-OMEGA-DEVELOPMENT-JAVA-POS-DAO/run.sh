#!/bin/bash

# Run FlipFit Application

echo "Starting FlipFit Application..."

java -cp "bin:lib/mysql-connector-j-8.0.33.jar" com.flipfit.client.FlipFitApplication
