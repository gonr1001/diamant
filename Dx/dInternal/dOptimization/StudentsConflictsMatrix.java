package dInternal.dConditionsTest;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */

import dInterface.dUtil.DXTools;

import dInternal.dData.*;
import dInternal.dUtil.DXValue;
import dInternal.dUtil.DXToolsMethods;
import dInternal.DModel;


public class StudentsConflictsMatrix {

  private int [][] _theMatrix;
  private SetOfResources _allSections;
  private boolean _doFirstGroupAssignement=true;

  /**
   * Constructor
   */
  public StudentsConflictsMatrix() {
  }

  /**
   *
   * @param soa
   * @param sos
   */
  public void buildMatrix(DModel dm){
    SetOfActivities soa= dm.getSetOfActivities();
    SetOfStudents sos= dm.getSetOfStudents();
    _allSections = buildSections(soa);
    if(_doFirstGroupAssignement){
      firstStudentGroupAssignment(soa,sos, dm.getSetOfImportErrors());
      _doFirstGroupAssignement=false;
    }
    _theMatrix = new int[_allSections.size()+1][_allSections.size()+1];
    for(int i=0; i< sos.size(); i++){
      StudentAttach student = (StudentAttach)sos.getResourceAt(i).getAttach();
      for(int j=0; j< student.getCoursesList().size(); j++){
        String course1 = student.getCoursesList().getResourceAt(j).getID().substring(0, soa._COURSENAMELENGTH)
                   +"."+student.getCoursesList().getResourceAt(j).getID().substring(soa._COURSENAMELENGTH)+"."+
                   DXTools.STIConvertGroup( ((DXValue)student.getCoursesList().getResourceAt(j).getAttach()).getIntValue());
        String token= DXToolsMethods.getToken(course1,".",0);
        String tokenType= DXToolsMethods.getToken(course1,".",1);
          if(dm.getSetOfActivities().getType(token,tokenType)==null){
            DXValue error= new DXValue();
            String matricule= "00000000"+sos.getResourceAt(i).getKey();
            error.setStringValue("Erreur --> "+matricule.substring(matricule.length()-8,matricule.length())+" - "
                                 +sos.getResourceAt(i).getID()+"- Activity: "+token+tokenType+" *** INEXISTANTE");
            dm.getSetOfImportErrors().addResource(new Resource("1",error),0);
          }// end if(dm.getSetOfActivities().getResource(token)==null)

        for (int k=j; k< student.getCoursesList().size(); k++){
          String course2 = student.getCoursesList().getResourceAt(k).getID().substring(0, soa._COURSENAMELENGTH)
                         +"."+student.getCoursesList().getResourceAt(k).getID().substring(soa._COURSENAMELENGTH)+"."+
                           DXTools.STIConvertGroup( ((DXValue)student.getCoursesList().getResourceAt(k).getAttach()).getIntValue());
          int[] index= getSectionsKeys(course1, course2);
          //System.out.println("Course = ["+ course1+","+course2+"]");//debug
          //System.out.println("Index = ["+ index[0]+","+index[1]+"]");//debug
          if((index[0]!=-1) && (index[1]!=-1)){
            _theMatrix[index[0]][index[1]]++;
          }
        }// end for (int k=j; k< student.getCoursesList().size()
      }// end for(int j=0; j< ((Activity)rescActivity.getAttach()).get
    }// end for(int i=0; i< soa.size(); i++)
    //this.toWriteMatrix();
  }


  /**
   *
   * @param str1 is the key of the first section ADM111.1.A
   * @param str2 is the key of the second section ADM111.1.A
   * @return
   */
  public int getNumberOfCOnflicts(String str1, String str2){
    int[] index= getSectionsKeys(str1, str2);
    if((index[0]!=-1) && (index[1]!=-1))
      return _theMatrix[index[0]][index[1]];
    return 0;
  }

  /**
   *
   * @param str1
   * @param str2
   * @return
   */
  public int[] getSectionsKeys(String str1, String str2){
    int[] keys ={-1,-1};
    if(_allSections!=null){
      Resource resc1 = _allSections.getResource(str1);
      Resource resc2 = _allSections.getResource(str2);
      if((resc1!= null) && (resc2!= null)){
        if(resc1.getKey()<resc2.getKey()){
          keys[0]= (int)resc1.getKey();
          keys[1]= (int)resc2.getKey();
        }else{
          keys[1]= (int)resc1.getKey();
          keys[0]= (int)resc2.getKey();
        }
      }
    }
    return keys;
  }

  /**
   *
   * @param soa
   * @return
   */
  private SetOfResources buildSections(SetOfActivities soa){
    SetOfResources allSections = new SetOfResources(0);
    for(int i=0; i< soa.size(); i++){
      Resource activity = soa.getResourceAt(i);
      for (int j=0; j< ((Activity)activity.getAttach()).getSetOfTypes().size(); j++){
        Resource type= ((Activity)activity.getAttach()).getSetOfTypes().getResourceAt(j);
        for (int k=0; k< ((Type)type.getAttach()).getSetOfSections().size(); k++){
          String idSection= activity.getID()+"."+type.getID()+"."+
                   ((Type)type.getAttach()).getSetOfSections().getResourceAt(k).getID();
          //System.out.println("ID Section: "+idSection);//debug
          allSections.addResource(new Resource(idSection,null),0);
        }// end for (int k=0; k< ((Type)type.getAttach()).getSetOfSections().
      }// end for (int j=0; j< activity.getSetOfTypes().size(); j++)
    }// end for(int i=0; i< soa.size(); i++)
    return allSections;
  }

  /**
   * First assignment of students in the groups
   * @param sos
   */
  private void firstStudentGroupAssignment(SetOfActivities soa, SetOfStudents sos,
      SetOfResources setOfImportErrors){
    for (int i=0; i< soa.size(); i++){
      Resource rescActivity = soa.getResourceAt(i);
      for (int j=0; j< ((Activity)rescActivity.getAttach()).getSetOfTypes().size(); j++){
        Resource rescType = ((Activity)rescActivity.getAttach()).getSetOfTypes().getResourceAt(j);
        int groupInd = 0;
        int[] tab= new int[((Type)rescType.getAttach()).getSetOfSections().size()];
        for(int z=0; z<((Type)rescType.getAttach()).getSetOfSections().size(); z++){
          Resource rescSection= ((Type)rescType.getAttach()).getSetOfSections().getResourceAt(z);
          tab[z]= sos.getStudentsByGroup(rescActivity.getID(),rescType.getID(),
              DXTools.STIConvertGroupToInt(rescSection.getID())).size();
        }//end for(int z=0; z<((Type)rescType.getAttach()).getSetOfSections().size(); z++)

        for (int k=0; k< ((Activity)rescActivity.getAttach()).getStudentRegistered().size(); k++){
          groupInd= this.getIndexOfSmallerValue(tab);//groupInc% ((Type)rescType.getAttach()).getSetOfSections().size();
          String studentKey = (String)((Activity)rescActivity.getAttach()).getStudentRegistered().get(k);
          Resource student = sos.getResource(Long.parseLong(studentKey));
          int groupValue = DXTools.STIConvertGroupToInt(((Type)rescType.getAttach()).getSetOfSections().getResourceAt(groupInd).getID());
          //int groupValue = (int)((Type)rescType.getAttach()).getSetOfSections().getResourceAt(groupInd).getKey();

          if((!((StudentAttach)student.getAttach()).isFixedInGroup(rescActivity.getID()+rescType.getID(),groupValue))
             && (((StudentAttach)student.getAttach()).getGroup(rescActivity.getID()+rescType.getID())==-1)){
            ((StudentAttach)student.getAttach()).setInGroup(rescActivity.getID()+rescType.getID(),groupValue,false);
            tab[groupInd]++;
            //groupInc++;
          }else{// else if(!((StudentAttach)student.getAttach()).isFixedInGroup
            int studentGroup=((StudentAttach)student.getAttach()).getGroup(rescActivity.getID()+rescType.getID());
            String groupeID=DXTools.STIConvertGroup(studentGroup);
            if(soa.getSection(rescActivity.getID(),rescType.getID(),groupeID)==null){
              DXValue error= new DXValue();
              error.setStringValue("Erreur --> "+student.getKey()+" - "+student.getID()+"- Activity: "+rescActivity.getID()
                                 +"."+rescType.getID()+" - Group: "+groupeID);
              setOfImportErrors.addResource(new Resource("1",error),0);
              ((StudentAttach)student.getAttach()).setInGroup(rescActivity.getID()+rescType.getID(),-1,false);
            }// end if(soa.getSection(rescActivity.getID(),rescType.getID(),groupeID)==null)
          }// end else if(!((StudentAttach)student.getAttach()).isFixedInGroup
        }// end for (int k=0; k< ((Activity)rescActivity.getAttach()).getSetOfTypes()
      }// end for (int j=0; j< ((Activity)rescActivity.getAttach()).
    }// end for (int i=0; i< soa.size(); i++)
  }

  /**
   * get the index of the smaller value of a table
   * @param tab
   * @return
   */
  private int getIndexOfSmallerValue(int [] tab){
    int inc=1000;
    int index=0;
    for (int i=0; i< tab.length; i++){
      if(tab[i]<inc)
        index=i;
      inc= tab[i];
    }
    return index;
  }

  /**
   *
   * @return
   */
  public String toWriteMatrix(){
    String str="";
    for (int i=1; i< _allSections.size()+1; i++){
      str+= _allSections.getResourceAt(i-1).getID()+" --> ";
      for (int j=1; j< i+1; j++){
        if (_theMatrix[i][j]<10)
          str+=_theMatrix[i][j]+"  ";
        else
          if (_theMatrix[i][j]<100)
            str+=_theMatrix[i][j]+" ";
      }// end for (int j=0; j< _theMatrix[i].length; j++)
      str+= SetOfResources.CR_LF;
    }// end for (int i=0; i< _allSections.size(); i++)

    return str;
  }

  public int[][] getTheMatrix(){
    return _theMatrix;
  }

}// end class