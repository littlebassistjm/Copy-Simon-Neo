package ph.edu.dlsu.mobapde.copy_simon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import static java.security.AccessController.getContext;

public class MultiplayerActivity extends AppCompatActivity {
    ArrayList<Integer> Key_Pattern=new ArrayList<>();
    int totalCount=0,LevelCount=0;
    String gameMode;
    TextView tvLevel1, tvCountdown;
    Random random = new Random();
    RelativeLayout r1,r2;
    ImageButton ivGreen, ivRed, ivYellow, ivBlue;
    Boolean playerLost, isMuted;
    MediaPlayer g1, c2, e2, g2;
    MediaPlayer mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);

        tvLevel1 = findViewById(R.id.tv_scoreM1);
        tvCountdown = findViewById(R.id.tv_countrdown);


        ivGreen = findViewById(R.id.iv_greenM);
        ivRed = findViewById(R.id.iv_redM);
        ivYellow = findViewById(R.id.iv_yellowM);
        ivBlue = findViewById(R.id.iv_blueM);

        r1=findViewById(R.id.side1);
        r2=findViewById(R.id.side2);

        playerLost = false;
        Intent gameIntent = getIntent();
        gameMode = gameIntent.getExtras().getString(MainActivity.GAME_MODE);
        isMuted = gameIntent.getExtras().getBoolean(MainActivity.SOUND_STATE);

        g1 = MediaPlayer.create(this, R.raw.g1);
        c2 = MediaPlayer.create(this, R.raw.c2);
        e2 = MediaPlayer.create(this, R.raw.e2);
        g2 = MediaPlayer.create(this, R.raw.g2);

        if (isMuted){ // if muted, set volumes to 0
            g1.setVolume(0,0);
            c2.setVolume(0,0);
            e2.setVolume(0,0);
            g2.setVolume(0,0);
        } else { // if not muted, set volumes to 1
            g1.setVolume(1,1);
            c2.setVolume(1,1);
            e2.setVolume(1,1);
            g2.setVolume(1,1);
        }

        ivGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                g1.start();
                if(Key_Pattern.get(LevelCount+1)==1){
                    LevelCount++;
                    tvCountdown.setText(""+(Key_Pattern.size()-(LevelCount+1)));
                    tvCountdown.invalidate();

                    tvLevel1.setText("" + (totalCount + LevelCount));
                    tvLevel1.invalidate();

                    if((LevelCount+1)==Key_Pattern.size())
                        levelup();
                }else{
                    r1.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.PlayerFault));
                    Log.i("playGame", "Green button fail");
                    Intent intent = new Intent(getBaseContext(), MultiplayerPostGameActivity.class);
                    intent.putExtra(MainActivity.GAME_MODE, gameMode);
                    intent.putExtra("Score",(totalCount+LevelCount));
                    startActivity(intent);
                    finish();
                }
            }
        });


        ivRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c2.start();

                if(Key_Pattern.get(LevelCount+1)==2){
                    LevelCount++;
                    tvCountdown.setText(""+(Key_Pattern.size()-(LevelCount+1)));
                    tvCountdown.invalidate();

                    tvLevel1.setText("" + (totalCount + LevelCount));
                    tvLevel1.invalidate();

                    if((LevelCount+1)==Key_Pattern.size())
                        levelup();
                }else{
                    r2.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.PlayerFault));
                    Log.i("playGame", "Red button fail");
                    Intent intent = new Intent(getBaseContext(), MultiplayerPostGameActivity.class);
                    intent.putExtra("Score",(totalCount+LevelCount));
                    intent.putExtra(MainActivity.GAME_MODE, gameMode);
                    startActivity(intent);
                    finish();
                }
            }
        });


        ivYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                e2.start();
                if(Key_Pattern.get(LevelCount+1)==3){
                    Log.i("playGame", "Yellow button selected");
                    LevelCount++;
                    tvCountdown.setText(""+(Key_Pattern.size()-(LevelCount+1)));
                    tvCountdown.invalidate();

                    tvLevel1.setText("" + (totalCount + LevelCount));
                    tvLevel1.invalidate();

                    if((LevelCount+1)==Key_Pattern.size())
                        levelup();
                }else{
                    r1.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.PlayerFault));
                    Log.i("playGame", "Yellow button fail");
                    Intent intent = new Intent(getBaseContext(), MultiplayerPostGameActivity.class);
                    intent.putExtra("Score",(totalCount+LevelCount));
                    intent.putExtra(MainActivity.GAME_MODE, gameMode);
                    startActivity(intent);
                    finish();

                }
            }
        });

        ivBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                g2.start();
                if(Key_Pattern.get(LevelCount+1)==4){
                    Log.i("playGame", "Blue button selected");
                    LevelCount++;
                    tvCountdown.setText(""+(Key_Pattern.size()-(LevelCount+1)));
                    tvCountdown.invalidate();

                    tvLevel1.setText("" + (totalCount + LevelCount));
                    tvLevel1.invalidate();

                    if((LevelCount+1)==Key_Pattern.size())
                        levelup();
                }else{
                    r2.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.PlayerFault));
                    Log.i("playGame", "Blue button fail");
                    Intent intent = new Intent(getBaseContext(), MultiplayerPostGameActivity.class);
                    intent.putExtra("Score",(totalCount+LevelCount));
                    intent.putExtra(MainActivity.GAME_MODE, gameMode);
                    startActivity(intent);
                    finish();

                }
            }
        });


        ivGreen.setClickable(false);
        ivRed.setClickable(false);
        ivYellow.setClickable(false);
        ivBlue.setClickable(false);

        Key_Pattern.add(5);

        tvCountdown.setText("Start");
        tvLevel1.setText("0");
        tvLevel1.invalidate();
        tvCountdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //playGame(gameMode);
                levelup();
            }
        });

    }
    public void levelup(){
        totalCount+=LevelCount;
        int newkey=random.nextInt(4)+1;
        Key_Pattern.add(newkey);
        int level = Key_Pattern.size()-1;
        //tvLevel.setText("Level " + level);
        //tvLevel.invalidate();
        tvCountdown.setText(String.valueOf(level));
        tvCountdown.invalidate();
        LevelCount=0;
        Integer[] keyIntArray = Key_Pattern.toArray(new Integer[Key_Pattern.size()]);
        new PatternTask().execute(keyIntArray);
    }

    public class PatternTask extends AsyncTask<Integer, Integer, Void> {

        int i = 0;
        boolean delayCheck = false;
        Integer[] pattern, delayArray;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ivGreen.setClickable(false);
            ivRed.setClickable(false);
            ivYellow.setClickable(false);
            ivBlue.setClickable(false);
            Log.i("playGame", "set unclickable");
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            pattern = integers;
            delayArray = new Integer[1];
            delayArray[0] = 5;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (i<pattern.length-1){
                if(!delayCheck){
                    i++;
                    delayCheck = true;
                    publishProgress(pattern[i]);
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    publishProgress(delayArray[0]);
                    delayCheck = false;
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            publishProgress(delayArray[0]);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);




            switch (values[0]){
                case 1: ivGreen.setBackground(getDrawable(R.drawable.green_lit_game_button)); g1.start(); break;
                case 2: ivRed.setBackground(getDrawable(R.drawable.red_lit_game_button)); c2.start(); break;
                case 3: ivYellow.setBackground(getDrawable(R.drawable.yellow_lit_game_button)); e2.start(); break;
                case 4: ivBlue.setBackground(getDrawable(R.drawable.blue_lit_game_button)); g2.start(); break;

                case 5: ivGreen.setBackground(getDrawable(R.drawable.green_game_button));
                    ivRed.setBackground(getDrawable(R.drawable.red_game_button));
                    ivYellow.setBackground(getDrawable(R.drawable.yellow_game_button));
                    ivBlue.setBackground(getDrawable(R.drawable.blue_game_button));
                    break;
            }

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ivGreen.setBackground(getDrawable(R.drawable.green_button_selector));
            ivRed.setBackground(getDrawable(R.drawable.red_button_selector));
            ivYellow.setBackground(getDrawable(R.drawable.yellow_button_selector));
            ivBlue.setBackground(getDrawable(R.drawable.blue_button_selector));

            ivGreen.setClickable(true);
            ivRed.setClickable(true);
            ivYellow.setClickable(true);
            ivBlue.setClickable(true);
            Log.i("playGame", "set clickable");

        }
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.LightDialogTheme);
        //builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        final AlertDialog alert = builder.create();
            /*
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user pressed "yes", then he is allowed to exit from application
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user select "No", just cancel this dialog and continue with app
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();*/
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alert.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alert.dismiss();
            }
        });
        alert.show();

    }
}
