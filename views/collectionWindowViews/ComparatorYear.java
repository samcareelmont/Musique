package musique.views.collectionWindowViews;

import java.util.Comparator;

/**
 *
 * @author Sam Careelmont
 */

public class ComparatorYear implements Comparator<Integer> {
    /**
     * Compare-methode die ervoor zorgt dat de jaartallen gesorteerd staan
     * van klein naar groot maar dat het jaartal "-1" achteraan komt
     */
    public int compare (Integer y1, Integer y2) {
        int val = 0;
        if(y1 == -1 && y2 != -1) {
            val = 1;
        } else if(y2 == -1 && y1 != -1) {
            val = -1;
        } else if (y1 > y2) {
            val = 1;
        } else if (y1 < y2) {
            val = -1;
        }
        return val;
    }
}
