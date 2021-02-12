package GestionHopitale;

import com.mongodb.client.*;
import org.bson.Document;
import java.util.Vector;

public class DBCollection {
    
    // Variables declaration
    private Vector columneNames = new Vector();
    private Vector data = new Vector();
    // End of variables declaration

    
    public DBCollection(MongoClient mongoClient, String Collection, String fields[]) {
        MongoCollection < Document > collection = mongoClient.getDatabase("Hospital").getCollection(Collection);
        MongoCursor < Document > cursor = collection.find().iterator();
        for (int i = 0; i < fields.length; i++) {
            this.columneNames.addElement(fields[i].toString());
        }
        while (cursor.hasNext()) {
            Document row = cursor.next();
            Vector line = new Vector(fields.length);
            for (int i = 0; i < fields.length; i++) {
                if (row.containsKey(fields[i]) == true) {
                    if (fields[i] == "Status") {
                        if (row.get(fields[i]).toString() == "true") {
                            line.addElement("online");

                        } else line.addElement("offline");

                    } else line.addElement(row.get(fields[i]).toString());
                } else line.addElement(null);

            }
            this.data.addElement(line);
        }
    }
    public DBCollection(MongoClient mongoClient, String Collection, String fields[], String docname, String pationordoctor) {
        MongoCollection < Document > collection = mongoClient.getDatabase("Hospital").getCollection(Collection);
        MongoCursor < Document > cursor = collection.find().iterator();
        int s = 0;
        if (pationordoctor == "Doctor") {
            s = fields.length - 1;
        }
        if (pationordoctor == "UserName") {
            s = fields.length;
        }
        for (int i = 0; i < s; i++) {
            this.columneNames.addElement(fields[i].toString());
        }

        while (cursor.hasNext()) {
            Document row = cursor.next();

            Vector line = new Vector();
            for (int i = 0; i < fields.length; i++) {
                if (row.containsKey(fields[i]) == true) {
                    if (docname.equals((String) row.get(pationordoctor))) {
                        line.addElement(row.get(fields[i]).toString());
                    }
                }
            }
            if (!line.isEmpty()) {
                this.data.addElement(line);
            }



        }

    }

    public Vector GetData() {
        return this.data;
    }
    public Vector GetColumnsNames() {
        return this.columneNames;
    }

}