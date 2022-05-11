/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author skand
 */
public class HomeForm extends Form{
    Form current;
    public HomeForm(){
        current = this;
        setTitle("Fissaa");
        setLayout(BoxLayout.y());
        add(new Label("Choose an option"));
        Button addUserButton = new Button("add user");
        Button listUsersButton = new Button("List users");
        
        
        addUserButton.addActionListener(e -> new RegistrationForm(current).show() );
        listUsersButton.addActionListener(e -> new ListUsersForm(current).show() );
        
        addAll(addUserButton,listUsersButton);

    }
    
}
