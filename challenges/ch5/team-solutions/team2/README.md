```java
    private void handleMessage(MessageReceivedEvent event) throws Exception {
        // TODO: IMPLEMENT THIS
        //  check if a user requested to print the leaderboard
        //  if yes: sent the leaderboard to the user

        if (event.getMessage().getContentDisplay().equals("!leaderboard")) {
            Leaderboard leaderboard = new Leaderboard();
            Map<String, Double> scores = leaderboard.getLeaderboard();

            List<Map.Entry<String, Double>> sortedScores = new ArrayList<>(scores.entrySet());
            sortedScores.sort(Map.Entry.comparingByValue());
            Collections.reverse(sortedScores);

            String message = "";

            for (Map.Entry<String, Double> entry : sortedScores) {
                message += String.format("%s: %d\n", entry.getKey(), Math.round(entry.getValue() / 2));
            }

            event.getChannel().sendMessage(message).queue();
        }
    }
```
