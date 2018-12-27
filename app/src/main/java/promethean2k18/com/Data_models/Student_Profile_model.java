package promethean2k18.com.Data_models;

public class Student_Profile_model {

    private String name,email,phone,college,year,dept,roll, regEvents, uid;

    public Student_Profile_model() {
    }

    public Student_Profile_model(String name, String email, String phone, String college, String year, String dept, String roll, String regEvents, String uid) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.college = college;
        this.year = year;
        this.dept = dept;
        this.roll = roll;
        this.regEvents = regEvents;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getRegEvents() {
        return regEvents;
    }

    public void setRegEvents(String regEvents) {
        this.regEvents = regEvents;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
