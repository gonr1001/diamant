package dTest.dInterface;

import java.io.File;

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
        return new ScriptTestSuite(TestsSuite.class, "D:\\Developpements\\DiamantExtreme\\Dx\\dTest\\dAbbotTest") {
            /** Determine whether the given script should be included. */
            public boolean accept(File file) {
                String name = file.getName();
                return name.startsWith("test") && name.endsWith(".xml");
            }
        };
    }

    public static void main(String[] args) {
        TestHelper.runTests(args, TestsSuite.class);
    }
}
