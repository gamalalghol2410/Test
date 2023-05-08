package com.azharcis.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

import com.azharcis.test.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ArrayList<Integer> colors;
    Message message;
    int numOfFingerTouch=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.background.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getActionMasked()) {

                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                        numOfFingerTouch=event.getPointerCount();
                        binding.num.setText(numOfFingerTouch+"");

                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                        numOfFingerTouch=0;
                        break;
                }
                return true;
            }
        });
        colors=new ArrayList<>();
        colors.add(R.color.red);
        colors.add(R.color.green);
        colors.add(R.color.blue);
        colors.add(R.color.pink);
        colors.add(R.color.yallow);
        colors.add(R.color.white);


        MyHandler handler=new MyHandler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<colors.size();i++) {
                    message=new Message();
                    message.arg1=i;
                    handler.sendMessage(message);
                    try {
                        // binding.background.setBackgroundColor(getColor(colors.get(i)));
                        Thread.sleep(2000);

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }).start();
    }
    public class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            binding.background.setBackgroundColor(getColor(colors.get(msg.arg1)));

        }
    }
}