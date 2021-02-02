package bot;

import crypto.Scrambler;
import io.Resources;

import java.io.IOException;

public class BotConstants {

    public static final String TOKEN;

    static {
        Scrambler scrambler = new Scrambler();
        try {
            TOKEN = scrambler.unscramble(Resources.getResourceContent("testbottoken.txt.scrambled"));
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    public static final String CHANNEL_NAME = "general";
}
