<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Under_Water_Robot_Date</title>

    <link rel="icon" href="bootstrap-3.4.1/docs/favicon.ico">
    <link rel="canonical" href="https://getbootstrap.com/docs/3.4/examples/signin/">
    <link href="bootstrap-3.4.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="bootstrap-3.4.1/docs/assets/css/ie10-viewport-bug-workaround.css" rel="stylesheet">
    <link href="signin.css" rel="stylesheet">
    <script src="bootstrap-3.4.1/docs/assets/js/ie-emulation-modes-warning.js"></script>

    <script language="javascript" type="text/javascript" src="My97DatePicker/calendar.js"></script>
    <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>

</head>
<body style="background: url(images/bg.jpg)">
<div class="container" style="margin-top: 64px">
    <form class="form-signin" action="/queryCallLogList" method="post">
        <h2 class="form-signin-heading" style="text-align: center;color: #1b809e">Welcome</h2>
        <h2 class="form-signin-heading" style="text-align: center;color: #1b809e">Please select condition</h2>

        <div class="input-group" style="margin-bottom: 5px">
            <span class="input-group-addon" >请选择查询年份</span>
            <input type="text" class="form-control" name="year" onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy'})" readonly>
        </div>

        <div class="input-group" style="margin-bottom: 5px">
            <span class="input-group-addon" >请选择查询月份</span>
            <input type="text" class="form-control" name="month" onfocus="WdatePicker({lang:'zh-cn', dateFmt:'MM'})" readonly>
        </div>

        <div class="input-group" style="margin-bottom: 5px">
            <span class="input-group-addon" >请选择查询天数</span>
            <input type="text" class="form-control" name="day" onfocus="WdatePicker({lang:'zh-cn', dateFmt:'dd'})" readonly>
        </div>
        <div class="input-group" style="margin-bottom: 5px">
            <span class="input-group-addon" >请选择查询时间</span>
            <input type="text" class="form-control" name="hour" onfocus="WdatePicker({lang:'zh-cn', dateFmt:'HH'})" readonly>
        </div>

        <button class="btn btn-lg btn-primary btn-block" type="submit">查询</button>
        <h3 class="form-signin-heading" style="text-align: center;color: #ffffff">建议输入 2017-01-04-07<br/>
        温馨提示：目前还处于测试阶段，数据暂时只有2017年的数据<br/>
        若文本框中未填数据，默认该时段全部数据<br/>
        例如：2017-01表示2017年1月的所有数据<br/></h3>

    </form>
</div>

<%--<form action="/queryCallLogList" method="post">--%>


    <%--请选择查询时间(精确到月份）<input name="date" type="text" onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM'})">--%>
    <%--<input type="submit" value="查询" class="btn btn-primary"><br/>--%>
    <%--请选择查询时间(精确到天数）<input name="date" type="text" onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd'})">--%>
    <%--<input type="submit" value="查询" class="btn btn-primary"><br/>--%>
    <%--请选择查询时间(精确到小时）<input name="date" type="text" onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd HH'})">--%>
    <%--<input type="submit" value="查询" class="btn btn-primary">--%>
    <%----%>
<%--</form>--%>
</body>
</html>
