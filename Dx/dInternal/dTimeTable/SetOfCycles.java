/* Generated by Together */

package dInternal.dTimeTable;

import dInternal.dData.SetOfResources;
import xml.InPut.ReadXMLElement;
import org.w3c.dom.Element;


public class SetOfCycles {

  public SetOfCycles() {
    _setOfCycles = new SetOfResources(4);
  }

  /**
   * get the set of cycles
   * @return SetOfResources the set of cycles
   * */
  public SetOfResources getSetOfCycles(){
    return _setOfCycles;
  }

  /**
   * set the set of cycles
   * @param SetOfResources the set of cycles
   * */
  public void setSetOfCycles(SetOfResources setOfCycles){
    _setOfCycles= setOfCycles;
  }

  /**
   *
   * */
  public void readXMLtag(Element setofCycles){
    ReadXMLElement list= new ReadXMLElement();
    Cycle setOfdays = new Cycle();
    String ID="";
    int size= list.getSize(setofCycles,_TAGITEM);
    System.out.println(" Cycles Size: "+size);//debug
    for (int i=0; i< size; i++){
      Element cycle= list.getElement(setofCycles,_TAGITEM,i);
      ID= list.getElementValue(cycle,_TAGITEM1);
      _periodLenght= Integer.parseInt(list.getElementValue(cycle,_TAGITEM2));
      System.out.println(" Cycle ID: "+ID+" PeriodLenght: "+_periodLenght);//debug
      Element days= list.getElement(cycle,_TAGITEM3,0);
      setOfdays.readXMLtag(days);
    }// end for (int i=0; i< size; i++)

  }

  private SetOfResources _setOfCycles;
  private int _periodLenght;
  private String _TAGITEM="TTcycle";
  private String _TAGITEM1="cycleID";
  private String _TAGITEM2="pLength";
  private String _TAGITEM3="TTdays";
}