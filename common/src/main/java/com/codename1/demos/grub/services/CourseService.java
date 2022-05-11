package com.codename1.demos.grub.services;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.codename1.demos.grub.Util;
import com.codename1.demos.grub.entities.Course;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
//import com.mycompany.myapp.MyApplication;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

    /**
     *
     * @author MSI
     */
    public class CourseService {

        public ArrayList<Course> courses;

        public static CourseService instance = null;
        public boolean resultOK;
        private ConnectionRequest req;

        private CourseService() {
            req = new ConnectionRequest();
        }

        public static CourseService getInstance() {
            if (instance == null) {
                instance = new CourseService();
            }
            return instance;
        }

        public boolean addTask(Course c) {
            String url = Util.BASE_URL + "/newapi4?titre=" + c.getTitre() + "&description=" + c.getDescription();
            System.out.println(url);
            req.setUrl(url);
            req.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {
                    resultOK = req.getResponseCode() == 200;
                    req.removeResponseListener(this);
                }
            });
            NetworkManager.getInstance().addToQueueAndWait(req);
            return resultOK;
        }

        public ArrayList<Course> parseCourses(String jsonText) {
            try {
                courses = new ArrayList<>();
                JSONParser j = new JSONParser();
                Map<String, Object> coursesListJson
                        = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
                System.out.println(coursesListJson);
                List<Map<String, Object>> list = (List<Map<String, Object>>) coursesListJson.get("root");
                for (Map<String, Object> obj : list) {
                    Course course = new Course();
                    float id = Float.parseFloat(obj.get("id").toString());
                    course.setId((int) id);
                    course.setTitre((obj.get("titre").toString()));
                    course.setDescription((obj.get("description").toString()));


                    courses.add(course);
                }

            } catch (IOException ex) {

            }
            return courses;
        }

        public ArrayList<Course> getAllCours() {
            String url = Util.BASE_URL + "/api1";
            req.setUrl(url);
            req.setPost(false);
            req.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {
                    courses = parseCourses(new String(req.getResponseData()));
                    req.removeResponseListener(this);
                }
            });
            NetworkManager.getInstance().addToQueueAndWait(req);
            return courses;
        }

        public boolean DeleteCourse(int id) {
            String url = Util.BASE_URL + "/deleteapi4/" + id;

            req.setUrl(url);

            req.setPost(false);
            req.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {
                    req.removeResponseCodeListener(this);
                }
            });
            NetworkManager.getInstance().addToQueueAndWait(req);
            return resultOK;
        }

        public boolean EditCourse(Course C) {
            //String url = Util.BASE_URL+"/editapi4/"+MyApplication.idA+"?titre=" + C.getTitre();
            String url = Util.BASE_URL + "/editapi4/" + C.getId() + "?titre=" + C.getTitre();

            req.setUrl(url);

            req.setPost(false);
            req.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {
                    req.removeResponseCodeListener(this);
                }
            });
            NetworkManager.getInstance().addToQueueAndWait(req);
            return resultOK;
        }
    }
