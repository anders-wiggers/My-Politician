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

    int getKey();

    String getName();

    Enum getParty();

    GeoPoint getGeoPoint();

    int getBannerId();

    int getProfilePictureId();

    Map<String,StrengthImpl> getStrength();

}
