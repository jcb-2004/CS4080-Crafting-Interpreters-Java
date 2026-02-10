#!/usr/bin/env bash
set -euo pipefail

echo "🧹 Cleaning old .class files..."

CLASS_FILES=$(find . -name "*.class")

if [ -z "$CLASS_FILES" ]; then
  echo "  (no .class files found)"
else
  while IFS= read -r file; do
    echo "  deleting $file"
    rm "$file"
  done <<< "$CLASS_FILES"
fi

echo
echo "🔍 Collecting Java source files..."

JAVA_FILES=$(find src tool -name "*.java")

if [ -z "$JAVA_FILES" ]; then
  echo "❌ No .java files found. Build aborted."
  exit 1
fi

echo "📦 Compiling the following files:"
while IFS= read -r file; do
  echo "  compiling $file"
done <<< "$JAVA_FILES"

echo
echo "🔨 Running javac..."
javac $JAVA_FILES

echo
echo "✅ Build complete. All sources compiled successfully."
