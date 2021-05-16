package com.wmx.reddoor.jpa.controller;

import com.wmx.reddoor.jpa.dao.IphoneRepository;
import com.wmx.reddoor.jpa.entity.Iphone;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
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
     * 根据 id 查询
     *
     * @param id
     * @return
     */
    @GetMapping("/seals/{id}")
    public Iphone findIphoneById(@PathVariable("id") Integer id, HttpServletResponse response) {
        System.out.println("com.lct.www.controllr.IphoneController.findIphoneById:::" + id);
        /**
         * 推荐使用 findById 方式，而不是 getOne方法
         * isPresent 判断 Optional是否为空，即有没有值
         */
        Optional<Iphone> IphoneOptional = iphoneRepository.findById(id);
        if (!IphoneOptional.isPresent()) {
            return null;
        } else {
            System.out.println("Iphone2::" + IphoneOptional.get());
            /** 获取Iphone值*/
            return IphoneOptional.get();
        }
    }

    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping("/seals")
    public List<Iphone> findAllIphones() {
        List<Iphone> IphoneList = iphoneRepository.findAll();
        return IphoneList;
    }

    /**
     * 添加 区域
     * localhost:8080/seals/save?name=党建学习区&clients=1,2&createTime=2018/08/12
     * localhost:8080/seals/save?name=一学一做区&clients=4,5,6&createTime=2018/08/15
     *
     * @param Iphone
     * @return 添加成功后重定向到查询所有
     */
    @GetMapping("seals/save")
    public String saveIphone(Iphone Iphone) {
        /**
         * save 方法：当实体的主键不存在时，则添加；实体的主键存在时，则更新
         */
        iphoneRepository.save(Iphone);
        return "redirect:/seals";
    }

    /**
     * 更新区域
     * localhost:8080/seals/save?id=2&name=一学一做区2&clients=4,5,6&createTime=2018/08/15
     *
     * @param Iphone
     * @return
     */
    @GetMapping("/seals/update")
    public String updateIphone(Iphone Iphone) {
        /**
         * save 方法：当实体的主键不存在时，则添加；实体的主键存在时，则更新
         */
        iphoneRepository.save(Iphone);
        return "redirect:/seals";
    }

    /**
     * 根据主键 id 删除区域
     *
     * @return
     */
    @GetMapping("/seals/del/{id}")
    public String deleteIphoneById(@PathVariable("id") Integer id) {
        iphoneRepository.deleteById(id);
        return "redirect:/seals";
    }


    /**
     * 删除表中所有数据
     *
     * @return
     */
    @GetMapping("seals/delAll")
    public String deleteAll() {
        iphoneRepository.deleteAll();
        return "redirect:/seals";
    }

    /**
     * 另外还有分页查询、排序查询等，在此就不再一一测试
     */
}

