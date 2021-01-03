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
 * 根据 .xml 文件内容生成静态的 采购合同(.docx)
 *
 * @author wangMaoXiong
 * @version 1.0
 * @date 2021/1/3 15:54
 */
public class PurchaseContract {

    @Test
    public void purchaseContract() throws IOException, TemplateException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
        configuration.setDirectoryForTemplateLoading(new File("E:/IDEA_Projects/red-door/src/main/resources/ftl"));
        configuration.setDefaultEncoding("utf-8");
        Template template = configuration.getTemplate("purchaseContract2.ftl");

        File homeDirectory = FileSystemView.getFileSystemView().getHomeDirectory();
        File file = new File(homeDirectory, "/freeMarker/purchaseContract2.doc");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

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

        FileWriter writer = new FileWriter(file);
        template.process(model, writer);
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
