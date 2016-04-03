package com.uidesign.vxh150530sxg143630.breakout;

import android.graphics.RectF;

/*************************************************************************
 * Created by Varsha and Satheesh on 11/29/2015.
 * create the adapter and set the list to the listview of scores
 **************************************************************************/
public class Paddle {
    private RectF rectangle;
    private float x;
    private float y;
    private float speed;

    /*************************************************************************
     * Created by Varsha  on 11/29/2015.
     * Constructor to create the paddle
     **************************************************************************/
    public Paddle(int givenX,int givenY){
        x = givenX/2-100;
        y = givenY-500;
        rectangle = new RectF(x, y, x+ givenX/6, y + 50);
        speed = 350;
    }

    public float getX(){
        return x;
    }
    public float gety(){
        return y;
    }

    /*************************************************************************
     * Created by Satheesh on 11/29/2015.
     * Move the paddle to the desired position
     **************************************************************************/
    public void editRectangle(int xPressed, int direction){
        if(xPressed<rectangle.left && direction ==-1){
            rectangle.left -= 20;
            rectangle.right -=20;
        }else if(xPressed>rectangle.left && direction == 1) {
            rectangle.left += 20;
            rectangle.right += 20;
        }else{
            GameView.shouldMovePaddle = false;
        }
        this.x = rectangle.left;
    }
    public RectF getRectangle(){
        return rectangle;
    }
}
