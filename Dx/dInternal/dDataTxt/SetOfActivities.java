package dInternal;

/**
 * <p>Title: DX</p>
 * <p>Description: exam timetable construction with Condition Pattern</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author  ysyam, alexander
 * @version 1.0
 */

public class ActivitiesList extends ResourceList{

  private byte[] _dataloaded; //_st;// activities in text format

  public ActivitiesList(byte[] dataloaded) {
    super(dataloaded,1);
    _dataloaded = dataloaded;
  }

  public boolean analyseTokens(){
    return true;
  }

  public void buildActivitiesList(){

  }

  public boolean addActivity(Activity activity){

    return true;
  }

  public boolean removeActivity(long activityKey){

    return true;
  }
}