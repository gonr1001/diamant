package dInterface;

public class AppInTTCmd implements Command {
  private DDocument _dd;

  public AppInTTCmd(DDocument dd) {
    _dd = dd;
  }

  public void execute() {
    _dd.updateBottomPanel();

  }
}
