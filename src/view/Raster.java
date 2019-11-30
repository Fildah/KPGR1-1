package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class Raster extends JPanel {

    private final BufferedImage img; // objekt pro zápis pixelů
    private final Graphics g; // objekt nad kterým jsou k dispozici grafické funkce
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int FPS = 1000 / 30;

    public Raster() {
        setPreferredSize(new Dimension(screenSize.width / 2, screenSize.height / 2));
        // inicializace image, nastavení rozměrů (nastavení typu - pro nás nedůležité)
        img = new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_INT_RGB);
        g = img.getGraphics();
        setLoop();
        clear();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
        // pro zájemce - co dělá observer - https://stackoverflow.com/a/1684476
    }

    private void setLoop() {
        // časovač, který 30 krát za vteřinu obnoví obsah plátna aktuálním img
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        }, 0, FPS);
    }

    public void clear() {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, screenSize.width, screenSize.height);
    }

    public void drawPixel(int x, int y, int color) {
        img.setRGB(x, y, color);
    }

    public void drawPixelAA(int x, int y, Color color, float alfa) {
        Color newColor = new Color(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, alfa);
        img.setRGB(x, y, newColor.getRGB());
    }

    public int getPixelColor(int x, int y) {
        return img.getRGB(x, y);
    }

    public Dimension getScreenSize() {
        return screenSize;
    }
}
