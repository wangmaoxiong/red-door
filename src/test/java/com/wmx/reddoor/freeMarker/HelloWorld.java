package com.wmx.reddoor.freeMarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * FreeMarker 入门示例
 *
 * @author wangMaoXiong
 * @version 1.0
 * @date 2021/1/2 9:06
 */
public class HelloWorld {
    /**
     * FreeMarker 入门示例
     *
     * @throws Exception
     */
    @Test
    public void helloWorld() throws Exception {
        //1.创建 freemarker 模板的 configuration 配置对象。
        // 指定当前兼容的版本号，官方不推荐使用 Configuration.getVersion()，2.3.30 版本时已经会报错提示，将来2.4.0时会直接抛异常
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);

        //2.设置模板文件所在的目录
        configuration.setDirectoryForTemplateLoading(new File("E:/IDEA_Projects/red-door/src/main/resources/ftl"));

        //3.设置模板文件的默认字符集
        configuration.setDefaultEncoding("utf-8");

        //4.加载模板文件(相对路径)，官方默认后缀名是 .ftl，实际上任意格式都可以，自定义都行
        Template template = configuration.getTemplate("helloWorld.ftl");

        //5.创建模板文件需要展示的数据模型，通常使用 Map 就可以。
        Map model = new HashMap<>();
        //普通的数据类型
        model.put("userName", "蚩尤后裔");

        //6.创建一个 FileWriter 文件输出流对象，指定生成的静态文件存放的路径及文件名.
        //暂时输出到桌面，存放文件的目录必须存在，否则 FileNotFoundException 异常
        File homeDirectory = FileSystemView.getFileSystemView().getHomeDirectory();
        File file = new File(homeDirectory, "/freeMarker/helloWorld.html");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileWriter writer = new FileWriter(file);

        //7.process(Object dataModel, Writer out)：使用提供的数据模型执行模板，将生成的文件写入到 out 输出流
        template.process(model, writer);
        //8.操作结束，关闭流
        writer.flush();
        writer.close();
        System.out.println("FreeMarker 生成文件：" + file.getAbsolutePath());
    }

}
