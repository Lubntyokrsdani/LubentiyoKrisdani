package com.dynamic.orderflowapp;

import com.dynamic.orderflowapp.view.LoginForm;
import javax.swing.SwingUtilities;

public class OrderFlowAPP {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            LoginForm login = new LoginForm();

            login.setTitle("OrderFlowAPP - Login");
            login.setLocationRelativeTo(null);
            login.setVisible(true);

        });

    }
}