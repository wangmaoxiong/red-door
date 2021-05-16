package com.wmx.reddoor.jpa.dao;

import com.wmx.reddoor.jpa.entity.Iphone;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * {@link Iphone} 对应的 Spring Data JPA 持久化层
 * <p>
 * 自定义接口继承 JpaRepository<T, ID> 接口
 * * @param T ：泛型，传入操作的实体类即可
 * * @param TD：传入实体类主键的类型
 * 1、继承 JpaRepository 之后，本接口就拥有了它的所有的 CRUD 、排序、分页方法。
 * 2、如果是以前 Hibernate 则还需要在 Dao 层的实现类上加 @Repository 注解注入每一个 Dao 实现组件
 * 3、而自定义的 Spring Data JPA 接口可以不用加 @Repository 注解，直接在 service 层或者 controller 层注入即可
 *
 * @author wangMaoXiong
 * @version 1.0
 * @date 2021/5/16 13:39
 */
public interface IphoneRepository extends JpaRepository<Iphone, Integer> {

}
