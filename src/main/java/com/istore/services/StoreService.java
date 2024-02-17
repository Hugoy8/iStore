package com.istore.services;

import com.istore.dao.StoreDAO;
import com.istore.models.Store;

import java.sql.SQLException;
import java.util.List;

public class StoreService {

    private StoreDAO storeDAO;

    public StoreService(StoreDAO storeDAO) {
        this.storeDAO = storeDAO;
    }

    /**
     * Crée un nouveau magasin.
     * @param store Le magasin à créer.
     */
    public void createStore(Store store) throws SQLException {
        storeDAO.createStore(store);
    }

    /**
     * Supprime un magasin.
     * @param storeId L'identifiant du magasin à supprimer.
     */
    public void deleteStore(int storeId) throws SQLException {
        storeDAO.deleteStore(storeId);
    }

    /**
     * Liste tous les magasins.
     * @return La liste de tous les magasins.
     */
    public List<Store> listAllStoresWithEmployees() throws SQLException {
        return storeDAO.listAllStoresWithEmployees();
    }
}
