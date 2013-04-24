package musique;

import be.ugent.caagt.discogs.DiscogsManager;
import musique.models.collectionModels.ModelOpenUserCollections;
import musique.models.mainModels.ModelOpenWindows;
import musique.models.mainModels.ModelSelectedReleasesView;
import musique.views.mainWindowViews.ViewMainFrame;

/**
 *
 * @author Sam Careelmont
 */

public class Musique {
    public static void main(String[] args) {
        // Stel de apiKey in
        DiscogsManager.setAPIKey("8bb564b535");
        // Maak het hoofdvenster aan (Thread-safe)
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGUI();
            }
        });
    }

    public static void createGUI() {
        // Initialiseer modellen die over de hele applicatie hetzelfde zijn
        ModelSelectedReleasesView modelSelectedReleasesView = new ModelSelectedReleasesView("tab");
        ModelOpenWindows modelOpenWindows = new ModelOpenWindows();
        ModelOpenUserCollections modelOpenUserCollections = new ModelOpenUserCollections();
        // Maak het hoofdvenster aan
        ViewMainFrame mf = new ViewMainFrame(modelSelectedReleasesView, modelOpenWindows, modelOpenUserCollections);
    }
}