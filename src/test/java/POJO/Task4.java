package POJO;

import java.util.ArrayList;
import java.util.List;

public class Task4 {

    private ArrayList<Users> listuser;

    public ArrayList<Users> getListuser() {
        return listuser;
    }

    public void setListuser(ArrayList<Users> listuser) {
        this.listuser = listuser;
    }

    @Override
    public String toString() {
        return "Task4{" +
                "listuser=" + listuser +
                '}';
    }
}
