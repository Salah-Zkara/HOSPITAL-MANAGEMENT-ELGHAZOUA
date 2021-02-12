package MyClasses;

import java.security.NoSuchAlgorithmException;
import java.text.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Doctor extends Citizen {
    private String Speciality;
    private boolean Status;
    private Date HireDate;


    public Doctor(){

    }

    public Doctor(String Fn, String Ln, String cni, String UN, String passwd, Date dob, String Speciality) throws NoSuchAlgorithmException, ParseException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        DateFormat format = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss", Locale.ENGLISH);
        Date date = format.parse(dtf.format(now));

        this.F_name = Fn.toUpperCase();
        this.L_name = Ln.toUpperCase();
        this.HireDate = date;
        this.DateOfBirth = dob;
        this.UserName = UN.toUpperCase();
        this.password = Password.GetHash(passwd);
        this.Cni = cni.toUpperCase();
        this.Speciality = Speciality.toUpperCase();
        this.Status = false;
    }


    public void SetHireDate(Date HD){
        this.HireDate = HD;
    }
    public void SetSpeciality(String spec){
        this.Speciality = spec;
    }
    public void SetStatus(boolean status){
        this.Status = status;
    }


    public Date GetHireDate(){
        return this.HireDate;
    }
    public String GetSpeciality(){
        return this.Speciality;
    }
    public boolean GetStatus(){ return this.Status; }

}
