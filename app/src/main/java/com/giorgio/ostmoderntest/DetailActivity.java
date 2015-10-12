package com.giorgio.ostmoderntest;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.giorgio.ostmoderntest.adapters.ViewPagerAdapter;
import com.giorgio.ostmoderntest.controllers.SetsController;

import org.json.JSONException;

/**
 * Created by Giorgio on 11/10/15.
 */
public class DetailActivity extends FragmentActivity implements DetailListener{

    ViewPager mViewPager;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        SetsController.getInstance().setDetailListner(this);

        this.mViewPager = (ViewPager) findViewById(R.id.pager);
        this.viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        this.mViewPager.setAdapter(viewPagerAdapter);
        this.mViewPager.setCurrentItem(getIntent().getExtras().getInt("position"));
        DetailOnPageChangeListener detailOnPageChangeListener = new DetailOnPageChangeListener();
        this.mViewPager.addOnPageChangeListener(detailOnPageChangeListener);

        try {
            if (SetsController.getInstance().getSets().get(getIntent().getExtras().getInt("position")).getPhotoArray() != null
                    && SetsController.getInstance().getSets().get(getIntent().getExtras().getInt("position")).getPhotoArray().get(0) != null) {
                SetsController.getInstance().retrieveImageUrl(getIntent().getExtras().getInt("position"));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onUrlImageLoaded() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewPagerAdapter.notifyDataSetChanged();
            }
        });
    }


    public class DetailOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            try {
                if (SetsController.getInstance().getSets().get(position).getPhotoArray() != null
                        && SetsController.getInstance().getSets().get(position).getPhotoArray().get(0) != null
                        && SetsController.getInstance().getSets().get(position).getImageUrl() == null) {
                    SetsController.getInstance().retrieveImageUrl(position);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }


}