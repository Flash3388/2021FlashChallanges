package bot;

import net.dv8tion.jda.api.JDABuilder;
import sheets.Leaderboard;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws LoginException {
        // https://github.com/DV8FromTheWorld/JDA
        JDABuilder.createDefault(BotConstants.TOKEN)
                .addEventListeners(new MessageListener())
                .build();
    }
}
