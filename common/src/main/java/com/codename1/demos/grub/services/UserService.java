/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.demos.grub.services;

import com.codename1.demos.grub.Util;
import com.codename1.demos.grub.entities.User;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author skander
 */
public class UserService {

    public ArrayList<User> tasks;

    public static UserService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private UserService() {
        req = new ConnectionRequest();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    //register
    public boolean addUser(User t) {
        String url = Util.BASE_URL + "/cn1json/register";
        req.setUrl(url);
        req.setPost(true);
        req.addArgument("firstName", t.getFirstName());
        req.addArgument("lastName", t.getLastName());
        req.addArgument("email", t.getEmail());
        req.addArgument("password", t.getPassword());
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public boolean login(User t) {
        String url = Util.BASE_URL + "/cn1json/login";
        req.setUrl(url);

        req.addArgument("email", t.getEmail());
        req.addArgument("password", t.getPassword());
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

//    public ArrayList<User> parseTasks(String jsonText){
//        try {
//            tasks=new ArrayList<>();
//            JSONParser j = new JSONParser();
//            Map<String,Object> tasksListJson =
//                    j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
//
//            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
//            for(Map<String,Object> obj : list){
//                Task t = new Task();
//                float id = Float.parseFloat(obj.get("id").toString());
//                t.setId((int)id);
//                t.setStatus(((int)Float.parseFloat(obj.get("status").toString())));
//                if (obj.get("name")==null)
//                    t.setName("null");
//                else
//                    t.setName(obj.get("name").toString());
//                tasks.add(t);
//            }
//
//
//        } catch (IOException ex) {
//
//        }
//        return tasks;
//    }
//
//    public ArrayList<Task> getAllTasks(){
//        //String url = Statics.BASE_URL+"/tasks/";
//        String url = Statics.BASE_URL+"get/";
//        req.setUrl(url);
//        req.setPost(false);
//        req.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//                tasks = parseTasks(new String(req.getResponseData()));
//                req.removeResponseListener(this);
//            }
//        });
//        NetworkManager.getInstance().addToQueueAndWait(req);
//        return tasks;
//    }
//}
}
