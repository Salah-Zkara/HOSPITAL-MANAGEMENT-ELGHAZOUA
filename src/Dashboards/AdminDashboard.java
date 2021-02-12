package Dashboards;


import GestionHopitale.*;
import MyComponents.*;
import MyClasses.*;
import static GestionHopitale.MethodsOnDB.*;
import static GestionHopitale.Main.*;
import com.mongodb.client.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.*;
import javax.swing.*;

public class AdminDashboard {
    

    String Connection_mongo = Main.Connection_mongo;
    private JLabel userLabel, passwordLabel, phoneLabel, fnamelabel, dateofbirthlabel, lnamelabel, cniLabel, rdvlabel, SpeacialityLabel, titleLabel;
    private JTextField userText, phoneText, fnameText, lnameText, cniText;
    private JPasswordField passwordText;
    private JPanel leftpanel, panel;
    private MyFrame frame;
    private JComboBox speciality, time;
    private MyButton gestiondesmedecins, gestiondespatients, gestiondesrdv;
    boolean gestiondespatientsclicked = false, gestiondesmedecinsclicked = false, validPassword, gestiondesrdvclicked = false;
    private JDateChooser datechooser;
    // End of variables declaration 


    public void gestiondespatients() throws NoSuchAlgorithmException, ParseException {
        if (gestiondespatientsclicked == false) {
            gestiondespatientsclicked = true;
            panel = new JPanel();
            panel.setBounds(300, 0, 724, 450);
            panel.setBackground(new Color(238, 238, 238));
            panel.setPreferredSize(new java.awt.Dimension(724, 600));
            frame.add(panel, BorderLayout.EAST);
            frame.add(panel);
            frr = frame;


            fnamelabel = new JLabel("First name :");
            panel.add(fnamelabel);
            fnameText = new JTextField(20);
            panel.add(fnameText);

            lnamelabel = new JLabel("Last name :");
            panel.add(lnamelabel);
            lnameText = new JTextField(20);
            panel.add(lnameText);


            userLabel = new JLabel("Username :");
            panel.add(userLabel);
            userText = new JTextField(20);
            panel.add(userText);


            passwordLabel = new JLabel("Password :");
            panel.add(passwordLabel);
            passwordText = new JPasswordField(20);
            panel.add(passwordText);

            cniLabel = new JLabel("CNI :");
            panel.add(cniLabel);
            cniText = new JTextField(20);
            panel.add(cniText);

            dateofbirthlabel = new JLabel("Date of birth");
            panel.add(dateofbirthlabel);
            datechooser = new JDateChooser();


            phoneLabel = new JLabel("Phone number :");
            panel.add(phoneLabel);
            phoneText = new JTextField(20);
            panel.add(phoneText);

            MyButton addbutton = new MyButton("Add");
            addbutton.addActionListener((ActionEvent evt) -> {
                if (netIsAvailable() == false) {
                    frr = frame;
                    JOptionPane.showMessageDialog(frr, "Please check your connection and try again!", "Connection Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        String FirstName = fnameText.getText();
                        String LastName = lnameText.getText();
                        String UserName = userText.getText().toUpperCase();
                        String cni = cniText.getText().toUpperCase();
                        String phone = phoneText.getText();
                        Date dob = datechooser.getDate();
                        String password = String.valueOf(passwordText.getPassword());
                        Pation pat = new Pation(FirstName, LastName, cni, UserName, password, dob, phone);
                        try (MongoClient mongoClient = MongoClients.create(Connection_mongo)) {
                            if (Main.CheckPationToAddHim(mongoClient, pat, password) == true) {
                                MethodsOnDB.InsertPation(mongoClient, pat);
                                JOptionPane.showMessageDialog(null, "Patient added");
                            }
                        }
                    } catch (NoSuchAlgorithmException | ParseException ex) {
                        Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            panel.add(addbutton);


            MyButton showdbbutton = new MyButton("Show DataBase");
            showdbbutton.addActionListener((ActionEvent evt) -> {
                if (netIsAvailable() == false) {
                    frr = frame;
                    JOptionPane.showMessageDialog(frr, "Please check your connection and try again!", "Connection Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        showdb showdatabase = new showdb();
                        showdatabase.showpatientsdb();
                    } catch (IOException ex) {
                        Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            panel.add(showdbbutton);

            javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
            panel.setLayout(panelLayout);
            panelLayout.setHorizontalGroup(
                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelLayout.createSequentialGroup()
                    .addGap(86, 86, 86)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(phoneLabel)
                        .addComponent(dateofbirthlabel)
                        .addComponent(cniLabel)
                        .addComponent(passwordLabel)
                        .addComponent(userLabel)
                        .addComponent(lnamelabel)
                        .addComponent(fnamelabel))
                    .addGap(45, 45, 45)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(fnameText, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lnameText, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(userText, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(passwordText, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cniText, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(datechooser, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(phoneText, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(79, 79, 79)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(addbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(showdbbutton))
                    .addContainerGap(118, Short.MAX_VALUE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                )
            );
            panelLayout.setVerticalGroup(
                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelLayout.createSequentialGroup()
                    .addContainerGap(190, Short.MAX_VALUE)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(panelLayout.createSequentialGroup()
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(fnamelabel)
                                .addComponent(fnameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lnamelabel)
                                .addComponent(lnameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(userLabel)
                                .addComponent(userText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(17, 17, 17)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(passwordLabel)
                                .addComponent(passwordText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cniLabel)
                                .addComponent(cniText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(17, 17, 17)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(dateofbirthlabel)
                                .addComponent(datechooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            )
                            .addGap(18, 18, 18)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(phoneLabel)
                                .addComponent(phoneText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(106, 106, 106))
                        .addGroup(panelLayout.createSequentialGroup()
                            .addComponent(addbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(showdbbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGap(180, 180, 180)))
                )
            );
            panel.validate();
        }
    }
    public void Removegestiondespatients() {
        if (gestiondespatientsclicked == true) {
            panel.setVisible(false);
            frame.remove(panel);
        }
        gestiondespatientsclicked = false;
    }
    public void gestiondesmedecins() {
        if (gestiondesmedecinsclicked == false) {
            gestiondesmedecinsclicked = true;
            panel = new JPanel();
            panel.setBounds(300, 0, 724, 450);
            panel.setBackground(new Color(238, 238, 238));
            panel.setPreferredSize(new java.awt.Dimension(724, 600));
            frame.add(panel, BorderLayout.EAST);
            frame.add(panel);
            frr = frame;
            fnamelabel = new JLabel("First name :");
            panel.add(fnamelabel);
            fnameText = new JTextField(20);
            panel.add(fnameText);

            lnamelabel = new JLabel("Last name :");
            panel.add(lnamelabel);
            lnameText = new JTextField(20);
            panel.add(lnameText);


            userLabel = new JLabel("Username :");
            panel.add(userLabel);
            userText = new JTextField(20);
            panel.add(userText);


            passwordLabel = new JLabel("Password :");
            panel.add(passwordLabel);
            passwordText = new JPasswordField(20);
            panel.add(passwordText);

            cniLabel = new JLabel("CNI :");
            panel.add(cniLabel);
            cniText = new JTextField(20);
            panel.add(cniText);

            dateofbirthlabel = new JLabel("Date of birth");
            panel.add(dateofbirthlabel);
            datechooser = new JDateChooser();


            SpeacialityLabel = new JLabel("Speciality :");
            panel.add(SpeacialityLabel);
            String[] specialitystring = {
                "",
                "Cardiologie",
                "Chirurgie",
                "Dermatologie",
                "Gériatrie",
                "Oncologie",
                "Pédiatrie",
                "Psychiatrie",
                "Allergologie"
            };
            speciality = new JComboBox(specialitystring);
            speciality.setBounds(700, 280, 100, 30);
            speciality.setBackground(new java.awt.Color(238, 238, 238));
            speciality.setFont(new java.awt.Font("Calibri", 0, 18));
            speciality.setEditable(true);
            speciality.setFocusable(false);
            panel.add(speciality);



            MyButton addbutton = new MyButton("Add");
            addbutton.addActionListener((ActionEvent e) -> {
                if (netIsAvailable() == false) {
                    frr = frame;
                    JOptionPane.showMessageDialog(frr, "Please check your connection and try again!", "Connection Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        String FirstName = fnameText.getText();
                        String LastName = lnameText.getText();
                        String UserName = userText.getText();
                        String cni = cniText.getText();
                        Date dob = datechooser.getDate();
                        String password = String.valueOf(passwordText.getPassword());
                        String specialitychosen = (String) speciality.getSelectedItem();
                        Doctor doc = new Doctor(FirstName, LastName, cni, UserName, password, dob, specialitychosen);
                        try (MongoClient mongoClient = MongoClients.create(Connection_mongo)) {
                            if (Main.CheckDoctorToAddHim(mongoClient, doc, password) == true) {
                                MethodsOnDB.InsertDoctor(mongoClient, doc);
                                JOptionPane.showMessageDialog(null, "Doctor added");
                            }
                        }
                    } catch (NoSuchAlgorithmException | ParseException ex) {
                        Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            panel.add(addbutton);



            MyButton showdbbutton = new MyButton("Show DataBase");
            showdbbutton.addActionListener((ActionEvent evt) -> {
                if (netIsAvailable() == false) {
                    frr = frame;
                    JOptionPane.showMessageDialog(frr, "Please check your connection and try again!", "Connection Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        showdb showdatabase = new showdb();
                        showdatabase.showmedecinsdb();
                    } catch (IOException ex) {
                        Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            });
            panel.add(showdbbutton);

            javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
            panel.setLayout(panelLayout);
            panelLayout.setHorizontalGroup(
                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelLayout.createSequentialGroup()
                    .addGap(86, 86, 86)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(SpeacialityLabel)
                        .addComponent(dateofbirthlabel)
                        .addComponent(cniLabel)
                        .addComponent(passwordLabel)
                        .addComponent(userLabel)
                        .addComponent(lnamelabel)
                        .addComponent(fnamelabel))
                    .addGap(45, 45, 45)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(fnameText, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lnameText, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(userText, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(passwordText, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cniText, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(datechooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(speciality, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(79, 79, 79)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(addbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(showdbbutton))
                    .addContainerGap(118, Short.MAX_VALUE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                )
            );
            panelLayout.setVerticalGroup(
                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelLayout.createSequentialGroup()
                    .addContainerGap(190, Short.MAX_VALUE)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(panelLayout.createSequentialGroup()
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(fnamelabel)
                                .addComponent(fnameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lnamelabel)
                                .addComponent(lnameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(userLabel)
                                .addComponent(userText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(17, 17, 17)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(passwordLabel)
                                .addComponent(passwordText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cniLabel)
                                .addComponent(cniText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(17, 17, 17)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(dateofbirthlabel)
                                .addComponent(datechooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            )
                            .addGap(18, 18, 18)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(SpeacialityLabel)
                                .addComponent(speciality, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(106, 106, 106))
                        .addGroup(panelLayout.createSequentialGroup()
                            .addComponent(addbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(showdbbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(180, 180, 180)))
                )
            );
            panel.validate();
        }
    }
    public void Removegestiondesmedecins() {
        if (gestiondesmedecinsclicked == true) {
            panel.setVisible(false);
            frame.remove(panel);
        }
        gestiondesmedecinsclicked = false;
    }

    public void gestiondesrendezvous() {
        if (gestiondesrdvclicked == false) {
            gestiondesrdvclicked = true;
            panel = new JPanel();
            panel.setBounds(300, 0, 724, 450);
            panel.setBackground(new Color(238, 238, 238));
            panel.setPreferredSize(new java.awt.Dimension(724, 450));
            frame.add(panel, BorderLayout.EAST);
            frame.add(panel);

            frr = frame;
            userLabel = new JLabel("Username :");
            panel.add(userLabel);
            userText = new JTextField(20);
            panel.add(userText);

            cniLabel = new JLabel("CNI :");
            panel.add(cniLabel);
            cniText = new JTextField(20);
            panel.add(cniText);

            rdvlabel = new JLabel("Rendez-vous");
            panel.add(rdvlabel);
            datechooser = new JDateChooser();
            time = new JComboBox();
            time.setModel(new javax.swing.DefaultComboBoxModel < > (new String[] {
                "",
                "9:00 AM",
                "10:00 AM",
                "11:00 AM",
                "12:00 AM",
                "2:00 PM",
                "3:00 PM",
                "4:00 PM",
                "5:00 PM"
            }));



            JLabel SpecialityLabel = new JLabel("Doctor speciality :");
            panel.add(SpecialityLabel);
            String[] specialitystring = {
                "",
                "Cardiologie",
                "Chirurgie",
                "Dermatologie",
                "Gériatrie",
                "Oncologie",
                "Pédiatrie",
                "Psychiatrie",
                "Allergologie"
            };
            speciality = new JComboBox(specialitystring);
            speciality.setBackground(new java.awt.Color(238, 238, 238));
            speciality.setFont(new java.awt.Font("Calibri", 0, 18));
            speciality.setEditable(true);
            speciality.setFocusable(false);
            panel.add(speciality);


            MyButton addbutton = new MyButton("Add");
            addbutton.addActionListener((ActionEvent evt) -> {
                if (netIsAvailable() == false) {
                    frr = frame;
                    JOptionPane.showMessageDialog(frr, "Please check your connection and try again!", "Connection Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    String UserName = userText.getText();
                    String cni = cniText.getText();
                    Date rdvDate = datechooser.getDate();
                    String t = (String) time.getSelectedItem();
                    if (t.isEmpty() == true) {
                        JOptionPane.showMessageDialog(null, "Please fill in the date");
                    } else {
                        String[] te = t.split(":");
                        int tint = Integer.parseInt(te[0]);
                        rdvDate.setHours(tint);
                        rdvDate.setSeconds(0);
                        rdvDate.setMinutes(0);
                    }
                    String specialitychosen = (String) speciality.getSelectedItem();
                    MongoClient mongoClient = MongoClients.create(Connection_mongo);
                    String docname = GetFirstDoctor(mongoClient, specialitychosen.toUpperCase());
                    try {
                        Rdv rdv1 = new Rdv(cni, UserName, rdvDate, docname);
                        if (checkRDV(mongoClient, rdv1) == true) {
                            MethodsOnDB.InsertRdv(mongoClient, rdv1);
                            JOptionPane.showMessageDialog(null, "RDV added");
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            });
            panel.add(addbutton);

            MyButton showdbbutton = new MyButton("Show RDV");
            showdbbutton.addActionListener((ActionEvent evt) -> {
                if (netIsAvailable() == false) {
                    frr = frame;
                    JOptionPane.showMessageDialog(frr, "Please check your connection and try again!", "Connection Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        showdb showd = new showdb();
                        showd.showrdvsdb();
                    } catch (IOException ex) {
                        Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            panel.add(showdbbutton);

            javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
            panel.setLayout(panelLayout);
            panelLayout.setHorizontalGroup(
                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelLayout.createSequentialGroup()
                    .addGap(86, 86, 86)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(cniLabel)
                        .addComponent(rdvlabel)
                        .addComponent(userLabel)
                        .addComponent(SpecialityLabel))
                    .addGap(62, 62, 62)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(speciality, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(userText)
                        .addComponent(datechooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(17, 17, 17)
                        .addComponent(time, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cniText))
                    .addGap(79, 79, 79)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(addbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(showdbbutton))
                    .addContainerGap(140, Short.MAX_VALUE))
            );
            panelLayout.setVerticalGroup(
                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelLayout.createSequentialGroup()
                    .addContainerGap(206, Short.MAX_VALUE)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(panelLayout.createSequentialGroup()
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(userLabel)
                                .addComponent(userText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(17, 17, 17)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cniLabel)
                                .addComponent(cniText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(rdvlabel)
                                .addComponent(datechooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(10, 10, 10)
                                .addComponent(time, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(19, 19, 19)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(SpecialityLabel)
                                .addComponent(speciality, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(180, 180, 180))
                        .addGroup(panelLayout.createSequentialGroup()
                            .addComponent(addbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(showdbbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(230, 230, 230))))
            );



            panel.validate();

        }
    }
    public void Removegestiondesrdv() {
        if (gestiondesrdvclicked == true) {
            panel.setVisible(false);
            frame.remove(panel);
        }
        gestiondesrdvclicked = false;
    }

    public void placeComponents() throws IOException {
        where = 1;
        leftpanel = new JPanel();
        frame = new MyFrame(leftpanel);


        titleLabel = new JLabel("Admin Dashboard");
        titleLabel.setBackground(new java.awt.Color(49, 153, 151));
        titleLabel.setFont(new java.awt.Font("Calibri", 1, 26)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(238, 238, 238));
        titleLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        leftpanel.add(titleLabel);


        gestiondesmedecins = new MyButton("DOCTORS");
        gestiondesmedecins.addActionListener((ActionEvent evt) -> {
            if (netIsAvailable() == false) {
                frr = frame;
                JOptionPane.showMessageDialog(frr, "Please check your connection and try again!", "Connection Error", JOptionPane.WARNING_MESSAGE);
            }
            if (gestiondespatientsclicked == true) {
                Removegestiondespatients();
            }
            if (gestiondesrdvclicked == true) {
                Removegestiondesrdv();
            }
            gestiondesmedecins();
        });
        leftpanel.add(gestiondesmedecins);


        gestiondespatients = new MyButton("PATIENTS");
        gestiondespatients.addActionListener((ActionEvent evt) -> {
            if (netIsAvailable() == false) {
                frr = frame;
                JOptionPane.showMessageDialog(frr, "Please check your connection and try again!", "Connection Error", JOptionPane.WARNING_MESSAGE);
            }
            if (gestiondesmedecinsclicked == true) {
                Removegestiondesmedecins();
            }
            if (gestiondesrdvclicked == true) {
                Removegestiondesrdv();
            }
            try {
                gestiondespatients();
            } catch (NoSuchAlgorithmException | ParseException ex) {
                Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        leftpanel.add(gestiondespatients);


        gestiondesrdv = new MyButton("RENDEZ-VOUS");
        gestiondesrdv.addActionListener((ActionEvent evt) -> {
            if (netIsAvailable() == false) {
                frr = frame;
                JOptionPane.showMessageDialog(frr, "Please check your connection and try again!", "Connection Error", JOptionPane.WARNING_MESSAGE);
            }
            if (gestiondesmedecinsclicked == true) {
                Removegestiondesmedecins();
            }
            if (gestiondespatientsclicked == true) {
                Removegestiondespatients();
            }
            gestiondesrendezvous();


        });
        leftpanel.add(gestiondesrdv);


        javax.swing.GroupLayout LeftPanelLayout = new javax.swing.GroupLayout(leftpanel);
        leftpanel.setLayout(LeftPanelLayout);
        LeftPanelLayout.setHorizontalGroup(
            LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LeftPanelLayout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addGroup(LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gestiondesmedecins, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                    .addComponent(gestiondespatients, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                    .addComponent(gestiondesrdv, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE))
                .addGap(39, 39, 39))
        );
        LeftPanelLayout.setVerticalGroup(
            LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeftPanelLayout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(gestiondesmedecins, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(gestiondespatients, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(gestiondesrdv, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(347, Short.MAX_VALUE))
        );
        frr = frame;
        LogoutButton logout = new LogoutButton(frame);
        frame.add(logout);
        frame.setVisible(true);
    }
}