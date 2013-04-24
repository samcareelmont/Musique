package musique.dao;

import java.util.ArrayList;

/**
 *
 * @author Sam Carelmont
 */

public interface IDAOCollection {
    public String getName();
    public String getDescription();
    public String getImage();
    public ArrayList<String[]> getReleases();
}
