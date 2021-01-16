package main;

public class Main {

    public static void main(String[] args) {
        if (args.length < 2) {
            return;
        }

        int shift = Integer.parseInt(args[0]);
        String text = args[1];

        Cipher cipher = new Cipher(shift);
        System.out.println(cipher.decrypt(text));
    }
}
