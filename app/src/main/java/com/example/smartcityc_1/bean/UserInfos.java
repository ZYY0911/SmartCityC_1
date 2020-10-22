package com.example.smartcityc_1.bean;

import java.io.Serializable;

/**
 * @Login Name win10
 * @Create by 张瀛煜 on 2020/10/8 at 10:23
 */
public class UserInfos implements Serializable {

    /**
     * id : 371402199902041133
     * name : 赵
     * avatar : http://localhost:8080/mobileA/images/user7.png
     * phone : 13505349999
     * sex : female
     */


    private String usreid;

    public String getUsreid() {
        return usreid;
    }

    public void setUsreid(String usreid) {
        this.usreid = usreid;
    }

    private String id;
    private String name;
    private String avatar;
    private String phone;
    private String sex;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
