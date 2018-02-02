package ctlms.pattern_calculator;

import ctlms.model.City;
import ctlms.model.Controler;
import ctlms.model.Pattern;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PatternCalculator {



    public static void greenWaveXY( City city, int fromX, int fromY, int toX, int toY,
                             int greenTime, int redTime){

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());

        int numeroFeu = 1;

        List<Controler> controlerList = city.getControlerList();

        //EW axis
        if(fromX < toX){
            for(int i = fromX; i <= toX; i++){
                Pattern controlerPattern = controlerList.get(fromY*10 + i ).getPattern();

                controlerPattern.setStartTime(calendar.getTimeInMillis()+ numeroFeu * 10000);
                controlerPattern.setEwAxisTime(greenTime);
                controlerPattern.setNsAxisTime(redTime);

                numeroFeu++;
            }
        }
        else{
            for(int i = fromX; i >= toX; i--){
                Pattern controlerPattern = controlerList.get(fromY * 10 + i ).getPattern();

                controlerPattern.setNsAxisTime(redTime);
                controlerPattern.setEwAxisTime(greenTime);
                controlerPattern.setStartTime(calendar.getTimeInMillis()+ numeroFeu * 10000);

                numeroFeu++;
            }
        }

        //NS axis
        if(fromY < toY) {
            for (int i = fromY +1 ; i <= toY; i++) {
                int controlerNumber = i * 10 + toX;
                Pattern controlerPatternNS = controlerList.get(controlerNumber).getPattern();

                controlerPatternNS.setStartTime(calendar.getTimeInMillis() + numeroFeu * 10000);

                controlerPatternNS.setNsAxisTime(greenTime);
                controlerPatternNS.setEwAxisTime(redTime);

                numeroFeu++;
            }
        }
        else{
            for (int i = fromY - 1; i >= toY; i--) {
                Pattern controlerPatternNS = controlerList.get(i * 10 + toX ).getPattern();
                controlerPatternNS.setEwAxisTime(redTime);
                controlerPatternNS.setNsAxisTime(greenTime);
                controlerPatternNS.setStartTime(calendar.getTimeInMillis() + numeroFeu * 10000);
                numeroFeu++;
            }
        }

    }
}
