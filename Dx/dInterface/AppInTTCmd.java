package dInterface;


public class AppInTTCmd implements Command {
  private DDocument _dd;

  public AppInTTCmd(DDocument dd) {
    _dd = dd;
  }

  public void execute(DApplication dApplic) {
    _dd.updateStatusPanel();
    //_dd.getTTPanel().updateTTPanel();

  }
}
