package lk.sliit.parkingmanagement.oopapp.FilleHander;

import lk.sliit.parkingmanagement.oopapp.User;
import lk.sliit.parkingmanagement.oopapp.config.FileConfig;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class UserFileHandler  {

    protected  String FILEPATH = "parkingUsers.txt";

    public void addUserToFile(User user)  throws IOException{

        try(BufferedWriter writer =  new BufferedWriter(new FileWriter(FILEPATH, true))){
        String userDetails =  user.toString();


        writer.write(userDetails);
        writer.newLine();

        }catch(IOException e ){
            throw  new IOException(" error while writing user data  to file ");
        }



    }
    public void deleteUser ( String username ) throws  IOException{

    }


}
