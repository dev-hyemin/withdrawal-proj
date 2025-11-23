#!/bin/bash
set -e
echo "Running post-create setup..."
./gradlew dependencies --no-daemon || true