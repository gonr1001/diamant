package dInternal.dTimeTable;


import java.util.Vector;
import dInternal.dData.SetOfResources;
import dInternal.dData.Resource;
import xml.OutPut.BuildXMLElement;
import xml.OutPut.writeFile;
import xml.InPut.readFile;
import xml.InPut.ReadXMLElement;
import xml.Const.Tag;
import org.w3c.dom.*;
//import javax.xml.parsers.FactoryConfigurationError;

public class TTStructure {
  private Vector _ttsListeners = new Vector();
  private SetOfCycles _setOfCycles;
  //private int _periodLenght=60;
  private int _nbOfStCycles=2;
  private int _nbOfStDays=7;
  //private int _currentCycleIndex = 1;
  //DXTimeTable tag
  static final String ITEM2= "DXTimeTable";
  //subtag
  private final String [] ITEM2_subTag={"TTcycle","TTdays","TTday",
    "TTsequences","TTsequence","TTperiods","TTperiod"};
  private final String [] ITEM2_subConst={"cycleID","pLength","dayRef",
    "sequenceID","priority","BeginTime","EndTime","periodID","dayID"};

  public static final String [] _weekTable = {"Lu", "Ma", "Me", "Je", "Ve", "Sa", "Di"};

  private String _str;
  private int _col;
  private int _row;
  private static int _numberOfActivesDays=5;// monday to friday

  public TTStructure() {
    _setOfCycles= new SetOfCycles();
    _col=6;
    _row= 15;

    //CreateStandardTT("StandardTTC.xml",_nbOfStCycles,_nbOfStDays);
    loadTTStructure("StandardTTC.xml");

    saveTTStructure("test.xml");


  }

  /***/
  public static int getNumberOfActiveDays(){
    return _numberOfActivesDays;
  }

  public int getPeriodLenght(){
    return _setOfCycles.getPeriodLenght();
  }

 // public void setPeriodLenght(int periodL){
  //   _periodLenght= periodL;
  //}

 public SetOfCycles getSetOfCycles() {
    return _setOfCycles;
 }

 public void setSetOfResources(SetOfResources setOfCycles) {
 }


 public String toWrite() {
    return "";
 }

 public int rgetColumn(){
   return _col;
 }

 public int rgetRow(){
  return _row;
 }


 public int rgetBegingTime(){
   int [] b = {8, 00} ;
   return b[0];
 }
 public int rgetEndTime(){
   int [] b = {22, 0} ;
   return b[0];
 }

 public String rgetDayName(int i) {
   String [] a = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
   return a[i];
 }
 public String rloadData(String  fils) {
   System.out.println("TTStructure.loadData must load the data file  into TTStructure");
   return new String("");
 }
  public String getError() {
    return new String("");
  }

  /**
   * */
  public int getCurrentCycleIndex(){
    return _setOfCycles.getCurrentCycleIndex();
  }


  public Cycle getCurrentCycle(){
    return _setOfCycles.getCurrentCycle() ;
  }


  public void setCurrentCycleIndex(int curCyc){
    _setOfCycles.setCurrentCycleIndex(curCyc);//_currentCycleIndex = curCyc;
  }


 /**
   * get a day in a cycle
   * @param Cycle the cycle where we want to find a day
   * @param int the day reference number
   * @return Day the day or null if the day does not found
   * */
  public Day getDay(Cycle cycle, int dayRefNo ){
    return (Day)cycle.getSetOfDays().getResource(dayRefNo).getAttach();
  }

  /**
   * get a sequence in a day
   * @param Day the day where we want to find a sequence
   * @param String the sequence ID (AM, PM, EM)
   * @return Sequence the sequence or null if the sequence does not found
   * */
  public Sequence getSequence(Day day, String seqID ){
    return (Sequence)day.getSetOfSequences().getResource(seqID).getAttach();
  }

  /**
  * get a sequence in a day
  * @param Day the day where we want to find a sequence
  * @param String the sequence ID (AM, PM, EM)
  * @return Sequence the sequence or null if the sequence does not found
  * */
 public Sequence getSequence(Day day, int seqRef ){
   return (Sequence)day.getSetOfSequences().getResource(seqRef).getAttach();
  }

  /**
   * get a period
   * @param Sequence the sequence where we want to find a period
   * @param int the period reference number in  the sequence
   * @return Period the period or null if period does not found
   * */
  public Period getPeriod(Sequence seq, int periodRefNo ){
    return (Period)seq.getSetOfPeriods().getResource(
        Integer.toString(periodRefNo)).getAttach();
  }

  /**
   * get the number of days in a cycle
   * @param Cycle the cycle where we want to find the number of days
   * @return int the number of days
   * */
 /* public int getNumberOfDays(Cycle cycle){
    return cycle.getSetOfDays().size();
  }*/
  public void setNumberOfDays(long currCycle){
    Cycle cycle = (Cycle) _setOfCycles.getSetOfCycles().getResource(currCycle).getAttach();
    System.out.println("setNumberOfDays  " + cycle.getSetOfDays().size() );

    cycle.getSetOfDays().addResource(new Resource("6", new Day()),4);
    System.out.println("setNumberOfDays  " + cycle.getSetOfDays().size() );
    //cycle..getSetOfDays().size();
  }
  /**
   * get the max number of sequences in one day in a cycle
   * @param Cycle the cycle where we want to find the max number of sequences
   * @return int the max number of sequences in a day
   * */
  public int getMaxNumberOfSeqs(Cycle cycle){
    int seq=0;
    for(int i=0; i< cycle.getSetOfDays().size(); i++){
      Day day =(Day)cycle.getSetOfDays().getResourceAt(i).getAttach();
      if(seq<day.getSetOfSequences().size())
        seq= day.getSetOfSequences().size();
     }
    return seq;
  }

  /**
   * get the max number of periods in one day in a cycle
   * @param Cycle the cycle where we want to find the max number of sequences
   * @return int the max number of periods in a day
   * */
  public int getMaxNumberOfPeriodsADay(Cycle cycle){
    int maxPer=0;
    for(int i=0; i< cycle.getSetOfDays().size(); i++){
      Day day =(Day)cycle.getSetOfDays().getResourceAt(i).getAttach();
      int inc=0;
      for (int j=0; j< day.getSetOfSequences().size(); j++){
        Sequence seq= (Sequence)day.getSetOfSequences().getResourceAt(j).getAttach();
        inc+= seq.getSetOfPeriods().size();
      }
      if (maxPer< inc)
        maxPer= inc;
    }
    return maxPer;
  }

  /**
   * get a period
   * @param Cycle the cycle where we want to find the period
   * @param int the day reference number where we want to find the period
   * @param int the sequence reference number where we want to find the period
   * @param int the index of the period int the sequence
   * @return Period the period
   * */
  public Period getPeriod(Cycle cycle, int dayRefNo, int seqRefNo, int perRef){
    if(cycle!=null){
      Day day =(Day)cycle.getSetOfDays().getResource(dayRefNo).getAttach();
      if(day!=null){
        Sequence seq= (Sequence)day.getSetOfSequences().getResource(seqRefNo).getAttach();
        if (seq!=null){
          return (Period)seq.getSetOfPeriods().getResource(perRef).getAttach();
        }
      }
    }
    return null;
  }

  /**
  * get the first period
  * @param Cycle the cycle where we want to find the period
  * @return Period the first period
  * */
 public Period getFirstPeriod(Cycle cycle){
   int maxPer=0;
   if(cycle!=null){
     Day day =(Day)getCurrentCycle().getCurrentDay();//cycle.getSetOfDays().getResource(1).getAttach();
     if(day!=null){
       Sequence seq= (Sequence)day.getSetOfSequences().getResourceAt(0).getAttach();
       return (Period)seq.getSetOfPeriods().getResourceAt(0).getAttach();
     }
   }
   return null;
  }

  /**
  * get the last period
  * @param Cycle the cycle where we want to find the period
  * @return Period the last period
  * */
 public Period getLastPeriod(Cycle cycle){
   int maxPer=0;
   if(cycle!=null){
     Day day =(Day)getCurrentCycle().getCurrentDay();
     if(day!=null){
       Sequence seq= (Sequence)day.getSetOfSequences().getResourceAt(getMaxNumberOfSeqs(cycle)-1).getAttach();
       return (Period)seq.getSetOfPeriods().getResourceAt(seq.getSetOfPeriods().size()-1).getAttach();
     }
   }
   return null;
  }

  public Period rgetPeriod( ){
    return new Period();
  }

  /**
   * Create a sequence of periods
   * @param Document the xml document where we are working
   * @param String String the sequence ID (AM, PM or EM= evening)
   * @param int the number of periods in the sequence
   * @param int the lenght of each period in the sequence
   * @param int[2] the begin time of the period. the first element of the table
   * is the our, and the second is the minutes
   * @param int the prioryti of each period
   * @return Element the sequence element
   * */
  private Element CreateSeqPeriods(Document doc, String seqID, int nbOfPeriods, int periodLenght, int[] beginTime, int priority){
    //add PM periods
    BuildXMLElement xmlElt;
    try{
      xmlElt = new BuildXMLElement();
      Element eltSeq= xmlElt.createElement(doc,ITEM2_subTag[4]);
      Element eltPers= xmlElt.createElement(doc,ITEM2_subTag[5]);
      int hour=beginTime[0];
      for (int i=0; i<nbOfPeriods; i++){
        int mn= (beginTime[1]+periodLenght*i)%60;//
        hour=beginTime[0]+(beginTime[1]+periodLenght*i)/60;
        String time= hour+":"+mn;
        Element child0=xmlElt.createElement(doc,ITEM2_subConst[5],time);
        int mn1= (mn+periodLenght)%60;//
        int hour1=hour+(mn+periodLenght)/60;
        time= hour1+":"+mn1;
        Element child01=xmlElt.createElement(doc,ITEM2_subConst[6],time);
        Element child1=xmlElt.createElement(doc,ITEM2_subConst[4],Integer.toString(priority));
        Element child2=xmlElt.createElement(doc,ITEM2_subConst[7],Integer.toString(i+1));//
        Element eltPer= xmlElt.createElement(doc,ITEM2_subTag[6]);
        eltPer= xmlElt.appendChildInElement(eltPer, child2);
        eltPer= xmlElt.appendChildInElement(eltPer, child0);
        eltPer= xmlElt.appendChildInElement(eltPer, child01);
        eltPer= xmlElt.appendChildInElement(eltPer, child1);
        eltPers=xmlElt.appendChildInElement(eltPers, eltPer);
      }
      Element childSeq=xmlElt.createElement(doc,ITEM2_subConst[3],seqID);
      eltSeq= xmlElt.appendChildInElement(eltSeq, childSeq);
      eltSeq= xmlElt.appendChildInElement(eltSeq, eltPers);

      return eltSeq;
    } catch(Exception e){
      System.out.println("TTStructure: "+e);//debug
      return null;
    }
  }

  /**
   * Create and save a standard TimeTable
   * @param String the timetable file name
   * @param int the number of cycles
   * @param int the number of days in each cycle
   * @return boolean the result of the operation
   * */
  public boolean CreateStandardTT(String fileName, int nbOfCycles, int nbOfDays){
    BuildXMLElement wr;
    try{
      wr= new BuildXMLElement();
      Document doc= wr.getNewDocument();
      Element eltTT= wr.createElement(doc,ITEM2);
      Element eltCycle;
      Element eltDays;
      Element eltDay;
      Element eltSeqs;
      Element eltSeq;
      for (int cyc=0; cyc<nbOfCycles; cyc++){
        eltCycle= wr.createElement(doc,ITEM2_subTag[0]);
        eltDays= wr.createElement(doc,ITEM2_subTag[1]);
        for (int day=0; day<nbOfDays; day++){
          eltDay= wr.createElement(doc,ITEM2_subTag[2]);
          eltSeqs= wr.createElement(doc,ITEM2_subTag[3]);

          //add AM periods
          int [] beginT={8,15};
          eltSeq= CreateSeqPeriods(doc,"AM",4,60,beginT,0);
          eltSeqs= wr.appendChildInElement(eltSeqs, eltSeq);
          //add PM periods
          beginT[0]=13; beginT[1]=30;
          eltSeq= CreateSeqPeriods(doc,"PM",5,60,beginT,0);
          eltSeqs= wr.appendChildInElement(eltSeqs, eltSeq);
          //add Evening periods
          beginT[0]=19; beginT[1]=00;
          eltSeq= CreateSeqPeriods(doc,"EM",3,60,beginT,1);
          eltSeqs= wr.appendChildInElement(eltSeqs, eltSeq);

          // add sequences in a day
          eltDay= wr.appendChildInElement(eltDay,eltSeqs);
          Element childDay=wr.createElement(doc,ITEM2_subConst[2],Integer.toString(day+1));
          String dayID= _weekTable[day%_numberOfActivesDays];
          Element childDayID=wr.createElement(doc,ITEM2_subConst[8],dayID);
          eltDay= wr.appendChildInElement(eltDay,childDay);
          eltDay= wr.appendChildInElement(eltDay,childDayID);
          //eltDays= wr.appendChildInElement(eltDays, childDay);
          eltDays= wr.appendChildInElement(eltDays, eltDay);
        }// end for (day)
        Element childCycle=wr.createElement(doc,ITEM2_subConst[0],Integer.toString(cyc+1));
        eltCycle= wr.appendChildInElement(eltCycle, childCycle);
        childCycle=wr.createElement(doc,ITEM2_subConst[1],Integer.toString(60));
        eltCycle= wr.appendChildInElement(eltCycle, childCycle);
        eltCycle= wr.appendChildInElement(eltCycle, eltDays);
        eltTT= wr.appendChildInElement(eltTT, eltCycle);
      }//for (int cyc=0; cyc<3; cyc++)

      // create document and write in the file
      doc= wr.buildDOM(doc,eltTT);
      writeFile.write(doc,fileName);
      return true;
    } catch(Exception e){
      System.out.println("TTStructure: "+e);//debug
      return false;
    }
  }// end of CreateStandardTT method

  /**
   * it load the time table structure
   * @param String the xml file containing the timetable structure
   * @return String the error message, empty if it does not found error
   * */
  public String loadTTStructure(String fileName){
    readFile xmlFile;
    Element root, item, ID;
    try{
      xmlFile = new readFile();
      Document  doc = xmlFile.getDocumentFile(fileName);
      ReadXMLElement list= new ReadXMLElement();
      root= list.getRootElement(doc);
      _setOfCycles.readXMLtag(root);
    }catch(Exception e){
      System.out.println(e);
      return e.toString();
    }
    return "";

  }

  /**
   * it save the time table structure
   * @param String the xml file where the timetable structure must be saved
   * @return String the error message, empty if it does not found error
   * */
   public String saveTTStructure(String fileName){
    BuildXMLElement wr;
    try{
      wr= new BuildXMLElement();
      Document doc= wr.getNewDocument();
      Element ttStruc= _setOfCycles.writeXMLtag(doc);
      // create document and write in the file
      doc= wr.buildDOM(doc,ttStruc);
      writeFile.write(doc,fileName);
      return "";
    } catch(Exception e){
      return e.toString();//debug
    }

   }

   public void modification() {
     sendEvent();
     System.out.println("Sending events");
   }

   public void sendEvent() {
     TTStructureEvent event = new TTStructureEvent(this);
     for (int i=0; i< _ttsListeners.size(); i++) {
       TTStructureListener ttsl = (TTStructureListener) _ttsListeners.elementAt(i);
       ttsl.changeInTTStructure(event);
     }
   }

   public synchronized void addTTStructureListener(TTStructureListener ttsl) {
     if (_ttsListeners.contains(ttsl)){
       return;
     }
     _ttsListeners.addElement(ttsl);
     System.out.println("addTTStructureListener");
   }

   public synchronized void removeTTStructureListener(TTStructureListener ttsl) {
     _ttsListeners.removeElement(ttsl);
   }

}
