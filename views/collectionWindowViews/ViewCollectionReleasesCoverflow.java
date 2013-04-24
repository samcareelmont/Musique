package musique.views.collectionWindowViews;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import musique.models.collectionModels.IModelCollection;
import musique.models.collectionModels.ModelRelease;
import musique.models.collectionModels.ModelSelectedRelease;
import musique.models.mainModels.ModelSelectedReleasesView;
import musique.controllers.collectionListeners.ListenerMouseWheel;
import musique.controllers.collectionActions.ActionCoverflowViewUp;
import musique.controllers.collectionActions.ActionCoverflowViewDown;

/**
 *
 * @author Sam Careelmont
 */

public class ViewCollectionReleasesCoverflow extends AbstractViewCollectionReleases implements ChangeListener, MouseListener {
    private BufferedImage noReleaseImage, buzyReleaseImage, up, down;
    private int yOffset = 10;
    private int xOffset = 10;
    private int thumbWidth = 64;
    private int thumbHeigth = 64;
    private int activeRow = -1;
    private int startCover = 0;
    private int numberOfCovers = 1;
    private Map<Integer,ModelRelease>mapReleasesIndexes;
    
    public ViewCollectionReleasesCoverflow (IModelCollection modelCollection, ModelSelectedRelease modelSelectedRelease, ModelSelectedReleasesView modelSelectedReleasesView) {
        super(modelCollection, modelSelectedRelease, modelSelectedReleasesView);
        mapReleasesIndexes = new HashMap<Integer,ModelRelease>();

        // Luister naar de muis
        addMouseListener(this);

        // Laad de afbeeldingen in die nodig zijn voor deze view
        try {
            noReleaseImage = ImageIO.read(ViewCollectionItem.class.getResource("/musique/images/NoReleaseImage.png"));
            buzyReleaseImage = ImageIO.read(ViewCollectionItem.class.getResource("/musique/images/BuzyReleaseImage.png"));
            up = ImageIO.read(ViewCollectionItem.class.getResource("/musique/images/up.png"));
            down = ImageIO.read(ViewCollectionItem.class.getResource("/musique/images/down.png"));
        } catch (IOException ex) {
            // doe niets
        }

        // Actionmap instellen
        ActionMap actionMap = getActionMap();
        actionMap.put("up", new ActionCoverflowViewUp(this));
        actionMap.put("down", new ActionCoverflowViewDown(this));

        // Inputmap instellen
        InputMap inputMap = getInputMap();
        inputMap.put(KeyStroke.getKeyStroke("UP"), "up");
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "down");

        addMouseWheelListener(new ListenerMouseWheel(this));
        setFocusable(true);
    }

    @Override
    protected void paintComponent (Graphics g) {
        // Maakt een graphics-object aan
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // Vraag de geselecteerde release op
        ModelRelease selectedRelease = modelSelectedRelease.getSelectedRelease();

        // Bereken het aantal covers dat er kunnen getoond worden
        numberOfCovers = ((getHeight() - 20) / 74) - 2;
        if(startCover + numberOfCovers > modelCollection.getNumberOfReleases()) {
            numberOfCovers = modelCollection.getNumberOfReleases()-startCover;
            startCover = modelCollection.getNumberOfReleases() - (startCover + numberOfCovers);
        }

        // Zet anti-aliasing aan
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        // Teken de omhoog-knop
        int y = yOffset;
        g2.drawImage(up, xOffset, y, thumbWidth, thumbHeigth, this);

        // Teken de releases
        mapReleasesIndexes.clear();
        int row = 1;
        for(int i = startCover; i < startCover + numberOfCovers; i++) {
            // Hou bij op welke release er op elke rij staat
            mapReleasesIndexes.put(row, modelCollection.getRelease(i));
            row++;
            // Teken de cover of een standaard afbeelding als er geen cover is
            BufferedImage image;
            if(modelCollection.getRelease(i).getBufferedImage() == null) {
                if(!modelCollection.getRelease(i).getTrackList().isEmpty()) {
                    image = noReleaseImage;
                } else {
                    image = buzyReleaseImage;
                }
            } else {
                image = modelCollection.getRelease(i).getBufferedImage();
            }
            y += thumbHeigth+yOffset;
            g2.drawImage(image, xOffset, y, thumbWidth, thumbHeigth, this);
            // Teken de naam van de release
            Font font = new Font("Dialog", Font.PLAIN, 14);
            if(selectedRelease != null && modelCollection.getRelease(i) == selectedRelease) {
                    font = new Font("Dialog", Font.BOLD, 14);
            }
            g2.setFont(font);
            g2.drawString(modelCollection.getRelease(i).getTitle(), xOffset+thumbWidth+10, y + thumbHeigth/2);
        }

        // Teken de omlaag-knop
        y += thumbHeigth+yOffset;
        g2.drawImage(down, xOffset, y, thumbWidth, thumbHeigth, this);
   }
      
    public void mouseClicked (MouseEvent e) {
        this.requestFocusInWindow();
        int rij = e.getY () / (thumbHeigth+yOffset);
        if (mapReleasesIndexes.get(rij) != null) {
            // Er werd een release geselecteerd
            modelSelectedRelease.setSelectedRelease(mapReleasesIndexes.get(rij));
            repaint();
        } else {
            // Er werd op omhoog of omlaag geklikt
            if (rij == 0) {
                moveUp();
            } else if (rij == numberOfCovers + 1) {
                moveDown();
            }
        }  
    }

    public void moveUp() {
        if(startCover > 0) {
            startCover--;
            repaint();
        }
    }

    public void moveDown() {
        if(startCover < (modelCollection.getNumberOfReleases() - numberOfCovers)) {
            startCover++;
            repaint();
        }
    }

    public void stateChanged (ChangeEvent e) {
        // Reageer alleen op wijziging als deze view geselecteerd is
        if(modelSelectedReleasesView.getSelectedView().equals("coverflow")) {
            // Controleer of er net van View is gewisseld
            if(e.getSource() instanceof ModelSelectedReleasesView && modelSelectedRelease.getSelectedRelease() != null) {
                int pos = modelCollection.getPositionOfRelease(modelSelectedRelease.getSelectedRelease());
                /*
                 * Als de positie van de geselecteerde release buiten de huidige grenzen valt,
                 * worden de grenzen aangepast
                 */
                if(pos < startCover || pos > startCover + numberOfCovers - 1) {
                    startCover = modelCollection.getPositionOfRelease(modelSelectedRelease.getSelectedRelease());
                }
            }
            // Herteken het panel
            repaint();
        }
    }

    // Methode die niets doen maar geimplementeerd moeten zijn
    public void mousePressed  (MouseEvent e) {
    }
    public void mouseReleased (MouseEvent e) {
    }
    public void mouseEntered  (MouseEvent e) {
    }
    public void mouseExited   (MouseEvent e) {
    }
    
}
