package dTest.dInternal.dConditionsTest;

import junit.framework.*;

 public class ConflictsAttachTest extends TestCase {

   public ConflictsAttachTest(String name) {
     super(name);

   }

   public static Test suite() {
    // the type safe way is in SimpleTest
    // the dynamic way :
    return new TestSuite(ConflictsAttachTest.class);
   } // end suite



   public void test_ConflictsAttach(){
     assertEquals("No test is required: : assertEquals", true, true);
   }


 }

