/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barrowrent;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Branislav Smik <xsmik @fi.muni>
 */
public class Lease {
    private Long id;
    private Long customerId;
    private Long barrowId;
    private BigDecimal price;
    private Date realEndTime;
    private Date startTime;
    private Date expectedEndTime;
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setBarrowId(Long barrowId) {
     this.barrowId = barrowId;
    }

    public Long getBarrowId() {
        return barrowId;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setRealEndTime(Date realEndTime) {
        this.realEndTime = realEndTime;
    }
    
    public Date getRealEndTime() {
        return realEndTime;
    }
    
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    
    public Date getStartTime() {
        return startTime;
    }
    
    public void setExpectedEndTime(Date expectedEndTime) {
        this.expectedEndTime = expectedEndTime;
    }
    
    public Date getExpectedEndTime() {
        return expectedEndTime;
    }
    
    @Override
    public String toString() {
        return "Lease{" + id + '}';
    }
    
    //EQUALS a HASHCODE? !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    
    @Override
    public int hashCode() {
        return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
    }
    
}
