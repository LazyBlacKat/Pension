
package Repository;

import Modell.Database;
import Modell.Pension;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

public class PensionRepo {
    //user hozhat létre, akár több panziót is (pl. más helyen vannak külön állatoknak, vagy a user egy "csoport")
        public static boolean addNewPension(Pension p){
        try{
            EntityManager em = Database.getDbConn();
            
            StoredProcedureQuery spq = em.createStoredProcedureQuery("addNewPension");
        
            spq.registerStoredProcedureParameter("useridIN", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("nameIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("settlementIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("addressIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("websiteIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("phoneIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("introIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("pictureIN", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("statusIN", Integer.class, ParameterMode.IN);
            
            spq.setParameter("useridIN", p.getUserid());
            spq.setParameter("nameIN", p.getName());
            spq.setParameter("settlementIN", p.getSettlement());
            spq.setParameter("addressIN", p.getAddress());
            spq.setParameter("websiteIN", p.getWebsite());
            spq.setParameter("phoneIN", p.getPhone());
            spq.setParameter("introIN", p.getIntro());
            spq.setParameter("pictureIN", p.getPicture());
            spq.setParameter("statusIN", p.getStatus());
            
            spq.execute();
            return true;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    public static List<Pension> getAllActivePension(){
        List<Pension> result = new ArrayList();        
        try{

            EntityManager em = Database.getDbConn();
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getAllActivePension");
            
            List<Object[]> pensions = spq.getResultList();
            for(Object[] pension : pensions){
                int id = Integer.parseInt(pension[0].toString());
                Pension p = em.find(Pension.class, id);
                result.add(p);
            }
        }
        catch(Exception ex){
        }
        finally{
            return result;
        }   
    }
    
    public static boolean logDelPension(Pension p){
        try{
            EntityManager em = Database.getDbConn();
            
            StoredProcedureQuery spq = em.createStoredProcedureQuery("logicalDeletePensionById");
        
            spq.registerStoredProcedureParameter("idIN", Integer.class, ParameterMode.IN);
            
            spq.setParameter("idIN", p.getId());
            
            spq.execute();
            return true;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    public static boolean updatePension(Pension input){
        // a db-ben lévő id
        try{
            EntityManager em = Database.getDbConn();
            em.getTransaction().begin();
            Pension uj = em.find(Pension.class, input.getId());
            uj.setName(input.getName());
            uj.setSettlement(input.getSettlement());
            uj.setAddress(input.getAddress());
            uj.setWebsite(input.getWebsite());
            uj.setPhone(input.getPhone());
            uj.setIntro(input.getIntro());
            uj.setPicture(input.getPicture());            
            em.getTransaction().commit();
            return true;
        }
        catch(Exception ex){
            return false;
        }
    }
}
