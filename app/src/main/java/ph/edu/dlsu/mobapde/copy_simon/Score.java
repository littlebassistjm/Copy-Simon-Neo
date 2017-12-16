package ph.edu.dlsu.mobapde.copy_simon;

/**
 * Created by Abdul Bandlang on 11/13/2017.
 */

public class Score {
    public static final String EXTRA_ID = "id";
    public static final String EXTRA_TOADD = "add";
    public static final String TABLE_NAME2="speedHighscore";
    public static final String TABLE_NAME="classicHighscore";

    public static final String COLUMN_ID="_id";
    public static final String COLUMN_NAME="name";
    public static final String COLUMN_POINTS="points";

    private int id;
    private int points;
    private String name;

    public int getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }

    Score(String name,int points){
        this.points=points;
        this.name=name;
    }
    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return id;
    }
}
