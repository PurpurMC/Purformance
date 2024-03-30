package org.purpurmc.purformance.gui;

import java.awt.BorderLayout;
import java.util.Objects;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class Gui {
    public static void createAndShow(String title)  {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        JFrame window = new JFrame(title);

        window.setVisible(true);
        window.setSize(400, 100);
        window.setName(title);
        window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JLabel label = new JLabel("The GUI has been disabled for performance reasons.", SwingConstants.CENTER);
        JButton button = new JButton("Understood");

        button.addActionListener(actionEvent -> {
            window.dispose();
        });

        window.add(label, BorderLayout.NORTH);
        window.add(button, BorderLayout.SOUTH);

        try {
            window.setIconImage(javax.imageio.ImageIO.read(Objects.requireNonNull(Gui.class.getClassLoader().getResourceAsStream("logo.png"))));
        } catch (java.io.IOException ignore) {
        }
    }
}
