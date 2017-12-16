package ph.edu.dlsu.mobapde.copy_simon;

import android.content.DialogInterface;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    CountDownTimer ct;
    String gameMode;
    ArrayList<Integer> Key_Pattern=new ArrayList<>();
    int totalCount=0,LevelCount=0;
    TextView tvLevel, tvCountdown;
    Random random = new Random();
    ImageButton ivGreen, ivRed, ivYellow, ivBlue;
    Boolean playerLost, isMuted;
    MediaPlayer g1, c2, e2, g2, correctTune, wrongTune;
    MediaPlayer mediaController;
    int g1Duration, c2Duration, e2Duration, g2Duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Initialize variables and elements

        tvLevel = findViewById(R.id.tv_level);
        tvCountdown = findViewById(R.id.tv_countdown);

        ivGreen = findViewById(R.id.iv_green);
        ivRed = findViewById(R.id.iv_red);
        ivYellow = findViewById(R.id.iv_yellow);
        ivBlue = findViewById(R.id.iv_blue);



        playerLost = false;

        // TODO: get intent for game mode
        Intent gameIntent = getIntent();
        gameMode = gameIntent.getExtras().getString(MainActivity.GAME_MODE);
        Log.i("GameActivity", gameMode);
        isMuted = gameIntent.getExtras().getBoolean(MainActivity.SOUND_STATE);

            g1 = MediaPlayer.create(this, R.raw.g1);
            c2 = MediaPlayer.create(this, R.raw.c2);
            e2 = MediaPlayer.create(this, R.raw.e2);
            g2 = MediaPlayer.create(this, R.raw.g2);
            correctTune = MediaPlayer.create(this, R.raw.correct);
            wrongTune = MediaPlayer.create(this, R.raw.wrong);

            g1Duration = g1.getDuration();
            c2Duration = c2.getDuration();
            e2Duration = e2.getDuration();
            g2Duration = g2.getDuration();

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



        /*
        ivBlue.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    ivBlue.setBackgroundResource(R.drawable.blue_lit_game_button);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    ivBlue.setBackgroundResource(R.drawable.blue_game_button);
                    //  *******start Intent here********
                }
                return false;
            }
        });*/

        // call playGame and pass the gameMode
        ivGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(g1.isPlaying()) g1.seekTo(g1Duration-1);
                else if(c2.isPlaying()) c2.seekTo(c2Duration-1);
                else if(e2.isPlaying()) e2.seekTo(e2Duration-1);
                else if(g2.isPlaying()) g2.seekTo(g2Duration-1);

                g1.start();

                if (gameMode.equals("speed"))
                    ct.cancel();
                if(Key_Pattern.get(LevelCount+1)==1){
                    Log.i("playGame", "Green button selected");
                    LevelCount++;
                    tvCountdown.setText(""+(Key_Pattern.size()-(LevelCount+1)));
                    tvCountdown.invalidate();
                    if(gameMode.equals("classic")) {
                        tvLevel.setText("" + (totalCount + LevelCount));
                        tvLevel.invalidate();
                    }
                    if((LevelCount+1)==Key_Pattern.size()){
                        correctTune.start();
                        levelup();
                    }
                    else if (gameMode.equals("speed"))
                        ct.start();
                }else{
                    Log.i("playGame", "Green button fail");
                    wrongTune.start();
                    while(wrongTune.isPlaying());
                    Intent intent = new Intent(getBaseContext(), PostGameActivity.class);
                    intent.putExtra(MainActivity.GAME_MODE, gameMode);
                    intent.putExtra("Score",(totalCount+LevelCount));
                    while(wrongTune.isPlaying());
                    startActivity(intent);
                    finish();
                }
            }
        });


        ivRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(g1.isPlaying()) g1.seekTo(g1Duration-1);
                else if(c2.isPlaying()) c2.seekTo(c2Duration-1);
                else if(e2.isPlaying()) e2.seekTo(e2Duration-1);
                else if(g2.isPlaying()) g2.seekTo(g2Duration-1);

                c2.start();
                if (gameMode.equals("speed"))
                    ct.cancel();
                if(Key_Pattern.get(LevelCount+1)==2){
                    Log.i("playGame", "Red button selected");
                    LevelCount++;
                    tvCountdown.setText(""+(Key_Pattern.size()-(LevelCount+1)));
                    tvCountdown.invalidate();

                    if(gameMode.equals("classic")) {
                        tvLevel.setText("" + (totalCount + LevelCount));
                        tvLevel.invalidate();
                    }
                    if((LevelCount+1)==Key_Pattern.size()){
                        correctTune.start();
                        levelup();
                    }
                    else if (gameMode.equals("speed"))
                        ct.start();

                }else{
                    Log.i("playGame", "Red button fail");
                    wrongTune.start();
                    Intent intent = new Intent(getBaseContext(), PostGameActivity.class);
                    intent.putExtra("Score",(totalCount+LevelCount));
                    intent.putExtra(MainActivity.GAME_MODE, gameMode);
                    while(wrongTune.isPlaying());
                    startActivity(intent);
                    finish();
                }
            }
        });


        ivYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(g1.isPlaying()) g1.seekTo(g1Duration-1);
                else if(c2.isPlaying()) c2.seekTo(c2Duration-1);
                else if(e2.isPlaying()) e2.seekTo(e2Duration-1);
                else if(g2.isPlaying()) g2.seekTo(g2Duration-1);

                e2.start();
                if (gameMode.equals("speed"))
                    ct.cancel();
                if(Key_Pattern.get(LevelCount+1)==3){
                    Log.i("playGame", "Yellow button selected");
                    LevelCount++;
                    tvCountdown.setText(""+(Key_Pattern.size()-(LevelCount+1)));
                    tvCountdown.invalidate();

                    if(gameMode.equals("classic")) {
                        tvLevel.setText("" + (totalCount + LevelCount));
                        tvLevel.invalidate();
                    }
                    if((LevelCount+1)==Key_Pattern.size()){
                        correctTune.start();
                        levelup();
                    }
                    else if (gameMode.equals("speed"))
                        ct.start();
                }else{
                    Log.i("playGame", "Yellow button fail");
                    wrongTune.start();
                    Intent intent = new Intent(getBaseContext(), PostGameActivity.class);
                    intent.putExtra("Score",(totalCount+LevelCount));
                    intent.putExtra(MainActivity.GAME_MODE, gameMode);
                    while(wrongTune.isPlaying());
                    startActivity(intent);
                    finish();

                }
            }
        });

        ivBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(g1.isPlaying()) g1.seekTo(g1Duration-1);
                else if(c2.isPlaying()) c2.seekTo(c2Duration-1);
                else if(e2.isPlaying()) e2.seekTo(e2Duration-1);
                else if(g2.isPlaying()) g2.seekTo(g2Duration-1);

                g2.start();
                if (gameMode.equals("speed"))
                    ct.cancel();
                if(Key_Pattern.get(LevelCount+1)==4){
                    Log.i("playGame", "Blue button selected");
                    LevelCount++;

                    tvCountdown.setText(""+(Key_Pattern.size()-(LevelCount+1)));
                    tvCountdown.invalidate();

                    if(gameMode.equals("classic")) {
                        tvLevel.setText("" + (totalCount + LevelCount));
                        tvLevel.invalidate();
                    }
                    if((LevelCount+1)==Key_Pattern.size()){
                        correctTune.start();
                        levelup();
                    }
                    else if (gameMode.equals("speed"))
                        ct.start();
                }else{
                    Log.i("playGame", "Blue button fail");
                    wrongTune.start();
                    Intent intent = new Intent(getBaseContext(), PostGameActivity.class);
                    intent.putExtra("Score",(totalCount+LevelCount));
                    intent.putExtra(MainActivity.GAME_MODE, gameMode);
                    while(wrongTune.isPlaying());
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
        tvCountdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //playGame(gameMode);
                levelup();
            }
        });
    }
    public void setTimer(int level){
        if(level<=4){
            ct=new CountDownTimer(3000,100) {
                @Override
                public void onTick(long l) {
                    double timeleft=l;
                    timeleft/=1000;
                    tvLevel.setText("Time Left " + new DecimalFormat("##.##").format(timeleft)+"s");
                    tvLevel.invalidate();
                    Log.i("playGame", "C Timer"+l);
                }

                @Override
                public void onFinish() {
                    Log.i("playGame", "C Timer Finished");
                    Intent intent = new Intent(getBaseContext(), PostGameActivity.class);
                    intent.putExtra("Score",(totalCount+LevelCount));
                    intent.putExtra(MainActivity.GAME_MODE, gameMode);
                    startActivity(intent);
                    finish();
                }
            };
        }else if (level<=8){
            ct=new CountDownTimer(2000,100) {
                @Override
                public void onTick(long l) {
                    double timeleft=l;
                    timeleft/=1000;
                    tvLevel.setText("Time Left " + new DecimalFormat("##.##").format(timeleft)+"s");
                    tvLevel.invalidate();
                    Log.i("playGame", "C Timer"+l);
                }

                @Override
                public void onFinish() {
                    Log.i("playGame", "C Timer Finished");
                    Intent intent = new Intent(getBaseContext(), PostGameActivity.class);
                    intent.putExtra("Score",(totalCount+LevelCount));
                    intent.putExtra(MainActivity.GAME_MODE, gameMode);
                    startActivity(intent);
                    finish();
                }
            };
        }else if(level<=11){
            ct=new CountDownTimer(1000,100) {
                @Override
                public void onTick(long l) {
                    double timeleft=l;
                    timeleft/=1000;
                    tvLevel.setText("Time Left " + new DecimalFormat("##.##").format(timeleft)+"s");
                    tvLevel.invalidate();
                    Log.i("playGame", "C Timer"+l);
                }

                @Override
                public void onFinish() {
                    Log.i("playGame", "C Timer Finished");
                    Intent intent = new Intent(getBaseContext(), PostGameActivity.class);
                    intent.putExtra("Score",(totalCount+LevelCount));
                    intent.putExtra(MainActivity.GAME_MODE, gameMode);
                    startActivity(intent);
                    finish();
                }
            };
        }else if(level<=14){
            ct=new CountDownTimer(500,100) {
                @Override
                public void onTick(long l) {

                    double timeleft=l;
                    timeleft/=1000;
                    tvLevel.setText("Time Left " + new DecimalFormat("##.##").format(timeleft)+"s");
                    tvLevel.invalidate();
                    Log.i("playGame", "C Timer"+l);
                }

                @Override
                public void onFinish() {
                    Log.i("playGame", "C Timer Finished");
                    Intent intent = new Intent(getBaseContext(), PostGameActivity.class);
                    intent.putExtra("Score",(totalCount+LevelCount));
                    intent.putExtra(MainActivity.GAME_MODE, gameMode);
                    startActivity(intent);
                    finish();
                }
            };
        }else{
            ct=new CountDownTimer(200,100) {
                @Override
                public void onTick(long l) {
                    double timeleft=l;
                    timeleft/=1000;
                    tvLevel.setText("Time Left " + new DecimalFormat("##.##").format(timeleft)+"s");
                    tvLevel.invalidate();
                    Log.i("playGame", "C Timer"+l);
                }

                @Override
                public void onFinish() {
                    Log.i("playGame", "C Timer Finished");
                    Intent intent = new Intent(getBaseContext(), PostGameActivity.class);
                    intent.putExtra("Score",(totalCount+LevelCount));
                    intent.putExtra(MainActivity.GAME_MODE, gameMode);
                    startActivity(intent);
                    finish();
                }
            };
        }
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
        if(gameMode.equals("speed"))
            setTimer(level);
        Integer[] keyIntArray = Key_Pattern.toArray(new Integer[Key_Pattern.size()]);
        new PatternTask().execute(keyIntArray);
    }

    /*
    public void playGame(String gameMode){

        SystemClock.sleep(1000);

        Log.i("playGame", "entered playGame");

        int newKey, timeDelay, remainingKeys;
        Random random = new Random();
        ArrayList<Integer> keyPatterns = new ArrayList<>();
        final ArrayList<ImageButton> gameButtons = new ArrayList<>();
        gameButtons.add(ivGreen);
        gameButtons.add(ivRed);
        gameButtons.add(ivYellow);
        gameButtons.add(ivBlue);

        Log.i("playGame", "initialized variables");

        playerLost = false;

        // green == 1, red == 2, yellow == 3, blue == 4
        switch (gameMode){
            case MainActivity.CLASSIC_MODE:
                Log.i("playGame", "starting classic mode");
                // TODO: loop rounds until user loses then go to high score
                while (!playerLost){

                // (at the start of each round)
                    // generate new key and add to list
                        // place holder
                        keyPatterns.add(5);
                        for (int i=0; i<1; i++){
                            newKey = random.nextInt(4)+1;
                            keyPatterns.add(newKey);
                        }
                        Log.i("playGame", "generated new key");

                    // set level # to size of list of keys
                        int level = keyPatterns.size()-1;
                        tvLevel.setText("Level " + level);
                        tvLevel.invalidate();
                        Log.i("playGame", "set level");

                    //set remaining keys to size of list of keys
                        remainingKeys = level;
                        tvCountdown.setText(String.valueOf(remainingKeys));
                        tvCountdown.invalidate();
                        Log.i("playGame", "set initial keys");

                    // TODO: animate the pattern
                        Integer[] keyIntArray = keyPatterns.toArray(new Integer[keyPatterns.size()]);
                        new PatternTask().execute(keyIntArray);

                    // TODO: if user pressed wrong button: lose
                        if(true){
                            playerLost = true;
                        }
                        Log.i("playGame", "player has lost");
                }
                break;
            /*case MainActivity.SPEED_MODE:
                break;*/
            /*case MainActivity.COOP_MODE:
                break;
            default:
                // invalid/unreleased game mode; notify and return to main activity
                Log.i("playGame", "invalid/unreleased game mode");
                Toast invalidModeToast = new Toast(getBaseContext());
                invalidModeToast.setText("Invalid//unreleased game mode");
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }*/

    // Used to animate the defined pattern
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

            if(g1.isPlaying()) g1.seekTo(g1Duration-1);
            else if(c2.isPlaying()) c2.seekTo(c2Duration-1);
            else if(e2.isPlaying()) e2.seekTo(e2Duration-1);
            else if(g2.isPlaying()) g2.seekTo(g2Duration-1);

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
            if (gameMode.equals("speed")) ct.start();
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
