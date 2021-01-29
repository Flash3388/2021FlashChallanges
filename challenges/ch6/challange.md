<div dir="rtl">

תיאור!!!!!

בעזרת מחקר ארוך, זוהה כי אם הקובץ הודבק, חלק מהתוכנה שמוכנסת לקובץ מכיל אוסף בתים קבוע:

</div>

```
70 108 97 115 104 51 51 56 56
```

<div dir="rtl">

ישמו קוד שמסוגל לזהות האם קובץ הוא מודבק על ידי הוירוס או לא, וזהו אילו קבצים מהקבצים החשודים ([here](binaries)) אכן מודבקים.

כדי לעבוד עם קבצים, נשתמש במחלקות java קצת ישנות.
הקוד הבא פותח קובץ וקורא ממנו ביית אחד:

</div>

```
File file = new File("file.txt");
try (InputStream inputStream = new FileInputStream()) {
    int singleByte = inputStream.read();
    // singleByte is either a byte from the file, or -1 if the file has not more bytes to read.
    System.out.println(singleByte);
}
```

<div dir="rtl">
העזרו גם בקישור הבא: https://www.javatpoint.com/java-fileinputstream-class

בנוסף, כדי לעבור על כל הקבצים בתיקייה, העזרו בדוגמא הבאה:
</div>

```
File folder = new File("someFolder");
File[] files = folder.listFiles();
```

<div dir="rtl">

השתמשו בקבוע הבא בקוד ותעזרו בו כדי לבדוק אם הקובץ מכיל את אוסף הבתים:


</div>

```
private static final int[] INFECTED_MAGIC = {
    70, 108, 97, 115, 104, 51, 51, 56, 56
};
```

<div dir="rtl">

</div>
