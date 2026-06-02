package com.example.rotatetool;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnUpsideDown;
    private Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnUpsideDown = findViewById(R.id.btnUpsideDown);
        btnReset = findViewById(R.id.btnReset);

        // 檢查 WRITE_SETTINGS 權限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                Toast.makeText(this, "請授予修改系統設定的權限", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }
        }

        btnUpsideDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotateUpsideDown();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetRotation();
            }
        });
    }

    /**
     * 使屏幕旋轉180度（倒立）
     */
    private void rotateUpsideDown() {
        try {
            // 禁用自動旋轉
            Settings.System.putInt(getContentResolver(),
                    Settings.System.ACCELEROMETER_ROTATION, 0);

            // 設置旋轉為 180 度
            Settings.System.putInt(getContentResolver(),
                    Settings.System.USER_ROTATION, 2);

            Toast.makeText(this, "屏幕已旋轉180度", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "旋轉失敗：" + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /**
     * 重置屏幕旋轉狀態（恢復正常）
     */
    private void resetRotation() {
        try {
            // 啟用自動旋轉
            Settings.System.putInt(getContentResolver(),
                    Settings.System.ACCELEROMETER_ROTATION, 1);

            // 重置旋轉角度為 0 度
            Settings.System.putInt(getContentResolver(),
                    Settings.System.USER_ROTATION, 0);

            Toast.makeText(this, "屏幕已重置", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "重置失敗：" + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
