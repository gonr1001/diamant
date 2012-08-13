/**
 * #rep
 * This comment must be replaced by
 * a copyright or copy left allowing to
 * distribute the code as open source 
 *
 * the prefix for the packages is
 * ca.sixs.
 *
 * 
 */
package ca.sixs.mains;

import javax.swing.JComponent;
import javax.swing.JMenuBar;

import org.jdesktop.application.Application;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.SingleFrameApplication;

/**
 * @author gonr1001
 *
 */
public class ApplicationDia extends SingleFrameApplication {

	/**
	 * @param args
	 */
		
	public static void main(String[] args) {
		Application.launch(ApplicationDia.class, args);
	}

	protected void initialize(String[] args) {
		super.initialize(args);
	}

	protected void startup() {
		FrameView mainView = getMainView();
		mainView.setMenuBar(createMenuBar());
		mainView.setComponent(createComponent());
		show(mainView);
	}

	private JComponent createComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	private JMenuBar createMenuBar() {
		_mainMenu = new javax.swing.JMenuBar();
		_fileMenu = new javax.swing.JMenu();
		_aboutMenuItem = new javax.swing.JMenuItem();
		_exitMenuItem = new javax.swing.JMenuItem();
		_fileMenu.setMnemonic('F');
		_fileMenu.setText("File");
		_aboutMenuItem.setMnemonic('A');
		_aboutMenuItem.setText("About");
		_aboutMenuItem.setToolTipText("About");
//		_aboutMenuItem.setAction(map.get("About"));
//		_aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
//			public void actionPerformed(java.awt.event.ActionEvent evt) {
//				aboutMenuItemActionPerformed(evt);
//			}
//		});

		_fileMenu.add(_aboutMenuItem);

		_exitMenuItem.setMnemonic('E');
		_exitMenuItem.setText("Exit");
		_exitMenuItem.setToolTipText("Quit Team, Quit!");
//		_exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
//			public void actionPerformed(java.awt.event.ActionEvent evt) {
//				exitMenuItemActionPerformed(evt);
//			}
//		});

		_fileMenu.add(_exitMenuItem);

		_mainMenu.add(_fileMenu);

		return _mainMenu;
	}

	
	
	private javax.swing.JMenuBar _mainMenu;
	private javax.swing.JMenu _fileMenu;
	
	private javax.swing.JMenuItem _exitMenuItem;
	
	private javax.swing.JMenuItem _aboutMenuItem;
	
	
	


}
