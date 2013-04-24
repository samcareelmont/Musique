package musique.views.collectionWindowViews;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableColumnModel;
import musique.models.collectionModels.IModelCollection;
import musique.models.collectionModels.ModelRelease;
import musique.models.collectionModels.ModelSelectedRelease;
import musique.models.collectionModels.TableModelTracks;
import musique.models.collectionModels.Track;

/**
 *
 * @author Sam Careelmont
 */

public class ViewCollectionItem extends JPanel implements ChangeListener {
    private ModelSelectedRelease modelSelectedRelease;
    private TableModelTracks tableModelTracks;
    private ViewAutoFitImage imagePanel;
    private BufferedImage noReleaseImage, buzyReleaseImage;    
    private static final int[] COLUMN_WIDTHS = {40, 180, 40};
    
    public ViewCollectionItem(ModelSelectedRelease modelSelectedRelease, IModelCollection modelCollection) {
        this.modelSelectedRelease = modelSelectedRelease;
        /*
         * Er wordt geluisterd naar elke release
         * omdat de view moet hernieuwd worden als er meer info over een release beschikbaar is
         */
        for(int i = 0; i < modelCollection.getNumberOfReleases(); i++) {
           modelCollection.getRelease(i).addChangeListener(this);
        }

        modelSelectedRelease.addChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // Marge tussen de boxen
        add(Box.createRigidArea(new Dimension(0, 10)));

        // Afbeeldingen inladen
        try {
            noReleaseImage = ImageIO.read(ViewCollectionItem.class.getResource("/musique/images/NoReleaseImage.png"));
            buzyReleaseImage = ImageIO.read(ViewCollectionItem.class.getResource("/musique/images/BuzyReleaseImage.png"));
            imagePanel = new ViewAutoFitImage(noReleaseImage);
            imagePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            imagePanel.setPreferredSize(new Dimension(200,200));
            add(imagePanel);
        } catch (IOException ex) {
            // doe niets
        }

        // Marge tussen de boxen
        add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.X_AXIS));

        // Tabel aanmaken
        tableModelTracks = new TableModelTracks();
        JTable tableTracks = new JTable(tableModelTracks);
        TableColumnModel tableColumnModel = tableTracks.getColumnModel();
        for (int i = 0; i < 3; i++) {
            tableColumnModel.getColumn(i).setPreferredWidth(COLUMN_WIDTHS[i]);
        }

        // Marge tussen de boxen
        tablePanel.add(Box.createRigidArea(new Dimension(10,0)));
        tablePanel.add(new JScrollPane(tableTracks));
        // Marge tussen de boxen
        tablePanel.add(Box.createRigidArea(new Dimension(10,0)));

        tablePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(tablePanel);

        add(Box.createRigidArea(new Dimension(0, 10)));
    }

    public void stateChanged (ChangeEvent e) {
        ModelRelease selectedRelease = modelSelectedRelease.getSelectedRelease();

        if(selectedRelease != null && !selectedRelease.getTrackList().isEmpty()) {
            // Er is een release geselecteerd en de tracklist is beschikbaar, toon de info in de tabel
            tableModelTracks.setData(selectedRelease.getTrackList());
        } else {
            // Maak de tabel leeg
            ArrayList<Track>emptyList = new ArrayList<Track>();
            tableModelTracks.setData(emptyList);
        }

        if(selectedRelease != null) {
            if(selectedRelease.getBufferedImage() != null) {
                // Toon de cover van een release
                imagePanel.setBufferedImage(selectedRelease.getBufferedImage());
            } else {
                if(!selectedRelease.getTrackList().isEmpty()) {
                     // Er bestaat geen coverimage voor deze release
                    imagePanel.setBufferedImage(noReleaseImage);
                } else {
                    // Toon de buzy-afbeelding daar de release nog moet worden ingeladen
                    imagePanel.setBufferedImage(buzyReleaseImage);
                }
            }
        } else {
            imagePanel.setBufferedImage(null);
        }

        revalidate();
        repaint();
    }
}
