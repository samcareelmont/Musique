package musique.workers;

import java.io.File;
import java.io.FileInputStream;
import javax.swing.SwingWorker;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author Sam Careelmont
 */

public class SwingWorkerLoadUserCollectionXml extends SwingWorker<Document, Void> {
    private File file;

    public SwingWorkerLoadUserCollectionXml(File file) {
        this.file = file;
    }

    @Override
    protected Document doInBackground() throws Exception {
        FileInputStream fileInputStream = new FileInputStream(file);
        Document document = new SAXBuilder().build(fileInputStream);
        /**
         * Aangezien deze file meestal vanop de HD gelezen wordt of heel klein kan zijn,
         * wordt deze regel toegevoegd om te voorkomen dat alles in de soep draait
         * door een te snelle afhandeling van deze SwingWorker
         */
        Thread.sleep(200);
        return document;
    }
}
