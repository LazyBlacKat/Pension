
package Repository;

import Modell.Database;
import Modell.Message;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

public class MessageRepo {
//bejelentkezés nélkül bárki küldhet üzenetet a usernek (a user emailben veheti fel a kapcsolatot vele)
public static boolean addNewMessage(Message m){
        try{
            EntityManager em = Database.getDbConn();
            
            StoredProcedureQuery spq = em.createStoredProcedureQuery("addNewMessage");
        
            spq.registerStoredProcedureParameter("useridIN", Integer.class, ParameterMode.IN);
            //spq.registerStoredProcedureParameter("dateIN", Date.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("emailIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("petIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("messageIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("statusIN", Integer.class, ParameterMode.IN);
            
            spq.setParameter("useridIN", m.getUserid());
            //spq.setParameter("dateIN", m.getDate());
            spq.setParameter("emailIN", m.getEmail());
            spq.setParameter("petIN", m.getPet());
            spq.setParameter("messageIN", m.getMessage());
            spq.setParameter("statusIN", m.getStatus());
            
            spq.execute();
            return true;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    //userhez tartozóakat kiszűri, törölteket(status=0) nem listázza
    public static List<Message> getFullMessage(){
            List<Message> result = new ArrayList();
            try{
                EntityManager em = Database.getDbConn();
                StoredProcedureQuery spq = em.createStoredProcedureQuery("getAllMessage");

                List<Object[]> messages = spq.getResultList();
                for(Object[] message : messages){
                    int id = Integer.parseInt(message[0].toString());
                    Message m = em.find(Message.class, id);
                    result.add(m);
                }
            }
            catch(Exception ex){
            }
            finally{
                return result;
            } 
    }
    public static List<Message> getAllMessage(Integer userid){
        List<Message> result = new ArrayList();
        List<Message> list = MessageRepo.getFullMessage();
        for(Message m : list)
        {
            if(m.getUserid()==userid){
                result.add(m);
            }
        }
        return result;
    }
    
    public static boolean logDelMessage(Message m){
        try{
            EntityManager em = Database.getDbConn();
            
            StoredProcedureQuery spq = em.createStoredProcedureQuery("logicalDeleteMessageById");
        
            spq.registerStoredProcedureParameter("idIN", Integer.class, ParameterMode.IN);
            
            spq.setParameter("idIN", m.getId());
            
            spq.execute();
            return true;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
    //statusz változtatása: "elolvasva"
    public static boolean updateMessage(Message input){
        try{
            EntityManager em = Database.getDbConn();
            
            StoredProcedureQuery spq = em.createStoredProcedureQuery("readMessageById");
        
            spq.registerStoredProcedureParameter("idIN", Integer.class, ParameterMode.IN);
            
            spq.setParameter("idIN", input.getId());
            
            spq.execute();
            return true;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }   
}
