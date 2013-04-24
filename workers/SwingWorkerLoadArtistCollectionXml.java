package musique.workers;

import be.ugent.caagt.discogs.DiscogsManager;
import javax.swing.SwingWorker;
import org.jdom.Document;

/**
 *
 * @author Sam Careelmont
 */

public class SwingWorkerLoadArtistCollectionXml extends SwingWorker<Document, Void> {
    private String name;

    public SwingWorkerLoadArtistCollectionXml(String name) {
        this.name = name;
    }

    @Override
    protected Document doInBackground() throws Exception {
        Document document = DiscogsManager.getArtistXML(name);
        return document;
    }
}
