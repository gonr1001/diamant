package dInternal;

/**
 * <p>Title: DX</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */
import dResources.DXObject;

public class Nature extends DXObject{

  /**the group list*/
  private ResourceList _groupList;

  /**
   * Constructor
   * */
  public Nature() {
    _groupList= new ResourceList(0);
  }

  /**
   * add a group object in the list
   * @param String the ID of the group
   * @return boolean result of the operation
   * */
  public boolean addGroup(String id){
    Group group= new Group();
    Resource actGroup = new Resource(id,group);
    return _groupList.addResource(actGroup,1);
  }

  /**
   * add a group object in the list
   * @param String the ID of the group
   * @param Group the group to be added
   * @return boolean result of the operation
   * */
  public boolean addGroup(String id, Group group){
    Resource actGroup = new Resource(id,group);
    return _groupList.addResource(actGroup,1);
  }

  /**
   * remove a group object from de list
   * @param String the ID of the group
   * @return boolean result of the operation
   * */
  public boolean removeGroup(String id){
    return _groupList.removeResource(id);
  }

  /**
   * set a group object in the list
   * @param Resource the group resource
   * @return boolean result of the operation
   * */
  public boolean setGroup(Resource group){
    return _groupList.setResource(group);
  }

  /**
   * return a group object from de the list
   * @param String the ID of the Group
   * @return Resource the nature object
   * */
  public Resource getGroup(String id){
    return _groupList.getResource(id);
  }

  /**
   * return the group list
   * @return ResourceList the list of nature object
   * */
  public ResourceList getGroupList(){
    return _groupList;
  }

  /**
   *This object (which is already a string!) is itself returned.
   * @return the string itself
   * */
  public String toString(){
   return _groupList.toString();
  }
}