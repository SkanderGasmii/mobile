/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.services.UserService;

/**
 *
 * @author skand
 */
public class RegistrationForm extends Form{

    public RegistrationForm(Form previous) {
        setTitle("Fissaa - Register");
        setLayout(BoxLayout.y());
        
        TextField nomTextField = new TextField("","nom");
        TextField prenomTextField = new TextField("","prenom");
        
        Button registerButton = new Button("Register");
        
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // controle de saisie:
                /*
                if 
                /*
                nomTextField.getText().length() == 0 || de preference nasnouu fonctionet verif louta 
                sinon ken taadewech 
                Dialog.show("Alert","please fill all fields",new Command("Ok");
                
                else 
                
                */
                
                // Integer.parseint(

                
                
                try {
                    User user = new User();
                    //User user = new User(nomTextField.getText(), prenomTextField.getText());
                    
                    UserService userService =  UserService.getInstance();
                    if(userService.Register(user)){
                        Dialog.show("Success","Connection accepted",new Command("Ok"));
                    }
                    else{
                        Dialog.show("Error","Server error",new Command("Ok"));

                    }

                } catch (NumberFormatException e) {
                    /*
                    ken bech  njib champs lezmou ykoun noumrou ou user dakhel haja mouch noumrou 
                                        Dialog.show("Error","Hajja must be a number",new Command("Ok"));

                    */
                }
            }
        });


        
        addAll(nomTextField,prenomTextField,registerButton);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        
    }
    
}
