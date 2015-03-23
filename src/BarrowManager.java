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
public interface BarrowManager {
    
    /**
     *
     * @param barrow
     * @throws ServiceFailureException
     */
    void createBarrow(Barrow barrow) throws ServiceFailureException;
    
    /**
     *
     * @param barrow
     * @throws ServiceFailureException
     */
    void updateBarrow(Barrow barrow) throws ServiceFailureException;
    
    /**
     *
     * @param barrowId
     * @throws ServiceFailureException
     */
    void deleteBarrow(Long barrowId) throws ServiceFailureException;
    
    /**
     *
     * @return
     * @throws ServiceFailureException
     */
    List<Barrow> findAllBarrows() throws ServiceFailureException;
    
    /**
     *
     * @param id
     * @return
     * @throws ServiceFailureException
     */
    Barrow getBarrowById(Long id) throws ServiceFailureException;
    
    
    
}
