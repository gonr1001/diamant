package dInterface;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author unascribed
 * @version 1.0
 */


public class helloCmd implements Command{

  public helloCmd(DApplication dApplic) {

  }

  public void execute(DApplication dApplic) {
    new helloDlg(dApplic);
  }
}
