/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.utils.Statics;
import com.mycompany.myapp.entities.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author skand
 */
public class UserService {
    private boolean resultatOK;
    private static UserService instance;
    private final ConnectionRequest connectionRequest ;
    private ArrayList<User> users;


    private UserService() {
        connectionRequest = new ConnectionRequest();
    }
    
    public static UserService getInstance(){
        if (instance == null)
            instance = new UserService();
        return instance ;
}
    
   
    
    //add user 
    public boolean Register(User u){
        String url = Statics.BASE_URL+"/register" ;
        connectionRequest.setUrl(url); 
        
        connectionRequest.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultatOK = connectionRequest.getResponseCode() == 200 ;
                connectionRequest.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(connectionRequest);
        return resultatOK;
        
    }
    
    
    public ArrayList<User> pasreUsers(String JSONText){
        try {
            users = new ArrayList<>();
            JSONParser jsonParser = new JSONParser();
            Map<String,Object> usersListJSON = jsonParser.parseJSON(new CharArrayReader(JSONText.toCharArray()));

            List<Map<String,Object>> list = (List<Map<String,Object>>) usersListJSON.get("root");
            
            for (Map<String, Object> obj : list) {
                User user = new User();
                user.setId((int)Float.parseFloat(obj.get("id").toString()));
                user.setFirstName(obj.get("firstName").toString());
                user.setLastName(obj.get("lastName").toString());
                users.add(user);
                
            }
        } catch (IOException ex) {
        }
        
        return users;
    }
    
    
    
    public ArrayList<User> getAllUsers(){
        String url = Statics.BASE_URL+"allUsers";
        connectionRequest.setUrl(url);
        connectionRequest.setPost(false);
        connectionRequest.addResponseListener(new ActionListener<NetworkEvent>() {
                   @Override
                   public void actionPerformed(NetworkEvent evt) {
                       users = pasreUsers(new String(connectionRequest.getResponseData()));
                       connectionRequest.removeResponseListener(this);                   }
               });
         NetworkManager.getInstance().addToQueueAndWait(connectionRequest);
        return users;
           }


}
