package com.midterm.pbl5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        ledView = (ImageView) findViewById(R.id.ledImg);
// Set default image
        ledView.setImageResource(R.drawable.white_circle);

// Init HTTP client
        client = new HttpClient();

        ledView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.doRrequest(status ? "1" : "0");
                status = !status;
                if (status)
                    ledView.setImageResource(R.drawable.white_circle);
                else
                    ledView.setImageResource(R.drawable.red_circle);

            }
        });
    }
}