package io;

public class MainLogger implements Logger {
    private static final String ERROR_MESSAGE = "Error: %s";
    @Override
    public void log(final String message) {
        System.out.println(message);
    }

    public void logError(final String message) {
        System.err.println(String.format(ERROR_MESSAGE, message));
    }
}
