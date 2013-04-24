package musique.views.collectionWindowViews;

import javax.swing.JPanel;
import javax.swing.event.ChangeListener;
import musique.models.collectionModels.IModelCollection;
import musique.models.collectionModels.ModelSelectedRelease;
import musique.models.mainModels.ModelSelectedReleasesView;

/**
 *
 * @author Sam Careelmont
 */

public abstract class AbstractViewCollectionReleases extends JPanel implements ChangeListener {
    protected ModelSelectedRelease modelSelectedRelease;
    protected IModelCollection modelCollection;
    protected ModelSelectedReleasesView modelSelectedReleasesView;

    public AbstractViewCollectionReleases(IModelCollection modelCollection, ModelSelectedRelease modelSelectedRelease, ModelSelectedReleasesView modelSelectedReleasesView) {
        this.modelSelectedRelease = modelSelectedRelease;
        this.modelCollection = modelCollection;
        this.modelSelectedReleasesView = modelSelectedReleasesView;

        /*
         * De view luistert naar het collectiemodel
         * om te reageren als er release worden toegevoegd of verwijderd
         */
        modelCollection.addChangeListener(this);
        // De view reageert wanneer de view werd gewijzigd
        modelSelectedReleasesView.addChangeListener(this);
        /*
         * Er wordt geluisterd naar elke release
         * omdat de view moet hernieuwd worden als er meer info over een release beschikbaar is
         */
        for(int i = 0; i < modelCollection.getNumberOfReleases(); i++) {
           modelCollection.getRelease(i).addChangeListener(this);
        }
    }
}
