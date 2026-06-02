package com.example.rotatetool;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnUpsideDown = findViewById(R.id.btnUpsideDown);
        Button btnReset = findViewById(R.id.btnReset);

        // 檢查 WRITE_SETTINGS 權限
        if (!Settings.System.canWrite(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        }

        btnUpsideDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRotation(2); // 180°
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetRotation();
            }
        });
    }

    private void setRotation(int rotation) {
        try {
            Settings.System.putInt(getContentResolver(),
                    Settings.System.ACCELEROMETER_ROTATION, 0);

            Settings.System.putInt(getContentResolver(),
                    Settings.System.USER_ROTATION, rotation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetRotation() {
        try {
            Settings.System.putInt(getContentResolver(),
                    Settings.System.ACCELEROMETER_ROTATION, 1);

            Settings.System.putInt(getContentResolver(),
                    Settings.System.USER_ROTATION, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
