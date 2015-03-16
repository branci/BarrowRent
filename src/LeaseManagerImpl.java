/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barrowrent;

import java.util.List;

/**
 *
 * @author Branislav Smik <xsmik @fi.muni>
 */
public class LeaseManagerImpl implements LeaseManager {
    
    @Override
    public void createLease(Lease lease) throws ServiceFailureException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void updateLease(Lease lease) throws ServiceFailureException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void deleteLease(Lease lease) throws ServiceFailureException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public List<Lease> findAllLeases() throws ServiceFailureException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
            
    
    @Override
    public List<Lease> findLeasesForCustomer(Customer customer) throws ServiceFailureException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public List<Lease> findLeasesForBarrow(Barrow barrow) throws ServiceFailureException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Lease getLeaseById(Long id) throws ServiceFailureException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
