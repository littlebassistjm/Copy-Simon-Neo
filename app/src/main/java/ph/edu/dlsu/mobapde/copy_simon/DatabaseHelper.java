package ph.edu.dlsu.mobapde.copy_simon;

/**
 * Created by Abdul Bandlang on 11/13/2017.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String SCHEMA="appdb";
    public static final int VERSION=2;
    public DatabaseHelper(Context context){
        super(context,SCHEMA,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + Score.TABLE_NAME + "("
                + Score.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Score.COLUMN_NAME + " TEXT,"
                + Score.COLUMN_POINTS + " INTEGER"
                + ");";
        String sql2 = "CREATE TABLE " + Score.TABLE_NAME2 + " ("
                + Score.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Score.COLUMN_NAME + " TEXT,"
                + Score.COLUMN_POINTS + " INTEGER"
                + ");";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL(sql2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS " + Score.TABLE_NAME + ";";
        String sql2 = "DROP TABLE IF EXISTS " + Score.TABLE_NAME2 + ";";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL(sql2);
        // call onCreate
        onCreate(sqLiteDatabase);
    }
    private boolean cleanup(String gameMode){
        if(getCount(gameMode)>10){
            SQLiteDatabase db=this.getWritableDatabase();
            Cursor c = db.rawQuery("SELECT max("+Score.COLUMN_ID+")'max' FROM "+gameMode,null);
            int s=0 ;
            if(c.moveToFirst()){
                s = c.getInt(c.getColumnIndex("max"));
            }
            c.close();
            int rowsAffected=db.delete(gameMode,Score.COLUMN_ID+"=?",new String[]{s+""});
            db.close();
            return rowsAffected>0;
        }
        return true;
    }
    private Score shiftScore(Score score,Score original,String gameMode){
        if(original.getPoints()>=score.getPoints())
            return score;
        else{
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(Score.COLUMN_NAME,score.getName());
            cv.put(Score.COLUMN_POINTS,score.getPoints());
            db.update(gameMode,cv,
                    Score.COLUMN_ID+"=?",
                    new String[]{original.getId()+""});
            db.close();
            return original;
        }

    }

    public long addScore(Score score,String gameMode){
        ArrayList<Score> slist=getScoresList(gameMode);
        for(Score curr:slist){
            score=shiftScore(score,curr,gameMode);
        }
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Score.COLUMN_NAME,score.getName());
        cv.put(Score.COLUMN_POINTS,score.getPoints());
        long id=db.insert(gameMode,null,cv);
        db.close();
        cleanup(gameMode);
        return id;
    }
    public int getHighest(String gameMode){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT Max("+Score.COLUMN_POINTS+")'max' FROM "+gameMode,null);
        int s=0 ;
        if(c.moveToFirst()){
            s = c.getInt(c.getColumnIndex("max"));
        }
        c.close();
        db.close();
        return s;
    }
    public int getLowest(String gamemode){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT Min("+Score.COLUMN_POINTS+")'min' FROM "+gamemode,null);
        int s=0 ;
        if(c.moveToFirst()){
            s = c.getInt(c.getColumnIndex("min"));
        }
        c.close();
        db.close();
        return s;
    }


    public Score getScore(long id,String gameMode){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(gameMode,
                null,
                Score.COLUMN_ID + "=?",
                new String[]{ id+"" },
                null,
                null,
                null);
        Score s = null;
        if(c.moveToFirst()){
            s = new Score(c.getString(c.getColumnIndex(Score.COLUMN_NAME)),
                    c.getInt(c.getColumnIndex(Score.COLUMN_POINTS)));
        }

        c.close();
        db.close();

        return s;
    }
    public int getCount(String gameMode){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT COUNT(*)'count' FROM "+gameMode,null);
        int s=0 ;
        if(c.moveToFirst()){
            s = c.getInt(c.getColumnIndex("count"));
        }
        c.close();
        db.close();
        return s;
    }
    private ArrayList<Score> getScoresList(String gameMode){
        ArrayList<Score> slist=new ArrayList<Score>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(gameMode,
                null,
                null,
                null,
                null,
                null,
                null);
        //Score s = null;
        /*
        if(c.moveToFirst()){
            s = new Score(c.getString(c.getColumnIndex(Score.COLUMN_NAME)),
                    c.getInt(c.getColumnIndex(Score.COLUMN_POINTS)));
        }*/
        try{
            while(c.moveToNext()){
                Score s =new Score(c.getString(c.getColumnIndex(Score.COLUMN_NAME)),
                        c.getInt(c.getColumnIndex(Score.COLUMN_POINTS)));
                s.setId(c.getInt(c.getColumnIndex(Score.COLUMN_ID)));
                slist.add(s);
            }
        }finally {
            c.close();
        }

        c.close();
        db.close();

        return slist;
    }

    // getAllKoreans
    public Cursor getAllScoresCursor(String gameMode){
        return getReadableDatabase().query(gameMode, null,null,null,null,null,null);
    }
}
