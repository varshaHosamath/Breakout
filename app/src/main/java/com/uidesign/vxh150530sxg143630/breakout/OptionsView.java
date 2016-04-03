package com.uidesign.vxh150530sxg143630.breakout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/*************************************************************************
 * Created by Varsha and Satheesh on 11/29/2015.
 * Options View to have the slider for speed control
 **************************************************************************/
public class OptionsView extends LinearLayout {
    static SeekBar seekBar;

    /*************************************************************************
     * Created by Varsha  on 11/29/2015.
     * constructor to generate the options pane with the slider
     **************************************************************************/
    public OptionsView(Context context, Point point){
        super(context);
        setBackgroundColor(Color.argb(255, 242, 241, 239));
        setOrientation(HORIZONTAL);
        seekBar = new SeekBar(context);
        seekBar.setMax(100);
        LayoutParams lp = new LayoutParams(point.x-100, 150);
        seekBar.setLayoutParams(lp);
        ShapeDrawable thumb = new ShapeDrawable(new OvalShape());
        thumb.setIntrinsicHeight(80);
        thumb.setIntrinsicWidth(30);
        seekBar.setThumb(thumb);
        seekBar.setProgress(40);
        seekBar.setVisibility(View.VISIBLE);
        seekBar.setBackgroundColor(Color.WHITE);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            /*************************************************************************
             * Created by Satheesh  on 11/29/2015.
             * get the value of the slider and update the speed of the ball in the game
             **************************************************************************/
            public void onStopTrackingTouch(SeekBar arg0) {
                if (arg0.getProgress() < 25) {
                    Ball.rise = 2;
                    Ball.sliderValue = 2;
                } else if (arg0.getProgress() >= 25 && arg0.getProgress() < 50) {
                    Ball.rise = 4;
                    Ball.sliderValue = 4;
                } else if (arg0.getProgress() >= 50 && arg0.getProgress() < 75) {
                    Ball.rise = 6;
                    Ball.sliderValue = 6;
                } else {
                    Ball.rise = 8;
                    Ball.sliderValue = 8;
                }
                System.out.println(arg0.getProgress());
            }

            public void onStartTrackingTouch(SeekBar arg0) {

            }

            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {

            }
        });
        TextView textView = new TextView(context);
        textView.setText("Speed");
        textView.setTypeface(null, Typeface.BOLD);
        lp = new LayoutParams(200, 150);
        textView.setLayoutParams(lp);
        textView.setBackgroundColor(Color.argb(255, 242, 241, 239));
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(16);
        addView(textView);
        lp = new LayoutParams(point.x-200,150);
        seekBar.setLayoutParams(lp);
        addView(seekBar);
    }


}
