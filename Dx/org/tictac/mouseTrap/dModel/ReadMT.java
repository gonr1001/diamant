
package org.tictac.mouseTrap.dModel;

import java.io.IOException;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import org.jdom.*;
import org.jdom.input.*;
//import org.jdom.output.*; //rgd: uncomment for debug

public class ReadMT {
	private String filename="";
	private String unformatFile="";	
	// Global value so it can be ref'd by the tree-adapter
	Document document; 

	public ReadMT (String file){
		unformatFile=file;
		String name=file.substring(0,file.indexOf(".log"));
		filename=name+"format.xml";
	}
	
	public void formatFile(){
		String str = "";
		BufferedReader inputFile = null;
		File outputFile;
		FileWriter out=null;

		try{
			outputFile = new File(filename);
			out = new FileWriter(outputFile); //true = append
			inputFile = new BufferedReader(new FileReader(unformatFile));   
			str = inputFile.readLine( );			//line
			out.write("<data>");				
			while (str!=null){
				out.write(str+"\n");
				str = inputFile.readLine( );			//line			
			}
			out.write("</data>");
			inputFile.close();
			out.close();
		}catch (IOException e) {
			System.err.println("ReadMT.formatFile Caught IOException: " + e.getMessage());
		}catch (Exception e) {
			System.err.println("ReadMT.formatFile Caught Exception: " + e.getMessage());
		}	
	}
	
	public Document readFile()	{
		Document doc=null;
		formatFile();
		try {
			// Build the document with SAX and Xerces, no validation
			SAXBuilder builder = new SAXBuilder();
			// Create the document
			doc = builder.build(new File(filename));

			// Output the document, use standard formatter 
			//XMLOutputter fmt = new XMLOutputter();//rgd: uncomment for debug
			//fmt.output(doc, System.out);//rgd: uncomment for debug
		}catch (Exception e) {
			System.err.println("ReadMT.readFile Caught Exception: " + e.getMessage());
		}
		return doc;
	} 
}