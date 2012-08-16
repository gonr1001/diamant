/**
 * #rep
 * This comment must be replaced by
 * a copyright or copy left allowing to
 * distribute the code as open source 
 *
 * the prefix for the packages is
 * ca.sixs.
 *
 * 
 */
package ca.sixs.ioFiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import junit.framework.TestCase;

/**
 * @author gonr1001
 * 
 */
public class ReadFileTest extends TestCase {

	private final String _path = "." + File.separator + "f4Tests"
			+ File.separator + "readTests" + File.separator;



	public void testReadSTIFile() {
		StiFile sf = new StiFile();
		
		try {
			StiData stiD = sf.loadData(_path + "stiFile0.xml");		
			assertEquals("testReadSTIFile", "503335", stiD.getInstructors().get(0).getInstructorID());
			assertEquals("testReadSTIFile", "SCA701-1-01", stiD.getInstructors().get(96).getInstructorID() );
			assertEquals("testReadSTIFile", 97, stiD.getInstructors().size());
			
			StiActivity sa = stiD.getActivities().get(0);
			assertEquals("testReadSTIFile", sa.getActivityCode(), "AMC917");
			ArrayList<StiInstructorID> instuctorsID = sa.getInstructors();
			assertEquals("testReadSTIFile", instuctorsID.size(), 1);
			assertEquals("testReadSTIFile", instuctorsID.get(0).getInstructorID(), "503335");
			
			
			
			assertEquals("testReadSTIFile", stiD.getActivities().get(211).getActivityCode(), "SCA701");
			assertEquals("testReadSTIFile", 212, stiD.getActivities().size());

		} catch (FileNotFoundException e) {
			assertEquals(true  ,e.toString().contains("FileNotFoundException"));
			System.out.println("Exception " + e.toString());
			e.printStackTrace();
			System.out
					.println("A problem while aFileGameTest FileNotFoundException, abnormal condtion in test");
		} catch (IOException e) {
			System.out.println("Exception " + e.toString());
			e.printStackTrace();
			System.out
					.println("A problem while aFileGameTest IOException, abnormal condtion in test");
		} catch (Exception e) {
			System.out.println("Exception " + e.toString());
			e.printStackTrace();
			System.out
					.println("A problem while aFileGameTest Exception, abnormal condtion in test");
		}

	}

}
