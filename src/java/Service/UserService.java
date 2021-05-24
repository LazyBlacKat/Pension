
package Service;

import Modell.User;
import Repository.UserRepo;
import java.util.List;

public class UserService {
    
    public static Integer logid = 3;
    private static boolean logtrue = false;
    

    public static String addNewUser(User uj){
        if(UserRepo.notexistEmail(uj)){
            if(UserRepo.notexistName(uj)){
                if(UserRepo.addNewUser(uj)) return "A regisztráció sikeresen megtörtént!";
                else return "A regisztráció nem sikerült de adatok jók";
            }
            else return "A név foglalt";
        }
        else {return "Az emailcím foglalt";}
    }

    public static String logDelUser(User u){
        if(UserRepo.logDelUser(u)){
                return "A user törlése sikeresen megtörtént!";
            }
            else{
                return "A törlés nem sikerült";
            }
    }    
    
    public static String updateUserName(User u){
        if(UserRepo.updateUserName(u)){
            return "Sikeres update!";
        }
        else{
            return "Sikertelen update!";
        }
    }
    
    public static String updateUserPw(User uj){
        if(UserRepo.updateUserPw(uj)){
            return "Sikeres update!";
        }
        else{
            return "Sikertelen update!";
        }
    }
    
    public static String login(User u)
    {
        if(UserRepo.login(u)){
            return "bejelentkezés sikeres";
        }
        else return "Rossz emailcím vagy jelszó";
    }
        
    public static boolean loggedIn(){
        if (logtrue){return true;}
        else return false;
    }
    
}
