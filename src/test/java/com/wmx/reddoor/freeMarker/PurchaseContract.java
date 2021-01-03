package com.wmx.reddoor.freeMarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * 为 xml 格式的 Word 文件注入数据，然后生成静态的 doc 格式文件
 *
 * @author wangMaoXiong
 * @version 1.0
 * @date 2021/1/3 15:54
 */
public class PurchaseContract {

    @Test
    public void purchaseContract() throws IOException, TemplateException {
        //1.创建 freemarker 模板的 configuration 配置对象。
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);

        //2.设置模板文件所在的目录
        configuration.setClassLoaderForTemplateLoading(PurchaseContract.class.getClassLoader(), "ftl");

        //3.设置模板文件的默认字符集
        configuration.setDefaultEncoding("utf-8");

        //4.加载模板文件(相对路径)，官方默认后缀名是 .ftl，实际上任意格式都可以，自定义都行。但是只有是 .ftl 时 IDEA 才会有语法提示
        Template template = configuration.getTemplate("purchaseContract.ftl");

        //6.创建一个 FileWriter 文件输出流对象，指定生成的静态文件存放的路径及文件名.
        //暂时输出到桌面，存放文件的目录必须存在，否则 FileNotFoundException 异常
        File homeDirectory = FileSystemView.getFileSystemView().getHomeDirectory();
        //只能生成 .doc 文件，如果生成的是 .docx 文件，则 Office Word  打开时会提示文件错误而打不开
        File file = new File(homeDirectory, "/freeMarker/采购合同.doc");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        //设置数据模型
        Map model = new HashMap<>();
        List<Map<String, Object>> productList = new ArrayList<>();
        this.setDataModel(productList);
        model.put("buyer", "长沙天鹰集团");//购货方
        model.put("supplier", "深圳华为技术有限责任公司");//供货方
        model.put("signingDate", new Date());//签约日期
        model.put("signingPlace", "长沙IFS国金中心2889");//签约地点
        model.put("contractNo", "BH7878TYR677");//合同编号
        model.put("totalContractAmountUpper", "壹仟玖佰陆拾壹万伍仟元整");//合同总额大写
        model.put("totalContractAmountLower", "19615000");//合同总额小写
        model.put("productList", productList);//产品数据列表

        //7.process(Object dataModel, Writer out)：使用提供的数据模型执行模板，将生成的文件写入到 out 输出流
        FileWriter writer = new FileWriter(file);
        template.process(model, writer);

        //8.操作结束，关闭流
        writer.flush();
        writer.close();
        System.out.println("FreeMarker 生成文件：" + file.getAbsolutePath());
    }

    private void setDataModel(List<Map<String, Object>> productList) {
        for (int i = 0; i < 5; i++) {
            Map<String, Object> product1 = new HashMap<>();
            product1.put("name", "螺丝刀");
            product1.put("model", "TS" + (i + 1));
            product1.put("brand", (i % 2 == 0 ? "小米" : "格力"));
            product1.put("unit", "把");
            product1.put("quantity", 34 + i);
            product1.put("unitPrice", 8.8 + i);
            product1.put("money", (34 + i) * (8.8 + i));
            product1.put("remarks", (i % 2 == 0 ? "新顾客" : "出货量大"));
            productList.add(product1);
        }

    }
}
