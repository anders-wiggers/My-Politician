/**
 * Copyright (c) 2018.
 * @author Anders Bille Wiggers
 * for Introduction-to-Human-Computer-InteractionI course.
 *
 */
package aacorp.mypolitician.framework;

public class Constants {
    //Politicians Strengths
    public static final String DEFENCE_ICON = "def";
    public static final String WORLD_ICON = "wor";
    public static final String ENVIRONMENT_ICON = "env";
    public static final String MONEY_ICON = "mon";
    public static final String EDUCATION_ICON = "edu";
    public static final String ACTIVITY_ICON = "act";
    public static final String CULTURE_ICON = "cul";
    public static final String ORGANIC_ICON = "org";
    public static final String FREEDOM_ICON = "fre";






    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * @returns Distance in Meters
     */
    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
}
