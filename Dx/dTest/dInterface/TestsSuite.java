package dTest.dInterface;



import junit.extensions.abbot.*;
import junit.framework.Test;

//import junit.textui.TestRunner;
//import junit.ui.TestRunner;
//import junit.swingui.TestRunner;

/** Simple example of a ScriptTestSuite.  Selects all scripts of the form
 * MyCode-[0-9]*.xml.
 */
public class TestsSuite extends ScriptFixture {

    /** Name is the name of a script filename. */
    public TestsSuite(String name) {
        super(name);
    }

    /** Return the set of scripts we want to run. */
    public static Test suite() {
      String testsDirectory = "D:\\Developpements\\DiamantExtreme\\Dx\\dTest\\dAbbotTest\\";
      String [] testFiles = {
        "testNewTTCycle.xml",
        "testNewTTExam.xml",
        "testOpenTT.xml",
        "testDefFileImport.xml",
        "testAutomImport.xml",
        "testClose.xml",
        "testQuit.xml",
        "testAboutBox.xml"
      };
      for(int i = 0; i < testFiles.length; i++){
        testFiles[i] = testsDirectory + testFiles[i];
      }
      return new ScriptTestSuite(TestsSuite.class, testFiles) {

      };
      // a second way for definig the tests files :
      /* return new ScriptTestSuite(TestsSuite.class, "D:\\Developpements\\DiamantExtreme\\Dx\\dTest\\dAbbotTest") {
        // Determine whether the given script should be included.
        public boolean accept(File file) {
          String name = file.getName();
          return name.startsWith("test") && name.endsWith(".xml");
        }
      };
      */
    } // end method suite()

    public static void main(String[] args) {
        TestHelper.runTests(args, TestsSuite.class);
    }
}
