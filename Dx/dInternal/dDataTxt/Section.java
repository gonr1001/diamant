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


public class Section extends DXObject{

  /**the bloc list*/
  private SetOfResources _blocList;

  /**
   * Constructor
   * */
  public Section() {
    _blocList= new SetOfResources(0);
  }

  /**
   * add a bloc object in the list
   * @param String the ID of the bloc
   * @return boolean result of the operation
   * */
  public boolean addUnity(String id){
    Unity bloc= new Unity();
    //Resource actBloc = new Resource(id,bloc);
    return _blocList.addResource(new Resource(id,bloc),1);
  }

  /**
  * add a bloc object in the list
  * @param String the ID of the bloc
  * @param int the number of cycle in de TTStructure
  * @return boolean result of the operation
   * */
  public boolean addUnity(String id, int NumberOfCycle, boolean isManualCreated){
    _blocList.sortSetOfResourcesByID();
      Assignment cycleAss = new Assignment();
      cycleAss.setPeriodKey("1.1.1");
      Unity newUnity= new Unity();
      for (int i=1; i<= NumberOfCycle; i++){
        newUnity.addAssignment(new Resource(Integer.toString(i),cycleAss));
      }
      Resource newUnitResc= new Resource(id, newUnity);
      newUnitResc.setManuallyCreated(isManualCreated);
      return _blocList.addResource(newUnitResc,1) ;
  }

  /**
   * add a bloc object in the list
   * @param String the ID of the bloc
   * @param Bloc the bloc to be added
   * @return boolean result of the operation
   * */
  public boolean addUnity(String id, Unity unity){
    //Resource actBloc = new Resource(id,bloc);
    return _blocList.addResource(new Resource(id,unity),1);
  }

  /**
   * remove a bloc object from de list
   * @param String the ID of the bloc
   * @return boolean result of the operation
   * */
  public boolean removeUnity(String id){
    return _blocList.removeResource(id);
  }

  /**
   * set a bloc object in the list
   * @param Resource the bloc resource
   * @return boolean result of the operation
   * */
  public boolean setUnity(Resource section){
    return _blocList.setResource(section);
  }

  /**
   * return a bloc object from de the list
   * @param String the ID of the bloc
   * @return Resource the nature object
   * */
  public Resource getUnity(String id){
    return _blocList.getResource(id);
  }

  /**
   * return a bloc object from de the list
   * @param String the ID of the bloc
   * @return Resource the nature object
   * */
  public SetOfResources getSetOfUnities(){
    return _blocList;
  }

  /**
   *This object (which is already a string!) is itself returned.
   * @return the string itself
   * */
  public String toWrite(){
   return _blocList.toWrite();
  }
  /**
* compare this resource with the specified resource
* @param resource the specified resource
* @return bolean true if this resource and the specified resource are equals
* false if they are not equals
* */
  public boolean isEquals(DXObject sec){
    Section section = (Section)sec;
    if(!this._blocList.isEquals( section._blocList))
      return false;
    return true;
  }

}