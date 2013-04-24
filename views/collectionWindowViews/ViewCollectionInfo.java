package musique.views.collectionWindowViews;

import musique.workers.SwingWorkerLoadImage;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutionException;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import musique.controllers.collectionListeners.ListenerCollectionInfoClick;
import musique.controllers.collectionListeners.ListenerCollectionInfoTextAreaClick;
import musique.models.collectionModels.IModelCollection;
import musique.models.collectionModels.ModelUserCollection;

/**
 *
 * @author Sam Careelmont
 */

public class ViewCollectionInfo extends JPanel implements ChangeListener, PropertyChangeListener {
    private IModelCollection modelCollection;
    private SwingWorker<BufferedImage, Void> worker;
    
    public ViewCollectionInfo(IModelCollection modelCollection) {
        this.modelCollection = modelCollection;

        setLayout(new BorderLayout());        
        setBorder(BorderFactory.createTitledBorder(modelCollection.getName()));

        // Tekstvak instellen waar de description inkomt
        JTextArea textAreaDescription = new JTextArea();
        textAreaDescription.setLineWrap(true);
        textAreaDescription.setWrapStyleWord(true);
        textAreaDescription.setEditable(false);
        textAreaDescription.setFocusable(false);
        textAreaDescription.setBackground(this.getBackground());
        textAreaDescription.setText(modelCollection.getDescription());
        add(textAreaDescription, BorderLayout.NORTH);

        // Bij een UserCollection kan je de description wijzigen, voeg daarvoor de juiste luisteraars toe
        if(modelCollection.getCollectionType().equals("UserCollection")) {
            modelCollection.addChangeListener(this);           
            textAreaDescription.addMouseListener(new ListenerCollectionInfoTextAreaClick());
            addMouseListener(new ListenerCollectionInfoClick(textAreaDescription, this.getBackground(), (ModelUserCollection)modelCollection));
        }

        // Stel een SwingWorker in die de collectie-afbeelding zal inladen
        worker = new SwingWorkerLoadImage(modelCollection.getImage(), this);
        worker.addPropertyChangeListener(this);
        worker.execute();
    }

    public void stateChanged (ChangeEvent e) {
       setBorder(BorderFactory.createTitledBorder(modelCollection.getName()));
    }

    public void propertyChange(PropertyChangeEvent evt) {
        // Als de worker gedaan heeft
        if ("state".equals(evt.getPropertyName()) && worker.isDone()) {
            try {
                // Voeg de afbeelding toe aan een paneel dat de afbeelding automatisch zal herschalen
                JPanel imagePanel = new ViewAutoFitImage(worker.get());
                add(imagePanel, BorderLayout.CENTER);
                revalidate();
                repaint();
            } catch (InterruptedException ex) {
                // doe niets
            } catch (ExecutionException ex) {
                // doe niets
            } 
        }
    }
}
