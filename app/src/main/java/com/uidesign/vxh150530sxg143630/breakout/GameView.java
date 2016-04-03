package com.uidesign.vxh150530sxg143630.breakout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.LinearLayout;

/*************************************************************************
 * Created by Varsha and Satheesh on 11/29/2015.
 * GameView class to hold the canvas and draw and control the game.
 **************************************************************************/
public class GameView extends SurfaceView implements Runnable{

    SurfaceHolder holder;
    Canvas canvas;
    Paint paint;
    int screenX, screenY;
    Paddle paddle;
    Ball ball;
    Point size;
    float xPressed;
    float yPressed;
    float desiredX;
    boolean isPaused = false;
    static boolean isGameOver = false;
    static boolean isBallMoving = false;
    static boolean isFirstGame = true;
    static int gameOverSize = 20;
    public static long startTime=0;
    private MainOptionsView mainOptionsView;
    int directionPressed = 0;
    public static boolean isGameWon= false;
    static boolean shouldMovePaddle = false;
    static Brick[][] bricks = new Brick[3][];
    private Context myContext;

    /*************************************************************************
     * Created by Varsha on 11/29/2015.
     * GameView constructor to create the items on screen used to play
     **************************************************************************/
    public GameView(Context context, Point size, MainOptionsView mainOptionsView){
        super(context);
        myContext = context;
        this.mainOptionsView = mainOptionsView;
        this.size=size;
        holder = getHolder();
        paint = new Paint();
        screenX = this.size.x;
        screenY = this.size.y;
        paddle = new Paddle(screenX,screenY);
        ball = new Ball(screenX,screenY,this);
        int startCount = 7;
        int existingColor = 0 ;
        for(int i=0; i<=2; i++){
            float currX=0;
            float currY=(int) (i*(screenX/12));
            bricks[i] = new Brick[startCount];
            for(int j=0;j<startCount;j++){

                Brick brick = new Brick(currX,currY,i,screenX,screenY,j+i,existingColor);
                bricks[i][j] = brick;
                existingColor = brick.getColor();
                currX += (int)(screenX/startCount);
            }
            startCount++;
        }
    }

    /*************************************************************************
     * Created by Varsha  on 11/29/2015.
     * variables to hold the components in the activity
     **************************************************************************/
    public void draw() {
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
            paint.setStyle(Paint.Style.FILL);
            // Draw the background color
            canvas.drawColor(Color.argb(255, 191, 191, 191));
            // Choose the brush color for drawing
            paint.setColor(Color.argb(255, 0, 0, 0));
            // Draw the paddle
            if(shouldMovePaddle && !isGameOver)
                paddle.editRectangle((int)desiredX,directionPressed);
            if (isBallMoving && !isGameOver)
                    ball.editBallPosition();
            canvas.drawRect(paddle.getRectangle(), paint);
            paint.setColor(Color.argb(255, 255, 255, 255));
            canvas.drawCircle(ball.getx(),ball.getY(),ball.getRadius(),paint);
            int startCount = 7;
            for(int i=0; i<=2; i++) {
                for (int j = 0; j < startCount; j++) {
                    Brick brick = bricks[i][j];
                    switch(brick.getColor()){
                        case 0: paint.setColor(Color.argb(255, 242, 241, 239));
                            break;
                        case 1: paint.setColor(Color.argb(255, 31, 58, 147));
                            break;
                        case 2: paint.setColor(Color.argb(255, 38, 166, 91));
                            break;
                        case 3: paint.setColor(Color.argb(255, 207, 0, 15));
                            break;
                        case 4: paint.setColor(Color.argb(255, 0, 0, 0));
                            break;
                        default: break;
                    }
                    if(brick.isPresent()) {
                        paint.setStyle(Paint.Style.FILL);
                        canvas.drawRect(brick.getBrick(), paint);
                        paint.setStyle(Paint.Style.STROKE);
                        paint.setColor(Color.argb(255, 0, 0, 0));
                        canvas.drawRect(brick.getBrick(), paint);
                    }
                }
                startCount++;
            }
            if(isGameOver && !isFirstGame && !isGameWon){
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Color.RED);
                paint.setTextSize(gameOverSize);
                canvas.drawText("Game Over", screenX/24, screenY/2,paint);
                if(gameOverSize<200){
                    gameOverSize += 10;
                }
            }
            if(isGameWon){
                isGameWon = false;
                isGameOver= true;
                isBallMoving = false;
                HighScore hs = new HighScore();
                hs.setTime(MainOptionsView.timer.getText().toString());
                if(HighScoreUtil.highScores.size()<10 || hs.compareTo(HighScoreUtil.highScores.get(HighScoreUtil.highScores.size()-1)) <0){
                    Intent intent = new Intent(myContext, EnterHighScoreActivity.class);
                    String message = hs.getTime();
                    intent.putExtra("score", message);
                    myContext.startActivity(intent);
                }else{
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(Color.GREEN);
                    paint.setTextSize(gameOverSize);
                    canvas.drawText("Score is" + MainOptionsView.timer.getText().toString(), screenX/24, screenY/2,paint);
                    if(gameOverSize<100){
                        gameOverSize += 10;
                    }
                }


            }
            holder.unlockCanvasAndPost(canvas);
        }

    }

    /*************************************************************************
     * Created by Varsha on 11/29/2015.
     * Thread to call draw periodically
     **************************************************************************/
    @Override
    public void run(){
        while(!isPaused){
            try {
                Thread.sleep(10);
            }catch(InterruptedException ie){

            }
            draw();
        }
    }

    public void resume() {
        isPaused = false;
        new Thread(this).start();
    }
    public void pause(){
        isPaused = true;
    }

    /*************************************************************************
     * Created by Satheesh on 11/29/2015.
     * On Touch of the screen start the game if game is not in progress
     * move tha paddle to the desired location
     **************************************************************************/
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            // Player has touched the screen
            case MotionEvent.ACTION_DOWN:
                    if((isGameOver==false && isFirstGame && !isBallMoving) || (isGameOver&&!isFirstGame && !isBallMoving)){
                        resetThings();
                    }
                xPressed = motionEvent.getX();
                float yPressed = motionEvent.getY();
                if(yPressed>screenY-600 && yPressed < screenY-200)
                if(xPressed < (2*paddle.getX() + screenX/6)/2){
                    directionPressed = -1;
                    shouldMovePaddle = true;
                    if(xPressed<screenX/12)
                        desiredX = 0;
                    else{
                        desiredX = xPressed - screenX/12;
                    }
                }
                else if(xPressed > (2*paddle.getX() + screenX/6)/2){
                    directionPressed = 1;
                    shouldMovePaddle = true;
                    if(xPressed>screenX - screenX/12)
                        desiredX = screenX - screenX/6;
                    else{
                        desiredX = xPressed - screenX/12;
                    }
                }

                break;

            // Player has removed finger from screen
            case MotionEvent.ACTION_UP:
                    shouldMovePaddle=false;
                break;
        }
        return true;
    }

    public void setMainViewOptionsView(MainOptionsView mainOptionsView) {
        this.mainOptionsView = mainOptionsView;
    }

    /*************************************************************************
     * Created by Varsha on 11/29/2015.
     * method to reset the gameview once the user fails completing the game or if he succesfully completes the game
     **************************************************************************/
    public void resetThings(){
        paddle = new Paddle(screenX,screenY);
        ball = new Ball(screenX,screenY,this);
        isGameWon=false;
        int startCount = 7;
        int existingColor = 0 ;
        gameOverSize = 20;
        if(!isFirstGame)
            for(int i=0; i<=2; i++){
                float currX=0;
                float currY=(int) (i*(screenX/12));
                bricks[i] = new Brick[startCount];
                for(int j=0;j<startCount;j++){

                    Brick brick = new Brick(currX,currY,i,screenX,screenY,j+i,existingColor);
                    bricks[i][j] = brick;
                    existingColor = brick.getColor();
                    currX += (int)(screenX/startCount);
                }
                startCount++;
            }
        isGameOver = false;
        isBallMoving = true;
        startTime = System.currentTimeMillis();
        mainOptionsView.startTimerThread();
        if(OptionsView.seekBar.isEnabled())
            OptionsView.seekBar.setEnabled(false);
    }
}
