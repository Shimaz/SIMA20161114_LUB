package kr.tangomike.sima20161114_lub;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by shimaz on 2016-11-09.
 * shimaz.bsh@gmail.com
 */

public class VideoSelectActivity extends Activity {


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
        setContentView(R.layout.layout_video_select);
        super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        registerReceiver(mReceiver, mFilter);

        apm = (ApplicationManager)getApplicationContext();


        Button btnBack = (Button)findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                apm.resetTimer();

                finish();
                overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);

            }
        });


        Button btn1 = (Button)findViewById(R.id.btn_list_1);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                apm.resetTimer();;

                Intent intent = new Intent(VideoSelectActivity.this, VideoActivity.class);
                intent.putExtra("videoNumber", 1);

                startActivity(intent);

                overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);

            }
        });


        Button btn2 = (Button)findViewById(R.id.btn_list_2);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                apm.resetTimer();;

                Intent intent = new Intent(VideoSelectActivity.this, VideoActivity.class);
                intent.putExtra("videoNumber", 2);

                startActivity(intent);

                overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
            }
        });


    }



    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(mReceiver);

    }

}
