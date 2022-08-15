<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Diamond Store</title>
    <jsp:include page="/WEB-INF/layout/meta_css.jsp"></jsp:include>
</head>

<body data-layout="horizontal">

<!-- Begin page -->
<div id="wrapper">
    <header id="topnav">
        <!-- Topbar Start -->
        <div class="navbar-custom">
            <div class="container-fluid">
                <ul class="list-unstyled topnav-menu float-right mb-0">
                    <li class="dropdown notification-list">
                        <a class="nav-link dropdown-toggle nav-user mr-0 waves-effect waves-light"
                           data-toggle="dropdown" href="#" role="button" aria-haspopup="false" aria-expanded="false">
                            <img src="assets\images\users\phap.png" alt="user-image" class="rounded-circle">
                        </a>
                    </li>
                </ul>
                <!-- LOGO -->
                <div class="logo-box">
                    <a href="/product?create=list" class="logo text-center">
                                    <span class="logo-lg">
                                        <img src="assets/images/animat-diamond-color.gif" alt="" height="90">
                                       <span class="logo-lg-text-light">DIAMOND</span>
                                    </span>
                        <span class="logo-sm">
                                        <img src="assets/images/animat-diamond-color.gif" alt="" height="80">
                                    </span>
                    </a>
                </div>
                <ul class="list-unstyled topnav-menu topnav-menu-left m-0">
                    <li class="d-none d-sm-block">
                        <form class="app-search">
                            <div class="app-search-box">
                                <div class="input-group">
                                    <input type="text" class="form-control" value="${requestScope.search}" name="search"
                                           placeholder="Search...">
                                    <div class="input-group-append">
                                        <button class="btn" type="submit">
                                            <i class="fas fa-search"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </li>
                </ul>
                <div class="clearfix"></div>
            </div>
        </div>
        <!-- end Topbar -->
        <div class="topbar-menu">
            <div class="container-fluid">
                <div id="navigation" class="active">
                    <!-- Navigation Menu-->
                    <ul class="navigation-menu in">

                        <li class="has-submenu active">
                            <a href="/products?action=list" class="active"> <i class="fa fa-list"></i>Product</a>
                        </li>
                        <li class="has-submenu">
                            <a href="/products?action=create">
                                <i class="fa fa-plus"></i>Create product
                            </a>
                        </li>
                    </ul>
                    <!-- End navigation menu -->
                    <div class="clearfix"></div>
                </div>
                <!-- end #navigation -->
            </div>
            <!-- end container -->
        </div>
        <!-- end navbar-custom -->
    </header>
    <div class="content-page">
        <div class="content">
            <!-- Start Content-->
            <div class="container-fluid">
                <div class="row">
                    <div class="col-xl-12">
                        <div class="card-box">
                            <h4 class="header-title mb-4" href="/products">Product List</h4>
                            <div class="table-responsive">
                                <table class="table table-hover table-centered m-0">
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Name</th>
                                        <th>Image</th>
                                        <th>Quantity</th>
                                        <th>Price</th>
                                        <th>Category</th>
                                        <th>Action</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="product" items="${requestScope.products}">
                                        <tr>
                                            <td><c:out value="${product.getId()}"/></td>
                                            <td><c:out value="${product.getName()}"/></td>
                                                <%--                                            <td><c:out value="${product.getImg()}"/></td>--%>
                                            <td><img src="images/${product.getImg()}" width="40px" height="60px"></td>
                                            <td><c:out value="${product.getQuantity()}"/></td>
                                            <td>
                                                <c:set var="price" value="${product.getPrice()}"/>
                                                <fmt:setLocale value="vi_VN"/>
                                                <fmt:formatNumber value="${price}" type="currency"/>
                                            </td>
                                            <td>
                                                <c:forEach items="${applicationScope.listCategories}" var="category">
                                                    <c:if test="${category.getId()==product.getIdCategory()}">
                                                        <c:out value="${category.getName()}"/>
                                                    </c:if>
                                                </c:forEach>
                                            </td>
                                            <td>
                                                <a href="/products?action=edit&id=${product.getId()}"
                                                   class="mdi mdi-settings-outline"></a>
                                                <a href="/products?action=delete&id=${product.getId()}" onclick="return confirm('Are you sure?')"
                                                   class="mdi mdi-delete"></a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                                <script>

                                </script>
                                <div class="row w-100">
                                    <div class="col-12  d-flex justify-content-center border-0 text-white">
                                        <div class="container-fluid mt-2">
                                            <div class="float-right">
                                                <%--For displaying Previous link except for the 1st page --%>
                                                <c:if test="${currentPage != 1}">
                                                    <a href="products?page=${currentPage - 1}&search=${requestScope.search}"
                                                       class="p-2 mr-1 border">Previous</a>
                                                </c:if>

                                                <%--For displaying Page numbers.
                                                The when condition does not display a link for the current page--%>
                                                <c:forEach begin="1" end="${noOfPages}" var="i">
                                                    <c:choose>
                                                        <c:when test="${currentPage eq i}">
                                                            <a href="products?page=${i}"
                                                               class="p-2 mr-1 border">${i}</a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <a href="products?page=${i}&search=${requestScope.search}"
                                                               class="p-2 mr-1 border">${i}</a>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>

                                                <%--For displaying Next link: lt < noOfPages--%>
                                                <c:if test="${currentPage < noOfPages}">
                                                    <a href="products?page=${currentPage + 1}&search=${requestScope.search}"
                                                       class="p-2 mr-1 border">Next</a>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Footer Start -->
        <jsp:include page="/WEB-INF/layout/footer.jsp"></jsp:include>
        <!-- end Footer -->
    </div>
</div>

<!-- END wrapper -->

<jsp:include page="/WEB-INF/layout/footer_js.jsp"></jsp:include>
</body>

</html>