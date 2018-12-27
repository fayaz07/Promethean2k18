package promethean2k18.com.Data_models;

public class Register_model {
    String reg_token, event_name, event_id, part_uid, part_name, part_phone, part_email, part_referrer, event_imageUrl,
            participationStatus,data,comments,paymentStatus,organizerEmail,eventType,teammates,eventPriceIndividual,eventPriceTeam,fee;

    public String getParticipationStatus() {
        return participationStatus;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public void setParticipationStatus(String participationStatus) {

        this.participationStatus = participationStatus;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getOrganizerEmail() {
        return organizerEmail;
    }

    public void setOrganizerEmail(String organizerEmail) {
        this.organizerEmail = organizerEmail;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getTeammates() {
        return teammates;
    }

    public void setTeammates(String teammates) {
        this.teammates = teammates;
    }

    public String getEventPriceIndividual() {
        return eventPriceIndividual;
    }

    public void setEventPriceIndividual(String eventPriceIndividual) {
        this.eventPriceIndividual = eventPriceIndividual;
    }

    public String getEventPriceTeam() {
        return eventPriceTeam;
    }

    public void setEventPriceTeam(String eventPriceTeam) {
        this.eventPriceTeam = eventPriceTeam;
    }

    public String getEvent_imageUrl() {
        return event_imageUrl;
    }

    public void setEvent_imageUrl(String event_imageUrl) {
        this.event_imageUrl = event_imageUrl;
    }

    public Register_model(String reg_token, String event_name, String event_id, String part_uid, String part_name, String part_phone, String part_email, String part_referrer, String event_imageUrl) {
        this.reg_token = reg_token;
        this.event_name = event_name;
        this.event_id = event_id;
        this.part_uid = part_uid;
        this.part_name = part_name;
        this.part_phone = part_phone;
        this.part_email = part_email;
        this.part_referrer = part_referrer;
        this.event_imageUrl = event_imageUrl;
    }

    public Register_model() {
    }

    public String getReg_token() {
        return reg_token;
    }

    public void setReg_token(String reg_token) {
        this.reg_token = reg_token;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getPart_uid() {
        return part_uid;
    }

    public void setPart_uid(String part_uid) {
        this.part_uid = part_uid;
    }

    public String getPart_name() {
        return part_name;
    }

    public void setPart_name(String part_name) {
        this.part_name = part_name;
    }

    public String getPart_phone() {
        return part_phone;
    }

    public void setPart_phone(String part_phone) {
        this.part_phone = part_phone;
    }

    public String getPart_email() {
        return part_email;
    }

    public void setPart_email(String part_email) {
        this.part_email = part_email;
    }

    public String getPart_referrer() {
        return part_referrer;
    }

    public void setPart_referrer(String part_referrer) {
        this.part_referrer = part_referrer;
    }

    public Register_model(String reg_token, String event_name, String event_id, String part_uid, String part_name, String part_phone, String part_email, String part_referrer) {
        this.reg_token = reg_token;
        this.event_name = event_name;
        this.event_id = event_id;
        this.part_uid = part_uid;
        this.part_name = part_name;
        this.part_phone = part_phone;
        this.part_email = part_email;
        this.part_referrer = part_referrer;
    }
}
