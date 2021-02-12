package MyComponents;

import Dashboards.PationDashboard;
import GestionHopitale.Home;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.*;
import javax.swing.*;
import static GestionHopitale.Main.netIsAvailable;

public class LogoutButton extends JButton {
    public LogoutButton(MyFrame frame) {
        this.setText("LOG OUT");
        this.setBackground(new java.awt.Color(238, 238, 238));
        this.setFont(new java.awt.Font("Calibri", 1, 18));
        this.setForeground(new java.awt.Color(49, 153, 151));
        this.setBounds(750, 450, 209, 70);
        this.setFocusPainted(false);
        this.setFocusable(false);
        this.setRolloverEnabled(false);
        this.addActionListener((ActionEvent evt) -> {
            int selectedoption = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to log out?",
                "Choose",
                JOptionPane.YES_NO_OPTION);

            if (selectedoption == JOptionPane.YES_OPTION) {
                if (netIsAvailable() == false) {
                    System.exit(-1);
                } else {
                    Home mainframe = new Home();
                    frame.setVisible(false);
                    frame.dispose();
                    try {
                        mainframe.placeComponents();
                    } catch (IOException ex) {
                        Logger.getLogger(PationDashboard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }


        });
    }

}