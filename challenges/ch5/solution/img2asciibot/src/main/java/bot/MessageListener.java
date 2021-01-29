package bot;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

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

        if (message.startsWith("!img2ascii")) {
            for (Message.Attachment attachment : event.getMessage().getAttachments()) {
                if (attachment.isImage()) {
                    attachment.downloadToFile()
                            .whenComplete((file, ex)-> {
                                try {
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    try (PrintWriter printStream = new PrintWriter(baos)) {
                                        printStream.println("```");
                                        new Img2Ascii().convertToAscii(file, printStream);
                                        printStream.println("```");
                                    }
                                    channel.sendFile(baos.toByteArray(), file.getName() + ".txt")
                                            .queue();
                                } catch (Throwable t){
                                    ex.printStackTrace();
                                }
                            });
                }
            }
        }
    }

    private boolean isOurChannel(MessageChannel channel) {
        return channel.getName().equalsIgnoreCase(CHANNEL_NAME);
    }
}
