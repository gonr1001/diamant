/**
 *
 * Title: DApplication
 *
 * Description: DApplication is a class used to display the application GUI,
 *              The class creates the main window, and menubar, and toolBar,
 *              and the logger
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.StringTokenizer;

import javax.swing.DefaultDesktopManager;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import dConstants.DConst;
import dInterface.dTimeTable.ConflictsOfAnEventDlg;
import dInterface.dTimeTable.ManualImprovementDlg;
import dInterface.dTimeTable.OpenTTDlg;
import dInterface.dTimeTable.OpenTTSDlg;
import dInterface.dTimeTable.SaveAsTTDlg;
import dInterface.dUtil.AboutDlg;
import dInterface.dUtil.ConflictDlg;
import dInterface.dUtil.PLAFDlg;
import dInterface.dAffectation.ActivityDlg;
import dInterface.dAffectation.ActivityModifDlg;
import dInterface.dAffectation.AvailabiltyRoomDialog;
import dInterface.dAssignementDlgs.DxInstructorAvailabilityDlg;
import dInterface.dAssignementDlgs.DxRoomAvailabilityDlg;
import dInterface.dAffectation.EventsDlg;
import dInterface.dAffectation.SectionDlg;
import dInterface.dAlgorithms.PersonalizeMixingAlgorithmDlg;
import dInterface.dData.DefFilesToImportDlg;
import dInterface.dData.ImportDlg;
import dInterface.dData.ImportSelectiveFileDlg;
import dInterface.dData.ReportsDlg;
import dInterface.dFileMenuDlgs.NewTimeTableDlg;
import dInterface.dMenus.DxMenuBar;
import dInterface.selectiveSchedule.dialog.SelectiveScheduleDlg;
import dInternal.DModel;

import dInternal.DxPreferences;
import dInternal.dOptimization.DxAssignAllAlg;
import dInternal.dOptimization.DxAssignRoomsAlg;
import dInternal.dOptimization.RoomAssignmentAlgo;
import dInternal.dOptimization.SelectAlgorithm;
import eLib.exit.dialog.FatalProblemDlg;
import eLib.exit.dialog.InformationDlg;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class DApplication { // implements ActionListener {
	private static Logger _logger = Logger.getLogger(DApplication.class
			.getName());

	boolean _inDevelopment;

	// Fake Singleton, car besoin pour fonctionnalite de Grille Selective
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

	private DxPreferences _preferences;

	private DMediator _dMediator;

	private String _currentDir;

	private String _fileToOpen;

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

	public void doIt(String[] args) {
		if (args.length > 0) {
			if (args[0].compareTo("-d") == 0) {
				_inDevelopment = true;
				System.out.println("Mode développement");
			}
		}

		_preferences = new DxPreferences(System.getProperty("user.dir")
				+ File.separator + "pref" + File.separator + "pref.txt");

		_screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		_dMediator = new DMediator(this);
		_currentDir = System.getProperty("user.dir");
		_jFrame = createFrame(DConst.APP_NAME + "   " + DConst.V_DATE);
		/* Icone de l'application */
		ImageIcon iconeDiamant = new ImageIcon(_currentDir + File.separator
				+ "pref" + File.separator + "logoDiamant.gif");
		_jFrame.setIconImage(iconeDiamant.getImage());
		setLAF(_preferences._lookAndFeel);
		_logger.warn("bye_from DApplication"); // this must be the end of an
		// execution
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
		_dxMenuBar = new DxMenuBar(this);
		jFrame.setJMenuBar(_dxMenuBar); // constructs the menu bar

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

	public JDesktopPane getDesktop() {
		return _jDesktopPane;
	} // end getDesktop

	public JFrame getJFrame() {
		return _jFrame;
	} // end getJFrame

	public DMediator getDMediator() {
		return _dMediator;
	} // end getDMediator

	public DDocument getCurrentDoc() {
		return _dMediator.getCurrentDoc();
	} // end getCurrentDoc

	public DxDocument getCurrentDxDoc() {
		return _dMediator.getCurrentDxDoc();
	}

	public DxMenuBar getDxMenuBar() {
		return _dxMenuBar;
	} // end getDxMenuBar

	public DToolBar getToolBar() {
		return _tbar;
	}

	public DxPreferences getPreferences() {
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

	/**
	 * Closes the document(s) and the application. Use this method for
	 * processing close via the quit menuItem.
	 * 
	 * @return void
	 * @since JDK 1.2
	 */
	public void closeApplic() {
		exit();
	}

	/**
	 * Closes the document(s) and the application. Use this method for
	 * processing close via the quit menuItem.
	 * 
	 * @return void
	 * 
	 */
	public void exit() {
		if (DConst.newDoc) {
			// if no Document exit ok
			while (_dMediator.getCurrentDxDoc() != null) { // is a while
				this.close(); // new CloseCmd().execute(this);
				if (_dMediator.getCancel())
					break;
			}
			// if Document changed as for save or not
			if (_dMediator.getCurrentDxDoc() == null) {
				_jFrame.setVisible(false);
				_jFrame.dispose();
				System.exit(0);
			}

		} else {
			// if no Document exit ok
			while (_dMediator.getCurrentDoc() != null) { // is a while
				this.close(); // new CloseCmd().execute(this);
				if (_dMediator.getCancel())
					break;
			}
			// if Document changed as for save or not
			if (_dMediator.getCurrentDoc() == null) {
				_jFrame.setVisible(false);
				_jFrame.dispose();
				System.exit(0);
			}
		}

	}

	public DModel getCurrentDModel() {
		if (DConst.newDoc) {
			return getCurrentDxDoc().getCurrentDModel();
		}
		return getCurrentDoc().getCurrentDModel();
	}

	// public DModel getCurrentDxModel() {
	// return getCurrentDxDoc()..getCurrentDModel();
	// }

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
	public void newTTable() {
		new NewTimeTableDlg(this, DConst.CYCLE);
		this.setCurrentDir(_fileToOpen);

		if (DConst.newDoc) {
			try {
				this.getDMediator().addDxTTableDoc(
						this.getCurrentDir() + DConst.NO_NAME, _fileToOpen);
				_dxMenuBar.afterNewTTable();
			} catch (Exception e) {
				new FatalProblemDlg(this._jFrame, e.toString());
				e.printStackTrace();
			}
		} else {
			try {/* !!!NIC!!! */
				/* error = !!!NIC!!! */this.getDMediator().addDoc(
						this.getCurrentDir() + DConst.NO_NAME, _fileToOpen,
						DConst.CYCLE);
				_dxMenuBar.afterNewTTable();
			} catch (Exception e) {/* !!!NIC!!! */
				// TODO Auto-generated catch block
				e.printStackTrace();/* !!!NIC!!! */
			}/* !!!NIC!!! */
		}

	}

	/**
	 * 
	 */
	public void newTTableExam() {
		new NewTimeTableDlg(this, DConst.EXAM);
		this.setCurrentDir(_fileToOpen);
		/* !!!NIC!!!String error; */
		try {
			/* !!!NIC!!!error = */this.getDMediator().addDoc(
					this.getCurrentDir() + DConst.NO_NAME, _fileToOpen,
					DConst.EXAM);
		} catch (Exception e) {
			/* !!!NIC!!! */
		}

		// XXXX Pascal: Ce 'if' n'est jamais appele s'il y a une erreur dans
		// dApplic.getDMediator().addDoc(), car addDoc() appelle lui-meme
		// new FatalProblemDlg(dApplic.getJFrame(),error);
		// System.exit(1);
		//
		// De plus, par convention, les valeurs positives de sortie d'une
		// application indiquent que tout s'est BIEN passe. Il faudrait
		// retourner une valeur negative quand un probleme majeur survient.
		// /*!!!NIC!!!*/ if (error.length() != 0) {
		// /*!!!NIC!!!*/ new FatalProblemDlg(this.getJFrame(), error);
		// /*!!!NIC!!!*/ System.exit(1);
		// /*!!!NIC!!!*/ }
		_dxMenuBar.afterNewTTable();
	}

	/**
	 * 
	 */
	public void newTTStrucCycle() {
		this.showToolBar();
		this.setCursorWait();
		if (DConst.newDoc) {
			try {
				this._dMediator
						.addDxTTStructureDoc(this.getPreferences()._standardTTC);
				_dxMenuBar.afterNewTTStruc();
			} catch (Exception e) {
				new FatalProblemDlg(this._jFrame, e.toString());
				// as a complement
				e.printStackTrace();
				this.hideToolBar();
			}
		} else {
			try {
				this._dMediator.addDoc(this.getPreferences()._standardTTC,
						DConst.NO_TYPE);
				_dxMenuBar.afterNewTTStruc();
			} catch (Exception e) {
				/* !!!NIC!!! */
			}
		}
		this.setCursorDefault();
	}

	/**
	 * 
	 */
	public void newTTStrucExam() {
		this.showToolBar();
		this.setCursorWait();
		if (DConst.newDoc) {
			try {
				this._dMediator
						.addDxTTStructureDoc(this.getPreferences()._standardTTE);
				_dxMenuBar.afterNewTTStruc();
			} catch (Exception e) {
				new FatalProblemDlg(this._jFrame, e.toString());
				// as a complement
				e.printStackTrace();
				this.hideToolBar();
			}
		} else {
			try {
				this._dMediator.addDoc(this.getPreferences()._standardTTE,
						DConst.NO_TYPE);
				_dxMenuBar.afterNewTTStruc();
			} catch (Exception e) {
				/* !!!NIC!!! */
			}
		}
		this.setCursorDefault();

	}

	/**
	 * 
	 */
	public void openTTable() {
		new OpenTTDlg(this);
		this.afterInitialAssign();
	}

	/**
	 * 
	 */
	public void openTTStruc() {
		this.showToolBar();
		new OpenTTSDlg(this);
		// this._dMediator
		// //.addDxTTStructureDoc(this.getPreferences()._standardTTC);
		_dxMenuBar.afterNewTTStruc();
		this.afterOpenTTSruc();
	}

	/**
	 * 
	 */
	public void afterOpenTTSruc() {
		_dxMenuBar.afterOpenTTSruc();
	}

	/**
	 * 
	 */
	public void close() {
		if (DConst.newDoc) {
			this._dMediator.closeCurrentDxDoc();
			if (!this._dMediator.getCancel()) {
				_dxMenuBar.initialState();
			}
		} else {
			this._dMediator.closeCurrentDoc();
			if (!this._dMediator.getCancel()) {
				_dxMenuBar.initialState();
			}
		}

	}

	/**
	 * 
	 */
	public void save() {
		if (DConst.newDoc) {
			if (this.getCurrentDxDoc().getDocumentName().endsWith(
					DConst.NO_NAME))
				new SaveAsTTDlg(this);
			else if (this.getCurrentDoc().isModified())
				this._dMediator.saveCurrentDoc(this.getCurrentDxDoc()
						.getDocumentName());
			// else not necessary to save
		} else {
			if (this.getCurrentDoc().getDocumentName().endsWith(DConst.NO_NAME))
				new SaveAsTTDlg(this);
			else if (this.getCurrentDoc().isModified())
				this._dMediator.saveCurrentDoc(this.getCurrentDoc()
						.getDocumentName());
			// else not necessary to save
		}

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

	public void doImport(JDialog jD, String fil) {

		try {
			String error = "";
			this.setCursorWait();
			if (this.getCurrentDoc() != null) {
				error = this.getCurrentDModel().importData(fil);
			} else
				error = "ImportDlg : Il n'existe pas de document pour effectuer l'importation des données";
			if (error.length() == 0) {
				new InformationDlg(this.getJFrame(), DConst.IMP_A_SUC);
			} else {
				new FatalProblemDlg(this.getJFrame(),
						"In DApplication.doImport: " + error);
				System.exit(1);
			}
			this.setCursorDefault();
		} catch (Exception e) {
			new FatalProblemDlg("In DApplication.doImport: " + e.toString());
			jD.dispose();
		}
	}

	/**
	 * 
	 */
	public void afterImport() {
		_dxMenuBar.afterImport();
	}

	/**
	 * 
	 */
	public void exportFiles() {

		String dir = getTokenDir(this.getCurrentDoc().getDocumentName(),
				File.separator);

		File fileStu = new File(dir + DConst.TT_STUD_FILE);
		File fileTT = new File(dir + DConst.TT_FILE);
		String mess = "";
		if (fileStu.exists() || fileTT.exists()) {
			mess += "Un ou les deux fichiers existent dans le répertoire"
					+ DConst.CR_LF;
			mess += "PAS d'exportation";
			new InformationDlg(this.getJFrame(), mess, DConst.EXPORT_MESSAGE);
		} else { // if (fileStu.exists() || fileTT.exists())
			this.getCurrentDModel().exportData(dir);
			mess += dir + DConst.TT_STUD_FILE + DConst.CR_LF + dir
					+ DConst.TT_FILE + DConst.CR_LF + DConst.EXPORTED;
			new InformationDlg(this.getJFrame(), mess, DConst.EXPORT_MESSAGE);
		}
	}

	/**
	 * return a token in from a stringtokenizer
	 * 
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
		new ActivityDlg(this);
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
		if (DConst.newDoc) {
			new DxInstructorAvailabilityDlg(this, this.getCurrentDxDoc()
					.getCurrentDModel().getDxSetOfInstructors());
		} else {
			new DxInstructorAvailabilityDlg(this, this.getCurrentDModel()
					.getDxSetOfInstructors());
		}
	}

	/**
	 * 
	 */
	public void roomAvailability() {
		if (DConst.newRooms) {
			if (DConst.newDoc) {
				new DxRoomAvailabilityDlg(this, this.getCurrentDxDoc()
						.getCurrentDModel().getDxSetOfSites());
			} else {
				new DxRoomAvailabilityDlg(this, this.getCurrentDModel()
						.getDxSetOfSites());
			}
			// new DxAvailabiltyRoomDlg(this, this.getCurrentDModel()
			// .getDxSetOfRooms(), DConst.ROOMASSIGN);
			// !!!NIC!!! How do we verify if it's multisite?
			// new DxRoomAvailabilityDlg(this, this.getCurrentDModel()
			// .getDxSetOfSites());
		} else {
			new AvailabiltyRoomDialog(this, this.getCurrentDModel()
					.getSetOfRooms(), DConst.ROOMASSIGN);
		}
	}

	/**
	 * 
	 */
	public void assignEvents() {
		new EventsDlg(this);
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
	public void modifyActivity() {
		new ActivityModifDlg(this);
	}

	/**
	 * 
	 */
	public void mergeInstructors() {
		new ImportSelectiveFileDlg(this, DConst.IMP_SELECT_INST);
	}

	/**
	 * 
	 */
	public void mergeRooms() {
		new ImportSelectiveFileDlg(this, DConst.IMP_SELECT_ROOM);
	}

	/**
	 * 
	 */
	public void mergeActivities() {
		new ImportSelectiveFileDlg(this, DConst.IMP_SELECT_ACT);
	}

	/**
	 * 
	 */
	public void mergeStudents() {
		new ImportSelectiveFileDlg(this, DConst.IMP_SELECT_STUD);
	}

	/**
	 * 
	 */
	public void initialAssignment() {
		this.getCurrentDModel().changeInDModel(this.getJFrame());
		_dxMenuBar.afterInitialAssignment();
		new InformationDlg(this.getJFrame(), DConst.INITIAL_ASSIGN_MESSAGE);
	}

	/**
	 * 
	 */
	public void afterInitialAssign() {
		_dxMenuBar.afterInitialAssignment();
	}

	/**
	 * 
	 */
	public void doTheTimeTable() {
		this.setCursorWait();

		if (DConst.newAlg) {
			new DxAssignAllAlg(this.getCurrentDModel(), this.getPreferences()
					.getDxConflictLimits()).doWork();

		} else {
			int _selectedContext = 0;
			(new SelectAlgorithm(this.getCurrentDModel(), _selectedContext))
					.execute();
		}

		this.setCursorDefault();
		new InformationDlg(this.getJFrame(), DConst.TT_BUILD_MESSAGE);
	}

	/**
	 * 
	 */
	public void doSectionPartition() {
		boolean _userTestActiv = true;

		DConst.USER_TEST_ACTIV = _userTestActiv;
		PersonalizeMixingAlgorithmDlg perso = new PersonalizeMixingAlgorithmDlg(
				DConst.DEFAULT_MIX_ALGO);
		String input = perso.showInputDialog();
		if (input != null) {
			int personalizeAcceptableVariation = Integer.parseInt(input);
			(new SelectAlgorithm(personalizeAcceptableVariation, this
					.getCurrentDModel())).execute();
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
		if (DConst.newDoc){
			if (this.getCurrentDxDoc() != null)
				this.getCurrentDxDoc().displaySimple();
		} else {
			if (this.getCurrentDoc() != null)
				this.getCurrentDoc().displaySimple();
		}
		
		
	}

	/**
	 * 
	 */
	public void horizontalSplitView() {
		if (DConst.newDoc){
			if (this.getCurrentDxDoc() != null)
				this.getCurrentDxDoc().displayHorizontalSplit();
		} else {
		if (this.getCurrentDoc() != null)
			this.getCurrentDoc().displayHorizontalSplit();
		}
	}

	/**
	 * 
	 */
	public void vericalSplitview() {
		if (DConst.newDoc){
			if (this.getCurrentDxDoc() != null)
				this.getCurrentDxDoc().displayVericalSplit();
		} else {
		if (this.getCurrentDoc() != null)
			this.getCurrentDoc().displayVericalSplit();
		}
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
		try {
			if (DConst.newDoc) {
				_dMediator.addDxTTableDoc("", ".\\devData\\fichier1.dia");
			} else {
				_dMediator.addDoc(".\\devData\\fichier1.dia", 0);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		if (DConst.newDoc) {
			getCurrentDxDoc().setAutoImportDIMFilePath(".\\devData\\");
			getCurrentDxDoc().getCurrentDModel().changeInDModel(
					this.getJFrame());
		} else {
			getCurrentDoc().setAutoImportDIMFilePath(".\\devData\\");
			getCurrentDModel().changeInDModel(this.getJFrame());
		}
		_dxMenuBar.afterInitialAssignment();
	}

	/**
	 * 
	 */
	public void initialState() {
		_dxMenuBar.initialState();
	}

	/**
	 * 
	 */
	public void showAllMenus() {
		_dxMenuBar.showAllMenus();
	}

	/**
	 * 
	 */
	public void conflictsOfAnEvent() {
		new ConflictsOfAnEventDlg(this, DConst.CONFLICTS_OF_AN_EVENT_DLG_TITLE);
	}

	/**
	 * 
	 */
	public void roomAssignment() {
		if (DConst.newAlg) {
			if (DConst.newDoc) {
				new DxAssignRoomsAlg(this.getCurrentDxDoc().getCurrentDModel(),
						this.getPreferences().getDxConflictLimits()).doWork();
				new InformationDlg(this.getJFrame(), DConst.ROOM_ASSIGN_MESSAGE);
			} else {
				new DxAssignRoomsAlg(this.getCurrentDModel(), this
						.getPreferences().getDxConflictLimits()).doWork();
				new InformationDlg(this.getJFrame(), DConst.ROOM_ASSIGN_MESSAGE);
			}

		} else {
			if (DConst.newDoc) {
				new RoomAssignmentAlgo(this.getCurrentDxDoc()
						.getCurrentDModel());
				new InformationDlg(this.getJFrame(), DConst.ROOM_ASSIGN_MESSAGE);
			} else {
				new RoomAssignmentAlgo(this.getCurrentDModel());
				new InformationDlg(this.getJFrame(), DConst.ROOM_ASSIGN_MESSAGE);
			}
		}
	}

	/**
	 * 
	 */
	public void eventAssignment() {
		new EventsDlg(this);
	}

	/**
	 * 
	 */
	public void conflictOption() {
		new ConflictDlg(this);

	}

	/**
	 * 
	 */
	public boolean isMultiSite() {
		if (DConst.newDoc) {
			if (this.getCurrentDxDoc() == null) {
				return false;
			}
			return this.getCurrentDxDoc().getCurrentDModel().isMultiSite();
		}
		if (this.getCurrentDoc() == null) {
			return false;
		}
		return this.getCurrentDModel().isMultiSite();

	}

	/**
	 * @param str
	 */
	public void changeInMulti(String str) {
		if (DConst.newDoc) {
			this.getCurrentDxDoc().getCurrentDModel().isMultiSite();
			if (str.equalsIgnoreCase(DConst.ALL_SITES))
				this.getCurrentDxDoc().getCurrentDModel().changeInDModel(
						this.getJFrame());
			else
				this.getCurrentDxDoc().getCurrentDModel().changeInDModel(
						this.getJFrame());

		} else {
			this.getCurrentDModel().setCurrentSite(str);
			if (str.equalsIgnoreCase(DConst.ALL_SITES))
				this.getCurrentDModel().changeInDModelByAllSites(
						this.getJFrame());
			else
				this.getCurrentDModel().changeInDModel(this.getJFrame());
		}

	}

	public void setFileToOpen(String absolutePath) {
		_fileToOpen = absolutePath;
	}

} /* end class DApplication */
