package aacorp.mypolitician.Implementation;

import java.util.List;

import aacorp.mypolitician.framework.Area;
import aacorp.mypolitician.framework.Match;
import aacorp.mypolitician.framework.Strength;

public class MatchImpl implements Match {

    private final String name;
    private final String party;

    public MatchImpl(String name, String party){
        this.name = name;
        this.party = party;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getParty() {
        return party;
    }

    @Override
    public Area getArea() {
        return null;
    }

    @Override
    public int getBannerId() {
        return 0;
    }

    @Override
    public int getProfilePictureId() {
        return 0;
    }

    @Override
    public List<Strength> getStrenghts() {
        return null;
    }
}
