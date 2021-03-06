package ph.edu.dlsu.mobapde.copy_simon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button buttonClassic, buttonSpeedMode, buttonCoop;
    ImageView ivHighScores, ivHelp, ivSettings;
    public static MediaPlayer lobbyMusic;

    public final static String GAME_MODE = "gameMode";
    public final static String CLASSIC_MODE = "classic";
    public final static String SPEED_MODE = "speed";
    public final static String COOP_MODE = "coop";
    public final static String SOUND_STATE = "soundState";

    //public AudioManager audioManager;

    private boolean isMuted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        BroadcastReceiver mReceiver = new ScreenReceiver();
        registerReceiver(mReceiver, filter);

        lobbyMusic = MediaPlayer.create(this, R.raw.bit_rush_lol);
        //lobbyMusic.setLooping(true);
        Log.i("main", lobbyMusic.getDuration()+"");

        buttonClassic = findViewById(R.id.button_classic);
        buttonSpeedMode = findViewById(R.id.button_speed_mode);
        buttonCoop = findViewById(R.id.button_coop);
        ivHighScores = findViewById(R.id.iv_highscores);
        ivHelp = findViewById(R.id.iv_help);
        ivSettings = findViewById(R.id.iv_settings);



        //audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        isMuted = false;

        // let user play TODO: allow other modes
        buttonClassic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), GameActivity.class);
                intent.putExtra(GAME_MODE, CLASSIC_MODE);
                intent.putExtra(SOUND_STATE, isMuted);

                lobbyMusic.pause();

                startActivity(intent);
            }
        });
        buttonSpeedMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), GameActivity.class);
                intent.putExtra(GAME_MODE, SPEED_MODE);
                intent.putExtra(SOUND_STATE, isMuted);

                lobbyMusic.pause();

                startActivity(intent);
            }
        });


        buttonCoop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MultiplayerActivity.class);
                intent.putExtra(GAME_MODE, COOP_MODE);
                intent.putExtra(SOUND_STATE, isMuted);

                lobbyMusic.seekTo(lobbyMusic.getDuration());

                startActivity(intent);
            }
        });

        // view High scores
        ivHighScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), HighScoreActivity.class);
                intent.putExtra("Mode",CLASSIC_MODE);
                intent.putExtra("From Main", true);
                startActivity(intent);
            }
        });

        // view help/instructions
        ivHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), InstructionsActivity.class);
                //Intent intent = new Intent(getBaseContext(), MainActivityFirebase.class);
                startActivity(intent);
            }
        });

        // TODO: turn on/off sounds
        ivSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isMuted){
                    //audioManager.setMode(AudioManager.ADJUST_MUTE);
                    ivSettings.setImageResource(R.drawable.muted_sound_button);
                    lobbyMusic.pause();
                    isMuted=true;
                } else{
                    //audioManager.setMode(AudioManager.ADJUST_UNMUTE);
                    ivSettings.setImageResource(R.drawable.sound_button);
                    lobbyMusic.start();
                    isMuted=false;
                }
            }
        });

    }

    @Override
    protected void onPause() {
        if (ScreenReceiver.wasScreenOn) {
            // this is the case when onPause() is called by the system due to a screen state change
            System.out.println("SCREEN TURNED OFF");
            Log.i("main", "onStop");
            lobbyMusic.pause();
        } else {
            // this is when onPause() is called when the screen state has not changed
        }
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        lobbyMusic.start();
        Log.i("main", "onstart");
    }
}
