package com.uidesign.vxh150530sxg143630.breakout;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/*************************************************************************
 * Created by Varsha  and Satheesh on 11/29/2015.
 * Launcher activity for the project
 **************************************************************************/
public class BreakOutActivity extends AppCompatActivity implements SensorEventListener {
    private float lastX, lastY, lastZ;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Thread accelerometerThread;
    public static int tiltDirection=0;
    GameScreenLayout gameScreenLayout;
    boolean firstReadingDone = false;
    long lastSensorReading = 0;

    /*************************************************************************
     * Created by Satheesh on 11/29/2015.
     * Regisitering the sensor listeners and creating the views requried for the project
     **************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        Display display = getWindowManager().getDefaultDisplay();
        // Load the resolution into a Point object
        Point size = new Point();
        display.getSize(size);
        gameScreenLayout= new GameScreenLayout(this,size);
        setContentView(gameScreenLayout);
        try{
            readInputData();
        }catch(IOException ioe){

        }


    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    /*************************************************************************
     * Created by Varsha  on 11/29/2015.
     * On sensor change, get the x value of the accelerometer and set the ball's x value to that position
     **************************************************************************/
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        if (GameView.isBallMoving)
            if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                //if(!firstReadingDone  || (System.currentTimeMillis() - lastSensorReading) > 50) {
                    lastSensorReading = System.currentTimeMillis();
                    firstReadingDone = true;
                    float x = sensorEvent.values[0];
                    Ball.tiltValue = x;
                    if (x > 0) {
                        tiltDirection = -1;
                        Ball.isTilted = true;
                    } else if (x < 0) {
                        tiltDirection = 1;
                        Ball.isTilted = true;
                    } else {
                        Ball.isTilted = false;
                    }
            }
    }



    /*************************************************************************
     * Created by Satheesh on 11/29/2015.
     * On Resume method register the sensor listener and resume the game
     **************************************************************************/
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        gameScreenLayout.resume();

    }

    /*************************************************************************
     * Created by Satheesh on 11/29/2015.
     * On pause method unregister the sensor listener and pause the game
     **************************************************************************/
    @Override
    protected  void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
        gameScreenLayout.pause();
    }

    /*************************************************************************
     * Created by Varsha on 11/29/2015.
     * Read high score data from the file
     **************************************************************************/
    public void readInputData() throws IOException {
        File file = new File(this.getFilesDir(), "highscores");
        HighScoreUtil.highScores = new ArrayList<HighScore>();
        if(!file.exists()){
            file.createNewFile();
        }
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] highScoreDetails = line.split("\\t");
            HighScore highScore = new HighScore();
            highScore.setName(highScoreDetails[0]);
            highScore.setTime(highScoreDetails[1]);
            HighScoreUtil.highScores.add(highScore);
        }
        Collections.sort(HighScoreUtil.highScores);
    }

}
