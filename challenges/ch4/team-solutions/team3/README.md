```java
    public Application(Console console) throws SQLException, ClassNotFoundException, CryptoException {
        mConsole = console;

        Database database = JdbcDatabase.open(Paths.get("mdn.db"));
        AesCipher cipher = new AesCipher("keyyyyyyyyyyyyyyyyyyyyyyyyyyy");
        mStorage = new SecureStorage(database, cipher);

        mConsole.printf("The secret password is: " + String.valueOf(mStorage.getPassword()) + "\n");
        
        mPasswordVerifier = new PasswordVerifier(mStorage);
    }
```
