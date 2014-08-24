package Run4YourBits;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * This class generates the remaining hits images and changes them according to the number of hits left
 */

public class Lives {

    public BufferedImage heartSprites[] = new BufferedImage[5];
    public BufferedImage heart, unheart, gold, locZero, locOne, locTwo, locThree, locFour;
/**
 * Constructs the lives display
 */
    public Lives() {
        try {
            heart = CompatibleImage.toCompatibleImage(ImageIO.read(new File(
                    "Images"+File.separator+"Character"+File.separator+"heart.png")));
            unheart = CompatibleImage.toCompatibleImage(ImageIO.read(new File(
                    "Images"+File.separator+"Character"+File.separator+"unheart.png")));
            gold = CompatibleImage.toCompatibleImage(ImageIO.read(new File(
                    "Images"+File.separator+"Character"+File.separator+"gold.png")));

        } catch (Exception e) {
            System.out.println("Error al cargar imagen" + ":" + e.toString());
        }
        //mayor a 3 vidas

        locFour = CompatibleImage.toCompatibleImage(new BufferedImage((heart.getWidth() * 3) + 30,
                heart.getHeight(), BufferedImage.TYPE_INT_ARGB));
        Graphics g = locFour.getGraphics();

        g.drawImage(gold, 0, 0, null);
        g.drawImage(gold, gold.getWidth() + 10, 0, null);
        g.drawImage(gold, (gold.getWidth() * 2) + 20, 0, null);
        g.dispose();
        heartSprites[4] = locFour;
        locFour.flush();

        //3 vidas

        locThree = CompatibleImage.toCompatibleImage(new BufferedImage((heart.getWidth() * 3) + 30,
                heart.getHeight(), BufferedImage.TYPE_INT_ARGB));
        Graphics h = locThree.getGraphics();

        h.drawImage(heart, 0, 0, null);
        h.drawImage(heart, gold.getWidth() + 10, 0, null);
        h.drawImage(heart, (gold.getWidth() * 2) + 20, 0, null);
        h.dispose();
        heartSprites[3] = locThree;
        locThree.flush();

        //2 vidas
        locTwo = CompatibleImage.toCompatibleImage(new BufferedImage((heart.getWidth() * 3) + 30,
                heart.getHeight(), BufferedImage.TYPE_INT_ARGB));
        Graphics i = locTwo.getGraphics();

        i.drawImage(heart, 0, 0, null);
        i.drawImage(heart, gold.getWidth() + 10, 0, null);
        i.drawImage(unheart, (gold.getWidth() * 2) + 20, 0, null);
        i.dispose();
        heartSprites[2] = locTwo;
        locTwo.flush();


        //1 vidas
        locOne = CompatibleImage.toCompatibleImage(new BufferedImage((heart.getWidth() * 3) + 30,
                heart.getHeight(), BufferedImage.TYPE_INT_ARGB));
        Graphics j = locOne.getGraphics();

        j.drawImage(heart, 0, 0, null);
        j.drawImage(unheart, gold.getWidth() + 10, 0, null);
        j.drawImage(unheart, (gold.getWidth() * 2) + 20, 0, null);
        j.dispose();
        heartSprites[1] = locOne;
        locOne.flush();
        
        //0 vidas
        locZero = CompatibleImage.toCompatibleImage(new BufferedImage((heart.getWidth() * 3) + 30,
                heart.getHeight(), BufferedImage.TYPE_INT_ARGB));
        Graphics k = locZero.getGraphics();

        k.drawImage(unheart, 0, 0, null);
        k.drawImage(unheart, gold.getWidth() + 10, 0, null);
        k.drawImage(unheart, (gold.getWidth() * 2) + 20, 0, null);
        k.dispose();
        heartSprites[0] = locZero;
        locZero.flush();

    }
/**
 * Reuturn the correct image depending on the lives reamining
 * @param remaining lives remaining
 * @return the live display image
 */
    public BufferedImage getImage(int remaining) {

        if (remaining > 3) {
            //System.out.println("Localidad["+remaining+"]");
            return heartSprites[4];
        }
        else if (remaining>=0){
            //System.out.println("Localidad["+remaining+"]");
            return heartSprites[remaining];
        }
        return null;
    }
}