package bot;

import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String[] args) throws LoginException {
        JDABuilder.createDefault(BotConstants.TOKEN)
                .addEventListeners(new MessageListener())
                .build();
    }
}
