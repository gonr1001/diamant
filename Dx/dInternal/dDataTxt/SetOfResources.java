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
import dResources.DXObject;

public class ResourceList extends DXObject{

  /**contains list of resource (instructor, rooms, student or activity)*/
  private Vector _resourceList;
  /**give type of the last sort _stateSort 0= sortResourceListByKey;
   * 1= sortResourceListByID; 2= sortResourceListBySelectedField
   */
  int _stateSort=0;
  /**resource in text format*/
  private StringTokenizer _st;//
  private int _numberOfLines;
  private int _numberOfColumns;// represent number of period a day.
  private long _currentKey=0;
  /**0= activities, 1= students, 2= instructors, 3 = rooms*/
  private int _resourceType;
  public static final String CR_LF = "\r\n";
  private Resource _resource;


  /**
   * Constructor call with byte[]  dataloaded
   * */
  public ResourceList( int resType) {
    _resourceList = new Vector(1,1);
    _resourceType = resType;
  }
  /**
   * Constructor call with byte[]  dataloaded and resource type resType
   * */
/*  public ResourceList(byte[]  dataloaded, int resType) {
    _st = new StringTokenizer(new String (dataloaded),"\r\n" );
    _resourceList = new Vector(1,1);
    _resourceType = resType;
  }*/

  /**
   * Constructor call with byte[]  dataloaded,  int nbDay (number of days),
   * int ndPerDay (number of period a day) and resource type resType
   * */
  public ResourceList(int nbDay, int ndPerDay, int resType) {
    //_st = new StringTokenizer(new String (dataloaded),"\r\n" );
    _numberOfLines = nbDay;
    _numberOfColumns = ndPerDay;
    _resourceList = new Vector(1,1);
    _resourceType = resType;
  }

  /**
   * methode analyse st, a stringtokenizer variable
   * @param
   * @return Vector
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
   * Add a Resource to ResourceList
   * @param resource a Resource object
   * @param insertType an integer. it select the type of insert you want to do
   * insertType=0 (insert by using key); insertType=1 (insert by using ID)
   * @return boolean (true if Resource added to list, false if Resource not
   * added because it already exists)
   * */
  public boolean addResource(Resource resource, int insertType){
    //if (getIndexOfResource(resource.getID()) == -1){
      resource.setKey(_currentKey);
      //if (_stateSort!=0)
        //this.sortResourceListByKey();
      int index = 0;
      int add=-1;
      if (insertType==0){
        add = getIndexOfResource(_currentKey);
        if (add==-1)
          index = searchWhereToInsert(_currentKey);
      } else{
        if (insertType==1){
          add = getIndexOfResource(resource.getID());
          if (add==-1)
            index =searchWhereToInsert(resource.getID());
        }// end if (insertType==1)
      }// end else if (insertType==0)

      if (add==-1){
        if (index >= (_resourceList.size()-1))
          _resourceList.add(resource);
        else
          _resourceList.insertElementAt(resource, index);
        _currentKey++;
        return true;
      }
      //_resourceList.add(resource);

      return false;
    //}
    //return false;
  }

  /**
   * set the resource list
   * @param Vector the vector of resource list to set
   * */
  public void setResourceList(Vector rlist){
    _resourceList = rlist;
  }

  /**
   * get the resource list
   * @return Vector the vector of resource list
   * */
  public Vector getResourceList(){
    return _resourceList;
  }

  /**
   * return the size of nature list
   * */
  public int size(){
    return _resourceList.size();
  }

  /**
   * Set the current key of the ResourceList
   * @param currentkey, a long integer
   * */
  public void setCurrentKey(long currentkey){
    _currentKey = currentkey;
  }

  /**
   * Remove a Resource from ResourceList
   * @param int the position of the resource in the resourcelist
   * @return boolean result of the operation. true if resource removed
   * succesfully and false otherwise
   * */
  public boolean removeResourceAt(int position){
    if (position< _resourceList.size()){
      _resourceList.removeElementAt(position);
      return true;
    }
    return false;
  }

  /**
   * Remove a Resource from ResourceList
   * @param ResourceID, a String
   * @return boolean result of the operation. true if resource removed
   * succesfully and false otherwise
   * */
  public boolean removeResource(String ResourceID){
    int index = getIndexOfResource(ResourceID);
    if (index != -1){
      _resourceList.removeElementAt(index);
      return true;
    }
    return false;
  }

  /**
   * Remove a Resource from ResourceList
   * @param key, a long integer
   * @return boolean result of the operation. true if resource removed
   * succesfully and false otherwise
   * */
  public boolean removeResource(long key){
    int index = getIndexOfResource(key);
    if (index!=-1){
      _resourceList.removeElementAt(index);
      return true;
    }
    return false;
  }

  /**
   * Get a Resource from the ResourceList
   * @param long integer, the key of the Resource
   * @return Resource null if Resource didn't found
   * */
  public Resource getResource(long key){
    int index = getIndexOfResource(key);
    if (index!=-1)
      return (Resource) _resourceList.get(index);
    return null;
  }

  /**
   * Get a Resource from the ResourceList
   * @param integer, the position of the Resource
   * @return Resource null if Resource didn't found
   * */
  public Resource getResourceAt(int position){
    if (position< _resourceList.size())
      return (Resource) _resourceList.get(position);
    return null;
  }

  /**
   *
   * */
  public int getIndexOfResource(long key){
    return searchKey(key);
  }

  /**
   *
   * */
  public int getIndexOfResource(String id){
    return searchID(id);
  }
  /**
  * Set a Resource in the ResourceList
  * @param Resource the resource to set
  * @return boolean true if Resource set succesful and false otherwise
  * */
 public boolean setResource(Resource resc){
   int index = getIndexOfResource(resc.getKey());
   if (index != -1){
     _resourceList.setElementAt(resc, index);
    return true;
   }
   return false;
  }

  /**
   * Get a Resource from the ResourceList
   * @param String The ID of Resource
   * @return Resource null if Resource didn't found
   * */
  public Resource getResource(String ID){
    for (int i = 0; i < _resourceList.size(); i++){
      if (((Resource)_resourceList.get(i)).getID().equalsIgnoreCase( ID))
        return (Resource)_resourceList.get(i);
    }
    return null;
  }

  /**
   * Sort the ResourceList by Resource's ID from smallest to biggest
   * */
  public void sortResourceListByID(){
    sort(0,_resourceList.size()-1,1,0);
    _stateSort=1;
  }

  /**
   * Sort the ResourceList by Resource's Key from smallest to biggest
   * */
  public void sortResourceListByKey(){
    sort(0,_resourceList.size()-1,0,0);
    _stateSort=0;
  }

  /**
  * Sort the ResourceList by Resource object selected field from smallest to biggest
  * */
 public void sortResourceListBySelectedObjectField(int field){
   sort(0,_resourceList.size()-1,2,field);
   _stateSort=2;
  }

  /**
   *This object (which is already a string!) is itself returned.
   * @return the string itself
   * */
  public String toString(){
    String reslist="";
    if(_resourceList.size()>0){
      if (_resourceType==3){
        for (int i=0; i< _resourceList.size()-1; i++)
          reslist+= ((Resource)_resourceList.get(i)).toString(";")+CR_LF;
        reslist+= ((Resource)_resourceList.get(_resourceList.size()-1)).toString(";");
      }else{
        for (int i=0; i< _resourceList.size()-1; i++)
          reslist+= ((Resource)_resourceList.get(i)).toString(CR_LF)+CR_LF;
        reslist+= ((Resource)_resourceList.get(_resourceList.size()-1)).toString(CR_LF);
      }
    }// end if(_resourceList.size()>0)
    return reslist;
  }

  /**
   * Build a list of Resources's ID
   * @return Vector It contents the Resources's ID
   * */
  public Vector getNamesVector(){
    Vector namesVector =new Vector();
    if(_stateSort!=1)
      sortResourceListByID();
    for (int i=0; i< this._resourceList.size(); i++)
      namesVector.add(((Resource)_resourceList.get(i)).getID());
   return namesVector;
  }

  /**
   * principal sort
  * @param integer represent the beginning of resourceList vector
  * @param integer represent the end of resourceList vector
  * @param integer 0= sort by key; 1= sort by ID, 2= sort by selected object field
  * @param integer if there's sort by selected object field it represent the
  * object's field selected
  * */
  private void sort(int begin, int end, int sortType, int field){
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
      case 2:
        p= partitionSelectedField(begin, end, field);
        break;
    }// end switch(sortType)
     //System.out.print("+"+begin+"-"+end);//debug
    sort(begin, p,sortType, field);
    sort(p+1, end,sortType, field);
  }

  // manage partition by key
  private int partitionKey(int begin, int end){
    Resource pivot= (Resource)_resourceList.get(begin);
    int i= begin -1;
    int j= end+1;
    while( i< j){
      i++;
      while(((Resource)_resourceList.get(i)).getKey() < pivot.getKey())
        i++;
      j--;
      while(((Resource)_resourceList.get(j)).getKey() > pivot.getKey())
        j--;
      if(i < j)
        swap(i,j);
    }
    return j;
  }

  // manage partition by selected field of object
  private int partitionSelectedField(int begin, int end, int field){
    Resource pivot= (Resource)_resourceList.get(begin);
    int i= begin -1;
    int j= end+1;
    while( i< j){
      i++;
      while(((Resource)_resourceList.get(i)).getObject().getSelectedField(field) <
            pivot.getObject().getSelectedField(field))
        i++;
      j--;
      while(((Resource)_resourceList.get(j)).getObject().getSelectedField(field) >
            pivot.getObject().getSelectedField(field))
        j--;
      if(i < j)
        swap(i,j);
    }
    return j;
  }

  // manage partition by ID
  private int partitionID(int begin, int end){
   Resource pivot= (Resource)_resourceList.get(begin);
   int i= begin -1;
   int j= end+1;
   while( i< j){
     i++;
     while(((Resource)_resourceList.get(i)).getID().compareTo(pivot.getID()) < 0)
       i++;
     j--;
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
    //System.out.print("+"+begin+"-"+end);//debug
    temp = (Resource)_resourceList.get(begin);
    _resourceList.setElementAt((Resource)_resourceList.get(end),begin);
    _resourceList.setElementAt(temp,end);
  }
  //end of private sort methods

  /**
   * finds a index in a sorted vector, using the binary search algorithm
   * @param long the Resource key from wich to find index in ResourceList
   * @return int index of the Resource in ResourceList
   * */
  private int searchKey(long key){
    if (_stateSort!=0)
      sortResourceListByKey();
    int low = 0;
    int high = _resourceList.size()-1;
    long middleKey=0;
    while(low <= high){
      int mid = (low + high)/2;
      int diff=1;
      middleKey = ((Resource)_resourceList.get(mid)).getKey();
      if (middleKey> key)
        diff = 1;
      else{
         if (middleKey< key)
           diff=-1;
         else
           diff=0;
      }
      //long diff = ((Resource)_resourceList.get(mid)).getKey() - key;
      if (diff == 0)
        return mid;
      else{
        if (diff < 0)
          low = mid + 1;
        else
          high = mid - 1;
      }//end else if (diff == 0)
    }//end while(low <= high)
    return -1;
  }

  /**
   * finds a index in a sorted vector, using the binary search algorithm
   * @param String the Resource ID from wich to find index in ResourceList
   * @return int index of the Resource in ResourceList
   * */
  private int searchID(String id){
    if (_stateSort!=1)
      this.sortResourceListByID();
    int low = 0;
    int high = _resourceList.size()-1;
    while(low <= high){
      int mid = (low + high)/2;
      int diff = ((Resource)_resourceList.get(mid)).getID().compareTo(id);
      if (diff == 0)
        return mid;
      else{
        if (diff < 0)
          low = mid + 1;
        else
          high = mid - 1;
      }//end else if (diff == 0)
    }//end while(low <= high)
    return -1;
  }

  /**
   * finds a index in a sorted vector, using the binary search algorithm
   * @param String the Resource ID from wich to find index in ResourceList
   * @return int index of the Resource in ResourceList
   * */
  private int searchWhereToInsert(String id){
    if (_stateSort!=1)
      this.sortResourceListByID();
    int low = 0;
    int high = _resourceList.size()-1;
    while(low <= high){
      int mid = (low + high)/2;
      int diff = ((Resource)_resourceList.get(mid)).getID().compareTo(id);
      if (diff == 0)
        return mid;
      else{
        if (diff < 0)
          low = mid + 1;
        else
          high = mid - 1;
      }//end else if (diff == 0)
    }//end while(low <= high)
    return low;
  }

  /**
   * finds a index where to insert a RESOURCE in a sorted vector, using the
   * binary search algorithm
   * @param long the Resource key from wich to find index in ResourceList
   * @return int index of the Resource in ResourceList
   * */
  private int searchWhereToInsert(long key){
    if (_stateSort!=0)
      sortResourceListByKey();
    int low = 0;
    int high = _resourceList.size()-1;
    long middleKey=0;
    while(low <= high){
      int mid = (low + high)/2;
      int diff=1;
      middleKey = ((Resource)_resourceList.get(mid)).getKey();
      if (middleKey> key)
        diff = 1;
      else{
         if (middleKey< key)
           diff=-1;
         else
           diff=0;
      }
      //long diff = ((Resource)_resourceList.get(mid)).getKey() - key;
      if (diff == 0)
        return mid;
      else{
        if (diff < 0)
          low = mid + 1;
        else
          high = mid - 1;
      }//end else if (diff == 0)
    }//end while(low <= high)
    return low;
  }

}