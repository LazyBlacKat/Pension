
package Controller;

import Modell.User;
import Service.UserService;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

public class UserController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            //regisztráció
            if(request.getParameter("task").equals("addNewUser")){
                JSONObject valasz = new JSONObject();
            
                if(!request.getParameter("email").isEmpty()&&!request.getParameter("password").isEmpty()&&!request.getParameter("name").isEmpty()){
                    String email = request.getParameter("email");
                    String pw = request.getParameter("password");
                    String name = request.getParameter("name");
                    Integer status = 1;
                    
                    User u = new User(null, email, pw, name, status);
                    
                    String serviceResult = UserService.addNewUser(u);
                    
                    valasz.put("result", serviceResult);
                }
                else{
                      valasz.put("result", "A mezők nincsenek megfelelően kitöltve.");                 
                }
                out.print(valasz.toString());
            }
            //Név megváltoztatása (Jelszó és email változtatása nem lehetséges)
             if(request.getParameter("task").equals("updateUserName")){
                    JSONObject valasz = new JSONObject();
                    if(request.getParameter("name").isEmpty()){
                        valasz.put("result", "A mezők nincsenek megfelelően kitöltve");
                    }
                    else{
                        if(UserService.logid>1){
                            Integer id = Integer.parseInt(request.getParameter("id"));
                            String name = request.getParameter("name");

                            User u = new User(id, null, null, name, 0);

                            String serviceResult = UserService.updateUserName(u);

                            valasz.put("result", serviceResult);
                        }
                        else valasz.put("result", "A felhasználó nincs bejelentkezve");
                    }
                    out.println(valasz.toString());
            }
             //Bejelentkezés
            if(request.getParameter("task").equals("login")){
                JSONObject returnValue = new JSONObject();
                    if(!request.getParameter("email").isEmpty()&&!request.getParameter("password").isEmpty()){
                        String email = request.getParameter("email");
                        String pw = request.getParameter("password");

                        User m = new User(0,email,pw,null,0);

                        String result = UserService.login(m);
                        returnValue.put("result", result);
                    }
                    else{
                        returnValue.put("result", "A mezők nincsenek megfelelően kitöltve.");
                    }
                out.print(returnValue.toString());
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
