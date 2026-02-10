#!/usr/bin/env bash
set -euo pipefail

branch=$(git branch --show-current)

if [[ -z "$branch" ]]; then
  echo "❌ Could not determine current branch."
  exit 1
fi

echo "🌿 Current branch: $branch"
echo

echo "📂 Git status (before staging):"
git status
echo

read -p "Stage all changes with 'git add .'? (y/n) " confirm
if [[ "$confirm" != "y" ]]; then
  echo "❌ Aborted."
  exit 1
fi

git add .

echo
echo "📦 Staged changes:"
git status
echo

read -p "Commit message: " message
if [[ -z "$message" ]]; then
  echo "❌ Commit message cannot be empty."
  exit 1
fi

git commit -m "$message"

echo
echo "🚀 Pushing branch '$branch'..."
git push -u origin "$branch"

echo
echo "✅ Done."
