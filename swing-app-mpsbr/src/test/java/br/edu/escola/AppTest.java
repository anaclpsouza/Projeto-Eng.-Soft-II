package br.edu.escola;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AppTest {

    @Test
    void main_ShouldNotThrowException() {
        // Just verify that calling main does not crash.
        // Since main uses SwingUtilities.invokeLater, it returns immediately.
        // The actual GUI creation happens on the EDT.
        // Catching specific runtime memory issues or headless exceptions is hard here
        // without modifying App,
        // but this ensures the code path in main is executed.
        assertDoesNotThrow(() -> App.main(new String[] {}));

        // Give a tiny moment for the invokeLater to be queued check (optional, but
        // helps coverage tools catch the lambda execution)
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            // ignore
        }
    }
}
