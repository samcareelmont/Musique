package musique.models.mainModels;

import musique.models.Model;

/**
 *
 * @author Sam Careelmont
 */

public class ModelSelectedReleasesView extends Model {
    private String currentView;

    public ModelSelectedReleasesView(String view) {
        this.currentView = view;
    }

   public void setSelectedView(String newView) {
        if(!newView.equals(currentView)) {
            currentView = newView;
            fireStateChanged();
        }
    }

    public String getSelectedView() {
        return currentView;
    }

}
