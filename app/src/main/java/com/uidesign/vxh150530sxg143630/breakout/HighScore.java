package com.uidesign.vxh150530sxg143630.breakout;

/*************************************************************************
 * Created by Varsha  and Satheesh on 11/29/2015.
 * HighScore class to hold the scores
 **************************************************************************/
public class HighScore implements Comparable<HighScore>{
    private String name;
    private String time;

    /*************************************************************************
     * Created by Satheesh on 11/29/2015.
     * Getters and setters for the variables of the class
     **************************************************************************/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    /*************************************************************************
     * Created by Varsha on 11/29/2015.
     * compareTo method to help sort the leaderboard
     **************************************************************************/
    @Override
    public int compareTo(HighScore highScore){
        String[] time1Arr = this.getTime().split(":");
        String[] time2Arr = highScore.getTime().split(":");
        return (Integer.parseInt(time1Arr[0])*60 + Integer.parseInt(time1Arr[1])) - (Integer.parseInt(time2Arr[0])*60 + Integer.parseInt(time2Arr[1 ]));
    }
}
