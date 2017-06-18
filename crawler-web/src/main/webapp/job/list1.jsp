<%@page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>控制台</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <!-- basic styles -->
    <link href="../assets/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="../assets/css/font-awesome.min.css" />
    <!--[if IE 7]>
    <link rel="stylesheet" href="../assets/css/font-awesome-ie7.min.css" />
    <![endif]-->
    <!-- page specific plugin styles -->
    <!-- fonts -->
    <%--<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" />--%>
    <!-- ace styles -->
    <link rel="stylesheet" href="../assets/css/ace.min.css" />
    <link rel="stylesheet" href="../assets/css/ace-rtl.min.css" />
    <link rel="stylesheet" href="../assets/css/ace-skins.min.css" />
    <!--[if lte IE 8]>
    <link rel="stylesheet" href="../assets/css/ace-ie.min.css" />
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
        window.jQuery || document.write("<script src='../assets/js/jquery-2.0.3.min.js'>"+"<"+"script>");
    </script>
    <script type="text/javascript">
        $(function() {
            $('#menu-job-list').addClass('active');
        });
    </script>
</head>

<body>
<jsp:include page="../common/top.jsp"/>

<div class="main-container" id="main-container">
    <jsp:include page="../common/sidebar.jsp"/>
    <jsp:include page="../common/settings.jsp"/>
    <script type="text/javascript">
        try{ace.settings.check('main-container' , 'fixed')}catch(e){}
    </script>
    <div class="main-container-inner">
        <div class="main-content"><!--导航栏 -->
            <div class="breadcrumbs" id="breadcrumbs">
                <script type="text/javascript">
                    try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
                </script>
                <ul class="breadcrumb">
                    <li>
                        <i class="icon-home home-icon"></i>
                        <a href="#">首页</a>
                    </li>
                    <li class="active">控制台</li>
                </ul><!-- .breadcrumb -->

                <div class="nav-search" id="nav-search">
                    <form class="form-search">
                                    <span class="input-icon">
                                        <input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
                                        <i class="icon-search nav-search-icon"></i>
                                    </span>
                    </form>
                </div><!-- #nav-search -->
            </div>
        </div><!--导航栏 -->
        <c:forEach items="${crawlerJobList}" var="i" >
        <tbody>
        <tr>
            <td class="center">
                <label>
                    <input type="checkbox" class="ace" />
                    <span class="lbl"></span>
                </label>
            </td>

            <td>
                <a href="#">ace.com</a>
            </td>
            <td>$45</td>
            <td class="hidden-480">3,330</td>
            <td>Feb 12</td>

            <td class="hidden-480">
                <span class="label label-sm label-warning">Expiring</span>
            </td>

            <td>
                <div class="visible-md visible-lg hidden-sm hidden-xs btn-group">
                    <button class="btn btn-xs btn-success">
                        <i class="icon-ok bigger-120"></i>
                    </button>

                    <button class="btn btn-xs btn-info">
                        <i class="icon-edit bigger-120"></i>
                    </button>

                    <button class="btn btn-xs btn-danger">
                        <i class="icon-trash bigger-120"></i>
                    </button>

                    <button class="btn btn-xs btn-warning">
                        <i class="icon-flag bigger-120"></i>
                    </button>
                </div>

                <div class="visible-xs visible-sm hidden-md hidden-lg">
                    <div class="inline position-relative">
                        <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown">
                            <i class="icon-cog icon-only bigger-110"></i>
                        </button>

                        <ul class="dropdown-menu dropdown-only-icon dropdown-yellow pull-right dropdown-caret dropdown-close">
                            <li>
                                <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
																				<span class="blue">
																					<i class="icon-zoom-in bigger-120"></i>
																				</span>
                                </a>
                            </li>

                            <li>
                                <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
																				<span class="green">
																					<i class="icon-edit bigger-120"></i>
																				</span>
                                </a>
                            </li>

                            <li>
                                <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
																				<span class="red">
																					<i class="icon-trash bigger-120"></i>
																				</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </td>
        </tr>
        </c:forEach>
    </div><!-- /.main-container-inner -->
</div><!--/.main-container-->
<!--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX -->
<!-- basic scripts -->
<script src="../assets/js/bootstrap.min.js"></script>
<script src="../assets/js/typeahead-bs2.min.js"></script>
<!-- page specific plugin scripts -->
<script src="../assets/js/excanvas.min.js"></script>
<script src="../assets/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="../assets/js/jquery.ui.touch-punch.min.js"></script>
<script src="../assets/js/jquery.slimscroll.min.js"></script>
<script src="../assets/js/jquery.easy-pie-chart.min.js"></script>
<script src="../assets/js/jquery.sparkline.min.js"></script>
<script src="../assets/js/flot/jquery.flot.min.js"></script>
<script src="../assets/js/flot/jquery.flot.pie.min.js"></script>
<script src="../assets/js/flot/jquery.flot.resize.min.js"></script>
<!-- ace scripts -->
<script src="../assets/js/ace-elements.min.js"></script>
<script src="../assets/js/ace.min.js"></script>
<!-- inline scripts related to this page -->
<script src="../assets/script/my.main.js"/>
</body>
</html>

