package uk.ac.anglia.student.hilda.studentadviser.controller;


import android.util.Log;
import android.view.*;

import uk.ac.anglia.student.hilda.studentadviser.model.User;
import uk.ac.anglia.student.hilda.studentadviser.util.*;
/**
 * Created by Hilda on 07/04/2017.
 */

public class UserController {

    private Parser userDAO ;

    View view;
    public UserController(View vw){
        view = vw;
        userDAO = new Parser(view);
    }

    public User getByEmail(String email, int id, String password){
        // public Boolean getByEmail(String email,String password) {
        String userId = null;
       // Log.i("Values","username = "+email + " password =" + password +   id);

        try {
            User user = userDAO.findByEmail(email,password,id);
           // userId = String.valueOf(user.getId());
            //if(userId == null){
           //     System.out.println("if statement line 22");
           //     return null;
           // }
           // else{
           //     System.out.println("line 26 error");
                return user;
            //}
        }
        catch (Exception ex) {
            System.out.println(ex);
            return null;
        }

    }
}
