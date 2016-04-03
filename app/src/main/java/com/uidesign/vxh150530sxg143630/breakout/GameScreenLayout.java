package com.uidesign.vxh150530sxg143630.breakout;

import android.content.Context;
import android.graphics.Point;
import android.widget.LinearLayout;

/*************************************************************************
 * Created by Varsha and Satheesh on 11/29/2015.
 * Main Layout which holds the other views in the main activity
 **************************************************************************/
public class GameScreenLayout extends LinearLayout{

    private GameView gameView;
    private OptionsView options;
    MainOptionsView mainOptionsView;

    /*************************************************************************
     * Created by Satheesh on 11/29/2015.
     * Constructor to generate the views and arrange them
     **************************************************************************/
    public GameScreenLayout(Context context, Point point){
        super(context);
        LayoutParams lpView = new LayoutParams(LayoutParams.WRAP_CONTENT, point.y - 400);

        options = new OptionsView(context,point);
        gameView = new GameView(context,point, mainOptionsView);
        mainOptionsView = new MainOptionsView(context,point,gameView);
        gameView.setMainViewOptionsView(mainOptionsView);
        mainOptionsView.setGameView(gameView);
        setOrientation(VERTICAL);

        gameView.setLayoutParams(lpView);
        addView(gameView);
        lpView = new LayoutParams(point.x, 150);
        options.setLayoutParams(lpView);
        mainOptionsView.setLayoutParams(lpView);
        addView(mainOptionsView);
        addView(options);
    }

    public void resume() {
       gameView.resume();
    }
    public void pause(){
        gameView.pause();
    }
}
