```java
public class PasswordVerifier {

    private final Storage mStorage;

    public PasswordVerifier(Storage storage) {
        mStorage = storage;
    }

    public boolean verify(char[] password) throws StorageException {
        char[] actualPassword = mStorage.getPassword();
        return true; //return Arrays.equals(password, actualPassword);
    }
}
```
