package dInternal;

/**
 * <p>Title: miniDia</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */
import java.util.StringTokenizer;
import java.util.Vector;

abstract class ResourceList {

  private Vector _resourceList;// contains list of resource (instructor, rooms, student or activity)
  private Resource _resource;
  private StringTokenizer _st;// resource in text format
  private int _numberOfLines;// represent number of days
  private int _numberOfColumns;// represent number of period a day.

  /**
   * Constructor call with byte[]  dataloaded
   * */
  public ResourceList(byte[]  dataloaded) {
    _st = new StringTokenizer(new String (dataloaded),"\r\n" );
    _resourceList = new Vector();
  }

  /**
   * Constructor call with byte[]  dataloaded,  int nbDay (number of days),
   * int ndPerDay (number of period a day)
   * */
  public ResourceList(byte[]  dataloaded, int nbDay, int ndPerDay) {
    _st = new StringTokenizer(new String (dataloaded),"\r\n" );
    _numberOfLines = nbDay;
    _numberOfColumns = ndPerDay;
    _resourceList = new Vector();
  }

  /**
   * methode analyse st, a stringtokenizer variable
   * INPUT:
   * OUTPUT: Vector
   */
  public Vector analyseTokens(){

    return null;
  }

  /**
   *build instructors list.
   *use StringTokenizer st: instructors in text format
   *
   */
  public void buildResourceList(){

  }

  /**
   *
   * */
  public void addResource(Resource resource){
    _resourceList.add(resource);
  }

  /**
   *
   * */
  public void removeResource(String instID){

  }

  /**
   *
   * */
  public Resource getResource(int key){

    return null;
  }

  /**
   *
   * */
  public Resource getResource(String ID){
    for (int i = 0; i < _resourceList.size(); i++){
      if (((Resource)_resourceList.get(i)).getID().equalsIgnoreCase( ID))
        return (Resource)_resourceList.get(i);
    }
    return null;
  }

  /**
   *
   * */
  public void sortResourceList(){

  }

  /**
   *
   * */
  public String toString(){

    return "";
  }
  /**
   * Main
   */
  public static void main(String[] args) {
       Instructor inst= new Instructor();
       inst.addDispDay("1 1 1 5 5");
       inst.addDispDay("5 5 1 1 5");
       Resource resc = new Resource(0,"Alex", inst);
       System.out.println(resc.toString());//debug
       resc = new Resource(1,"Yannick", inst);
       System.out.println(resc.toString());//debug

    } // end main

}