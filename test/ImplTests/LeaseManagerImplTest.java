/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImplTests;

import barrowrent.Barrow;
import barrowrent.Customer;
import barrowrent.Lease;
import barrowrent.LeaseManagerImpl;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Branislav Smik <xsmik @fi.muni>
 * @author marek
 */
public class LeaseManagerImplTest {

    private LeaseManagerImpl manager;
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    @Before
    public void setUp() throws SQLException {
        manager = new LeaseManagerImpl();
    }

    @Test
    public void createLease() {
        Barrow barrow = newBarrow("sand", 100D);
        Customer customer = newCustomer("Jozko Mrkvicka", setDate("22-01-1986"), "0976SK25");
        BigDecimal price = new BigDecimal("1000.99");
        Lease lease = newLease(customer, barrow, price, setDate("22-08-2015"), setDate("22-08-2015"), setDate("22-08-2015"));

        manager.createLease(lease);

        Long leaseId = lease.getId();
        assertNotNull(leaseId);
        Lease result = manager.getLeaseById(leaseId);
        assertEquals(lease, result);
        assertNotSame(lease, result);
        assertDeepEquals(lease, result);
    }

    @Test
    public void getLease() {
        assertNull(manager.getLeaseById(1L));

        Barrow barrow = newBarrow("sand", 100D);
        Customer customer = newCustomer("Jozko Mrkvicka", setDate("22-01-1986"), "0976SK25");
        BigDecimal price = new BigDecimal("1000.99");
        Lease lease = newLease(customer, barrow, price, setDate("22-08-2015"), setDate("22-08-2015"), setDate("22-08-2015"));

        manager.createLease(lease);
        Long leaseId = lease.getId();

        Lease result = manager.getLeaseById(leaseId);
        assertEquals(lease, result);
        assertDeepEquals(lease, result);
    }
    
//----------------------------------------UPDATE GOOD LEASE----------------------------------------------------------    
    @Test
    public void updateLeaseBarrow() {
        Customer customer1 = newCustomer("John Kviatkowsky", setDate("22-01-1986"), "0976SK41");
        Customer customer2 = newCustomer("Jozo Petrik", setDate("20-04-1991"), "0936SK00"); 
        Barrow barrow1 = newBarrow("sand", 100D);
        Barrow barrow2 = newBarrow("soil", 200D);
        Barrow barrow3 = newBarrow("concrete", 150D);

        Lease lease1 = newLease(customer1, barrow1, new BigDecimal(1.0), 
                setDate("22-01-1983"), setDate("22-01-1981"), setDate("22-01-1982"));
        Lease lease2 = newLease(customer1, barrow2, new BigDecimal(1.0), 
                setDate("22-01-1985"), setDate("22-01-1981"), setDate("22-01-1982"));
        
        manager.createLease(lease1);
        manager.createLease(lease2);
        Long leaseId = lease1.getId();
        
        lease1 = manager.getLeaseById(leaseId);
        lease1.setBarrow(barrow3);
        manager.updateLease(lease1);
        assertEquals(barrow3, lease1.getBarrow());
        assertEquals(customer1, lease1.getCustomer());
        assertEquals(new BigDecimal(1.0), lease1.getPrice());
        assertEquals(setDate("22-01-1983"), lease1.getRealEndTime());
        assertEquals(setDate("22-01-1981"), lease1.getStartTime());
        assertEquals(setDate("22-01-1982"), lease1.getExpectedEndTime());
        
        // Check if updates didn't affect other records
        assertDeepEquals(lease2, manager.getLeaseById(lease2.getId()));  
    }
    
    @Test
    public void updateLeaseCustomer() {
        Customer customer1 = newCustomer("John Kviatkowsky", setDate("22-01-1986"), "0976SK41");
        Customer customer2 = newCustomer("Jozo Petrik", setDate("20-04-1991"), "0936SK00");
        Customer customer3 = newCustomer("Brano Mojsej", setDate("20-04-1977"),"0936SK32");     
        Barrow barrow1 = newBarrow("sand", 100D);
        Barrow barrow2 = newBarrow("soil", 200D);

        Lease lease1 = newLease(customer1, barrow1, new BigDecimal(1.0), 
                setDate("22-01-1983"), setDate("22-01-1981"), setDate("22-01-1982"));
        Lease lease2 = newLease(customer1, barrow2, new BigDecimal(1.0), 
                setDate("22-01-1985"), setDate("22-01-1981"), setDate("22-01-1982"));
        
        manager.createLease(lease1);
        manager.createLease(lease2);
        Long leaseId = lease1.getId();
        
        lease1 = manager.getLeaseById(leaseId);
        lease1.setCustomer(customer3);
        manager.updateLease(lease1);
        assertEquals(barrow1, lease1.getBarrow());
        assertEquals(customer3, lease1.getCustomer());
        assertEquals(new BigDecimal(1.0), lease1.getPrice());
        assertEquals(setDate("22-01-1983"), lease1.getRealEndTime());
        assertEquals(setDate("22-01-1981"), lease1.getStartTime());
        assertEquals(setDate("22-01-1982"), lease1.getExpectedEndTime());
        
        // Check if updates didn't affect other records
        assertDeepEquals(lease2, manager.getLeaseById(lease2.getId()));  
    }
    
    @Test
    public void updateLeasePrice() {
        Customer customer1 = newCustomer("John Kviatkowsky", setDate("22-01-1986"), "0976SK41");
        Customer customer2 = newCustomer("Jozo Petrik", setDate("20-04-1991"), "0936SK00");
        Barrow barrow1 = newBarrow("sand", 100D);
        Barrow barrow2 = newBarrow("soil", 200D);

        Lease lease1 = newLease(customer1, barrow1, new BigDecimal(1.0), 
                setDate("22-01-1983"), setDate("22-01-1981"), setDate("22-01-1982"));
        Lease lease2 = newLease(customer1, barrow2, new BigDecimal(2.0), 
                setDate("22-01-1985"), setDate("22-01-1981"), setDate("22-01-1982"));
        
        manager.createLease(lease1);
        manager.createLease(lease2);
        Long leaseId = lease1.getId();
        
        lease1 = manager.getLeaseById(leaseId);
        lease1.setPrice(new BigDecimal(12.34));
        manager.updateLease(lease1);
        assertEquals(barrow1, lease1.getBarrow());
        assertEquals(customer1, lease1.getCustomer());
        assertEquals(new BigDecimal(12.34), lease1.getPrice());
        assertEquals(setDate("22-01-1983"), lease1.getRealEndTime());
        assertEquals(setDate("22-01-1981"), lease1.getStartTime());
        assertEquals(setDate("22-01-1982"), lease1.getExpectedEndTime());
        
        // Check if updates didn't affect other records
        assertDeepEquals(lease2, manager.getLeaseById(lease2.getId()));  
    }
    
    @Test
    public void updateLeaseRealEndTime() {
        Customer customer1 = newCustomer("John Kviatkowsky", setDate("22-01-1986"), "0976SK41");
        Customer customer2 = newCustomer("Jozo Petrik", setDate("20-04-1991"), "0936SK00");  
        Barrow barrow1 = newBarrow("sand", 100D);
        Barrow barrow2 = newBarrow("soil", 200D);

        Lease lease1 = newLease(customer1, barrow1, new BigDecimal(1.0), 
                setDate("22-01-1983"), setDate("22-01-1981"), setDate("22-01-1982"));
        Lease lease2 = newLease(customer1, barrow2, new BigDecimal(1.0), 
                setDate("22-01-1985"), setDate("22-01-1981"), setDate("22-01-1982"));
        
        manager.createLease(lease1);
        manager.createLease(lease2);
        Long leaseId = lease1.getId();
        
        lease1 = manager.getLeaseById(leaseId);
        lease1.setRealEndTime(setDate("25-01-1983"));
        manager.updateLease(lease1);
        assertEquals(barrow1, lease1.getBarrow());
        assertEquals(customer1, lease1.getCustomer());
        assertEquals(new BigDecimal(1.0), lease1.getPrice());
        assertEquals(setDate("25-01-1983"), lease1.getRealEndTime()); //25 is different
        assertEquals(setDate("22-01-1981"), lease1.getStartTime());
        assertEquals(setDate("22-01-1982"), lease1.getExpectedEndTime());
        
        // Check if updates didn't affect other records
        assertDeepEquals(lease2, manager.getLeaseById(lease2.getId()));  
    }
    
    @Test
    public void updateLeaseStartTime() {
        Customer customer1 = newCustomer("John Kviatkowsky", setDate("22-01-1986"), "0976SK41");
        Customer customer2 = newCustomer("Jozo Petrik", setDate("20-04-1991"), "0936SK00");    
        Barrow barrow1 = newBarrow("sand", 100D);
        Barrow barrow2 = newBarrow("soil", 200D);

        Lease lease1 = newLease(customer1, barrow1, new BigDecimal(1.0), 
                setDate("22-01-1983"), setDate("22-01-1981"), setDate("22-01-1982"));
        Lease lease2 = newLease(customer1, barrow2, new BigDecimal(1.0), 
                setDate("22-01-1985"), setDate("22-01-1981"), setDate("22-01-1982"));
        
        manager.createLease(lease1);
        manager.createLease(lease2);
        Long leaseId = lease1.getId();
        
        lease1 = manager.getLeaseById(leaseId);
        lease1.setStartTime(setDate("25-01-1983"));
        manager.updateLease(lease1);
        assertEquals(barrow1, lease1.getBarrow());
        assertEquals(customer1, lease1.getCustomer());
        assertEquals(new BigDecimal(1.0), lease1.getPrice());
        assertEquals(setDate("22-01-1983"), lease1.getRealEndTime());
        assertEquals(setDate("25-01-1983"), lease1.getStartTime());     //25 is different
        assertEquals(setDate("22-01-1982"), lease1.getExpectedEndTime());
        
        // Check if updates didn't affect other records
        assertDeepEquals(lease2, manager.getLeaseById(lease2.getId()));  
    }
    
       @Test
    public void updateExpectedEndTime() {
        Customer customer1 = newCustomer("John Kviatkowsky", setDate("22-01-1986"), "0976SK41");
        Customer customer2 = newCustomer("Jozo Petrik", setDate("20-04-1991"), "0936SK00");
        Barrow barrow1 = newBarrow("sand", 100D);
        Barrow barrow2 = newBarrow("soil", 200D);

        Lease lease1 = newLease(customer1, barrow1, new BigDecimal(1.0), 
                setDate("22-01-1983"), setDate("22-01-1981"), setDate("22-01-1982"));
        Lease lease2 = newLease(customer1, barrow2, new BigDecimal(1.0), 
                setDate("22-01-1985"), setDate("22-01-1981"), setDate("22-01-1982"));
        
        manager.createLease(lease1);
        manager.createLease(lease2);
        Long leaseId = lease1.getId();
        
        lease1 = manager.getLeaseById(leaseId);
        lease1.setExpectedEndTime(setDate("25-01-1983"));
        manager.updateLease(lease1);
        assertEquals(barrow1, lease1.getBarrow());
        assertEquals(customer1, lease1.getCustomer());
        assertEquals(new BigDecimal(1.0), lease1.getPrice());
        assertEquals(setDate("22-01-1983"), lease1.getRealEndTime());
        assertEquals(setDate("22-01-1981"), lease1.getStartTime());     
        assertEquals(setDate("25-01-1983"), lease1.getExpectedEndTime()); //25 is different
        
        // Check if updates didn't affect other records
        assertDeepEquals(lease2, manager.getLeaseById(lease2.getId()));  
    }
//---------------------------------------------------------------------------------------------------    

    @Test
    public void deleteLease() {
        Customer customer1 = newCustomer("John Kviatkowsky", setDate("22-01-1986"), "0976SK41");
        Customer customer2 = newCustomer("Jozo Petrik", setDate("20-04-1991"), "0936SK00");
        Barrow barrow1 = newBarrow("sand", 100D);
        Barrow barrow2 = newBarrow("soil", 200D);

        Lease lease1 = newLease(customer1, barrow1, new BigDecimal(1.0), 
                setDate("22-01-1983"), setDate("22-01-1981"), setDate("22-01-1982"));
        Lease lease2 = newLease(customer1, barrow2, new BigDecimal(1.0), 
                setDate("22-01-1985"), setDate("22-01-1981"), setDate("22-01-1982"));
        
        manager.createLease(lease1);
        manager.createLease(lease2);
        
        assertNotNull(manager.getLeaseById(lease1.getId()));
        assertNotNull(manager.getLeaseById(lease2.getId()));
       
        manager.deleteLease(lease1);
        
        assertNull(manager.getLeaseById(lease1.getId()));
        assertNotNull(manager.getLeaseById(lease2.getId()));
    }
//----------------------------------------------DELETE WRONG LEASE-----------------------------------------    
    @Test(expected = IllegalArgumentException.class)
    public void deleteNullLease() {
        
        manager.deleteLease(null);

    } 
    
    @Test(expected = IllegalArgumentException.class)
    public void deleteLeaseWithNullId() {
        Barrow barrow = newBarrow("sand", 100D);
        Customer customer = newCustomer("Jozko Mrkvicka", setDate("22-01-1986"), "0976SK25");
        Lease lease = newLease(customer, barrow, new BigDecimal("1000.99"), 
                setDate("22-08-2018"), setDate("22-08-2015"), setDate("22-08-2016"));
        
        lease.setId(null);
        manager.deleteLease(lease);

    }       
    
    @Test(expected = IllegalArgumentException.class)
    public void deleteLeaseWithWrongId() {
        Barrow barrow = newBarrow("sand", 100D);
        Customer customer = newCustomer("Jozko Mrkvicka", setDate("22-01-1986"), "0976SK25");
        Lease lease = newLease(customer, barrow, new BigDecimal("1000.99"), 
                setDate("22-08-2018"), setDate("22-08-2015"), setDate("22-08-2016"));
        
        lease.setId(1L);
        manager.deleteLease(lease);

    } 
    
//----------------------------------CREATE WRONG LEASE-----------------------------------------------------
    @Test(expected = IllegalArgumentException.class)
    public void createNullLease() {
        
        manager.createLease(null);

    }
    
    @Test(expected = IllegalArgumentException.class)
    public void createLeaseWithWrongId() {
        Barrow barrow = newBarrow("sand", 100D);
        Customer customer = newCustomer("Jozko Mrkvicka", setDate("22-01-1986"), "0976SK25");
        Lease lease = newLease(customer, barrow, new BigDecimal("1000.99"), 
                setDate("22-08-2018"), setDate("22-08-2015"), setDate("22-08-2016"));
        
        lease.setId(1L);
        
        manager.createLease(lease);

    }
    
    @Test(expected = IllegalArgumentException.class)
    public void createLeaseWithZeroPrice() {
        Barrow barrow = newBarrow("sand", 100D);
        Customer customer = newCustomer("Jozko Mrkvicka", setDate("22-01-1986"), "0976SK25");
        Lease lease = newLease(customer, barrow, new BigDecimal("0"), 
                setDate("22-08-2018"), setDate("22-08-2015"), setDate("22-08-2016"));
        
        manager.createLease(lease);

    }
    
    @Test(expected = IllegalArgumentException.class)
    public void createLeaseWithNegativePrice() {
        Barrow barrow = newBarrow("sand", 100D);
        Customer customer = newCustomer("Jozko Mrkvicka", setDate("22-01-1986"), "0976SK25");
        Lease lease = newLease(customer, barrow, new BigDecimal("-1"), 
                setDate("22-08-2018"), setDate("22-08-2015"), setDate("22-08-2016"));
        
        manager.createLease(lease);
            
    }
    

    //tests if startTime isn't after expectedReturnTime
    @Test(expected = IllegalArgumentException.class)
    public void createLeaseWithWrongExpectedTime() {
        Barrow barrow = newBarrow("sand", 100D);
        Customer customer = newCustomer("Jozko Mrkvicka", setDate("22-01-1986"), "0976SK25");
        Lease lease = newLease(customer, barrow, new BigDecimal("100"), 
                setDate("22-08-2016"), setDate("22-08-2015"), setDate("22-08-2014"));
        
        manager.createLease(lease);

    }
    
    //tests if startTime isn't after realEndTime
    @Test(expected = IllegalArgumentException.class)
    public void createLeaseWithWrongRealTime() {
        Barrow barrow = newBarrow("sand", 100D);
        Customer customer = newCustomer("Jozko Mrkvicka", setDate("22-01-1986"), "0976SK25");
        Lease lease = newLease(customer, barrow, new BigDecimal("100"), 
                setDate("22-08-2014"), setDate("22-08-2015"), setDate("22-08-2016"));
        
        manager.createLease(lease);

    }
    
//---------------------------------------------------------------------------------------------------------
    
    
            
//-----MAREK
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

//-----MAREK koniec
    
//    ----------------------------------------------------------------------------------------------------------------

    private static Lease newLease(Customer customer, Barrow barrow, BigDecimal price,
            Date realEndTime, Date startTime, Date expectedEndTime) {
        Lease lease = new Lease();
        lease.setPrice(price);
        lease.setRealEndTime(realEndTime);
        lease.setExpectedEndTime(expectedEndTime);
        lease.setStartTime(startTime);

        return lease;

    }

    private Barrow newBarrow(String use, Double volumeLt) {
        Barrow barrow = new Barrow();
        barrow.setUse(use);
        barrow.setVolumeLt(volumeLt);

        return barrow;
    }

    private static Customer newCustomer(String fullName, Date birthDate, String idCard) {
        Customer customer = new Customer();
        customer.setBirthDate(birthDate);
        customer.setFullName(fullName);
        customer.setIdCard(idCard);

        return customer;
    }

    private void assertDeepEquals(Lease expected, Lease actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getCustomer(), actual.getCustomer());
        assertEquals(expected.getBarrow(), actual.getBarrow());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getRealEndTime(), actual.getRealEndTime());
        assertEquals(expected.getStartTime(), actual.getStartTime());
        assertEquals(expected.getExpectedEndTime(), actual.getExpectedEndTime());

    }
    
    private void assertDeepEquals(List<Lease> expectedList, List<Lease> actualList) {
        for (int i = 0; i < expectedList.size(); i++) {
            Lease expected = expectedList.get(i);
            Lease actual = actualList.get(i);
            assertDeepEquals(expected, actual);
        }
    }

    private Date setDate(String dateInString) {
        try {
            Date date = sdf.parse(dateInString);
            return date;
        } catch (ParseException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}
