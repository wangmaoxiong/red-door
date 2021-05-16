package com.wmx.reddoor.jpa.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * 手机实体类
 * <p>
 * * @Entity :告诉 JPA 本类是一个与数据库表进行映射的实体类，而不是普通的 Java Bean
 * * @Table ：指定本映射实体类与数据库哪个表进行映射，不写时默认为类名首字母小写（area）
 *
 * @author wangMaoXiong
 * @version 1.0
 * @date 2021/5/16 13:18
 */

@Entity
@Table(name = "iphone")
public class Iphone {
    /**
     * @Id : 指定此字段为数据库主键
     * @GeneratedValue :指定主键生成的策略(strategy)，可以选择如下：
     * TABLE：由程序保证主键的唯一性，比如程序设置主键值为 UUID
     * SEQUENCE：由 DB2、Oracle、SAP DB 等数据库 使用自己的 序列 进行管理生成
     * IDENTITY：由数据库自己进行管理，如 Mysql 的 自动递增
     * AUTO：让 ORM框 架自动选择，默认值
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * @Column :指定此属性映射数据库表的哪个字段，不写时默认为属性名
     * length ：指定此字段的长度，只对字符串有效，不写时默认为 255
     * unique ：是否添加唯一约束，不写时默认为 false
     * 更多详细属性，可以进入 javax.persistence.Column 查看
     */
    @Column(name = "name", length = 32, unique = true)
    private String name;

    /**
     * nullable ：表示此字段是否允许为 null，默认为 true
     */
    @Column(nullable = false)
    private Float price;

    //未指定注解时，作为普通字段，属性使用默认值
    private Date publishTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Iphone iphone = (Iphone) o;
        return Objects.equals(id, iphone.id) &&
                Objects.equals(name, iphone.name) &&
                Objects.equals(price, iphone.price) &&
                Objects.equals(publishTime, iphone.publishTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, publishTime);
    }

    @Override
    public String toString() {
        return "Iphone{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", publishTime=" + publishTime +
                '}';
    }
}
