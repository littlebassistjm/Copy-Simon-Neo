package ph.edu.dlsu.mobapde.copy_simon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class InstructionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        MainActivity.lobbyMusic.start();
    }
}
