package Dashboards;

import MyComponents.*;
import static GestionHopitale.MethodsOnDB.*;
import GestionHopitale.*;
import MyClasses.Dossier;
import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.logging.*;
import javax.swing.*;
import org.bson.Document;

public class DoctorDashboard extends MouseAdapter {

    String Connection_mongo = Main.Connection_mongo;
    private JLabel userLabel, cniLabel, titleLabel, messageLabel, medicLabel;
    private JTextField userText, cniText, medicText;
    private JPanel leftpanel, panel;
    private MyFrame frame;
    private JTable mytable;
    private MyButton gestiondesrdv, voirdossier, searchbutton, addbutton;
    private JTextArea messageText;
    private JScrollPane sp;
    boolean validPassword, gestiondesrdvclicked, gestiondossierclicked;
    private MongoCursor < Document > cursor;
    private final MongoClient mongoClient = MongoClients.create(Connection_mongo);
    private String usr = null;
    showdb ss = new showdb();
    // End of variables declaration


    public void gestiondossierpation(String docname) {
        if (gestiondossierclicked == false) {
            gestiondossierclicked = true;
            panel = new JPanel();
            panel.setBounds(300, 0, 724, 450);
            panel.setBackground(new Color(238, 238, 238));
            panel.setPreferredSize(new java.awt.Dimension(724, 600));
            frame.add(panel, BorderLayout.EAST);
            frame.add(panel);

            userLabel = new JLabel("Username :");
            panel.add(userLabel);
            userText = new JTextField(20);
            panel.add(userText);

            cniLabel = new JLabel("CNI :");
            panel.add(cniLabel);
            cniText = new JTextField(20);
            panel.add(cniText);

            medicLabel = new JLabel("Medicament :");
            panel.add(medicLabel);
            medicText = new JTextField(20);
            panel.add(medicText);

            messageLabel = new JLabel("Message :");
            messageText = new JTextArea(5, 20);
            messageText.setLineWrap(true);
            messageText.setBorder(BorderFactory.createLineBorder(Color.gray));



            addbutton = new MyButton("Add");
            addbutton.addActionListener((ActionEvent evt) -> {
                String UserName = userText.getText().toUpperCase();
                String cni = cniText.getText().toUpperCase();
                String medic = medicText.getText().toUpperCase();
                String msg = messageText.getText();
                try (MongoClient mongoClient = MongoClients.create(Connection_mongo)) {
                    System.out.println(docname);
                    Dossier dossier = new Dossier(cni, UserName, medic, docname, msg);
                    if (checkifFieldinCollections(mongoClient,"UserName", UserName,"Pations") != null) {
                        if (checkifFieldinCollections(mongoClient,"Cni", cni,"Pations") != null) {
                            if (getFieldbyUsername(mongoClient, UserName, "Cni","Pations").equals(cni)) {
                                MethodsOnDB.InsertDossier(mongoClient, dossier);
                                JOptionPane.showMessageDialog(null, "File added");
                            } else {
                                JOptionPane.showMessageDialog(null, "are you sure this the cni for this pation");
                            }

                        } else {
                            JOptionPane.showMessageDialog(null, "Cni not found");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Username not found");
                    }


                }

            });
            panel.add(addbutton);

            searchbutton = new MyButton("Search");
            searchbutton.addActionListener((ActionEvent evt) -> {
                try {
                    MyFrame frame2 = new MyFrame(300, 300, JFrame.DISPOSE_ON_CLOSE);
                    String opt[] = {
                        "UserName",
                        "Cni",
                        "Medic"
                    };
                    JComboBox Options = new JComboBox(opt);
                    Options.setBounds(50, 50, 200, 30);
                    Options.setFont(new java.awt.Font("Calibri", 0, 16));
                    Options.setEditable(true);
                    Options.setFocusable(false);
                    JTextField t1 = new JTextField();
                    t1.setBounds(50, 100, 200, 30);
                    frame2.add(t1);
                    frame2.add(Options);
                    frame2.setVisible(true);
                    MyButton searchbutton1 = new MyButton("Search");
                    searchbutton1.setBounds(130, 160, 100, 30);
                    searchbutton1.addActionListener((ActionEvent evt1) -> {
                        try {
                            ss.showdossierdb(docname, Options.getSelectedItem().toString(), t1.getText().toUpperCase());
                        } catch (IOException ex) {
                            Logger.getLogger(DoctorDashboard.class.getName()).log(Level.SEVERE, null, ex);
                        }



                    });
                    frame2.add(searchbutton1);
                } catch (IOException ex) {
                    Logger.getLogger(PationDashboard.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            panel.add(searchbutton);



            javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
            panel.setLayout(panelLayout);
            panelLayout.setHorizontalGroup(
                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelLayout.createSequentialGroup()
                    .addGap(94, 94, 94)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(messageLabel)
                        .addComponent(medicLabel)
                        .addComponent(cniLabel)
                        .addComponent(userLabel))
                    .addGap(57, 57, 57)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(messageText, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(medicText, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cniText, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(userText, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(106, 106, 106)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(addbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(searchbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(118, Short.MAX_VALUE))
            );
            panelLayout.setVerticalGroup(
                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                    .addContainerGap(206, Short.MAX_VALUE)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(panelLayout.createSequentialGroup()
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(userLabel)
                                .addComponent(userText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cniLabel)
                                .addComponent(cniText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(medicLabel)
                                .addComponent(medicText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(messageLabel)
                                .addComponent(messageText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(111, 111, 111))
                        .addGroup(panelLayout.createSequentialGroup()
                            .addComponent(addbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(searchbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGap(180, 180, 180))))
            );

            panel.validate();

        }
    }
    public void Removegestiondossier() {
        if (gestiondossierclicked == true) {
            panel.setVisible(false);
            frame.remove(panel);
        }
        gestiondossierclicked = false;
    }

    public void gestiondesrendezvous(String docname) {
        if (gestiondesrdvclicked == false) {
            gestiondesrdvclicked = true;
            panel = new JPanel();
            panel.setBounds(362, 60, 600, 350);
            panel.setBackground(new java.awt.Color(49, 153, 151));
            frame.add(panel, BorderLayout.EAST);
            JPanel bottompanel = new JPanel();
            bottompanel.setSize(600, 50);
            bottompanel.setBackground(new java.awt.Color(49, 153, 151));
            panel.add(bottompanel, BorderLayout.PAGE_END);
            searchbutton = new MyButton("Search");
            bottompanel.add(searchbutton);
            MyButton removebutton = new MyButton("Remove");
            bottompanel.add(removebutton);

            try (MongoClient mongoClient = MongoClients.create(this.Connection_mongo)) {
                String CollectionName = "RDV";
                String fields[] = {
                    "_id",
                    "UserName",
                    "Cni",
                    "Rendez-vous date",
                    "Doctor"
                };
                DBCollection RDVData = new DBCollection(mongoClient, CollectionName, fields, docname, "Doctor");
                mytable = new JTable(RDVData.GetData(), RDVData.GetColumnsNames());
                mytable.setCellSelectionEnabled(true);
                sp = new JScrollPane();
                sp.setViewportView(mytable);
                panel.add(sp);
            } catch (Exception e) {
                System.out.println(e.toString());
            }



            javax.swing.GroupLayout bottompanelLayout = new javax.swing.GroupLayout(bottompanel);
            bottompanel.setLayout(bottompanelLayout);
            bottompanelLayout.setHorizontalGroup(
                bottompanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(bottompanelLayout.createSequentialGroup()
                    .addGap(18, 18, 18)
                    .addComponent(removebutton, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(37, 37, 37))
            );
            bottompanelLayout.setVerticalGroup(
                bottompanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(bottompanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(bottompanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(removebutton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(searchbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

            javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
            panel.setLayout(panelLayout);
            panelLayout.setHorizontalGroup(
                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(sp, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                .addComponent(bottompanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            panelLayout.setVerticalGroup(
                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelLayout.createSequentialGroup()
                    .addComponent(sp, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, 0)
                    .addComponent(bottompanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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


    private static void AddStatus(MongoClient mongoClient, String user, boolean status) {
        user = user.toUpperCase();
        MongoCollection < Document > collection = mongoClient.getDatabase("Hospital").getCollection("Doctors");
        BasicDBObject filter = new BasicDBObject("UserName", user);
        BasicDBObject updateQuery = new BasicDBObject();
        updateQuery.append("$set", new BasicDBObject("Status", status));
        collection.updateOne(filter, updateQuery);
    }

    public void placeComponents(String user) throws IOException {
        usr = user;
        MongoClient mongoClient = MongoClients.create(Connection_mongo);
        AddStatus(mongoClient, usr, true);


        leftpanel = new JPanel();
        frame = new MyFrame(leftpanel);
        frame.addMouseMotionListener(this);

        titleLabel = new JLabel();
        titleLabel.setBackground(new java.awt.Color(49, 153, 151));
        titleLabel.setFont(new java.awt.Font("Calibri", 1, 22)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(238, 238, 238));

        String docname = getFieldbyUsername(mongoClient, user.toUpperCase(), "L_name","Doctors") + " " + getFieldbyUsername(mongoClient, user.toUpperCase(), "F_name","Doctors");
        titleLabel.setText(docname);
        titleLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        leftpanel.add(titleLabel);

        gestiondesrdv = new MyButton("RENDEZ-VOUS");
        gestiondesrdv.addActionListener((ActionEvent evt) -> {
            if (gestiondossierclicked == true) {
                Removegestiondossier();
            }
            gestiondesrendezvous(docname);;
        });
        leftpanel.add(gestiondesrdv);

        voirdossier = new MyButton("Dossier de Pation");
        voirdossier.addActionListener((ActionEvent evt) -> {
            if (gestiondesrdvclicked == true) {
                Removegestiondesrdv();
            }
            gestiondossierpation(docname);

        });
        leftpanel.add(voirdossier);

        javax.swing.GroupLayout LeftPanelLayout = new javax.swing.GroupLayout(leftpanel);
        leftpanel.setLayout(LeftPanelLayout);
        LeftPanelLayout.setHorizontalGroup(
            LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LeftPanelLayout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addGroup(LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gestiondesrdv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(voirdossier, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE))
                .addGap(39, 39, 39))
        );
        LeftPanelLayout.setVerticalGroup(
            LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeftPanelLayout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(gestiondesrdv, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(voirdossier, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(347, Short.MAX_VALUE))
        );


        LogoutButton logout = new LogoutButton(frame);
        frame.add(logout);
        frame.setVisible(true);
    }

    @Override public void mouseMoved(MouseEvent event) {
        usr = usr.toUpperCase();
        try {
            MongoClient mongoClient = MongoClients.create(Connection_mongo);
            AddStatus(mongoClient, usr, true);
            System.out.println(usr);
        } catch (Exception e) {
            System.out.println("error");
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }


}