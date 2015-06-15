package at.tba.treasurehunt.utils;

/**
 * Created by dAmihl on 24.05.15.
 * Hot Cold Manager to determine the distance to a treasure.
 */
public class HotColdManager {

    public enum HOT_COLD_STATE{
        VERY_COLD,
        COLD,
        WARM,
        HOT,
        VERY_HOT
    }

    public static HOT_COLD_STATE determineHotCold(int distanceToTreasure){

        if (distanceToTreasure < 150){
            return HOT_COLD_STATE.VERY_HOT;
        }
        if (distanceToTreasure < 200){
            return HOT_COLD_STATE.HOT;
        }
        if (distanceToTreasure < 300){
            return HOT_COLD_STATE.WARM;
        }
        if (distanceToTreasure < 500){
            return HOT_COLD_STATE.COLD;
        }

        return HOT_COLD_STATE.VERY_COLD;
    }

}
