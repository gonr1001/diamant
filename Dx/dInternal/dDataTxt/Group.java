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


public class Group extends DXObject{

  /**the bloc list*/
  private ResourceList _blocList;

  /**
   * Constructor
   * */
  public Group() {
    _blocList= new ResourceList(0);
  }

  /**
   * add a bloc object in the list
   * @param String the ID of the bloc
   * @return boolean result of the operation
   * */
  public boolean addBloc(String id){
    Bloc bloc= new Bloc();
    Resource actBloc = new Resource(id,bloc);
    return _blocList.addResource(actBloc,1);
  }

  /**
   * add a bloc object in the list
   * @param String the ID of the bloc
   * @param Bloc the bloc to be added
   * @return boolean result of the operation
   * */
  public boolean addBloc(String id, Bloc bloc){
    Resource actBloc = new Resource(id,bloc);
    return _blocList.addResource(actBloc,1);
  }

  /**
   * remove a bloc object from de list
   * @param String the ID of the bloc
   * @return boolean result of the operation
   * */
  public boolean removeBloc(String id){
    return _blocList.removeResource(id);
  }

  /**
   * set a bloc object in the list
   * @param Resource the bloc resource
   * @return boolean result of the operation
   * */
  public boolean setBloc(Resource group){
    return _blocList.setResource(group);
  }

  /**
   * return a bloc object from de the list
   * @param String the ID of the bloc
   * @return Resource the nature object
   * */
  public Resource getBloc(String id){
    return _blocList.getResource(id);
  }

  /**
   * return a bloc object from de the list
   * @param String the ID of the bloc
   * @return Resource the nature object
   * */
  public ResourceList getBlocList(){
    return _blocList;
  }

  /**
   *This object (which is already a string!) is itself returned.
   * @return the string itself
   * */
  public String toString(){
   return _blocList.toString();
  }
}