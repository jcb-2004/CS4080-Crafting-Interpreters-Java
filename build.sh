#!/usr/bin/env bash
set -euo pipefail

echo "🧹 Cleaning old build artifacts..."

CLASS_FILES=$(find . -name "*.class" || true)
if [ -n "$CLASS_FILES" ]; then
  while IFS= read -r file; do
    echo "  deleting $file"
    rm "$file"
  done <<< "$CLASS_FILES"
else
  echo "  (no .class files found)"
fi

echo
echo "🧨 Removing generated AST files..."
if [ -f src/com/craftinginterpreters/lox/Expr.java ]; then
  echo "  deleting src/com/craftinginterpreters/lox/Expr.java"
  rm src/com/craftinginterpreters/lox/Expr.java
else
  echo "  (no generated Expr.java found)"
fi

echo
echo "🔧 Compiling AST generator..."
javac tool/com/craftinginterpreters/tool/GenerateAst.java
echo "  compiled GenerateAst"

echo
echo "🏃  Running AST generator..."
java -cp tool com.craftinginterpreters.tool.GenerateAst src/com/craftinginterpreters/lox

echo
echo "🔍 Collecting all Java source files..."
JAVA_FILES=$(find src tool -name "*.java")

echo "📦 Compiling the following files:"
while IFS= read -r file; do
  echo "  compiling $file"
done <<< "$JAVA_FILES"

echo
echo "🔨 Running javac..."
javac $JAVA_FILES

echo
echo "✅ Build complete."
