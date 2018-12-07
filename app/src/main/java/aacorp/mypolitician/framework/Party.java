/**
 * Copyright (c) 2018.
 * @author Anders Bille Wiggers
 * @auther Alex Krogh Smythe
 * for Introduction-to-Human-Computer-InteractionI course.
 *
 */

package aacorp.mypolitician.framework;

public class Party {
    private int color;
    private String name;
    private String block;


    public Party(){
    }

    /**
     * returns a the party color
     * @return int color parsed to int
     */
    public int getColor() {
        return color;
    }

    /**
     * set the color
     * @param color int color parsed to int
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * get name of party
     * @return String name of party
     */
    public String getName(){
        return name;
    }

    /**
     * set color of block
     * @param colorOfBlock color of the block
     */
    public void setColorOfBlock(String colorOfBlock){
        this.block = colorOfBlock;
    }

    /**
     * get the color of the block
     * @return string that represent the color
     */
    public String getColorOfBlock(){
        return block;
    }

    /**
     * set the name of the party
     * @param name get the name
     */
    public void setName(String name){
        this.name = name;
    }
}
