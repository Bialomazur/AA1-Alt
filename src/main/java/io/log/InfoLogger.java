package io.log;

import java.io.PrintStream;

public class InfoLogger extends Logger {

    public InfoLogger(final PrintStream outputTarget) {
        super(outputTarget);
    }

    @Override
    public void logInfo(final String message) {
         this.getOutputTarget().println(message);
    }

}
