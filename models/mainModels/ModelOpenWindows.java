package musique.models.mainModels;

import java.util.ArrayList;
import musique.models.Model;
import musique.views.collectionWindowViews.ViewCollectionFrame;

/**
 *
 * @author Sam Careelmont
 */

public class ModelOpenWindows extends Model {
    private ArrayList<ViewCollectionFrame> windows;

    public ModelOpenWindows() {
        windows = new ArrayList<ViewCollectionFrame>();
    }

    public void addWindow(ViewCollectionFrame cf) {
        windows.add(cf);
        fireStateChanged();
    }

    public void removeWindow(ViewCollectionFrame cf) {
        windows.remove(cf);
        fireStateChanged();
    }

    public ArrayList<ViewCollectionFrame> getOpenWindows() {
        return windows;
    }
}
