package bot;

import bot.sheets.Leaderboard;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;

public class MessageListener extends ListenerAdapter {

    private static final String CHANNEL_NAME = "general⭐";

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

        if (message.startsWith("!leaderboard")) {
            try {
                Map<String, Double> scores = new Leaderboard().getLeaderboard();

                StringBuilder builder = new StringBuilder();
                builder.append("|\tTeam\t|\tScore\t|\n");
                scores.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .forEachOrdered((entry)-> {
                            builder.append("|\t")
                                    .append(entry.getKey())
                                    .append("\t|\t")
                                    .append(entry.getValue())
                                    .append("\t|\n");
                        });
                channel.sendMessage(builder.toString())
                    .queue();
            } catch (GeneralSecurityException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isOurChannel(MessageChannel channel) {
        return channel.getName().equalsIgnoreCase(CHANNEL_NAME);
    }
}
