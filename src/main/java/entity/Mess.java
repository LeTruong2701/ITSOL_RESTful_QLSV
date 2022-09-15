package entity;

import java.util.LinkedList;
import java.util.List;

public class Mess {
    private List<String> mess = new LinkedList<>();

    public void add(String mess){
        this.mess.add(mess);
    }

}
