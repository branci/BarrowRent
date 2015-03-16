/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImplTests;


import barrowrent.Barrow;
import barrowrent.Customer;
import barrowrent.Lease;
import java.math.BigDecimal;
import java.util.Date;
import org.junit.Test;

/**
 *
 * @author Branci
 */
public class LeaseManagerImplTest {
    
    @Test
    public void createLease() {
        
    }
    
    private static Lease newLease(Long id, Customer customer, Barrow barrow, BigDecimal price,
                                  Date realEndTime, Date startTime, Date expectedEndTime) {
        Lease lease = new Lease();
        lease.setId(id);
        lease.setPrice(price);
        lease.setRealEndTime(realEndTime);
        lease.setExpectedEndTime(expectedEndTime);
        lease.setStartTime(startTime);
        
        
        return lease;
        
    }
}
