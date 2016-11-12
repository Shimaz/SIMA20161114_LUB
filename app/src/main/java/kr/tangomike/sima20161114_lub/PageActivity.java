package kr.tangomike.sima20161114_lub;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by shimaz on 2016-11-11.
 */

public class PageActivity extends Activity {
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            finish();

        }
    };

    private IntentFilter mFilter = new IntentFilter("shimaz.restart");


    private ApplicationManager apm;


    private int bookNumber;
    private int pageNumber;

    private TextView tvPage;

    private boolean isLoading;

    private RelativeLayout rlPage;


    private PhotoViewAttacher pva;

    @Override
    public void onCreate(Bundle sis){
        super.onCreate(sis);
        super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        registerReceiver(mReceiver, mFilter);
        apm = (ApplicationManager)getApplicationContext();
        bookNumber = getIntent().getIntExtra("bookNumber", 0);
        pageNumber = getIntent().getIntExtra("pageNumber", 0);

        isLoading = false;

        setContentView(R.layout.laytou_page);

        Button btnBack = (Button)findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
                overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
            }
        });

        Button btnPrev = (Button)findViewById(R.id.btn_booK_prev);
        btnPrev.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                apm.resetTimer();

                if(!isLoading && pageNumber != 1){

                    isLoading = true;

                    Intent intent = new Intent(PageActivity.this, PageActivity.class);
                    intent.putExtra("bookNumber", bookNumber);
                    intent.putExtra("pageNumber", pageNumber - 1);

                    startActivity(intent);

                    overridePendingTransition(R.anim.page_fade_in, R.anim.page_fade_out);

                    finish();

                }


            }
        });

        final int nextRID = getResources().getIdentifier("book_" + bookNumber + "_img_" +  (pageNumber+1), "drawable", getPackageName());

        Button btnNext = (Button)findViewById(R.id.btn_book_next);
        btnNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                apm.resetTimer();

                if(!isLoading && nextRID != 0){

                    isLoading = true;

                    Intent intent = new Intent(PageActivity.this, PageActivity.class);
                    intent.putExtra("bookNumber", bookNumber);
                    intent.putExtra("pageNumber", pageNumber + 1);

                    startActivity(intent);

                    overridePendingTransition(R.anim.page_fade_in, R.anim.page_fade_out);

                    finish();

                }


            }
        });


        if(pageNumber == 1){
            btnPrev.setAlpha(0.5f);
        }

        if(nextRID == 0) {

            btnNext.setAlpha(0.5f);

        }


        String strImageName = "book_" + bookNumber + "_img_" + pageNumber;

        ImageView ivPage = (ImageView)findViewById(R.id.iv_page);

        ivPage.setImageResource(getResources().getIdentifier(strImageName, "drawable", getPackageName()));






        tvPage = (TextView)findViewById(R.id.tv_page);

        tvPage.setText(pageNumber + " / " + apm.getPageCount(bookNumber));

        Typeface tf = Typeface.createFromAsset(getAssets(), "bmdohyun_otf.otf");
        tvPage.setTypeface(tf);

        String strTitle = "book_top_title_" + bookNumber + "_img";
        ImageView ivTitle = (ImageView)findViewById(R.id.iv_book_title);
        ivTitle.setImageResource(getResources().getIdentifier(strTitle, "drawable", getPackageName()));



        pva = new PhotoViewAttacher(ivPage);
        pva.setMaximumScale(apm.getScale(bookNumber - 1));
        pva.update();

        pva.setScale(apm.getScale(bookNumber - 1), true);

        rlPage = (RelativeLayout)findViewById(R.id.rl_page);
        rlPage.setAlpha(1.0f);
    }

    @Override
    public void onResume(){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in_delay);
        rlPage.startAnimation(animation);
        super.onResume();




    }



    @Override
    public void onDestroy(){

        unregisterReceiver(mReceiver);
        super.onDestroy();


    }

}
