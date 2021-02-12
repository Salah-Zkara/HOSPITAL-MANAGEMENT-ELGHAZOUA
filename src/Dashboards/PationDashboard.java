package Dashboards;

import GestionHopitale.*;
import MyComponents.*;
import MyClasses.Rdv;
import static GestionHopitale.MethodsOnDB.*;
import GestionHopitale.DBCollection;
import static GestionHopitale.Main.checkRDV;
import GestionHopitale.showdb;
import com.mongodb.client.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.bson.Document;

public class PationDashboard {
    
    String Connection_mongo = Main.Connection_mongo;
    private JLabel rdvlabel, SpecialityLabel, titleLabel, timelabel;
    private JPanel leftpanel, panel;
    private MyFrame frame;
    private MyButton gestiondesrdv, voirlesrendezvous, searchbutton, removebutton;
    private LogoutButton logout;
    private JTable mytable;
    private JDateChooser datechooser;
    private JComboBox speciality, time;
    private MongoCursor < Document > cursor;
    private JScrollPane sp;
    boolean validPassword, gestiondesrdvclicked, gestiondossierclicked;
    private final MongoClient mongoClient = MongoClients.create(Connection_mongo);
    // End of variables declaration
    

    public void gestiondesrendezvous(String UserName, String cni) {
        if (gestiondesrdvclicked == false) {
            gestiondesrdvclicked = true;
            panel = new JPanel();
            panel.setBounds(300, 0, 724, 450);
            panel.setBackground(new Color(238, 238, 238));
            panel.setPreferredSize(new java.awt.Dimension(724, 450));
            frame.add(panel, BorderLayout.EAST);
            frame.add(panel);

            rdvlabel = new JLabel("Rendez-vous");
            panel.add(rdvlabel);
            datechooser = new JDateChooser();

            timelabel = new JLabel("Time");
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

            SpecialityLabel = new JLabel("Doctor speciality :");
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


            });
            panel.add(addbutton);

            MyButton showdbbutton = new MyButton("Show RDV");
            showdbbutton.addActionListener((ActionEvent evt) -> {
                try {
                    String CollectionName = "RDV";
                    String fields[] = {
                        "_id",
                        "UserName",
                        "Cni",
                        "Rendez-vous date",
                        "Doctor"
                    };
                    DBCollection RDVData = new DBCollection(mongoClient, CollectionName, fields, UserName, "UserName");
                    JTable mytable = new JTable(RDVData.GetData(), RDVData.GetColumnsNames());
                    mytable.setCellSelectionEnabled(true);
                    showdb showd = new showdb();
                    showd.TableFrame(mytable, 2);

                } catch (IOException e) {
                    System.out.println(e.toString());
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
                        .addComponent(rdvlabel)
                        .addComponent(timelabel)
                        .addComponent(SpecialityLabel)
                    )
                    .addGap(62, 62, 62)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(speciality, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(datechooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(17, 17, 17)
                        .addComponent(time, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                                .addComponent(rdvlabel)
                                .addComponent(datechooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(17, 17, 17)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(timelabel)
                                .addComponent(time, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(SpecialityLabel)
                                .addComponent(speciality, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(180, 180, 180))
                        .addGroup(panelLayout.createSequentialGroup()
                            .addComponent(addbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(showdbbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(200, 200, 200))))
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
    public void gestiondossier(String UserName, String cni) {
        if (gestiondossierclicked == false) {
            gestiondossierclicked = true;

            panel = new JPanel();
            panel.setBounds(362, 60, 600, 350);
            panel.setBackground(new java.awt.Color(49, 153, 151));
            frame.add(panel, BorderLayout.EAST);
            JPanel bottompanel = new JPanel();
            bottompanel.setSize(600, 50);
            bottompanel.setBackground(new java.awt.Color(49, 153, 151));
            panel.add(bottompanel, BorderLayout.PAGE_END);


            try {
                String CollectionName = "Dossiers";
                String fields[] = {
                    "_id",
                    "Medic",
                    "Message",
                    "Doctor"
                };
                DBCollection DossierData = new DBCollection(mongoClient, CollectionName, fields, UserName, "UserName");
                mytable = new JTable(DossierData.GetData(), DossierData.GetColumnsNames());
                mytable.setCellSelectionEnabled(true);
                sp = new JScrollPane();
                sp.setViewportView(mytable);
                panel.add(sp);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
            searchbutton = new MyButton("Search");
            searchbutton.addActionListener((ActionEvent evt) -> {
                try {
                    MyFrame frame2 = new MyFrame(300, 300, JFrame.DISPOSE_ON_CLOSE);
                    String opt[] = {
                        "Medic",
                        "Doctor"
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
                        String fields[] = {
                            "_id",
                            "Medic",
                            "Message",
                            "Doctor"
                        };
                        try {
                            cursor = MethodsOnDB.GetDossierBy(mongoClient, Options.getSelectedItem().toString(), t1.getText().toUpperCase());
                            DefaultTableModel model = (DefaultTableModel) mytable.getModel();
                            int rowCount = model.getRowCount();
                            for (int i = rowCount - 1; i >= 0; i--) {
                                model.removeRow(i);
                            }
                            while (cursor.hasNext()) {
                                Document row = cursor.next();
                                Object[] obj = new Object[fields.length];
                                for (int i = 0; i < fields.length; i++) {
                                    obj[i] = row.get(fields[i]);
                                }
                                model.addRow(obj);

                                for (int i = 0; i < obj.length; i++) {
                                    System.out.println("obj " + i + ": " + obj[i]);
                                }

                                System.out.println(model);
                                mytable.setModel(model);
                            }

                        } catch (Exception e) {
                            System.out.println(e.toString());
                        }
                    });
                    frame2.add(searchbutton1);
                } catch (IOException ex) {
                    Logger.getLogger(PationDashboard.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            bottompanel.add(searchbutton);


            javax.swing.GroupLayout bottompanelLayout = new javax.swing.GroupLayout(bottompanel);
            bottompanel.setLayout(bottompanelLayout);
            bottompanelLayout.setHorizontalGroup(
                bottompanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(bottompanelLayout.createSequentialGroup()
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(37, 37, 37))
            );
            bottompanelLayout.setVerticalGroup(
                bottompanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(bottompanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(bottompanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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

    public void Removegestiondossier() {
        if (gestiondossierclicked == true) {
            panel.setVisible(false);
            frame.remove(panel);
        }
        gestiondossierclicked = false;
    }


    public void placeComponents(String user) throws IOException {
        leftpanel = new JPanel();
        frame = new MyFrame(leftpanel);

        titleLabel = new JLabel();
        titleLabel.setBackground(new java.awt.Color(49, 153, 151));
        titleLabel.setFont(new java.awt.Font("Calibri", 1, 22));
        titleLabel.setForeground(new java.awt.Color(238, 238, 238));
        titleLabel.setText(getFieldbyUsername(mongoClient, user.toUpperCase(), "F_name","Pations") + " " + getFieldbyUsername(mongoClient, user.toUpperCase(), "L_name","Pations"));
        titleLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        leftpanel.add(titleLabel);
        System.out.println(user);
        gestiondesrdv = new MyButton("RENDEZ-VOUS");
        gestiondesrdv.addActionListener((ActionEvent evt) -> {
            if (gestiondossierclicked == true) {
                Removegestiondossier();
            }
            gestiondesrendezvous(user, getFieldbyUsername(mongoClient, user, "Cni","Pations"));


        });
        leftpanel.add(gestiondesrdv);

        voirlesrendezvous = new MyButton("Dossier");
        voirlesrendezvous.addActionListener((ActionEvent evt) -> {
            if (gestiondesrdvclicked == true) {
                Removegestiondesrdv();
            }
            gestiondossier(user, getFieldbyUsername(mongoClient, user, "Cni","Pations"));

        });
        leftpanel.add(voirlesrendezvous);


        javax.swing.GroupLayout LeftPanelLayout = new javax.swing.GroupLayout(leftpanel);
        leftpanel.setLayout(LeftPanelLayout);
        LeftPanelLayout.setHorizontalGroup(
            LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LeftPanelLayout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addGroup(LeftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gestiondesrdv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(voirlesrendezvous, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE))
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
                .addComponent(voirlesrendezvous, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(347, Short.MAX_VALUE))
        );


        logout = new LogoutButton(frame);
        frame.add(logout);
        frame.setVisible(true);
    }

}