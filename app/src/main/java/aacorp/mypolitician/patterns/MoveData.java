/**
 *  Singleton that handles moving obejcts from between classes.
 *
 * @author Anders Bille Wiggers
 * @author Alex Krogh Smythe
 * for Introduction-to-Human-Computer-InteractionI course.
 * Copyright (c) 2018
 *
 */
package aacorp.mypolitician.patterns;

import aacorp.mypolitician.framework.Politician;

public class MoveData {
    private static MoveData instance;
    private Politician politician;
    private boolean reset;

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

    /**
     * sets politcian to move
     * @param politician
     */
    public void setPolitician(Politician politician){
        this.politician = politician;
    }

    /**
     * returns the statis of the has reset toggle
     * @return boolean has reset
     */
    public boolean hasReset(){
        return reset;
    }

    /**
     * toggle the has reset boolean use when match is resumed
     */
    public void toggleHasReset(){
        if(reset) {reset = false;}
        else {reset = true;}
    }

    /**
     * returns the politician to move
     * @return politician that has been set
     */
    public Politician getPolitician() {
        return politician;
    }
}
