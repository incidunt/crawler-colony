<%@page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>控制台</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <!-- basic styles -->
    <link href="../assets/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="../assets/css/font-awesome.min.css"/>
    <!--[if IE 7]>
    <link rel="stylesheet" href="../assets/css/font-awesome-ie7.min.css"/>
    <![endif]-->
    <!-- page specific plugin styles -->
    <!-- fonts -->
    <%--<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" />--%>
    <!-- ace styles -->
    <link rel="stylesheet" href="../assets/css/ace.min.css"/>
    <link rel="stylesheet" href="../assets/css/ace-rtl.min.css"/>
    <link rel="stylesheet" href="../assets/css/ace-skins.min.css"/>
    <!--[if lte IE 8]>
    <link rel="stylesheet" href="../assets/css/ace-ie.min.css"/>
    <![endif]-->
    <!-- inline styles related to this page -->
    <!-- ace settings handler -->
    <script src="../assets/js/ace-extra.min.js"></script>
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="../assets/js/html5shiv.js"></script>
    <script src="../assets/js/respond.min.js"></script>
    <![endif]-->
    <script src="../assets/js/jquery-2.0.3.min.js"/>
    <script type="text/javascript">
        window.jQuery || document.write("<script src='../assets/js/jquery-2.0.3.min.js'>" + "<" + "script>");
    </script>
    <script type="text/javascript">
        $(function () {
            $('#menu-job-list').addClass('active');
        });
        function toEdit(jobId) {

        }
    </script>
</head>

<body>
<div class="breadcrumbs" id="breadcrumbs">
    <script type="text/javascript">
        try {
            ace.settings.check('breadcrumbs', 'fixed')
        } catch (e) {
        }
    </script>

    <ul class="breadcrumb">
        <li>
            <i class="icon-home home-icon"></i>
            <a href="#">首页</a>
        </li>
        <li class="active">任务列表</li>
        <li class="active">编辑</li>
    </ul><!-- .breadcrumb -->

    <div class="nav-search" id="nav-search">
        <form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input"
                                           id="nav-search-input" autocomplete="off"/>
									<i class="icon-search nav-search-icon"></i>
								</span>
        </form>
    </div><!-- #nav-search -->
</div>
<br/>
<form id="myform" class="form-horizontal" role="form" action="update.action" >
    <input type="hidden" name="projectId" value="${job.projectId}"/>
    <input type="hidden" name="sourceId" value="${job.sourceId}"/>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" >jobId</label>
        <div class="col-sm-9">
            <input type="text" value="${job.jobId}"  name="jobId" class="col-xs-10 col-sm-5"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" >jobName</label>
        <div class="col-sm-9">
            <input type="text" value="${job.name}" name="name" class="col-xs-10 col-sm-5"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" >周期</label>
        <div class="col-sm-9">
            <input type="text" value="${job.period}" name="period" class="col-xs-10 col-sm-5"/>
            <span class="help-inline col-xs-12 col-sm-7">
                <span class="middle">毫秒</span>
            </span>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" >nextStartDate</label>
        <div class="col-sm-9">
            <input type="text" value="<fmt:formatDate value="${job.nextStartDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/> " name="nextStartDate"/>
        </div>
    </div>
    <div class="form-group">
    <label class="col-sm-3 control-label no-padding-right" >状态</label>
    <div class="col-sm-9" width="200"  style="width:330px">
        <select class="form-control " id="" width="200" style="width:330px" name="status">
            <option selected="selected" value="${job.status}">${job.status}</option>
            <option value="start">start</option>
            <option value="stop">stop</option>
            <option value="standby">standby</option>
            <option value="goOn">goOn</option>
            <option value="kill">kill</option>
            <option value="off">off</option>
        </select>
    </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-16">优先级</label>
        <div class="col-sm-9">
            <input data-rel="tooltip" type="text" id="form-field-16" value="${job.priority}" name="priority"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right" for="form-field-17">最大线程数</label>
        <div class="col-sm-9">
            <input data-rel="tooltip" type="text" id="form-field-17" value="${job.maxThread}" name="maxThread"/>
        </div>
    </div>
    <div class="form-group" >
        <label class="col-sm-3 control-label no-padding-right" >note</label>
        <div class="col-sm-9">
            <input type="text" value="${job.note}" name="note" class="col-xs-10 col-sm-5"/>
        </div>
    </div>

    <div class="clearfix form-actions">
        <div class="col-md-offset-3 col-md-9">
            <button class="btn btn-info" type="button" onclick="document.getElementById('myform').submit();">
                <i class="icon-ok bigger-110"></i>
                Submit
            </button>
            &nbsp; &nbsp; &nbsp;
            <button class="btn" type="reset">
                <i class="icon-undo bigger-110"></i>
                Reset
            </button>
        </div>
    </div>

</form>
</body>
</html>
