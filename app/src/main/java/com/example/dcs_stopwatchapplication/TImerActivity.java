package com.example.dcs_stopwatchapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TImerActivity extends AppCompatActivity
{
    ImageView ivTakenPic,ivBack;
    TextView tvHr,tvMin,tvSec;
    Button btnStart,btnStop,btnReset;
    boolean isRunning = false;
    Thread t;

    class StopWatch implements Runnable
    {
        int sec,min,hr;
        StopWatch(int sec,int min, int hr)
        {
            this.sec=sec;
            this.min=min;
            this.hr=hr;
        }
        public void run()
        {
            isRunning = true;
            try
            {
                while (isRunning)
                {
                    sec++;
                    if (sec==60)
                    {
                        min++;
                        sec=0;
                        if (min==60)
                        {
                            hr++;
                            min=0;
                        }
                    }
                    Thread.sleep(1000);
                    runOnUiThread(()->{
                            tvSec.setText(String.format("%02d", sec));
                            tvMin.setText(String.format("%02d", min));
                            tvHr.setText(String.format("%02d", hr));

                    });
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_timer);

        ivTakenPic = (ImageView) findViewById(R.id.ivTakenPic);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        tvHr = (TextView) findViewById(R.id.tvHr);
        tvMin = (TextView) findViewById(R.id.tvMin);
        tvSec = (TextView) findViewById(R.id.tvSec);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnReset = (Button) findViewById(R.id.btnReset);

        byte[] barray = getIntent().getByteArrayExtra("image");
        Bitmap bm = BitmapFactory.decodeByteArray(barray,0,barray.length);

        ivTakenPic.setImageBitmap(bm);

        ivBack.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                startActivity(new Intent(TImerActivity.this,CaptureActivity.class));
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                isRunning = true;
                t = new Thread(new StopWatch(Integer.parseInt(tvSec.getText().toString()), Integer.parseInt(tvMin.getText().toString()), Integer.parseInt(tvHr.getText().toString())));
                t.start();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                isRunning = false;
                if (t != null) {
                    t.interrupt();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                isRunning = false;
                if (t != null) {
                    t.interrupt();
                }
                tvSec.setText("00");
                tvMin.setText("00");
                tvHr.setText("00");
            }
        });
    }
}