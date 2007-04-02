/**
 *
 * Title: ATTSAXWriteXml
 * Description: ATTSAXWriteXml
 *
 *
 */
package dInternal.dTimeTable;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class ATTSAXWriteXml {
	public ATTSAXWriteXml(String fileName, ACycle ttCycle) throws FileNotFoundException{
		String _fileSpec = fileName;
		PrintStream ps;
		FileOutputStream fHandler = new FileOutputStream(_fileSpec);
		ps = new PrintStream(fHandler); // création de l'entête du fichier
		ttCycle.CycleToXml(ps);
	}

}
