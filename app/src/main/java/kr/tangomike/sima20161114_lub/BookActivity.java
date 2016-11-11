package kr.tangomike.sima20161114_lub;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by shimaz on 2016-11-10.
 */

public class BookActivity extends Activity {
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            finish();

        }
    };

    private IntentFilter mFilter = new IntentFilter("shimaz.restart");


    private ApplicationManager apm;

    private int bookNumber;
    private TextView tvPage;


    @Override
    public void onCreate(Bundle sis){
        super.onCreate(sis);
        super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        registerReceiver(mReceiver, mFilter);
        apm = (ApplicationManager)getApplicationContext();
        setContentView(R.layout.layout_book);

        bookNumber = getIntent().getIntExtra("bookNumber", 0);



        String strTitle = "book_top_title_" + bookNumber + "_img";
        ImageView ivTitle = (ImageView)findViewById(R.id.iv_book_title);
        ivTitle.setImageResource(getResources().getIdentifier(strTitle, "drawable", getPackageName()));


        tvPage = (TextView)findViewById(R.id.tv_page);




    }





    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }




}
