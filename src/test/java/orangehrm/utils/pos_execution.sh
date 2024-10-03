#!/bin/bash

current_date=$(date +"%Y-%m-%d")
folder_path="/gatling/src/test/java/orangehrm/results/$current_date"
target_path="/gatling/target/gatling"

# Check if the folder exists
if [ -d "$folder_path" ]; then
    # If the folder exists, delete it
    echo "Deleting existing folder: $folder_path"
    rm -rf "$folder_path"
fi

# Create a new folder
echo "Creating new folder: $folder_path"
mkdir -p "$folder_path"

echo "Move the generated from: $target_path to the report folder: $folder_path"
mv $target_path/performance*/* "$folder_path"