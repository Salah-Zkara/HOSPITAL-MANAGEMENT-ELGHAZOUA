package GestionHopitale;

import MyClasses. * ;

import static GestionHopitale.ConnectionAuth.*;
import static GestionHopitale.MethodsOnDB. * ;
import static MyClasses.Password. * ;
import com.mongodb.BasicDBObject;
import com.mongodb.client. * ;
import org.bson.Document;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net. * ;
import java.security.NoSuchAlgorithmException;
import java.text. * ;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util. * ;
import java.util.logging. * ;
import javax.swing. * ;

public class Main extends Thread {

  private final int Option;
  public static JFrame frr = null;
  public static int where = -1;
  Home mainframe = new Home();
  //Local//private String Connection_mongo ="mongodb://localhost:27017";
  private static Scanner MySc = new Scanner(System.in);
  public static String Connection_mongo;

  static {
    try {
      Connection_mongo = ReadF(MySc);
    } catch (IOException e) {
      e.getMessage();
    }
  }

  public Main(int opt) {
    this.Option = opt;
  }
  public static void slp() throws InterruptedException {
    Thread.sleep(2000);
  }

  public static boolean CheckDoctorToAddHim(MongoClient mongoClient, Doctor doc, String password) throws ParseException,
  NoSuchAlgorithmException {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    DateFormat format = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss", Locale.ENGLISH);
    Date date = format.parse(dtf.format(now));
    boolean validPassword = Password.isValidPassword(password);
    if (doc.GetSpeciality().equals("") || doc.GetDateOfBirth() == null || doc.GetFirstName().isEmpty() || doc.GetLastName().isEmpty() || doc.GetUserName().isEmpty() || doc.GetCni().isEmpty() || doc.GetPassword().isEmpty()) {
      JOptionPane.showMessageDialog(null, "Sorry, please fill all blanks");
    } else {
      if (doc.GetDateOfBirth().after(date)) {
        JOptionPane.showMessageDialog(null, "Are you sure you are born in the future");
      } else {
        if (password.isEmpty() == false && validPassword == true) {
          if (null == checkifFieldinCollections(mongoClient, "UserName", doc.GetUserName(), "Doctors")) {
            if (null == checkifFieldinCollections(mongoClient, "Cni", doc.GetCni(), "Doctors")) {
              if (doc.GetDateOfBirth().after(date)) {
                JOptionPane.showMessageDialog(null, "Are you sure you are born in the future");
              } else {
                return true;
              }
            } else {
              JOptionPane.showMessageDialog(null, "Are you sure this is your CNI");
            }
          } else {
            JOptionPane.showMessageDialog(null, "Sorry, username exists already");
          }
        } else {
          JOptionPane.showMessageDialog(null, "Sorry, password must have atleast (one lowercase character,one uppercase character,one number,one special character)");
        }
      }
    }
    return false;
  }

  public static boolean CheckPationToAddHim(MongoClient mongoClient, Pation pat, String password) throws ParseException,
  NoSuchAlgorithmException {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    DateFormat format = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss", Locale.ENGLISH);
    Date date = format.parse(dtf.format(now));
    boolean validPassword = Password.isValidPassword(password);
    if (pat.GetDateOfBirth() == null || pat.GetFirstName().isEmpty() || pat.GetLastName().isEmpty() || pat.GetDateOfBirth() == null || pat.GetUserName().isEmpty() || pat.GetCni().isEmpty() || pat.GetPhone().isEmpty() || pat.GetPassword().isEmpty()) {
      JOptionPane.showMessageDialog(null, "Sorry, please fill all blanks");
    } else {
      if (pat.GetDateOfBirth().after(date)) {
        JOptionPane.showMessageDialog(null, "Are you sure you are born in the future");
      } else {
        if (pat.GetPhone().length() > 10) {
          JOptionPane.showMessageDialog(null, "Moroccan numbers only please");
        } else {
          if (pat.GetPassword().isEmpty() == false && validPassword == true) {
            if (null == checkifFieldinCollections(mongoClient, "UserName", pat.GetUserName(), "Pations")) {
              if (null == checkifFieldinCollections(mongoClient, "Cni", pat.GetCni(), "Pations")) {
                return true;
              } else {
                JOptionPane.showMessageDialog(null, "Are you sure this is your CNI");
              }
            } else {
              JOptionPane.showMessageDialog(null, "Sorry, username exists already");
            }
          } else {
            JOptionPane.showMessageDialog(null, "Sorry, password must have atleast (one lowercase character,one uppercase character,one number,one special character)");
          }
        }
      }
    }
    return false;
  }
  public static boolean netIsAvailable() {
    try {
      final URL url = new URL("http://www.google.com");
      final URLConnection conn = url.openConnection();
      conn.connect();
      conn.getInputStream().close();
      return true;
    } catch(MalformedURLException e) {
      throw new RuntimeException(e);
    } catch(IOException e) {
      return false;
    }
  }
  public static boolean checkRDV(MongoClient mongoClient, Rdv rdv) throws ParseException {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    DateFormat format = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss", Locale.ENGLISH);
    Date date = format.parse(dtf.format(now));
    System.out.println(date);
    System.out.println(rdv.GetDate());

    if (rdv.GetUserName().isEmpty() == true || rdv.GetCni().isEmpty() == true || rdv.GetDate() == null || rdv.GetDoctor().isEmpty() == true) {
      JOptionPane.showMessageDialog(null, "Sorry, please fill all blanks");
    } else {
      if (rdv.GetDate().before(date)) {
        JOptionPane.showMessageDialog(null, "Sorry, please insert a valid date");
      } else {
        System.out.println(checkifRDVinRDVs(mongoClient, rdv.GetDate()));
        if (checkifRDVinRDVs(mongoClient, rdv.GetDate()) != null) {
          JOptionPane.showMessageDialog(null, "Sorry, this date is already full");
        } else {
          if (checkifFieldinCollections(mongoClient, "UserName", rdv.GetUserName(), "Pations") != null) {
            if (rdv.GetCni().compareTo(getFieldbyUsername(mongoClient, rdv.GetUserName(), "Cni", "Pations")) == 0) {
              return true;
            } else {
              JOptionPane.showMessageDialog(null, "Cni erreur");
            }
          } else {
            JOptionPane.showMessageDialog(null, "User not found");
            return false;
          }
        }
      }
    }

    return false;
  }
  public static boolean login(MongoClient mongoClient, String usr, String password, String Collection) {
    if (null != checkifFieldinCollections(mongoClient, "UserName", usr.toUpperCase(), Collection)) {
      try {
        String pass = getFieldbyUsername(mongoClient, usr.toUpperCase(), "Password", Collection);
        password = GetHash(password);
        if (pass.compareTo(password) == 0) {
          return true;
        } else {
          JOptionPane.showMessageDialog(null, "Sorry, password is incorrect.");
        }
      } catch(NoSuchAlgorithmException ex) {
        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
      }
    } else {
      JOptionPane.showMessageDialog(null, "Sorry, username is incorrect.");
    }
    return false;
  }
  public static boolean loginAdmin(MongoClient mongoClient, String usr, String password) {
    if (null != checkifFieldinCollections(mongoClient, "UserName", usr, "Admins")) {
      if (getFieldbyUsername(mongoClient, usr, "Password", "Admins").compareTo(password) == 0) {
        return true;
      } else {
        JOptionPane.showMessageDialog(null, "Sorry, password is incorrect.");
      }
    } else {
      JOptionPane.showMessageDialog(null, "Sorry, username is incorrect.");
    }
    return false;
  }

  public void run() {
    try {
      if (this.Option == 0) {

        if (netIsAvailable() == true) {
          try {
            mainframe.placeComponents();
          } catch(IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
          }
        } else {
          JOptionPane.showMessageDialog(null, "Failed to connect", "Connection Error", JOptionPane.ERROR_MESSAGE);
          System.exit( - 1);
        }

      } else if (this.Option == 1) {
        while (true) {
          Thread.sleep(30000);
          if (netIsAvailable() == false) {
            System.out.println("Great");
            JOptionPane.showMessageDialog(frr, "Failed to connect", "Connection Error", JOptionPane.ERROR_MESSAGE);
            Thread.sleep(1000);
            System.exit( - 1);
          } else if (netIsAvailable() == true) {
            MongoClient mongoClient = MongoClients.create(Connection_mongo);
            MongoCollection < Document > collection = mongoClient.getDatabase("Hospital").getCollection("Doctors");
            BasicDBObject filter = new BasicDBObject("Status", true);
            BasicDBObject updateQuery = new BasicDBObject();
            updateQuery.append("$set", new BasicDBObject("Status", false));
            collection.updateMany(filter, updateQuery);
          }

        }
      }

    } catch(Exception e) {
      System.out.println(e.toString());
    }
  }

  public static void main(String[] args) throws NoSuchAlgorithmException,
          IOException, InterruptedException {


    File myObj = new File("connection.txt");


    if (myObj.createNewFile()){
      System.out.println("'connection.txt' file has been created!");
      System.out.println("rerun this app!!");
    }


    else{
      try {
        ConnectionAuth Conn = new ConnectionAuth();
        System.out.println(Conn.check(Connection_mongo,MySc));
        Thread t1 = new Main(0);
        Thread t2 = new Main(1);
        t1.start();
        t2.start();
      } catch (Exception e) {
        System.out.println(e.getMessage());
        FileWriter myWriter = new FileWriter("connection.txt");
        myWriter.write("");
        myWriter.close();
      }
    }


  }

}