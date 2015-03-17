/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImplTests;

import java.math.BigDecimal;
import java.util.Date;
import org.junit.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.sql.SQLException;
import org.junit.Before;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

/**
 *
 * @author marek, brano
 */
public class LeaseManagerImplTest {
     
    private LeaseManagerImpl manager;
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");

    @Before
    public void setUp() throws SQLException {
        manager = new LeaseManagerImpl();
    }
    
    @Test
    public void getAllLeasesForCustomer() {
        

        Customer c1 = newCustomer("John Kviatkowsky",setDate("22-01-1986"),"0976SK41");
        Customer c2 = newCustomer("Jozo Petrik",setDate("20-04-1991"),"0936SK00");
        
        assertTrue(manager.findLeasesForCustomer(c1).isEmpty());
                
        Barrow b1 = newBarrow("sand", 100D);
        Barrow b2 = newBarrow("soil", 200D);
        Barrow b3 = newBarrow("concrete", 150D);
        Lease l1 = newLease(c1,b1, new BigDecimal(1.0), setDate("22-01-1983"),setDate("22-01-1981"),setDate("22-01-1982"));
        Lease l2 = newLease(c1,b2, new BigDecimal(1.0), setDate("22-01-1985"),setDate("22-01-1981"),setDate("22-01-1982"));
        Lease l3 = newLease(c2,b3, new BigDecimal(1.0), setDate("22-01-1984"),setDate("22-01-1982"),setDate("22-01-1983"));
        
        manager.createLease(l1);
        manager.createLease(l2);
        manager.createLease(l3);
        
        List<Lease> expected = Arrays.asList(l1,l2);
        List<Lease> actual = manager.findLeasesForCustomer(c1);
        
        assertEquals(expected, actual);
        assertDeepEquals(expected, actual);
        
    }
    
    
    @Test
    public void getAllLeasesForBarrow() {
        
        
        Customer c1 = newCustomer("John Kviatkowsky",setDate("22-01-1986"),"0976SK41");
        Customer c2 = newCustomer("Jozo Petrik",setDate("20-04-1991"),"0936SK00");
        Customer c3 = newCustomer("Brano Mojsej",setDate("20-04-1977"),"0936SK32");
        Barrow b1 = newBarrow("sand", 100D);
        Barrow b2 = newBarrow("concrete", 150D);
        
        assertTrue(manager.findLeasesForBarrow(b1).isEmpty());
        
        Lease l1 = newLease(c1,b1, new BigDecimal(1.0), setDate("22-01-1983"),setDate("22-01-1981"),setDate("22-01-1982"));
        Lease l2 = newLease(c2,b1, new BigDecimal(1.0), setDate("22-01-1985"),setDate("22-01-1981"),setDate("22-01-1982"));
        Lease l3 = newLease(c3,b2, new BigDecimal(1.0), setDate("22-01-1984"),setDate("22-01-1980"),setDate("22-01-1983"));
        
        manager.createLease(l1);
        manager.createLease(l2);
        manager.createLease(l3);
        
        List<Lease> expected = Arrays.asList(l1, l2);
        List<Lease> actual = manager.findLeasesForBarrow(b1);
        
        assertEquals(expected, actual);
        assertDeepEquals(expected, actual);
        
    }
    
    @Test
    public void getAllLeases() {
        
        
        Customer c1 = newCustomer("John Kviatkowsky",setDate("22-01-1986"),"0976SK41");
        Customer c2 = newCustomer("Jozo Petrik",setDate("20-04-1991"),"0936SK00");
        Barrow b1 = newBarrow("sand", 100D);
        
        assertTrue(manager.findLeasesForCustomer(c1).isEmpty());
        
        Lease l1 = newLease(c1,b1, new BigDecimal(1.0), setDate("22-01-1983"),setDate("22-01-1981"),setDate("22-01-1982"));
        Lease l2 = newLease(c2,b1, new BigDecimal(1.0), setDate("22-01-1985"),setDate("22-01-1981"),setDate("22-01-1982"));
        
        manager.createLease(l1);
        manager.createLease(l2);
        
        List<Lease> expected = Arrays.asList(l1, l2);
        List<Lease> actual = manager.findAllLeases();
        
        assertEquals(expected, actual);
        assertDeepEquals(expected, actual);
        
    }
    
    
    
       
    private static Lease newLease(Customer customer, Barrow barrow, BigDecimal price, Date realEndTime, Date startTime, Date expectedEndTime) {
        Lease lease = new Lease();
        lease.setPrice(price);
        lease.setRealEndTime(realEndTime);
        lease.setExpectedEndTime(expectedEndTime);
        lease.setStartTime(startTime);
        return lease;
    }
    
        
    private static Customer newCustomer(String fullName, Date birthDate, String idCard) {
        Customer customer = new Customer();
        customer.setBirthDate(birthDate);
        customer.setFullName(fullName);
        customer.setIdCard(idCard);
        return customer;
    }
    
    private Barrow newBarrow(String use, Double volumeLt) {
        Barrow barrow = new Barrow();
        //barrow.setId(id);
        barrow.setUse(use);
        barrow.setVolumeLt(volumeLt);
        
        return barrow;
    }

    private void assertDeepEquals(List<Lease> expectedList, List<Lease> actualList) {
        for (int i = 0; i < expectedList.size(); i++) {
            Lease expected = expectedList.get(i);
            Lease actual = actualList.get(i);
            assertDeepEquals(expected, actual);
        }
    }

    private void assertDeepEquals(Lease expected, Lease actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getCustomer(), actual.getCustomer());
        assertEquals(expected.getBarrow(), actual.getBarrow());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getRealEndTime(), actual.getRealEndTime());
        assertEquals(expected.getExpectedEndTime(), actual.getExpectedEndTime());
        assertEquals(expected.getRealEndTime(), actual.getRealEndTime());
    }
    
    private Date setDate(String dateInString) {
        try {
                //String dateInString = "22-01-1986";
            Date date = sdf.parse(dateInString);
            return date;
        } catch (ParseException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}
