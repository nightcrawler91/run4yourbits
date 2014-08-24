package Run4YourBits;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Background for the different menus
 */
public class Fondo extends javax.swing.JPanel {

    BufferedImage imagenFondo;

    /**
     * Constructs the panel
     *
     * @param stringImage String for finding the image file
     */
    public Fondo(String stringImage) {
        initComponents();
        try {
            imagenFondo = CompatibleImage.toCompatibleImage(ImageIO.read(new File("Images" + File.separator + stringImage)));
        } catch (IOException ex) {
            Logger.getLogger(Fondo.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setSize(imagenFondo.getWidth(), imagenFondo.getHeight());
    }

    /**
     * Paints everything
     *
     * @param g Required Graphics component
     */
    @Override
    public void paintComponent(Graphics g) {

        g.drawImage(imagenFondo, 0, 0, null);
        setOpaque(false);
        super.paintComponent(g);
    }

    /**
     * Initialize Components
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
