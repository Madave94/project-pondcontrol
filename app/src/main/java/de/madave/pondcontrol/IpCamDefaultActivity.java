package de.madave.pondcontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.niqdev.mjpeg.DisplayMode;
import com.github.niqdev.mjpeg.Mjpeg;
import com.github.niqdev.mjpeg.MjpegView;

public class IpCamDefaultActivity extends AppCompatActivity {

    MjpegView mjpegView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_cam_default);
        mjpegView = (MjpegView) findViewById(R.id.mjpegViewDefault);
        loadIPCam();
    }

    private void loadIPCam() {
        int TIMEOUT = 5; //seconds

        System.out.println("Hee");

        Mjpeg.newInstance()
                .credential("USERNAME", "PASSWORD")
                .open("http://187.157.229.132:80/mjpg/video.mjpg", TIMEOUT)
                .subscribe(inputStream -> {
                    mjpegView.setSource(inputStream);
                    mjpegView.setDisplayMode(DisplayMode.BEST_FIT);
                    mjpegView.showFps(true);
                });
    }
}
