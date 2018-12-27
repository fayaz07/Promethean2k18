package promethean2k18.com.Data_models;

public class Sponsor_model {
    String image,name,desc;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Sponsor_model() {
    }

    public Sponsor_model(String image, String name, String desc) {
        this.image = image;
        this.name = name;
        this.desc = desc;
    }
}
