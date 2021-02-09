```java
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

   public static void main(String[] args) {

        File binFolder = new File("C:\\Users\\c4317\\Downloads\\binaries\\binaries");
        File[] folderFiles = binFolder.listFiles();
        if (folderFiles != null) {
            List<String> lst = searchInFiles(folderFiles);
            for(String i : lst) {
                System.out.println(i);
            }
        }

    }



    public static List<String> searchInFiles(File[] folderFiles) {

        List<String> returnList = new ArrayList<>();
        for (File file : folderFiles) {
            try {
                List<Integer> fileByteList = new ArrayList<>();
                InputStream iStream = new FileInputStream(file);
                int data = iStream.read();
                for(int i = 0; i < VIRUS_ID_LENGTH; i++) {
                    fileByteList.add(data);
                    data = iStream.read();
                }
                while(data != -1) {
                    if (fileByteList.equals(VIRUS_ID)) {
                        returnList.add(file.getName());
                        break;
                    }
                    fileByteList.remove(0);
                    data = iStream.read();
                    fileByteList.add(data);
                }
                iStream.close();
            }
            catch (IOException error) {
                error.printStackTrace();
                System.out.println("Reached Catch Error"); // Debug
            }
        }
        System.out.println("Return"); // Debug

        return returnList;
    }

    public static List<Integer> VIRUS_ID = new ArrayList<Integer>() {{
        add(70);
        add(108);
        add(97);
        add(115);
        add(104);
        add(51);
        add(51);
        add(56);
        add(56);
    }};
    public static int VIRUS_ID_LENGTH = 9; // 9 byte long, counting start from 0

}
```
