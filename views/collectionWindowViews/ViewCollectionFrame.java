package musique.views.collectionWindowViews;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import musique.models.collectionModels.IModelCollection;
import musique.models.collectionModels.ModelOpenUserCollections;
import musique.models.collectionModels.ModelSelectedRelease;
import musique.models.mainModels.ModelSelectedReleasesView;

/**
 *
 * @author Sam Careelmont
 */

public class ViewCollectionFrame extends JFrame implements ChangeListener {
    private IModelCollection modelCollection;
    private ModelSelectedReleasesView modelSelectedReleasesView;
    
    public ViewCollectionFrame(IModelCollection modelCollection, ModelSelectedReleasesView modelSelectedReleasesView, ModelSelectedRelease modelSelectedRelease, ModelOpenUserCollections modelOpenUserCollections) {
            super(modelCollection.getCollectionType() + " - " + modelCollection.getName());
            this.modelCollection = modelCollection;
            this.modelSelectedReleasesView = modelSelectedReleasesView;
        
            // Luisteraars instellen
            if(modelCollection.getCollectionType().equals("UserCollection")) {
                modelCollection.addChangeListener(this);
            }
            modelSelectedReleasesView.addChangeListener(this);

            // Stel layout in
            setIconImage(new ImageIcon (ViewCollectionFrame.class.getResource("/musique/images/icon.png")).getImage());
            setPreferredSize(new Dimension(1000, 750));
            setMinimumSize(new Dimension(600, 400));
            setLayout(new GridLayout(1,3));

            // Voeg de drie panel toe
            add(new ViewCollectionInfo(modelCollection));
            add(new ViewCollectionReleasesContainer(modelCollection, modelSelectedReleasesView, modelSelectedRelease));
            add(new ViewCollectionItem(modelSelectedRelease, modelCollection));

            // Menubar instellen,Packen, Sluitoperatie instelen, Zichtbaar maken en Centreren van venster
            setJMenuBar (new ViewCollectionMenu(modelCollection, modelOpenUserCollections, modelSelectedRelease));
            pack();
            setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
            setVisible (true);
            setLocationRelativeTo(null);
    }

    public void stateChanged (ChangeEvent e) {
        // Stel de titel opnieuw in
        setTitle(modelCollection.getCollectionType() + " - " + modelCollection.getName());
    }

}
