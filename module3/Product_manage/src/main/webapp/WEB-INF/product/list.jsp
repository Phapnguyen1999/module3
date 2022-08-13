<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Diamond Shop</title>
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
                            <img src="assets\images\users\avatar-1.jpg" alt="user-image" class="rounded-circle">
                        </a>
                        <div class="dropdown-menu dropdown-menu-right profile-dropdown ">
                            <!-- item-->
                            <div class="dropdown-header noti-title">
                                <h6 class="text-overflow m-0">Welcome !</h6>
                            </div>

                            <!-- item-->
                            <a href="javascript:void(0);" class="dropdown-item notify-item">
                                <i class="mdi mdi-account-outline"></i>
                                <span>Profile</span>
                            </a>

                            <!-- item-->
                            <a href="javascript:void(0);" class="dropdown-item notify-item">
                                <i class="mdi mdi-settings-outline"></i>
                                <span>Settings</span>
                            </a>

                            <!-- item-->
                            <a href="javascript:void(0);" class="dropdown-item notify-item">
                                <i class="mdi mdi-lock-outline"></i>
                                <span>Lock Screen</span>
                            </a>

                            <div class="dropdown-divider"></div>

                            <!-- item-->
                            <a href="javascript:void(0);" class="dropdown-item notify-item">
                                <i class="mdi mdi-logout-variant"></i>
                                <span>Logout</span>
                            </a>

                        </div>
                    </li>

                    <li class="dropdown notification-list">
                        <a href="/products?action=login" class="nav-link right-bar-toggle waves-effect waves-light">
                            <i class="">Login</i>
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
                            <a href="/products?action=list" class="active"> <i class="mdi mdi-view-dashboard"></i>Product</a>
                        </li>

                        <li class="has-submenu">
                            <a href="/products?action=create">
                                <i class="mdi mdi-layers"></i>Create product
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
                                            <td><c:out value="${product.getImg()}"/></td>
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
                                                <a href="/products?action=delete&id=${product.getId()}"
                                                   class="mdi mdi-delete"></a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                                <div>
                                    <%--For displaying Previous link except for the 1st page --%>
                                    <c:if test="${currentPage != 1}">
                                        <a href="products?page=${currentPage - 1}&search=${requestScope.search}">Previous</a>
                                    </c:if>

                                    <%--For displaying Page numbers.
                                    The when condition does not display a link for the current page--%>
                                    <table border="1" cellpadding="5" cellspacing="5">
                                        <tr>
                                            <c:forEach begin="1" end="${noOfPages}" var="i">
                                                <c:choose>
                                                    <c:when test="${currentPage eq i}">
                                                        <td>${i}</td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td>
                                                            <a href="products?page=${i}&search=${requestScope.search}">${i}</a>
                                                        </td>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </tr>
                                    </table>

                                    <%--For displaying Next link: lt < noOfPages--%>
                                    <c:if test="${currentPage lt noOfPages}">
                                        <a href="products?page=${currentPage + 1}&search=${requestScope.search}">Next</a>
                                    </c:if>
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