package kr.tangomike.sima20161114_lub;

import android.app.Application;

import java.lang.reflect.Array;

/**
 * Created by shimaz on 2016-11-07.
 */

public class ApplicationManager extends Application {


    private int[] pageCount;
    private float[] scales;

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


        scales[0] = 1 / 0.3433f;
        scales[1] = 1 / 0.5378f;
        scales[2] = 1 / 0.5267f;
        scales[3] = 1 / 0.5431f;
        scales[4] = 1 / 0.4455f;
        scales[5] = 1 / 0.4068f;
        scales[6] = 1 / 0.5899f;
        scales[7] = 1 / 0.3798f;
        scales[8] = 1 / 0.5863f;
        scales[9] = 1 / 0.4504f;
        scales[10] = 1 / 0.3988f;



    }

    public void resetTimer(){

    }


    public void pauseTimer(){

    }

    public void resumeTimer(){

    }

    public void playBGM(){

    }


    public void stopBGM(){

    }

    public float getScale(int bookNum){

        return scales[bookNum];
    }


    public int getPageCount(int bookNum){

        return pageCount[bookNum];

    }
}
