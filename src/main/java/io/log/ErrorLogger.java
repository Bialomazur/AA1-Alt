package io.log;

import java.io.PrintStream;

public class ErrorLogger extends Logger{
    private static final String MESSAGE_TEMPLATE = "Error: %s";

    public ErrorLogger(final PrintStream printStream) {
        super(printStream);
    }

    @Override
    public void logInfo(final String message) {
        this.getOutputTarget().println(String.format(MESSAGE_TEMPLATE, message));
    }
}
