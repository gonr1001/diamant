/* Generated by Together */

package dInternal.dTimeTable;

import dInternal.dData.SetOfResources;
import dInternal.dTimeTable.TTStructure;
import dInternal.dData.Resource;
import dInternal.dUtil.*;

import java.lang.Math;

import xml.InPut.ReadXMLElement;
import xml.OutPut.BuildXMLElement;
import org.w3c.dom.Element;
import org.w3c.dom.Document;

public class Cycle extends DXObject{

  //********Alex
  //private Day _currentDay;
  private int _currentDayIndex=0;
  //********Alex/


  public Cycle() {
    _setOfDays = new SetOfResources(4);
  }

  /**
   *
   * */
  public void addDays(int nbDays){
    int size= _setOfDays.size();
    Resource day= _setOfDays.getResourceAt(size-1);
    String lastDayID= day.getID();
    int lastIndWeek=0;
    for(int i=0; i< TTStructure._weekTable.length; i++)
      if(TTStructure._weekTable[i].equalsIgnoreCase(lastDayID)){
        lastIndWeek=i;
        break;
      }

    for (int i=size; i< (size+nbDays); i++){
      lastIndWeek++;
      String dayID= TTStructure._weekTable[lastIndWeek%TTStructure.getNumberOfActiveDays()];
      _setOfDays.setCurrentKey(i+1);
      _setOfDays.addResource(new Resource(dayID,((Day)day.getAttach()).cloneDay()),0);
    }

  }

  /**
   *
   * */
  public void removeDays(int nbDays){
    int size= _setOfDays.size();
    Day day= (Day)_setOfDays.getResourceAt(size-1).getAttach();
    for (int i=size; i> (size-nbDays); i--){
      //String dayID= TTStructure._weekTable[i%TTStructure.getNumberOfActiveDays()];
      //_setOfDays.setCurrentKey(i+1);
      //_setOfDays.addResource(new Resource(dayID,day),0);
      _setOfDays.removeResourceAt(i-1);
    }
  }


  /**
     * get the set of days
     * @return SetOfResources the set of days
     * */
    public SetOfResources getSetOfDays(){
      return _setOfDays;
    }

    /**
     * set the set of days
     * @param SetOfResources the set of days
     * */
    public void setSetOfDays(SetOfResources setOfDays){
      _setOfDays= setOfDays;
    }

    /**
   * get the number of days in a cycle
   * @param Cycle the cycle where we want to find the number of days
   * @return int the number of days
   * */
  public int getNumberOfDays(){
    return _setOfDays.size();
  }

  /**
   * @param int the index of the day
   * */
  public Day getDayByIndex(int dayIndex){
    return (Day)_setOfDays.getResourceAt((dayIndex)).getAttach();
  }

  /**
   * */
  public Day getCurrentDay(){
    return getDayByIndex(_currentDayIndex) ;
  }

  /**
  * */
 public int getCurrentDayIndex(){
   return _currentDayIndex ;
  }

  /**
   * */
  public void setCurrentDayIndex(int curDayIndex){
    _currentDayIndex = curDayIndex;
  }



    /**
    *read a xml tag containing a set of days and build the resource
    * @param Element the root xml tag of the set of days
   * */
  public String readXMLtag(Element setofDays){
    ReadXMLElement list= new ReadXMLElement();
    String ID="";
    String key="";
    int size= list.getSize(setofDays,_TAGITEM);
    if (size == 0){
      _error = _errorMessage;
      return _error;
    }
    for (int i=0; i< size; i++){
      Day setOfSequences = new Day();
      Element day= list.getElement(setofDays,_TAGITEM,i);
      ID= list.getElementValue(day,_TAGITEM4);
      key= list.getElementValue(day,_TAGITEM1);
      Element sequences= list.getElement(day,_TAGITEM2,0);
      if (!setOfSequences.readXMLtag(sequences).equals("")){;
      _error = _errorMessage;
      return _error;
      }
      _setOfDays.setCurrentKey(Integer.parseInt(key));
      _setOfDays.addResource(new Resource(ID,setOfSequences),0);
    }// end for (int i=0; i< size; i++)
    return _error;
  }

    /**
    * Contruct a xml element from the set of days
    * @param Document the root xml document
    * @Element the xml tag of the set of days
   * */
   public Element writeXMLtag(Document doc){
    BuildXMLElement xmlElt;
    try{
      xmlElt = new BuildXMLElement();
      Element eltDays= xmlElt.createElement(doc,TTStructure._TAGITEM3);
      for (int i=0; i<_setOfDays.size(); i++){
        Element eltDay= xmlElt.createElement(doc,Cycle._TAGITEM);
        Element day= ((Day)_setOfDays.getResourceAt(i).getAttach()).writeXMLtag(doc);
        Element dayID= xmlElt.createElement(doc,_TAGITEM4,_setOfDays.getResourceAt(i).getID());
        Element dayKey= xmlElt.createElement(doc,_TAGITEM1,Integer.toString((int)_setOfDays.getResourceAt(i).getKey()));
        eltDay= xmlElt.appendChildInElement(eltDay, day);
        eltDay= xmlElt.appendChildInElement(eltDay, dayID);
        eltDay= xmlElt.appendChildInElement(eltDay, dayKey);
        eltDays= xmlElt.appendChildInElement(eltDays, eltDay);
      }
      return eltDays;
    } catch(Exception e){
      System.out.println("Cycle: "+e);//debug
      return null;
    }
   }

   /**
    *
    * @return
    */
   public String getError(){
    return _error;
  }

  /**
  * get the first period
  * @param Cycle the cycle where we want to find the period
  * @return Period the first period
  * */
 public Period getFirstPeriod(){
   int maxPer=0;
     Day day =(Day)this.getCurrentDay();//cycle.getSetOfDays().getResource(1).getAttach();
     if(day!=null){
       Sequence seq= (Sequence)day.getSetOfSequences().getResourceAt(0).getAttach();
       return (Period)seq.getSetOfPeriods().getResourceAt(0).getAttach();
     }
   return null;
  }

  /**
   * get a day in a cycle
   * @param Cycle the cycle where we want to find a day
   * @param int the day reference number
   * @return Day the day or null if the day does not found
   * */
  public Day getDayByRefNo(int dayRefNo ){
    return (Day)this.getSetOfDays().getResource(dayRefNo).getAttach();
  }

  /**
 * get the last period
 * @param Cycle the cycle where we want to find the period
 * @return Period the last period
 * */
public Period getLastPeriod(){
  int maxPer=0;
  Period lastPer= new Period();
    for (int i=0; i< this.getNumberOfDays(); i++){
      Day day =(Day)this.getSetOfDays().getResourceAt(i).getAttach();
      Sequence seq= (Sequence)day.getSetOfSequences().getResourceAt(getMaxNumberOfSeqs()-1).getAttach();
      Period per = (Period)seq.getSetOfPeriods().getResourceAt(seq.getSetOfPeriods().size()-1).getAttach();
      if(DXToolsMethods.compareTabsHour(lastPer.getBeginHour(),per.getBeginHour())==-1)
        lastPer= per;
      //return (Period)seq.getSetOfPeriods().getResourceAt(seq.getSetOfPeriods().size()-1).getAttach();
    }// end for (int i=0; i< cycle.getNumberOfDays(); i++)
  return lastPer;
 }

 /**
   * get the max number of sequences in one day in a cycle
   * @param Cycle the cycle where we want to find the max number of sequences
   * @return int the max number of sequences in a day
   * */
  public int getMaxNumberOfSeqs(){
    int seq=0;
    for(int i=0; i< getSetOfDays().size(); i++){
      Day day =(Day)getSetOfDays().getResourceAt(i).getAttach();
      if(seq<day.getSetOfSequences().size())
        seq= day.getSetOfSequences().size();
     }
    return seq;
  }

  /**
   * get the hour of periods in one day in a cycle
   * @param Cycle the cycle where we want to find the max number of sequences
   * @return int the max number of periods in a day
   * */
  public String[] getHourOfPeriodsADay(){
    String[] time= new String[getMaxNumberOfPeriodsADay()+getMaxNumberOfSeqs()-1];
    int maxPer=0;
      Day day =(Day)getCurrentDay();
      int inc=0;
      for (int i=0; i< day.getSetOfSequences().size(); i++){
        Sequence seq= (Sequence)day.getSetOfSequences().getResourceAt(i).getAttach();
        Period per= new Period();
        for (int j=0; j< seq.getSetOfPeriods().size(); j++){
          per = (Period)seq.getSetOfPeriods().getResourceAt(j).getAttach();
          time[inc]= per.getBeginHour()[0]+":"+per.getBeginHour()[1];
          inc++;
        }
      }
    return time;
  }

  /**
   * get the max number of periods in one day in a cycle
   * @param Cycle the cycle where we want to find the max number of sequences
   * @return int the max number of periods in a day
   * */
  public int getMaxNumberOfPeriodsADay(){
    int maxPer=0;
    for(int i=0; i< getSetOfDays().size(); i++){
      Day day =(Day)getSetOfDays().getResourceAt(i).getAttach();
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
 public Period getPeriodByIndex( int dayIndex, int seqIndex, int perIndex){
     Day day =(Day)getSetOfDays().getResourceAt(dayIndex).getAttach();
     if(day!=null){
       Sequence seq= (Sequence)day.getSetOfSequences().getResourceAt(seqIndex).getAttach();
       if (seq!=null){
         return (Period)seq.getSetOfPeriods().getResourceAt(perIndex).getAttach();
       }
     }
   return null;
  }

  /**
  * get a period
  * @param Cycle the cycle where we want to find the period
  * @param int the day reference number where we want to find the period
  * @param int the sequence reference number where we want to find the period
  * @param int the index of the period int the sequence
  * @return Period the period
  * */
 public Period getPeriodByKey( long dayKey, long seqKey, long perKey){
     Day day =(Day)getSetOfDays().getResource(dayKey).getAttach();
     if(day!=null){
       Sequence seq= (Sequence)day.getSetOfSequences().getResource(seqKey).getAttach();
       if (seq!=null){
         return (Period)seq.getSetOfPeriods().getResource(perKey).getAttach();
       }
     }
   return null;
  }

  /**
  * get a period position in a day
  * @param Cycle the cycle where we want to find the period
  * @param int the day reference number where we want to find the period
  * @param int the sequence reference number where we want to find the period
  * @param int the index of the period int the sequence
  * @return int the period pesition
  * */
 public int getPeriodPositionInDay( long dayKey, long seqKey, long perKey){
     Day day =(Day)getSetOfDays().getResource(dayKey).getAttach();
     int periodPos=-1;
     if(day!=null){
       Sequence seq= (Sequence)day.getSetOfSequences().getResource(seqKey).getAttach();
       if (seq!=null){
         periodPos=0;
         for (int i=(int)seqKey-1; i>0; i--)
           periodPos+= ((Sequence)day.getSetOfSequences().getResource(i).getAttach()).getSetOfPeriods().size();
         return periodPos+ (int)perKey;
       }
     }
   return -1;
  }

  /**
  * check adjacency of periods
  * @param long the day reference number where we want to find the period
  * @param long the sequence reference number where we want to find the period
  * @param long the index of the begining period in the sequence
  * @param int the duration where we want to check adjacency of periods
  * @return Period the period
  * */
 public boolean isPeriodContiguous( long dayKey, long seqKey, long beginperKey, int duration, int[] avoidPriority){
   Day day =(Day)getSetOfDays().getResource(dayKey).getAttach();
   if(day!=null){
     Sequence seq= (Sequence)day.getSetOfSequences().getResource(seqKey).getAttach();
     if (seq!=null){
       int index = seq.getSetOfPeriods().getIndexOfResource(beginperKey);
       if( (index!=-1) && ( (index+duration-1) < seq.getSetOfPeriods().size() ) ){
         for(int i=0; i< seq.getSetOfPeriods().size(); i++){
           for (int j=0; j< avoidPriority.length; j++){
             if (((Period)seq.getSetOfPeriods().getResourceAt(i).getAttach()).getPriority()==avoidPriority[j])
               return false;
           }
         }
         return true;
       }
     }
   }
   return false;
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
   * get a period
   * @param int [3] the day time index rank: 0= dayindex, 1= hour , 2= minute
   * @return String the period complete key a.b.c where a= day key, b= sequence key
   * c= period key
   * */
  public String getPeriod(int[] dayTime ){
    int error = 1000;
    String key="";
    Day day;
    if(_setOfDays.getResource(dayTime[0])!=null){
      day =(Day)_setOfDays.getResource(dayTime[0]).getAttach();
      for (int i=0; i< day.getSetOfSequences().size(); i++){
        Sequence seq = (Sequence)day.getSetOfSequences().getResourceAt(i).getAttach();
        for(int j=0; j< seq.getSetOfPeriods().size(); j++){
          Period per = (Period)seq.getSetOfPeriods().getResourceAt(j).getAttach();
          int newError = Math.abs((per.getBeginHour()[0]-dayTime[1])*60+ per.getBeginHour()[1] -dayTime[2]);
          if(newError <error){
            key = _setOfDays.getResource(dayTime[0]).getKey()+"."+
                  day.getSetOfSequences().getResourceAt(i).getKey()+"."+
                  seq.getSetOfPeriods().getResourceAt(j).getKey();
            error = newError;
          }
        }// for(int j=0; j< seq.getSetOfPeriods().size(); j++)
      }// end for (int i=0; i< day.getSetOfSequences().size(); i++)
    }// end if(day!=null)
    return key;
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
   *
   * @return int[0]= number of instructors conflicts, int[1]= rooms, int[2]= students
   */
  public int[] getTotalNumberOfConflicts(){
    int [] conf={0,0,0};
    for (int i=0; i< getSetOfDays().size(); i++){
      Day day = (Day)getSetOfDays().getResourceAt(i).getAttach();
      for(int j=0; j< day.getSetOfSequences().size(); j++){
        Sequence seq = (Sequence)day.getSetOfSequences().getResourceAt(j).getAttach();
        for(int k=0; k< seq.getSetOfPeriods().size(); k++){
          conf[0]+=((Period)seq.getSetOfPeriods().getResourceAt(k).getAttach()).getNbInstConflict();
          conf[1]+=((Period)seq.getSetOfPeriods().getResourceAt(k).getAttach()).getNbRoomConflict();
          conf[2]+=((Period)seq.getSetOfPeriods().getResourceAt(k).getAttach()).getNbStudConflict();
        }// end for(int k=0; k< seq.getSetOfPeriods().size(); k++)
      }// end for(int j=0; j< day.getSetOfSequences().size(); j++)

    }//end for (int i=0; i< getSetOfDays().size(); i++)
    return conf;
  }

  /**
   *
   */
  public void emptyAllEventsInPeriod(){
    for (int i=0; i< getSetOfDays().size(); i++){
      Day day = (Day)getSetOfDays().getResourceAt(i).getAttach();
      for(int j=0; j< day.getSetOfSequences().size(); j++){
        Sequence seq = (Sequence)day.getSetOfSequences().getResourceAt(j).getAttach();
        for(int k=0; k< seq.getSetOfPeriods().size(); k++){
          ((Period)seq.getSetOfPeriods().getResourceAt(k).getAttach()).emptyEventsInPeriod();
        }// end for(int k=0; k< seq.getSetOfPeriods().size(); k++)
      }// end for(int j=0; j< day.getSetOfSequences().size(); j++)
    }//end for (int i=0; i< getSetOfDays().size(); i++)
  }

  private SetOfResources _setOfDays;
  private int _periodLength;
  private String _error = "";
  private String _errorMessage = "XML file is corrupted";
  static final String _TAGITEM="TTday";
  static final String _TAGITEM1="dayRef";
  static final String _TAGITEM2="TTsequences";
  static final String _TAGITEM4="dayID";
}
