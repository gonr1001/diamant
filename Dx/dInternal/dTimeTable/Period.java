package dInternal.dTimeTable;

import dInternal.dUtil.DXObject;
public class Period extends DXObject {
    public Period() {
    }

    private int[] _beginHour= new int[2];//_beginHour[0]= hour; _beginHour[1]= minute
    private int[] _endHour= new int[2];//_endHour[0]= hour; _endHour[1]= minute
    private int _priority;// 0= normal; 1= low; 2= null
}
