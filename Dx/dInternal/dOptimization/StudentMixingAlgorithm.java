package dInternal.dAlgorithms;

/**
 * <p>Title: Proto</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */

import dInternal.DModel;
import dInternal.dTimeTable.*;
import dInternal.dConditionsTest.*;
import dInternal.dData.*;
import dInternal.dUtil.DXValue;
import dInternal.dUtil.DXToolsMethods;
import dInterface.dUtil.DXTools;
import java.util.Vector;

public class StudentMixingAlgorithm implements Algorithm {

  private DModel _dm;
  private Vector _eventsRescList;
  private int _mixingType;// 0= balance student mixing,  1= balance student
  // mixing with acceptable variation, 2= optimize student mixing
  private int ACCEPTABLEVARIATION=5;

  /**
   *
   * @param DModel dm
   * @param int the mixingType 0= balance student mixing, 1= optimize student mixing
   */
  public StudentMixingAlgorithm(DModel dm, int mixingType) {
    _dm= dm;
    _mixingType= mixingType;

  }

  /**
   *
   * @param DModel
   * @param vectorOfEvents
   */
  public void build(){

    Vector eventRescList=buildEventsVector();
    compileStudents(eventRescList);
    //compileStudents(eventRescList);
    /*Vector secondVec= new Vector();
    for (int i=eventRescList.size()-1; i>=0; i--)
      secondVec.add(eventRescList.get(i));
    compileStudents(secondVec);*/

  }

  /**
   *
   * @param DModel
   * @param vectorOfEvents
   */
  private void compileStudents(Vector eventRescList){
    _eventsRescList= eventRescList;//buildEventsVector();
    for(int i=0; i< _eventsRescList.size(); i++){
      Vector allConvexGroups= new Vector(1);
      String actID= DXToolsMethods.getToken(_eventsRescList.get(i).toString(),".",0);
      String typeID= DXToolsMethods.getToken(_eventsRescList.get(i).toString(),".",1);
      Type type= _dm.getSetOfActivities().getType(actID,typeID);
       Vector studentRegistered= buildStudentsRegistered(actID,typeID);
      for (int j=0; j< type.getSetOfSections().size(); j++){
        Resource section= type.getSetOfSections().getResourceAt(j);
        //make it for each section of an activity type
        SetOfResources associatesEvents= buildOtherAssociatesEvents(actID,typeID,section.getID());
        SetOfResources convGroup= buildConvexGroup(associatesEvents,studentRegistered);
        allConvexGroups.add(convGroup);
        //
      }// end
      setStudentsInGroup(actID,typeID,allConvexGroups);
    }//end for(int i=0; i< _eventsRescList.size(); i++)
    _dm.getConditionsTest().setMatrixBuilded(false);
    _dm.getTTStructure().getCurrentCycle().getAttributsToDisplay(_dm.getTTStructure().getPeriodLenght());
    _dm.sendEvent(null);
    //System.out.println("Mixing type: "+_mixingType);// debug
  }

  /**
   * return events list to use by the algorithm. these events is all events place
   * in ttstructure
   * @return
   */
  private Vector buildEventsVector(){
    Vector vect= new Vector(1);
    for (int i=0; i< _dm.getSetOfEvents().size(); i++){
      Resource rescEvent= _dm.getSetOfEvents().getResourceAt(i);
      if(((EventAttach)rescEvent.getAttach()).isPlaceInAPeriod()){
        String actID= DXToolsMethods.getToken(rescEvent.getID(),".",0);
        String typeID= DXToolsMethods.getToken(rescEvent.getID(),".",1);
        Type type= _dm.getSetOfActivities().getType(actID,typeID);
        String str= actID+"."+typeID+".";
        if( (type.getSetOfSections().size()>1) && (!vect.contains(str)) )
          vect.add(str);
      }
    }// end for (int i=0; i< _dm.getSetOfEvents().size();
    return vect;
  }

  /**
   * build the list of all events wich are place in the same periods that the
   * current activity
   * @param String activityID
   * @param String typeID
   * @return
   */
  private SetOfResources buildOtherAssociatesEvents(String activityID, String typeID, String sectionID){
    SetOfResources assEvents= new SetOfResources(1);
    Type type= _dm.getSetOfActivities().getType(activityID,typeID);
    //for (int i=0; i< type.getSetOfSections().size(); i++){
    Resource section= type.getSection(sectionID);
    for(int j=0; j< ((Section)section.getAttach()).getSetOfUnities().size(); j++){
      Resource unity= ((Section)section.getAttach()).getSetOfUnities().getResourceAt(j);
      String eventID=activityID+"."+typeID+"."+section.getID()+"."+unity.getID()+".";
      EventAttach event= (EventAttach)_dm.getSetOfEvents().getResource(eventID).getAttach();
      if(event.isPlaceInAPeriod()){
        int duration = event.getDuration()/_dm.getTTStructure().getPeriodLenght();
        for(int k=0; k< duration; k++){
          long dayKey= Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",0));
          long seqKey= Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",1));
          long perKey= Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",2));
          Period period= _dm.getTTStructure().getCurrentCycle().getPeriodByKey(dayKey,seqKey,perKey+k);
          for (int l=0; l< period.getEventsInPeriod().size(); l++){
            String inPeriodEventID=period.getEventsInPeriod().getResourceAt(l).getID();
            if(!eventID.equalsIgnoreCase(inPeriodEventID)){
              Resource assEvent= assEvents.getResource(inPeriodEventID);
              if(assEvent!=null){
                ((DXValue)assEvent.getAttach()).setIntValue(((DXValue)assEvent.getAttach()).getIntValue()+1);
              }else{// end if(assEvent!=null)
                DXValue value= new DXValue();
                value.setIntValue(1);
                assEvents.addResource(new Resource(period.getEventsInPeriod().getResourceAt(l).getID(),value),1);
              }// end else if(assEvent!=null)
            }// end if(!eventID.equalsIgnoreCase(inPeriodEventID))
          }// end for (int l=0; l< period.getEventsInPeriod().size(); l++)
        }// end for(int k=0; k< event.getDuration(); k++)
      }// end if(event.isPlaceInAPeriod())
    }// end for(int j=0; j< section.getSetOfUnities().size(); j++)
    //}// end for (int i=0; i< type.getSetOfSections().size(); i++)
    return assEvents;
  }

  /**
   * get the liste of all students registered in the activity who are not fixe
   * in a group
   * @param activityID
   * @return
   */
  private Vector buildStudentsRegistered(String activityID, String typeID){
    Vector studentR= new Vector(1);
    Activity activity= (Activity)_dm.getSetOfActivities().getResource(activityID).getAttach();
    Vector studentList= activity.getStudentRegistered();
    for (int i=0; i< studentList.size(); i++){
      long studentKey= Long.parseLong(studentList.get(i).toString());
      StudentAttach student= (StudentAttach)_dm.getSetOfStudents().getResource(studentKey).getAttach();
      int group= student.getGroup(activityID+typeID);
      if(!student.isFixedInGroup(activityID+typeID,group)){
        studentR.add(Long.toString(studentKey));
      }// end if(student.isFixedInGroup(activityID+typeID,group))
    }// end for (int i=0; i< student.size(); i++)
    return studentR;
  }

  /**
   * get max number of repetition of an events in contiguous periods
   * @param sor setofresources containt a DXValue object attach
   * @return int
   */
 /* private int getMaxEventRepeatsInPeriod(SetOfResources sor){
    int count=0;
    for (int i=0; i< sor.size(); i++)
      if(count < ((DXValue)sor.getResourceAt(i).getAttach()).getIntValue())
        count = ((DXValue)sor.getResourceAt(i).getAttach()).getIntValue();
    return count;
  }*/

 /**
  * build all convex groups of students
  * @param String the current activity
  * @param associatesEvents
  * @param maxRepeats
  * @param studentsReg
  * @return
  */
  private SetOfResources buildConvexGroup(SetOfResources associatesEvents, Vector studentsReg){
    /**
     * contains a ressource where the key is the student key and the ID is the
     * number of potential conflicts of the student
     */
    SetOfResources convexGroup= new SetOfResources(1);//
  //  int[][] convexMatrix= new int[associatesEvents.size()][studentsReg.size()];
    for (int i=0; i< studentsReg.size(); i++){
      long studentKey= Long.parseLong(studentsReg.get(i).toString());
      StudentAttach student= (StudentAttach)_dm.getSetOfStudents().getResource(studentKey).getAttach();
      int nbOfPotentialConf=0;
      for (int j=0; j< student.getCoursesList().size(); j++){
        String actID= student.getCoursesList().getResourceAt(j).getID();
        int group= student.getGroup(actID);
        Section sect=_dm.getSetOfActivities().getSection(actID.substring(0,
            SetOfActivities._COURSENAMELENGTH),actID.substring(
            SetOfActivities._COURSENAMELENGTH),Character.toString(DXTools.STIConvertGroup(group)));
        if((group<=0) || sect==null){
          group=1;
          student.setInGroup(actID,group,false);
        }
        String sectionID= Character.toString(DXTools.STIConvertGroup(group));
        String typeID= actID.substring(SetOfActivities._COURSENAMELENGTH,SetOfActivities._COURSENAMELENGTH+1);
        actID=actID.substring(0,SetOfActivities._COURSENAMELENGTH);
        //Type type= _dm.getSetOfActivities().getType(actID,typeID);
        //for(int k=0; k< type.getSetOfSections().size(); k++){
        //Resource section= type.getSetOfSections().getResourceAt (k);
        Section section= _dm.getSetOfActivities().getSection(actID,typeID,sectionID);
        if(section!=null){
          for(int l=0; l< section.getSetOfUnities().size(); l++){
            String eventID= actID+"."+typeID+"."+sectionID+"."+
                    section.getSetOfUnities().getResourceAt(l).getID()+".";
            Resource assEvent= associatesEvents.getResource(eventID);
            if(assEvent!=null){
              nbOfPotentialConf+= ((DXValue)assEvent.getAttach()).getIntValue();
            }// end if(assEvent!=null)
            //System.out.println(eventID);//debug
          }// end for(int l=0; l< section.getSetOfUnities().size(); l++)
        }//end if(section!=null)
        //}// end for(int k=0; k< type.getSetOfSections().size(); k++)
      }// end for (int j=0; j< student.getCoursesList().size(); j++)
      convexGroup.setCurrentKey(studentKey);
      convexGroup.addResource(new Resource(Integer.toString(nbOfPotentialConf),new DXValue()),0);
      //DXValue value= new DXValue();
      //value.setIntValue(nbOfPotentialConf);
    }//end for (int i=0; i< studentsReg.size(); i++)
    convexGroup.sortSetOfResourcesByID();
    return convexGroup;
  }

  /**
   *
   * @param activityID
   * @param typeID
   * @param allConvGroup
   */
  private void setStudentsInGroup(String activityID, String typeID, Vector allConvGroup){
    DXValue value= new DXValue();
    value.setIntValue(0);
    DXValue size;
    Vector sizeOfGroups= new Vector();
    for(int i=0; i< allConvGroup.size(); i++){
      size= new DXValue();
      //size.setIntValue(_dm.getSetOfStudents().getStudentsByGroup(activityID,typeID,i+1).size());
      size.setIntValue(0);
      sizeOfGroups.add(size);
    }
    Resource newStudentGroup= getStudent(allConvGroup,value, sizeOfGroups);
    while(newStudentGroup!=null){
      StudentAttach student= (StudentAttach)_dm.getSetOfStudents().getResource(newStudentGroup.getKey()).getAttach();
      student.setInGroup(activityID+typeID, ((DXValue)newStudentGroup.getAttach()).getIntValue(),false);
      newStudentGroup= getStudent(allConvGroup,value, sizeOfGroups);
    }
  }

  /**
   * get the student from allConvexGroup depending of the mixingType
   * @return
   */
  private Resource getStudent(Vector allConvGroup, DXValue currentConvGroup, Vector sizeOfGroups){
    Resource resc=null;
    switch(_mixingType){
      case 0:
        resc=giveBestGroup(resc, allConvGroup,  currentConvGroup,  sizeOfGroups,1);
        /*int group=currentConvGroup.getIntValue();
        SetOfResources biggestConvGroup= (SetOfResources)allConvGroup.get(group);
        if(biggestConvGroup.size()>0){
          resc= (Resource)biggestConvGroup.getResourceAt(0);
          ((DXValue)resc.getAttach()).setIntValue(group+1);
          for (int i=0; i< allConvGroup.size(); i++)
            ((SetOfResources)allConvGroup.get(i)).removeResource(resc.getKey());
        }// end if(biggestConvGroup.size()>0)
        if(++group>=allConvGroup.size())
          group=0;
        currentConvGroup.setIntValue(group);*/
        break;

      case 1:
        resc=giveBestGroup(resc, allConvGroup,  currentConvGroup,  sizeOfGroups,ACCEPTABLEVARIATION);
        break;

      case 2:
        if(allConvGroup.size()>0 && ((SetOfResources)allConvGroup.get(0)).size()>0){
          int bestConf=Integer.parseInt(((SetOfResources)allConvGroup.get(0)
              ).getResourceAt(0).getID());
          int bestGroup=0;
          int count;
          //for (int i=currentConvGroup.getIntValue(); i< allConvGroup.size()+currentConvGroup.getIntValue(); i++){
          for (int i=1; i< allConvGroup.size(); i++){
            if(bestConf>=Integer.parseInt(((SetOfResources)allConvGroup.get(i)
                ).getResourceAt(0).getID())){
            bestConf=Integer.parseInt(((SetOfResources)allConvGroup.get(i)
            ).getResourceAt(0).getID());
            bestGroup=i;
          }
          }
          resc= (Resource)((SetOfResources)allConvGroup.get(bestGroup)).getResourceAt(0);
          ((DXValue)resc.getAttach()).setIntValue(bestGroup+1);
          for (int i=0; i< allConvGroup.size(); i++)
            ((SetOfResources)allConvGroup.get(i)).removeResource(resc.getKey());
          currentConvGroup.setIntValue(bestGroup+1);
        }
        break;
    }
    return resc;
  }

  /**
   *
   * @param sizeOfGroups
   * @return
   */
  private int getSmallerGroup(Vector sizeOfGroups){
    int group=0;
    int min= ((DXValue)sizeOfGroups.get(0)).getIntValue();
    for (int i=1; i< sizeOfGroups.size(); i++){
      if(min> ((DXValue)sizeOfGroups.get(i)).getIntValue()){
        min= ((DXValue)sizeOfGroups.get(i)).getIntValue();
        group=i;
      }
    }
    return group;
  }


  /**
   *
   * @param resc
   * @param allConvGroup
   * @param currentConvGroup
   * @param sizeOfGroups
   */
  private Resource giveBestGroup(Resource resc, Vector allConvGroup,
                             DXValue currentConvGroup, Vector sizeOfGroups, int acceptableVariation){
    Vector includeGroupsList=new Vector();
        int smallGroup= getSmallerGroup(sizeOfGroups);
        for(int i=0; i< allConvGroup.size(); i++){
          if ((((DXValue)sizeOfGroups.get(i)).getIntValue()-
               ((DXValue)sizeOfGroups.get(smallGroup)).getIntValue())< acceptableVariation)
          includeGroupsList.add(new Integer(i));
        }// end for(int i=0; i< allConvGroup.size(); i++){

        int bGroup=((Integer)includeGroupsList.get(0)).intValue();
        if(((SetOfResources)allConvGroup.get( bGroup)).size()>0){
          //System.out.println("bGroup: "+bGroup+" ((SetOfResources)allConvGroup.get( bGroup)) size: "+((SetOfResources)allConvGroup.get( bGroup)).size() );//debug
          int bConf=Integer.parseInt(((SetOfResources)allConvGroup.get(
              bGroup)).getResourceAt(0).getID());


          for(int i=1; i< includeGroupsList.size(); i++){
            if(bConf>Integer.parseInt(((SetOfResources)allConvGroup.get(
                ((Integer)includeGroupsList.get(i)).intValue())).getResourceAt(0).getID())){
            bConf=Integer.parseInt(((SetOfResources)allConvGroup.get(
            ((Integer)includeGroupsList.get(i)).intValue())).getResourceAt(0).getID());
            bGroup=((Integer)includeGroupsList.get(i)).intValue();
          }// end if(bConf>=Integer.parseInt(((SetOfResources
          }// end for(int i=0; i< includeGroupsList.size(); i++){
          //currentConvGroup.setIntValue(bGroup);
          ((DXValue)sizeOfGroups.get(bGroup)).setIntValue(
              ((DXValue)sizeOfGroups.get(bGroup)).getIntValue()+1);

          resc= (Resource)((SetOfResources)allConvGroup.get(bGroup)).getResourceAt(0);
          ((DXValue)resc.getAttach()).setIntValue(bGroup+1);
          for (int i=0; i< allConvGroup.size(); i++)
            ((SetOfResources)allConvGroup.get(i)).removeResource(resc.getKey());
          currentConvGroup.setIntValue(bGroup+1);
        }// end if(((SetOfResources)allConvGroup.get( bGroup)).size()>0)
        return resc;
  }

}// end class