/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImplTests;

import barrowrent.Barrow;
import barrowrent.BarrowManagerImpl;
import java.sql.SQLException;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Branislav Smik <xsmik @fi.muni>
 */
public class BarrowManagerImplTest {
    
    private BarrowManagerImpl manager;
    
    @Before
    public void setUp() throws SQLException {
        manager = new BarrowManagerImpl();
    }
    
    @Test
    public void createBarrow() {
        Barrow barrow = newBarrow("sand", 100.1D);
        manager.createBarrow(barrow);
        
        Long barrowId = barrow.getId();
        assertNotNull(barrowId);
        Barrow result = manager.getBarrowById(barrowId);
        assertEquals(barrow, result);
        assertNotSame(barrow, result);
        assertDeepEquals(barrow, result);
}
    //rozdelit na mensie?
    @Test
    public void updateBarrow() {
        Barrow barrow = newBarrow("sand", 100.2D);
        Barrow barrow2 = newBarrow("soil", 200D);
        manager.createBarrow(barrow);
        manager.createBarrow(barrow2);
        Long barrowId = barrow.getId();
        
        barrow = manager.getBarrowById(barrowId);
        barrow.setUse("water");
        manager.updateBarrow(barrow);
        assertEquals("water", barrow.getUse());
        assertEquals(100.2, barrow.getVolumeLt(), 0.01); //third value is a threshold within which two doubles should be considered "equal"
        
        barrow = manager.getBarrowById(barrowId);
        barrow.setVolumeLt(111.1D);
        manager.updateBarrow(barrow);
        assertEquals("water", barrow.getUse());
        assertEquals(111.1, barrow.getVolumeLt(), 0.01);
        
        barrow = manager.getBarrowById(barrowId);
        barrow.setUse(null);
        manager.updateBarrow(barrow);
        assertEquals(111.1, barrow.getVolumeLt(), 0.01);
        assertNull(barrow.getUse());
        
        // Check if updates didn't affect other records
        assertDeepEquals(barrow2, manager.getBarrowById(barrow2.getId()));
    }
//----------------------------------------UPDATE WRONG BARROW-----------------------------------------
    @Test
    public void updateBarrowWithNull() {
        Barrow barrow = newBarrow("sand", 100D);
        manager.createBarrow(barrow);
    
        try {
            manager.updateBarrow(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }
    
    @Test
    public void updateBarrowWithNullId() {
        Barrow barrow = newBarrow("sand", 100D);
        manager.createBarrow(barrow);
        Long barrowId = barrow.getId();
    
        try {
            barrow = manager.getBarrowById(barrowId);
            barrow.setId(null);
            manager.updateBarrow(barrow);        
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }
    
    @Test
    public void updateBarrowWithWrongId() {
        Barrow barrow = newBarrow("sand", 100D);
        manager.createBarrow(barrow);
        Long barrowId = barrow.getId();
    
        try {
            barrow = manager.getBarrowById(barrowId);
            barrow.setId(barrowId - 1);
            manager.updateBarrow(barrow);        
            fail();      
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }
    
    @Test
    public void updateBarrowWithNullUse() {
        Barrow barrow = newBarrow("sand", 100D);
        manager.createBarrow(barrow);
        Long barrowId = barrow.getId();
        
        try {
            barrow = manager.getBarrowById(barrowId);
            barrow.setUse(null);
            manager.updateBarrow(barrow);        
            fail();      
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }
    
    @Test
    public void updateBarrowWithZeroVolume() {
        Barrow barrow = newBarrow("sand", 100D);
        manager.createBarrow(barrow);
        Long barrowId = barrow.getId();
        
        try {
            barrow = manager.getBarrowById(barrowId);
            barrow.setVolumeLt(0D);
            manager.updateBarrow(barrow);        
            fail();      
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }
    
    @Test
    public void updateBarrowWithNegativeVolume(){
        Barrow barrow = newBarrow("sand", 100D);
        manager.createBarrow(barrow);
        Long barrowId = barrow.getId();
        
        try {
            barrow = manager.getBarrowById(barrowId);
            barrow.setVolumeLt(-1D);
            manager.updateBarrow(barrow);        
            fail();      
        } catch (IllegalArgumentException ex) {
            //OK
        }
    }
               
//------------------------------------------------------------------------------------------------------

    @Test 
    public void deleteBarrow() {
        Barrow barr1 = newBarrow("sand", 100D);
        Barrow barr2 = newBarrow("soil", 200D);
        
        manager.createBarrow(barr1);
        manager.createBarrow(barr2);
        
        assertNotNull(manager.getBarrowById(barr1.getId()));
        assertNotNull(manager.getBarrowById(barr2.getId()));
        
        manager.deleteBarrow(barr1);
        
        assertNull(manager.getBarrowById(barr1.getId()));
        assertNotNull(manager.getBarrowById(barr2.getId()));
    }
//----------------------------------DELETE WRONG BARROW-------------------------------------------------
    //netreba ich najprv mmanager.create?
    @Test
    public void deleteNullBarrow() {
        
        try {
            manager.deleteBarrow(null);
            fail();
        } catch (IllegalArgumentException ex){
            //OK
        }
    }
    
    @Test
    public void deleteBarrowWithNullId() {
        Barrow barrow = newBarrow("sand", 100D);
        
        try {
            barrow.setId(null);
            manager.deleteBarrow(barrow);
            fail();
        } catch (IllegalArgumentException ex){
            //OK
        }
    }
    
    @Test
    public void deleteBarrowWithWrongId() {
        Barrow barrow = newBarrow("sand", 100D);
        
        try {
            barrow.setId(1l);
            manager.deleteBarrow(barrow);
            fail();
        } catch (IllegalArgumentException ex){
            //OK
        }
    }
//------------------------------------------------------------------------------------------------------
    private Barrow newBarrow(String use, Double volumeLt) {
        Barrow barrow = new Barrow();
        //barrow.setId(id);
        barrow.setUse(use);
        barrow.setVolumeLt(volumeLt);
        
        return barrow;
    }
    
    private void assertDeepEquals(Barrow expected, Barrow actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUse(), actual.getUse());
        assertEquals(expected.getVolumeLt(), actual.getVolumeLt());
    }
    
}
