package dInternal.dTimeTable;

import dInternal.dUtil.DXObject;
import dInternal.dUtil.DXValue;
import dInternal.dUtil.ArrayValue;
import dInternal.dOptimization.ConflictsAttach;
import dInternal.dData.SetOfResources;
import dInternal.dData.Resource;
import xml.InPut.ReadXMLElement;
import xml.OutPut.BuildXMLElement;
import org.w3c.dom.Element;
import java.util.StringTokenizer;
import java.util.Vector;
import org.w3c.dom.Document;

public class Period extends DXObject {

  private final int MINUTES = 60;
  private int _nbStudConflict = 0;
  private int _nbInstConflict = 0;
  private int _nbRoomConflict= 0;
  private int[] _beginHour= {8,0};//_beginHour[0]= hour; _beginHour[1]= minute
  private int _priority;// 0= normal; 1= low; 2= null
  private String _error = "";
  private String _errorMessage = "XML file is corrupted";
  static final String  _TAGITEM="BeginTime";
  static final String _TAGITEM1="EndTime";
  static final String _TAGITEM2="priority";
  /**
   * contains a resource where ID is the event which is place in the period,
   * and resource attach is a conflictsattach type
   */
  private SetOfResources _eventsInPeriod;//


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
    endHour[1] = (_beginHour[1] + periodLenght) % MINUTES;//
    endHour[0] = _beginHour[0] + (_beginHour[1] + periodLenght)/MINUTES;
    return endHour;
  }

  /**
   * get number of students conflicts
   * @param int the number of students conflicts
   * */
  public int getNbStudConflict(){
    return _nbStudConflict;
  }

  /**
   * add number of students conflicts
   * @param int the number of students conflicts
   * */
  public void addNbStudConflict(int conflict){
     _nbStudConflict+=conflict;
  }

  /**
   * add number of rooms conflicts
   * @param int the number of rooms conflicts
   * */
  public void addNbRoomsConflict(int conflict){
     _nbRoomConflict+=conflict;
  }

  /**
   * add number of instructors conflicts
   * @param int the number of instructors conflicts
   * */
  public void addNbInstructorsConflict(int conflict){
     this._nbInstConflict+=conflict;
  }

  /**
   * remove number of instructors conflicts
   * @param int the number of instructors conflicts
   * */
  public void removeNbInstructorsConflict(int conflict){
     this._nbInstConflict-=conflict;
  }

  /**
   * remove number of students conflicts
   * @param int the number of students conflicts
   * */
  public void removeNbStudConflict(int conflict){
     _nbStudConflict-=conflict;
  }

  /**
   * remove number of rooms conflicts
   * @param int the number of rooms conflicts
   * */
  public void removeNbRoomsConflict(int conflict){
     _nbRoomConflict-=conflict;
  }

  /**
   * set number of instructors conflicts
   * @param int the number of instructors conflicts
   * */
  public void setNbInstructorsConflict(int conflict){
     this._nbInstConflict=conflict;
  }

  /**
   * set number of students conflicts
   * @param int the number of students conflicts
   * */
  public void setNbStudConflict(int conflict){
     _nbStudConflict=conflict;
  }

  /**
   * set number of rooms conflicts
   * @param int the number of rooms conflicts
   * */
  public void setNbRoomsConflict(int conflict){
     _nbRoomConflict=conflict;
  }

  /**
   * get number of instructors conflicts
   * @param int the number of instructors conflicts
   * */
  public int getNbInstConflict(){
    return _nbInstConflict;
  }

  /**
   * get number of rooms conflicts
   * @param int get number of rooms conflicts
   * */
  public int getNbRoomConflict(){
    return _nbRoomConflict;
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
    newPer._nbInstConflict= 0;
    newPer._nbRoomConflict=0;
    newPer._nbStudConflict=0;
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
   *Get events in period with his conflicts generated number
   * @param String the event from wich we need conflicts in period
   * @return
   */
  public SetOfResources getConflictsEventsInPeriod(String event){
    Vector inPeriod= new Vector();
//    event="EPS122.1.A.1.";//debug
    SetOfResources setOfConf = new SetOfResources(99);
    for (int i=0; i< _eventsInPeriod.size(); i++){
      Resource eventInPeriod= _eventsInPeriod.getResourceAt(i);
      String ID= eventInPeriod.getID();
      if (event.equalsIgnoreCase(eventInPeriod.getID())){
         setOfConf=((ConflictsAttach)eventInPeriod.getAttach()).getAllConflictsOfAnEvent(setOfConf);
      }else {// else if (!event.equalsIgnoreCase(_eventsInPeriod.getResourceAt(i
        int sizeIn= setOfConf.size();
       setOfConf= ((ConflictsAttach)eventInPeriod.getAttach()).getAllConflictsOfAnEvent(setOfConf,ID, event);
       if( setOfConf.size()== sizeIn)
        setOfConf.addResource(new Resource(ID,new ArrayValue(3)),1);
      }// end  else if (!event.equalsIgnoreCase(_eventsInPeriod.getResourceAt(i
    }// end for (int i=0; i< _eventsInPeriod.size(); i++){
    SetOfResources setOfRes = new SetOfResources(99);
    for(int i=0; i< setOfConf.size(); i++){
      ArrayValue array = (ArrayValue)setOfConf.getResourceAt(i).getAttach();
      String ID=setOfConf.getResourceAt(i).getID()+"  "+ array.getIntArrayValue(0)
      +"  "+ array.getIntArrayValue(1)+"  "+ array.getIntArrayValue(2);
      setOfRes.addResource(new Resource(ID,new DXValue()),1);
    }
    return setOfRes;
  }


  /**
   *
   * */
  public String toString(){
    String str=_beginHour[0]+":"+_beginHour[1]+" -- "+_priority;
    return str;
  }

  /**
   *
   */
  public void emptyEventsInPeriod(){
    _eventsInPeriod = new SetOfResources(6);
    _nbStudConflict = 0;
    _nbInstConflict = 0;
    _nbRoomConflict= 0;
  }



}