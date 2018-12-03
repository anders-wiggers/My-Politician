package aacorp.mypolitician.patterns;

import aacorp.mypolitician.framework.Politician;

public class MoveData {
    private static MoveData instance;
    private Politician politician;

    protected MoveData(){

    }

    /**
     * Singleton method to return the instance of the Database class.
     * @return the only object of the database instance.
     */
    public static synchronized MoveData getInstance(){
        if(instance == null){
            instance = new MoveData();
        }
        return instance;
    }

    public void setPolitician(Politician politician){
        this.politician = politician;
    }

    public Politician getPolitician() {
        return politician;
    }
}
