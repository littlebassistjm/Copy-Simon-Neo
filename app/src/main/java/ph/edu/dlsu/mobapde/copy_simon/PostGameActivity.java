package ph.edu.dlsu.mobapde.copy_simon;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class PostGameActivity extends AppCompatActivity {

    TextView tvScore, tvHighest;
    ImageView ivHighScores, ivPlay, ivHome;
    EditText etDialogHighScoreName;
    String table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_game);

        tvScore = findViewById(R.id.tv_score);
        tvHighest = findViewById(R.id.tv_highest);
        ivHighScores = findViewById(R.id.iv_highscores);
        ivPlay = findViewById(R.id.iv_play);
        ivHome = findViewById(R.id.iv_home);
        final DatabaseHelper dbhelper=new DatabaseHelper(getBaseContext());
        Intent i = getIntent();
        final int score = i.getExtras().getInt("Score");
        final String gamemode = i.getExtras().getString(MainActivity.GAME_MODE);
        tvScore.setText(""+score);
        //String table;
        if (gamemode.equals(MainActivity.CLASSIC_MODE)){
            table = Score.TABLE_NAME;
        }
        else  if (gamemode.equals(MainActivity.SPEED_MODE)){
            table = Score.TABLE_NAME2;
        } else {
            table = "no table given";
        }
        tvHighest.setText(""+dbhelper.getHighest(table));

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.LightDialogTheme);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_new_highscore_input, null);
        etDialogHighScoreName = v.findViewById(R.id.et_name_dialog);

        builder.setTitle("New High Score!")
                .setView(v)
                .setCancelable(false)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = etDialogHighScoreName.getText().toString();
                        dbhelper.addScore(new Score(name, score), table);
                    }
                });
        AlertDialog newHighScore = builder.create();

        ivHighScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), HighScoreActivity.class);
                intent.putExtra("Mode",gamemode);
                startActivity(intent);
            }
        });

        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), GameActivity.class);
                intent.putExtra(MainActivity.GAME_MODE,gamemode);

                startActivity(intent);
                finish();
            }
        });

        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Log.i("postGame", "lowest score " + dbhelper.getLowest(table));
        if(score > dbhelper.getLowest(table)){
            newHighScore.show();
        }

    }

}
