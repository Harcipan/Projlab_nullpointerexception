#!/bin/bash

echo "Press any button to compile 'n' run..."
read -n 1 -s

clear

# Navigate up one level to the project root directory
cd ..

# Now you are in the project-root/ directory
echo "Running from: $(pwd)" # Optional: show current directory

# --- Compilation Step ---
# Assuming sources.txt is created temporarily for javac
if [ -f sources.txt ]; then
  rm sources.txt
fi

# Find Java files from the root (now 'src')
# Note: Your find command was looking in ../src from compiler.
# Now you are in the root, so you look in ./src or just src.
# Let's assume sources are under src/
find src -name "*.java" -print0 | while IFS= read -r -d $'\0' file; do
  # The file paths will now be relative to the root, e.g., src/graphics/...
  echo "$file" >> sources.txt
done

# Compile from the root
# -d target/classes: Output classes to project-root/target/classes
# -sourcepath src: The source root is project-root/src
javac -d target/classes -sourcepath src @sources.txt

# Optional: Clean up sources.txt
# rm sources.txt # You might want to keep it for debugging or remove it

# --- Execution Step ---
# Run the Java program from the root
# -cp target/classes: Looks for classes in project-root/target/classes
java -cp target/classes Main

echo "Press any key to continue..."
read -n 1 -s
