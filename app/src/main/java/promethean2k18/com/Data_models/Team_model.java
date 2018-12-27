package promethean2k18.com.Data_models;

public class Team_model {

    String name, dept, image,contact;

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Team_model(String name, String dept, String image, String contact) {
        this.name = name;
        this.dept = dept;
        this.image = image;
        this.contact = contact;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Team_model() {
    }

    public Team_model(String name, String dept, String image) {
        this.name = name;
        this.dept = dept;
        this.image = image;
    }
}
