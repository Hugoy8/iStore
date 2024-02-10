package com.istore.gui.controllers.auth;

import com.istore.gui.AppLauncher;

import java.io.IOException;

public class LoginController {

    public void handleLogin() throws IOException {
        AppLauncher.showDashboardView();
    }

    public void showRegisterView() throws IOException {
        AppLauncher.showRegisterView();
    }
}
