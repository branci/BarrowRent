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
public class BarrowManagerImpl implements BarrowManager {

    final static Logger log = LoggerFactory.getLogger(BarrowManagerImpl.class);
    private final DataSource dataSource;

    public BarrowManagerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public void createBarrow(Barrow barrow) throws ServiceFailureException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void updateBarrow(Barrow barrow) throws ServiceFailureException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void deleteBarrow(Long barrowId) throws ServiceFailureException {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("DELETE FROM barrow WHERE id=?")) {
                st.setLong(1, barrowId);
                if (st.executeUpdate() != 1) {
                    throw new ServiceFailureException("did not delete barrow with id =" + barrowId);
                }
            }
        } catch (SQLException ex) {
            log.error("db connection problem", ex);
            throw new ServiceFailureException("Error when retrieving all barrows", ex);
        }
    }
    
    @Override
    public List<Barrow> findAllBarrows() throws ServiceFailureException {
        log.debug("finding all barrows");
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT id,use,volumeLt FROM grave")) {
                ResultSet rs = st.executeQuery();
                List<Barrow> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(resultSetToBarrow(rs));
                }
                return result;
            }
        } catch (SQLException ex) {
            log.error("db connection problem", ex);
            throw new ServiceFailureException("Error when retrieving all barrows", ex);
        }
    }
    
    @Override
    public Barrow getBarrowById(Long id) throws ServiceFailureException {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement("SELECT id,use,volumeLt FROM barrow WHERE id = ?")) {
                st.setLong(1, id);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    Barrow barrow = resultSetToBarrow(rs);
                    if (rs.next()) {
                        throw new ServiceFailureException(
                                "Internal error: More entities with the same id found "
                                        + "(source id: " + id + ", found " + barrow + " and " + resultSetToBarrow(rs));
                    }
                    return barrow;
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            log.error("db connection problem", ex);
            throw new ServiceFailureException("Error when retrieving all barrows", ex);
        }
    }
    
    private Barrow resultSetToBarrow(ResultSet rs) throws SQLException {
        Barrow barrow = new Barrow();
        barrow.setId(rs.getLong("id"));
        barrow.setUse(rs.getString("use"));
        barrow.setVolumeLt(rs.getDouble("volumeLt"));
        return barrow;
    }
    
}
