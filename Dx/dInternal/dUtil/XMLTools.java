package dInternal.dUtil;

/**
 * <p>Title: Diamant 1.5</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author YS
 * @version 1.0
 */

import xml.OutPut.BuildXMLElement;
import xml.OutPut.writeFile;
import xml.InPut.readFile;
import xml.InPut.ReadXMLElement;
import dResources.DConst;
import org.w3c.dom.*;



public class XMLTools {

  public XMLTools() {
  }

  /**
   * get a root document of an xml file
   * @param fileName
   * @return
   */
  public final static Element getRootDocument(String fileName){
    Element element;
   int numberfoElements=0;
   String error="";//DConst.ERROR_XML_FILE;
   try{
     readFile xmlFile = new readFile();
     Document  doc = xmlFile.getDocumentFile(fileName);
     ReadXMLElement list= new ReadXMLElement();
     element= list.getRootElement(doc);
   }catch(Exception e){
     System.out.println("Import file name :"+ e);
     error= e.toString();
     return null;
    }
    return element;
  }
}