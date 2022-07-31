package com.example.roomdatabasedemo.entity;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.roomdatabasedemo.bean.Address;

import java.util.Date;
import java.util.List;

@Entity(tableName = "users", indices = {@Index(value = {"first_name", "last_name"}, unique = true)})
public class UserInfo {

    @NonNull
    @PrimaryKey
    private String idCard; //必须要有，且不为空，根据主键进行数据更新和删除（注意唯一性）

    private int age;
    private int sex;
    private Date birthday;
    private List<String> friends;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    @Ignore
    private Bitmap picture;


    /**
     * 对象嵌套，Address对象中所有字段，也都会被映射到users表中
     * 同时也支持Address内部还有嵌套对象
     */
    @Embedded
    private Address address;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "idCard='" + idCard + '\'' +
                ", age=" + age +
//                ", sex=" + sex +
//                ", birthTime=" + birthday +
                ", friends=" + friends +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", picture=" + picture +
//                ", address=" + address +
                '}';
    }
}
