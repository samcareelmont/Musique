package musique.views.mainWindowViews;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import musique.controllers.mainActions.ActionExit;
import musique.controllers.mainActions.ActionNewUserCollection;
import musique.controllers.mainActions.ActionOpenArtistCollection;
import musique.controllers.mainActions.ActionOpenLabelCollection;
import musique.controllers.mainActions.ActionOpenUserCollection;
import musique.controllers.mainActions.ActionSwitchReleasesView;
import musique.models.collectionModels.ModelOpenUserCollections;
import musique.models.mainModels.ModelOpenWindows;
import musique.models.mainModels.ModelSelectedReleasesView;

/**
 *
 * @author Sam Careelmont
 */

public class ViewMainFrame extends JFrame {
        public ViewMainFrame(ModelSelectedReleasesView modelSelectedReleasesView, ModelOpenWindows modelOpenWindows, ModelOpenUserCollections modelOpenUserCollections) {
            super("Musique");
            setIconImage(new ImageIcon (ViewMainFrame.class.getResource("/musique/images/icon.png")).getImage());
            setMinimumSize(new Dimension(200, 200));

            // Stel de actions in
            Map<String, AbstractAction> actions = new HashMap<String, AbstractAction>();
            ImageIcon openArtistCollectionImage = new ImageIcon (ViewMainFrame.class.getResource("/musique/images/OpenArtistCollection.png"));
            actions.put("openArtistCollection", new ActionOpenArtistCollection("Open Artist", openArtistCollectionImage, modelSelectedReleasesView, modelOpenWindows, modelOpenUserCollections));
            ImageIcon openLabelCollectionImage = new ImageIcon (ViewMainFrame.class.getResource("/musique/images/OpenLabelCollection.png"));
            actions.put("openLabelCollection", new ActionOpenLabelCollection("Open Label", openLabelCollectionImage, modelSelectedReleasesView, modelOpenWindows, modelOpenUserCollections));

            ImageIcon newUserCollectionImage = new ImageIcon (ViewMainFrame.class.getResource("/musique/images/NewUserCollection.png"));
            actions.put("newUserCollection", new ActionNewUserCollection("New collection", newUserCollectionImage, modelSelectedReleasesView, modelOpenWindows, modelOpenUserCollections));
            ImageIcon openUserCollectionImage = new ImageIcon (ViewMainFrame.class.getResource("/musique/images/OpenUserCollection.png"));
            actions.put("openUserCollection", new ActionOpenUserCollection("Open collection", openUserCollectionImage, modelSelectedReleasesView, modelOpenWindows, modelOpenUserCollections));

            actions.put("exit", new ActionExit("Exit"));
            actions.put("SwitchToTabView", new ActionSwitchReleasesView("Tab View", "tab", modelSelectedReleasesView));
            actions.put("SwitchToTreeView", new ActionSwitchReleasesView("Tree View", "tree", modelSelectedReleasesView));
            actions.put("SwitchToCoverflowView", new ActionSwitchReleasesView("Coverflow View", "coverflow", modelSelectedReleasesView));

            // Menubar instellen, Paneel toevoegen, Packen, Sluitoperatie instelen, Zichtbaar maken en Centreren van venster
            setJMenuBar (new ViewMainMenu(actions, modelOpenWindows));
            add(new ViewMainPanel(actions));
            pack();
            setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);                        
            setVisible (true);
            setLocationRelativeTo(null);            
        }

}
