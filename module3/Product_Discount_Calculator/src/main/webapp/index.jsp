<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Product Discount Calculator</title>
</head>
<body>
<h3>Product Discount Calculator</h3>
<form action="/conculatordiscount" method="post">
    <label>Description: </label>
    <input type="text" name="description" placeholder="Description"/>
    <label>List Price: </label>
    <input type="number" name="price" placeholder="Price"/>
    <label>Discount Percent: </label>
    <input type="number" name="percent" placeholder="Percent"/>
    <input type = "submit" id = "submit" value = "Calculate Discount"/>
</form>
</body>
</html>