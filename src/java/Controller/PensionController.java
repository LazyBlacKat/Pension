
package Controller;

import Modell.Pension;
import Service.PensionService;
import Service.UserService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;


public class PensionController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            /*Új panzió létrehozása*/
            if(request.getParameter("task").equals("addPension")){
                if(!request.getParameter("uid").isEmpty()&&UserService.logid>0){
                    JSONObject returnValue = new JSONObject(); 
                    if(!request.getParameter("name").isEmpty()&&!request.getParameter("settlement").isEmpty()&&!request.getParameter("address").isEmpty()&&!request.getParameter("website").isEmpty()&&!request.getParameter("phone").isEmpty()&&!request.getParameter("intro").isEmpty()&&!request.getParameter("picture").isEmpty()){
                        Integer uid = Integer.parseInt(request.getParameter("uid"));
                        String name = request.getParameter("name");
                        String settlement = request.getParameter("settlement");
                        String address = request.getParameter("address");
                        String website = request.getParameter("website");
                        String phone = request.getParameter("phone");
                        String intro = request.getParameter("intro");
                        String picture = request.getParameter("picture");
                        Integer status = 1;

                        Pension m = new Pension(0,uid,name,settlement,address,website,phone,intro,picture,status);

                        String result = PensionService.addNewPension(m);
                        returnValue.put("result", result);
                    }
                    else{
                        returnValue.put("result", "A mezők nincsenek megfelelően kitöltve.");
                    }
                    out.print(returnValue.toString());
                }
                else out.print("A felhasználó nincs bejelentkezve");
            }
            //Panziók listázása (bejelentkezés után ebből frontend kiszűri mik tartoznak a bejelentkezetthez)
            if(request.getParameter("task").equals("getAllActivePension")){
                JSONArray returnValue = new JSONArray();
                List<Pension> pensions = PensionService.getAllActivePension();
                
                if(pensions.isEmpty()){
                    JSONObject obj = new JSONObject();
                    obj.put("Result", "Nincs aktív panzió");
                    returnValue.put(obj);
                    out.print(returnValue.toString());
                }
                else{
                    for(Pension m : pensions){
                        returnValue.put(m.toJson());   
                    }
                    out.print(returnValue.toString());
                }
            }
            //Panzió adatainak frissítése
            if(request.getParameter("task").equals("updatePension")){
                if(!request.getParameter("id").isEmpty()&&UserService.logid>0){
                    JSONObject valasz = new JSONObject();
                    if(!request.getParameter("name").isEmpty()&&!request.getParameter("settlement").isEmpty()&&!request.getParameter("address").isEmpty()&&!request.getParameter("website").isEmpty()&&!request.getParameter("phone").isEmpty()&&!request.getParameter("intro").isEmpty()&&!request.getParameter("picture").isEmpty()){
                        Integer id = Integer.parseInt(request.getParameter("id"));
                        String name = request.getParameter("name");
                        String settlement = request.getParameter("settlement");
                        String address = request.getParameter("address");
                        String website = request.getParameter("website");
                        String phone = request.getParameter("phone");
                        String intro = request.getParameter("intro");
                        String picture = request.getParameter("picture");
                        Integer status = 1;
                        
                        Pension m = new Pension(id,0,name,settlement,address,website,phone,intro,picture,status);
                        String result = PensionService.updatePension(m);
                        valasz.put("result", result);
                    }
                    else{valasz.put("result", "A mezők nincsenek megfelelően kitöltve.");}
                    out.println(valasz.toString());
                }
                else out.print("A felhasználó nincs bejelentkezve");
            }
            
            //Panzió "törlése", status=0
            if(request.getParameter("task").equals("logDelPension")){
                JSONObject returnValue = new JSONObject();
                if(UserService.logid>0){
                    if(!request.getParameter("id").isEmpty()){
                        Integer id = Integer.parseInt(request.getParameter("id"));

                        Pension p = Pension.getPensionById(id);

                        String result = PensionService.logDelPension(p);
                        returnValue.put("result", result);
                    }
                    else{
                        returnValue.put("result", "A mezők nincsenek megfelelően kitöltve.");
                    }
                out.print(returnValue.toString());
                }
                else out.print("A felhasználó nincs bejelentkezve");
            }
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
