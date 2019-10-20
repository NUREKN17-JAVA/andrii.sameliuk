package ua.nure.cs.sameliuk.usermanagment.domain;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.Calendar;

public class UserTest extends TestCase {

    private static final int ETALON_AGE = 19;
    private static final int YEAR_OF_BIRTHDAY = 2000;
    private static final int MONTH_OF_BIRTHDAY = 4;
    private static final int DATE_OF_BIRTHDAY = 10;

    private static final int MONTH_OF_BIRTHDAY_1 = 12;
    private static final int ETALON_AGE_1 = 18;

    private User user = new User();

    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetFullName() {
        user.setFirstName("John");
        user.setLastName("Doe");
        Assert.assertEquals("Getting full user name was failed.", "Doe, John", user.getFullName());
    }

    public void testTestAge() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_OF_BIRTHDAY, MONTH_OF_BIRTHDAY, DATE_OF_BIRTHDAY);
        user.setDateOfBirth(calendar.getTime());
        Assert.assertEquals("Calculation of age was failed if birthday comes.", ETALON_AGE,
                            user.getAge());
    }

    public void testGetAge1() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_OF_BIRTHDAY, MONTH_OF_BIRTHDAY_1, DATE_OF_BIRTHDAY);
        user.setDateOfBirth(calendar.getTime());
        Assert.assertEquals("Calculation of age was failed.", ETALON_AGE_1, user.getAge());
    }

    public void testGetAge2() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_OF_BIRTHDAY, 10, DATE_OF_BIRTHDAY);
        user.setDateOfBirth(calendar.getTime());
        Assert.assertEquals("Calculation of age was failed.", ETALON_AGE_1, user.getAge());
    }

    public void testGetAge3() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_OF_BIRTHDAY, 10, 15);
        user.setDateOfBirth(calendar.getTime());
        Assert.assertEquals("Calculation of age was failed.", ETALON_AGE_1, user.getAge());
    }

    public void testGetAge4() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR_OF_BIRTHDAY, 10, 13);
        user.setDateOfBirth(calendar.getTime());
        Assert.assertEquals("Calculation of age was failed.", ETALON_AGE_1, user.getAge());
    }

}
