
package Controller;

import Modell.Message;
import Service.MessageService;
import Service.UserService;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

public class MessageController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /*Új üzenet küldése egy adott panzión belüli form-mal,
            nem kell bejelentkezni hozzá
            A userid-t a frontend küldi panzió adatai alapján
            */
            if(request.getParameter("task").equals("addMessage")){
                JSONObject returnValue = new JSONObject();
                
                if(!request.getParameter("email").isEmpty()&&!request.getParameter("pet").isEmpty()&&!request.getParameter("message").isEmpty()){
                    Integer userid = Integer.parseInt(request.getParameter("userid")); //a frontend küldi majd
                    //SimpleDateFormat dateformat = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss");
                    //Date datetime = dateformat.parse(request.getParameter("datetime"));//aznapi dátum
                    String email = request.getParameter("email");
                    String pet = request.getParameter("pet");
                    String message = request.getParameter("message");
                    Integer status = 1; //az új üzenet státusza 1, olvasotté 2, törölté 0
                    
                    Message m = new Message(0,userid,null,email,pet,message,status);
                    
                    String result = MessageService.addNewMessage(m);
                    returnValue.put("result", result);
                }
                else{
                    returnValue.put("result", "A mezők nincsenek megfelelően kitöltve.");
                }
                out.print(returnValue.toString());
            }
            //Üzenetek megtekintése userid alapján, frontendenden számolja és jelzi az olvasott és olvasatlan üzeneteket
            if(request.getParameter("task").equals("getAllMessage")){
                if(!request.getParameter("userid").isEmpty()&&UserService.logid>0){
                    Integer userid = Integer.parseInt(request.getParameter("userid"));
                    
                    JSONArray returnValue = new JSONArray();
                    List<Message> messages = Service.MessageService.getAllMessage(userid);

                    if(messages.isEmpty()){
                        JSONObject obj = new JSONObject();
                        obj.put("Result", "Nincs üzenet");
                        returnValue.put(obj);
                        out.print(returnValue.toString());
                    }
                    else{
                        for(Message m : messages){
                            returnValue.put(m.toJson());

                        }
                        out.print(returnValue.toString());
                    }
                }
                else{
                    out.print("A felhasználó nincs bejelentkezve");
                }    
            }
            
            //Üzenet "törlése", status=0, frontenddel gombbal csak azok jelennek meg amiket törölni lehet
            if(request.getParameter("task").equals("logDelMessage")){
                JSONObject returnValue = new JSONObject();
                if(UserService.logid>0){
                    if(!request.getParameter("id").isEmpty()){
                        Integer id = Integer.parseInt(request.getParameter("id"));

                        Message m = Message.getMessageById(id);

                        String result = MessageService.logDelMessage(m);
                        returnValue.put("result", result);
                    }
                    else{
                        returnValue.put("result", "A mezők nincsenek megfelelően kitöltve.");
                    }
                out.print(returnValue.toString());
                }
                else out.print("A felhasználó nincs bejelentkezve");
            }
            
            //Üzenet "elolvasva", status=2, frontenddel gombbal csak azok jelennek meg amiket olvasni lehet
            if(request.getParameter("task").equals("readMessage")){
                JSONObject valasz = new JSONObject();
                if(UserService.logid>0){
                    if(!request.getParameter("id").isEmpty()){
                        Integer id = Integer.parseInt(request.getParameter("id"));

                        Message m = Message.getMessageById(id);

                        String result = MessageService.readMessage(m);
                        valasz.put("result", result);
                    }
                    else{
                        valasz.put("result", "A mezők nincsenek megfelelően kitöltve.");
                    }
                out.print(valasz.toString());
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
