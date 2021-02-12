package MyClasses;

import GestionHopitale.Main;
import com.mongodb.client.*;
import org.bson.Document;


public class Dossier {
    private String Connection_mongo = Main.Connection_mongo;

    private static int DynId;
    private int Id;
    String Cni;
    String UserName;
    String medic;
    String doctor;
    String message;
    
    public Dossier(String cni, String UN, String medic, String doctor,String message) {
            try (MongoClient mongoClient = MongoClients.create(this.Connection_mongo) ) {
            MongoCollection<Document> rdv = mongoClient.getDatabase("Hospital").getCollection("Dossiers");
            DynId = (int) rdv.countDocuments();
            this.Id = DynId + 1;
            this.UserName = UN.toUpperCase();
            this.Cni = cni.toUpperCase();
            this.doctor = doctor.toUpperCase();
            this.message = message;
            this.medic = medic.toUpperCase();
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }

    //setters
    public void SetDoctor(String doctor){
        this.doctor = doctor;
    }
    public void SetCni(String cni){
        this.Cni = cni;
    }
    public void SetMedic(String medic){
        this.medic = medic;
    }
    public void SetUserName(String UN){
        this.UserName = UN;
    }
    public void SetMessage(String message){
        this.message = message;
    }
    public void SetId(int Id){
        this.Id = Id;
    }
    


    //getters
    public String GetDoctor(){
        return this.doctor ;
    }
    public String GetCni(){
        return this.Cni;
    }
    public String GetMedic(){
        return this.medic;
    }
    public String GetUserName(){
        return this.UserName;
    }
    public String GetMessage(){
        return this.message;
    }
    public int GetId(){
        return this.Id;
    }
    public int getIdMax(){
        return DynId;
    }
    
    

}
