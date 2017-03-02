package com.zhangzhaowen.domain;

import java.io.Serializable;

/**
 * Created by zhangzhaowen on 2016/7/12.9:43
 * Describe:
 */
public class User  implements java.io.Serializable{

    private static final long serialVersionUID = -4249766384111370194L;
    private Integer id;
    private String userName;
    private String realName;
    private String passWord;
    private String ORGANID;
    private String userDept;
    private String userJob;
    private String mobilePhone;
    private String telephone;
    private String idCard;
    private String email;
    private String gender;
    private String address;
    private String IP;
    private String lastLoginTime;
    private String createName;
    private String createDate;
    private String userType;
    private Integer status;
    private String RESERVER3;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getORGANID() {
        return ORGANID;
    }

    public void setORGANID(String ORGANID) {
        this.ORGANID = ORGANID;
    }

    public String getUserDept() {
        return userDept;
    }

    public void setUserDept(String userDept) {
        this.userDept = userDept;
    }

    public String getUserJob() {
        return userJob;
    }

    public void setUserJob(String userJob) {
        this.userJob = userJob;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRESERVER3() {
        return RESERVER3;
    }

    public void setRESERVER3(String RESERVER3) {
        this.RESERVER3 = RESERVER3;
    }

    @Override
    public String toString(){
        return "id:"+id + " userName:" + userName+" realName:"+realName;
    }
}
