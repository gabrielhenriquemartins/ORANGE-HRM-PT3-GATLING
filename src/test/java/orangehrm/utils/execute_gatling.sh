#!/bin/bash

set -e

echo "Running pre-execution script..."
./src/test/java/orangehrm/utils/pre_execution.sh

mvn gatling:test -Dgatling.simulationClass=orangehrm.projects.regression.PerformanceRunner

echo "Running post-execution script..."
./src/test/java/orangehrm/utils/pos_execution.sh