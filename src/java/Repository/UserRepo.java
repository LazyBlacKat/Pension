
package Repository;

import Modell.Database;
import Modell.User;
import Service.UserService;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;


public class UserRepo {
    public static boolean addNewUser(User user){
        try{
            EntityManager em = Database.getDbConn();
            
            StoredProcedureQuery spq = em.createStoredProcedureQuery("addNewUser");
        
            spq.registerStoredProcedureParameter("emailIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("passwordIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("nameIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("statusIN", Integer.class, ParameterMode.IN);
            
            spq.setParameter("emailIN", user.getEmail());
            spq.setParameter("passwordIN", user.getPassword());
            spq.setParameter("nameIN", user.getName());
            spq.setParameter("statusIN", user.getStatus());
            
            spq.execute();
            return true;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
   
    
    public static List<User> getAllUser(){
        List<User> result = new ArrayList();
        try{
            EntityManager em = Database.getDbConn();
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getAllUser");
            
            List<Object[]> users = spq.getResultList();
            for(Object[] user : users){
                int id = Integer.parseInt(user[0].toString());
                User u = em.find(User.class, id);
                result.add(u);
            }
        }
        catch(Exception ex){
        }
        finally{
            return result;
        }   
    }
    public static boolean notexistEmail(User u){
        try{
            EntityManager em = Database.getDbConn();
            
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getEmail");
        
            spq.registerStoredProcedureParameter("emailIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("exist", Integer.class, ParameterMode.OUT);
            
            spq.setParameter("emailIN", u.getEmail());            
            spq.execute();
            Integer result = (Integer) spq.getOutputParameterValue("exist");
            if(result>0) return false;
            else return true;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return true;
        }
    }
        public static boolean notexistName(User u){
        try{
            EntityManager em = Database.getDbConn();
            
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getName");
        
            spq.registerStoredProcedureParameter("nameIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("exist", Integer.class, ParameterMode.OUT);
            
            spq.setParameter("nameIN", u.getName());            
            spq.execute();
            Integer result = (Integer) spq.getOutputParameterValue("exist");
            if(result>0) return false;
            else return true;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return true;
        }
    }
    public static User getUserById(Integer userid){
        User result = new User();
        List<User> list = UserRepo.getAllUser();
        for(User u : list){
            if(u.getId()==userid){
                result = u;
            }
        }
        return result;
    }

    
    public static boolean logDelUser(User user){
        try{
            EntityManager em = Database.getDbConn();
            
            StoredProcedureQuery spq = em.createStoredProcedureQuery("logicalDeleteUserById");
        
            spq.registerStoredProcedureParameter("idIN", Integer.class, ParameterMode.IN);
            
            spq.setParameter("idIN", user.getId());
            
            spq.execute();
            return true;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    public static boolean updateUserName(User input){
        
        try{
            EntityManager em = Database.getDbConn();
            em.getTransaction().begin();
            User uj = em.find(User.class, input.getId());
            uj.setName(input.getName());            
            em.getTransaction().commit();
            return true;
        }
        catch(Exception ex){
            return false;
        }
    }
    
    public static boolean updateUserPw(User input){
        try{
            EntityManager em = Database.getDbConn();
            em.getTransaction().begin();
            User uj = em.find(User.class, input.getId());
            uj.setPassword(input.getPassword());            
            em.getTransaction().commit();
            return true;
        }
        catch(Exception ex){
            return false;
        }
    }
    
    /*public static boolean login(User u){
        try{
            EntityManager em = Database.getDbConn();
            StoredProcedureQuery spq = em.createStoredProcedureQuery("login");
            spq.registerStoredProcedureParameter("emailIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("passwordIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("idOUT", Integer.class, ParameterMode.OUT);
            spq.setParameter("emailIN", u.getEmail());
            spq.setParameter("passwordIN", u.getPassword());
            spq.execute();
            Integer result = (Integer)spq.getOutputParameterValue("idOUT");
            if(result > 0){
                return true;
            }
            else {return false;}
        }
        catch(Exception ex){
            return true;
        }
    }*/
    public static boolean login(User u)
    {
        List<User> list = UserRepo.getAllUser();
        boolean exist = false;
        for(User l : list)
        {
            if(u.getEmail().equals(l.getEmail())&&u.getPassword().equals(l.getPassword())){
                exist = true;
            }
        }
        return exist;
    }
   
}
