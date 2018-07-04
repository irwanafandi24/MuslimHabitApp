package com.illiyinmagang.miafandi.muslimhabitapp.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by user on 04/07/2018.
 */

public class User extends RealmObject {
    @PrimaryKey
    private int id;
    private String username, email, password, nama;

    public User(String username, String email, String password, String nama, int id) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nama = nama;
        this.id = id;
    }

    public User() {
    }

    public User(String username, String email, String password, String nama) {

        this.username = username;
        this.email = email;
        this.password = password;
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
