package dInterface;

/**
 * <p>Title: Diamant</p>
 * <p>Description:  timetable construction</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: UdeS</p>
 * @author rgr, ysyam, alexander
 * @version 1.0
 */
import javax.swing.JDialog;

public class SelectCmd implements Command {

  public SelectCmd() {
  }

  public void execute(DApplication dApplic) {
    JDialog jd = new JDialog(dApplic.getJFrame(),  "ello", true);
    jd.show();

  }
}