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
                        <a href="" class="nav-link right-bar-toggle waves-effect waves-light">
                            <i class="mdi mdi-settings-outline noti-icon"></i>
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
                            <h4 class="header-title">Edit Product</h4>
                            <div class="row">
                                <div class="col-lg-6">
                                    <form class="form-horizontal" method="post" enctype="multipart/form-data">
                                        <div class="form-group row">
                                            <label class="col-md-2 control-label">Name</label>
                                            <div class="col-md-10">
                                                <input type="text" class="form-control" name="name"
                                                       value="${product.getName()}">
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-md-2 control-label">Image</label>
                                            <div class="col-md-10">
                                                <input type="file" class="" name="img"
                                                       value="${product.getImg()}">
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-md-2 control-label">Quantity</label>
                                            <div class="col-md-10">
                                                <input type="number" name="quantity" value="${product.getQuantity()}"
                                                       class="form-control">
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-md-2 control-label">Price</label>
                                            <div class="col-md-10">
                                                <input type="number" class="form-control" name="price"
                                                       value="${product.getPrice()}" placeholder="Price">
                                            </div>
                                        </div>

                                        <div class="form-group row">
                                            <label class="col-md-2 control-label">Category</label>
                                            <div class="col-md-10">
                                                <select class="form-control" name="idCategory">
                                                    <c:forEach var="category"
                                                               items="${applicationScope.listCategories}">
                                                        <option value="${category.getId()}">${category.getName()}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-md-2 control-label"></label>
                                            <div class="col-md-10">
                                                <button type="submit" class="btn btn-purple waves-effect waves-light">
                                                    Submit
                                                </button>
                                            </div>
                                        </div>
                                    </form>
                                    <div>
                                        <c:if test="${requestScope.errors!=null}">
                                            <div class="alert alert-icon alert-danger alert-dismissible fade show mb-0"
                                                 role="alert">
                                                <button type="button" class="close" data-dismiss="alert"
                                                        aria-label="Close">
                                                    <span aria-hidden="true">×</span>
                                                </button>
                                                <span>${errors}</span> </br>
                                            </div>
                                        </c:if>
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