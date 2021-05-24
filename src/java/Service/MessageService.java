
package Service;

import Modell.Message;
import Repository.MessageRepo;
import java.util.ArrayList;
import java.util.List;

public class MessageService {
    public static String addNewMessage(Message m){
        if(m.getPet().length()<51&&m.getMessage().length()<1001&&m.getEmail().length()<251){
            if(MessageRepo.addNewMessage(m)){
                return "Az üzenet elküldése sikeresen megtörtént!";
            }
            else{
                return "Az adatok helyesek de a küldés nem sikerült";
            }
        }
        else{
            return "Túl hosszú valamely mező. A pet, message, email mezők maximális hossza: 50,1000,250.";//számlálót lehet rakni a frontenden
        }       
    }
    public static String logDelMessage(Message m){
        if(MessageRepo.logDelMessage(m)){
                return "Az üzenet törlése sikeresen megtörtént!";
            }
            else{
                return "A törlés nem sikerült";
            }
    }

    public static List<Message> getAllMessage(Integer userid){
        return MessageRepo.getAllMessage(userid);
    }
    
    public static String readMessage(Message input){
        if(MessageRepo.updateMessage(input)){
                return "Az üzenet elolvasva";
            }
            else{
                return "A státusz változtatása nem sikerült";
            }
    }
    
}
