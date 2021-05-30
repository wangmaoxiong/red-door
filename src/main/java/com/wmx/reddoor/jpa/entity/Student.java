package com.wmx.reddoor.jpa.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 学生实体类
 * * @Entity :告诉 JPA 本类是一个与数据库表进行映射的实体类，而不是普通的 Java Bean
 *
 * @author wangMaoXiong
 * @version 1.0
 * @date 2021/5/16 15:50
 */
@Entity
public class Student {

    //定义主键及生成规则
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private String sid;//学生ID

    @Column(nullable = false, length = 64)
    private String name;//姓名

    private Date birthday;//出生日期

    private Integer sexCode;//性别：1是男，0是女

    /**
     * 1、需要在哪一方建外键，则在哪一方设置 @OneToOne 注解
     * 2、cascade 属性表示级联操作选项，经过实测，这个属性似乎并没有生效，不过没有关系，可以自动手动去数据库修改即可。
     *
     * @JoinColumn 表示关联字段，属性用途如下：
     * A）name：指定【meal_card】表中外键的名称，默认为"关联表名称_关联表字段名称"，即默认为 "student_sid"
     * B）referencedColumnName：指定关联【student】表中的什么字段，默认为对方主键，即默认为 "sid"
     * C）nullable：外键值是否可以为空，默认为 true
     * D）@JoinColumn 注解可以省略不写，则默认会在【student】表中新建外键 meal_card_mid 并关联【MealCard】表的主键 mid
     */
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "meal_card_mid", referencedColumnName = "mid", nullable = true)
    private MealCard meal_card;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getSexCode() {
        return sexCode;
    }

    public void setSexCode(Integer sexCode) {
        this.sexCode = sexCode;
    }

    public MealCard getMeal_card() {
        return meal_card;
    }

    public void setMeal_card(MealCard meal_card) {
        this.meal_card = meal_card;
    }

    @Override
    public String toString() {
        return "Student{" +
                "sid='" + sid + '\'' +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", sexCode=" + sexCode +
                ", meal_card=" + meal_card +
                '}';
    }
}
