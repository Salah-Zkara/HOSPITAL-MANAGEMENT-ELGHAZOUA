package MyComponents;

import GestionHopitale.Main;
import javax.swing.*;

public class MyButton extends JButton {

    public MyButton(String titre) {

        Thread t1 = new Main(1);
        t1.start();
        this.setBackground(new java.awt.Color(238, 238, 238));
        this.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        this.setForeground(new java.awt.Color(49, 153, 151));
        this.setText(titre);
        this.setFocusPainted(false);
        this.setFocusable(false);
        this.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        this.setRolloverEnabled(false);

    }

}