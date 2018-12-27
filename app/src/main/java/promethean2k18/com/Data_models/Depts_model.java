package promethean2k18.com.Data_models;

public class Depts_model {
    String name,noevents,imageurl;

    public Depts_model() {
    }

    public Depts_model(String name, String noevents, String imageurl) {
        this.name = name;
        this.noevents = noevents;
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNoevents() {
        return noevents;
    }

    public void setNoevents(String noevents) {
        this.noevents = noevents;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
