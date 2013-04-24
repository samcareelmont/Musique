package musique.models.collectionModels;

import java.util.ArrayList;
import musique.models.Model;

/**
 *
 * @author Sam Careelmont
 */

public class ModelOpenUserCollections extends Model {
    private ArrayList<ModelUserCollection> collections;

    public ModelOpenUserCollections() {
        collections = new ArrayList<ModelUserCollection>();
    }

    public void addCollection(ModelUserCollection collection) {
        collections.add(collection);
        fireStateChanged();
    }

    public void removeCollection(ModelUserCollection collection) {
        collections.remove(collection);
        fireStateChanged();
    }

    public int getNumberOfOpenUserCollections() {
        return collections.size();
    }

     public ArrayList<ModelUserCollection> getOpenCollections() {
        return collections;
    }
}
