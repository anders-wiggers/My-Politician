package aacorp.mypolitician.Implementation;

import aacorp.mypolitician.framework.Strength;

public class StrengthImpl implements Strength {
    private int percent;
    private String text;

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
