package kr.tangomike.sima20161114_lub;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.VideoView;

/**
 * Created by shimaz on 2016-11-09.
 * shimaz.bsh@gmail.com
 */

public class VideoActivity extends Activity {

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            finish();

        }
    };

    private IntentFilter mFilter = new IntentFilter("shimaz.restart");



    private RelativeLayout rlMain;

    private VideoView vv;
    private AudioManager am;

    private Button btnPlayPause;
    private SeekBar sbAudio;
    private SeekBar sbVolume;
    private ApplicationManager apm;

    private ImageView ivBG;

    @Override
    public void onCreate(Bundle sis){
        super.onCreate(sis);
        registerReceiver(mReceiver, mFilter);
        apm = (ApplicationManager)getApplicationContext();

        setContentView(R.layout.layout_video);


        rlMain = (RelativeLayout)findViewById(R.id.rl_main);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        sbAudio = (SeekBar)findViewById(R.id.sb_audio);
        sbVolume = (SeekBar)findViewById(R.id.sb_volume);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);


        ivBG = (ImageView)findViewById(R.id.iv_video_bg);

        Intent intent = getIntent();
        int vn = intent.getIntExtra("videoNumber", 0);


        btnPlayPause = new Button(this);
        btnPlayPause.setBackgroundResource(R.drawable.audiobook_btn_pause);
        btnPlayPause.setLayoutParams(lp);
        btnPlayPause.setX(118);
        btnPlayPause.setY(767);
        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(vv.isPlaying()){
                    vv.pause();
                    btnPlayPause.setBackgroundResource(R.drawable.audiobook_btn_play);
                }else{
                    vv.start();
                    btnPlayPause.setBackgroundResource(R.drawable.audiobook_btn_pause);
                }

            }
        });

        rlMain.addView(btnPlayPause);



        ImageView ivTop = (ImageView)findViewById(R.id.iv_video_top);


        if(vn == 1){

            ivTop.setBackgroundResource(R.drawable.video_top_title_img);
            ivBG.setBackgroundResource(R.drawable.video_1_media_video_img_bg);
            sbAudio.setY(sbAudio.getY() + 80);
            sbVolume.setY(sbVolume.getY() + 80);
            btnPlayPause.setY(btnPlayPause.getY() + 80);

        }else if(vn == 2){

            ivTop.setBackgroundResource(R.drawable.video_top_title_img);
            ivBG.setBackgroundResource(R.drawable.video_2_media_video_img_bg);
            sbAudio.setY(sbAudio.getY() + 80);
            sbVolume.setY(sbVolume.getY() + 80);
            btnPlayPause.setY(btnPlayPause.getY() + 80);

        }else{

            ivTop.setBackgroundResource(R.drawable.interview_top_title_img);
            ivBG.setBackgroundResource(R.drawable.interview_media_video_img_bg);
            sbAudio.setY(sbAudio.getY() + 40);
            sbVolume.setY(sbVolume.getY() + 40);
            btnPlayPause.setY(btnPlayPause.getY() + 40);

        }

        am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        sbAudio.setProgressDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.media_progress, null));
        sbAudio.setThumb(ResourcesCompat.getDrawable(getResources(), R.drawable.thumb_audio, null));

        sbVolume.setMax(am.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        sbVolume.setProgress(am.getStreamVolume(AudioManager.STREAM_MUSIC));
        sbVolume.setProgressDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.media_progress, null));
        sbVolume.setThumb(ResourcesCompat.getDrawable(getResources(), R.drawable.thumb_volume, null));

        Button btnBack = (Button)findViewById(R.id.btn_video_back);
        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                apm.resetTimer();

                finish();

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
