package kr.tangomike.sima20161114_lub;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * Created by shimaz on 2016-11-09.
 * shimaz.bsh@gmail.com
 */

public class VideoActivity extends Activity implements Runnable{

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

    private TextView totalTime;
    private TextView nowTime;

    private int videoDuration;

    @Override
    public void onCreate(Bundle sis){
        super.onCreate(sis);
        super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        registerReceiver(mReceiver, mFilter);
        apm = (ApplicationManager)getApplicationContext();

        setContentView(R.layout.layout_video);


        rlMain = (RelativeLayout)findViewById(R.id.rl_main);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        sbAudio = (SeekBar)findViewById(R.id.sb_audio);
        sbVolume = (SeekBar)findViewById(R.id.sb_volume);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        sbAudio.setProgressDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.media_progress, null));
        sbAudio.setThumb(ResourcesCompat.getDrawable(getResources(), R.drawable.thumb_audio, null));
        sbVolume.setProgressDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.media_progress, null));
        sbVolume.setThumb(ResourcesCompat.getDrawable(getResources(), R.drawable.thumb_volume, null));


        ivBG = (ImageView)findViewById(R.id.iv_video_bg);

        Intent intent = getIntent();
        int vn = intent.getIntExtra("videoNumber", 0);

        vv = new VideoView(this);

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
                    apm.startTimer();
                    btnPlayPause.setBackgroundResource(R.drawable.audiobook_btn_play);
                }else{
                    vv.start();
                    apm.pauseTimer();
                    btnPlayPause.setBackgroundResource(R.drawable.audiobook_btn_pause);
                }

            }
        });

        rlMain.addView(btnPlayPause);




        totalTime = new TextView(this);
        totalTime.setLayoutParams(lp);

        totalTime.setTextColor(Color.rgb(255, 227, 148));
        totalTime.setTextSize(20);
        totalTime.setX(608);


        rlMain.addView(totalTime);


        nowTime = new TextView(this);
        nowTime.setLayoutParams(lp);
        nowTime.setText("00:00");
        nowTime.setTextColor(Color.rgb(255, 227, 148));
        nowTime.setTextSize(20);
        nowTime.setX(108);


        rlMain.addView(nowTime);



        ImageView ivTop = (ImageView)findViewById(R.id.iv_video_top);


        String path = "android.resource://" + getPackageName() + "/";
        if(vn == 1){

            ivTop.setBackgroundResource(R.drawable.video_top_title_img);
            ivBG.setBackgroundResource(R.drawable.video_1_media_video_img_bg);
            sbAudio.setY(sbAudio.getY() + 80);
            sbVolume.setY(sbVolume.getY() + 80);
            btnPlayPause.setY(btnPlayPause.getY() + 80);
            vv.setLayoutParams(new ViewGroup.LayoutParams(768, 576));
            vv.setX(0);
            vv.setY(230);

            path += "raw/video_1_mov";
            totalTime.setY(954);
            nowTime.setY(954);

        }else if(vn == 2){

            ivTop.setBackgroundResource(R.drawable.video_top_title_img);
            ivBG.setBackgroundResource(R.drawable.video_2_media_video_img_bg);
            sbAudio.setY(sbAudio.getY() + 80);
            sbVolume.setY(sbVolume.getY() + 80);
            btnPlayPause.setY(btnPlayPause.getY() + 80);
            vv.setLayoutParams(new ViewGroup.LayoutParams(768, 576));
            vv.setX(0);
            vv.setY(230);
            totalTime.setY(954);
            nowTime.setY(954);


            path += "raw/video_2_mov";

        }else{

            ivTop.setBackgroundResource(R.drawable.interview_top_title_img);
            ivBG.setBackgroundResource(R.drawable.interview_media_video_img_bg);
            sbAudio.setY(sbAudio.getY() + 40);
            sbVolume.setY(sbVolume.getY() + 40);
            btnPlayPause.setY(btnPlayPause.getY() + 40);
            vv.setLayoutParams(new ViewGroup.LayoutParams(768, 432));
            vv.setX(0);
            vv.setY(256);

            totalTime.setY(914);
            nowTime.setY(914);

            path += "raw/interview";

        }



        Button btnBack = (Button)findViewById(R.id.btn_video_back);
        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                apm.resetTimer();

                finish();

                overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);

            }
        });


        rlMain.addView(vv);

        vv.setVideoURI(Uri.parse(path));


        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoDuration = vv.getDuration();
                setupLeftovers();
                apm.pauseTimer();
            }
        });

        vv.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                return false;
            }
        });

        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                sbAudio.setProgress(0);
                btnPlayPause.setBackgroundResource(R.drawable.audiobook_btn_play);
                apm.startTimer();
            }
        });


        vv.start();
    }


    private void setupLeftovers(){


        am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);


        sbAudio.setOnSeekBarChangeListener(onSeekAudio);

        sbVolume.setMax(am.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        sbVolume.setProgress(am.getStreamVolume(AudioManager.STREAM_MUSIC));

        sbVolume.setOnSeekBarChangeListener(onSeekVolume);

        sbAudio.setMax(videoDuration);


        totalTime.setText(getTimeString(sbAudio.getMax()));

        new Thread(VideoActivity.this).start();
    }


    SeekBar.OnSeekBarChangeListener onSeekAudio = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(fromUser) vv.seekTo(progress);
            sbAudio.setProgress(progress);
            nowTime.setText(getTimeString(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

            if(vv.isPlaying()) vv.pause();

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

            vv.start();
        }
    };

    SeekBar.OnSeekBarChangeListener onSeekVolume = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            am.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };


    private String getTimeString(int time){

        float tmp = time / 1000;
        int origin = Math.round(tmp);

        int second = origin % 60;
        int minute = origin / 60;

        String retVal = String.format("%02d:%02d", minute, second);

        android.util.Log.i("shimaz", "" + time);

        return retVal;



    }

    @Override
    public void run() {
        int current;
        while(vv != null)
        {
            try{
                Thread.sleep(1000);
                current = vv.getCurrentPosition();
                if(vv.isPlaying()){
                    apm.resetTimer();
                    sbAudio.setProgress(current);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
//        apm.setAnimating(false);
        apm.stopBGM();
    }

    @Override
    protected void onDestroy(){

        if(vv !=null){
            if(vv.isPlaying()) vv.stopPlayback();
            vv = null;
        }

        unregisterReceiver(mReceiver);

        apm.playBGM();

        super.onDestroy();

    }

}
