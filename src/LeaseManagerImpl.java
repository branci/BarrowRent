/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barrowrent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author Branislav Smik <xsmik @fi.muni>
 */
public class LeaseManagerImpl implements LeaseManager {
    
    final static Logger log = LoggerFactory.getLogger(LeaseManagerImpl.class);
    private final DataSource dataSource;
    
    public LeaseManagerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    
    @Override
    public void createLease(Lease lease) throws ServiceFailureException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void updateLease(Lease lease) throws ServiceFailureException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void deleteLease(Long leaseId) throws ServiceFailureException {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("DELETE FROM LEASE WHERE id=?")) {
                st.setLong(1, leaseId);
                if (st.executeUpdate() != 1) {
                    throw new ServiceFailureException("did not delete lease with id =" + leaseId);
                }
            }
        } catch (SQLException ex) {
            log.error("db connection problem", ex);
            throw new ServiceFailureException("Error when retrieving all leases", ex);
        }
    }
    
    @Override
    public List<Lease> findAllLeases() throws ServiceFailureException {
        log.debug("finding all leases");
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT id,customerId,"
                    + "barrowId,price,realEndTime,startTime,expectedEndTime FROM LEASE")) {
                ResultSet rs = st.executeQuery();
                List<Lease> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(resultSetToLease(rs));
                }
                return result;
            }
        } catch (SQLException ex) {
            log.error("db connection problem", ex);
            throw new ServiceFailureException("Error when retrieving all leases", ex);
        }
    }
            
    
    //Prerobena metoda z Customer customer na Long customerId
    @Override
    public List<Lease> findLeasesForCustomer(Long customerId) throws ServiceFailureException {
        log.debug("finding all leases for customer");
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT id,customerId,"
                    + "barrowId,price,realEndTime,startTime,expectedEndTime FROM LEASE WHERE customerId=?")) {
                st.setLong(1, customerId);
                ResultSet rs = st.executeQuery();
                List<Lease> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(resultSetToLease(rs));
                }
                return result;
            }
        } catch (SQLException ex) {
            log.error("db connection problem", ex);
            throw new ServiceFailureException("Error when retrieving all leases", ex);
        }
    }
    
    //Prerobena metoda z Barrow barrow na Long barrowId
    @Override
    public List<Lease> findLeasesForBarrow(Long barrowId) throws ServiceFailureException {
                log.debug("finding all leases for barrow");
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT id,customerId,"
                    + "barrowId,price,realEndTime,startTime,expectedEndTime FROM LEASE WHERE barrowId=?")) {
                st.setLong(1, barrowId);
                ResultSet rs = st.executeQuery();
                List<Lease> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(resultSetToLease(rs));
                }
                return result;
            }
        } catch (SQLException ex) {
            log.error("db connection problem", ex);
            throw new ServiceFailureException("Error when retrieving all leases", ex);
        }
    }
    
    @Override
    public Lease getLeaseById(Long id) throws ServiceFailureException {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT id,customerId,"
                    + "barrowId,price,realEndTime,startTime,expectedEndTime FROM LEASE WHERE id=?")) {
                st.setLong(1, id);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    Lease lease = resultSetToLease(rs);
                    if (rs.next()) {
                        throw new ServiceFailureException(
                                "Internal error: More entities with the same id found "
                                + "(source id: " + id + ", found " + lease + " and " + resultSetToLease(rs));
                    }
                    return lease;
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            log.error("db connection problem", ex);
            throw new ServiceFailureException("Error when retrieving all leases", ex);
        }
    }
    
    private Lease resultSetToLease(ResultSet rs) throws SQLException {
        Lease lease = new Lease();
        lease.setId(rs.getLong("id"));
        lease.setCustomerId(rs.getLong("customerId"));
        lease.setBarrowId(rs.getLong("barrowId")); 
        lease.setPrice(rs.getBigDecimal("price"));
        lease.setRealEndTime(rs.getDate("realEndTime"));
        lease.setStartTime(rs.getDate("startTime")); 
        lease.setExpectedEndTime(rs.getDate("expectedEndTime")); 
        return lease;
    }
    
}
