package enzapps.com.hajath;

import java.util.ArrayList;

public class MenuModel {

    String name;
    ArrayList<Items> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Items> getItems() {
        return items;
    }

    public void setItems(ArrayList<Items> items) {
        this.items = items;
    }

    public MenuModel(String name, ArrayList<Items> items) {
        this.name = name;
        this.items = items;
    }

}
