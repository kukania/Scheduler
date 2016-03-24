package com.example.angks.scheduler;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by angks on 2016-02-25.
 */
public class addMenuMoveListener implements Animation.AnimationListener {
    View v;
    int number;
    boolean isUp;
    public View menu;
    public Context main;
    public RelativeLayout contentBody;
    int widthLen;
    int heightLen;
    public addMenuMoveListener(View v, int number, boolean isUp) {
        this.v = v;
        this.number = number;
        this.isUp = isUp;
    }
    public void setMain(Context main){
        this.main=main;
    }
    public void setView(View v) {this.v = v; }
    public void setNumber(int i) {number = i;}
    @Override
    public void onAnimationStart(Animation animation) {
        menu.setClickable(false);
        widthLen=dpToPixels(main,50);
        heightLen=dpToPixels(main,50);
    }
    @Override
    public void onAnimationEnd(Animation animation) {
        RelativeLayout.LayoutParams tlp=(RelativeLayout.LayoutParams)v.getLayoutParams();
        if(isUp){
            tlp.rightMargin+=2*widthLen;
            tlp.bottomMargin+=heightLen*number;
            v.setLayoutParams(tlp);
            v.setVisibility(View.VISIBLE);
        }
        else{
            tlp.rightMargin-=2*widthLen;
            tlp.bottomMargin-=heightLen*number;
            v.setLayoutParams(tlp);
            v.setVisibility(View.INVISIBLE);
        }
        menu.setClickable(true);
    }
    @Override
    public void onAnimationRepeat(Animation animation) {

    }
    public static int dpToPixels(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
