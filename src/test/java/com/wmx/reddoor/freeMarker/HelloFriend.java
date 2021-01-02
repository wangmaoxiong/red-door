package com.wmx.reddoor.freeMarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * FreeMarker 入门示例
 *
 * @author wangMaoXiong
 * @version 1.0
 * @date 2021/1/2 9:06
 */
public class HelloFriend {

    /**
     * FreeMarker 模板
     */
    private Template template = null;
    private FileWriter writer = null;
    private File file = null;

    @Before
    public void init() throws IOException {
        //1.创建 freemarker 模板的 configuration 配置对象。
        // 指定当前兼容的版本号，官方不推荐使用 Configuration.getVersion()，2.3.30 版本时已经会报错提示，将来2.4.0时会直接抛异常
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);

        //2.设置模板文件所在的目录
        configuration.setDirectoryForTemplateLoading(new File("E:/IDEA_Projects/red-door/src/main/resources/ftl"));

        //3.设置模板文件的默认字符集
        configuration.setDefaultEncoding("utf-8");

        //4.加载模板文件(相对路径)，官方默认后缀名是 .ftl，实际上任意格式都可以，自定义都行
        template = configuration.getTemplate("helloWorld.ftl");

        //6.创建一个 FileWriter 文件输出流对象，指定生成的静态文件存放的路径及文件名.
        //暂时输出到桌面，存放文件的目录必须存在，否则 FileNotFoundException 异常
        File homeDirectory = FileSystemView.getFileSystemView().getHomeDirectory();
        file = new File(homeDirectory, "/freeMarker/helloFriend.html");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
    }

    @After
    public void destroy() throws IOException {
        //8.操作结束，关闭流
        writer.flush();
        writer.close();
        System.out.println("FreeMarker 生成文件：" + file.getAbsolutePath());
    }

    /**
     * FreeMarker 入门示例
     *
     * @throws Exception
     */
    @Test
    public void helloFriend() throws Exception {
        //1.创建模板文件需要展示的数据模型，通常使用 Map 就可以。
        Map model = new HashMap<>();

        //基本数据类型
        model.put("userName", "蚩尤后裔");

        //map 类型
        Map<String, Object> employeeMap = new HashMap<>();
        employeeMap.put("id", 9527);
        employeeMap.put("name", "华安");
        employeeMap.put("person", new Person("1020", "张三风", 1, new Date(), 19500.10F));
        model.put("map", employeeMap);

        //POJO 类型
        Person person = new Person("1000", "张三", 1, new Date(), 9500.00F);
        model.put("person", person);

        //List 类型：元素为基本类型
        List<String> basicList = Arrays.asList(new String[]{"emp", "dept", "student"});
        model.put("basicList", basicList);

        //List 类型：元素为对象类型
        /**
         * FreeMarker 处理日期：${person.birthday?date} ：表示取 yyyy-MM-dd，如 2021-1-2
         * FreeMarker 处理日期：${person.birthday?time} ：表示取 HH:mm:ss，如 13:02:45
         * FreeMarker 处理日期：${person.birthday?datetime} ：表示取 yyyy-MM-dd HH:mm:ss，如 2021-1-2 13:03:06
         * FreeMarker 处理日期：${person.birthday?string("yyyy/MM/dd HH:mm:ss")} ：自定义日期格式，如 2021/01/02 13:04:31
         */
        List<Person> personList = new ArrayList<>();
        Person person2 = new Person("1002", "李斯", 1, new Date(), 11500.00F);
        Person person3 = new Person("1004", "裘千尺", 0, new Date(), 13500.45F);
        personList.add(person);
        personList.add(person2);
        personList.add(person3);
        model.put("personList", personList);

        //null 类型，空字符串类型，空格。
        // Thymeleaf 取值时，如果 key 不存在或者值为 null，则直接不处理，也不会异常。
        // 而 FreeMarker 则不同，取值时 key 必须存在且不能为 null，否则生成静态文件直接报错，所以必须先判断
        model.put("testNull", null);
        model.put("testBlank", "");
        model.put("testSpace", " ");

        //7.process(Object dataModel, Writer out)：使用提供的数据模型执行模板，将生成的文件写入到 out 输出流
        writer = new FileWriter(file);
        template.process(model, writer);
    }

}
