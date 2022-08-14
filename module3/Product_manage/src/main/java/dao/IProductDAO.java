package dao;

import model.Product;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IProductDAO {
    public void insertProduct(Product product) throws SQLException;

    public Product selectProduct(int id);

    public List<Product> selectAllProduct();
    public List<Product> selectProductPaging(int offset, int noOfRecords);
    public List<Product> selectProductPaging(int offset, int noOfRecords,String search);
    public int getNoOfRecords();

    public boolean deleteProduct(int id) throws SQLException;

    public boolean updateProduct(Product product) throws SQLException;

}
