package view;

import javax.swing.*;
import java.awt.*;

public class PGRFWindow extends JFrame {

    private final Raster raster;

    public PGRFWindow() {
        // bez tohoto nastavení se okno zavře, ale aplikace stále běží na pozadí
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("PGRF1 cvičení"); // titulek okna

        raster = new Raster();
        raster.setFocusable(true);
        raster.grabFocus(); // důležité pro pozdější ovládání z klávesnice

        FlowLayout layout = new FlowLayout();
        layout.setVgap(0);
        layout.setHgap(0);

        setLayout(layout);
        add(raster); // vložit plátno do okna
        pack();

        setLocationRelativeTo(null); // vycentrovat okno
    }

    public Raster getRaster() {
        return raster;
    }

}
