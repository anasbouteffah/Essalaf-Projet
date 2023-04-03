package com.example.essalaf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloController implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnClient;

    @FXML
    private Button btnCommande;

    @FXML
    private Button btnCredit;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnProduit;

    @FXML
    private Button btnTableau;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<Clients, Integer> colID;

    @FXML
    private TableColumn<Clients, String> colNom;

    @FXML
    private TableColumn<Clients, String> colTelephone;

    @FXML
    private TextField id;

    @FXML
    private TextField nom;

    @FXML
    private TextField telephone;

    @FXML
    private TableView<Clients> tvClient;


    
    public Connection getConnexion() {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/esalaf","root","");
            return  conn;
        } catch (Exception ex) {
            System.out.println("Error: "+ ex.getMessage());
            return null;
        }
    }

    public ObservableList<Clients> getClientsList() {
        ObservableList<Clients> clientsList = FXCollections.observableArrayList();
        Connection conn = getConnexion();
        String query = "SELECT * from client";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Clients clients;
            while (rs.next()) {
                clients = new Clients(rs.getInt("id"), rs.getString("nom"),rs.getString("telephone"));
                clientsList.add(clients);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return clientsList;
    }

    public void showClients() {
        ObservableList<Clients> list = getClientsList();
        colID.setCellValueFactory(new PropertyValueFactory<Clients,Integer>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<Clients,String>("nom"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<Clients,String>("telephone"));

        tvClient.setItems(list);

    }

    private void insertClient() {
        String query = "INSERT INTO client VALUES ('" + id.getText() + "','" + nom.getText() + "','" + telephone.getText() + "')";
        executeQuery(query);
        showClients();
    }

    private void updateClient() {
        String query = "UPDATE client SET nom='" + nom.getText() + "', telephone ='" + telephone.getText() + "' WHERE id='" + id.getText() + "'";
        executeQuery(query);
        showClients();
    }

    private void deleteClient() {
        String query = "DELETE FROM client WHERE id=" + id.getText() ;
        executeQuery(query);
        showClients();
    }

    private void executeQuery(String query) {
        Connection conn = getConnexion();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void onAddBtnClicked(ActionEvent event) {
        if(event.getSource()== btnAdd) {
            insertClient();
        }
    }

    @FXML
    void onDeleteBtnClicked(ActionEvent event) {
        if(event.getSource() == btnDelete) {
            deleteClient();
        }
    }

    @FXML
    void onUpdateBtnClicked(ActionEvent event) {
        if(event.getSource() == btnUpdate) {
            updateClient();
        }
    }

    @FXML
    void gestionClientsPage(ActionEvent event) {

    }

    @FXML
     private void gestionProduitsPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("produits.fxml"));
        Scene newScene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(newScene);
        primaryStage.show();
    }


    @FXML
    void gestionCommandesPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("commandes.fxml"));
        Scene newScene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(newScene);
        primaryStage.show();
    }

    @FXML
    void tableauPage(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showClients();
    }

}