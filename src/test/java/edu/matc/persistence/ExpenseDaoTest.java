package edu.matc.persistence;

import edu.matc.entity.Expense;
import edu.matc.entity.ExpenseCategory;
import edu.matc.entity.User;
import edu.matc.util.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseDaoTest {

    ExpenseDao expenseDao;

    @BeforeEach
    void setUp() {
        expenseDao = new ExpenseDao();
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");
    }

    @Test
    void getById() {
        Expense retrievedExpense = expenseDao.getById(1);
        assertNotNull(retrievedExpense);
        assertEquals("February Rent", retrievedExpense.getDescription());
        assertEquals(1, retrievedExpense.getUser().getUserId());
    }

    @Test
    void update() {
        Expense expenseToUpdate = expenseDao.getById(1);
        expenseToUpdate.setAmount(1500.00);
        expenseDao.update(expenseToUpdate);

        Expense actualExpense = expenseDao.getById(1);
        assertEquals(1500.00, actualExpense.getAmount());
    }

    @Test
    void insert() {

        UserDao userDao = new UserDao();
        ExpenseCategoryDao categoryDao = new ExpenseCategoryDao();

        User user = userDao.getUserById(2);
        ExpenseCategory category = categoryDao.getCategoryById(4);

        Expense expense = new Expense(user, category, 19.99, LocalDate.of(2025, 3, 10), "Netflix subscription");
        int insertedExpenseId = expenseDao.insert(expense);
        Expense retrievedExpense = expenseDao.getById(insertedExpenseId);
        assertNotNull(retrievedExpense);

        assertEquals(expense.getDescription(), retrievedExpense.getDescription());
        assertEquals("Princess", expense.getUser().getFirstName());

    }

    @Test
    void delete() {
        Expense expenseToDelete = expenseDao.getById(5);
        assertNull(expenseDao.getById(5));
    }

    @Test
    void getAllExpenses() {
        List<Expense> expenses = expenseDao.getAllExpenses();
        assertEquals(4, expenses.size());
    }

}
