package musique.workers;

import be.ugent.caagt.discogs.DiscogsManager;
import javax.swing.SwingWorker;
import org.jdom.Document;

/**
 *
 * @author Sam Careelmont
 */

public class SwingWorkerLoadLabelCollectionXml extends SwingWorker<Document, Void> {
    private String name;

    public SwingWorkerLoadLabelCollectionXml(String name) {
        this.name = name;
    }

    @Override
    protected Document doInBackground() throws Exception {
        Document document = DiscogsManager.getLabelXML(name);
        return document;
    }
}
