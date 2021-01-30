package bot;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import sheets.Leaderboard;

import java.util.Comparator;
import java.util.Map;

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
        MessageChannel channel = event.getChannel();
        String message = event.getMessage().getContentDisplay();

        if (message.startsWith("!leaderboard")) {
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
        }
    }
}
