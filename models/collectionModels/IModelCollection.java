package musique.models.collectionModels;

import javax.swing.event.ChangeListener;

/**
 *
 * @author Sam Careelmont
 */

// Interface voor een collectie, op deze manier is Polymorfisme voor collection-models mogelijk
public interface IModelCollection {
    public void stopWorker();
    public String getDescription();
    public String getName();
    public String getImage();
    public String getCollectionType();
    public ModelRelease getRelease(int index);
    public ModelRelease getReleaseById(int id);
    public void addRelease(ModelRelease release);
    public void removeRelease(ModelRelease release);
    public void removeAllReleases();
    public int getNumberOfReleases();
    public int getPositionOfRelease(ModelRelease release);
    public void addChangeListener(ChangeListener l);
    public void removeChangeListener(ChangeListener l);
}
