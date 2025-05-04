package edu.matc.persistence;

import edu.matc.entity.Expense;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Expense Dao class
 * @author Btaneh
 */
public class ExpenseDao {

    private final Logger logger = LogManager.getLogger(this.getClass());
    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

    /**
     * Get expense by id.
     *
     * @param id Expense id.
     * @return Expense corresponding to the given id.
     */
    public Expense getById(int id) {
        Session session = sessionFactory.openSession();
        Expense expense = session.get(Expense.class, id);
        session.close();
        return expense;
    }

    /**
     * Update an expense.
     *
     * @param expense Expense to be updated.
     */
    public void update(Expense expense) {
        logger.debug("Before update: " + expense);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(expense);
        transaction.commit();
        logger.debug("Updating expense: " + expense.getExpenseId());
        session.close();
        //logger.debug("New amount: " + amount + ", New desc: " + description);
    }

    /**
     * Insert a new expense.
     *
     * @param expense Expense to be inserted.
     * @return The generated id for the new expense.
     */
    public int insert(Expense expense) {
        int expenseId = 0;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(expense);
        transaction.commit();
        expenseId = expense.getExpenseId();
        session.close();
        return expenseId;
    }

    /**
     * Delete an expense.
     *
     * @param expense Expense to be deleted.
     */
    public void delete(Expense expense) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(expense);
        transaction.commit();
        session.close();
    }

    /**
     * Return a list of all expenses.
     *
     * @return All expenses.
     */
    public List<Expense> getAllExpenses() {
        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Expense> query = session.getCriteriaBuilder().createQuery(Expense.class);
        Root<Expense> root = query.from(Expense.class);
        List<Expense> expenses = session.createQuery(query).getResultList();

        logger.debug("The list of expenses: " + expenses);
        session.close();

        return expenses;
    }

    public List<Expense> getExpensesByUserId(int userId) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Expense> query = builder.createQuery(Expense.class);
        Root<Expense> root = query.from(Expense.class);
        query.select(root).where(builder.equal(root.get("user").get("userId"), userId));
        List<Expense> expenses = session.createQuery(query).getResultList();
        logger.debug("Expenses for userId {}: {}", userId, expenses);
        session.close();
        return expenses;
    }

    public Map<String, Double> getExpenseCategoryTotalsByUser(int userId) {
        Session session = sessionFactory.openSession();
        String hql = "SELECT e.category.name, SUM(e.amount) " +
                "FROM Expense e WHERE e.user.userId = :userId GROUP BY e.category.name";

        List<Object[]> results = session.createQuery(hql)
                .setParameter("userId", userId)
                .list();
        session.close();

        Map<String, Double> totals = new LinkedHashMap<>();
        for (Object[] row : results) {
            totals.put((String) row[0], (Double) row[1]);
        }
        return totals;
    }

    public List<Expense> getRecentExpenses(int userId, int pastDays) {
        Session session = sessionFactory.openSession();
        LocalDate fromDate = LocalDate.now().minusDays(pastDays);

        String hql = "FROM Expense e WHERE e.user.userId = :userId AND e.date >= :fromDate ORDER BY e.date DESC";
        List<Expense> expenses = session.createQuery(hql)
                .setParameter("userId", userId)
                .setParameter("fromDate", fromDate)
                .list();
        session.close();
        return expenses;
    }

    public List<Expense> getExpensesByCategory(int userId, String categoryName) {
        Session session = sessionFactory.openSession();
        String hql = "FROM Expense e WHERE e.user.userId = :userId AND e.category.name = :categoryName";
        List<Expense> expenses = session.createQuery(hql)
                .setParameter("userId", userId)
                .setParameter("categoryName", categoryName)
                .list();
        session.close();
        return expenses;
    }



}
