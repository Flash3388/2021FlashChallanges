package io;

import crypto.Scrambler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class Resources {

    public static String getResourceContent(String path) throws IOException {
        try(InputStream inputStream = Scrambler.class.getClassLoader().getResourceAsStream(path);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return bufferedReader.lines()
                    .collect(Collectors.joining("\n"));
        }
    }
}
