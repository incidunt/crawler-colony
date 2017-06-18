<%--
  Created by IntelliJ IDEA.
  User: dangqihe
  Date: 2016/10/8
  Time: 17:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加商品</title>
    <form action="add.action" method="post">
    <table colspan="1">
       <tr>
           <td>商品名称</td>
           <td><input type="text" name="name"></td>
       </tr>
        <tr>
            <td>商品价格</td>
            <td><input type="text" name="price"></td>
        </tr>
        <tr>
            <td>商品信息</td>
            <td><input type="text" name="detail"></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="添加"></td>
        </tr>
    </table>
    </form>
</head>
<body>

</body>
</html>
