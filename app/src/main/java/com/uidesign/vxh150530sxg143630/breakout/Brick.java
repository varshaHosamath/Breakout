package com.uidesign.vxh150530sxg143630.breakout;

import android.graphics.RectF;

import java.util.Random;

/*************************************************************************
 * Created by Varsha and Satheesh on 11/29/2015.
 * Brick class to represent the bricks at the top of the screen
 **************************************************************************/
public class Brick {
    private RectF rectangle;
    private float x;
    private float y;
    private int rowNumber;
    private boolean isPresent;
    private int color;
    Random random= new Random();


    /*************************************************************************
     * Created by Varsha on 11/29/2015.
     * Brick constructor to create the brick at the respective position
     **************************************************************************/
    public Brick(float x, float y, int rowNumber, int givenX,int givenY, int color, int existingColor){
        this.x = x;
        this.y = y;
        this.rowNumber = rowNumber;
        color = random.nextInt(5);
        while(color==existingColor){
            color = random.nextInt(5);
        }
        this.color = color;
        rectangle = new RectF(x, y, (int) (x+ (givenX/(7+rowNumber))), (int)(y +givenX/12));
        this.isPresent = true;
    }

    /*************************************************************************
     * Created by Satheesh on 11/29/2015
     * Getters and Setters for the class
     **************************************************************************/
    public boolean isPresent() { return isPresent; }
    public void setPresent(boolean isPresent){
        this.isPresent = isPresent;
    }
    public int getColor(){
        return this.color;
    }
    public void setRectangle(float x, float y,int givenX, int givenY){
        rectangle = new RectF(x, y, (int) (x+ (givenX/(7+rowNumber))), (int)(y +givenX/12));
    }
    public void setColor(int color){
        this.color = color;
    }
    public RectF getBrick(){
        return rectangle;
    }

}
