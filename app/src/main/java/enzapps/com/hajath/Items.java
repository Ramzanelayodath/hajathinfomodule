package enzapps.com.hajath;

public class Items {
    String name;
    String price;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String image;
    String id;

    public Items(String name, String price, String image, String id) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.id = id;
    }

}
