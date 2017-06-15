package dto;

import java.io.Serializable;

/**
 * Created by chanaka on 6/14/17.
 */
public class NumberRangeMessage implements Serializable {

    private long startNumber;

    private long endNumber;

    public NumberRangeMessage(long startNumber, long endNumber) {
        this.startNumber = startNumber;
        this.endNumber = endNumber;
    }

    public long getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(long startNumber) {
        this.startNumber = startNumber;
    }

    public long getEndNumber() {
        return endNumber;
    }

    public void setEndNumber(long endNumber) {
        this.endNumber = endNumber;
    }
}