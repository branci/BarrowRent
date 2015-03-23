/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ImplTests;


import barrowrent.Customer;
import barrowrent.CustomerManagerImpl;
import java.util.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;
import java.text.ParseException;
import java.text.SimpleDateFormat;


import static org.junit.Assert.*;

/**
 *
 * @author marek
 */
public class CustomerManagerImplTest {
    
    private CustomerManagerImpl manager;
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    
    
    @Before
    public void setUp() throws SQLException {
        manager = new CustomerManagerImpl();
    }
    

    @Test
    public void createCustomer() {
        

        
        Customer customer = newCustomer("Jozko Mrkvicka",setDateDefault(),"0976SK25");
        manager.createCustomer(customer);

        Long customerId = customer.getId();
        assertNotNull(customerId);
        Customer result = manager.getCustomerById(customerId);
        assertEquals(customer, result);
        assertNotSame(customer, result);
        assertDeepEquals(customer, result);
    }

    @Test
    public void getContact() {
        
        assertNull(manager.getCustomerById(1l));

        
        Customer customer = newCustomer("Jozko Mrkvicka",setDateDefault(),"0976SK25");
        manager.createCustomer(customer);
        Long customerId = customer.getId();

        Customer result = manager.getCustomerById(customerId);
        assertEquals(customer, result);
        assertDeepEquals(customer, result);
    }

    @Test
    public void getAllContacts() {

        assertTrue(manager.findAllCustomers().isEmpty());

        
        Customer c1 = newCustomer("John Kviatkowsky",setDateDefault(),"0976SK41");
        Customer c2 = newCustomer("Dezider Puska",setDateDefault(),"0936SK25");

        manager.createCustomer(c1);
        manager.createCustomer(c2);

        List<Customer> expected = Arrays.asList(c1,c2);
        List<Customer> actual = manager.findAllCustomers();

        Collections.sort(actual,idComparator);
        Collections.sort(expected,idComparator);

        assertEquals(expected, actual);
        assertDeepEquals(expected, actual);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void addContactFromNull() {

        manager.createCustomer(null);

    }
    
    @Test(expected = IllegalArgumentException.class)
    public void addContactWithId() {

        Customer customer = newCustomer("Jozko Mrkvicka",setDateDefault(),"0936SK25");
        customer.setId(1l);
 
        manager.createCustomer(customer);

    }
   
    @Test(expected = IllegalArgumentException.class) 
    public void addContactWithNullName() {

        Customer customer = newCustomer(null,setDateDefault(),"0936SK25");
  
        manager.createCustomer(customer);
 
    }
    
    @Test(expected = IllegalArgumentException.class) 
    public void addContactWithNullIdNumber() {

        Customer customer = newCustomer("Jozko Mrkvicka",setDateDefault(),null);

        manager.createCustomer(customer);

    }
    
    
    
    
    @Test
    public void updateCustomer() throws ParseException {
         Customer customer = newCustomer("John Kviatkowsky",setDateDefault(),"0976SK41");
         Customer customer2 = newCustomer("Dezider Puska",setDateDefault(),"0936SK25");
         manager.createCustomer(customer);
         manager.createCustomer(customer2);
         Long customerId = customer.getId();
         
         
         customer = manager.getCustomerById(customerId);
         customer.setFullName("Leopold Mickiewicz");
         manager.updateCustomer(customer);
         assertEquals("Leopold Mickiewicz", customer.getFullName());
         assertEquals(customer.getBirthDate(),setDateDefault()); 
         assertEquals(customer.getIdCard(),"0976SK41"); 
         
         customer = manager.getCustomerById(customerId);
         customer.setIdCard("0000SK00");
         manager.updateCustomer(customer);
         assertEquals("John Kviatkowsky", customer.getFullName());
         assertEquals(customer.getBirthDate(),setDateDefault()); 
         assertEquals(customer.getIdCard(),"0000SK00"); 
         
         
         String dateInString = "22-01-1900";
         Date date = sdf.parse(dateInString);
         
         customer = manager.getCustomerById(customerId);
         customer.setBirthDate(date);
         manager.updateCustomer(customer);
         assertEquals("John Kviatkowsky", customer.getFullName());
         assertEquals(customer.getBirthDate(),date); 
         assertEquals(customer.getIdCard(),"0976SK41");

         // Check if updates didn't affect other records
         assertDeepEquals(customer2, manager.getCustomerById(customer2.getId()));
    }
    
    
    @Test(expected = IllegalArgumentException.class)
    public void updateCustomerWithNullArgument() {
        
        manager.updateCustomer(null);
            
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void updateCustomerWithNullId() {

        Customer customer = newCustomer("Jozko Mrkvicka",setDateDefault(),"0936SK25");
        manager.createCustomer(customer);
        Long customerId = customer.getId();
        
        customer = manager.getCustomerById(customerId);
        customer.setId(null);
        manager.updateCustomer(customer);        
            
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void updateBarrowWithWrongId() {
        Customer customer = newCustomer("Jozko Mrkvicka",setDateDefault(),"0936SK25");
        manager.createCustomer(customer);
        Long customerId = customer.getId();
        
        customer = manager.getCustomerById(customerId);
        customer.setId(customerId - 1);
        manager.updateCustomer(customer);

    }
    
    @Test(expected = IllegalArgumentException.class)
    public void updateBarrowWithNullName() {
        Customer customer = newCustomer("Jozko Mrkvicka",setDateDefault(),"0936SK25");
        manager.createCustomer(customer);
        Long customerId = customer.getId();
        
        customer = manager.getCustomerById(customerId);
        customer.setFullName(null);
        manager.updateCustomer(customer);


    } 

    @Test(expected = IllegalArgumentException.class)
    public void updateBarrowWithNullIdNumber() {
        Customer customer = newCustomer("Jozko Mrkvicka",setDateDefault(),"0936SK25");
        manager.createCustomer(customer);
        Long customerId = customer.getId();
        
        customer = manager.getCustomerById(customerId);
        customer.setIdCard(null);
        manager.updateCustomer(customer);

    }     
    
    
    @Test
    public void deleteCustomer() {
      
        Customer c1 = newCustomer("John Kviatkowsky",setDateDefault(),"0976SK41");
        Customer c2 = newCustomer("Dezider Puska",setDateDefault(),"0936SK25");
        manager.createCustomer(c1);
        manager.createCustomer(c2);
        
        assertNotNull(manager.getCustomerById(c1.getId()));
        assertNotNull(manager.getCustomerById(c2.getId()));

        manager.deleteCustomer(c1.getId());
        
        assertNull(manager.getCustomerById(c1.getId()));
        assertNotNull(manager.getCustomerById(c2.getId())); 
        
    }
    
     @Test(expected = IllegalArgumentException.class)
     public void deleteNullCustomer() {

        manager.deleteCustomer(null);

     }
     
     
     @Test(expected = IllegalArgumentException.class)
     public void deleteCustomerWithWrongId() {
        Customer customer = newCustomer("Jozko Mrkvicka",setDateDefault(),"0936SK25");
        manager.createCustomer(customer);

        customer.setId(1l);
        manager.deleteCustomer(2l);

     }
        
    
    private static Customer newCustomer(String fullName, Date birthDate, String idCard) {
        Customer customer = new Customer();
        customer.setBirthDate(birthDate);
        customer.setFullName(fullName);
        customer.setIdCard(idCard);
        
        return customer;
    }
    
    private void assertDeepEquals(List<Customer> expectedList, List<Customer> actualList) {
        for (int i = 0; i < expectedList.size(); i++) {
            Customer expected = expectedList.get(i);
            Customer actual = actualList.get(i);
            assertDeepEquals(expected, actual);
        }
    }
    
    private void assertDeepEquals(Customer expected, Customer actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getBirthDate(), actual.getBirthDate());
        assertEquals(expected.getFullName(), actual.getFullName());
        assertEquals(expected.getIdCard(), actual.getIdCard());
    }
    
    private static final Comparator<Customer> idComparator = new Comparator<Customer>() {

        @Override
        public int compare(Customer o1, Customer o2) {
            return Long.valueOf(o1.getId()).compareTo(Long.valueOf(o2.getId()));
        }
    };

    private Date setDateDefault() {
        try {
            String dateInString = "22-01-1986";
            Date date = sdf.parse(dateInString);
            return date;
        } catch (ParseException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}
