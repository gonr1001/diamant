package dInternal.dData;

/**
 * <p>Title: DX</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */
import dInternal.dUtil.DXObject;

public class Type extends DXObject{

  /**the group list*/
  private SetOfResources _setOfSections;

  /**
   * Constructor
   * */
  public Type() {
    _setOfSections= new SetOfResources(0);
  }

  /**
   * add a group object in the list
   * @param String the ID of the group
   * @return boolean result of the operation
   * */
  public boolean addSection(String id){
    Section section= new Section();
    return _setOfSections.addResource(new Resource(id,section),1);
  }

  /**
   * add a group object in the list
   * @param String the ID of the group
   * @param Group the group to be added
   * @return boolean result of the operation
   * */
  public boolean addSection(String id, Section section){
    return _setOfSections.addResource(new Resource(id,section),1);
  }

  /**
   * remove a group object from de list
   * @param String the ID of the group
   * @return boolean result of the operation
   * */
  public boolean removeSection(String id){
    return _setOfSections.removeResource(id);
  }

  /**
   * set a group object in the list
   * @param Resource the group resource
   * @return boolean result of the operation
   * */
  public boolean setSection(Resource group){
    return _setOfSections.setResource(group);
  }

  /**
   * return a group object from de the list
   * @param String the ID of the Group
   * @return Resource the nature object
   * */
  public Resource getSection(String id){
    return _setOfSections.getResource(id);
  }

  /**
   * return the group list
   * @return SetOfResources the list of nature object
   * */
  public SetOfResources getSetOfSections(){
    return _setOfSections;
  }

  /**
   *This object (which is already a string!) is itself returned.
   * @return the string itself
   * */
  public String toWrite(){
   return _setOfSections.toWrite();
  }
}