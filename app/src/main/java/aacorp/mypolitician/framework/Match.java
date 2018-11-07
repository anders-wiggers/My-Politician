package aacorp.mypolitician.framework;

import java.util.List;

public interface Match {

    String getName();

    String getParty();

    Area getArea();

    int getBannerId();

    int getProfilePictureId();

    List<Strength> getStrenghts();

}