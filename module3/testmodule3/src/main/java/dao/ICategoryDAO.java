package dao;

import model.Category;

import java.sql.SQLException;
import java.util.List;

public interface ICategoryDAO {


    public List<Category> selectAllCategory();


}
