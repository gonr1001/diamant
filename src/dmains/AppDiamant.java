package dmains;



import org.jdesktop.application.Application;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.SingleFrameApplication;

public class AppDiamant extends SingleFrameApplication {

	/**
	 * @param args
	 */
		
	public static void main(String[] args) {
		Application.launch(AppDiamant.class, args);
	}

	protected void initialize(String[] args) {

	}

	protected void startup() {
		FrameView mainView = getMainView();

		
		show(mainView);
	}

}
