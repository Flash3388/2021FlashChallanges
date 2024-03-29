package stingerlib.logging;

public interface Logger {

    void info(String message, Object... args);
    void error(String message, Object... args);
    void error(String message, Throwable t);
}
