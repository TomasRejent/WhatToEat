package cz.afrosoft.whattoeat.core.util;

/**
 * @author Tomas Rejent
 */
public final class NumberUtils {

    public static boolean isWithinRange(float checkedNumber, float targetValue, float aboveTolerancePercentage, float belowTolerancePercentage){
        float aboveDelta = targetValue * aboveTolerancePercentage;
        float belowDelta = targetValue * belowTolerancePercentage;
        return checkedNumber >= (targetValue - belowDelta) && checkedNumber <= (targetValue + aboveDelta);
    }
}
