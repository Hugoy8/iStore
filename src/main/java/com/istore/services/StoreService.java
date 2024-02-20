package com.istore.services;

import com.istore.dao.StoreDAO;
import com.istore.models.Store;
import com.istore.models.User;

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
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public void createStore(Store store) throws SQLException {
        storeDAO.createStore(store);
    }

    /**
     * Supprime un magasin.
     * @param storeId L'identifiant du magasin à supprimer.
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public void deleteStore(int storeId) throws SQLException {
        storeDAO.deleteStore(storeId);
    }

    /**
     * Met à jour un magasin.
     * @param store Le magasin à mettre à jour.
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public void updateStore(Store store) throws SQLException {
        storeDAO.updateStore(store);
    }

    /**
     * Liste tous les magasins.
     * @return La liste de tous les magasins.
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public List<Store> listAllStoresWithEmployees() throws SQLException {
        return storeDAO.listAllStoresWithEmployees();
    }

    /**
     * Retrouve un magasin par son ID.
     * @param id L'ID du magasin.
     * @return Le magasin trouvé.
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public List<User> getEmployeesByStoreId(int id) throws SQLException {
        return storeDAO.getEmployeesByStoreId(id);
    }

    /**
     * Ajoute un employé à un magasin.
     * @param storeId L'ID du magasin.
     * @param employeeId L'ID de l'employé.
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public void addEmployeeToStore(int storeId, int employeeId) throws SQLException {
        storeDAO.addEmployeeToStore(storeId, employeeId);
    }

    /**
     * Supprime un employé d'un magasin.
     * @param storeId L'ID du magasin.
     * @param employeeId L'ID de l'employé.
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public void removeEmployeeFromStore(int storeId, int employeeId) throws SQLException {
        storeDAO.removeEmployeeFromStore(storeId, employeeId);
    }

    /**
     * Supprime un employé de tous les magasins.
     * @param employeeId L'ID de l'employé.
     * @throws SQLException Exception SQL en cas d'erreur durant une requête à la base de données
     */
    public void removeEmployeeFromAllStores(int employeeId) throws SQLException {
        storeDAO.removeEmployeeFromAllStores(employeeId);
    }
}
