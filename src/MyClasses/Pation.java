package MyClasses;

import java.security.NoSuchAlgorithmException;
import java.text.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Pation extends Citizen {
    private Date InsDate;

    public Pation(String Fn,String Ln,String cni,String UN,String passwd,Date dob,String phone) throws NoSuchAlgorithmException, ParseException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        DateFormat format = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss", Locale.ENGLISH);
        Date date = format.parse(dtf.format(now));

        this.F_name = Fn.toUpperCase();
        this.L_name = Ln.toUpperCase();
        this.InsDate = date;
        this.DateOfBirth = dob;
        this.UserName = UN.toUpperCase();
        this.password = Password.GetHash(passwd);
        this.Cni = cni.toUpperCase();
        this.Phone = phone;
    }
    public void SetInsDate(Date insdate){
        this.InsDate = insdate;
    }
    public Date GetInsDate(){
        return this.InsDate;
    }
}
