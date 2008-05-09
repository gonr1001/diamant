package dTest.dInternal.dOptimizationTest;

import java.util.Vector;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.dData.StandardCollection;
import dInternal.dOptimization.ConflictsAttach;
//import dInternal.dTimeTable.SetOfConflicts;
import dInternal.DValue;

 public class ConflictsAttachTest extends TestCase {

   ConflictsAttach _confAttach;

   public ConflictsAttachTest(String name) {
     super(name);
     _confAttach= new ConflictsAttach();
     Vector <String> vec= new Vector <String>();
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
     DResource resc= _confAttach.getConflictsAttach().getResource("AMC640.1.02.1");
     assertEquals("test_addConflicts : assertEquals 2","AMC640.1.02.1" , resc.getID());
   }

   /**
   *
   */
  public void test_numberOfConflicts(){
    DResource resc= _confAttach.getConflictsAttach().getResource("AMC640.1.02.1");
    assertEquals("test_numberOfConflicts : assertEquals",3, ((DValue)resc.getAttach()).getIntValue());
   }

   /**
    *
    */
   public void test_mergeConflictsAttach(){
     ConflictsAttach confAttach= new ConflictsAttach();
     Vector <String> vec= new Vector <String>();
     vec.add("YS,DP");
     confAttach.addConflict("ADM111.1.01.1",2,"student",vec);
     _confAttach.mergeConflictsAttach(confAttach);
     //DResource resc= _confAttach.getConflictsAttach().getResource("AMC645.1.01.1");
     //assertEquals("test_mergeConflictsAttach : assertEquals", null, resc);
     DResource resc= _confAttach.getConflictsAttach().getResource("ADM111.1.01.1");
     assertEquals("test_mergeConflictsAttach1 : assertEquals 1","ADM111.1.01.1" , resc.getID());
     assertEquals("test_mergeConflictsAttach2 : assertEquals 2",2, ((DValue)resc.getAttach()).getIntValue());
   }

   /**
    *
    */
   public void test_getAllConflictsOfAnEvent(){
     DSetOfResources sor= new StandardCollection();
     sor = _confAttach.getAllConflictsOfAnEvent(sor);
     assertEquals("test_getAllConflictsOfAnEvent : assertEquals", sor.size(), _confAttach.getConflictsAttach().size());
   }


 }

