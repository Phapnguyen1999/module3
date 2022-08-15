<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Diamond Store</title>
    <jsp:include page="/WEB-INF/layout/meta_css.jsp"></jsp:include>
    <link href="/assets\libs\toastr\toastr.min.css" rel="stylesheet" type="text/css">


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
                    <div class="col-sm-12">
                        <div class="card-box">
                            <h4 class="header-title">Create Product</h4>
                            <div class="row">
                                <div class="col-lg-6">
                                    <form class="form-horizontal" method="post">
                                        <div class="form-group row">
                                            <label class="col-md-2 control-label">Name</label>
                                            <div class="col-md-10">
                                                <input type="text" class="form-control" name="name"
                                                       value="${product.getName()}">
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
                                            <label class="col-md-2 control-label">Quantity</label>
                                            <div class="col-md-10">
                                                <input type="number" name="quantity" value="${product.getQuantity()}"
                                                       class="form-control">
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-md-2 control-label">Color</label>
                                            <div class="col-md-10">
                                                <input type="text" name="color" value="${product.getColor()}"
                                                       class="form-control">
                                            </div>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-md-2 control-label">Description</label>
                                            <div class="col-md-10">
                                                <input type="text" name="description" value="${product.getDescription()}"
                                                       class="form-control">
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
                                        <div class="form-group row">
                                            <label class="col-md-2 control-label"></label>
                                            <div class="col-md-10">
                                                <a href="/products?action=list" class="active"> <i class="btn btn-orange">Back</i></a>
                                            </div>
                                        </div>
                                    </form>
                                    <div>
                                        <c:if test="${requestScope.errors!=null}">
                                            <div class="alert alert-icon alert-danger alert-dismissible fade show mb-0" role="alert">
                                                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                                    <span aria-hidden="true">×</span>
                                                </button>
                                                <c:forEach items="${requestScope.errors}" var="e">
                                                    <strong>*Field: ${fn:toUpperCase(e.key)}</strong> </br>
                                                    <c:forEach items="${e.value}" var="item">
                                                        <span>${item}</span> </br>
                                                    </c:forEach>
                                                </c:forEach>

                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- end row -->
            </div>
            <!-- end container-fluid -->
        </div>
        <!-- end content -->
        <!-- Footer Start -->
        <jsp:include page="/WEB-INF/layout/footer.jsp"></jsp:include>
        <!-- end Footer -->
    </div>
</div>

<jsp:include page="/WEB-INF/layout/footer_js.jsp"></jsp:include>
<script src="/assets\libs\toastr\toastr.min.js"></script>
<script src="/assets\js\pages\toastr.init.js"></script>
<c:if test="${requestScope.success!=null}">
    <script>
        $(document).ready(function () {
            <% String success= (String) request.getAttribute("success"); %>
            var js_Success = "<%= success %>";
            toastr.options = {
                "closeButton": true,
                "debug": false,
                "newestOnTop": false,
                "progressBar": false,
                "positionClass": "toast-top-right",
                "preventDuplicates": false,
                "onclick": null,
                "showDuration": "300",
                "hideDuration": "1000",
                "timeOut": "5000",
                "extendedTimeOut": "1000",
                "showEasing": "swing",
                "hideEasing": "linear",
                "showMethod": "fadeIn",
                "hideMethod": "fadeOut"
            }
            toastr["success"](js_Success)
        });
    </script>
</c:if>

</body>

</html>