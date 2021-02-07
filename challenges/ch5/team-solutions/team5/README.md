```java
 private void handleMessage(MessageReceivedEvent event) throws Exception {
        // TODO: IMPLEMENT THIS
        //  check if a user requested to print the leaderboard
        //  if yes: sent the leaderboard to the user


        if(event.getMessage().getContentDisplay().equals("!leaderboard")) {
            Leaderboard leaderboard = new Leaderboard();
            Map<String, Double> scores = leaderboard.getLeaderboard();
            MessageChannel channel = event.getChannel();
            if(channel == null) {
                throw new IOException("No data");
            }
            String msg = "";
            List<Double> lst = new ArrayList<Double>();
            for (Map.Entry<String, Double> i : scores.entrySet()) {
                lst.add(i.getValue());
            }
            Collections.sort(lst);
            Collections.reverse(lst);
            msg += "these are the scores:\n";
            for(Double i : lst) {
                for (Map.Entry<String, Double> k : scores.entrySet()) {
                    if(k.getValue() == i) {
                        msg += k.getKey() + ": " + k.getValue() + "\n";
                    }
                }
            }
            channel.sendMessage(msg).queue();
        }
    }
```
