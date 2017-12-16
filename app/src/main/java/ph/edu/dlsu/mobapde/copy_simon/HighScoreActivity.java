package ph.edu.dlsu.mobapde.copy_simon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class HighScoreActivity extends AppCompatActivity {
    RecyclerView rvScore;
    DatabaseHelper dbhelper;
    ScoreAdapter sa;
    Button chs,shs;
    String mode;
    public static boolean INITIALIZED=false;
    private void intialize(DatabaseHelper db){
        SharedPreferences shp=
                PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor e =shp.edit();
        String in=shp.getString("initialized",null);
        if(in!=null)
            return;

        dbhelper.addScore(new Score("Samantha",150),Score.TABLE_NAME);
        dbhelper.addScore(new Score("Jason",90),Score.TABLE_NAME);
        dbhelper.addScore(new Score("Ahmed",110),Score.TABLE_NAME);
        dbhelper.addScore(new Score("Leon",100),Score.TABLE_NAME);
        dbhelper.addScore(new Score("Carlo",50),Score.TABLE_NAME);
        dbhelper.addScore(new Score("Vincent",100),Score.TABLE_NAME);
        dbhelper.addScore(new Score("Tomas",60),Score.TABLE_NAME);
        dbhelper.addScore(new Score("Hansel",90),Score.TABLE_NAME);
        dbhelper.addScore(new Score("Urich",20),Score.TABLE_NAME);
        dbhelper.addScore(new Score("Thomson",15),Score.TABLE_NAME);

        dbhelper.addScore(new Score("Ben",150),Score.TABLE_NAME2);
        dbhelper.addScore(new Score("Nikolai",90),Score.TABLE_NAME2);
        dbhelper.addScore(new Score("Blake",110),Score.TABLE_NAME2);
        dbhelper.addScore(new Score("Porzingis",100),Score.TABLE_NAME2);
        dbhelper.addScore(new Score("DeMarcus",50),Score.TABLE_NAME2);
        dbhelper.addScore(new Score("Demar",100),Score.TABLE_NAME2);
        dbhelper.addScore(new Score("Kemba",60),Score.TABLE_NAME2);
        dbhelper.addScore(new Score("Grettel",90),Score.TABLE_NAME2);
        dbhelper.addScore(new Score("Damian",20),Score.TABLE_NAME2);
        dbhelper.addScore(new Score("Lonzo",15),Score.TABLE_NAME2);
        e.putString("initialized","yes");
        e.commit();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        rvScore=(RecyclerView) findViewById(R.id.rvscore);
        chs=findViewById(R.id.classichs);
        shs=findViewById(R.id.speedhs);

        shs.setText("SPEED");
        chs.setPaintFlags(chs.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        chs.setText("CLASSIC");

        Intent i = getIntent();
        mode = i.getExtras().getString("Mode")+"Highscore";
        dbhelper = new DatabaseHelper(getBaseContext());
        intialize(dbhelper);
        Log.d("Count of scores", " value of "+dbhelper.getCount(mode));
        sa=new ScoreAdapter(getBaseContext(),
                dbhelper.getAllScoresCursor(mode));

        /*
        ArrayList<Score> SList=new ArrayList<Score>();
        SList.add(new Score("Tom",100));
        SList.add(new Score("Jake",90));
        SList.add(new Score("Hassan",80));
        SList.add(new Score("Ching",70));
        SList.add(new Score("Cho",60));
        SList.add(new Score("Hans",50));
        SList.add(new Score("Nick",40));
        SList.add(new Score("Juan",30));
        SList.add(new Score("Samael",20));
        SList.add(new Score("Jamal",15));
        ScoreAdapter sa= new ScoreAdapter(SList);
        */
        rvScore.setAdapter(sa);

        rvScore.setLayoutManager(new LinearLayoutManager(
                //getBaseContext()
                this, LinearLayoutManager.VERTICAL, false));

        chs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chs.setText("CLASSIC");
                chs.setPaintFlags(chs.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                chs.setBackgroundResource(R.drawable.green_button);
                shs.setText("SPEED");
                shs.setPaintFlags(chs.getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));
                shs.setBackgroundResource(R.drawable.yellow_button_off);

                sa=new ScoreAdapter(getBaseContext(),
                        dbhelper.getAllScoresCursor("classicHighscore"));
                rvScore.setAdapter(sa);

            }
        });
        shs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shs.setText("SPEED");
                shs.setPaintFlags(chs.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                shs.setBackgroundResource(R.drawable.yellow_button);
                chs.setText("CLASSIC");
                chs.setPaintFlags(chs.getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));
                chs.setBackgroundResource(R.drawable.green_button_off);
                sa=new ScoreAdapter(getBaseContext(),
                        dbhelper.getAllScoresCursor("speedHighscore"));
                rvScore.setAdapter(sa);
            }
        });
    }

}
