package ph.edu.dlsu.mobapde.copy_simon;

/**
 * Created by ASUS on 12/15/2017.
 */

public class ScoreFirebase {

    public static final String EXTRA_KEY = "key";

    private String uid;
    private String name;
    private String score;

    public ScoreFirebase() {
    }

    public ScoreFirebase(String name, String score) {
        this.name = name;
        this.score = score;
    }

    public ScoreFirebase(String uid, String name, String score) {
        this.uid = uid;
        this.name = name;
        this.score = score;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
