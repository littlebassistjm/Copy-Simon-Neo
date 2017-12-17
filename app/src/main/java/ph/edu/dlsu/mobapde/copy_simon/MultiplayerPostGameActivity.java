package ph.edu.dlsu.mobapde.copy_simon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MultiplayerPostGameActivity extends AppCompatActivity {

    TextView tvScore;
    ImageView ivPlay, ivHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_post_game);

        tvScore = findViewById(R.id.tv_score);
        ivPlay = findViewById(R.id.iv_play);
        ivHome = findViewById(R.id.iv_home);

        Intent i = getIntent();
        final int score = i.getExtras().getInt("Score");
        final String gamemode = i.getExtras().getString(MainActivity.GAME_MODE);
        tvScore.setText(""+score);

        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MultiplayerActivity.class);
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
    }
}
