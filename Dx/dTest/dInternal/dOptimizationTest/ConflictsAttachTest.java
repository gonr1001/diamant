package dTest.dInternal.dConditionsTest;

import junit.framework.*;
import java.util.Vector;

import dInternal.dConditionsTest.ConflictsAttach;
import dInternal.dData.Resource;
import dInternal.dData.SetOfResources;
import dInternal.dUtil.DXValue;

 public class ConflictsAttachTest extends TestCase {

   ConflictsAttach _confAttach;

   public ConflictsAttachTest(String name) {
     super(name);
     _confAttach= new ConflictsAttach();
     Vector vec= new Vector();
     vec.add("YS,RGR,AJ");
     _confAttach.addConflict("AMC640.1.02.1",3,"student",vec);
     _confAttach.addConflict("AMC645.1.01.1",1,"Instructor",vec);
   }

   public static Test suite() {
    // the type safe way is in SimpleTest
    // the dynamic way :
    return new TestSuite(ConflictsAttachTest.class);
   } // end suite

   /**
    *
    */
   public void test_addConflicts(){
     assertEquals("test_addConflicts : assertEquals 1",2 , _confAttach.getConflictsAttach().size());
     Resource resc= _confAttach.getConflictsAttach().getResource("AMC640.1.02.1");
     assertEquals("test_addConflicts : assertEquals 2","AMC640.1.02.1" , resc.getID());
   }

   /**
   *
   */
  public void test_numberOfConflicts(){
    Resource resc= _confAttach.getConflictsAttach().getResource("AMC640.1.02.1");
    assertEquals("test_addConflicts : assertEquals",3, ((DXValue)resc.getAttach()).getIntValue());
   }

   /**
    *
    */
   public void test_mergeConflictsAttach(){
     ConflictsAttach confAttach= new ConflictsAttach();
     Vector vec= new Vector();
     vec.add("YS,DP");
     confAttach.addConflict("ADM111.1.01.1",2,"student",vec);
     _confAttach.mergeConflictsAttach(confAttach);
     Resource resc= _confAttach.getConflictsAttach().getResource("ADM111.1.01.1");
     assertEquals("test_mergeConflictsAttach : assertEquals 1","ADM111.1.01.1" , resc.getID());
     assertEquals("test_mergeConflictsAttach : assertEquals 2",2, ((DXValue)resc.getAttach()).getIntValue());
   }

   /**
    *
    */
   public void test_removeConflicts(){
     //_confAttach.addConflict("AMC645.1.01.1",1,"Instructor",vec);
     _confAttach.removeConflict("AMC645.1.01.1","Instructor");
     assertEquals("test_removeConflicts : assertEquals 1",1 , _confAttach.getConflictsAttach().size());
      Resource resc= _confAttach.getConflictsAttach().getResource("AMC645.1.01.1");
     assertEquals("test_removeConflicts : assertEquals", null, resc);
   }

   /**
    *
    */
   public void test_getAllConflictsOfAnEvent(){
     SetOfResources sor= new SetOfResources(99);
     sor= _confAttach.getAllConflictsOfAnEvent(sor);
     assertEquals("test_getAllConflictsOfAnEvent : assertEquals", sor.size(), _confAttach.getConflictsAttach().size());
   }


 }

