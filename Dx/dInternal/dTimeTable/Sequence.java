/* Generated by Together */

package dInternal.dTimeTable;

import dInternal.dData.SetOfResources;
import dInternal.dData.Resource;
import dInternal.dUtil.DXObject;
import xml.InPut.ReadXMLElement;
import xml.OutPut.BuildXMLElement;
import org.w3c.dom.Element;
import org.w3c.dom.Document;

public class Sequence extends DXObject{

  /**
   * Constructor
   * */
  public Sequence() {
     _setOfPeriods= new SetOfResources(4);
  }

  /**
   * get the set of periods
   * @return SetOfResources the set of periods
   * */
  public SetOfResources getSetOfPeriods(){
    return _setOfPeriods;
  }

  /**
   * */
  public Period getPeriod(int periodIndex){
    return (Period)_setOfPeriods.getResourceAt((periodIndex)).getAttach();
  }

  /**
   * */
  public Period getCurrentPeriod(){
    return getPeriod(_currentPeriodIndex) ;
  }

  /**
  * */
 public int getCurrentPeriodIndex(){
   return _currentPeriodIndex ;
  }

  /**
   * */
  public void setCurrentPeriodIndex(int curPeriodIndex){
    _currentPeriodIndex = curPeriodIndex;
  }




  /**
   * set the set of periods
   * @param SetOfResources the set of periods
   * */
  public void setSetOfPeriods(SetOfResources setOfPeriods){
    _setOfPeriods= setOfPeriods;
  }

  /**
    *read a xml tag containing a set of periods and build the resource
    * @param Element the root xml tag of the set of periods
   * */
   public void readXMLtag(Element setofPers){
     ReadXMLElement list= new ReadXMLElement();
      int size= list.getSize(setofPers,_TAGITEM);
      //System.out.println(" Periods Size: "+size);//debug
      for (int i=0; i< size; i++){
        Period period = new Period();
        Element per= list.getElement(setofPers,_TAGITEM,i);
        String periodID= list.getElementValue(per,_TAGITEM1);
        period.readXMLtag(per);
        _setOfPeriods.addResource(new Resource(periodID,period),0);
      }// end for (int i=0; i< size; i++)
   }

   /**
     * Contruct a xml element from the set of periods
     * @param Document the root xml document
     * @Element the xml tag of the set of periods
   * */
   public Element writeXMLtag(Document doc){
    BuildXMLElement xmlElt;
    try{
      xmlElt = new BuildXMLElement();
      Element eltPers= xmlElt.createElement(doc,Day._TAGITEM2);
      for (int i=0; i<_setOfPeriods.size(); i++){
        Element period= ((Period)_setOfPeriods.getResourceAt(i).getAttach()).writeXMLtag(doc);
        Element periodID=  xmlElt.createElement(doc, _TAGITEM1, _setOfPeriods.getResourceAt(i).getID());
        period= xmlElt.appendChildInElement(period, periodID);
        eltPers= xmlElt.appendChildInElement(eltPers, period);
      }
      return eltPers;
    } catch(Exception e){
      System.out.println("Sequence: "+e);//debug
      return null;
    }
   }


   private SetOfResources _setOfPeriods;
   private int _currentPeriodIndex = 0;
   static final String _TAGITEM="TTperiod";
   static final String _TAGITEM1="periodID";

}
