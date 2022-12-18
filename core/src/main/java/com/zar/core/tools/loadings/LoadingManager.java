package com.zar.core.tools.loadings;

import android.view.View;

import androidx.annotation.ColorRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

/**
 * Created by m-latifi on 11/27/2022.
 */

@Module
@InstallIn(SingletonComponent.class)
public class LoadingManager {

    private RecyclerViewSkeletonScreen skeletonScreen;
    private ViewSkeletonScreen viewSkeletonScreen;

    //______________________________________________________________________________________________ setRecyclerLoading
    public void setRecyclerLoading(RecyclerView recyclerLoading, int layout, @ColorRes int Color, int count) {

        AdapterLoading ap_loading = new AdapterLoading();
        recyclerLoading.setLayoutManager(new LinearLayoutManager(recyclerLoading.getContext(), RecyclerView.VERTICAL, false));
        skeletonScreen = Skeleton.bind(recyclerLoading)
                .adapter(ap_loading)
                .load(layout)
                .shimmer(true)      // whether show shimmer animation.                      default is true
                .count(count)          // the recycler view item count.                        default is 10
                .color(Color)       // the shimmer color.                                   default is #a2878787
                .angle(0)          // the shimmer angle.                                   default is 20;
                .duration(2000)     // the shimmer animation duration.                      default is 1000;
                .frozen(false)
                .show();
    }
    //______________________________________________________________________________________________ setRecyclerLoading



    //______________________________________________________________________________________________ stopLoadingRecycler
    public void stopLoadingRecycler() {

        if (skeletonScreen != null) {
            skeletonScreen.hide();
            skeletonScreen = null;
        }
    }
    //______________________________________________________________________________________________ stopLoadingRecycler



    //______________________________________________________________________________________________ setViewLoading
    public void setViewLoading(View view, int layout, @ColorRes int Color) {

        viewSkeletonScreen = Skeleton.bind(view)
                .load(layout)
                .angle(0)
                .color(Color)
                .duration(2200)
                .shimmer(true)
                .show();
    }
    //______________________________________________________________________________________________ setViewLoading



    //______________________________________________________________________________________________ stopLoadingView
    public void stopLoadingView() {

        if (viewSkeletonScreen != null) {
            viewSkeletonScreen.hide();
            viewSkeletonScreen = null;
        }
    }
    //______________________________________________________________________________________________ stopLoadingView


}
