import os

def collect_java_files(input_folder):
    """Recursively collects all .java file paths from the given folder."""
    java_files = []
    for root, _, files in os.walk(input_folder):
        for file in files:
            if file.lower().endswith('.java'):
                java_files.append(os.path.join(root, file))
    return java_files

def count_words(text):
    """Counts the words in a given text string."""
    return len(text.split())

def write_batch(output_folder, batch_num, file_entries):
    """Writes the current batch of file entries to an output file.
    
    The naming convention is as follows:
      - First batch: output.txt
      - Subsequent batches: output_02.txt, output_03.txt, ...
    """
    # Determine file name based on batch number.
    if batch_num == 1:
        output_filename = "output.txt"
    else:
        output_filename = f"output_{batch_num:02d}.txt"
    output_path = os.path.join(output_folder, output_filename)
    
    with open(output_path, 'w', encoding='utf-8') as f_out:
        # Write each file's name and content in the batch.
        for file_name, content in file_entries:
            f_out.write(f"===== {file_name} =====\n")
            f_out.write(content)
            f_out.write("\n\n")
    print(f"Batch {batch_num} written to {output_path}")

def main():
    # Step 1: Ask user for input folder and collect Java file paths.
    input_folder = input("Enter the path to the input folder containing Java files: ").strip()
    if not os.path.isdir(input_folder):
        print("Invalid input folder. Exiting.")
        return

    java_files = collect_java_files(input_folder)
    if not java_files:
        print("No Java files found in the provided folder.")
        return
    print(f"Found {len(java_files)} Java file(s).")

    # Step 2: Compute word counts for every file while reading their content.
    file_contents = []
    skipped_files = 0
    for file_path in java_files:
        try:
            with open(file_path, 'r', encoding='utf-8') as f:
                content = f.read()
            words = count_words(content)
            # If an individual file exceeds the 1000 word limit, skip it.
            if words > 1000:
                print(f"Skipping {file_path} (contains {words} words, which exceeds the 1000-word limit).")
                skipped_files += 1
                continue
            file_contents.append((os.path.basename(file_path), content))
        except Exception as e:
            print(f"Error reading {file_path}: {e}")
    
    print(f"Total files to process: {len(file_contents)} (skipped {skipped_files} file(s) due to length).")

    # Step 3: Ask user for output folder.
    output_folder = input("Enter the path to the output folder for the text files: ").strip()
    if not os.path.isdir(output_folder):
        try:
            os.makedirs(output_folder)
            print(f"Created output folder: {output_folder}")
        except Exception as e:
            print(f"Error creating output folder: {e}")
            return

    # Step 4: Iterate over the files and write batches that do not exceed 1000 words.
    batch = []
    batch_word_count = 0
    batch_num = 1

    for file_name, content in file_contents:
        file_word_count = count_words(content) + count_words(file_name)  # including the file name header
        # If adding this file would exceed 1000 words in this batch,
        # write out the current batch and start a new one.
        if batch_word_count + file_word_count > 1000 and batch:
            write_batch(output_folder, batch_num, batch)
            batch_num += 1
            batch = []
            batch_word_count = 0
        
        # If the file itself is larger than 1000 words (should have been skipped earlier),
        # the following block will ensure we do not add it.
        if file_word_count > 1000:
            print(f"Skipping {file_name} as its content is too large to fit in a batch even alone.")
            continue

        batch.append((file_name, content))
        batch_word_count += file_word_count

    # Write any remaining files in the current batch.
    if batch:
        write_batch(output_folder, batch_num, batch)

if __name__ == "__main__":
    main()
