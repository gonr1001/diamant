/* Generated by Together */

package dInternal.dTimeTable;

import dInternal.dData.SetOfResources;
import dInternal.dUtil.DXObject;
import xml.InPut.ReadXMLElement;
import org.w3c.dom.Element;


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
      }// end for (int i=0; i< size; i++)

    }

  private SetOfResources _setOfDays;
  private String _TAGITEM="TTday";
  private String _TAGITEM1="dayID";
  private String _TAGITEM2="TTsequences";
}
