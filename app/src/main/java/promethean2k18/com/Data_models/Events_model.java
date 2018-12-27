package promethean2k18.com.Data_models;

public class Events_model {

    String eventId,eventName, organisingDept, desc, rules, tags, price, co1, co2,imageUrl,co1Phone,co2Phone,type,e_email,price_team;

    public String getType() {
        return type;
    }

    public String getPrice_team() {
        return price_team;
    }

    public void setPrice_team(String price_team) {
        this.price_team = price_team;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCo1Phone() {
        return co1Phone;
    }

    public String getE_email() {
        return e_email;
    }

    public void setE_email(String e_email) {
        this.e_email = e_email;
    }

    public void setCo1Phone(String co1Phone) {
        this.co1Phone = co1Phone;
    }

    public String getCo2Phone() {
        return co2Phone;
    }

    public void setCo2Phone(String co2Phone) {
        this.co2Phone = co2Phone;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Events_model() {

    }

    public Events_model(String eventId, String eventName, String organisingDept, String desc, String rules, String tags, String price, String co1, String co2, String imageUrl, String co1Phone, String co2Phone) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.organisingDept = organisingDept;
        this.desc = desc;
        this.rules = rules;
        this.tags = tags;
        this.price = price;
        this.co1 = co1;
        this.co2 = co2;
        this.imageUrl = imageUrl;
        this.co1Phone = co1Phone;
        this.co2Phone = co2Phone;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getOrganisingDept() {
        return organisingDept;
    }

    public void setOrganisingDept(String organisingDept) {
        this.organisingDept = organisingDept;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCo1() {
        return co1;
    }

    public void setCo1(String co1) {
        this.co1 = co1;
    }

    public String getCo2() {
        return co2;
    }

    public void setCo2(String co2) {
        this.co2 = co2;
    }
}
