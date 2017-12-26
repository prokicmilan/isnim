package etf.nim.pm160695d;

/**
 * Created by Proka on 12/22/2017.
 */
public class Move {
    public Move(int pos, int numDiscs) {
        this.pos = pos;
        this.numDiscs = numDiscs;
        this.value = -2000;
    }

    public Move(int pos, int numDiscs, boolean isMax) {
        this(pos, numDiscs);
        this.isMax = isMax;
    }

    public int getPos() {
        return pos;
    }

    public int getNumDiscs() {
        return numDiscs;
    }

    public int getValue() {
        return value;
    }

    public boolean getIsMax() {
        return isMax;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Integer.toString(numDiscs) + " diskova sa stuba " + Integer.toString(pos) + " (vrednost " + Integer.toString(value) + ")";
    }

    private int pos;
    private int numDiscs;
    private int value;
    private boolean isMax;
}
