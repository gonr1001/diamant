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
  public String readXMLtag(Element setofPers){
    ReadXMLElement list= new ReadXMLElement();
    int size= list.getSize(setofPers,_TAGITEM);
    if (size == 0){
      _error = _errorMessage;
      return _error;
    }
    for (int i=0; i< size; i++){
      Period period = new Period();
      Element per= list.getElement(setofPers,_TAGITEM,i);
      String periodID= list.getElementValue(per,_TAGITEM1);
      System.out.println("period.readXMLtag(per) "+ period.readXMLtag(per));
      if (!period.readXMLtag(per).equals("")){
        _error = _errorMessage;
        return _error;
      }
      _setOfPeriods.addResource(new Resource(periodID,period),0);
    }// end for (int i=0; i< size; i++)
    return _error;
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

   /**
    * */
   public Sequence cloneSequence(){
     Sequence newSeq= new Sequence();
     newSeq._currentPeriodIndex= this._currentPeriodIndex;
     for (int i=0; i<this.getSetOfPeriods().size(); i++ ){
       Period newPer=((Period)this.getSetOfPeriods().getResourceAt(i).getAttach()).clonePeriod();
       int position=(int)getSetOfPeriods().getResourceAt(i).getKey();
       newSeq.getSetOfPeriods().setCurrentKey(position);
       newSeq.getSetOfPeriods().addResource(new Resource(getSetOfPeriods().getResourceAt(i).getID(),newPer),0);
     }
     return newSeq;
   }

   public String getError(){
    return _error;
  }


   private SetOfResources _setOfPeriods;
   private int _currentPeriodIndex = 0;
   private String _error = "";
   private String _errorMessage = "XML file is corrupted";
   static final String _TAGITEM="TTperiod";
   static final String _TAGITEM1="periodID";

}
