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
    <h3 class="h3">FreeMarker 语法快速入门</h3>

    <p>基本类型：userName= ${userName}</p>

    <p>Map类型：id=${map.id?c}、name=${map.name}、
        <span class="badge badge-primary">${map.person.pid}</span>
        <span class="badge badge-primary">${map.person.name}</span>
        <span class="badge badge-primary">${map.person.sex}</span>
        <span class="badge badge-primary">${map.person.birthday?datetime}</span>
        <span class="badge badge-primary">${map.person.salary?c}</span>
    </p>

    <p>POJO类型：pid=${person.pid}、name=${person.name}、sex=${person.sex}、birthday=${person.birthday?datetime}
        、salary=${person.salary?c}</p>

    <p>List类型(元素为基本类型)：
        <#list basicList as b>
            <span class="badge badge-primary">${b}</span>
        </#list>
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
            <td>${p.sex}</td>
            <td>${p.birthday?string("yyyy/MM/dd HH:mm:ss")}</td>
            <td>${p.salary?c}</td>
            </tr>
        </#list>
        </tbody>
    </table>
    </p>

    <p>null 判断：testNull= ${testNull!"值为null"}、testNull= ${testNull!""}、testNull=${testNull!}</p>
    <p>空值 判断：testBlank= ${testBlank!"值为null"}、testBlank= ${testBlank!""}、testBlank=${testBlank!}</p>

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

</header>
<footer>
</footer>
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