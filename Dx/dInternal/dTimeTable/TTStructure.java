package dInternal.dTimeTable;

import dInternal.dData.SetOfResources;

public class TTStructure {


  private SetOfCycles _setOfCycles;
  private String _str;
  private int _col;
  private int _row;

  public TTStructure() {
    _col=4;
    _row=10;
  }

 public SetOfResources getSetOfCycles() {
    return new SetOfResources(4);
 }

 public void setSetOfResources(SetOfResources setOfCycles) {
 }

 public String toWrite() {
    return "";
 }

 public int getColumn(){
   return _col;
 }

 public int getRow(){
  return _row;
 }

 public String loadData(String  fils) {
   return new String("error");
  }


}
