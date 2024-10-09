package com.example.dcs_stopwatchapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;

public class CaptureActivity extends AppCompatActivity
{
    Button btnCam;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_capture);

        btnCam = (Button) findViewById(R.id.btnCam);

        btnCam.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent cam = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(cam,1);
            }
        });
    }

    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode==1 && resultCode==RESULT_OK)
        {
            Bitmap bm = (Bitmap)data.getExtras().get("data");
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG,100,bout);
            byte[] barray = bout.toByteArray();

            Intent intent = new Intent(CaptureActivity.this,TImerActivity.class);
            intent.putExtra("image",barray);
            startActivity(intent);
        }
    }
}