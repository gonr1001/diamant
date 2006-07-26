/*
 * Created on May 19, 2005
 *
 */
package dInterface.selectiveSchedule;

import dDeveloper.DxFlags;
import dInterface.DApplication;

import dInterface.selectiveSchedule.dialog.SelectiveScheduleDlg;
import dInterface.selectiveSchedule.dialog.SelectiveScheduleFileIOException;

import dInterface.selectiveSchedule.filters.FilterSet;
import dInterface.selectiveSchedule.filters.FilterSetIdentifier;
import dInterface.selectiveSchedule.filters.FilterSetNameException;

import dInterface.selectiveSchedule.persistance.PersistanceMismatch;
import dInterface.selectiveSchedule.persistance.PersistanceMismatchException;
import dInterface.selectiveSchedule.persistance.PersistanceMismatchManager;
import dInterface.selectiveSchedule.relationTesters.RelationTester;
import dInterface.selectiveSchedule.relationTesters.RelationTester_ActivityEvent;
import dInterface.selectiveSchedule.relationTesters.Tuple;
import dInterface.selectiveSchedule.relationTesters.UnexpectedArguments;

import dInternal.DResource;

import dInternal.dData.dActivities.Activity;

import dInternal.dOptimization.EventAttach;

import org.apache.log4j.Logger;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;

import org.jdom.input.SAXBuilder;

import org.jdom.output.XMLOutputter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;


/**
 * "Façade" pour la fonctionnalité de Grille Sélective.  Le
 * SelectiveScheduleManager offre des services pour valider la
 * relation entre un élément de l'extérieur et les éléments des
 * ensembles qu'il contient.  Il offre également les services
 * nécessaires pour la gestion des ensembles.  Finalement, les
 * services de persistances des ensembles sont également offerts par
 * cette classe.
 * 
 * @author Pascal
 *  
 */
public class SelectiveScheduleManager {
    /**
     * Implémentation de l'interface PersistanceMismatch
     * 
     * @author Pascal
     * 
     */
    public class PersistanceMismatchImpl implements PersistanceMismatch {
        private int _expectedNbOfElements = -1;

        /**
         * @associates String 
         */
        private ArrayList <String> _unresolvedElementNames;
        private FilterSet _mismatchedFilterSet;
        
        /**
         *  
         */
        public PersistanceMismatchImpl(FilterSet mismatchedFilterSet) {
            _mismatchedFilterSet = mismatchedFilterSet;
            _unresolvedElementNames = new ArrayList <String>();
        }

        /*
         * (non-Javadoc)
         * 
         * @see dInterface.selectiveSchedule.PersistanceMismatch#expectedNbOfElements(int)
         */
        public void setExpectedNbOfElements(int nb) {
            _expectedNbOfElements = nb;
        }

        /*
         * (non-Javadoc)
         * 
         * @see dInterface.selectiveSchedule.PersistanceMismatch#addUnresolvedElement(java.lang.String)
         */
        public void addUnresolvedElement(String elementName) {
            _unresolvedElementNames.add(elementName);
        }

        /*
         * (non-Javadoc)
         * 
         * @see dInterface.selectiveSchedule.PersistanceMismatch#getMismatchedFilterSet()
         */
        public FilterSet getMismatchedFilterSet() {
            return _mismatchedFilterSet;
        }

        /*
         * (non-Javadoc)
         * 
         * @see dInterface.selectiveSchedule.PersistanceMismatch#getMissingElementNames()
         */
        public Collection getMissingElementNames() {
            return _unresolvedElementNames;
        }

        /*
         * (non-Javadoc)
         * 
         * @see dInterface.selectiveSchedule.PersistanceMismatch#getExpectedNbOfElements()
         */
        public int getExpectedNbOfElements() {
            return _expectedNbOfElements;
        }

    }
    /**
     * @author Pascal
     *  
     */
    public class PersistanceMismatchManagerImpl implements PersistanceMismatchManager {
        /**
         * @associates PersistanceMismatch 
         */
        private List _persistanceMismatches = null;

        /**
         *  
         */
        public PersistanceMismatchManagerImpl() {
            _persistanceMismatches = new ArrayList();
        }

        /*
         * (non-Javadoc)
         * 
         * @see dInterface.selectiveSchedule.PersistanceMismatchManager#isPersistanceMismatch()
         */
        public boolean isPersistanceMismatch() {
            return (_persistanceMismatches.size() != 0);
        }

        /*
         * (non-Javadoc)
         * 
         * @see dInterface.selectiveSchedule.PersistanceMismatchManager#addPersistanceMismatch(dInterface.selectiveSchedule.PersistanceMismatch)
         */
        public void addPersistanceMismatch(PersistanceMismatch mm) {
            _persistanceMismatches.add(mm);
        }

        /*
         * (non-Javadoc)
         * 
         * @see dInterface.selectiveSchedule.PersistanceMismatchManager#getPersistanceMismatches()
         */
        public Collection getPersistanceMismatches() {
            return _persistanceMismatches;
        }
        
    }

    private static Logger logger = Logger.getLogger(SelectiveScheduleManager.class);

    /**
     * "Singleton"
     *  
     */
    private static SelectiveScheduleManager _instance = null;

    /**
     * Une Map qui associe un Tuple à un RelationTester approprié
     * @associates RelationTesterTemplate
     *  
     */
    private Map _tupleToRelationTester = null;

    /**
     * Flag qui identifie si le SelectiveScheduleManger est en fonction ou non
     *  
     */
    private boolean _enabled = false;

    /**
     * Une Map qui associe un FilterSetIdentifier à FilterSet
     * @associates FilterSet
     *  
     */
    private Map _filterSetIdentifierToFilterSets = null;

    /**
     * Le nom du dernier fichier XML de persistance lu
     *  
     */
    private String _lastXMLPersistanceFileName = null;
    
    private PersistanceMismatchManagerImpl _persistanceMismatchManager = null;

    /**
     * Constructeur privé pour supporter DP "Singleton"
     */
    private SelectiveScheduleManager() {
        initialize();
    }

    /**
     * "Singleton"
     * 
     * @return l'unique instance de cette classe
     */
    public static SelectiveScheduleManager getInstance() {
        if (_instance == null) {
            _instance = new SelectiveScheduleManager();
        }

        return _instance;
    }

    /**
     * Permet d'initialiser le SelectiveScheduleManager à partir de n'importe
     * quel constructeur
     *  
     */
    private void initialize() {
        _enabled = false;
        _filterSetIdentifierToFilterSets = new HashMap();
        _persistanceMismatchManager = new PersistanceMismatchManagerImpl();

        initializeRelationTesters();
    }

    /**
     * Rempli la map _tupleToRelationTester
     *  
     */
    private void initializeRelationTesters() {
        _tupleToRelationTester = new HashMap();

        _tupleToRelationTester.put(new Tuple(
                new Class[] { EventAttach.class, Activity.class }),
            RelationTester_ActivityEvent.getInstance());
    }

    /*
     * Services liés au propre de la fonctionnalité de Grille Sélective
     */
    public boolean isEnabled() {
        return _enabled;
    }

    public void setEnabled(boolean enabled) {
        _enabled = enabled;
    }

    /**
     * Service à appeler afin de vérifier si un élément (celui passé en
     * paramètre) est en relation avec un second élément présent parmis les
     * ensembles contenus par l'instance de cette classe
     * 
     * @param element
     *            l'élément à valider
     * @return
     */
    public boolean validateElement(DResource element) {
        boolean matchFound = false;

        /* Iterateur sur tous les ensembles de filtres */
        Iterator itrAllFilterSets = _filterSetIdentifierToFilterSets.values()
                                                                    .iterator();

        /* pour chaque ensemble de filtres... */
        while (itrAllFilterSets.hasNext() && !matchFound) {
            FilterSet fs = (FilterSet) itrAllFilterSets.next();

            /*
             * Si le filtre n'est pas actif, on ne vérifie pas si ses éléments
             * sont en relation
             */
            if (fs.getFilterSetIdentifier().isActive() == false) {
                continue;
            }

            Iterator itrFilterSet = fs.getIterator();

            /*
             * pour chaque filtre, on effectue un test afin de savoir si un
             * "match" existe
             */
            DResource el;
            while (itrFilterSet.hasNext()) {
                el = (DResource) itrFilterSet.next();

                if (el == null) {
                    continue;
                }

                /*
                 * Verification de la relation entre l'element passe en
                 * parametre et le filtre que l'on vient de recuperer
                 */
                RelationTester rt = (RelationTester) _tupleToRelationTester.get(new Tuple(
                            new Class[] {
                                el.getAttach().getClass(),
                                element.getAttach().getClass()
                            }));

                if (rt == null) {
                    logger.warn(
                        "aucun RelationTester trouvé pour valider l'élément " +
                        element);

                    return false;
                }

                try {
                    matchFound = rt.isRelated(el, element);
                } catch (UnexpectedArguments e) {
                    System.err.println(e);
                }

                if (matchFound) {
                    break;
                }
            }
        }

        return matchFound;
    }

    /*
     * Services liés aux ensembles de filtres
     */
    public FilterSet createFilterSet() {
        return new FilterSet(new FilterSetIdentifier());
    }

    public FilterSet addFilterSet(FilterSet aFilterSetToAdd) {
        return (FilterSet) _filterSetIdentifierToFilterSets.put(aFilterSetToAdd.getFilterSetIdentifier(),
                aFilterSetToAdd);
    }

    public FilterSet removeFilterSet(FilterSet aFilterSetToRemove) {
        return (FilterSet) _filterSetIdentifierToFilterSets.remove(aFilterSetToRemove.getFilterSetIdentifier());
    }

    public FilterSet[] getAllFilterSet() {
        return (FilterSet[]) _filterSetIdentifierToFilterSets.values().toArray(new FilterSet[0]);
    }

    public int getNbFilterSet() {
        return _filterSetIdentifierToFilterSets.size();
    }

    public boolean isFilterSetNameAvailable(String name) {
        boolean result = true;

        Iterator itr = _filterSetIdentifierToFilterSets.values().iterator();

        FilterSet fs;
        while (itr.hasNext()) {
            fs = (FilterSet) itr.next();

            if (fs == null) {
                continue;
            }

            if (fs.getFilterSetIdentifier().getFilterSetName().equals(name)) {
                result = false;

                break;
            }
        }

        return result;
    }

    /**
     * Service permettant de sauvegarder les données propres à la fonctionnalité
     * de la grille sélective dans un réceptacle approprié (en ce moment, ce
     * réceptacle est un fichier XML)
     *  
     */
    public void writeToPersistenceSink()
        throws SelectiveScheduleFileIOException {
        File fileOut = new File(fetchXMLPersistanceFileName());

        try {
            fileOut.createNewFile();
        } catch (IOException e) {
            logger.error("impossible de créer ou d'ouvrir le fichier " +
                fileOut.getName());
            throw new SelectiveScheduleFileIOException(
                "Impossible de créer ou d'ouvrir le fichier " +
                fileOut.getName());
        }

        if (fileOut.canWrite() == false) {
            logger.error("impossible d'écrire dans le fichier " +
                fileOut.getName());
            throw new SelectiveScheduleFileIOException(
                "Impossible d'écrire dans le fichier " + fileOut.getName());
        }

        FileWriter fileOutWriter = null;

        try {
            fileOutWriter = new FileWriter(fileOut);
        } catch (IOException e1) {
            // Je ne gère pas ce cas, car il m'apparaît extrêmement peu probable
            // de survenir
            e1.printStackTrace();
        }

        XMLOutputter xmlOut = new XMLOutputter();

        Document doc = new Document();

        Element rootElement = new Element("SelectiveSchedule");
        doc.setRootElement(rootElement);

        Element setsElement = new Element("Sets");
        rootElement.addContent(setsElement);

        Iterator itrFilterSet = _filterSetIdentifierToFilterSets.values()
                                                                .iterator();
        FilterSet fs;
        while (itrFilterSet.hasNext()) {
            fs = (FilterSet) itrFilterSet.next();

            Element setElement = new Element("Set");

            setElement.setAttribute("name",
                fs.getFilterSetIdentifier().getFilterSetName());

            Element setElementsElement = new Element("Elements");

            setElement.addContent(setElementsElement);

            Iterator itrFilterSetElements = fs.getIterator();
            
            Element setElementElement;

            while (itrFilterSetElements.hasNext()) {
                setElementElement = new Element("Element");
                setElementsElement.addContent(setElementElement);
                setElementElement.setAttribute("type", "Activity");
                setElementElement.setAttribute("id",
                    ((DResource) itrFilterSetElements.next()).getID());
            }

            setsElement.addContent(setElement);
        }

        try {
            xmlOut.output(doc, fileOutWriter);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    /**
     * Service permettant de récupérer les données propres à la fonctionnalité
     * de la grille sélective d'un réceptacle approprié (en ce moment, ce
     * réceptacle est un fichier XML)
     *  
     */
    public void readFromPersistenceSource()
        throws SelectiveScheduleFileIOException, PersistanceMismatchException {
        //File fileIn = new File("./fgs.xml");
        File fileIn = null;

        /*
         * Si aucun fichier de persistance n'a encore été lu OU si le fichier de
         * persistance courrant n'est PAS le même qui avait été utilisé lors du
         * dernier appel de cette fonction alors...
         */
        if ((_lastXMLPersistanceFileName == null) ||
                (_lastXMLPersistanceFileName.equals(
                    fetchXMLPersistanceFileName()) == false)) {
            _lastXMLPersistanceFileName = fetchXMLPersistanceFileName();

            _filterSetIdentifierToFilterSets.clear();

            if (_lastXMLPersistanceFileName == null) {
                logger.error(
                    "le chargement des ensembles n'a pas fonctionné, car le nom du fichier d'importation automatique de données n'a pas été inscrit");
                JOptionPane.showMessageDialog(SelectiveScheduleDlg.getInstance(),
                    "Le chargement des ensembles n'a pas fonctionné, car le nom du fichier d'importation automatique de données n'a pas été inscrit",
                    "Chargement des ensembles", JOptionPane.ERROR_MESSAGE);

                return;
            }
        } else { // Sinon, on retourne (rien a à être fait)

            return;
        }

        fileIn = new File(_lastXMLPersistanceFileName);

        if (fileIn.exists() == false) {
            logger.info("aucun fichier XML de disponible pour la persistance");

            return;
        }

        if (fileIn.canRead() == false) {
            logger.error("impossible de lire le fichier XML " +
                fileIn.getName());
            throw new SelectiveScheduleFileIOException(SelectiveScheduleManager.class +
                " : impossible de lire le fichier XML " + fileIn.getName());
        }

        FileReader fileInReader = null;

        try {
            fileInReader = new FileReader(fileIn);
        } catch (FileNotFoundException e) {
            logger.error("ne trouve pas le fichier " + fileIn.getName());
            throw new SelectiveScheduleFileIOException(SelectiveScheduleManager.class +
                " : ne trouve pas le fichier XML " + fileIn.getName());
        }

        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = null;

        try {
            doc = saxBuilder.build(fileInReader);
        } catch (JDOMException e1) {
            logger.error(e1);
            JOptionPane.showMessageDialog(DApplication.getInstance().getJFrame(),
                "Erreur DOM lors de la lecture du fichier " + fileIn.getName(),
                "Erreur de lecture de fichier", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e1) {
            logger.error(e1);
            JOptionPane.showMessageDialog(DApplication.getInstance().getJFrame(),
                "Erreur I/O lors de la lecture du fichier " + fileIn.getName(),
                "Erreur de lecture de fichier", JOptionPane.ERROR_MESSAGE);
        }

        Element selectiveScheduleElement = doc.getRootElement();
        Element setsElement = selectiveScheduleElement.getChild("Sets");

        Iterator itrSetsElement = setsElement.getChildren("Set").iterator();

        FilterSet fs;
        Element setElement;
        
        while (itrSetsElement.hasNext()) {
            fs = createFilterSet();

            setElement = (Element) itrSetsElement.next();

            try {
                fs.getFilterSetIdentifier().setFilterSetName(setElement.getAttributeValue(
                        "name"));
            } catch (FilterSetNameException e2) {
                logger.error("le nom " + setElement.getAttributeValue("name") +
                    " est déjà utilisé");
                JOptionPane.showMessageDialog(DApplication.getInstance()
                                                          .getJFrame(),
                    "Erreur lors de la lecture du fichier XML " +
                    fileIn.getName(), "Erreur de lecture de fichier",
                    JOptionPane.ERROR_MESSAGE);
            }

            PersistanceMismatch pmm = new PersistanceMismatchImpl(fs);
            
            pmm.setExpectedNbOfElements(setElement.getChild("Elements")
            .getChildren("Element").size());

            Iterator itrSetElement = setElement.getChild("Elements")
            .getChildren("Element").iterator();
            
            Element setElementElement;
            String activityID;
            DResource activity;

            while (itrSetElement.hasNext()) {
                setElementElement = (Element) itrSetElement.next();
                activityID = setElementElement.getAttributeValue("id");

                // Si l'activité spécificée dans le fichier XML n'existe plus,
                // alors il ne faut pas l'ajouter à l'ensemble
                // XXXX GS: Générer un rapport sur les manquements existants
                // entre la description ds la persistance et ce qui se trouve
                // réellement dans le système. Si l'ensemble devient vide, alors
                // il faut carrément le retirer (et en informer l'utilisateur)
                activity = DApplication.getInstance().getCurrentDModel()
                                                 .getSetOfActivities()
                                                 .getResource(activityID);

                if (activity != null) {
                    fs.addFilter(activity);
                } else {
                    /*
                     * Une Activité est décrite dans le fichier de persistance,
                     * mais elle ne se trouve pas dans
                     * DApplication.getInstance().getDModel().getSetOfActivities()
                     */
                    pmm.addUnresolvedElement(activityID);
                }
            }

            if (pmm.getMissingElementNames().size() != 0) {
                _persistanceMismatchManager.addPersistanceMismatch(pmm);
            }
            
            addFilterSet(fs);
        }
        
        if (_persistanceMismatchManager.isPersistanceMismatch()) {
            throw new PersistanceMismatchException();
        }
    }

    public PersistanceMismatchManagerImpl getPersistanceMismatchManager() {
        return _persistanceMismatchManager;
    }
    
    private String fetchXMLPersistanceFileName() {
    	if(DxFlags.newDoc) {
    		return DApplication.getInstance().getCurrentDxDoc().getAutoImportDIMFilePath() + "fgs.xml";
    	}
        return DApplication.getInstance().getCurrentDoc()
                           .getAutoImportDIMFilePath() + "fgs.xml";
    }
}
