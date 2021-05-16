package com.wmx.reddoor.jpa.controller;

import com.wmx.reddoor.jpa.dao.IphoneRepository;
import com.wmx.reddoor.jpa.entity.Iphone;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * {@link Iphone} 对应的控制层 —— 演示 JpaRepository 的常用 API
 *
 * @author wangMaoXiong
 * @version 1.0
 * @date 2021/5/16 14:08
 */
@RestController
public class IphoneController {
    @Resource
    private IphoneRepository iphoneRepository;

    /**
     * 新增数据
     * http://localhost:8317/iphone/save?name=华为P30&price=4488.00&publishTime=2018/08/15
     * http://localhost:8317/iphone/save?name=华为P31&price=4588.00&publishTime=2019/08/15
     * http://localhost:8317/iphone/save?name=小米11&price=4388.00&publishTime=2020/08/15
     *
     * @param iphone
     * @return 添加成功后重定向到查询所有
     */
    @GetMapping("iphone/save")
    public String saveIphone(Iphone iphone) {
        System.out.println("新增数据：" + iphone);
        /**
         * 1、save 方法：当实体的主键不存在时，则添加；实体的主键存在时，则更新
         * 2、如果是交由数据库自动设置主键，则 save 方法内部会自动设置主键的值
         */
        iphoneRepository.save(iphone);
        return "新增成功：" + iphone;
    }

    /**
     * 更新数据
     * http://localhost:8317/iphone/update?id=1&name=华为P30&price=2488.00&publishTime=2018/08/15
     *
     * @param iphone
     * @return
     */
    @GetMapping("/iphone/update")
    public String updateIphone(Iphone iphone) {
        System.out.println("更新数据：" + iphone);
        //save 方法：当实体的主键不存在时，则添加；实体的主键存在时，则更新
        iphoneRepository.save(iphone);
        return "更新成功：" + iphone;
    }

    /**
     * 根据主键 id 删除数据
     * http://localhost:8317/iphone/del/1
     *
     * @return
     */
    @GetMapping("/iphone/del/{id}")
    public String deleteIphoneById(@PathVariable("id") Integer id) {
        System.out.println("删除数据：id=" + id);

        //void deleteById(ID id)：如果主键 id 不存在，则删除报错，所以删除前必须进行判断
        //boolean isPresent()：如果存在值，则返回 true，否则返回 false
        Optional<Iphone> iphoneOptional = iphoneRepository.findById(id);
        if (iphoneOptional.isPresent()) {
            iphoneRepository.deleteById(id);
        }
        return "删除成功：id=" + id;
    }


    /**
     * 删除表中所有数据
     * http://localhost:8317/iphone/delAll
     *
     * @return
     */
    @GetMapping("iphone/delAll")
    public String deleteAll() {
        System.out.println("删除表中所有数据");
        iphoneRepository.deleteAll();
        return "删除表中所有数据成功！";
    }

    /**
     * 根据主键 id 查询
     * http://localhost:8317/iphone/1
     *
     * @param id
     * @return
     */
    @GetMapping("/iphone/{id}")
    public Iphone findIphoneById(@PathVariable("id") Integer id) {
        Iphone iphone = null;

        //boolean isPresent()：如果存在值，则返回 true，否则返回 false
        //推荐使用 findById 方式，而不是 getOne方法，isPresent 判断 Optional 是否为空，即有没有值
        Optional<Iphone> iphoneOptional = iphoneRepository.findById(id);
        if (iphoneOptional.isPresent()) {
            iphone = iphoneOptional.get();
        }
        System.out.println("根据主键 id 查询，id=" + id + "\n" + iphone);
        return iphone;
    }

    /**
     * 查询表中所有数据
     * http://localhost:8317/iphone
     *
     * @return
     */
    @GetMapping("/iphone")
    public List<Iphone> findAll() {
        List<Iphone> IphoneList = iphoneRepository.findAll();
        return IphoneList;
    }


}

