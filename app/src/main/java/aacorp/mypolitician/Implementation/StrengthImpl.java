/**
 *  StrengthImpl is the implementation of the strength interface.
 *
 *  @author Anders Bille Wiggers
 *  for Introduction-to-Human-Computer-InteractionI course.
 *  Copyright (c) 2018.
 *
 */

package aacorp.mypolitician.Implementation;

import aacorp.mypolitician.framework.Strength;

public class StrengthImpl implements Strength{
    private int percent;
    private String text;

    public StrengthImpl(){
    }

    @Override
    public int getPercent() {
        return percent;
    }

    @Override
    public String getText() {
        return text;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "StrengthImpl{" +
                "percent=" + percent +
                ", text='" + text + '\'' +
                '}';
    }
}
