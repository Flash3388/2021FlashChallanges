package io;

import crypto.Scrambler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class Resources {

    public static String getResourceContent(Class<?> caller, String path) throws IOException {
        return getResourceContent(caller.getClassLoader(), path);
    }

    public static String getResourceContent(ClassLoader classLoader, String path) throws IOException {
        try(InputStream inputStream = classLoader.getResourceAsStream(path);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return bufferedReader.lines()
                    .collect(Collectors.joining("\n"));
        }
    }
}
