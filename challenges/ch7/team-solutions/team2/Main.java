package app;

import pdf.Pdf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        ArrayList<Pair> pairs = new ArrayList<Pair>();
        pairs.add(new Pair(2,6));
        pairs.add(new Pair(14,175));
        pairs.add(new Pair(8,142));
        pairs.add(new Pair(4,21));
        pairs.add(new Pair(49,266));
        pairs.add(new Pair(2,6));
        pairs.add(new Pair(663,269));
        pairs.add(new Pair(4,2));
        pairs.add(new Pair(4,50));
        pairs.add(new Pair(65,166));
        pairs.add(new Pair(5,108));
        pairs.add(new Pair(5,89));
        pairs.add(new Pair(5,227));
        pairs.add(new Pair(2,6));
        pairs.add(new Pair(663,269));
        pairs.add(new Pair(2,21));
        pairs.add(new Pair(21,58));

        Pdf pdf = new Pdf(new File("war-and-peace.pdf"));
        String msg = "";

        for (int i = 0; i < pairs.size(); i++)
        {
            msg += pdf.readPage(pairs.get(i).First()).split("\\s+")[pairs.get(i).Second() - 1] + " ";
        }

        System.out.println(msg);
    }

    private static class Pair {
        private int first;
        private int second;

        private Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }

        public int First() {
            return first;
        }

        public int Second() {
            return second;
        }
    }
}
