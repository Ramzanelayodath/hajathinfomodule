package enzapps.com.hajath;

public class Modelreview  {
    String name;
    String text;
    String date;
    String rating;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }



    public Modelreview(String name, String text, String date, String rating) {
        this.name = name;
        this.text = text;
        this.date = date;
        this.rating = rating;
    }


}
