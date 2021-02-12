package MyClasses;

import GestionHopitale.Main;
import com.mongodb.client.*;
import org.bson.Document;
import java.util.Date;

public class Rdv {
    private String Connection_mongo = Main.Connection_mongo;
    private static int DynId;
    private int Id;
    String Cni;
    String UserName;
    Date Date;
    String doctor;
    
    public Rdv(String cni, String UN, Date rdvDate, String doctor) {
        try (MongoClient mongoClient = MongoClients.create(this.Connection_mongo) ) {
            MongoCollection<Document> rdv = mongoClient.getDatabase("Hospital").getCollection("RDV");
            DynId = (int) rdv.countDocuments();
            this.Id = DynId + 1;
            this.Date = rdvDate;
            this.UserName = UN.toUpperCase();
            this.Cni = cni.toUpperCase();
            this.doctor = doctor.toUpperCase();
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
    public void SetDate(Date rdvDate){
        this.Date = rdvDate;
    }
    public void SetUserName(String UN){
        this.UserName = UN;
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
    public Date GetDate(){
        return this.Date;
    }
    public String GetUserName(){
        return this.UserName;
    }
    public int GetId(){
        return this.Id;
    }
    public int getIdMax(){
        return DynId;
    }
}
