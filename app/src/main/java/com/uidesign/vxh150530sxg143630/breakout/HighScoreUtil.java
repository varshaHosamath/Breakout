package com.uidesign.vxh150530sxg143630.breakout;

import java.util.ArrayList;

/**
 * Created by Varsha and Satheeshon 11/29/2015.
 * Util class to hold the highscores
 */
public class HighScoreUtil {
    static ArrayList<HighScore> highScores = new ArrayList<>();
    public static ArrayList<HighScore> getHighScoresList(){
        return highScores;
    }
}
