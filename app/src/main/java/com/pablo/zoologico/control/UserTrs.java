package com.pablo.zoologico.control;

import android.app.Activity;
import android.content.Context;

import com.pablo.zoologico.modelo.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class UserTrs {
    /**
     * Method to save an user in a txt and also in de arraylist
     * @param user
     * @throws IOException
     */
    public void save(User user, Activity context)  {

        User.listUser.add(user);
        try {
            FileOutputStream file = context.openFileOutput("users.txt", Context.MODE_PRIVATE);
            ObjectOutputStream write=new ObjectOutputStream(file);
            write.writeObject(user);
            write.close();
            file.close();
        }catch (IOException e){

        }


    }

    /**
     * Method to read the txt file and save all the user that contains this txt to a list
     */
    public static void listar(Activity context)  {
        try{
            FileInputStream file=context.openFileInput("users.txt");
            ObjectInputStream reader=new ObjectInputStream(file);
            Object user=reader.readObject();
            while(user!=null){
                if(user instanceof  User){
                    User.listUser.add((User) user);


                }
                user=reader.readObject();
            }


        }catch(Exception e){

        }

    }

    /**
     *  Method to log on the app if the password and mail match
     * @param mail
     * @param password
     * @return
     */
    public User validate(String mail,String password){
        for (User user:User.listUser) {
            if(user.getMail().equalsIgnoreCase(mail)&&user.getPassword().equalsIgnoreCase(password)){
                return user;
            }
        }
            return null;
    }

    public static boolean availability(String mail){
        for (User user :User.listUser) {
            if(user.getMail().equalsIgnoreCase(mail))return false;


        }
        return true;
    }

}
