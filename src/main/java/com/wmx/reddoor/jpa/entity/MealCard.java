package com.wmx.reddoor.jpa.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 学生餐卡实体类
 * 与 {@link Student} 是一对一关系，一个学生一张餐卡
 * <p>
 * * @Entity :告诉 JPA 本类是一个与数据库表进行映射的实体类，而不是普通的 Java Bean
 * * 1、当实体类名称是驼峰命名，且没有使用 @Table 指定表名，则默认表名为 meal_card
 *
 * @author wangMaoXiong
 * @version 1.0
 * @date 2021/5/16 15:55
 */
@Entity
public class MealCard {

    //定义主键及主键生成规则
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private String mid;//参考卡号

    private Date createTime;//制卡日期

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "MealCard{" +
                "mid='" + mid + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
