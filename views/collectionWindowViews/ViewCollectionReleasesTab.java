package musique.views.collectionWindowViews;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import musique.controllers.collectionListeners.ListenerList;
import musique.models.collectionModels.IModelCollection;
import musique.models.collectionModels.ModelRelease;
import musique.models.collectionModels.ModelSelectedRelease;
import musique.models.mainModels.ModelSelectedReleasesView;

/**
 *
 * @author Sam Careelmont
 */

public class ViewCollectionReleasesTab extends AbstractViewCollectionReleases implements ChangeListener, ListSelectionListener {
    private JTabbedPane tabbedPane;
    private SortedMap<Integer,JList> mapJLists;
    private Map<ModelRelease,Integer> mapReleasesIndexes;
    private int selectedIndex = -1;
    private int currentYear = -1;

    public ViewCollectionReleasesTab(IModelCollection modelCollection, ModelSelectedRelease modelSelectedRelease, ModelSelectedReleasesView modelSelectedReleasesView) {
        super(modelCollection, modelSelectedRelease, modelSelectedReleasesView);
        
        mapJLists = new TreeMap<Integer,JList>(new ComparatorYear());
        mapReleasesIndexes = new HashMap<ModelRelease,Integer>();
        
        setLayout(new BorderLayout());
        tabbedPane = new JTabbedPane();
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        add(tabbedPane, BorderLayout.CENTER);

        refreshLists(false);
    }

    public void refreshLists(boolean changedView) {
        // Sla de geselecteerde release op 
        ModelRelease selectedRelease = modelSelectedRelease.getSelectedRelease();

        // Vraag de titel van de geselecteerde tab op
        String selectedTabTitle = "";
        if(tabbedPane.getSelectedIndex() != -1) {
            selectedTabTitle = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
        }

        // Wis alle jLists en de tabs
        mapJLists.clear();
        tabbedPane.removeAll();

        // Overloop alle releases en voeg deze toe zodat mapJList gevuld wordt
        for(int i = 0; i < modelCollection.getNumberOfReleases(); i++) {
           addRelease(modelCollection.getRelease(i));
        }

        // Overloop nu de map en maak voor elke JList een tab aan
        int i = 0;
        for(Entry<Integer,JList> entry : mapJLists.entrySet()) {
            // Het jaatal is ongekend
            if(entry.getKey() == -1) {
                tabbedPane.addTab("Unknown", new JScrollPane(entry.getValue()));
            } else {
                tabbedPane.addTab(Integer.toString(entry.getKey()), new JScrollPane(entry.getValue()));
            }

            // Er werd van niet van view gewisseld, selecteer de tab die geselecteerd was
            if(!changedView) {
                if(selectedTabTitle.equals(tabbedPane.getTitleAt(i))) {
                    tabbedPane.setSelectedIndex(i);
                }
            // Er werd wel van view gewisseld, selecteer de tab waar de geslecteerde release in voorkomt
            } else if (selectedRelease != null && selectedRelease.getYear() == entry.getKey()) {
                tabbedPane.setSelectedIndex(i);
            }

            // Er is een release geselecteerd en we zitten bij lijst waarin deze geselecteerde release voorkomt
            if(selectedRelease != null && selectedRelease.getYear() == entry.getKey()) {
                JList jList = ((JList)entry.getValue());
                // Stel de geselecteerde index in op de correcte index
                jList.setSelectedIndex(mapReleasesIndexes.get(selectedRelease));
            } 
            i++;
        }
    }

    /*
     * Methode die een release zal toevoegen aan de juiste lijst
     * Dit gebeurt in een map zodat de lijsten later vlot overlopen kunnen worden
     */
    public void addRelease(ModelRelease release) {
        int year = release.getYear();
        // Er bestaat reeds een lijst voor dit jaartal
        if (mapJLists.containsKey(year)) {
            DefaultListModel modelList = (DefaultListModel)mapJLists.get(year).getModel();
            modelList.addElement(release);
            mapReleasesIndexes.put(release, modelList.getSize() - 1);
        // Er bestaat nog geen lijst voor dit jaartal
        } else {
            DefaultListModel modelList = new DefaultListModel();
            modelList.addElement(release);
            JList jList = new JList(modelList);
            jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            jList.addListSelectionListener(new ListenerList(modelSelectedRelease));
            jList.addListSelectionListener(this);
            mapJLists.put(year, jList);
            mapReleasesIndexes.put(release, 0);
        }
    }

    public void stateChanged (ChangeEvent e) {
        // Reageer alleen op wijziging als deze view geselecteerd is
        if(modelSelectedReleasesView.getSelectedView().equals("tab")) {
            // Controleer of er net van View werd gewisseld
            if(e.getSource() instanceof ModelSelectedReleasesView) {
                refreshLists(true);
            } else {
                refreshLists(false);
            }
        }
    }


    /*
     * Als er een element in de lijst wordt geselecteerd
     * dan worden alle lijsten gewist behalve die lijst waar net een element geselecteerd werd
     * Dit zorgt ervoor dat er slecht één element kan geslecteerd zijn over alle lijsten
     */
    public void valueChanged(ListSelectionEvent e) {
        ModelRelease selectedRelease = modelSelectedRelease.getSelectedRelease();
        if(selectedRelease != null) {
            for(Entry<Integer,JList> entry : mapJLists.entrySet()) {
                if(selectedRelease.getYear() == entry.getKey() && ((JList)entry.getValue()) != ((JList)e.getSource())) {
                    JList jList = ((JList)entry.getValue());
                    if(jList != ((JList)e.getSource())) {
                        jList.clearSelection();
                    }
                }
            }
        }
    }
}
