package MyClasses;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

abstract class Citizen {

    String F_name;
    String L_name;
    String Cni;
    String UserName;
    String password;
    String Phone;
    Date DateOfBirth;

    //setters
    public void SetFirstName(String Fn){
        this.F_name = Fn;
    }
    public void SetLastName(String Ln){
        this.L_name = Ln;
    }
    public void SetCni(String cni){
        this.Cni = cni;
    }
    public void SetDateOfBirth(Date age){
        this.DateOfBirth = age;
    }
    public void SetUserName(String UN){
        this.UserName = UN;
    }
    public void SetPassword(String pass) throws NoSuchAlgorithmException { this.password = Password.GetHash(pass); }
    public void SetPhone(String phone){
        this.Phone = phone;
    }

    //getters
    public String GetFirstName(){
        return this.F_name;
    }
    public String GetLastName(){
        return this.L_name;
    }
    public String GetCni(){
        return this.Cni;
    }
    public Date GetDateOfBirth(){
        return this.DateOfBirth;
    }
    public String GetUserName(){
        return this.UserName;
    }
    public String GetPassword(){
        return this.password;
    }
    public String GetPhone(){ return this.Phone; }
}
