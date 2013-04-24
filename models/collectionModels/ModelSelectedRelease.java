package musique.models.collectionModels;

import musique.models.Model;

/**
 *
 * @author Sam Careelmont
 */

public class ModelSelectedRelease extends Model {
    ModelRelease release;

    public ModelSelectedRelease() {
        release = null;
    }

    public ModelRelease getSelectedRelease() {
        return release;
    }

    // Wijzig de geselecteerde release
    public void setSelectedRelease(ModelRelease release) {
        if(this.release != release) {
            this.release = release;
            fireStateChanged();
        }
    }
}
