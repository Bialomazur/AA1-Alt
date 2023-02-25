package io.log;

import java.io.PrintStream;

public abstract class Logger {

    private final PrintStream outputTarget;

    protected Logger(final PrintStream outputTarget) {
        this.outputTarget = outputTarget;
    }

    public abstract void logInfo(String message);

    protected PrintStream getOutputTarget() {
        return this.outputTarget;
    }

}
