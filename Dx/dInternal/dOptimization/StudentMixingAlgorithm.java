package dInternal.dOptimization;

/**
 * <p>Title: Proto</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */

import dInternal.DModel;
import dInternal.dTimeTable.*;
import dInternal.dOptimization.*;
import dInternal.dDataTxt.*;
import dConstants.DConst;
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
    for(int i=0; i< 2; i++)
    compileStudents(eventRescList);
    //compileStudents(eventRescList);
    /*Vector secondVec= new Vector();
    for (int j=eventRescList.size()-1; j>=0; j--)
      secondVec.add(eventRescList.get(j));
    compileStudents(secondVec);*/
    //}

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
      //System.out.println(writeConvexGroups(allConvexGroups));
      SetOfResources sumOfConvex= buildSumOfConvexGroups(allConvexGroups);
      setStudInGroup(actID,typeID,allConvexGroups,sumOfConvex);
      //setStudentsInGroup(actID,typeID,allConvexGroups); ysyam
    }//end for(int i=0; i< _eventsRescList.size(); i++)
    _dm.getConditionsTest().setMatrixBuilded(false,false);
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
        long dayKey= Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",0));
        long seqKey= Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",1));
        long perKey= Long.parseLong(DXToolsMethods.getToken(event.getPeriodKey(),".",2));
        for(int k=0; k< duration; k++){
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
        student.setInGroup(activityID+typeID,-1,false);
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
    for (int i=0; i< studentsReg.size(); i++){
      long studentKey= Long.parseLong(studentsReg.get(i).toString());
      StudentAttach student= (StudentAttach)_dm.getSetOfStudents().getResource(studentKey).getAttach();
      int nbOfPotentialConf=0;
      for (int j=0; j< student.getCoursesList().size(); j++){
        String actID= student.getCoursesList().getResourceAt(j).getID();
        String typeID= actID.substring(SetOfActivities._COURSENAMELENGTH,SetOfActivities._COURSENAMELENGTH+1);
        actID=actID.substring(0,SetOfActivities._COURSENAMELENGTH);
        int group= student.getGroup(actID+typeID);
        Vector groupList= new Vector(1);
        if((group>0) ){
          groupList.add(DXTools.STIConvertGroup(group));
        }// end if((group>0) )

        for(int k=0; k< groupList.size(); k++){
          String sectionID= groupList.get(k).toString().trim();
          Section section= _dm.getSetOfActivities().getSection(actID,typeID,sectionID);
          if(section!=null){
            for(int l=0; l< section.getSetOfUnities().size(); l++){
              String eventID= actID+"."+typeID+"."+sectionID+"."+
                      section.getSetOfUnities().getResourceAt(l).getID()+".";
              Resource assEvent= associatesEvents.getResource(eventID);
              if(assEvent!=null){
                nbOfPotentialConf+= ((DXValue)assEvent.getAttach()).getIntValue();
              }// end if(assEvent!=null)
            }// end for(int l=0; l< section.getSetOfUnities().size(); l++)
          }//end if(section!=null)
        }// end for(int k=0; k< groupList.size(); k++)
      }// end for (int j=0; j< student.getCoursesList().size(); j++)
      convexGroup.setCurrentKey(studentKey);
      convexGroup.addResource(new Resource(Integer.toString(nbOfPotentialConf),new DXValue()),0);
    }//end for (int i=0; i< studentsReg.size(); i++)
    convexGroup.sortSetOfResourcesByKey();
    return convexGroup;
  }

  /**
   *
   * @param convGroup
   * @return
   */
  private String writeConvexGroups(Vector convGroups){
    String str=DConst.SEPARATOR;
    str+=DConst.CR_LF;
    for(int i=0; i< convGroups.size(); i++){
      SetOfResources group = (SetOfResources)convGroups.get(i);
      group.sortSetOfResourcesByKey();
      for (int j=0; j< group.size(); j++){
        String mat= "0000"+group.getResourceAt(j).getKey();
        str+= group.getResourceAt(j).getID()+ " --> "+ mat.substring(mat.length()-8,mat.length()) +DConst.CR_LF;
      }// end for (int j=0; j< group.size(); j++)
      str+=DConst.SEPARATOR;
      str+=DConst.CR_LF;
    }// end for(int i=0; i< convGroup.size(); i++)

    return str;
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
    Type type= _dm.getSetOfActivities().getType(activityID,typeID);
    for(int i=0; i< allConvGroup.size(); i++){
      size= new DXValue();
      int group= DXTools.STIConvertGroupToInt(type.getSetOfSections().getResourceAt(i).getID());//int group= i+1;
      size.setIntValue(_dm.getSetOfStudents().getStudentsByGroup(activityID,typeID,group).size());
      //size.setIntValue(0);
      sizeOfGroups.add(size);
    }
    Resource newStudentGroup= getStudent(allConvGroup,value, sizeOfGroups);
    while(newStudentGroup!=null){
      StudentAttach student= (StudentAttach)_dm.getSetOfStudents().getResource(newStudentGroup.getKey()).getAttach();
      int group= DXTools.STIConvertGroupToInt(type.getSetOfSections().getResourceAt(
          ((DXValue)newStudentGroup.getAttach()).getIntValue()).getID());//int group= i+1;
      //student.setInGroup(activityID+typeID, ((DXValue)newStudentGroup.getAttach()).getIntValue(),false);
      student.setInGroup(activityID+typeID, group,false);
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
        break;

      case 1:
        if(quickSolution(allConvGroup))
          resc=giveBestGroup(resc, allConvGroup,  currentConvGroup,  sizeOfGroups,1);
        else
          resc=giveBestGroup(resc, allConvGroup,  currentConvGroup,  sizeOfGroups,ACCEPTABLEVARIATION);
        break;

      case 2:
        if(quickSolution(allConvGroup))
          resc=giveBestGroup(resc, allConvGroup,  currentConvGroup,  sizeOfGroups,1);
        else
          resc=giveBestGroup(resc, allConvGroup,  currentConvGroup,  sizeOfGroups,150);
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
   * @param sizeOfGroups
   * @param smallerGroup
   * @return
   */
 private boolean isEligibleGroups(Vector sizeOfGroups, int groupIndex, int acceptableVariation){
   //int group=0;
   //int min= ((DXValue)sizeOfGroups.get(0)).getIntValue();
   Vector eligibleGroups= new Vector();
    int smalGroupIndex = getSmallerGroup(sizeOfGroups);
    DXValue smallerGroup= (DXValue)sizeOfGroups.get(smalGroupIndex);
   if(groupIndex< sizeOfGroups.size()){
     DXValue currentGroup = ((DXValue)sizeOfGroups.get(groupIndex));
     if((currentGroup.getIntValue()- smallerGroup.getIntValue())<=acceptableVariation){
       return true;
     }// end if((currentGroup.getIntValue()- smallerGroup.getIntValue()
   }// end if(groupIndex< sizeOfGroups.size())
   return false;
  }

  /**
   * return true if no student is in conflict
   * @return
   */
  private boolean quickSolution(Vector allConvGroup){
    for (int i=0; i< allConvGroup.size(); i++){
      SetOfResources group= (SetOfResources)allConvGroup.get(i);
      for (int j=0; j< group.size(); j++){
        if(Integer.parseInt(group.getResourceAt(j).getID())!=0)
          return false;
      }// end for (int j=0; j< group.size(); j++)
    }// end for (int i=0; i< allConvGroup.size(); i++)
    return true;
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
      ((DXValue)resc.getAttach()).setIntValue(bGroup);
      for (int i=0; i< allConvGroup.size(); i++)
        ((SetOfResources)allConvGroup.get(i)).removeResource(resc.getKey());
      currentConvGroup.setIntValue(bGroup);
    }// end if(((SetOfResources)allConvGroup.get( bGroup)).size()>0)
    return resc;
  }

  //-------------- New Implementation------------------------------------------------

  /**
   *
   * @param studentRegistered
   * @param allConvexGroups each convexgroup is sort by key (matricule)
   * @return
   */
  private SetOfResources buildSumOfConvexGroups( Vector allConvexGroups){
    // sort the the convexgroup by key (matricule)
    for(int j=0; j< allConvexGroups.size(); j++)
      ((SetOfResources)allConvexGroups.get(j)).sortSetOfResourcesByKey();

    SetOfResources convexSum= new SetOfResources(66);
    SetOfResources studentRegistered = (SetOfResources)allConvexGroups.get(0);
    for(int i=0; i< studentRegistered.size(); i++){
      int sum=0;
      for(int j=0; j< allConvexGroups.size(); j++){
        String conf=((SetOfResources)allConvexGroups.get(j)).getResourceAt(i).getID();
        sum+= Integer.parseInt(conf);
      }// end for(int j=0; j< studentRegistered.size(); j++)
       String confSum ="0000"+Integer.toString(sum);
       confSum= confSum.substring(confSum.length()-4,confSum.length());
      convexSum.setCurrentKey(studentRegistered.getResourceAt(i).getKey());
      convexSum.addResource(new Resource(confSum,new DXValue()),0);
    }// end for(int i=0; i< allConvexGroups.size(); i++)
    convexSum.sortSetOfResourcesByID();
    return convexSum;
  }

  /**
  *
  * @param activityID
  * @param typeID
  * @param allConvGroup
  */
 private void setStudInGroup(String activityID, String typeID, Vector allConvGroups, SetOfResources sumList){
   //SetOfResources studentRegistered = (SetOfResources)allConvGroups.get(0);
   Vector sizeOfGroups= new Vector();
   Type type= _dm.getSetOfActivities().getType(activityID,typeID);
   for(int i=0; i< type.getSetOfSections().size(); i++){
     DXValue size= new DXValue();
     int group= DXTools.STIConvertGroupToInt(type.getSetOfSections().getResourceAt(i).getID());//int group= i+1;
     size.setIntValue(_dm.getSetOfStudents().getStudentsByGroup(activityID,typeID,group).size());
     //size.setIntValue(0);
     sizeOfGroups.add(size);
    }// end for(int i=0; i< type.getSetOfSections().size(); i++)
    SetOfResources removeStudents= new SetOfResources(65);
    while(sumList.size()>0){
      //Vector eligibleGroups = getEligibleGroups(sizeOfGroups);
      for (int i=0; i< sumList.size(); i++){
        long studentKey= sumList.getResourceAt(sumList.size()-1).getKey();
        int groupIndex= getGroupIndex(studentKey, allConvGroups,sizeOfGroups);
        if(groupIndex!=-1){
          StudentAttach student= (StudentAttach)_dm.getSetOfStudents().getResource(studentKey).getAttach();
          int group= DXTools.STIConvertGroupToInt(type.getSetOfSections().getResourceAt(
              groupIndex).getID());//int group= i+1;
          DXValue value= (DXValue)sizeOfGroups.get(groupIndex);
          value.setIntValue(value.getIntValue()+1);
          //student.setInGroup(activityID+typeID, ((DXValue)newStudentGroup.getAttach()).getIntValue(),false);
          student.setInGroup(activityID+typeID, group,false);
          sumList.removeResource(studentKey);
          break;
          //newStudentGroup= getStudent(allConvGroup,value, sizeOfGroups);
        } else{// end if(groupIndex!=-1)
          removeStudents.addResource(sumList.getResourceAt(sumList.size()-1),0);
        }// end else if(groupIndex!=-1)

      }// end for (int i=0; i< studentRegistered.size(); i++)
      if(sumList.size()==0){
        for(int i=0; i< removeStudents.size(); i++){
          sumList.addResource(removeStudents.getResourceAt(i),0);
        }// end for(int i=0; i< removeStudents.size(); i++)
        sumList.sortSetOfResourcesByID();
      }// end if(sumList==0)

    }// end while(studentRegistered.size()>0)


 }

 /**
  *
  * @param studentKey
  * @param allConvexGrourps
  * @param eligibleGroups
  * @return
  */
 private int getGroupIndex (long studentKey, Vector allConvexGroups, Vector sizeOfGroups){
   SetOfResources setOfConflicts= new SetOfResources(99);
   for(int i=0; i< allConvexGroups.size(); i++){
     SetOfResources group = (SetOfResources)allConvexGroups.get(i);
     String conf="0000"+group.getResource(studentKey).getID();
     conf= conf.substring(conf.length()-4,conf.length());
     setOfConflicts.addResource(new Resource(conf,new DXValue()),0);
   }// end for(int i=0; i< allConvexGroups.size(); i++)
   setOfConflicts.sortSetOfResourcesByID();
   for (int i=0; i< setOfConflicts.size(); i++){
     int groupIndex= (int)setOfConflicts.getResourceAt(i).getKey()-1;
     if(isEligibleGroups(sizeOfGroups, groupIndex,ACCEPTABLEVARIATION))
       return groupIndex;
   }
   return -1;
 }

}// end class