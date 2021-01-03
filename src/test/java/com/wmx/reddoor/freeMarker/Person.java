package com.wmx.reddoor.freeMarker;

import java.util.Date;

/**
 * 用户实体类
 *
 * @author wangMaoXiong
 * @version 1.0
 * @date 2021/1/2 11:02
 */
public class Person {
    private String pid;
    private String name;
    private Integer sex;
    private Date birthday;
    private Float salary;
    private Boolean married;

    public Person() {
    }

    public Person(String pid, String name, Integer sex, Date birthday, Float salary, Boolean married) {
        this.pid = pid;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.salary = salary;
        this.married = married;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public Boolean getMarried() {
        return married;
    }

    public void setMarried(Boolean married) {
        this.married = married;
    }
}
