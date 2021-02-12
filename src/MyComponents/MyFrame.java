package MyComponents;

import java.awt.*;
import java.io.*;
import java.nio.file.*;
import javax.swing.*;

public class MyFrame extends JFrame {
    File currentDir = new File(".");
    String basePath = currentDir.getCanonicalPath();
    Path imgPath = Paths.get(basePath, "src", "main", "java", "Images", "hosp.png");
    ImageIcon img = new ImageIcon(imgPath.toString());

    public MyFrame(JPanel panel) throws IOException {
        this.setTitle("ElGuezoua Hospital");
        this.setSize(1024, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setIconImage(img.getImage());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBounds(0, 0, 300, 600);
        panel.setBackground(new Color(49, 153, 151));
        panel.setPreferredSize(new java.awt.Dimension(300, 600));
        this.add(panel, BorderLayout.WEST);
    }
    public MyFrame() throws IOException {
        this.setTitle("ElGuezoua Hospital");
        this.setSize(1024, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setIconImage(img.getImage());
    }

    public MyFrame(int width, int height, int a) throws IOException {
        this.setTitle("ElGuezoua Hospital");
        this.setSize(width, height);
        this.setDefaultCloseOperation(a);
        this.setLayout(null);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setIconImage(img.getImage());
    }

}