package bot;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // message received
        MessageChannel channel = event.getChannel();
        String message = event.getMessage().getContentDisplay();

        System.out.printf("[%s] %s: %s\n",
                channel.getName(), event.getAuthor().getName(),
                message);

        if (!isOurChannel(channel)) {
            return;
        }

        try {
             handleMessage(event);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private boolean isOurChannel(MessageChannel channel) {
        return channel.getName().equalsIgnoreCase(BotConstants.CHANNEL_NAME);
    }

    private void handleMessage(MessageReceivedEvent event) throws Exception {
        // TODO: IMPLEMENT THIS
        //  check if a user requested to print the leaderboard
        //  if yes: sent the leaderboard to the user
    }
}
