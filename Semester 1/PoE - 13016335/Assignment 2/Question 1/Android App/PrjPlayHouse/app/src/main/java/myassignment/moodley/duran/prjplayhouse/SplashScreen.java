package myassignment.moodley.duran.prjplayhouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;

import com.moodley.duran.prjplayhouse.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ProgressBar loading = (ProgressBar) findViewById(R.id.prbLoading);
        //Sets up thread to run
        if (loading != null) {
            loading.setProgress(10);
        }
        Thread timer = new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(3000);
                }
                catch(InterruptedException ex)
                {
                    ex.printStackTrace();
                }
                finally
                {
                    Intent newActivity = new Intent(SplashScreen.this,Login.class);
                    startActivity(newActivity);
                }
            }
        };
        timer.start();

    }
    //*********************************************************************************
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
