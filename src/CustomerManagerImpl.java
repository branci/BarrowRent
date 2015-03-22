/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barrowrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Branislav Smik <xsmik @fi.muni>, Marek Perichta
 */
public class CustomerManagerImpl implements CustomerManager {
    
    @Override
    public void createCustomer(Customer customer) throws ServiceFailureException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void updateCustomer(Customer customer) throws ServiceFailureException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void deleteCustomer(long customerId) throws ServiceFailureException {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("DELETE FROM barrow WHERE id=?")) {
                st.setLong(1, customerId);
                if (st.executeUpdate() != 1) {
                    throw new ServiceFailureException("did not delete customer with id =" + customerId);
                }
            }
        } catch (SQLException ex) {
            log.error("db connection problem", ex);
            throw new ServiceFailureException("Error when retrieving all customers", ex);
        }
    }
    
    @Override
    public Customer getCustomerById(Long id) throws ServiceFailureException {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT id,fullName,birthDate,idCard FROM customer WHERE id = ?")) {
                st.setLong(1, id);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    Customer customer = resultSetToCustomer(rs);
                    if (rs.next()) {
                        throw new ServiceFailureException(
                                "Internal error: More entities with the same id found "
                                        + "(source id: " + id + ", found " + customer + " and " + resultSetToCustomer(rs));
                    }
                    return customer;
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            log.error("db connection problem", ex);
            throw new ServiceFailureException("Error when retrieving all graves", ex);
        }
    }
    
    @Override
    public List<Customer> findAllCustomers() throws ServiceFailureException {
        log.debug("finding all customers");
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT id,fullName,birthDate,idCard FROM grave")) {
                ResultSet rs = st.executeQuery();
                List<Customer> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(resultSetToCustomer(rs));
                }
                return result;
            }
        } catch (SQLException ex) {
            log.error("db connection problem", ex);
            throw new ServiceFailureException("Error when retrieving all customers", ex);
        }
    }
    
    private Customer resultSetToCustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getLong("id"));
        customer.setFullName(rs.getString("fullName"));
        customer.setBirthDate(rs.getDate("birthDate"));
        customer.setIdCard(rs.getString("idCard"));
        return customer;
    }
    
}
