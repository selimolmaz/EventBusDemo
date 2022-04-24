package com.example.eventbusdouble;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    Button btnClick,btnClick1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnClick1 = findViewById(R.id.btnClick1);
        btnClick1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new Posting());
            }
        });
        btnClick = findViewById(R.id.btnClick);
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call eventBus when click button
                EventBus.getDefault().post(new MessageToastEvent("Button clicked"));
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventToast(MessageToastEvent messageToastEvent) {
        Toast.makeText(MainActivity.this, messageToastEvent.message, Toast.LENGTH_LONG).show();
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void eventToast(Posting post) {
        post.run();
        Toast.makeText(MainActivity.this, "post yaptim!!!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}