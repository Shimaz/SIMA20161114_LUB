package kr.tangomike.sima20161114_lub;

import android.app.Activity;
//import android.app.Application;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private ApplicationManager apm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.layout_main);
        apm = (ApplicationManager) getApplicationContext();

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
            String strImgName = "mainimg" + (position + 1);
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