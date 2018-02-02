package ctlms.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class City {

    @Id
    private String name;

    private List<Controler> controlerList = new ArrayList<>();

    private int width;

    private int height;



    protected City(){
    }

    public City(int width, int height) {
        this.width = width;
        this.height = height;
        for(int i = 1; i <= width * height; i++){
            controlerList.add(new Controler("ctl" + i , (i-1) % height, (i-1) / height));
        }
    }

    public City(int width, int height, String name) {
        this(width, height);
        this.name = name;
    }

    public List<Controler> getControlerList() {
        return controlerList;
    }

    public void setControlerList(List<Controler> controlerList) {
        this.controlerList = controlerList;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
