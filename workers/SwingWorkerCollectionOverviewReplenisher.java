package musique.workers;

import musique.models.collectionModels.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.SwingWorker;
import musique.dao.DAORelease;

/**
 *
 * @author Sam Careelmont
 */

public class SwingWorkerCollectionOverviewReplenisher extends SwingWorker<Void, ModelRelease> {
    private IModelCollection modelCollection;
    private ArrayList<String[]> releasesInfo;
    private boolean threadDone;

    public SwingWorkerCollectionOverviewReplenisher(IModelCollection modelCollection, ArrayList<String[]> releasesInfo) {
        this.modelCollection = modelCollection;
        this.releasesInfo = releasesInfo;
        threadDone = false;
    }

    public void stopWorking() {
        threadDone = true;
    }
    
    @Override
    protected void process(List<ModelRelease> releases) {
        // Overloop de gepubliceerde releases en voeg deze toe aan de collectie
        for (ModelRelease release : releases) {
            if(modelCollection.getReleaseById(release.getId()) != null) {
            modelCollection.getReleaseById(release.getId()).setInfo(
                    release.getFormat(), release.getBufferedImage(),
                    release.getYear(), release.getTrackList(),
                    release.getArtist(), release.getLabel());
            }
        }   
    }

    @Override
    protected Void doInBackground() throws Exception {
        // Overloop alle releases
        int i = 0;
        while(i < releasesInfo.size() && !threadDone) {
        //for(String[] releaseInfo : releasesInfo) {
            String[] releaseInfo = releasesInfo.get(i);
            // Maak een Data Access Object op basis van het release-id
            DAORelease daoRelease = new DAORelease(Integer.parseInt(releaseInfo[0]));
            // Laad de afbeelding indien deze bestaat
            BufferedImage bufImage = null;
            if(!daoRelease.getImage().equals("")) {
                 bufImage = ImageIO.read(new URL(daoRelease.getImage()));
            }
            // Maak een release-object met de complete info verkregen uit het DOA
            ModelRelease release = new ModelRelease(
                    Integer.parseInt(releaseInfo[0]), releaseInfo[1],
                     daoRelease.getFormat(), bufImage,
                     daoRelease.getYear(), daoRelease.getTracks(),
                     daoRelease.getArtist(), daoRelease.getLabel());
            // Publiceer de release
            publish(release);
            i++;
        }
        return null;
    }
}
