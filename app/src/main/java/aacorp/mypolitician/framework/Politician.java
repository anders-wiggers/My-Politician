/*
 * Copyright (c) 2018.
 * @author Anders Bille Wiggers
 * for Introduction-to-Human-Computer-InteractionI course.
 *
 */

package aacorp.mypolitician.framework;

import com.google.firebase.firestore.GeoPoint;

import java.util.Map;

import aacorp.mypolitician.Implementation.StrengthImpl;

public interface Politician {

    /**
     * @return Should return a politicians Name
     */
    String getName();

    /**
     * @return Should return a politicians Party
     */
    String getParty();

    /**
     * @return Should return a politicians Area as a Geopoint
     */
    GeoPoint getArea();

    /**
     * @return Should return a politicians Banner Id
     */
    int getBannerId();

    /**
     * @return Should return a politicians Profile picture id
     */
    int getProfilePictureId();

    /**
     * @return Should return boolean base on politicians gender.
     */
    boolean getIsMale();

    /**
     * @return Should return a politicians strength as a List
     */
    Map<String,StrengthImpl> getStrength();

    /**
     * @return Should return the id of a politician
     */
    String getId();



}
