package dInternal.dDataTxt;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */

import java.util.StringTokenizer;
import java.util.Vector;

import dConstants.DConst;
//import dInterface.dUtil.DXTools;
import dInternal.DModel;
import dInternal.dOptimization.ConflictsAttach;
import dInternal.dOptimization.EventAttach;
import dInternal.dTimeTable.Day;
import dInternal.dTimeTable.Period;
import dInternal.dTimeTable.Sequence;
import dInternal.dUtil.DXToolsMethods;
import dInternal.dUtil.DXValue;

public class StandardReportData {

  private DModel _dm;
  //private int _AHOUR=60;// a hour= 60 minutes
  private int STATE1=300;
  private int STATE2=600;
  private int STATE3=100;
  //private StringBuffer _HOURSEPARATOR= new StringBuffer("h");
  /*
  *_activitiesReport is a string where each line contains more informations separeted
  * by a ";" separator
  * token number 0= activity name, 1= type name, 2= section name, 3= unity name, 4= duration of the activity
  * 5= day number where activity is assign, 6= day name where activity is assign
  * 7= begin hour of the activity, 8= end hour of the activity, 9= instructor name
  * 10= room name, 11=size of Students in an event
  */
  private String _activitiesReport="";
   /*
  *_studentsReport is a string where each line contains more informations separeted
  * by a ";" separator
  * token number 0= student matricule, 1= student program, 2= student name, 3= student courses choice
  *--------------------------
  * the token number 3 contains more same type of informations separated by "," separator.
  * These subTokens contains more informations separated by a "-" separator
  * subtoken 0= unity name, 1= day number where this unity is place, 2= day name where this unity is place
  *3= begining hour of this unity
  */
  private String _studentsReport= "";

  /**
   *_conflictsReport is a string where each line contains more informations separeted
   * by a ";" separator
   * token number 0= day number, 1= day name, 2= sequence Id, 3= period id, 4= period begin hour
   * 5= first event in conflict, 6= event in conflict with the first,
   * 7= number of conflicts, 8= type of conflicts
   * 9= conflict description
   * --------------------------
   * description of the token number 8: type of conflicts
   * type= 0: student conflict
   * type= 1: room conflict
   * type= 2: instructor conflict
   */
  private String _conflictsReport= "";

  /**
   * Constructor
   * @param dm
   */
  public StandardReportData(DModel dm) {
    _dm = dm;
    _activitiesReport= buildActivitiesReport();
    
    _studentsReport = buildStudentsReport();
    
    _conflictsReport= buildConflictsReport();
    

    _dm.getProgressBarState().setIntValue(1000);
   // System.out.println("**** Final Change progess bar: "+ _dm.getProgressBarState().getIntValue());
  }

  /**
   *
   * @return
   */
  private String buildActivitiesReport(){
    //StringBuffer actlist= new StringBuffer("");
    //int size=_dm.getSetOfActivities().size();
    /*
     * TODO delete class
    for (int i=0; i<size ; i++){
      _dm.getProgressBarState().setIntValue(STATE1*i/size);
      //System.out.println("Change progess bar: "+ _dm.getProgressBarState().getIntValue());
     
     Activity activity = (Activity)_dm.getSetOfActivities().getResourceAt(i).getAttach();
      if (activity.getActivityVisibility()){
        for(int j=0; j< activity.getSetOfTypes().size(); j++){
          Type nature = (Type)(activity.getSetOfTypes().getResourceAt(j)).getAttach();
          for (int k=0; k< nature.getSetOfSections().size(); k++){
            Section section= (Section)nature.getSetOfSections().getResourceAt(k).getAttach();
            for(int l=0; l< section.getSetOfUnities().size(); l++){
              Unity bloc= (Unity)section.getSetOfUnities().getResourceAt(l).getAttach();
              Assignment currentCycAss = (Assignment)bloc.getSetOfAssignments(
                  ).getResourceAt(_dm.getTTStructure().getCurrentCycleIndex()).getAttach();
              StringBuffer hour= new StringBuffer("00");
			               hour.append(Integer.toString(bloc.getDuration()/_AHOUR));
              StringBuffer minute= new StringBuffer("00");
			               minute.append(Integer.toString(bloc.getDuration()%_AHOUR));
             
              Period period= _dm.getTTStructure().getCurrentCycle().getPeriodByPeriodKey(currentCycAss.getPeriodKey());
              if((bloc.isAssign()) && period.getPriority()!=2){
                StringBuffer activityName = new StringBuffer(_dm.getSetOfActivities().getResourceAt(i).getID());
                actlist.append(_dm.getSetOfActivities().getResourceAt(i).getID()+";");// write activity name
                StringBuffer activityType = new StringBuffer(activity.getSetOfTypes().getResourceAt(j).getID());
                actlist.append(activity.getSetOfTypes().getResourceAt(j).getID()+";") ;// write nature and 2 space
                actlist.append(nature.getSetOfSections().getResourceAt(k).getID()+";");//soa.CR_LF;//
                StringBuffer activitySection = new StringBuffer(nature.getSetOfSections().getResourceAt(k).getID());
                actlist.append(section.getSetOfUnities().getResourceAt(l).getID()+";");
                actlist.append(hour.substring(hour.length()-2,hour.length())+DConst.HOUR_SEPARATOR+
                        minute.substring(minute.length()-2,minute.length())+";");

                long dayKey= Long.parseLong(DXToolsMethods.getToken(currentCycAss.getPeriodKey(),".",0));
                String dayS= "00"+DXToolsMethods.getToken(currentCycAss.getPeriodKey(),".",0);
                actlist.append(dayS.substring(dayS.length()-2,dayS.length())+";");
                actlist.append( _dm.getTTStructure().getCurrentCycle().getSetOfDays(
                    ).getResource(dayKey).getID()+";");//.getID()
                //Period period= _dm.getTTStructure().getCurrentCycle().getPeriodByKey(dayKey,seqKey,perKey);
                hour.delete(0,hour.length());
                hour.append("00"+period.getBeginHour()[0]);
                minute.delete(0,minute.length());
                minute.append("00"+period.getBeginHour()[1]);
                actlist.append( hour.substring(hour.length()-2,hour.length())+DConst.HOUR_SEPARATOR+
                          minute.substring(minute.length()-2,minute.length())+";");
                int mnEnd= period.getBeginHour()[1]+bloc.getDuration()%_AHOUR;
                int hrEnd= period.getBeginHour()[0]+bloc.getDuration()/_AHOUR+mnEnd/_AHOUR;
                hour.delete(0,hour.length());
                hour.append("00"+Integer.toString(hrEnd));
                minute.delete(0,minute.length());
                minute.append("00"+Integer.toString(mnEnd%_AHOUR));
                actlist.append( hour.substring(hour.length()-2,hour.length())+DConst.HOUR_SEPARATOR+
                          minute.substring(minute.length()-2,minute.length())+";");
                String str [] = currentCycAss.getInstructorNames();
                for(int m = 0 ; m < str.length; m++ ) {
                  actlist.append( DXToolsMethods.getToken(str[m],",",0)+" "+DXToolsMethods.getToken(str[m],",",1)+",");
                  //actlist+= str[m] +":";
                }
                actlist.append(";");
                actlist.append( currentCycAss.getRoomName()+";");
                Vector v = activity.getStudentRegistered();
                String nbOfStudents= "000"+_dm.getSetOfEvents().studentsInSection(v,activityName.toString()+activityType.toString(), activitySection.toString()).size();
                actlist.append( nbOfStudents.substring(nbOfStudents.length()-3,nbOfStudents.length())+";"+DConst.CR_LF);
              }// end if(bloc.isAssign())
            }// end for(int l=0; l< section.getSetOfUnities().size(); l++)
          }// end for (int k=0; k< nature.getSetOfSections().size(); k++)
        }// end for(int j=0; j< activity.getSetOfTypes().size(); j++)
      }// end for (int i=0; i< soa.size(); i++){
    }*/// end if (activity.getActivityVisibility())
    return new String(""); //actlist.toString();
  }

  /**
   * return standard activity report
   * @param principalAct
   * @param otherAct
   * @return
   */
  public String getActivitiesReport( int principalElt, int[] otherElts){
    return sortReport(getReport(_activitiesReport,principalElt,otherElts)).toString();
  }

  /**
   * return standard conflicts report
   * @param principalElt The index of the first report element
   * @param otherElts The index of the other report elements
   * @return
   */
  public String getConflictsReport( int principalElt, int[] otherElts){
    return sortReport(getReport(_conflictsReport, principalElt, otherElts)).toString();
  }

  /**
   * return standard student report
   * @param principalElt
   * @param otherElt
   * @return
   */
  public String getStudentsReport( int principalElt, int[] otherElts){
    return getReport(_studentsReport,principalElt,otherElts).toString();
  }

  /**
   * @return
   */
  private String getReport(String allLines, int principalAct, int[] otherAct){
    SetOfResources setOf= new SetOfResources(1);
    StringTokenizer theReport= new StringTokenizer(allLines.toString(), DConst.CR_LF);
    //int nbTokens= theReport.countTokens();
    while(theReport.hasMoreTokens()){
      String currentLine= theReport.nextToken();
      String ID= extractToken(currentLine,principalAct);
      DXValue dxValue=new DXValue();
      Vector vec= new Vector(1);
      for(int i=0; i< otherAct.length; i++){
        vec.add(extractToken(currentLine,otherAct[i]));
      }
      dxValue.setObjectValue(vec);
      setOf.addResource(new Resource(ID, dxValue),0);
    }// end while(theReport.hasMoreTokens())
    setOf.sortSetOfResourcesByID();
    StringBuffer report= new StringBuffer("");
    for (int i=0;i< setOf.size(); i++){
      report.append(setOf.getResourceAt(i).getID()+";");//+setOf.CR_LF;
      Vector vec= (Vector)((DXValue)setOf.getResourceAt(i).getAttach()).getObjectValue();
      for (int j=0; j< vec.size(); j++){
        report.append(vec.get(j).toString()+";");
      }// end for (int j=0; j< vec.size(); j++)
      report.append(DConst.CR_LF);
    }// end  for(int i=0; i< nbTokens; i++)
    return report.toString();
  }

  /**
   *_conflictsReport is a string where each line contains more informations separeted
   * by a ";" separator
   * token number 0= day number, 1= day name, 2= sequence Id, 3= period id, 4= period begin hour
   * 5= first event in conflict, 6= event in conflict with the first, 7= number of conflicts, 8= type of conflicts
   * --------------------------
   * description of the token number 8: type of conflicts
   * type= 0: student conflict
   * type= 1: room conflict
   * type= 2: instructor conflict
   * @return
   */
  private String buildConflictsReport(){
    StringBuffer report= new StringBuffer("");
    int size= _dm.getTTStructure().getCurrentCycle().getSetOfDays().size();
    for(int i=0; i< size; i++){
      _dm.getProgressBarState().setIntValue(STATE1+STATE2+STATE3*i/size);
      Resource day= _dm.getTTStructure().getCurrentCycle().getSetOfDays().getResourceAt(i);
      for(int j=0; j< ((Day)day.getAttach()).getSetOfSequences().size(); j++){
        Resource seq= ((Day)day.getAttach()).getSetOfSequences().getResourceAt(j);
        for(int k=0; k< ((Sequence)seq.getAttach()).getSetOfPeriods().size(); k++){
          Resource per= ((Sequence)seq.getAttach()).getSetOfPeriods().getResourceAt(k);
          ((Period)per.getAttach()).getEventsInPeriod().sortSetOfResourcesByID();
          for(int x=0; x< ((Period)per.getAttach()).getEventsInPeriod().size(); x++){
            Resource confEvents= ((Period)per.getAttach()).getEventsInPeriod().getResourceAt(x);
            ((ConflictsAttach)confEvents.getAttach()).getConflictsAttach().sortSetOfResourcesByID();
            for(int y=0; y< ((ConflictsAttach)confEvents.getAttach()).getConflictsAttach().size(); y++){
              Resource confAttach= ((ConflictsAttach)confEvents.getAttach()).getConflictsAttach().getResourceAt(y);
              DXValue confValue= (DXValue)confAttach.getAttach();
              StringBuffer strBuf = new StringBuffer("yyyyyyy");
              if (confValue.getStringValue().equalsIgnoreCase(DConst.R_STUDENT_NAME)){
               /* str = _dm.getSetOfEvents().getStudentConflictDescriptions(
                  _dm.getSetOfEvents().getEventID(confEvents.getID(), _dm.getSetOfActivities()),
                  _dm.getSetOfEvents().getEventID(confAttach.getID(), _dm.getSetOfActivities()));*/
                strBuf = new StringBuffer(_dm.getSetOfEvents().getStudentConflictDescriptions(confEvents.getID(),confAttach.getID()));
              }
              if (confValue.getStringValue().equalsIgnoreCase(DConst.R_INSTRUCTOR_NAME)){
              	strBuf = new StringBuffer(_dm.getSetOfEvents().getInstructorConflictDescriptions(confEvents.getID(),confAttach.getID()));
              }
              if (confValue.getStringValue().equalsIgnoreCase(DConst.R_INSTRUCTOR_NAME_AVAIL)){
               
  //            	strBuf = new StringBuffer( _dm.getSetOfEvents().getInstructorConflictDescriptions(confValue));//;,confEvents.getID()));
              }
              if (confValue.getStringValue().equalsIgnoreCase(DConst.R_ROOM_NAME)){
               long roomKey= ((EventAttach)_dm.getSetOfEvents().getResource(confEvents.getID()).getAttach()).getRoomKey();
               strBuf = new StringBuffer( _dm.getSetOfRooms().getResource(roomKey).getID());
              }

              String hour= "00"+((Period)per.getAttach()).getBeginHour()[0];
              String minute= "00"+((Period)per.getAttach()).getBeginHour()[1];
              report.append(day.getKey()+";"+
                      day.getID()+";"+
                      seq.getID()+";"+
                      per.getID()+";"+
                      hour.substring(hour.length()-2,hour.length())+":"+minute.substring(minute.length()-2,minute.length())+";"
                     +_dm.getSetOfEvents().getEventID(confEvents.getID(), _dm.getSetOfActivities())+";"
                     +_dm.getSetOfEvents().getEventID(confAttach.getID(), _dm.getSetOfActivities())+";"
                     +confValue.getIntValue()+";"+confValue.getStringValue()+";"
                     +strBuf.toString()+";"+DConst.CR_LF);
            }// end for(int y=0; y< ((ConflictsAttach)confEvents.ge
          }// end for(int x=0; x< ((Period)per.getAttach()).getEventsInPeriod()
        }//end for(int k=0; k< ((Sequence)seq.getAttach()).getSetOfPeriods()
      }//end for(int j=0; j< ((Day)day.getAttach()).getSetOfSequences().size()
    }//end for(int i=0; i< _dm.getTTStructure().getCurrentCycle().getSetOfDays().size(
    //System.out.println(report);//debug
    return report.toString();
  }

  /**
   *
   * @param str
   * @param tokenNumber
   * 0= activity name, 1= type name, 2= section name, 3= unity name, 4= duration of the activity
   * 5= day number where activity is assign, 6= day name where activity is assign
   * 7= begin hour of the activity, 8= end hour of the activity, 9= instructor name
   * 10= room name
   * @return
   */
  private String extractToken(String line, int tokenNumber){
    StringTokenizer str = new StringTokenizer(line,";");
    int nb= str.countTokens();
    String currentToken="";
    for (int i=0; i< nb; i++){
      currentToken=str.nextToken();
      if (i==tokenNumber)
        return currentToken;
    }
    return "";
  }


  /**
   *
   * @return
   */
  private String buildStudentsReport(){
  	StringBuffer studlist= new StringBuffer("");
    /*
  	int size= _dm.getSetOfStudents().size();
    for (int i=0; i< size; i++){
      //_dm.getProgressBarState().setIntValue(STATE1+STATE2*i/size);
      //StudentAttach student= (StudentAttach)_dm.getSetOfStudents().getResourceAt(i).getAttach();
      String line = _dm.getSetOfStudents().getResourceAt(i).toWrite(" ");
      StringTokenizer strTokens= new StringTokenizer(line.substring(DConst.END_STUDENT_NUMBER_OF_COURSE,line.length()));//+SetOfResources.CR_LF;
      String name_mat = line.substring(0,DConst.BEGIN_STUDENT_NUMBER_OF_COURSE);
      String str= " "+name_mat.substring(0,DConst.END_STUDENT_MATRICULE)+";"+
                  name_mat.substring(DConst.END_STUDENT_MATRICULE, DConst.BEGIN_STUDENT_NAME)
                +";"+name_mat.substring(DConst.BEGIN_STUDENT_NAME,DConst.END_STUDENT_NAME)+";";
      String strcrs="";
      while(strTokens.hasMoreTokens()){
        String course= strTokens.nextToken();
        course = DXToolsMethods.getToken(course,";",0);
        String sect="";
        if(course.length()==DConst.STUD_COURSE_GROUP_LENGTH){
          int group= Integer.parseInt(course.substring(DConst.STUD_COURSE_LENGTH, DConst.STUD_COURSE_GROUP_LENGTH));
          sect=course.substring(0,DConst.STUD_COURSE_LENGTH-1)+"."+
               course.substring(DConst.STUD_COURSE_LENGTH-1, DConst.STUD_COURSE_LENGTH)+
               "."+DXTools.STIConvertGroup(group)+".";
          Section section= new Section();//_dm.getSetOfActivities().getSection(course.substring(0,DConst.STUD_COURSE_LENGTH-1)
              //,course.substring(DConst.STUD_COURSE_LENGTH-1, DConst.STUD_COURSE_LENGTH),
              //DXTools.STIConvertGroup(group));
          if(section!=null){
            for(int j=0; j<section.getSetOfUnities().size(); j++){
              Unity bloc= (Unity)section.getSetOfUnities().getResourceAt(j).getAttach();
              Assignment currentCycAss = (Assignment)bloc.getSetOfAssignments(
                  ).getResourceAt(_dm.getTTStructure().getCurrentCycleIndex()).getAttach();
              StringTokenizer dtime= new StringTokenizer(_dm.getTTStructure(
                  ).getCurrentCycle().getPeriod(currentCycAss.getDateAndTime()),DConst.TOKENSEPARATOR);
              long dayKey= Long.parseLong(dtime.nextToken());
              long seqKey= Long.parseLong(dtime.nextToken());
              long perKey= Long.parseLong(dtime.nextToken());
              Period period= _dm.getTTStructure().getCurrentCycle().getPeriodByKey(dayKey,seqKey,perKey);
              String hour= "00"+period.getBeginHour()[0];
              String minute= "00"+period.getBeginHour()[1];
              String time= hour.substring(hour.length()-2,hour.length())+DConst.HOUR_SEPARATOR+
                           minute.substring(minute.length()-2,minute.length());
              strcrs+=sect+Integer.toString(j+1)+"-"+ _dm.getTTStructure().getCurrentCycle().getSetOfDays(
                  ).getResource(dayKey).getKey()+"-"+ _dm.getTTStructure().getCurrentCycle().getSetOfDays(
                  ).getResource(dayKey).getID()+"-"+time+",";

            }// end for(int j=0; j<section.getSetOfUnities().size(); j++)
          }// end  if(section!=null)
        }// end if(course.length()==SetOfStudents._COURSEGROUPLENGTH)
      }// end while(strTokens.hasMoreTokens())
      studlist.append( str+strcrs+";"+DConst.CR_LF);
    }// end for (int i=0; i< _dm.getSetOfStudents().size(); i++)
    */
    return studlist.toString();
  }

  /**
   * sort a standard report
   * @param rep
   * @return
   */
  public String sortReport(String rep){
    StringBuffer newRep=new StringBuffer("");
    SetOfResources setOfRep= new SetOfResources(1);
    StringTokenizer theReport= new StringTokenizer(rep,DConst.CR_LF);
    //int nbTokens= theReport.countTokens();
    //for (int i=0; i< theReport.countTokens(); i++)
    while (theReport.hasMoreElements())
      setOfRep=createSortReport(setOfRep,theReport.nextToken());
      /*setOfRep.addResource(new Resource(theReport.nextToken(),null),1);
    for (int i=0; i< setOfRep.size(); i++)
      newRep+= setOfRep.getResourceAt(i).getID()+SetOfActivities.CR_LF;*/
    Vector res= new Vector();
    writeSortReport(setOfRep, newRep.toString(), res);
    for (int i=0; i< res.size(); i++)
      newRep.append( res.get(i).toString()+DConst.CR_LF);

    //System.out.println("******************************************");//debug
    //System.out.println(newRep);//debug
    return newRep.toString();
  }

  /**
   * create a sort report using setof resource
   * @param setOr
   * @param line
   * @return
   */
  private SetOfResources createSortReport(SetOfResources setOr, String line){
    if(line.length()!=0){
     // return setOr;
    String firstToken= DXToolsMethods.getToken(line,";",0);
    int index= setOr.getIndexOfResource(firstToken);
    if(index==-1){
      setOr.addResource(new Resource(firstToken,new SetOfResources(100)),1);
      createSortReport(setOr,line);
    }else{// end if(index==-1)
      String newLine= DXToolsMethods.removeToken(line,";",0);
      SetOfResources sor= (SetOfResources)setOr.getResourceAt(index).getAttach();
      createSortReport(sor,newLine);
    }// end else if(index==-1)
    }// end if(line.length()!=0)
    return setOr;
  }

  /**
   * write e report using setofresources
   * @param setOr
   * @param line
   * @return
   */
  private void writeSortReport(SetOfResources setOr, String line, Vector result){
    //="";
    if(setOr.size()!=0){
      for(int i=0; i< setOr.size(); i++){
        //line+= setOr.getResourceAt(i).getID()+";";
        SetOfResources sOr = (SetOfResources)setOr.getResourceAt(i).getAttach();
         writeSortReport(sOr, line.toString() + setOr.getResourceAt(i).getID()+";", result);//+SetOfActivities.CR_LF;
      }// end for(int i=0; i< setOr.size(); i++)
    }else{// end if(setOr!=null)
      result.add(line);
    }
  }

}