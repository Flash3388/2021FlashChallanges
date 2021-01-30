package bot;

import crypto.Scrambler;
import sheets.Leaderboard;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BotConstants {

    public static final String TOKEN;

    static {
        Scrambler scrambler = new Scrambler();
        TOKEN = scrambler.unscramble(loadStringFromFile("data/testbottoken.txt.scrambled"));
    }

    public static final String CHANNEL_NAME = "general";

    private static String loadStringFromFile(String filePath) {
        try {
            Path file = Paths.get(filePath);
            return new String(Files.readAllBytes(file), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new Error(e);
        }
    }
}
