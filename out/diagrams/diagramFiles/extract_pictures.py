import os
import shutil

# Get the current working directory
current_dir = os.getcwd()

# Iterate over all items in the current directory
for folder_name in os.listdir(current_dir):
    folder_path = os.path.join(current_dir, folder_name)
    
    # Check if it's a directory
    if os.path.isdir(folder_path):
        # Iterate over all files in the subdirectory
        for item in os.listdir(folder_path):
            item_path = os.path.join(folder_path, item)
            
            # Check if it's a file
            if os.path.isfile(item_path):
                # Move the file to the parent folder
                shutil.move(item_path, os.path.join(current_dir, item))

        # Optionally, remove the now-empty folder
        os.rmdir(folder_path)

print("Files have been moved to the root folder.")
