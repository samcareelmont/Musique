package musique.controllers.mainActions;

import java.awt.event.ActionEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import musique.models.collectionModels.ModelOpenUserCollections;
import musique.models.mainModels.ModelOpenWindows;
import musique.models.mainModels.ModelSelectedReleasesView;
import musique.models.collectionModels.ModelUserCollection;
import musique.views.mainWindowViews.ViewMainMenu;

/**
 *
 * @author Sam Careelmont
 */

public class ActionNewUserCollection extends AbstractActionOpenCollection {
    private ViewMainMenu viewMainMenu;

    public ActionNewUserCollection(String name, Icon icon, ModelSelectedReleasesView modelSelectedReleasesView, ModelOpenWindows modelOpenWindows, ModelOpenUserCollections modelOpenUserCollections) {
        super(name, icon, modelSelectedReleasesView, modelOpenWindows, modelOpenUserCollections);
        viewMainMenu = null;
    }
    
    public void setViewMainMenu(ViewMainMenu viewMainMenu) {
        this.viewMainMenu = viewMainMenu;
    }

    public void actionPerformed(ActionEvent e) {
       // Toont een dialoogvenster waar je een naam kunt ingeven
       Object name = JOptionPane.showInputDialog(null, "Give your collection a name", "New Collection", JOptionPane.DEFAULT_OPTION, new ImageIcon (ActionOpenArtistCollection.class.getResource("/musique/images/NewUserCollectionSmall.png")), null, null);
       if (name != null && !name.equals("")) {
           // Maak het model aan die bij de collectie zal behoren
           ModelUserCollection modelCollection = new ModelUserCollection((String)name);
           /**
            * De mainenu luister naar dit model
            * Belangrijk om de aanduiding van een UserCollecion aan te passen in de menu
            * wanneer deze van naam veranderd
            */
           modelCollection.addChangeListener(viewMainMenu);
           // Roep de supermethode op die het venster zal initialiseren
           super.initCollectionFrame(modelCollection);
       }
    }
}
