package br.edu.escola.swingapp.infra.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Responsible for reading and writing text file content.
 *
 * Single Responsibility: abstracts file I/O operations for repositories.
 */
public class FileStorage {

    private final String filePath;

    /**
     * @param filePath file path (e.g. "students.csv").
     */
    public FileStorage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Reads the whole file content as a single String.
     *
     * @return file content (empty String if file does not exist or is empty).
     */
    public String readAll() {
        File file = new File(filePath);
        if (!file.exists()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                sb.append(line).append(System.lineSeparator());
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + filePath, e);
        }

        return sb.toString();
    }

    /**
     * Overwrites file content with the given String.
     *
     * @param content content to be written.
     */
    public void writeAll(String content) {
        File file = new File(filePath);

        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            writer.write(content != null ? content : "");
        } catch (IOException e) {
            throw new RuntimeException("Error writing file: " + filePath, e);
        }
    }

    public String getFilePath() {
        return filePath;
    }
}