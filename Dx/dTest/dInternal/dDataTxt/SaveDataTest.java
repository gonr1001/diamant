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
import dInternal.dData.SaveData;



public class SaveDataTest extends TestCase {
  String path;
  Vector _timeTable;
  public SaveDataTest(String name) {
    super(name);
    path =System.getProperty("user.dir")+ File.separator+"dataTest"+File.separator;
    LoadData _lData= new LoadData();
    _timeTable = _lData.loadProject(path+"fichier1.dia");
  }

  public static Test suite() {
    // the type safe way is in SimpleTest
    // the dynamic way :
    return new TestSuite(SaveDataTest.class);
  } // end suite

  /**
   * test that check the version of timetable
   * */
  public void test_saveTimeTable(){
    SaveData save = new SaveData("1.6");
    save.saveTimeTable((TTStructure)_timeTable.get(1), (SetOfInstructors)_timeTable.get(2)
                                 , (SetOfRooms)_timeTable.get(3), (SetOfActivities)_timeTable.get(4)
                                 , (SetOfStudents)_timeTable.get(5),path+"fichier1Test.dia");
    LoadData _lData= new LoadData();
    Vector _timeTable1 = _lData.loadProject(path+"fichier1Test.dia");
    assertEquals("test_saveTimeTable : assertEquals: ", true, ((SetOfRooms)_timeTable1.get(3)).isEquals((SetOfRooms)_timeTable.get(3)));
  }

  /**
   * test that check that the saved file is the same as the loaded file
   * */
  public void test1_loadTimeTable(){
    LoadData _lData= new LoadData();
    Vector _timeTable1 = _lData.loadProject(path+"fichierTest.dia");
     assertEquals("test_loadTimeTable : assertEquals: ",true, ((SetOfRooms)_timeTable1.get(3)).isEquals((SetOfRooms)_timeTable.get(3)));
  }


}