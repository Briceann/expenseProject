package edu.matc.service;

import edu.matc.entity.ExpenseCategory;
import edu.matc.persistence.ExpenseCategoryDao;

public class ExpenseCategoryService {
    private ExpenseCategoryDao categoryDao = new ExpenseCategoryDao();

    public ExpenseCategory getCategoryById(int id) {
        return categoryDao.getCategoryById(id);
    }
}
