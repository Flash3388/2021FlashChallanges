public class Main {
    public static void main(String[] args) {
        Encrypter enc = new Encrypter(3);
        Decrypter dec = new Decrypter(3);
        System.out.println(dec.decrypt(enc.encrypt("abcABCxyzXYZ")));
    }
}
