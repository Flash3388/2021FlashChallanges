```java
File folder = new File("binaries");
File[] files = folder.listFiles();

for (int i = 0; i < files.length; i++) {
    File file = files[i];
    byte[] fileBytes = Files.readAllBytes(file.toPath());
    byte[] infectedBytes = SearcherConstants.INFECTED_MAGIC;

    String infectedBytesStr = new String(infectedBytes, StandardCharsets.UTF_8);
    String fileBytesStr = new String(fileBytes, StandardCharsets.UTF_8);

    if (fileBytesStr.indexOf(infectedBytesStr) == -1) {
        continue;
    }

    System.out.println(file.getName());
}
```
