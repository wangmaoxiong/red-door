<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <title>FreeMarker 语法快速入门</title>
</head>
<body>
<header style="margin-left: 10%">
    <h3 class="h3">FreeMarker 语法快速入门 &nbsp;&nbsp;<span style="font-size: 12px">当前时间：${.now}</span></h3>

    <p>基本类型：userName= ${userName}</p>
    <p>"数组类型："${array[0]}、${array[1]}、${array[2]}</p>
    <p>Map类型：id=${map.id?c}、name=${map.name}、
        <span class="badge badge-primary">${map.person.pid}</span>
        <span class="badge badge-primary">${map.person.name}</span>
        <span class="badge badge-primary">${map.person.sex}</span>
        <span class="badge badge-primary">${map.person.birthday?datetime}</span>
        <span class="badge badge-primary">${map.person.salary?c}</span>
    </p>

    <p>POJO类型：pid=${person.pid}、name=${person.name}、sex=${person.sex}、birthday=${person.birthday?datetime}
        、salary=${person.salary?c}
        <#if person.salary < 8000>
            &nbsp;低产阶级
        <#elseif person.salary < 8000>
            &nbsp;中产阶级
        <#else >
            &nbsp;资本家
        </#if>
    </p>

    <p>List类型(元素为基本类型)：
        <#list basicList as b>
            <span class="badge badge-primary">${b}</span>
        </#list>
        --获取指定索引=1元素：${basicList[1]}
    </p>
    <p>List类型(元素为对象类型)：
    <table class="table">
        <thead>
        <tr>
            <th scope="col">序号</th>
            <th scope="col">姓名</th>
            <th scope="col">性别</th>
            <th scope="col">出生日期</th>
            <th scope="col">薪资</th>
            <th scope="col">是否已婚</th>
        </tr>
        </thead>
        <tbody>
        <#list personList as p>
            <#if p_index%2==0>
                <tr style="background-color: gray">
            <#else>
                <tr>
            </#if>
            <th scope="row" pid="${p.pid}">${p_index+1}</th>
            <td>${p.name}</td>
            <td>
                <#if p.sex==1>
                    男
                <#elseif p.sex == 0>
                    女
                <#else >
                    -
                </#if>
            </td>
            <td>${p.birthday?string("yyyy/MM/dd HH:mm:ss")}</td>
            <td>${p.salary?c}</td>
        <#--<td><#if p.married>是<#else >否</#if></td>-->
            <td>${p.married?string("是","否")}</td>
            </tr>
        </#list>
        </tbody>
    </table>
    --获取索引等于2的元素：${personList[2].name}
    </p>

    <#list personList as person>
        <tr>
            <td> 序号：${person_index+1}，</td>
            <td> 名称：${person.name}，</td>
            <td> 薪资：${person.salary?c}</td>
        </tr>
    </#list>

    <p>null 判断：testNull= ${testNull!"值为null"}、testNull= ${testNull!""}、testNull=${testNull!}</p>
    <p>空值 判断：testBlank= ${testBlank!"值为空"}、testBlank= ${testBlank!""}、testBlank=${testBlank!}</p>
    <p><#if testBlank == "">testBlank 值为空！</#if></p>

    <#if testNull??>
        <p class="text-left">testNull=${testNull}</p>
    <#else >
        <p class="text-left">testNull 值为null，或者key不存在</p>
    </#if>

    <#if testNull?exists>
        <p class="text-left">testNull=${testNull}</p>
    <#else >
        <p class="text-left">testNull 值为null，或者key不存在</p>
    </#if>

    <p class="text-left">首字母自动大写：${personA1.name?cap_first}</p>
    <p class="text-left">首字母自动小写：${personA1.name?uncap_first}</p>
    <p class="text-left">字母自动全小写：${personA1.name?lower_case}，字符串替换：${personA1.name?replace("MX","猫熊")}</p>
    <p class="text-left">字母自动全大写：${personA1.name?upper_case}，内容长度：${personA1.name?length}</p>

    <button type="button" class="btn btn-primary btn-lg btn-block">
        字符串转数字：${personA1.pid?eval + 100}、
        字符串转数字：${personA1.pid?number + 200}
    </button>
    <button type="button" class="btn btn-primary btn-lg btn-block">
        数字转字符串：${personA1.salary?c}、
        数字转货币：${personA1.salary?string("currency")}、
        数字转百分比：${personA1.salary?string("percent")}
    </button>

    <p class="text-left"><#if person.name == "张三">字符串值比较</#if> </p>
    <p class="text-left"><#if person.name?contains("三")>是否包含子字符串</#if> </p>
    <p class="text-left">获取字符串的索引位置：${person.name?index_of("三")}</p>
    <p class="text-left">截取子字符串：${personA1.name?substring(4)}</p>

    <p class="text-left"><#if person.married>已婚</#if></p>

</header>

<#--引入公共的底部模板-->
<#include "./commFooter.ftl">

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/js/bootstrap.min.js"
        integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        crossorigin="anonymous"></script>
</body>
</html>