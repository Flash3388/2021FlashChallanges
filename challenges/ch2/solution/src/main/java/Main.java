public class Main {
    public static void main(String[] args) {
        Cypher cypher = new Cypher(3);
        System.out.println(cypher.decrypt(cypher.encrypt("abcABCxyzXYZ")));
    }
}
