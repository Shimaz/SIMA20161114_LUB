package kr.tangomike.sima20161114_lub;

import android.app.Application;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;

import java.lang.reflect.Array;

/**
 * Created by shimaz on 2016-11-07.
 */

public class ApplicationManager extends Application {


    private int[] pageCount;
    private float[] scales;

    private Handler mHandler;

    private int tickTime;
    private int TIMER_TIME = 60;
    private boolean isTicking;


    private MediaPlayer bgm;

    public void onCreate(){
        super.onCreate();

        pageCount = new int[11];
        scales = new float[11];

        pageCount[0] = 20;
        pageCount[1] = 17;
        pageCount[2] = 18;
        pageCount[3] = 20;
        pageCount[4] = 16;
        pageCount[5] = 18;
        pageCount[6] = 17;
        pageCount[7] = 12;
        pageCount[8] = 17;
        pageCount[9] = 19;
        pageCount[10] = 17;


        scales[0] = 1 / 0.343163f;
        scales[1] = 1 / 0.537815f;
        scales[2] = 1 / 0.526748f;
        scales[3] = 1 / 0.54314f;
        scales[4] = 1 / 0.445475f;
        scales[5] = 1 / 0.406779f;
        scales[6] = 1 / 0.589861f;
        scales[7] = 1 / 0.379821f;
        scales[8] = 1 / 0.586259f;
        scales[9] = 1 / 0.450439f;
        scales[10] = 1 / 0.398753f;


        tickTime = 0;
        isTicking = false;


        bgm = MediaPlayer.create(getApplicationContext(), R.raw.bgm);
        bgm.setLooping(true);


        mHandler = new Handler(){
            public void handleMessage(Message msg){

                if(isTicking) tickTime++;

                if(tickTime <= TIMER_TIME){

                    mHandler.sendEmptyMessageDelayed(0, 1000);
                    android.util.Log.i("shimaz", "" + tickTime);

                }else if(tickTime > TIMER_TIME){
                    tickTime = 0;
                    isTicking = false;
                    mHandler.removeMessages(0);

                    Intent intent = new Intent("shimaz.restart");

                    sendBroadcast(intent);
                }


            }
        };

    }

    public boolean isTimerTicking(){
        return isTicking;
    }

    public void resetTimer(){

        tickTime = 0;

    }


    public void pauseTimer(){

        isTicking = false;
        mHandler.removeMessages(0);
        tickTime = 0;


    }

    public void startTimer(){

        tickTime = 0;
        isTicking = true;
        mHandler.sendEmptyMessageDelayed(0, 1000);



    }

    public void playBGM(){

        if(!bgm.isPlaying()){
            bgm.start();
        }

    }


    public void stopBGM(){

        if(bgm.isPlaying()){
            bgm.pause();
        }


    }

//    public boolean isBGMPlaying(){
//        return bgm.isPlaying();
//    }

    public float getScale(int bookNum){

        return scales[bookNum];
    }


    public int getPageCount(int bookNum){

        return pageCount[bookNum];

    }
}
