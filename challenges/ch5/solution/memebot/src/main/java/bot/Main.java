package bot;

import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws LoginException, URISyntaxException {
        // https://github.com/DV8FromTheWorld/JDA

        File memesFolder = new File(Main.class.getClassLoader()
                .getResource("memes")
                .toURI());

        JDABuilder.createDefault(BotConstants.TOKEN)
                .addEventListeners(new MessageListener(memesFolder))
                .build();
    }
}
