package com.example.username.myslideshow;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ImageSwitcher mImageSwitcher;

    int[] mImageResources = {R.drawable.slide00,R.drawable.slide01
            ,R.drawable.slide02,R.drawable.slide03
            ,R.drawable.slide04,R.drawable.slide05
            ,R.drawable.slide06,R.drawable.slide07
            ,R.drawable.slide08,R.drawable.slide09};

    int mPosition = 0;

    boolean mIsSlideshow = false;

    MediaPlayer mMediaPlayer;

    public class MainTimerTask extends TimerTask {
        @Override
        public void run() {
            if(mIsSlideshow) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        movePosition(1);
                    }
                });
            }
        }
    }
    Timer mTimer = new Timer();
    TimerTask mTimerTask = new MainTimerTask();
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        mImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayoutParams(
                        new ImageSwitcher.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                return imageView;
            }
        });

        mTimer.schedule(mTimerTask, 0, 5000);

        mMediaPlayer =  MediaPlayer.create(this, R.raw.getdown);
        mMediaPlayer.setLooping(true);
    }
    public void onAnimationButtonTapped(View view) {
        // 5回転します
        //view.animate().setDuration(3000).rotation(360.0f * 5.0f);
        // x座標200の位置に移動
        //view.animate().setDuration(3000).x(200.0f);
        // y座標200の位置に移動
        //view.animate().setDuration(3000).y(200.0f);
        // 2.5倍のサイズにします
        //view.animate().setDuration(3000).scaleX(2.5f).scaleY(2.5f);
        // 透過度を50%に変更します
        //view.animate().setDuration(1000).alpha(0.0f);
        // ボールが跳ねるような効果を加えます
        view.animate().setDuration(1000).setInterpolator(new BounceInterpolator()).y(300.0f);
    }

    private void movePosition(int move) {
        mPosition = mPosition + move;
        if (mPosition >= mImageResources.length) {
            mPosition = 0;
        } else if (mPosition < 0) {
            mPosition = mImageResources.length - 1;
        }
        mImageSwitcher.setImageResource(mImageResources[mPosition]);
    }

    public void onPrevButtonTapped(View view) {
        mImageSwitcher.setInAnimation(this,android.R.anim.fade_in);
        mImageSwitcher.setOutAnimation(this, android.R.anim.fade_out);
        movePosition(-1);
    }
    public void onNextButtonTapped(View view) {
        mImageSwitcher.setInAnimation(this,android.R.anim.slide_in_left);
        mImageSwitcher.setOutAnimation(this,android.R.anim.slide_out_right);
        movePosition(1);
    }
    public void onSlideshowButtonTapped(View view) {
        mIsSlideshow = !mIsSlideshow;

        if(mIsSlideshow) {
            mMediaPlayer.start();
        } else {
            mMediaPlayer.pause();
            mMediaPlayer.seekTo(0);
        }

    }

}
