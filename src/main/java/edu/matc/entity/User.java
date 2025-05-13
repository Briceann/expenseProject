package edu.matc.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * User class
 * @author Btaneh
 */
@Entity(name = "User")
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "native,", strategy = "native")
    @Column(name = "user_id")
    private int userId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Expense> expenses = new ArrayList<>();

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_name")
    private String username;

    @Column(name = "email")
    private String email;

    /**
     * No argument constructor
     */
    public User() {
    }
    /**
     * Instantiate new user
     *
     * @param firstName the first name
     * @param lastName the last name
     * @param email the email
     */
    public User(String firstName, String lastName, String username, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username =username;
        this.email = email;
        this.expenses = new ArrayList<>();

    }

    /**
     * Constructor with parameters for creating new users
     * @param username the username
     * @param email the email
     */
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    /**
     * get expense
     * @return
     */
    public List<Expense> getExpenses() {
        return expenses;
    }

    /**
     * set expense
     * @param expenses the expenses
     */
    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public void addExpense(Expense expense) {
        if (!expenses.contains(expense)) {
            expenses.add(expense);
            expense.setUser(this);
        }
    }

    public void removeExpense(Expense expense) {
        if (expenses.contains(expense)) {
            expenses.remove(expense);
            expense.setUser(null);
        }
    }

    /**
     * Get first name
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name
     *
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets username
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets email
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets id
     *
     * @return the id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets id
     *
     * @param userId the id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", expenses=" + expenses.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(username, user.username) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, lastName, username, email);
    }
}