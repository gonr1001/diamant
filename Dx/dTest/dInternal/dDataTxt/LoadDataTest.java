package dTest.dInternal.dData;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author  ysyam
 * @version 1.0
 */

import junit.framework.*;
import java.io.File;
import java.util.Vector;
import dInternal.dTimeTable.TTStructure;
import dInternal.dTimeTable.Cycle;
import dInternal.dData.SetOfActivities;
import dInternal.dData.SetOfStudents;
import dInternal.dData.SetOfInstructors;
import dInternal.dData.SetOfRooms;

import dInternal.dData.LoadData;



public class LoadDataTest extends TestCase {
  String path;
  Vector _timeTable;
  public LoadDataTest(String name) {
    super(name);
    path =System.getProperty("user.dir")+ File.separator+"dataTest"+File.separator+"fichier1.dia";
    LoadData _lData= new LoadData();
    _timeTable = _lData.loadProject(path);
  }

  public static Test suite() {
    // the type safe way is in SimpleTest
    // the dynamic way :
    return new TestSuite(LoadDataTest.class);
  } // end suite

  /**
   * test that check the version of timetable
   * */
  public void test_loadTimeTable(){
    assertEquals("test_loadTimeTable : assertEquals: ", "1.5", (String)_timeTable.get(0));
  }

  /**
   * test that check the xml file
   * */
  public void test1_loadTimeTable(){
    Cycle cycle= ((TTStructure)_timeTable.get(1)).getCurrentCycle();
    assertEquals("test1_loadTimeTable : assertEquals1: ", 12, ((TTStructure)_timeTable.get(1)).getMaxNumberOfPeriodsADay(cycle));
    assertEquals("test1_loadTimeTable : assertEquals2: ", 3, ((TTStructure)_timeTable.get(1)).getMaxNumberOfSeqs(cycle));
    assertEquals("test1_loadTimeTable : assertEquals3: ", 5, ((TTStructure)_timeTable.get(1)).getNumberOfActiveDays());
  }

  /**
   * test that check the setofactivities
   * */
  public void test2_loadTimeTable(){
    SetOfActivities setAct= ((SetOfActivities)_timeTable.get(4));
    assertEquals("test2_loadTimeTable : assertEquals: ", "AMC640", setAct.getResourceAt(1).getID());
  }

  /**
   * test that check the setofstudents
   * */
  public void test3_loadTimeTable(){
    SetOfStudents setStud= ((SetOfStudents)_timeTable.get(5));
    assertEquals("test3_loadTimeTable : assertEquals: ", "AUDET FRE", setStud.getResourceAt(1).getID());
  }

  /**
  * test that check the setofinstructors
  * */
 public void test4_loadTimeTable(){
   SetOfInstructors setIns= ((SetOfInstructors)_timeTable.get(2));
   assertEquals("test4_loadTimeTable : assertEquals: ", "THÉRIAULT, MICHÈLE", setIns.getResourceAt(2).getID());
  }

  /**
 * test that check the setofrooms
 * */
public void test5_loadTimeTable(){
  SetOfRooms setRooms= ((SetOfRooms)_timeTable.get(3));
  assertEquals("test5_loadTimeTable : assertEquals: ", "D13016", setRooms.getResourceAt(4).getID());
  }

}