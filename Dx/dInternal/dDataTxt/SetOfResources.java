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
  int _stateSort=0;// give type of the last sort
  private StringTokenizer _st;// resource in text format
  private int _numberOfLines;// represent number of days
  private int _numberOfColumns;// represent number of period a day.
  private int _currentKey=0;
  private static final String CR_LF = "\r\n";

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
  public boolean analyseTokens(){

    return false;
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
    resource.setKey(_currentKey);
    _currentKey++;
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
 public boolean setResource(Resource resc){

   return false;
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
  public void sortResourceListByID(){
    sort(0,_resourceList.size()-2,1);
    _stateSort=1;
  }

  /**
   *
   * */
  public void sortResourceListByKey(){
    sort(0,_resourceList.size()-2,0);
    _stateSort=0;
  }

  /**
   *
   * */
  public String toString(){
    String reslist="";
    for (int i=0; i< _resourceList.size()-1; i++)
      reslist+= ((Resource)_resourceList.get(i)).toString()+CR_LF;
    reslist+= ((Resource)_resourceList.get(_resourceList.size()-1)).toString();
    return reslist;
  }

  public Vector getNamesVector(){
    Vector namesVector =new Vector();
    if(_stateSort!=1)
      sortResourceListByID();
    for (int i=0; i< this._resourceList.size(); i++)
      namesVector.add(((Resource)_resourceList.get(i)).getID());
   return namesVector;
  }

  /**
   * Begin of private sort methods
   * */
  // principal sort
  // INPUTS: begin (begin of resourceList vector), end (end of resourceList vector)
  // sortType (0= sort by key; 1= sort by ID)
  private void sort(int begin, int end, int sortType){
    if (begin >= end)
      return;
    int p=0;
    switch(sortType){
      case 0:
        p= partitionKey(begin, end);
        break;
      case 1:
        p= partitionID(begin, end);
        break;
    }// end switch(sortType)
    sort(begin, p,sortType);
    sort(p+1, end,sortType);
  }

  // manage partition
  private int partitionKey(int begin, int end){
    Resource pivot= (Resource)_resourceList.get(begin);
    int i= begin -1;
    int j= end+1;
    while( i< j){
      i++;
      while(((Resource)_resourceList.get(i)).getKey() < pivot.getKey())
        i++;
      while(((Resource)_resourceList.get(j)).getKey() > pivot.getKey())
        j--;
      if(i < j)
        swap(i,j);
    }
    return j;
  }

  private int partitionID(int begin, int end){
   Resource pivot= (Resource)_resourceList.get(begin);
   int i= begin -1;
   int j= end+1;
   while( i< j){
     i++;
     while(((Resource)_resourceList.get(i)).getID().compareTo(pivot.getID()) < 0)
       i++;
     while(((Resource)_resourceList.get(j)).getID().compareTo(pivot.getID()) > 0)
       j--;
     if(i < j)
       swap(i,j);
   }
   return j;
  }

  // permit elements
  private void swap (int begin, int end){
    Resource temp;
    temp = (Resource)_resourceList.get(begin);
    _resourceList.setElementAt((Resource)_resourceList.get(end),begin);
    _resourceList.setElementAt(temp,end);
  }
  //end of private sort methods

  /**
   * Main
   */
  public static void main(String[] args) {
       Instructor inst= new Instructor();
       inst.addDispDay("1 1 1 5 5");
       inst.addDispDay("5 5 1 1 5");
       Resource resc = new Resource("Alex", inst);
       System.out.println(resc.toString());//debug
       resc = new Resource("Yannick", inst);
       System.out.println(resc.toString());//debug

    } // end main


}