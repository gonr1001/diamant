/* Generated by Together */

package dInternal.dTimeTable;

import dInternal.dData.SetOfResources;
import dInternal.dData.Resource;
import dInternal.dUtil.DXObject;
import xml.InPut.ReadXMLElement;
import xml.OutPut.BuildXMLElement;
import org.w3c.dom.Element;
import org.w3c.dom.Document;

public class Day extends DXObject{

  /**
   * Constructor
   * */
  public Day() {
    _setOfSequences = new SetOfResources(4);
  }

  /**
   * get the set of sequences
   * @return SetOfResources the set of sequences
   * */
  public SetOfResources getSetOfSequences(){
    return _setOfSequences;
  }

  /**
   * @param int the index of the sequence
   * */
  public Sequence getSequence(int sequenceIndex){
    return (Sequence)_setOfSequences.getResourceAt((sequenceIndex)).getAttach();
  }

  /**
   * */
  public Sequence getCurrentSequence(){
    return getSequence(_currentSequenceIndex) ;
  }

  /**
  * */
 public int getCurrentSequenceIndex(){
   return _currentSequenceIndex ;
  }

  /**
   * */
  public void setCurrentSequenceIndex(int curSequenceIndex){
    _currentSequenceIndex = curSequenceIndex;
  }



  /**
   * set the set of sequences
   * @param SetOfResources the set of sequences
   * */
  public void setSetOfSequences(SetOfResources setOfSequences){
    _setOfSequences= setOfSequences;
  }

  /**
    *read a xml tag containing a set of sequences and build the resource
    * @param Element the root xml tag of the set of sequences
   * */
   public void readXMLtag(Element setofSeqs){
     ReadXMLElement list= new ReadXMLElement();
      String ID="";
      int size= list.getSize(setofSeqs,_TAGITEM);
      //System.out.println(" Sequnces Size: "+size);//debug
      for (int i=0; i< size; i++){
        Sequence setOfPeriods = new Sequence();
        Element sequence= list.getElement(setofSeqs,_TAGITEM,i);
        ID= list.getElementValue(sequence,_TAGITEM1);
        //System.out.println(" Sequences ID: "+ID);//debug
        Element periods= list.getElement(sequence,_TAGITEM2,0);
        setOfPeriods.readXMLtag(periods);
        _setOfSequences.addResource(new Resource(ID,setOfPeriods),0);
      }// end for (int i=0; i< size; i++)
   }

   /**
     * Contruct a xml element from the set of sequences
     * @param Document the root xml document
     * @Element the xml tag of the set of sequences
   * */
   public Element writeXMLtag(Document doc){
    BuildXMLElement xmlElt;
    try{
      xmlElt = new BuildXMLElement();
      Element eltDay= xmlElt.createElement(doc,Cycle._TAGITEM2);
      for (int i=0; i<_setOfSequences.size(); i++){
        Element eltSeqs= xmlElt.createElement(doc,_TAGITEM);
        Element sequence= ((Sequence)_setOfSequences.getResourceAt(i).getAttach()).writeXMLtag(doc);
        Element sequenceID= xmlElt.createElement(doc,_TAGITEM1,_setOfSequences.getResourceAt(i).getID());
        eltSeqs= xmlElt.appendChildInElement(eltSeqs, sequenceID);
        eltSeqs= xmlElt.appendChildInElement(eltSeqs, sequence);
        eltDay=xmlElt.appendChildInElement(eltDay, eltSeqs);
      }
      //eltDay=xmlElt.appendChildInElement(eltDay, sequence);
      return eltDay;
    } catch(Exception e){
      System.out.println("Day: "+e);//debug
      return null;
    }
   }

   /**
  * */
 public Day cloneDay(){
   Day newDay= new Day();
   newDay._currentSequenceIndex= this._currentSequenceIndex;
   for(int i=0; i< this.getSetOfSequences().size(); i++){
     String id= getSetOfSequences().getResourceAt(i).getID();
     Sequence seq= ((Sequence)this.getSetOfSequences().getResourceAt(i).getAttach()).cloneSequence();
     newDay.getSetOfSequences().addResource(new Resource(id,seq),0);
   }// end for(int i=0; i< day.getSetOfSequences().size(); i++)

   return newDay;
  }


  private SetOfResources _setOfSequences;
  private int _currentSequenceIndex = 0;
  static final String _TAGITEM="TTsequence";
  static final String _TAGITEM1="sequenceID";
  static final String _TAGITEM2="TTperiods";
}