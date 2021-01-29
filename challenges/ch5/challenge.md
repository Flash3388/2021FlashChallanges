<div dir="rtl">

תיאור!!!!!

ישמו בוט ל-discord אשר מדפיס רשימת מיקום של כל הקבוצות בתחרות כאשר משתמשים כותבים `!leaderboard` בערוץ של הקבוצה שלכם.

השתמשו [template](source/template) כדי להתחיל את העבודה. כדי לפתוח את התבנית, פתחו את IntelliJ IDEA ובלשונית File->Open פתחו את התיקייה של התבנית.
בקובץ `BotConstants` שנו את הקבוע `TOKEN` לערך ספציפי אותו תבקשו מתום כאשר אתם מתחילים את האתגר.
שנו בקובץ `BotConstants` שנו את הקבוע `CHANNEL_NAME` לערוץ של הקבוצה שלכם בו תרצו להקשיב להודעות.

ממשו את המחלקה `MessageListener`. הפעולה `onMessageReceived` נקראת כאשר הודעה חדשה נשלחת בשרת. 
ממשו את הפעולה `handleMessage` כדי לבדוק האם ביקשו להדפיס את הלוח ניקוד, והדפיסו אם כן.

העזרו בפעולות הבאות:
</div>

```
MessageChannel channel = event.getChannel(); // the channel in which the message was sent

String message = event.getMessage().getContentDisplay(); // the message sent

channel.sentMessage("MESSAGE").queue(); // send a message in a channel

String channelName = channel.getName(); // get the channel name
```

<div dir="rtl">

העזרו במחלקה [`Leaderboard`](source/Leaderboard.java) בכדי להשיג מידע עדכני על ניקוד כל הקבוצות:

</div>

```
Leaderboard leaderboard = new Leaderboard();
Map<String, Double> scores = leaderboard.getLeaderboard();
```

<div dir="rtl">

כאשר `scores` היא מפה של שם קבוצה לניקוד של הקבוצה.

כדי להריץ את ה-bot, הריצו ב-terminal של IntelliJ את הפקודה `gradlew.bat run`.

</div>
