package dTest.dInternal;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  DModel Tests </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;




public class DModelTest extends TestCase{



  public DModelTest(String name) {
     super(name);

  }

  public static Test suite() {
   // the type safe way is in SimpleTest
   // the dynamic way :
   return new TestSuite(DModelTest.class);
  } // end suite


}