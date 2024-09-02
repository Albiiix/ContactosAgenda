package com.example.contactosagenda;

public class ModelContact {

    private String id, name, image, phone, email, dir, note;

    public ModelContact(String id, String name, String image, String phone, String email, String dir, String note) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.phone = phone;
        this.email = email;
        this.dir = dir;
        this.note = note;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getDir() {
        return dir;
    }
    public void setDir(String dir) {
        this.dir = dir;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
}
