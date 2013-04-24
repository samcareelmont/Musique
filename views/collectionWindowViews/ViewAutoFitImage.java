package musique.views.collectionWindowViews;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author Sam Careelmont
 */

public class ViewAutoFitImage extends JPanel {
    private BufferedImage image;

    public ViewAutoFitImage(BufferedImage image) {
        this.image = image;
    }

    public void setBufferedImage(BufferedImage image) {
        this.image = image;
    }

    // Herschaalt de afbeelding zodat deze past binnen de huidige afmetingen van het panel
    @Override
    protected void paintComponent(Graphics g) {
        if(image != null) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            int w = getWidth();
            int h = getHeight();
            int iw = image.getWidth();
            int ih = image.getHeight();
            double xScale = (double)w/iw;
            double yScale = (double)h/ih;
            double scale = Math.min(xScale, yScale);
            int width = (int)(scale*iw);
            int height = (int)(scale*ih);
            int x = (w - width)/2;
            int y = (h - height)/2;
            g2.drawImage(image, x, y, width, height, this);
        }
    }


}
