package dInternal.dUtil;

/**
 * <p>Title: Diamant 1.5</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author YS
 * @version 1.0
 */






public class XMLTools {

  /**
   * get a root document of an xml file
   * @param fileName
   * @return
   
  public final static Element getRootDocument(String fileName){
    Element element;
   //int numberfoElements=0;
   String error="";//DConst.ERROR_XML_FILE;
   try{
     ReadXMLFile xmlFile = new ReadXMLFile();
     Document  doc = xmlFile.getDocumentFile(fileName);
     ReadXMLElement list= new ReadXMLElement();
     element= list.getRootElement(doc);
   }catch(Exception e){
     System.out.println("Import file name :"+ e);
     error+= e.toString();
     return null;
    }
    return element;
  }
*/
  /**
   * check if a tag exist in a xml element
   * @param element
   * @param tag
   * @return
  
  public final static String tagError(Element element, String tag){
    ReadXMLElement list= new ReadXMLElement();
    String error="";
    if(element==null)//{
      return DConst.ERROR_XML_FILE;
    //}else{
      int size= list.getSize(element,tag);
      if (size == 0){
        error = DConst.ERROR_XML_FILE;
      }
   // }// end else if(element==null)

    return error;

  }
 */
  /**
   * return the number of occurence of an element représent by its tag
   * @param element
   * @param tag
   * @return
   
  public final static int tagSize(Element element, String tag){
    ReadXMLElement list= new ReadXMLElement();
    if (element==null)
      return 0;
    return list.getSize(element,tag);
  }
*/
}