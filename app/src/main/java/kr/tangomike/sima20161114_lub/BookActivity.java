package kr.tangomike.sima20161114_lub;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private ViewPager pager;





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
        pager = (ViewPager)findViewById(R.id.pager_book);





    }





    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }



    public class BookViewAdapter extends PagerAdapter{
        LayoutInflater lf;
        int bookNumber;
        BookData bookData;

        public BookViewAdapter(LayoutInflater inflater, int bookNum, BookData bookData){
            lf = inflater;
            bookNumber = bookNum;
            this.bookData = bookData;
        }


        @Override
        public int getCount(){

            return bookData.pageCount;

        }

        @Override
        public void destroyItem(ViewGroup container, int arg1, Object arg2){

            container.removeView((View)arg2);

        }

        public Object instantiateItem(ViewGroup container, int position){

            View view;

            view = lf.inflate(R.layout.layout_pager_book, null);





            container.addView(view);

            return view;
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
