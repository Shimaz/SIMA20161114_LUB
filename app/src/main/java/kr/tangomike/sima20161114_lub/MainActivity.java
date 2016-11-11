package kr.tangomike.sima20161114_lub;

import android.app.Activity;
//import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private ApplicationManager apm;

    private ViewPager viewPager;

    private Button btnPrev;
    private Button btnNext;
    private Button btnBook;
    private Button btnNote;
    private Button btnInterview;
    private Button btnVideo;

    private boolean isAnimating;

    private boolean isInit;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            resetStatus();

        }
    };

    private IntentFilter mFilter = new IntentFilter("shimaz.restart");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.layout_main);

        registerReceiver(mReceiver, mFilter);

        isAnimating = false;
        isInit = true;
        apm = (ApplicationManager)getApplicationContext();


        viewPager = (ViewPager)findViewById(R.id.pager_main);

        final MainPageAdapter adapter = new MainPageAdapter(getLayoutInflater());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if(!isInit){
                    dimButtons();
                    isAnimating = true;
                }

                isInit = false;

            }

            @Override
            public void onPageSelected(int position) {
                apm.resetTimer();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

                if(state == 0){
                    isAnimating = false;
                    setButtonStatus();
                    unDimButtons();

                }

                android.util.Log.i("shimaz", "" + state);

            }
        });

        btnNext = (Button)findViewById(R.id.btn_main_next);
        btnNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                apm.resetTimer();
                if(viewPager.getCurrentItem() + 1 != adapter.getCount()){
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
            }


        });


        btnPrev = (Button)findViewById(R.id.btn_main_prev);
        btnPrev.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                apm.resetTimer();

                if(viewPager.getCurrentItem() != 0){

                    viewPager.setCurrentItem((viewPager.getCurrentItem() - 1));

                }
            }
        });


        btnBook = (Button)findViewById(R.id.btn_open_book);
        btnBook.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                apm.resetTimer();
                if(!isAnimating){
                    int bookNumber = viewPager.getCurrentItem() + 1;
                    Intent intent = new Intent(MainActivity.this, BookActivity.class);
                    intent.putExtra("bookNumber", bookNumber);

                    startActivity(intent);

                    overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
                }
            }
        });


        btnNote = (Button)findViewById(R.id.btn_open_note);
        btnNote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                apm.resetTimer();

                if(!isAnimating){

                    if(viewPager.getCurrentItem() == 2 || viewPager.getCurrentItem() == 5 || viewPager.getCurrentItem() == 8){

                    }else{
                        int noteNumber = viewPager.getCurrentItem() + 1;

                        Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                        intent.putExtra("noteNumber", noteNumber);
                        startActivity(intent);

                        overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);

                    }


                }

            }
        });


        btnInterview = (Button)findViewById(R.id.btn_open_interview);
        btnInterview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                apm.resetTimer();
                if(!isAnimating){

                    Intent intent = new Intent(MainActivity.this, VideoActivity.class);
                    intent.putExtra("videoNumber", 3);
                    startActivity(intent);

                    overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);

                }



            }



        });


        btnVideo = (Button)findViewById(R.id.btn_open_video);
        btnVideo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                apm.resetTimer();
                if(!isAnimating) {


                    Intent intent = new Intent(MainActivity.this, VideoSelectActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);


                }


            }
        });



    }

    @Override
    public void onResume(){
        super.onResume();
        isAnimating = false;
        setButtonStatus();
        unDimButtons();

    }

    private void setButtonStatus(){
        if(viewPager.getCurrentItem() == 2 || viewPager.getCurrentItem() == 5 || viewPager.getCurrentItem() == 8){
            btnNote.setBackgroundResource(R.drawable.main_note_btn_disabled);

        }else{
            btnNote.setBackgroundResource(R.drawable.main_note_btn);
        }

        if(viewPager.getCurrentItem() == 0){
            btnPrev.setVisibility(View.INVISIBLE);
        }else if(viewPager.getCurrentItem() == 10){
            btnNext.setVisibility(View.INVISIBLE);
        }else{
            btnPrev.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
        }

    }

    private void dimButtons(){
        btnNext.setAlpha(0.5f);
        btnPrev.setAlpha(0.5f);
        btnBook.setAlpha(0.5f);
        btnNext.setAlpha(0.5f);
        btnNote.setAlpha(0.5f);

    }

    private void unDimButtons(){
        btnNext.setAlpha(1);
        btnPrev.setAlpha(1);
        btnBook.setAlpha(1);
        btnNext.setAlpha(1);
        btnNote.setAlpha(1);

    }



    private void resetStatus(){

    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }


    public class MainPageAdapter extends PagerAdapter{

        private LayoutInflater lf;

        public MainPageAdapter(LayoutInflater inflater){
            this.lf = inflater;
        }


        @Override
        public int getCount(){
            return 11;
        }

        public Object instantiateItem(ViewGroup container, int position){
            View view;
            view = lf.inflate(R.layout.layout_pager_main, null);
            ImageView ivPager = (ImageView)view.findViewById(R.id.iv_main_pager);
            String strImgName = "main_book_" + (position + 1) + "_img";
            ivPager.setImageResource(getResources().getIdentifier(strImgName, "drawable", getPackageName()));

            container.addView(view);

            return view;

        }


        @Override
        public void destroyItem(ViewGroup container, int arg1, Object arg2){

            container.removeView((View)arg2);

        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);

        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {

            return arg0 == ((View) arg1);


        }

        @Override
        public Parcelable saveState() {
            return null;
        }

    }

}