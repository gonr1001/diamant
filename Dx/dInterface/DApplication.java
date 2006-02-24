/**
 *
 * Title: DApplication
 *
 * Description: DApplication is a class used display the application GUI,
 *              The class creates the main window, and ...
 *
 *
 * Copyright (c) 2001 by rgr.
 * All rights reserved.
 *
 *
 * This software is the confidential and proprietary information
 * of rgr. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with rgr.
 *
 *
 *
 */
package dInterface;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.StringTokenizer;

import javax.swing.DefaultDesktopManager;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import dConstants.DConst;
import dInterface.dTimeTable.CloseCmd;
import dInterface.dTimeTable.ManualImprovementDlg;
import dInterface.dTimeTable.NewTTDlg;
import dInterface.dTimeTable.OpenTTDlg;
import dInterface.dTimeTable.OpenTTSDlg;
import dInterface.dTimeTable.SaveAsTTDlg;
import dInterface.dUtil.AboutDlg;
import dInterface.dUtil.ConflictDlg;
import dInterface.dUtil.PLAFDlg;
import dInterface.dAffectation.ActivityDlg;
import dInterface.dAffectation.AvailabiltyDialog;
import dInterface.dAffectation.EventsDlg;
import dInterface.dAffectation.SectionDlg;
import dInterface.dAlgorithms.PersonalizeMixingAlgorithmDlg;
import dInterface.dData.DefFilesToImportDlg;
import dInterface.dData.ImportDlg;
import dInterface.dData.ReportsDlg;
import dInterface.dMenus.DxMenuBar;
import dInterface.selectiveSchedule.dialog.SelectiveScheduleDlg;
import dInternal.DModel;

import dInternal.Preferences;
import dInternal.dOptimization.SelectAlgorithm;
import eLib.exit.dialog.FatalProblemDlg;
import eLib.exit.dialog.InformationDlg;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class DApplication implements ActionListener {
	private static Logger _logger = Logger.getLogger(DApplication.class
			.getName());

	boolean _inDevelopment;

	// Fake Singletonm, car besoin pour fonctionnalite de Grille Selective
	private static DApplication _instance = null;

	/* ZERO is needed to fix Frame Location (origin) */
	private final static int ZERO = 0;

	/*
	 * ADJUST_HEIGHT is needed to ajdust the screenSize minus the barSize (the
	 * value is a guess) at the bottom
	 */
	// private final static int ADJUST_HEIGHT = 92;
	/*
	 * ADJUST_WIDTH is needed to ajdust the screenSize minus border pixels (the
	 * value is a guess) at each side of the screen
	 */
	private final static int ADJUST_WIDTH = 6;

	/*
	 * MIN_HEIGHT is needed to ajdust the minimum height of the screenSize
	 */
	private final static int MIN_HEIGHT = 512;

	/*
	 * MIN_WIDTH is needed to ajdust the minimum width screenSize
	 */
	private final static int MIN_WIDTH = 512;

	/* _screenSize contains the Dimension of the screen in pixels */
	private Dimension _screenSize;

	private JFrame _jFrame;

	private JDesktopPane _jDesktopPane;

	private Preferences _preferences;

	private DMediator _mediator;

	private String _currentDir;

	private DMenuBar _dMenuBar;

	private DxMenuBar _dxMenuBar;

	private DToolBar _tbar;

	// -------------------------------------------
	/**
	 * DApplication initialize the data members
	 */

	public DApplication() {
		PropertyConfigurator.configureAndWatch("trace" + File.separator
				+ "log4j.conf");
		_logger.warn("Hi from DApplication");
		_instance = this;
		_inDevelopment = false;
	}

	public static DApplication getInstance() {
		return _instance;
	}

	// XXXX Pascal: System.getProperty("user.dir") est appele assez souvent
	// pour que ca vaille la peine d'assigner son resultat a une
	// variable
	public void doIt(String[] args) {
		if (args.length > 0) {
			if (args[0].compareTo("-d") == 0) {
				_inDevelopment = true;
				System.out.println("Mode développement");
			}
		}

		_preferences = new Preferences(System.getProperty("user.dir")
				+ File.separator + "pref" + File.separator + "pref.txt");

		_screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		_mediator = new DMediator(this);
		_currentDir = System.getProperty("user.dir");
		_jFrame = createFrame(DConst.APP_NAME + "   " + DConst.V_DATE);
		/* Icone de l'application */
		ImageIcon iconeDiamant = new ImageIcon(_currentDir + File.separator
				+ "pref" + File.separator + "logoDiamant.gif");
		_jFrame.setIconImage(iconeDiamant.getImage());

		setLAF(_preferences._lookAndFeel);
		_logger.warn("bye_from DApplication"); // XXXX Pascal: Comment ca 'bye'
		// ?!
	}

	// -------------------------------------------
	private JFrame createFrame(String str) {
		JFrame jFrame = new JFrame(str + "   " + System.getProperty("user.dir"));
		jFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		jFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				e.toString(); // this is to aboid a warning
				closeApplic();
			}
		});

		JPanel panel = new JPanel(new BorderLayout(0, 0));
		jFrame.setContentPane(panel);
		if (_inDevelopment) {
			_dxMenuBar = new DxMenuBar(this);
			jFrame.setJMenuBar(_dxMenuBar); // constructs the menu bar
		} else {
			_dMenuBar = new DMenuBar(this);
			jFrame.setJMenuBar(_dMenuBar); // constructs the menu bar
		}

		_tbar = new DToolBar(this); // constucts the tool bar

		jFrame.getContentPane().add(_tbar, BorderLayout.NORTH);

		hideToolBar();

		_jDesktopPane = new JDesktopPane();
		_jDesktopPane.setOpaque(false);
		_jDesktopPane.setDesktopManager(new DefaultDesktopManager());
		panel.add(_jDesktopPane, BorderLayout.CENTER);
		jFrame.setLocation(ZERO, ZERO);
		panel.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		panel.setMaximumSize(_screenSize);

		if (_inDevelopment) {
			panel.setPreferredSize(new Dimension(_screenSize.width
					- ADJUST_WIDTH, 500));
		} else {
			panel.setPreferredSize(new Dimension(_screenSize.width
					- DConst.ADJUST_WIDTH, _screenSize.height
					- DConst.ADJUST_HEIGHT));
		}

		jFrame.pack();
		jFrame.setVisible(true);
		return jFrame;
	} // end createUI

	// -------------------------------------------
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JRadioButton) {
			// XXXX Pascal: Pourquoi ce 'if' est-il vide?
			// Ne devrait pas etre laisse ds cet etat
		}
		if (e.getSource() instanceof CommandHolder) {
			((CommandHolder) e.getSource()).getCommand().execute(this);
		} else {
			System.out
					.println("DApplication: I do not know what to do, please help me (Action Performed)");
			// XXXX Pascal: Devrait etre logge
		}// end if ... else
	}// end actionPerformed

	public JDesktopPane getDesktop() {
		return _jDesktopPane;
	} // end getDesktop

	public JFrame getJFrame() {
		return _jFrame;
	} // end getJFrame

	public DMediator getDMediator() {
		return _mediator;
	} // end getDMediator

	public DDocument getCurrentDoc() {
		return getDMediator().getCurrentDoc();
	} // end getDMediator

	public DMenuBar getMenuBar() {
		return _dMenuBar;
	} // end getDesktop

	public DxMenuBar getDxMenuBar() {
		return _dxMenuBar;
	} // end getDesktop

	public DToolBar getToolBar() {
		return _tbar;
	}

	// -------------------------------------------
	public Preferences getPreferences() {
		return _preferences;
	} // end getPreferences

	public String getCurrentDir() {
		return _currentDir;
	} // end getCurrentDir

	/*
	 * the str can contain a file name, it will be left out
	 */
	public void setCurrentDir(String str) {
		_currentDir = str.substring(0, str.lastIndexOf(File.separator) + 1);
	} // end setCurrentDir

	public void showToolBar() {
		_tbar.setVisible(true);
	}

	public void hideToolBar() {
		_tbar.setVisible(false);
	}

	// -------------------------------------------
	public void setLAF(String str) {
		// Force SwingApp to come up in the System L&F
		try {
			UIManager.setLookAndFeel(str);
			// System.out.println("pref" + str );
		} catch (UnsupportedLookAndFeelException ulafe) {
			new FatalProblemDlg("UnsupportedLookAndFeel: " + str);
			System.err.println("Warning: UnsupportedLookAndFeel: " + str);
			ulafe.printStackTrace();
			System.exit(31);
		} catch (ClassNotFoundException cnfe) {
			new FatalProblemDlg("Error ClassNotFound LookAndFeel" + str);
			System.err.println("Error ClassNotFound LookAndFeel" + str);
			cnfe.printStackTrace();
			System.exit(41);
		} catch (IllegalAccessException iace) {
			new FatalProblemDlg("Error IllegalAccess LookAndFeel" + str);
			System.err.println("Error IllegalAccess LookAndFeel" + str);
			iace.printStackTrace();
			System.exit(51);
		} catch (InstantiationException ie) {
			new FatalProblemDlg("Error Instantiation LookAndFeel" + str);
			System.err.println("Error Instantiation LookAndFeel" + str);
			ie.printStackTrace();
			System.exit(61);
		}

		// SwingUtilities.updateComponentTreeUI(_jFrame); //To erease (AlexJ)
	} // end setLF

	// -------------------------------------------
	/**
	 * This methode updates the look and feel style
	 * 
	 * @param String
	 *            A look and feel style
	 */
	public void updateLAF(String str) {
		setLAF(str);
		SwingUtilities.updateComponentTreeUI(_jFrame);
	}

	public void constructToolBar() {
		_tbar = new DToolBar(this); // constucts the tool bar
		_tbar.updateUI();

		_jFrame.getContentPane().add(_tbar, BorderLayout.NORTH);
		// panel.add(_tbar,BorderLayout.NORTH);
		_tbar.updateUI();
		updateLAF(_preferences._lookAndFeel);

	}

	// -------------------------------------------
	// -------------------------------------------
	/**
	 * Closes the document(s) and the application. Use this method for
	 * processing close via the quit menuItem.
	 * 
	 * @return void
	 * @since JDK 1.2
	 */
	public void closeApplic() {
		// if no Document exit ok
		while (_mediator.getCurrentDoc() != null) { // is a while
			new CloseCmd().execute(this);
			if (_mediator.getCancel())
				break;
		}
		// if Document changed as for save or not
		if (_mediator.getCurrentDoc() == null) {
			_jFrame.setVisible(false);
			_jFrame.dispose();
			System.exit(0);
		}
	}

	/**
	 * Closes the document(s) and the application. Use this method for
	 * processing close via the quit menuItem.
	 * 
	 * @return void
	 * 
	 */
	public void exit() {
		// if no Document exit ok
		while (_mediator.getCurrentDoc() != null) { // is a while
			new CloseCmd().execute(this);
			if (_mediator.getCancel())
				break;
		}
		// if Document changed as for save or not
		if (_mediator.getCurrentDoc() == null) {
			_jFrame.setVisible(false);
			_jFrame.dispose();
			System.exit(0);
		}
	}

	public DModel getDModel() {
		return getDMediator().getCurrentDoc().getDM();
	}

	/**
	 * @return
	 */

	public void setCursorWait() {
		_jFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}

	public void setCursorDefault() {
		_jFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * @return
	 */
	public boolean isInDevelopment() {
		return _inDevelopment;
	}

	/**
	 * 
	 */
	public void newTTableCycle() {
		new NewTTDlg(this, DConst.CYCLE);
	}

	/**
	 * 
	 */
	public void newTTableExam() {
		new NewTTDlg(this, DConst.EXAM);
	}

	/**
	 * the Time table can be cycle or exam
	 */
	public void afterNewTTable() {
		this.getDxMenuBar().afterNewTTable();
	}

	/**
	 * 
	 */
	public void newTTStrucCycle() {
		this.showToolBar();
		this.getDMediator().addDoc(this.getPreferences()._standardTTC,
				DConst.NO_TYPE);
		this.getDxMenuBar().afterNewTTStruc();
	}

	/**
	 * 
	 */
	public void newTTStrucExam() {
		this.showToolBar();
		this.getDMediator().addDoc(this.getPreferences()._standardTTE,
				DConst.NO_TYPE);
		this.getDxMenuBar().afterNewTTStruc();
	}

	/**
	 * 
	 */
	public void openTTable() {
		new OpenTTDlg(this);
	}

	/**
	 * 
	 */
	public void openTTStruc() {
		this.showToolBar();
		new OpenTTSDlg(this);
	}

	/**
	 * 
	 */
	public void afterOpenTTSruc() {
		this.getDxMenuBar().afterOpenTTSruc();
	}

	/**
	 * 
	 */
	public void close() {
		this.getDMediator().closeCurrentDoc();
		if (!this.getDMediator().getCancel()) {
			this.getDxMenuBar().setInitialState();
		}
	}

	/**
	 * 
	 */
	public void save() {
		if (this.getDMediator().getCurrentDoc().getDocumentName().endsWith(
				DConst.NO_NAME))
			new SaveAsTTDlg(this);
		else if (this.getDMediator().getCurrentDoc().isModified())
			this.getDMediator().saveCurrentDoc(
					this.getDMediator().getCurrentDoc().getDocumentName());
		//confirm dialog ?
		//else not necessary to save
	}

	/**
	 * 
	 */
	public void saveAs() {
		new SaveAsTTDlg(this);
	}

	/**
	 * 
	 */
	public void defineFiles() {
		new DefFilesToImportDlg(this);
	}

	/**
	 * 
	 */
	public void importFiles() {
		new ImportDlg(this);
	}

	/**
	 * 
	 */
	public void afterImport() {
		this.getDxMenuBar().afterImport();
	}

	/**
	 * 
	 */
	public void exportFiles() {

		String dir = getTokenDir(this.getDMediator().getCurrentDoc()
				.getDocumentName(), File.separator);

		File fileStu = new File(dir + DConst.TT_STUD_FILE);
		File fileTT = new File(dir + DConst.TT_FILE);
		String mess = "";
		if (fileStu.exists() || fileTT.exists()) {
			mess += "Un ou les deux fichiers existent dans le répertoire"
					+ DConst.CR_LF;
			mess += "PAS d'exportation";
			new InformationDlg(this.getJFrame(), mess, DConst.EXPORT_MESSAGE);
		} else { //if (fileStu.exists() || fileTT.exists())
			this.getDModel().exportData(dir);
			mess += dir + DConst.TT_STUD_FILE + DConst.CR_LF + dir
					+ DConst.TT_FILE + DConst.CR_LF + DConst.EXPORTED;
			new InformationDlg(this.getJFrame(), mess, DConst.EXPORT_MESSAGE);
		}

	}

	/**
	 * return a token in from a stringtokenizer
	 * @param str
	 * @param delimiter
	 * @param position
	 * @return
	 */
	private String getTokenDir(String str, String delimiter) {
		StringTokenizer strToken = new StringTokenizer(str, delimiter);
		String string = "";
		int nbTokens = strToken.countTokens();
		for (int i = 0; i < nbTokens - 1; i++) {
			string += strToken.nextToken() + delimiter;
		}
		return string;
	}

	/**
	 * 
	 */
	public void assignActivities() {
		new ActivityDlg(this, DConst.ACT_LIST);
	}

	/**
	 * 
	 */
	public void assignSections() {
		new SectionDlg(this);
	}

	/**
	 * 
	 */
	public void instructorAvailability() {
		new AvailabiltyDialog(this, this.getDModel().getSetOfInstructors());
	}

	/**
	 * 
	 */
	public void roomAvailability() {
		new AvailabiltyDialog(this, this.getDModel().getSetOfRooms());
	}

	/**
	 * 
	 */
	public void assignEvents() {
		new EventsDlg(this, DConst.EVENTS_DLG_TITLE, true);
	}

	/**
	 * 
	 */
	public void selectiveViews() {
		SelectiveScheduleDlg.getInstance().displayDlg();
	}

	/**
	 * 
	 */
	public void conflictOfAnEvent() {
		new ManualImprovementDlg(this, DConst.MANUALIMPROVEMENT_DLG_TITLE);
	}

	/**
	 * 
	 */
	public void initialAssignment() {
		this.getDModel().initChangeInDModel(this.getJFrame());
		this.getMenuBar().postInitialAssign();
		new InformationDlg(this.getJFrame(), DConst.INITIAL_ASSIGN_MESSAGE);
	}

	/**
	 * 
	 */
	public void afterInitialAssign() {
		this.getDxMenuBar().afterInitialAssignment();
	}

	/**
	 * 
	 */
	public void doTheTimeTable() {
		int _selectedContext = 0;// context for first affect algorithm

		(new SelectAlgorithm(this.getDModel(), _selectedContext)).execute();
		new InformationDlg(this.getJFrame(), DConst.TT_BUILD_MESSAGE);
	}

	/**
	 * 
	 */
	public void doSectionPartition() {
		boolean _userTestActiv = true;

		DConst.USER_TEST_ACTIV = _userTestActiv;
		//new PersonalizeMixingAlgorithmDlg();
		PersonalizeMixingAlgorithmDlg perso = new PersonalizeMixingAlgorithmDlg(
				DConst.DEFAULT_MIX_ALGO);
		String input = perso.showInputDialog();
		if (input != null) {
			int personalizeAcceptableVariation = Integer.parseInt(input);
			(new SelectAlgorithm(personalizeAcceptableVariation, this
					.getDModel())).execute();
			new InformationDlg(this.getJFrame(), DConst.STUDENTS_MIXING_MESSAGE);
		}

	}

	/**
	 * 
	 */
	public void report() {
		new ReportsDlg(this);
	}

	/**
	 * 
	 */
	public void showPLAFDlg() {
		new PLAFDlg(this);
	}

	/**
	 * 
	 */
	public void showConflictsDlg() {
		new ConflictDlg(this);
	}

	/**
	 * 
	 */
	public void simpleView() {
		if (this.getDMediator().getCurrentDoc() != null)
			this.getDMediator().getCurrentDoc().displaySimple();
	}

	/**
	 * 
	 */
	public void horizontalSplitView() {
		if (this.getDMediator().getCurrentDoc() != null)
			this.getDMediator().getCurrentDoc().displayHorizontalSplit();
	}

	/**
	 * 
	 */
	public void vericalSplitview() {
		if (this.getDMediator().getCurrentDoc() != null)
			this.getDMediator().getCurrentDoc().displayVericalSplit();
	}

	/**
	 * 
	 */
	public void showAboutDlg() {
		new AboutDlg(this);
	}

	/**
	 * 
	 */
	public void myFile() {
		setCurrentDir(".\\devData\\");
		getDMediator().addDoc(".\\devData\\fichier1.dia", 0);
		getDMediator().getCurrentDoc().setAutoImportDIMFilePath(".\\devData\\");
		getDModel().changeInDModel(this.getJFrame());
		this.getDxMenuBar().afterInitialAssignment();
	}

	/**
	 * 
	 */
	public void initialState() {
		this.getDxMenuBar().setInitialState();
	}

	/**
	 * 
	 */
	public void showAllMenus() {
		this.getDxMenuBar().showAllMenus();
	}

} /* end class DApplication */
