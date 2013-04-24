package musique.controllers.collectionActions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import musique.models.collectionModels.IModelCollection;
import musique.models.collectionModels.ModelRelease;
import musique.models.collectionModels.ModelUserCollection;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author Sam Careelmont
 */

public class ActionSaveUserCollectionToFile extends AbstractAction {
    private ModelUserCollection modelUserCollection;

    public ActionSaveUserCollectionToFile(String name, IModelCollection modelUserCollection) {
        super(name);
        // Deze actie mag enkel Enabled zijn bij een UserCollection-venster
        if(modelUserCollection.getCollectionType().equals("UserCollection")) {
            this.modelUserCollection = (ModelUserCollection)modelUserCollection;
        } else {
            setEnabled(false);
        }
    }

    public void actionPerformed(ActionEvent e) {
        // Toon een dialoogvenster waarmee je een naam opgeeft en een pad selecteert
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File(modelUserCollection.getName() + ".xml"));

        int returnVal = fileChooser.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            writeDateToFile(file);
        } 
    }

    // Schrijf de data van de UserCollectie weg naar de opgegeven file
    public void writeDateToFile(File file) {
        XMLOutputter outputter = new XMLOutputter();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            //<collection>
            Element rootElement = new Element("collection");
            Document documentUserCollection = new Document(rootElement);
            //<info></info>
            rootElement.addContent(new Element("info").addContent(modelUserCollection.getDescription()));
            //<name></name>
            rootElement.addContent(new Element("name").addContent(modelUserCollection.getName()));
            //<releases>
            Element elementReleases = new Element("releases");
            for(int i = 0; i < modelUserCollection.getNumberOfReleases(); i++) {
                ModelRelease release = modelUserCollection.getRelease(i);
                Element elementRelease = new Element("release");
                elementRelease.setAttribute(new Attribute("id", Integer.toString(release.getId())));
                elementRelease.addContent(new Element("artist").addContent(release.getArtist()));
                elementRelease.addContent(new Element("label").addContent(release.getLabel()));
                elementRelease.addContent(new Element("title").addContent(release.getTitle()));
                if(release.getYear() != -1) {
                    elementRelease.addContent(new Element("year").addContent(Integer.toString(release.getYear())));
                }
                elementReleases.addContent(elementRelease);
            }
            rootElement.addContent(elementReleases);

            // Het aangemaakte JDOM-document wegschrijven naar de outputstream
            outputter.output(documentUserCollection, fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File not saved", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
