/**
 * Copyright (c) 2018.
 * @author Anders Bille Wiggers & Alex Krogh Smythe
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName(){
        return name;
    }

    public void setColorOfBlock(String colorOfBlock){
        this.block = colorOfBlock;
    }

    public String getColorOfBlock(){
        return block;
    }

    public void setName(String name){
        this.name = name;
    }
}
