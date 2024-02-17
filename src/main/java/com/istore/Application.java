package com.istore;

import com.istore.dao.InventoryDAO;
import com.istore.dao.StoreDAO;
import com.istore.dao.UserDAO;
import com.istore.dao.WhitelistEmailDAO;
import com.istore.database.Database;
import com.istore.gui.AppLauncher;
import com.istore.services.AuthService;
import com.istore.services.InventoryService;
import com.istore.services.StoreService;
import com.istore.services.UserService;

public class Application {
    private static final Database database = new Database();
    private static final AuthService authService = new AuthService(new UserDAO(getDatabase()));

    private static final InventoryService inventoryService = new InventoryService(new InventoryDAO(getDatabase()));

    private static final StoreService storeService = new StoreService(new StoreDAO(getDatabase()));

    private static final UserService userService = new UserService(new UserDAO(getDatabase()), new WhitelistEmailDAO(getDatabase()));

    public static void main(String[] args) {
        AppLauncher.main(args);
    }

    /**
     * Récupération de l'instance de la base de donnée.
     * @return L'instance de la base de donnée.
     */
    public static Database getDatabase() {
        return database;
    }
    /**
     * Récupération de l'instance du service d'authentification.
     * @return L'instance du service d'authentification.
     */
    public static AuthService getAuthService() {
        return authService;
    }
    /**
     * Récupération de l'instance du service de l'inventaire.
     * @return L'instance du service de l'inventaire.
     */
    public static InventoryService getInventoryService() {
        return inventoryService;
    }
    /**
     * Récupération de l'instance du service des magasins.
     * @return L'instance du service des magasins.
     */
    public static StoreService getStoreService() {
        return storeService;
    }
    /**
     * Récupération de l'instance du service des utilisateurs.
     * @return L'instance du service des utilisateurs.
     */
    public static UserService getUserService() {
        return userService;
    }
}
