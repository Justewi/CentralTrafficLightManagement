package ctlms.gestion_pattern;

/*
Obsolète dû au nouveau modèle de donnée
 */
public class FeuPieton extends Feu {

    public static int DEFAULT_GREEN_DURATION = 10;
    public static int DEFAULT_RED_DURATION = 22;

    public FeuPieton() {
        super(DEFAULT_GREEN_DURATION, DEFAULT_RED_DURATION);
    }

    @Override
    public String getDescription() {
        return "{ \"timeGreen\" : " + timeGreen + ", \"timeRed\" : " + timeRed + " }";
    }
}
