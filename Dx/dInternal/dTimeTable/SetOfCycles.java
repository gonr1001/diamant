/* Generated by Together */

package dInternal.dTimeTable;

import dInternal.dData.SetOfResources;
import dInternal.dData.Resource;
import xml.InPut.ReadXMLElement;
import xml.OutPut.BuildXMLElement;
import org.w3c.dom.Element;
import org.w3c.dom.Document;

public class SetOfCycles {

  /**
   * Constructor
   * */
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
   *read a xml tag containing a set of cycle and build the resource
   * @param Element the root xml tag of the set of cycle
   * */
  public void readXMLtag(Element setofCycles){
    ReadXMLElement list= new ReadXMLElement();

    String ID="";
    int size= list.getSize(setofCycles,_TAGITEM);
    //System.out.println(" Cycles Size: "+size);//debug
    for (int i=0; i< size; i++){
      Cycle setOfdays = new Cycle();
      Element cycle= list.getElement(setofCycles,_TAGITEM,i);
      ID= list.getElementValue(cycle,_TAGITEM1);
      _periodLenght= Integer.parseInt(list.getElementValue(cycle,_TAGITEM2));
      //System.out.println(" Cycle ID: "+ID+" PeriodLenght: "+_periodLenght);//debug
      Element days= list.getElement(cycle,_TAGITEM3,0);
      setOfdays.readXMLtag(days);
      _setOfCycles.addResource(new Resource(ID,setOfdays),0);
    }// end for (int i=0; i< size; i++)

  }

  /**
   * Contruct a xml element from the set of cycles
   * @param Document the root xml document
   * @Element the xml tag of the set of cycles
   * */
   public Element writeXMLtag(Document doc){
    BuildXMLElement xmlElt;
    try{
      xmlElt = new BuildXMLElement();
      Element eltCycles= xmlElt.createElement(doc,TTStructure.ITEM2);
      for (int i=0; i<_setOfCycles.size(); i++){
        Element eltCycle= xmlElt.createElement(doc,_TAGITEM);
        Element cycle= ((Cycle)_setOfCycles.getResourceAt(i).getAttach()).writeXMLtag(doc);
        Element cycleID= xmlElt.createElement(doc,_TAGITEM1,_setOfCycles.getResourceAt(i).getID());
        eltCycle= xmlElt.appendChildInElement(eltCycle, cycle);
        eltCycle= xmlElt.appendChildInElement(eltCycle, cycleID);
        eltCycles= xmlElt.appendChildInElement(eltCycles, eltCycle);
      }
      return eltCycles;
    } catch(Exception e){
      System.out.println("SetOfCycle: "+e);//debug
      return null;
    }
  }

  public int getPeriodLenght() {
    return  _periodLenght;
  }

  private SetOfResources _setOfCycles;
  private int _periodLenght;
  static final String _TAGITEM="TTcycle";
  static final String _TAGITEM1="cycleID";
  static final String _TAGITEM2="pLength";
  static final String _TAGITEM3="TTdays";
}