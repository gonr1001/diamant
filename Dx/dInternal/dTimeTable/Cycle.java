/* Generated by Together */

package dInternal.dTimeTable;

import dInternal.dData.SetOfResources;
import dInternal.dData.Resource;
import dInternal.dUtil.DXObject;
import xml.InPut.ReadXMLElement;
import xml.OutPut.BuildXMLElement;
import org.w3c.dom.Element;
import org.w3c.dom.Document;

public class Cycle extends DXObject{
    public Cycle() {
      _setOfDays = new SetOfResources(4);
    }

    /**
     * get the set of days
     * @return SetOfResources the set of days
     * */
    public SetOfResources getSetOfSequences(){
      return _setOfDays;
    }

    /**
     * set the set of days
     * @param SetOfResources the set of days
     * */
    public void setSetOfSequences(SetOfResources setOfDays){
      _setOfDays= setOfDays;
    }

    /**
     *
     * */
    public void readXMLtag(Element setofDays){
      ReadXMLElement list= new ReadXMLElement();
      Day setOfSequences = new Day();
      String ID="";
      int size= list.getSize(setofDays,_TAGITEM);
      System.out.println(" Days Size: "+size);//debug
      for (int i=0; i< size; i++){
        Element day= list.getElement(setofDays,_TAGITEM,i);
        ID= list.getElementValue(day,_TAGITEM1);
        System.out.println(" Day ID: "+ID);//debug
        Element sequences= list.getElement(day,_TAGITEM2,0);
        setOfSequences.readXMLtag(sequences);
        _setOfDays.addResource(new Resource(ID,setOfSequences),0);
      }// end for (int i=0; i< size; i++)

    }

   /**
   * */
   public Element writeXMLtag(Document doc){
    BuildXMLElement xmlElt;
    try{
      xmlElt = new BuildXMLElement();
      Element eltDays= xmlElt.createElement(doc,SetOfCycles._TAGITEM3);
      for (int i=0; i<_setOfDays.size(); i++){
        Element eltDay= xmlElt.createElement(doc,Cycle._TAGITEM);
        Element day= ((Day)_setOfDays.getResourceAt(i).getAttach()).writeXMLtag(doc);
        Element dayID= xmlElt.createElement(doc,_TAGITEM1,_setOfDays.getResourceAt(i).getID());
        eltDay= xmlElt.appendChildInElement(eltDay, day);
        eltDay= xmlElt.appendChildInElement(eltDay, dayID);
        eltDays= xmlElt.appendChildInElement(eltDays, eltDay);
      }
      return eltDays;
    } catch(Exception e){
      System.out.println("Cycle: "+e);//debug
      return null;
    }
   }

  private SetOfResources _setOfDays;
  private int _periodLength;
  static final String _TAGITEM="TTday";
  static final String _TAGITEM1="dayID";
  static final String _TAGITEM2="TTsequences";
}
