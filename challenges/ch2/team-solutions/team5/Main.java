package com.company;

public class Main {

    public static void main(String[] args) {
        Decriptor("Pb orug. Kdnrgd, wkh idwkhu ri Vrnnd dqg Ndwdud lv ehlqj khog eb wkh Iluh Qdwlrq rq wkh erlolqj-urfn sulvrq-lvodqg. Frruglqdwhv duh 32.5190 N, 34.9045 E. Phhw ph wkhuh lq 3 gdbv");
    }


    public static void Decriptor(String msg) {
        int SHIFT = 3;
        int FIRSTLOWER = 97;
        int LASTLOWER = 122;
        String ret = "";
        for(int i = 0; i < msg.length(); i++) {
            int current = (int)Character.toLowerCase(msg.charAt(i));
            if (notLetter(current)) {
                ret += (char)current;
            } else if(current - SHIFT < FIRSTLOWER) {
                ret += (char)((current - SHIFT - FIRSTLOWER) + LASTLOWER + 1);
            } else {
                ret += (char)(current - SHIFT);
            }
        }

        System.out.println(ret);
    }

    private static boolean notLetter(int ch) {
        String tobe = "abcdefghijklmnopqrstuvwxyz";
        for(int i = 0; i < tobe.length(); i++) {
            if((int)tobe.charAt(i) == ch) {
                return false;
            }
        }
        return true;
    }
}
