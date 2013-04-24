package musique.controllers.mainActions;

import musique.workers.SwingWorkerLoadUserCollectionXml;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.concurrent.ExecutionException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import musique.dao.DAOUserCollection;
import musique.models.collectionModels.IModelCollection;
import musique.models.collectionModels.ModelOpenUserCollections;
import musique.models.mainModels.ModelOpenWindows;
import musique.models.mainModels.ModelSelectedReleasesView;
import musique.models.collectionModels.ModelUserCollection;
import musique.views.mainWindowViews.ViewMainMenu;
import org.jdom.Document;
import org.jdom.JDOMException;

/**
 *
 * @author Sam Careelmont
 */

public class ActionOpenUserCollection extends AbstractActionOpenCollection implements PropertyChangeListener {
    private SwingWorker<Document, Void> worker;
    private ViewMainMenu viewMainMenu;

    public ActionOpenUserCollection(String name, Icon icon, ModelSelectedReleasesView modelSelectedReleasesView, ModelOpenWindows modelOpenWindows, ModelOpenUserCollections modelOpenUserCollections) {
        super(name, icon, modelSelectedReleasesView, modelOpenWindows, modelOpenUserCollections);
        viewMainMenu = null;
    }
    
    public void setViewMainMenu(ViewMainMenu viewMainMenu) {
        this.viewMainMenu = viewMainMenu;
    }

    public void actionPerformed(ActionEvent e) {
         // Toon een dialoogvenster waarmee je een naam opgeeft en een pad selecteert
        JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
             // Maak een worker aan die de XML van het label in de achtergrond kan laden
            worker = new SwingWorkerLoadUserCollectionXml(file);
            worker.addPropertyChangeListener(this);
            worker.execute();
        }
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName()) && worker.isDone()) {
            try {
                 /**
                 *  Maak een Data Acces Object aan die de info uit het JDOMdocument
                 *  kan weergeven onder de vorm van java-objecten
                 */
                DAOUserCollection daoUserCollection = new DAOUserCollection(worker.get());
                // Maak het model aan die bij de collectie zal behoren
                IModelCollection modelCollection = new ModelUserCollection(daoUserCollection);
                // Roep de supermethode op die het venster zal initialiseren
                modelCollection.addChangeListener(viewMainMenu);
                super.initCollectionFrame(modelCollection);
            } catch (InterruptedException ex) {
                // doe niets
            } catch (ExecutionException ex) {
                JOptionPane.showMessageDialog(null, "Inputfile doesn't match correct syntax", "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon (ActionOpenArtistCollection.class.getResource("/musique/images/error.png")));
            } catch (JDOMException ex) {
                // doe niets
            }
        }
    }
}
