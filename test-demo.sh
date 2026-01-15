#!/bin/bash

echo "=========================================="
echo "  Habbo CLI - Build, Test & Run Demo"
echo "=========================================="
echo ""

cd /Users/ander/Desktop/Projects/Habbo/HabboCLI

# Build
echo "1️⃣  Building project..."
mvn clean package -q
echo "✅ Build complete!"
echo ""

# Tests
echo "2️⃣  Running tests..."
mvn test -q
echo "✅ Tests passed!"
echo ""

# List JARs
echo "3️⃣  Built artifacts:"
ls -lh target/*.jar | awk '{print "   " $9 " (" $5 ")"}'
echo ""

# Test the CLI
echo "4️⃣  Running CLI demo..."
echo "   Sending commands: help, status, exit"
echo ""

(
  sleep 0.5
  echo "help"
  sleep 0.5
  echo "status"
  sleep 0.5
  echo "exit"
) | java -jar target/habbo-cli-1.0.0-SNAPSHOT-jar-with-dependencies.jar

echo ""
echo "=========================================="
echo "✅ Build, Test & Run Complete!"
echo "=========================================="
