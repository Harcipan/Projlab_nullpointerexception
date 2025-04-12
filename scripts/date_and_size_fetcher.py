import os
import time
import math
from datetime import datetime

def collect_java_files(folder):
    """
    Recursively collects .java files within the given folder.
    Returns a list of file paths.
    """
    java_files = []
    for root, _, files in os.walk(folder):
        for file_name in files:
            if file_name.lower().endswith('.java'):
                java_files.append(os.path.join(root, file_name))
    return java_files

def format_creation_date(file_path):
    """
    Fetches file creation time (ctime) and converts to 'YYYY.MM.DD' format.
    On some platforms, ctime might reflect last metadata change rather than creation.
    """
    creation_time = os.path.getctime(file_path)  # Returns a float (seconds since epoch)
    dt = datetime.fromtimestamp(creation_time)
    return dt.strftime('%Y.%m.%d')  # e.g. "2025.04.12"

def format_file_size(file_size_bytes):
    """
    Converts file size from bytes to a suitable unit (B, KB, MB, GB).
    Returns a string with one decimal place for KB or higher units.
    """
    if file_size_bytes < 1024:
        # Display in bytes
        return f"{file_size_bytes}B"
    elif file_size_bytes < 1024**2:
        # Display in KB
        size_kb = file_size_bytes / 1024
        return f"{size_kb:.1f}KB"
    elif file_size_bytes < 1024**3:
        # Display in MB
        size_mb = file_size_bytes / (1024**2)
        return f"{size_mb:.1f}MB"
    else:
        # Display in GB
        size_gb = file_size_bytes / (1024**3)
        return f"{size_gb:.1f}GB"

def main():
    input_folder = input("Enter the path to the folder with your .java files: ").strip()
    if not os.path.isdir(input_folder):
        print("Invalid input folder. Exiting.")
        return
    
    output_folder = input("Enter the path to the folder for the text file output: ").strip()
    # Create output folder if it doesn't exist
    if not os.path.exists(output_folder):
        try:
            os.makedirs(output_folder)
            print(f"Created output folder: {output_folder}")
        except Exception as e:
            print(f"Error creating output folder: {e}")
            return
    
    # Collect java files
    java_files = collect_java_files(input_folder)
    if not java_files:
        print("No Java files found in the provided folder.")
        return
    print(f"Found {len(java_files)} .java file(s).")
    
    # Prepare output text
    output_lines = []
    for file_path in java_files:
        file_name = os.path.basename(file_path)
        creation_date = format_creation_date(file_path)
        file_size_bytes = os.path.getsize(file_path)
        formatted_size = format_file_size(file_size_bytes)
        # Compose a line in the desired format: filename TAB creation_date TAB size
        # Each field is separated by a tab or you can use spacing as desired
        line = f"{file_name}\t{creation_date}\t{formatted_size}"
        output_lines.append(line)
    
    # Write results into file_info.txt
    output_file_path = os.path.join(output_folder, "file_info.txt")
    with open(output_file_path, 'w', encoding='utf-8') as f_out:
        for line in output_lines:
            f_out.write(line + "\n")
    
    print(f"Successfully wrote file info to: {output_file_path}")

if __name__ == "__main__":
    main()
