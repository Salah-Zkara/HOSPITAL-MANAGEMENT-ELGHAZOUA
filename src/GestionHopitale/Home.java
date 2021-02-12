package GestionHopitale;

import Dashboards.*;
import MyComponents.MyFrame;
import static GestionHopitale.Main.*;
import com.mongodb.client.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import javax.swing.*;
import java.util.logging.*;


public class Home extends javax.swing.JFrame {
    
    String Connection_mongo = Main.Connection_mongo;
    private JPanel panel;
    private JLabel userLabel, passwordLabel, welcome, iconLabel, iconLabel2;
    private JTextField userText;
    private JPasswordField passwordText;
    private JButton loginButton;
    private JComboBox choose;
    MongoClient mongoClient = MongoClients.create(Connection_mongo);
    AdminDashboard admindashboard = new AdminDashboard();
    PationDashboard pationdashboard = new PationDashboard();
    DoctorDashboard doctordashboard = new DoctorDashboard();
    // End of variables declaration 

    public void placeComponents() throws IOException {
        where = 0;
        MyFrame frame = new MyFrame();
        File currentDir = new File(".");
        String basePath = currentDir.getCanonicalPath();
        Path imgPath2 = Paths.get(basePath, "src", "main", "java", "Images", "usericon.png");
        Path imgPath3 = Paths.get(basePath, "src", "main", "java", "Images", "passicon.png");

        iconLabel = new JLabel();
        iconLabel.setBounds(590, 205, 16, 16);
        iconLabel.setIcon(new javax.swing.ImageIcon(imgPath2.toString()));
        frame.add(iconLabel);


        userLabel = new JLabel("Username :");
        userLabel.setBounds(610, 200, 80, 30);
        frame.add(userLabel);
        userText = new JTextField(20);
        userText.setBounds(700, 200, 160, 30);
        frame.add(userText);

        iconLabel2 = new JLabel();
        iconLabel2.setBounds(590, 245, 16, 16);
        iconLabel2.setIcon(new javax.swing.ImageIcon(imgPath3.toString()));
        frame.add(iconLabel2);


        passwordLabel = new JLabel("Password :");
        passwordLabel.setBounds(610, 240, 80, 30);
        frame.add(passwordLabel);
        passwordText = new JPasswordField(20);
        passwordText.setBounds(700, 240, 160, 30);
        frame.add(passwordText);

        String[] choosestring = {
            "",
            "Admin",
            "Doctor",
            "Pation"
        };
        choose = new JComboBox(choosestring);
        choose.setBounds(700, 280, 100, 30);
        choose.setBackground(new java.awt.Color(238, 238, 238));
        choose.setFont(new java.awt.Font("Calibri", 0, 18));
        choose.setEditable(true);
        choose.getEditor().getEditorComponent().setBackground(new java.awt.Color(49, 153, 151));
        ((JTextField) choose.getEditor().getEditorComponent()).setBackground(new java.awt.Color(49, 153, 151)); // NOI18N
        choose.setFocusable(false);
        frame.add(choose);



        loginButton = new JButton("LOGIN");
        loginButton.setBounds(700, 320, 100, 30);
        loginButton.setBackground(new java.awt.Color(238, 238, 238));
        loginButton.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        loginButton.setForeground(new java.awt.Color(49, 153, 151));
        loginButton.setFocusPainted(false);
        loginButton.setFocusable(false);
        loginButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        loginButton.setRolloverEnabled(false);
        loginButton.addActionListener((ActionEvent e) -> {
            String usr = userText.getText();
            String password = String.valueOf(passwordText.getPassword());
            String chosen = choose.getSelectedItem().toString();
            if (netIsAvailable() == false) {
                frr = frame;
                JOptionPane.showMessageDialog(frame, "Please check your connection and try again!", "Connection Error", JOptionPane.WARNING_MESSAGE);
            } else if (usr.isEmpty() || password.isEmpty() || "".equals(chosen)) {
                JOptionPane.showMessageDialog(null, "Sorry, please fill all blanks");
            } else {
                if ("Doctor".equals(chosen)) {
                    if (login(mongoClient, usr, password,"Doctors") == true) {
                        frame.setVisible(false);
                        frame.dispose();
                        try {
                            doctordashboard.placeComponents(usr);
                        } catch (IOException ex) {
                            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                if ("Pation".equals(chosen)) {
                    if (login(mongoClient, usr, password,"Pations") == true) {
                        frame.setVisible(false);
                        frame.dispose();
                        try {
                            pationdashboard.placeComponents(usr.toUpperCase());
                        } catch (IOException ex) {
                            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                if ("Admin".equals(chosen)) {
                    if (loginAdmin(mongoClient, usr, password) == true && "Admin".equals(chosen)) {
                        frame.setVisible(false);
                        frame.dispose();
                        try {

                            admindashboard.placeComponents();
                        } catch (IOException ex) {
                            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
        frame.add(loginButton);


        panel = new JPanel();
        welcome = new JLabel();
        welcome.setText("Welcome to ElGhezoua Hospital");
        welcome.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        welcome.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        welcome.setForeground(new Color(250, 250, 250));
        welcome.setFont(new Font("Calibri", Font.BOLD, 30));
        panel.setLayout(new GridBagLayout());
        panel.add(welcome);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBounds(0, 0, 450, 600);
        panel.setBackground(new Color(49, 153, 151));
        frame.add(panel, BorderLayout.WEST);
        frame.setVisible(true);

    }
    
}