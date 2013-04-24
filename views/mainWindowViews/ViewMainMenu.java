package musique.views.mainWindowViews;

import musique.views.collectionWindowViews.ViewCollectionFrame;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import musique.controllers.mainActions.ActionActivateWindow;
import musique.controllers.mainActions.ActionNewUserCollection;
import musique.controllers.mainActions.ActionOpenUserCollection;
import musique.models.mainModels.ModelOpenWindows;

/**
 *
 * @author Sam Careelmont
 */

public class ViewMainMenu extends JMenuBar implements ChangeListener {
    private JMenu menuWindows;
    private ModelOpenWindows modelOpenWindows;

    // Menu aanmaken
    public ViewMainMenu(Map<String, AbstractAction> actions, ModelOpenWindows modelOpenWindows) {
        this.modelOpenWindows = modelOpenWindows;
        modelOpenWindows.addChangeListener(this);

        //File menu
        JMenu menuFile = new JMenu("File");
        menuFile.setMnemonic(KeyEvent.VK_F);
            menuFile.add(actions.get("openArtistCollection")).setIcon(null);
            menuFile.add(actions.get("openLabelCollection")).setIcon(null);
            menuFile.addSeparator();

            ActionOpenUserCollection actionOpenUserCollection = (ActionOpenUserCollection)actions.get("openUserCollection");
            actionOpenUserCollection.setViewMainMenu(this);
            menuFile.add(actionOpenUserCollection).setIcon(null);

            ActionNewUserCollection actionNewUserCollection = (ActionNewUserCollection)actions.get("newUserCollection");
            actionNewUserCollection.setViewMainMenu(this);
            menuFile.add(actionNewUserCollection).setIcon(null); 
            
            menuFile.addSeparator();
            menuFile.add(actions.get("exit"));

        add(menuFile);

        //Settings menu
        JMenu menuSettings = new JMenu("Settings");
        menuSettings.setMnemonic(KeyEvent.VK_S);
            JRadioButtonMenuItem tabView = new JRadioButtonMenuItem(actions.get("SwitchToTabView"));
            tabView.setSelected(true);
            menuSettings.add(tabView);

            JRadioButtonMenuItem treeView = new JRadioButtonMenuItem(actions.get("SwitchToTreeView"));
            menuSettings.add(treeView);

            JRadioButtonMenuItem coverflowView = new JRadioButtonMenuItem(actions.get("SwitchToCoverflowView"));
            menuSettings.add(coverflowView);
            
            ButtonGroup group = new ButtonGroup();
            group.add(tabView);
            group.add(treeView);
            group.add(coverflowView);

        add(menuSettings);

        //Settings
        menuWindows = new JMenu("Windows");
        menuWindows.setMnemonic(KeyEvent.VK_W);
        menuWindows.setEnabled(false);
        add(menuWindows);
    }

    public void stateChanged (ChangeEvent e) {
        menuWindows.removeAll();
        ArrayList<ViewCollectionFrame> openWindows = modelOpenWindows.getOpenWindows();
        // Er staan venster open, toon deze in de menu
        if(openWindows.size() > 0) {
            menuWindows.setEnabled(true);
            for(ViewCollectionFrame cf : openWindows) {
                JMenuItem menuItemWindow = new JMenuItem(new ActionActivateWindow(cf.getTitle(), cf));
                menuWindows.add(menuItemWindow);
            }
        // Er staan geen venster open, disable deze menu
        } else {
            menuWindows.setEnabled(false);
        }
    }
}
