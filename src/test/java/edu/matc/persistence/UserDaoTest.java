package edu.matc.persistence;

import edu.matc.entity.Expense;
import edu.matc.entity.User;
import edu.matc.util.Database;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest {

    UserDao userDao;
    private Session HibernateUtil;

    @BeforeEach
    void setUp() {
        userDao = new UserDao();
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");

    }


    @Test
    void getByUserID() {
        User retrievedUser = userDao.getUserById(1);
        assertNotNull(retrievedUser);
        assertEquals("Jenny", retrievedUser.getFirstName());

    }

    @Test
    void updateUser() {
        User userToUpdate = userDao.getUserById(1);
        userToUpdate.setLastName("Jackson");
        userDao.update(userToUpdate);

        User actualUser = userDao.getUserById(1);
        assertEquals("Jackson", actualUser.getLastName());
    }

    @Test
    void insertUser() {
        User userToInsert = new User("Cyn", "Skai", "cskai", "cskai20@gmail.com");
        int insertedUserId = userDao.insertUser(userToInsert);
        assertNotEquals(0, insertedUserId);
        User insertedUser = userDao.getUserById(insertedUserId);
        assertEquals("Cyn", insertedUser.getFirstName());
    }

    @Test
    void deleteUser() {
        userDao.delete(userDao.getUserById(2));
        assertNull(userDao.getUserById(2));
    }

    @Test
    void deleteWithExpenses() {
        User userToBeDeleted = userDao.getUserById(1);
        List<Expense> expenses = userToBeDeleted.getExpenses();
        int expenseNumber1 = expenses.get(0).getExpenseId();
        int expenseNumber2 = expenses.get(1).getExpenseId();

        userDao.delete(userToBeDeleted);
        assertNull(userDao.getUserById(1));

        ExpenseDao expenseDao = new ExpenseDao();
        assertNull(expenseDao.getById(expenseNumber1));
        assertNull(expenseDao.getById(expenseNumber2));

    }

    @Test
    void getAllUsers() {
        List<User> users = userDao.getAllUsers();
        assertEquals(3, users.size());
    }

    @Test
    void getByPropertyEqual() {
        List<User> users = userDao.getByPropertyEqual("lastName", "Adams");
        assertEquals(1, users.size());
        assertEquals(2, users.get(0).getUserId());
    }
}
