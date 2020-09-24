package com.example.gogaga.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gogaga.Post;
import com.example.gogaga.PostAdapter;
import com.example.gogaga.R;
import com.example.gogaga.apiinterface.PostApiInterface;
import com.example.gogaga.retrofit.PostRetrofit;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataFragment extends Fragment {

    private static final String TAG = "DataFragment";
    private Integer START = 0;
    private Integer LIMIT = 15;

    ConstraintLayout constraintLayout;
    PostApiInterface postApiInterface;
    LinearLayout brokenInternetLL;
    RecyclerView recyclerView;
    PostAdapter postAdapter;
    List<Post> postList = new ArrayList<>();
    ProgressBar progressBar;
    LinearLayoutManager linearLayoutManager;

    Boolean isScrolling = false;
    int currentItem, totalItems, scrollOutItems;
    private Handler progressBarHandler = new Handler();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_data, container, false);
        PostRetrofit postRetrofit = new PostRetrofit();
        postApiInterface = postRetrofit.getPostsInterface();

        constraintLayout = rootView.findViewById(R.id.fragment_data);
        brokenInternetLL = rootView.findViewById(R.id.broken_internet_ll);

        progressBar = rootView.findViewById(R.id.progress_bar);
        recyclerView = rootView.findViewById(R.id.data_recycler_view);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        postAdapter = new PostAdapter(postList);
        recyclerView.setAdapter(postAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.i(TAG, "onScrollStateChanged: newState: " + newState + ", SCROLL_STATE_TOUCH_SCROLL: " + AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItem = linearLayoutManager.getChildCount();
                totalItems = linearLayoutManager.getItemCount();
                scrollOutItems = linearLayoutManager.findFirstVisibleItemPosition();
                Log.i(TAG, "onScrolled: currentItem: " + currentItem + ", total: " + totalItems + ", scrolled: " + scrollOutItems);
                if (isScrolling && (currentItem + scrollOutItems == totalItems)) {
                    isScrolling = false;
                    START = START + LIMIT;
                    fetchData();
                }
            }
        });

        fetchData();
        return rootView;
    }

    public void fetchData() {
        toggleImage(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        progressBarHandler.postDelayed(showProgressRunnable, 5000);
        postApiInterface.getPosts(String.valueOf(START), String.valueOf(LIMIT)).enqueue(callback);
    }


    Callback<List<Post>> callback = new Callback<List<Post>>() {
        @Override
        public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
            if (response.isSuccessful()) {
                List<Post> tempList = response.body();
                Log.i(TAG, "onResponse: " + postList);
                postAdapter.addData(tempList);
                progressBar.setVisibility(View.GONE);
            }
        }

        @Override
        public void onFailure(Call<List<Post>> call, Throwable t) {
            t.printStackTrace();
        }
    };

    private void displayNoNetworkToast() {
        Snackbar snackbar = Snackbar.make(constraintLayout, R.string.no_internet_connection, Snackbar.LENGTH_SHORT);
        snackbar.show();
        toggleImage(View.VISIBLE);
    }

    public void toggleImage(int visibility) {
        Transition transition = new Slide(Gravity.BOTTOM);
        transition.setDuration(1000);
        transition.addTarget(brokenInternetLL);

        TransitionManager.beginDelayedTransition(constraintLayout, transition);
        brokenInternetLL.setVisibility(visibility);
    }
    Runnable showProgressRunnable = new Runnable() {
        @Override
        public void run() {
            if (progressBar.getVisibility() == View.VISIBLE) {
                progressBar.setVisibility(View.GONE);
                displayNoNetworkToast();
            } else {
                toggleImage(View.VISIBLE);
            }
        }
    };

}