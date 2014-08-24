package Run4YourBits;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsConfiguration;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
/**
 * This class creates button with the right look and feel for the project
 */
public class CustomButton {

    /**
     * Creates a Custom Swing JButton
     */
    public static JButton createButton(String display, GraphicsConfiguration gc, int fontSize) {
        // create the button
        JButton button = new JButton(display);
        button.setIgnoreRepaint(true);
        button.setFocusable(false);
        button.setOpaque(true);
        button.setForeground(new Color(215, 58, 55));
        button.setBackground(new Color(255, 255, 255, 200));
        button.setBorder(BorderFactory.createMatteBorder(-1, -1, -1, -1, new ImageIcon("Images/brick.png")));
        button.setBorderPainted(true);
        button.setContentAreaFilled(true);
        button.setRolloverIcon(null);
        try {
            button.setFont((Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("Fonts" + File.separator + "wonder.ttf")))).deriveFont(Font.BOLD, fontSize));
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(Highscores.class.getName()).log(Level.SEVERE, null, ex);
        }

        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.setHorizontalTextPosition(SwingConstants.CENTER);

        return button;
    }
}