package GestionHopitale;

import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import org.bson.Document;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;

public class ConnectionAuth {
    public static void WriteF(Scanner MySc) throws IOException {
        System.out.println("Provide Your Mongo DataBase Information ex('mongodb+srv://USER:PASS@hospitalcluster.dlcev.mongodb.net/hospital?retryWrites=true&w=majority' //// 'mongodb://localhost:27017')  :");
        FileWriter myWriter = new FileWriter("connection.txt");
        myWriter.write(MySc.next());
        myWriter.close();
    }
    public static String ReadF(Scanner MySc) throws IOException {
        while (true){
            File MyFile = new File("connection.txt");
            Scanner myReader = new Scanner(MyFile);
            if (myReader.hasNext()){
                String Data = myReader.next();
                myReader.close();
                return Data;
            }
            WriteF(MySc);
        }
    }
    public boolean check(String Connection_mongo,Scanner Mysc){
        MongoClient mongoClient = MongoClients.create(Connection_mongo);
        MongoCursor<String> ColListe =  mongoClient.getDatabase("Hospital").listCollectionNames().iterator();
        boolean create = true;
        while (ColListe.hasNext()){
            if (ColListe.next().equals("Admins")) {
                create = false;
                break;
            }
        }
        if (create == true) {
            System.out.println("IN prt");
            mongoClient.getDatabase("Hospital").createCollection("Admins");
        }
        System.out.println("befor");
        MongoCollection<Document> collection = mongoClient.getDatabase("Hospital").getCollection("Admins");


        System.out.println(collection.find().);
        System.out.println("after");
        if (create == false){

            Document Doc = collection.find().first();
            if (Doc.containsKey("UserName") || Doc.containsKey("Password")) return true;
        }


        System.out.print("Enter UserName for admin account : ");
        Document admin = new Document("UserName",Mysc.next());
        System.out.print("Enter Password for admin account : ");
        admin.put("Password",Mysc.next());
        collection.insertOne(admin);
        return true;
    }

}
