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
public interface LeaseManager {
    
    /**
     *
     * @param lease
     * @throws ServiceFailureException
     */
    void createLease(Lease lease) throws ServiceFailureException;
    
    /**
     *
     * @param lease
     * @throws ServiceFailureException
     */
    void updateLease(Lease lease) throws ServiceFailureException;
    
    /**
     *
     * @param leaseId 
     * @throws ServiceFailureException
     */
    void deleteLease(Long leaseId) throws ServiceFailureException;
    
    /**
     *
     * @return
     * @throws ServiceFailureException
     */
    List<Lease> findAllLeases() throws ServiceFailureException;
    
    /**
     *
     * @param customerId
     * @return
     * @throws ServiceFailureException
     */
    List<Lease> findLeasesForCustomer(Long customerId) throws ServiceFailureException;
    
    /**
     *
     * @param barrowId
     * @return
     * @throws ServiceFailureException
     */
    List<Lease> findLeasesForBarrow(Long barrowId) throws ServiceFailureException;
    
    /**
     *
     * @param id
     * @return
     * @throws ServiceFailureException
     */
    Lease getLeaseById(Long id) throws ServiceFailureException;
    
}
