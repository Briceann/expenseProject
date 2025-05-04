package edu.matc.service;

import edu.matc.entity.Expense;
import edu.matc.persistence.ExpenseDao;

import java.util.List;

/**
 * Service class to handle business logic related to expenses.
 * @author Btaneh
 */
public class ExpenseService {

    private final ExpenseDao expenseDao;

    // Constructor for dependency injection
    public ExpenseService() {
        this.expenseDao = new ExpenseDao();
    }

    /**
     * Adds a new expense to the database.
     * @param expense The expense to be added.
     */
    public void addExpense(Expense expense) {
        expenseDao.insert(expense);
    }

    /**
     * Retrieves a list of expenses for a specific user.
     * @param userId The ID of the user whose expenses are being fetched.
     * @return A list of expenses for the given user.
     */
    public List<Expense> getExpensesForUser(int userId) {
        return expenseDao.getAllExpenses();
    }
}
