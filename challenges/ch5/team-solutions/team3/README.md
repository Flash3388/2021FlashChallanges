```java
private void handleMessage(MessageReceivedEvent event) throws Exception {
        // TODO: IMPLEMENT THIS
        //  check if a user requested to print the leaderboard
        //  if yes: sent the leaderboard to the user

        Leaderboard leaderboard = new Leaderboard();
        Map<String, Double> scores = leaderboard.getLeaderboard();

        if (event.getMessage().getAuthor().isBot())
            return;

        if (event.getMessage().getContentRaw().equals("!leaderboard")) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Leaderboard", null);
            eb.setColor(Color.red);
            eb.setDescription("Points");

            List<Map.Entry<String, Double>> scoresSorted = new ArrayList<>(scores.entrySet());
            scoresSorted.sort(Map.Entry.comparingByValue());
            Collections.reverse(scoresSorted);

            for (Map.Entry<String, Double> pair : scoresSorted) {
                double score = pair.getValue().doubleValue();
                eb.addField(String.format("%s", pair.getKey()), String.format("Score: %f", score), false);
            }

            event.getChannel().sendMessage(eb.build()).queue();
            return;
        }
    }
```
