/*
 * Created on 26 nov. 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dInternal.dData.dActivities;


import dInternal.DObject;
import dInternal.DResource;
import dInternal.DSetOfResources;
import dInternal.dData.StandardCollection;


public class Type extends DObject{

  /**the group list*/
  private DSetOfResources _setOfSections;

  /**
   * Constructor
   * */
  public Type() {
    _setOfSections= new StandardCollection();
  }

  /**
   * add a group object in the list
   * @param String the ID of the group
   * @return boolean result of the operation
   * */
  public boolean addSection(String id){
    Section section= new Section();
    return _setOfSections.addResource(new DResource(id,section),1);
  }

  /**
   * add a group object in the list
   * @param String the ID of the group
   * @return boolean result of the operation
   * */
  public boolean addSection(String id, int nbCycle, boolean isManualCreated){
    Section section= new Section();
    section.addUnity("1",nbCycle, isManualCreated);
    DResource rescsection=new DResource(id,section);
    rescsection.setManuallyCreated(isManualCreated);
    return _setOfSections.addResource(rescsection,1);
  }

  /**
   * add a group object in the list
   * @param String the ID of the group
   * @param Group the group to be added
   * @return boolean result of the operation
   * */
  public boolean addSection(String id, Section section){
    return _setOfSections.addResource(new DResource(id,section),1);
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
  public boolean setSection(DResource group){
    return _setOfSections.setResource(group);
  }

  /**
   * return a group object from de the list
   * @param String the ID of the Group
   * @return Resource the nature object
   * */
  public DResource getSection(String id){
    return _setOfSections.getResource(id);
  }

  /**
   * return the group list
   * @return SetOfResources the list of nature object
   * */
  public DSetOfResources getSetOfSections(){
    return _setOfSections;
  }

  /**
   *This object (which is already a string!) is itself returned.
   * @return the string itself
   * */
  public String toWrite(){
    return _setOfSections.toWrite();
  }
  /**
   * compare this resource with the specified resource
   * @param resource the specified resource
   * @return bolean true if this resource and the specified resource are equals
   * false if they are not equals
   * */
  public boolean isEquals(DObject type){
    Type types = (Type)type;
    if(!this._setOfSections.isEquals( types._setOfSections))
      return false;
    return true;
  }

/* (non-Javadoc)
 * @see dInternal.DObject#getSelectedField()
 */
public long getSelectedField() {
	// TODO Auto-generated method stub
	return 0;
}

}