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
<div class="row">
    <div class="col-xs-12">
        <div class="table-responsive">
            <table id="sample-table-1" class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>Job ID</th>
                    <th>Job Name</th>
                    <th>周期</th>
                    <th>
                        <i class="icon-time bigger-110">下次执行日期</i>
                    </th>
                    <th class="hidden-480">优先级</th>
                    <th class="hidden-480">线程数</th>
                    <th class="hidden-480">Status</th>


                    <th></th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${crawlerJobList}" var="i">
                        <td><c:out value="${i.jobId}"/></td>
                        <td><c:out value="${i.name}"/></td>
                        <td><fmt:formatNumber value="${(i.period/(1000*60*60*24))}" type="NUMBER" maxFractionDigits="0"></fmt:formatNumber>D
                            <fmt:formatNumber value="${(i.period%(1000*60*60*24))/(1000*60*60)}" type="NUMBER" maxFractionDigits="0"></fmt:formatNumber>:
                            <fmt:formatNumber value="${(i.period%(1000*60*60))/(1000*60)}" type="NUMBER" maxFractionDigits="0"></fmt:formatNumber>:
                            <fmt:formatNumber value="${(i.period%(1000*60))/(1000)}" type="NUMBER" maxFractionDigits="0"></fmt:formatNumber>
                        </td>
                        <td><fmt:formatDate value="${i.nextStartDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td><c:out value="${i.priority}"/></td>
                        <td><c:out value="${i.maxThread}"/></td>
                        <td class="hidden-480">
                            <c:if test='${i.status=="run"}'><span class="label label-sm label-success">${i.status}</span></c:if>
                            <c:if test='${i.status=="off"}'><span class="label label-sm label-inverse arrowed-in">${i.status}</span></c:if>
                            <c:if test='${i.status=="standby"}'><span class="label label-sm label-info">${i.status}</span></c:if>
                            <c:if test='${i.status=="stopped"}'><span class="label label-sm label-danger">${i.status}</span></c:if>
                        </td>

                        <td>
                            <div class="visible-md visible-lg hidden-sm hidden-xs btn-group">
                                <button class="btn btn-xs btn-success" onclick="stat">
                                    <i class="icon-ok bigger-120"></i>
                                </button>

                                <button class="btn btn-xs btn-info" onclick="window.location.href='edit.action?jobId=${i.jobId}'">
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

                </tbody>
            </table>
        </div><!-- /.table-responsive -->
    </div><!-- /span -->
</div><!-- /row -->


</div><!-- /.main-container -->

<!-- basic scripts -->

<!--[if !IE]> -->

<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>

<!-- <![endif]-->

<!--[if IE]>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<![endif]-->

<!--[if !IE]> -->

<script type="text/javascript">
    window.jQuery || document.write("<script src='assets/js/jquery-2.0.3.min.js'>" + "<" + "/script>");
</script>

<!-- <![endif]-->

<!--[if IE]>
<script type="text/javascript">
    window.jQuery || document.write("<script src='assets/js/jquery-1.10.2.min.js'>" + "<" + "/script>");
</script>
<![endif]-->

<script type="text/javascript">
    if ("ontouchend" in document) document.write("<script src='assets/js/jquery.mobile.custom.min.js'>" + "<" + "/script>");
</script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/typeahead-bs2.min.js"></script>

<!-- page specific plugin scripts -->

<script src="assets/js/jquery.dataTables.min.js"></script>
<script src="assets/js/jquery.dataTables.bootstrap.js"></script>

<!-- ace scripts -->

<script src="assets/js/ace-elements.min.js"></script>
<script src="assets/js/ace.min.js"></script>

<!-- inline scripts related to this page -->

<script type="text/javascript">
    jQuery(function ($) {
        var oTable1 = $('#sample-table-2').dataTable({
            "aoColumns": [
                {"bSortable": false},
                null, null, null, null, null,
                {"bSortable": false}
            ]
        });


        $('table th input:checkbox').on('click', function () {
            var that = this;
            $(this).closest('table').find('tr > td:first-child input:checkbox')
                .each(function () {
                    this.checked = that.checked;
                    $(this).closest('tr').toggleClass('selected');
                });

        });


        $('[data-rel="tooltip"]').tooltip({placement: tooltip_placement});
        function tooltip_placement(context, source) {
            var $source = $(source);
            var $parent = $source.closest('table')
            var off1 = $parent.offset();
            var w1 = $parent.width();

            var off2 = $source.offset();
            var w2 = $source.width();

            if (parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2)) return 'right';
            return 'left';
        }
    })
</script>
<div style="display:none">
    <script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540' language='JavaScript' charset='gb2312'></script>
</div>
</body>
</html>
