<%--
  Created by IntelliJ IDEA.
  User: Z
  Date: 2017/10/28
  Time: 14:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>显示数据记录</title>
    <script type="text/javascript" src="../js/echarts.min.js"></script>

    <link rel="icon" href="bootstrap-3.4.1/docs/favicon.ico">
    <link rel="canonical" href="https://getbootstrap.com/docs/3.4/examples/signin/">
    <link href="bootstrap-3.4.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="bootstrap-3.4.1/docs/assets/css/ie10-viewport-bug-workaround.css" rel="stylesheet">
    <link href="signin.css" rel="stylesheet">
    <script src="bootstrap-3.4.1/docs/assets/js/ie-emulation-modes-warning.js"></script>

    <%--<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.min.js"></script>--%>
    <%--<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-3.2.0.min.js"></script>--%>
    <%--<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts-all-3.js"></script>--%>
</head>
<%--<div id="myalert" class="alert alert-warning" >--%>
    <%--<a href="javascript:history.back(-1);" class="alert-link">该时间段暂无数据，点击返回</a>--%>
<%--</div>--%>
<body style="height: 100%; margin: 0; background-color: #F7F7F7">
<%--<h3>${requestScope.temperature}</h3>--%>
<%--<h3>${requestScope.oxygen_capacity}</h3>--%>
<%--<h3>${requestScope.Ammonia}</h3>--%>
<%--<h3>${requestScope.pH}</h3>--%>
<%--<h3>${requestScope.year}</h3>--%>
<%--<h3>${requestScope.month}</h3>--%>
<%--<h3>${requestScope.day}</h3>--%>
<style type="text/css">
    h3{
        font-size: 14px;
        color: white;
        display: inline;
    }

</style>
<div id="container" style="height: 80%; width: 90%; float: none"></div>
<%--<div id="container2" style="height: 80%; width: 50%; float:right"></div>--%>

<script type="text/javascript">
    var temperature = "${requestScope.temperature}"
    var oxygen_capacity = "${requestScope.oxygen_capacity}"
    var Ammonia = "${requestScope.Ammonia}"
    var pH = "${requestScope.pH}"

    var date = "${requestScope.date}"

    callLog1();

    function callLog1() {
        var dom = document.getElementById("container");
        var myChar1 = echarts.init(dom);
        if(temperature.length<1||Ammonia.length<1||pH.length<1||date.length<1){
            alert("该时段暂无数据");
        }
        myChar1.showLoading();
        var option = {
            title: {
                text: '折线图堆叠'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: ['温度', '含氧量', '氨氮含量', 'pH值']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: date.split(",")
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    name: '温度',
                    type: 'line',
                    stack: '总量',
                    data: temperature.split(",")
                },
                {
                    name: '含氧量',
                    type: 'line',
                    stack: '总量',
                    data: oxygen_capacity.split(",")
                },
                {
                    name: '氨氮含量',
                    type: 'line',
                    stack: '总量',
                    data: Ammonia.split(",")
                },
                {
                    name: 'pH值',
                    type: 'line',
                    stack: '总量',
                    data: pH.split(",")
                },

            ]
        };
        myChar1.setOption(option)
        myChar1.hideLoading();

    }


</script>
</body>
</html>
