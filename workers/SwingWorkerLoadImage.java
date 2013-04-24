package musique.workers;

import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

/**
 *
 * @author Sam Careelmont
 */

public class SwingWorkerLoadImage extends SwingWorker<BufferedImage, Void> {
    private String imageUrl;

    public SwingWorkerLoadImage(String imageUrl, JPanel viewCollectionInfo) {
        this.imageUrl = imageUrl;
    }

    @Override
    protected BufferedImage doInBackground() throws Exception {
        BufferedImage bimage = ImageIO.read(new URL(imageUrl));
        return bimage;
    }   
}
