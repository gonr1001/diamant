package dInternal.dTimeTable;

import dInternal.dUtil.DXObject;
import dInternal.dData.SetOfResources;
public class Cycle extends DXObject {
    public Cycle() {
    }

    public String toWrite() {
        return "";
    }

    public SetOfResources getSetOfDays() {
        return new SetOfResources(4);
    }

    public void setSetOfDays(SetOfResources setofdays) {
    }

    private SetOfDays _setOfDays;
    private int _periodSize;// it give the period size in minutes. It must be a multiple of 5.
}
