package io;

public class InfoLogger implements Logger {
    @Override
    public void log(String message) {
        System.out.println(message);
    }
}
