package com.example.gogaga.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gogaga.R;
import com.example.gogaga.SliderAdapter;
import com.example.gogaga.SliderItem;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private static final Integer SLIDER_TIMER_COUNT = 2000;
    ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        viewPager2 = rootView.findViewById(R.id.viewPagerImageSlider);

        // Here I'm preparing list of images from drawable
        // You can get it from api as well.

        List<SliderItem> sliderItemList = new ArrayList<>();
        sliderItemList.add(new SliderItem(R.drawable.im1));
        sliderItemList.add(new SliderItem(R.drawable.im2));
        sliderItemList.add(new SliderItem(R.drawable.im3));
        sliderItemList.add(new SliderItem(R.drawable.im4));
        sliderItemList.add(new SliderItem(R.drawable.im5));

        viewPager2.setAdapter(new SliderAdapter(sliderItemList, viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(0));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(.85f + r * 0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, SLIDER_TIMER_COUNT);
            }
        });
        return rootView;
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, SLIDER_TIMER_COUNT);
    }
}