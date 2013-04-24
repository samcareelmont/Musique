package musique.models.collectionModels;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Sam Careelmont
 */

public class TableModelTracks extends AbstractTableModel {
    private ArrayList<Track> data;
    private static final String[] COLUMN_NAMES = {"Track", "Title", "Duration"};

    public TableModelTracks() {
        data = new ArrayList<Track>();
    }

    public int getRowCount() {
        return data.size();
    }

    public int getColumnCount() {
        return 3;
    }

    public Object getValueAt(int row, int column) {
        Track track = data.get(row);
        switch(column) {
            case 0:
                    return track.getPosition();
            case 1:
                    return track.getTitle();
            case 2:
                    return track.getDuration();
            default:
                    return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    public void setData(ArrayList<Track> data) {
        this.data = data;
        fireTableDataChanged();
    }
}
