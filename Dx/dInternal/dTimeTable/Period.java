package dInternal.dTimeTable;

import dInternal.dUtil.DXObject;
public class Period extends DXObject {
    public Period() {
      _beginHour[0] = 8;
      _beginHour[1] = 30;

      _endHour[0] = 9;
      _endHour[1] = 00;
    }

    public int nbConflictStud = 0;
    public int nbConflictInstr = 0;
    public int nbConflictRoom= 0;
    public int[] _beginHour= new int[2];//_beginHour[0]= hour; _beginHour[1]= minute
    public int[] _endHour= new int[2];//_endHour[0]= hour; _endHour[1]= minute
    public int _priority;// 0= normal; 1= low; 2= null
}
