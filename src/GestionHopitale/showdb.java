package GestionHopitale;

import MyComponents.*;
import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import org.bson.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.file.*;
import java.util.Vector;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.table.*;

public class showdb {
    
    // Variables declaration
    private static String DataRdv = "";
    private static String DataPation = "";
    private MongoCursor < Document > cursor;
    //Local//private String Connection_mongo ="mongodb://localhost:27017";
    private String Connection_mongo =Main.Connection_mongo;
    // End of variables declaration 
    
    public void showdb() throws IOException {
        showpatientsdb();
    }

    public static String GetSelectedRow(JTable table) {
        String Data = "";
        try {
            for (int i = 0; i < table.getColumnCount(); i++) {
                if (table.getColumnName(i) == "Cni") Data = (String) table.getValueAt(table.getSelectedRow(), i);
            }
        } catch (Exception e) {
            Data = "NO";
        } finally {
            return Data;
        }
    }
    private static int GetSelectedRow_Id(JTable table) {
        int Data = 0;
        try {
            for (int i = 0; i < table.getColumnCount(); i++) {
                if (table.getColumnName(i) == "_id") Data = Integer.parseInt((String) table.getValueAt(table.getSelectedRow(), i));

            }
        } catch (Exception e) {
            System.out.println(e.toString());
            Data = -1;
        } finally {
            return Data;
        }
    }


    public void TableFrame(JTable table, int a) throws IOException {

        File currentDir = new File(".");
        String basePath = currentDir.getCanonicalPath();
        Path imgPath = Paths.get(basePath, "src", "main", "java", "Images", "hosp.png");
        JFrame frame = new JFrame("ElGuezoua Hospital");
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(true);
        ImageIcon img = new ImageIcon(imgPath.toString());
        frame.setIconImage(img.getImage());
        JScrollPane sp = new JScrollPane();
        sp.setViewportView(table);
        frame.add(sp);
        frame.setVisible(true);


        JPanel panel = new JPanel();
        panel.setBackground(new java.awt.Color(49, 153, 151));
        frame.add(panel, BorderLayout.PAGE_END);

        MyButton modifiebutton = new MyButton("Modify");
        modifiebutton.addActionListener((ActionEvent evt) -> {
            try (MongoClient mongoClient = MongoClients.create(this.Connection_mongo)) {
                String[] fields = null;
                switch (a) {
                    case 0:
                        {
                            String[] s = {
                                "F_name",
                                "L_name",
                                "UserName",
                                "Cni",
                                "DateOfBirth",
                                "Speciality",
                                "Status"
                            };
                            fields = s;
                            break;
                        }
                    case 1:
                        {
                            String[] s = {
                                "F_name",
                                "L_name",
                                "UserName",
                                "Cni",
                                "DateOfBirth",
                                "Phone"
                            };
                            fields = s;
                            break;
                        }
                    case 2:
                        {
                            String[] s = {
                                "_id",
                                "UserName",
                                "Cni",
                                "Rendez-vous date",
                                "Doctor"
                            };
                            fields = s;
                            break;
                        }
                    default:
                        break;
                }

                DefaultTableModel model = (DefaultTableModel) table.getModel();
                Vector < Vector > vect = model.getDataVector();
                int[] selectedR = table.getSelectedRows();
                for (int i = 0; i < selectedR.length; i++) {
                    Vector line = vect.get(selectedR[i]);
                    BasicDBObject filter = new BasicDBObject();
                    BasicDBObject doc = null;

                    switch (a) {
                        case 0:
                            {
                                doc = new BasicDBObject();
                                String cni = line.get(3).toString().toUpperCase();
                                filter = new BasicDBObject();
                                filter.append("Cni", cni);
                                doc.append("F_name", line.get(0).toString().toUpperCase());
                                doc.append("L_name", line.get(1).toString().toUpperCase());
                                doc.append("UserName", line.get(2).toString().toUpperCase());
                                doc.append("Cni", line.get(3).toString().toUpperCase());
                                doc.append("DateOfBirth", line.get(4));
                                doc.append("Speciality", line.get(5).toString().toUpperCase());
                                break;
                            }
                        case 1:
                            {
                                doc = new BasicDBObject();
                                String cni = line.get(3).toString().toUpperCase();
                                filter = new BasicDBObject();
                                filter.append("Cni", cni);
                                doc.append("F_name", line.get(0).toString().toUpperCase());
                                doc.append("L_name", line.get(1).toString().toUpperCase());
                                doc.append("UserName", line.get(2).toString().toUpperCase());
                                doc.append("Cni", line.get(3).toString().toUpperCase());
                                doc.append("DateOfBirth", line.get(4));
                                doc.append("Phone", line.get(5).toString().toUpperCase());
                                break;
                            }
                        case 2:
                            {
                                doc = new BasicDBObject();
                                int id = Integer.parseInt(line.get(0).toString());
                                filter = new BasicDBObject();
                                filter.append("_id", id);
                                doc.append("UserName", line.get(1).toString().toUpperCase());
                                doc.append("Cni", line.get(2).toString().toUpperCase());
                                doc.append("Rendez-vous date", line.get(3));
                                doc.append("Doctor", line.get(4).toString().toUpperCase());
                                break;
                            }
                        default:
                            break;
                    }


                    BasicDBObject updateQuery = new BasicDBObject();
                    updateQuery.append("$set", doc);
                    switch (a) {
                        case 0:
                            MethodsOnDB.UpdateDoctorDB(mongoClient, filter, updateQuery);
                            JOptionPane.showMessageDialog(null, "Element modifier");
                            break;
                        case 1:
                            MethodsOnDB.UpdatePationDB(mongoClient, filter, updateQuery);
                            JOptionPane.showMessageDialog(null, "Element modifier");
                            break;
                        case 2:
                            MethodsOnDB.UpdateRdvDB(mongoClient, filter, updateQuery);
                            JOptionPane.showMessageDialog(null, "Element modifier");
                            break;
                        default:
                            break;
                    }
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }

        });
        panel.add(modifiebutton);


        MyButton removebutton = new MyButton("Remove");
        removebutton.addActionListener((ActionEvent evt) -> {
            String Data = "NO";
            int DataId = 0;
            if (a == 0 || a == 1) {
                Data = GetSelectedRow(table);
            } else if (a == 2) {
                DataId = GetSelectedRow_Id(table);
                System.out.println(DataId);
                if (DataId == -1) Data = "NO";
                else Data = "";
            }

            if ("NO".equals(Data)) {
                JOptionPane.showMessageDialog(null, "Please Select a specific row!");
            } else {
                try (MongoClient mongoClient = MongoClients.create(this.Connection_mongo)) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.removeRow(table.getSelectedRow());
                    switch (a) {
                        case 0:
                            MethodsOnDB.deleteDoctor(mongoClient, Data);
                            break;
                        case 1:
                            MethodsOnDB.deletePation(mongoClient, Data);
                            break;
                        case 2:
                            MethodsOnDB.deleteRDV(mongoClient, DataId);
                            break;
                        default:
                            break;
                    }
                    table.setModel(model);
                    SwingUtilities.updateComponentTreeUI(frame);

                } catch (Exception e) {
                    System.out.println(e.toString());
                }

            }
        });
        panel.add(removebutton);

        MyButton searchbutton = new MyButton("Search");
        panel.add(searchbutton);
        searchbutton.addActionListener((ActionEvent evt) -> {
            try {
                MyFrame frame2 = new MyFrame(300, 300, JFrame.DISPOSE_ON_CLOSE);
                String opt[] = null;
                if (a == 0) {
                    String[] s = {
                        "F_name",
                        "L_name",
                        "UserName",
                        "Cni",
                        "DateOfBirth",
                        "Speciality",
                        "Status"
                    };
                    opt = s;
                } else if (a == 1) {
                    String[] s = {
                        "F_name",
                        "L_name",
                        "UserName",
                        "Cni",
                        "DateOfBirth",
                        "Phone"
                    };
                    opt = s;
                } else if (a == 2) {
                    String[] s = {
                        "_id",
                        "UserName",
                        "Cni",
                        "Rendez-vous date",
                        "Doctor"
                    };
                    opt = s;
                }
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
                    String fields[] = null;
                    try (MongoClient mongoClient = MongoClients.create(this.Connection_mongo)) {
                        if (a == 0) {
                            cursor = MethodsOnDB.GetDoctorBy(mongoClient, Options.getSelectedItem().toString(), t1.getText().toUpperCase());
                            String f[] = {
                                "F_name",
                                "L_name",
                                "UserName",
                                "Cni",
                                "DateOfBirth",
                                "Speciality",
                                "Status"
                            };
                            fields = f;
                        } else if (a == 1) {
                            cursor = MethodsOnDB.GetPationBy(mongoClient, Options.getSelectedItem().toString(), t1.getText().toUpperCase());
                            String f[] = {
                                "F_name",
                                "L_name",
                                "UserName",
                                "Cni",
                                "DateOfBirth",
                                "Phone"
                            };
                            fields = f;
                        } else if (a == 2) {
                            cursor = MethodsOnDB.GetRdvBy(mongoClient, Options.getSelectedItem().toString(), t1.getText().toUpperCase());
                            String f[] = {
                                "_id",
                                "UserName",
                                "Cni",
                                "Rendez-vous date",
                                "Doctor"
                            };
                            fields = f;
                        }
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
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
                            table.setModel(model);
                        }





                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }

                });


                frame2.add(searchbutton1);
            } catch (IOException ex) {
                Logger.getLogger(showdb.class.getName()).log(Level.SEVERE, null, ex);
            }


        });


        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(modifiebutton, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(removebutton, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(searchbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(modifiebutton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(removebutton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

    }


    public void showmedecinsdb() throws IOException {

        try (MongoClient mongoClient = MongoClients.create(this.Connection_mongo)) {
            String CollectionName = "Doctors";
            String fields[] = {
                "F_name",
                "L_name",
                "UserName",
                "Cni",
                "DateOfBirth",
                "Speciality",
                "Status"
            };
            DBCollection DoctorData = new DBCollection(mongoClient, CollectionName, fields);
            JTable mytable = new JTable(DoctorData.GetData(), DoctorData.GetColumnsNames());
            mytable.setCellSelectionEnabled(true);
            TableFrame(mytable, 0);

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public void showrdvsdb() throws IOException {

        try (MongoClient mongoClient = MongoClients.create(this.Connection_mongo)) {
            String CollectionName = "RDV";
            String fields[] = {
                "_id",
                "UserName",
                "Cni",
                "Rendez-vous date",
                "Doctor"
            };
            DBCollection RDVData = new DBCollection(mongoClient, CollectionName, fields);
            JTable mytable = new JTable(RDVData.GetData(), RDVData.GetColumnsNames());
            mytable.setCellSelectionEnabled(true);
            TableFrame(mytable, 2);

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public void showdossierdb(String docname, String optselected, String textt1) throws IOException {

        try (MongoClient mongoClient = MongoClients.create(this.Connection_mongo)) {
            String CollectionName = "Dossiers";
            String fields[] = {
                "_id",
                "UserName",
                "Cni",
                "Medic",
                "Message",
                "Doctor"
            };
            DBCollection RDVData = new DBCollection(mongoClient, CollectionName, fields, docname, "Doctor");
            JTable mytable = new JTable(RDVData.GetData(), RDVData.GetColumnsNames());
            mytable.setCellSelectionEnabled(true);
            TableFrame(mytable, -1);
            try {
                cursor = MethodsOnDB.GetDossierBy(mongoClient, optselected, textt1);
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


        }
    }


    public void showpatientsdb() throws IOException {


        try (MongoClient mongoClient = MongoClients.create(this.Connection_mongo)) {
            String CollectionName = "Pations";
            String fields[] = {
                "F_name",
                "L_name",
                "UserName",
                "Cni",
                "DateOfBirth",
                "Phone"
            };
            DBCollection PationData = new DBCollection(mongoClient, CollectionName, fields);
            JTable mytable = new JTable(PationData.GetData(), PationData.GetColumnsNames());
            mytable.setCellSelectionEnabled(true);
            TableFrame(mytable, 1);

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}