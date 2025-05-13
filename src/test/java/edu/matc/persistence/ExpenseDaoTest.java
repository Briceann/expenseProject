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

    GenericDao genericDao;
    GenericDao userDao;
    GenericDao categoryDao;

    @BeforeEach
    void setUp() {
        genericDao = new GenericDao(Expense.class);
        userDao = new GenericDao(User.class);
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");
    }

    @Test
    void getById() {
        Expense retrievedExpense = (Expense)genericDao .getById(1);
        assertNotNull(retrievedExpense);
        assertEquals("February Rent", retrievedExpense.getDescription());
        assertEquals(1, retrievedExpense.getUser().getUserId());
    }

    @Test
    void update() {
        Expense expenseToUpdate = (Expense) genericDao.getById(1);
        expenseToUpdate.setAmount(1500.00);
        genericDao.update(expenseToUpdate);

        Expense actualExpense = (Expense) genericDao.getById(1);
        assertEquals(1500.00, actualExpense.getAmount());
    }

    @Test
    void insert() {

        GenericDao<ExpenseCategory> categoryDao = new GenericDao<>(ExpenseCategory.class);
        GenericDao<Expense> expenseDao = new GenericDao<>(Expense.class);
        GenericDao<User> userDao = new GenericDao<>(User.class);

        User user = userDao.getById(2);
        ExpenseCategory category = categoryDao.getById(4);

        Expense expense = new Expense(user, category, 19.99, LocalDate.of(2025, 3, 10), "Netflix subscription");
        int insertedExpenseId = expenseDao.insert(expense);
        Expense retrievedExpense = expenseDao.getById(insertedExpenseId);
        assertNotNull(retrievedExpense);

        assertEquals(expense.getDescription(), retrievedExpense.getDescription());
        assertEquals("Princess", expense.getUser().getFirstName());
    }

    @Test
    void delete() {
        Expense expenseToDelete = (Expense) genericDao.getById(4);
        assertNotNull(expenseToDelete);
        genericDao.delete(expenseToDelete);
        assertNull(genericDao.getById(4));
    }

    @Test
    void getAllExpenses() {
        List<Expense> expenses = genericDao.getAll();
        assertEquals(4, expenses.size());
    }

}
