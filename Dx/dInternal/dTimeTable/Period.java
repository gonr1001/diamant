package dInternal.dTimeTable;

import dInternal.dUtil.DXObject;
import dInternal.dData.SetOfResources;
import dInternal.dData.Resource;
import xml.InPut.ReadXMLElement;
import xml.OutPut.BuildXMLElement;
import org.w3c.dom.Element;
import java.util.StringTokenizer;
import org.w3c.dom.Document;

public class Period extends DXObject {

  private int nbStudConflict = 0;
  private int nbInstConflict = 0;
  private int nbRoomConflict= 0;
  private int[] _beginHour= {8,0};//_beginHour[0]= hour; _beginHour[1]= minute
  private int _priority;// 0= normal; 1= low; 2= null
  private String _error = "";
  private String _errorMessage = "XML file is corrupted";
  static final String  _TAGITEM="BeginTime";
  static final String _TAGITEM1="EndTime";
  static final String _TAGITEM2="priority";
  private SetOfResources _eventsInPeriod;

  /**
   * Constructor
   * */
  public Period() {
    _eventsInPeriod = new SetOfResources(6);
  }

  /**
   * set the begin hour of the period
   * @param int the hour
   * @param int the minute
   * */
  public void setBeginHour(int hour, int minute){
    _beginHour[0] = hour;
    _beginHour[1] = minute;
  }

  /**
   * set the priority of the periode
   * @param int the priority of the period
   * */
  public void setPriority(int prior){
    _priority= prior;
  }

  /**
   * get the begin hour of the period
   * @return int[2] the table of the begin hour. The in range 0 is the hour
   * and the element in the range 1 is the minutes
   * */
  public int[] getBeginHour(){
    return _beginHour;
  }

  /**
   * get the end hour of the period
   * @return int[2] the table of the begin hour. The in range 0 is the hour
   * and the element in the range 1 is the minutes
   * */
  public int[] getEndHour(int periodLenght){
    int[] endHour={0,0};
    endHour[1]= (_beginHour[1]+periodLenght)%60;//
    endHour[0]=_beginHour[0]+(_beginHour[1]+periodLenght)/60;
    return endHour;
  }

  /**
   * get number of students conflicts
   * @param int the number of students conflicts
   * */
  public int getNbStudConflict(){
    return nbStudConflict;
  }

  /**
   * add number of students conflicts
   * @param int the number of students conflicts
   * */
  public void addNbStudConflict(int conflict){
     nbStudConflict+=conflict;
  }

  /**
   * remove number of students conflicts
   * @param int the number of students conflicts
   * */
  public void removeNbStudConflict(int conflict){
     nbStudConflict-=conflict;
  }

  /**
   * get number of instructors conflicts
   * @param int the number of instructors conflicts
   * */
  public int getNbInstConflict(){
    return nbInstConflict;
  }

  /**
   * get number of rooms conflicts
   * @param int get number of rooms conflicts
   * */
  public int getNbRoomConflict(){
    return nbRoomConflict;
  }

  /**
   * get priority of the period
   * @param int the priority of the period
   * */
  public int getPriority(){
    return _priority;
  }

  /**
    *read a xml tag containing a period and build the resource
    * @param Element the root xml tag of a period
   * */
  public String readXMLtag(Element setPeriod){
    ReadXMLElement list= new ReadXMLElement();
    Period period = new Period();
    String begin, end, prior;
    begin= list.getElementValue(setPeriod,_TAGITEM);
    StringTokenizer time= new StringTokenizer(begin,":");
    end= list.getElementValue(setPeriod,_TAGITEM1);
    prior= list.getElementValue(setPeriod,_TAGITEM2);
    _beginHour[0]= Integer.parseInt(time.nextToken());
    _beginHour[1]= Integer.parseInt(time.nextToken());
    _priority= Integer.parseInt(prior);
    if (begin == null || end == null || prior == null){
      _error = _errorMessage;
      return _error;
    }
    return _error;

    //System.out.println(" Period properties -- begin: "+_beginHour[0]+"%"+_beginHour[1]+" end: "+end+" Priority: "+prior);//debug
  }

     /**
       * Contruct a xml element from this period
       * @param Document the root xml document
       * @Element the xml tag of this period
   * */
   public Element writeXMLtag(Document doc){
    BuildXMLElement xmlElt;
    try{
      xmlElt = new BuildXMLElement();
      String time= _beginHour[0]+":"+_beginHour[1];
      Element eltPer= xmlElt.createElement(doc,Sequence._TAGITEM);
        Element child0=xmlElt.createElement(doc,_TAGITEM,time);
        Element child1=xmlElt.createElement(doc,_TAGITEM2,Integer.toString(_priority));
        eltPer= xmlElt.appendChildInElement(eltPer, child0);
        eltPer= xmlElt.appendChildInElement(eltPer, child1);
      return eltPer;
    } catch(Exception e){
      System.out.println("Period: "+e);//debug
      return null;
    }
   }

  /**
   * return the number of events in this period
   * */
   public int getNumberOfEvents(){
    return _eventsInPeriod.size();
  }

  /**
   * */
  public Period clonePeriod(){
    Period newPer= new Period();
    newPer.nbInstConflict= 0;
    newPer.nbRoomConflict=0;
    newPer.nbStudConflict=0;
    newPer._priority= this._priority;
    newPer._beginHour= this._beginHour;
    return newPer;
  }

  /**
   *
   * @return
   */
  public SetOfResources getEventsInPeriod(){
    return _eventsInPeriod;
  }

  /**
   *
   * */
  public String toString(){

    String str=_beginHour[0]+":"+_beginHour[1]+" -- "+_priority;
    return str;
  }



}