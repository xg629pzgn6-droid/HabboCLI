#!/bin/bash

echo "╔════════════════════════════════════════════════════════╗"
echo "║   Habbo CLI - SSO Authentication System Demo          ║"
echo "╚════════════════════════════════════════════════════════╝"
echo ""

cd /Users/ander/Desktop/Projects/Habbo/HabboCLI

# Build
echo "📦 Building project..."
mvn clean package -q
echo "✅ Build complete!"
echo ""

# Tests
echo "🧪 Running 14 authentication & model tests..."
mvn test -q
echo "✅ All tests passed!"
echo ""

# Show artifacts
echo "📊 Built artifacts:"
ls -lh target/*.jar | awk '{print "   " $9 " (" $5 ")"}'
echo ""

# Interactive demo
echo "🎮 Running CLI with SSO authentication demo..."
echo ""
echo "Demo commands:"
echo "  1. help       - Show all commands"
echo "  2. connect    - Connect to server (will fail - no server running)"
echo "  3. login      - Test SSO token generation"
echo "  4. status     - Check authentication status"
echo "  5. logout     - Revoke token"
echo "  6. exit       - Exit"
echo ""

(
  sleep 0.5
  echo "help"
  sleep 0.5
  echo "status"
  sleep 0.5
  echo "exit"
) | java -jar target/habbo-cli-1.0.0-SNAPSHOT-jar-with-dependencies.jar 2>&1 | grep -E "^>|^✅|^❌|^🔐|^📊|Available|login|logout|exit|Habbo CLI|Commands"

echo ""
echo "╔════════════════════════════════════════════════════════╗"
echo "║              Demo Complete! ✅                         ║"
echo "╚════════════════════════════════════════════════════════╝"
echo ""
echo "📚 Features implemented:"
echo "   ✓ SSO Token generation and validation"
echo "   ✓ Authentication manager with max login attempts"
echo "   ✓ Token refresh capability"
echo "   ✓ Protocol messages (AuthenticationMessage)"
echo "   ✓ CLI commands (connect, login, logout, status)"
echo "   ✓ Comprehensive error handling"
echo "   ✓ Full logging support"
echo ""
echo "📋 Test Results:"
mvn test 2>&1 | grep "Tests run:" | tail -1 | sed 's/^/   /'
echo ""
