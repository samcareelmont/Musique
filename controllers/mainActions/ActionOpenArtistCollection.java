package musique.controllers.mainActions;

import musique.workers.SwingWorkerLoadArtistCollectionXml;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutionException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import musique.dao.DAOArtistCollection;
import musique.models.collectionModels.IModelCollection;
import musique.models.collectionModels.ModelArtistCollection;
import musique.models.collectionModels.ModelOpenUserCollections;
import musique.models.mainModels.ModelOpenWindows;
import musique.models.mainModels.ModelSelectedReleasesView;
import org.jdom.Document;
import org.jdom.JDOMException;

/**
 *
 * @author Sam Careelmont
 */

public class ActionOpenArtistCollection extends AbstractActionOpenCollection implements PropertyChangeListener {
    private SwingWorker<Document, Void> worker;

    public ActionOpenArtistCollection(String name, Icon icon, ModelSelectedReleasesView modelSelectedReleasesView, ModelOpenWindows modelOpenWindows, ModelOpenUserCollections modelOpenUserCollections) {
        super(name, icon, modelSelectedReleasesView, modelOpenWindows, modelOpenUserCollections);
    }

    public void actionPerformed(ActionEvent e) {
        // Toont een dialoogvenster waar je de naam van een artiest kan ingeven
        Object name = JOptionPane.showInputDialog(null, "Type the name of the artist", "Open artist", JOptionPane.DEFAULT_OPTION, new ImageIcon (ActionOpenArtistCollection.class.getResource("/musique/images/OpenArtistCollectionSmall.png")), null, null);
        if (name != null && !name.equals("")) {
            // Maak een worker aan die de XML van de artiest in de achtergrond kan laden
            worker = new SwingWorkerLoadArtistCollectionXml((String)name);
            worker.addPropertyChangeListener(this);
            worker.execute(); 
        }        
    }

    public void propertyChange(PropertyChangeEvent evt) {
         // De xml is klaar met laden
        if ("state".equals(evt.getPropertyName()) && worker.isDone()) {
            try {
                /**
                 *  Maak een Data Acces Object aan die de info uit het JDOMdocument
                 *  kan weergeven onder de vorm van java-objecten
                 */
                DAOArtistCollection daoArtistCollection = new DAOArtistCollection(worker.get());
                // Maak het model aan die bij de collectie zal behoren
                IModelCollection modelCollection = new ModelArtistCollection(daoArtistCollection);
                // Roep de supermethode op die het venster zal initialiseren
                super.initCollectionFrame(modelCollection);
            } catch (InterruptedException ex) {
                // doe niets
            } catch (ExecutionException ex) {
                 JOptionPane.showMessageDialog(null, "Artist doesn't exist", "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon (ActionOpenArtistCollection.class.getResource("/musique/images/error.png")));
            } catch (JDOMException ex) {
                // doe niets
            }
        }
    }
}
