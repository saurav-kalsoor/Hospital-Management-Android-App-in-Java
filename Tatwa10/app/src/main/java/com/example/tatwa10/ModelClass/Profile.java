package com.example.tatwa10.ModelClass;

public class Profile {
    private String name;
    private String email;
    private String phone;
    private boolean sex;
    private String age;

    public Profile(String name, String email, String phone, boolean sex, String age) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.sex = sex;
        this.age = age;
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

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    public Profile() {
    }
}
