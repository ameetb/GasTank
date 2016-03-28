package com.example.ameet.gastank;

import java.io.Serializable;

/**
 * Created by Ameet on 1/30/2016.
 */
public class Car implements Serializable {
    //performs all calculations required
    //saves values for mileage and miles left

    final double mpg;
    final String name;
    final double tankSize;
    double gasRemaining;
    final int cursorPosition;


    public Car(double mpg, String name, double tankSize, double gasRemaining,int cursorPosition) {
        this.mpg = mpg;
        this.name = name;
        this.tankSize = tankSize;
        this.gasRemaining = gasRemaining;
        this.cursorPosition=cursorPosition;
    }

    public String getName() {
        return this.name;
    }

    public double getMpg() {
        return mpg;
    }

    public String getMpgString() {
        return String.valueOf(mpg);
    }


    public String getGasRemaining() {

        if (gasRemaining != -1) {
            return String.valueOf(gasRemaining);
        } else {
            return "---";
        }
    }


    public void removeGas(double gasUsedValue) {
        double tempValue =gasRemaining- gasUsedValue;
        if(tempValue<=0)
        {
            gasRemaining=0;
        }
        else gasRemaining=tempValue;
    }

    public String getMilesRemaining() {

        if (isTankReset()) {
            return String.valueOf(gasRemaining * mpg);
        } else {
            return "---";

        }
    }

    public String getMilesTraveled() {
        if (isTankReset()) {
            return String.valueOf(
                    tankSize * mpg - gasRemaining * mpg
            );
        } else {
            return "---";
        }
    }
    public void setGasRemaining(double gasRemaining)
    {
        this.gasRemaining=gasRemaining;
    }
    public boolean isTankReset()
    {
        if (gasRemaining==-1.0)
        {
            return false;
        }
        else return true;

    }
    public double getTankSize()
    {
        return tankSize;
    }
    public int getCursorPosition()
    {
        return cursorPosition;
    }



}
