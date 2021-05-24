
package Service;

import Modell.Pension;
import Repository.PensionRepo;
import java.util.List;


public class PensionService {
    public static String addNewPension(Pension p){
        if(p.getName().length()>0&&p.getName().length()<101&&
                p.getSettlement().length()>0&&p.getSettlement().length()<101&&
                p.getAddress().length()>0&&p.getAddress().length()<101&&
                p.getWebsite().length()>0&&p.getWebsite().length()<251&&
                p.getPhone().length()>0&&p.getPhone().length()<16&&
                p.getIntro().length()>0&&p.getIntro().length()<1001&&
                p.getPicture().length()>0&&p.getPicture().length()<251){
            if(PensionRepo.addNewPension(p)){
                return "A panzió rögzítése sikeresen megtörtént!";
            }
            else{
                return "Az adatok helyesek de a rögzítés nem sikerült";
            }
        }
        else{
            return "A megadott érték(ek) hossza nem megfelelő.";
        }
        
    }
    
    public static List<Pension> getAllActivePension(){
        return PensionRepo.getAllActivePension();
    }
    
    public static String logDelPension(Pension p){
        if(PensionRepo.logDelPension(p)){
                return "A panzió törlése sikeresen megtörtént!";
            }
            else{
                return "A törlés nem sikerült";
            }
    }
    
    public static String updatePension(Pension p){
        
    if(p.getName().length()>0&&p.getName().length()<101&&
                p.getSettlement().length()>0&&p.getSettlement().length()<101&&
                p.getAddress().length()>0&&p.getAddress().length()<101&&
                p.getWebsite().length()>0&&p.getWebsite().length()<251&&
                p.getPhone().length()>0&&p.getPhone().length()<16&&
                p.getIntro().length()>0&&p.getIntro().length()<1001&&
                p.getPicture().length()>0&&p.getPicture().length()<251){
            if(PensionRepo.updatePension(p)){
                return "A panzió adatainak frissítése sikeresen megtörtént!";
            }
            else{
                return "A megadott panzió nem létezik";
            }
        }
        else{
            return "A megadott érték(ek) hossza nem megfelelő.";
        }
    }   
}
