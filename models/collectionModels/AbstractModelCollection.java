package musique.models.collectionModels;

import musique.workers.SwingWorkerCollectionOverviewReplenisher;
import java.util.ArrayList;
import musique.dao.IDAOCollection;
import musique.models.Model;

/**
 *
 * @author Sam Careelmont
 */

public class AbstractModelCollection extends Model implements IModelCollection {
    private String name, description, image;
    private ArrayList<ModelRelease> releases;
    private SwingWorkerCollectionOverviewReplenisher worker;

    public AbstractModelCollection(IDAOCollection daoCollection) {
        // Stel de gegevens in die je krijgt uit het Data Access Object
        name = daoCollection.getName();
        description = daoCollection.getDescription();
        image = daoCollection.getImage();
        releases = new ArrayList<ModelRelease>();

        ArrayList<String[]> releasesInfo = daoCollection.getReleases();
        for(String[] releaseInfo : releasesInfo) {
            // Als de lengte van de releaseInfo 3 is, weten we dat er ook info over het jaartal bij is
            if(releaseInfo.length == 3) {
                // Voeg een release toe met de reeds gekende info
                addRelease(new ModelRelease(
                        Integer.parseInt(releaseInfo[0]), releaseInfo[1], Integer.parseInt(releaseInfo[2])));
            } else {
                // Omdat we het jaartal nog niet kennen, wordt dit op -1 ingesteld
                addRelease(new ModelRelease(
                        Integer.parseInt(releaseInfo[0]), releaseInfo[1], -1));
            }
        }

        // Start een SwingWorker die de extra info over de releases zal opvragen
        worker = new SwingWorkerCollectionOverviewReplenisher(this, releasesInfo);
        worker.execute();
     }

    // Tweede constructor die gebruikt wordt bij het aanmaken van een nieuwe UserCollection
    public AbstractModelCollection(String name) {
        this.name = name;
        description = "";
        image = "";
        releases = new ArrayList<ModelRelease>();
    }

    // Methode om de thread te doen stoppen als het venster gesloten is
    public void stopWorker() {
        if(worker != null) {
            worker.stopWorking();
        }
    }

    // getters en setters
    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public ModelRelease getRelease(int i) {
        return releases.get(i);
    }

    public int getNumberOfReleases() {
        return releases.size();
    }

    public int getPositionOfRelease(ModelRelease release) {
        return releases.indexOf(release);
    }

    public ModelRelease getReleaseById(int id) {
        int i = 0;
        while(i < releases.size() && releases.get(i).getId() != id) {
                i++;
        }
        if(i == releases.size()) {
            return null;
        } else {
            return releases.get(i);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addRelease(ModelRelease release) {
        if(!releases.contains(release)) {
            releases.add(release);
            fireStateChanged();
        }
    }

    public void removeRelease(ModelRelease release) {
        releases.remove(release);
        fireStateChanged();
    }

    public void removeAllReleases() {
        releases.clear();
    }

    public String getCollectionType() {
            return "";
    }
}
