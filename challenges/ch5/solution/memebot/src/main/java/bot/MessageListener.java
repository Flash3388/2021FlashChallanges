package bot;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.util.Random;

public class MessageListener extends ListenerAdapter {

    //private static final String CHANNEL_NAME = "דיבור-קבוצה-1";
    private static final String CHANNEL_NAME = "general⭐";

    private final File mMemesFolder;
    private final Random mRandom;

    public MessageListener(File memesFolder) {
        mMemesFolder = memesFolder;
        mRandom = new Random();
    }

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

        if (message.startsWith("!meme")) {
            File memeFile = findRandomMeme();
            if (memeFile != null) {
                channel.sendFile(memeFile)
                    .queue();
            }
        } else if (message.startsWith("!echo")) {
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

    private File findRandomMeme() {
        File[] memeFiles = mMemesFolder.listFiles();
        if (memeFiles == null) {
            return null;
        }

        int random = mRandom.nextInt(memeFiles.length);
        return memeFiles[random];
    }
}
