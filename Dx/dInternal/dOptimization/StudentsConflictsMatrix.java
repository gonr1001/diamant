package dInternal.dConditionsTest;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author ysyam
 * @version 1.0
 */
import dInternal.dData.SetOfActivities;
import dInternal.dData.Activity;
import dInternal.dData.Type;
import dInternal.dData.SetOfStudents;
import dInternal.dData.StudentAttach;
import dInternal.dData.SetOfResources;
import dInternal.dData.Resource;
import dInternal.dUtil.DXValue;

public class StudentsConflictsMatrix {

  private int [][] _theMatrix ;
  private SetOfResources _allSections;

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
  public void buildMatrix(SetOfActivities soa, SetOfStudents sos){
    _allSections = buildSections(soa);
    firstStudentGroupAssignment(soa,sos);
    _theMatrix = new int[_allSections.size()+1][_allSections.size()+1];
    for(int i=0; i< sos.size(); i++){
      StudentAttach student = (StudentAttach)sos.getResourceAt(0).getAttach();
      for(int j=0; j< student.getCoursesList().size(); j++){

      }// end for(int j=0; j< ((Activity)rescActivity.getAttach()).get
    }// end for(int i=0; i< soa.size(); i++)
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
  private void firstStudentGroupAssignment(SetOfActivities soa, SetOfStudents sos){
    for (int i=0; i< soa.size(); i++){
      Resource rescActivity = soa.getResourceAt(i);
      for (int j=0; j< ((Activity)rescActivity.getAttach()).getSetOfTypes().size(); j++){
        Resource rescType = ((Activity)rescActivity.getAttach()).getSetOfTypes().getResourceAt(j);
        int groupInc = 0;
        for (int k=0; k< ((Activity)rescActivity.getAttach()).getStudentRegistered().size(); k++){
          groupInc= groupInc% ((Type)rescType.getAttach()).getSetOfSections().size();
          String studentKey = (String)((Activity)rescActivity.getAttach()).getStudentRegistered().get(k);
          Resource student = sos.getResource(Long.parseLong(studentKey));
          int groupValue = (int)((Type)rescType.getAttach()).getSetOfSections().getResourceAt(groupInc).getKey();
          ((StudentAttach)student.getAttach()).setInGroup(rescActivity.getID()+rescType.getID(),groupValue,false);
          groupInc++;
        }// end for (int k=0; k< ((Activity)rescActivity.getAttach()).getSetOfTypes()
      }// end for (int j=0; j< ((Activity)rescActivity.getAttach()).
    }// end for (int i=0; i< soa.size(); i++)

  }

}// end class