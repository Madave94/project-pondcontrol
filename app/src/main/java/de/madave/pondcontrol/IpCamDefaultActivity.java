package de.madave.pondcontrol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.niqdev.mjpeg.DisplayMode;
import com.github.niqdev.mjpeg.Mjpeg;
import com.github.niqdev.mjpeg.MjpegView;

public class IpCamDefaultActivity extends AppCompatActivity {

    MjpegView mjpegView;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_cam_default);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mjpegView = (MjpegView) findViewById(R.id.mjpegViewDefault);
    }

    private void loadIPCam() {
        int TIMEOUT = 5; //seconds

        Mjpeg.newInstance()
                .credential(sharedPreferences.getString("username",""), sharedPreferences.getString("password", ""))
                .open(sharedPreferences.getString("url", ""), TIMEOUT)
                .subscribe(inputStream -> {
                    mjpegView.setSource(inputStream);
                    mjpegView.setDisplayMode(DisplayMode.BEST_FIT);
                    mjpegView.showFps(true);
                },
                throwable -> {
                    Log.e(getClass().getSimpleName(), "mjpeg error", throwable);
                    Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadIPCam();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mjpegView.stopPlayback();
    }
}
