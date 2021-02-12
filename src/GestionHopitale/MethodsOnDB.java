package GestionHopitale;

import MyClasses.*;
import com.mongodb.BasicDBObject;
import org.bson.Document;
import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import java.util.*;
import javax.swing.JOptionPane;

public class MethodsOnDB {

    public static String getFieldbyUsername(MongoClient mongoClient, String name, String field,String Collection) {
        var adminsDB = mongoClient.getDatabase("Hospital").getCollection(Collection);
        Document admin = adminsDB.find(new Document("UserName", name)).first();
        return admin.get(field).toString();
    }

    public static Object checkifFieldinCollections(MongoClient mongoClient,String field, String name,String Collection) {
        var adminsDB = mongoClient.getDatabase("Hospital").getCollection(Collection);
        Document admin = adminsDB.find(new Document(field, name)).first();
        return admin;
    }
    
    public static Object checkifRDVinRDVs(MongoClient mongoClient, Date rdvDate) {
        var RDVsDB = mongoClient.getDatabase("Hospital").getCollection("RDV");
        Document rdv = RDVsDB.find(new Document("Rendez-vous date", rdvDate)).first();
        return rdv;
    }
    static void deletePation(MongoClient mongoClient, String cni) {
        var PationsDB = mongoClient.getDatabase("Hospital").getCollection("Pations");
        BasicDBObject theQuery = new BasicDBObject();
        theQuery.put("Cni", cni);
        DeleteResult result = PationsDB.deleteOne(theQuery);
    }

    static void deleteDoctor(MongoClient mongoClient, String cni) {
        var DoctorsDB = mongoClient.getDatabase("Hospital").getCollection("Doctors");
        BasicDBObject theQuery = new BasicDBObject();
        theQuery.put("Cni", cni);
        DeleteResult result = DoctorsDB.deleteOne(theQuery);
    }

    public static List < Object > GetDoctorFields_cni(MongoClient mongoClient, String cni, String fields[]) {
        var doctorDB = mongoClient.getDatabase("Hospital").getCollection("Doctors");
        Document doctor1 = doctorDB.find(new Document("Cni", cni.toUpperCase())).first();
        List < Object > result = new ArrayList < Object > ();
        for (int i = 0; i < fields.length; i++) {
            result.add(doctor1.get(fields[i]));
        }
        return result;
    }

    public static String GetFirstDoctor(MongoClient mongoClient, String speciality) {
        var doctorDB = mongoClient.getDatabase("Hospital").getCollection("Doctors");
        Document doctor = doctorDB.find(new Document("Speciality", speciality)).first();
        String docname = "";
        if (doctor == null) {
            JOptionPane.showMessageDialog(null, "Sorry, we don't have doctors available for this speciality");
        } else {
            docname = (doctor.get("L_name").toString().concat(" ")).concat(doctor.get("F_name").toString());
        }
        return docname;
    }


    private static Document CreateRdv(Rdv rdv) {
        Document doc = new Document();
        doc.append("_id", rdv.GetId());
        doc.append("Rendez-vous date", rdv.GetDate());
        doc.append("Cni", rdv.GetCni());
        doc.append("UserName", rdv.GetUserName());
        doc.append("Doctor", rdv.GetDoctor());
        return doc;
    }

    public static void InsertRdv(MongoClient mongoClient, Rdv rdv) {
        Document DocRdv = CreateRdv(rdv);
        var rdvDB = mongoClient.getDatabase("Hospital").getCollection("RDV");
        rdvDB.insertOne(DocRdv);
    }

    private static Document CreatePation(Pation pation) {
        Document doc = new Document();
        doc.append("_id", pation.GetCni());
        doc.append("F_name", pation.GetFirstName());
        doc.append("L_name", pation.GetLastName());
        doc.append("DateOfBirth", pation.GetDateOfBirth());
        doc.append("Cni", pation.GetCni());
        doc.append("UserName", pation.GetUserName());
        doc.append("Password", pation.GetPassword());
        doc.append("Phone", pation.GetPhone());
        return doc;
    }

    public static void InsertPation(MongoClient mongoClient, Pation pation) {
        Document DocPation = CreatePation(pation);
        var pationDB = mongoClient.getDatabase("Hospital").getCollection("Pations");
        pationDB.insertOne(DocPation);
    }

    private static Document CreateDoctor(Doctor doctor) {
        Document doc = new Document();
        doc.append("_id", doctor.GetCni());
        doc.append("F_name", doctor.GetFirstName());
        doc.append("L_name", doctor.GetLastName());
        doc.append("HireDate", doctor.GetHireDate());
        doc.append("DateOfBirth", doctor.GetDateOfBirth());
        doc.append("UserName", doctor.GetUserName());
        doc.append("Password", doctor.GetPassword());
        doc.append("Cni", doctor.GetCni());
        doc.append("Speciality", doctor.GetSpeciality());
        doc.append("Status", doctor.GetStatus());
        return doc;
    }

    public static void InsertDoctor(MongoClient mongoClient, Doctor doctor) {
        Document DocDoctor = CreateDoctor(doctor);
        var doctorDB = mongoClient.getDatabase("Hospital").getCollection("Doctors");
        doctorDB.insertOne(DocDoctor);
    }
    private static Document CreateDossier(Dossier dossier) {
        Document doc = new Document();
        doc.append("_id", dossier.GetId());
        doc.append("UserName", dossier.GetUserName());
        doc.append("Cni", dossier.GetCni());
        doc.append("Medic", dossier.GetMedic());
        doc.append("Message", dossier.GetMessage());
        doc.append("Doctor", dossier.GetDoctor());
        return doc;
    }

    public static void InsertDossier(MongoClient mongoClient, Dossier dossier) {
        Document DocDossier = CreateDossier(dossier);
        var dossierDB = mongoClient.getDatabase("Hospital").getCollection("Dossiers");
        dossierDB.insertOne(DocDossier);
    }

    static void UpdateDoctor(MongoClient mongoClient, Document filter, Doctor NewDoctor) {
        Document DocDoctor = CreateDoctor(NewDoctor);
        var doctorDB = mongoClient.getDatabase("Hospital").getCollection("Doctors");
        doctorDB.replaceOne(filter, DocDoctor);
    }


    static void DropCollection(MongoClient mongoClient, String CollectionName, Document where) {
        mongoClient.getDatabase("Hospital").getCollection(CollectionName).deleteMany(where);
    }

    static void deleteRDV(MongoClient mongoClient, int data) {
        var DoctorsDB = mongoClient.getDatabase("Hospital").getCollection("RDV");
        BasicDBObject theQuery = new BasicDBObject();
        theQuery.put("_id", data);
        DeleteResult result = DoctorsDB.deleteOne(theQuery);
    }

    static MongoCursor < Document > GetDoctorBy(MongoClient mongoClient, String field, String Value) {
        MongoCollection < Document > collection = mongoClient.getDatabase("Hospital").getCollection("Doctors");
        BasicDBObject theQuery = new BasicDBObject();
        theQuery.put(field, Value);
        MongoCursor < Document > cursor = collection.find(theQuery).iterator();
        return cursor;
    }
    public static MongoCursor < Document > GetDossierBy(MongoClient mongoClient, String field, String Value) {
        MongoCollection < Document > collection = mongoClient.getDatabase("Hospital").getCollection("Dossiers");
        BasicDBObject theQuery = new BasicDBObject();
        theQuery.put(field, Value);
        MongoCursor < Document > cursor = collection.find(theQuery).iterator();
        return cursor;
    }
    static MongoCursor < Document > GetPationBy(MongoClient mongoClient, String field, String Value) {
        MongoCollection < Document > collection = mongoClient.getDatabase("Hospital").getCollection("Pations");
        BasicDBObject theQuery = new BasicDBObject();
        theQuery.put(field, Value);
        MongoCursor < Document > cursor = collection.find(theQuery).iterator();
        return cursor;
    }
    static MongoCursor < Document > GetRdvBy(MongoClient mongoClient, String field, String Value) {

        boolean numeric = true;

        try {
            Double num = Double.parseDouble(Value);
        } catch (NumberFormatException e) {
            numeric = false;
        }


        MongoCollection < Document > collection = mongoClient.getDatabase("Hospital").getCollection("RDV");
        BasicDBObject theQuery = new BasicDBObject();

        if (numeric) theQuery.put(field, Integer.parseInt(Value));

        else theQuery.put(field, Value);

        MongoCursor < Document > cursor = collection.find(theQuery).iterator();
        return cursor;
    }

    public static void UpdateDoctorDB(MongoClient mongoClient, BasicDBObject filter, BasicDBObject NewVal) {
        MongoCollection < Document > collection = mongoClient.getDatabase("Hospital").getCollection("Doctors");
        collection.updateOne(filter, NewVal);
    }
    static void UpdatePationDB(MongoClient mongoClient, BasicDBObject filter, BasicDBObject NewVal) {
        MongoCollection < Document > collection = mongoClient.getDatabase("Hospital").getCollection("Pations");
        collection.updateOne(filter, NewVal);
    }
    public static void UpdateRdvDB(MongoClient mongoClient, BasicDBObject filter, BasicDBObject NewVal) {
        MongoCollection < Document > collection = mongoClient.getDatabase("Hospital").getCollection("RDV");
        collection.updateOne(filter, NewVal);
    }

}