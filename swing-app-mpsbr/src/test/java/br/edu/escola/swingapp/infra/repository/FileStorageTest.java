package br.edu.escola.swingapp.infra.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileStorageTest {

    @TempDir
    Path tempDir;

    @Test
    void shouldWriteAndReadFile() {
        Path filePath = tempDir.resolve("test-storage.csv");
        FileStorage storage = new FileStorage(filePath.toString());
        // We write with system separator to be consistent with what we expect to read
        // back
        // (though simple \n works for write, read back will use system separator)
        String content = "line1" + System.lineSeparator() + "line2";

        storage.writeAll(content);
        String readContent = storage.readAll();

        assertNotNull(readContent);
        // FileStorage.readAll reads line by line and appends System.lineSeparator()
        // So it will end with a separator.
        String expected = content + System.lineSeparator();

        assertEquals(expected, readContent);
    }

    @Test
    void shouldReturnEmptyStringIfFileDoesNotExist() {
        Path filePath = tempDir.resolve("non-existent.csv");
        FileStorage storage = new FileStorage(filePath.toString());

        String content = storage.readAll();
        assertEquals("", content);
    }

    @Test
    void shouldCreateDirectoryIfItDoesNotExist() {
        Path subDir = tempDir.resolve("subdir");
        Path filePath = subDir.resolve("test.csv");
        FileStorage storage = new FileStorage(filePath.toString());

        storage.writeAll("test");

        assertTrue(Files.exists(subDir));
        assertTrue(Files.exists(filePath));
        assertEquals("test" + System.lineSeparator(), storage.readAll());
    }
}
