package bot;

import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String[] args) throws LoginException {
        // https://github.com/DV8FromTheWorld/JDA
        JDABuilder.createDefault(BotConstants.TOKEN)
                .addEventListeners(new MessageListener())
                .build();
    }
}
