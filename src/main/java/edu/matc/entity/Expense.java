package edu.matc.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Class for expenses
 * @author Btaneh
 */
@Entity (name = "Expense")
@Table(name = "expenses")
public class Expense {
    //private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    private int expenseId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false)
    private ExpenseCategory category;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "description")
    private String description;

    private transient double convertedAmount; // used to store the result of currency conversion
    /**
     * No-argument constructor
     */
    public Expense() {

    }

    /**
     * Constructor to create an Expense with initial values.
     *
     * @param category    the expense category
     * @param amount      the expense amount
     * @param date        the expense date
     * @param description the expense description
     */
    public Expense(User user, ExpenseCategory category, double amount, LocalDate date, String description) {
        this.user = user;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    /**
     * get expense id
     */
    public int getExpenseId() {
        return expenseId;
    }

    /**
     * set expense id
     * @param expenseId the expense id
     */
    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    /**
     * Get category
     *
     * @return the category
     */
    public ExpenseCategory getCategory() {
        return category;
    }

    /**
     * Set category
     *
     * @param category the category
     */
    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }

    /**
     * get amount
     *
     * @return the anount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Set amount
     *
     * @param amount the amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * get date
     *
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * set date
     *
     * @param date the date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Get description
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public double getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(double convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    /**
     * Get user
     * @return user
     */
    public User getUser() {
        return user;
    }

    /**
     * Set user
     * @param user the user who owns the expense
     */
    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            if (!user.getExpenses().contains(this)) {
                user.addExpense(this);
            }
        }
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expenseId=" + expenseId +
                ", user=" + user +
                ", category='" + category.getName() + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", description='" + description + '\'' +
                '}';
    }
}

