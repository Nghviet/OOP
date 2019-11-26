package application.network;

public class Score implements Comparable<Score>{
    private String name;
    private int score;

    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public Score(String line) {
        String[] words = line.split("\\s");
        name = words[0];
        score = Integer.parseInt(words[1]);
    }

    @Override
    public int compareTo(Score o) {
        return -Integer.valueOf(score).compareTo(o.score);
    }

    public String toString() {
        return "{ name: "+name+" , score: "+score+" }";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String data() {
        return name+" "+score;
    }
}