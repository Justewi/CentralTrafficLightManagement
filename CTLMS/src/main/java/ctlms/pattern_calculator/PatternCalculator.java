package ctlms.pattern_calculator;

import ctlms.model.City;
import ctlms.model.Controler;
import ctlms.model.Pattern;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PatternCalculator {

    private int redTime = 20;
    private int greenTime = 40;


    public void manifestation (City city, int x , int y, int greenTime, int redTime){
        this.setGreenTime(greenTime);
        this.setRedTime(redTime);
        manifestation(city, x, y);
    }

    public void manifestation(City city, int x , int y){

        List<Controler> controlerList = city.getControlerList();

        //Y
        for(int j = 0; j < city.getHeight(); j++){
            //X
            for(int i = 0; i < city.getWidth(); i++){
                Pattern controlerPattern = controlerList.get(j * city.getWidth() + i).getPattern();
                if( i != x ){
                    controlerPattern.setEwAxisTime(this.getGreenTime());
                    controlerPattern.setNsAxisTime(this.getRedTime());
                }
                else if ( (i == x) && (j == y) ){
                    int time = (this.getGreenTime() + this.getRedTime() )/2;
                    controlerPattern.setEwAxisTime(time);
                    controlerPattern.setNsAxisTime(time);
                }
                else{
                    controlerPattern.setEwAxisTime(this.getRedTime());
                    controlerPattern.setNsAxisTime(this.getGreenTime());
                }

            }
        }

    }


    /**
     * Considering a width*height city eg 10*10
     * X -> {0 - width-1} (must be in) eg {0-9}
     * Y -> {0 - height-1} (must be in) eg {0-9}
     * Create a green wave from (x,y) to (x,y)
     * @param city
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     * @param greenTime
     * @param redTime
     */
    public static void greenWaveXY(City city, int fromX, int fromY, int toX, int toY,
                                   int greenTime, int redTime) {

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());

        int numeroFeu = 1;

        List<Controler> controlerList = city.getControlerList();

        //EW axis
        if (fromX < toX) {
            for (int i = fromX; i <= toX; i++) {
                Pattern controlerPattern = controlerList.get(fromY * city.getWidth() + i).getPattern();

                controlerPattern.setStartTime(calendar.getTimeInMillis() + numeroFeu * 10000);
                controlerPattern.setEwAxisTime(greenTime);
                controlerPattern.setNsAxisTime(redTime);

                numeroFeu++;
            }
        } else {
            for (int i = fromX; i >= toX; i--) {
                Pattern controlerPattern = controlerList.get(fromY * city.getWidth() + i).getPattern();

                controlerPattern.setNsAxisTime(redTime);
                controlerPattern.setEwAxisTime(greenTime);
                controlerPattern.setStartTime(calendar.getTimeInMillis() + numeroFeu * 10000);

                numeroFeu++;
            }
        }

        //NS axis
        if (fromY < toY) {
            for (int i = fromY + 1; i <= toY; i++) {
                int controlerNumber = i * city.getWidth() + toX;
                Pattern controlerPatternNS = controlerList.get(controlerNumber).getPattern();

                controlerPatternNS.setStartTime(calendar.getTimeInMillis() + numeroFeu * 10000);
                controlerPatternNS.setNsAxisTime(greenTime);
                controlerPatternNS.setEwAxisTime(redTime);

                numeroFeu++;
            }
        } else {
            for (int i = fromY - 1; i >= toY; i--) {
                Pattern controlerPatternNS = controlerList.get(i * city.getWidth() + toX).getPattern();
                controlerPatternNS.setEwAxisTime(redTime);
                controlerPatternNS.setNsAxisTime(greenTime);
                controlerPatternNS.setStartTime(calendar.getTimeInMillis() + numeroFeu * 10000);
                numeroFeu++;
            }
        }

    }

    public int getRedTime() {
        return redTime;
    }

    public void setRedTime(int redTime) {
        this.redTime = redTime;
    }

    public int getGreenTime() {
        return greenTime;
    }

    public void setGreenTime(int greenTime) {
        this.greenTime = greenTime;
    }
}
