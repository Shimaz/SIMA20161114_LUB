package kr.tangomike.sima20161114_lub;

import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by shimaz on 2016-11-09.
 * shimaz.bsh@gmail.com
 */

public class NoteActivity extends Activity {

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            finish();

        }
    };

    private IntentFilter mFilter = new IntentFilter("shimaz.restart");


    private ApplicationManager apm;

    @Override
    public void onCreate(Bundle sis){
        super.onCreate(sis);

        super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        registerReceiver(mReceiver, mFilter);
        apm = (ApplicationManager)getApplicationContext();

        setContentView(R.layout.layout_note);

        int noteNumber = getIntent().getIntExtra("noteNumber", 0);

        Button btnBack = (Button)findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                apm.resetTimer();

                finish();
                overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);

            }
        });

        String strTitleName = "note_title_" + noteNumber + "_img";

        ImageView ivTitle = (ImageView)findViewById(R.id.iv_note_title);
        ivTitle.setImageResource(getResources().getIdentifier(strTitleName, "drawable", getPackageName()));



        LinearLayout lrText = (LinearLayout)findViewById(R.id.lr_text);

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        int nn = 1;
        while (true){
            int rID = getResources().getIdentifier("note_title_" + noteNumber + "_text_img_" + nn, "drawable", getPackageName());

            if (rID == 0) break;

            ImageView iv = new ImageView(this);
            iv.setLayoutParams(lp);
            iv.setImageResource(rID);

            nn++;

            lrText.addView(iv);
        }

        lrText.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                apm.resetTimer();

                return false;
            }

        });



    }


    @Override
    public void onDestroy(){
        unregisterReceiver(mReceiver);
        super.onDestroy();

    }



}
