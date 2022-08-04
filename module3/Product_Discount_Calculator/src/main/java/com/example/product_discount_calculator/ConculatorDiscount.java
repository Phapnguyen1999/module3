package com.example.product_discount_calculator;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "ConculatorDiscount", value = "/conculatordiscount")
public class ConculatorDiscount extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        float price = Float.parseFloat(req.getParameter("price"));
        float percent = Float.parseFloat(req.getParameter("percent"));
        float discountamount = price * percent * 0.01f;
        float discountprice=price-discountamount;
        PrintWriter writer = resp.getWriter();
        writer.println("<html>");
        writer.println("<h1>Discount Amount: " + discountamount + "</h1>");
        writer.println("<h1>Discount Price: " + discountprice + "</h1>");
        writer.println("</html>");
    }
}