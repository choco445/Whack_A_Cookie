package com.example.whackaberrykin;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    ImageView cookieBrown;
    ImageView cookieChocoChips;
    ImageView cookiePink;
    ImageView cookieRainbow;
    ImageView cookieWhite;
    ImageView cookieDark;
    TextView timerText;
    TextView points;
    ConstraintLayout layout;
    GridLayout gridLay;
    Button restart;
    int score = 0;
    AtomicInteger count;
    int rand;
    int pause;
    Runnable r;

    Handler timing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerText = findViewById(R.id.id_timer);
        points = findViewById(R.id.textView2);
        cookieChocoChips = findViewById(R.id.id_cookieChocoChips);
        cookiePink = findViewById(R.id.id_cookiePink);
        cookieRainbow = findViewById(R.id.id_cookieRainbow);
        cookieWhite = findViewById(R.id.id_cookieWhite);
        cookieDark = findViewById(R.id.id_cookieDark);
        cookieBrown = findViewById(R.id.id_cookieBrown);
        layout = findViewById(R.id.id_layout);
        restart = findViewById(R.id.id_restart);
        gridLay = findViewById(R.id.id_gridLayout);

        count = new AtomicInteger(30);

        final ScaleAnimation animateappear = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        final ScaleAnimation animatedisappear = new ScaleAnimation(1f, 0f, 1f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animateappear.setDuration(1500);
        animatedisappear.setStartOffset(5000);
        animatedisappear.setDuration(1500);
        AnimationSet as = new AnimationSet(true);
        as.addAnimation(animateappear);
        as.addAnimation(animatedisappear);

        timing = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                //animating();
                // if (count > 0) {
                rand = (int) (Math.random() * 6) + 1; // 1,2,3,4,5,6
                pause = (int) (Math.random() * 2000) + 500; //500-2000
                int status = (int)(Math.random()*9)+1;

                if (rand == 6 && cookieBrown.getScaleX() == 0 && cookieBrown.getScaleY() == 0) {
                    scaleCookie(cookieBrown, true, true,status); //meaning it's starting from 0 and scaling up and it's clickable
                    timing.postDelayed(() -> scaleCookie(cookieBrown, false, true,status), 2000);
                }
                if (rand == 1 && cookieDark.getScaleX() == 0 && cookieDark.getScaleY() == 0) {
                    scaleCookie(cookieDark, true, true,status);
                    timing.postDelayed(() -> scaleCookie(cookieDark, false, true,status), 2000);
                }
                if (rand == 2 && cookiePink.getScaleX() == 0 && cookiePink.getScaleY() == 0) {
                    scaleCookie(cookiePink, true, true,status);
                    timing.postDelayed(() -> scaleCookie(cookiePink, false, true,status), 2000);
                }

                if (rand == 3 && cookieRainbow.getScaleX() == 0 && cookieRainbow.getScaleY() == 0) {
                    scaleCookie(cookieRainbow, true, true,status);
                    timing.postDelayed(() -> scaleCookie(cookieRainbow, false, true,status), 2000);
                }
                if (rand == 4 && cookieWhite.getScaleX() == 0 && cookieWhite.getScaleY() == 0) {
                    scaleCookie(cookieWhite, true, true,status);
                    timing.postDelayed(() -> scaleCookie(cookieWhite, false, true,status), 2000);
                }
                if (rand == 5 && cookieChocoChips.getScaleX() == 0 && cookieChocoChips.getScaleY() == 0) {
                    scaleCookie(cookieChocoChips, true, true,status);
                    timing.postDelayed(() -> scaleCookie(cookieChocoChips, false, true,status), 2000);
                }
                if (count.get()> 2) {
                    pause = (int) (Math.random() * 2000) + 500; //500-2000
                    timing.postDelayed(this, pause);
                }
            }
        };

        Toast message = Toast.makeText(this,"Game Over! Click restart to play again!",Toast.LENGTH_SHORT);


        Thread thread2 = new Thread(new Runnable()
                //this manages cookies appearring/disappearring + points
        {
            public void run() {
                timing.postDelayed(r, 0);
            }
        });

        Thread thread = new Thread(new Runnable() {
            //this manages the timer - counts down from 60 to 0 + says game over
            @Override
            public void run() {
                while (count.get() >= 0) {
                    if (count.get() > 0) {
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        count.getAndDecrement();
                        timerText.setText("" + count);
                    } else if (count.get() == 0) {
                        timerText.setText("Time's up!");
                         //Toast message = new Toast(MainActivity.this);
                        //message.setText("Game Over! Click restart to play again!");
                        message.show();
                        try {
                            sleep(2000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });

        thread.start();
        thread2.start();

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count.set(30);
                score = 0;
                timerText.setText("30");
                points.setText("0");
                timing.postDelayed(r, 1000);
                gridLay.removeAllViews();
            }
        });
    }

    public void scaleCookie(ImageView img, boolean scaleUp, boolean isClickable, int num) {
        if(num==6)
        {
            img.setImageResource(R.drawable.cookiepower);
        }
        float startScale = scaleUp ? 0f : 1f;
        //if scaleUp is true then start at 0f, else start at 1f
        float endScale = scaleUp ? 1f : 0f;
        //this makes the end scale 1 if scaleUp is true and 0 if false
        img.animate().scaleX(endScale).scaleY(endScale).setDuration(1000).withEndAction(() ->
        {
            if (!scaleUp) {
                img.setEnabled(true);
            }
        });
        if (isClickable) {

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(num==6)
                    {
                        count.addAndGet(5);
                        timerText.setText(""+count.get());
                    }
                    score++;
                    points.setText("" + score);
                    v.setEnabled(false);
                    //this makes it go down after getting clicked
                    scaleCookie(img, false, false,num);
                    addWidget(img);
                }
            });
        }
    }

    public void addWidget(ImageView image) {
        ImageView cookie = new ImageView(this);
        cookie.setImageDrawable(image.getDrawable());
        gridLay.addView(cookie);

        cookie.getLayoutParams().height = 100;
        cookie.getLayoutParams().width = 100;
    }
}