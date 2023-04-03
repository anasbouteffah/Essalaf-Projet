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

public class StatistiquesController implements Initializable {

    @javafx.fxml.FXML
    private Button btnClient;
    @javafx.fxml.FXML
    private Button btnCommande;
    @javafx.fxml.FXML
    private Button btnTableau;
    @javafx.fxml.FXML
    private Button btnProduit;
    @javafx.fxml.FXML
    private TableView<Tableau> tv;
    @javafx.fxml.FXML
    private TableColumn<Tableau,String> colnomproduit;
    @javafx.fxml.FXML
    private TableColumn<Tableau,Integer> colmontant;
    @javafx.fxml.FXML
    private TableColumn<Tableau,String> coldate;

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

    public ObservableList<Tableau> getTableauList() {
        ObservableList<Tableau> tableauList = FXCollections.observableArrayList();
        Connection conn = getConnexion();
        String query = "SELECT * from tableau";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Tableau tableau;
            while (rs.next()) {
                tableau = new Tableau(rs.getString("nom"), rs.getInt("montant"),rs.getString("date"));
                tableauList.add(tableau);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return tableauList;
    }

    public void showProduits() {
        ObservableList<Tableau> list = getTableauList();
        colnomproduit.setCellValueFactory(new PropertyValueFactory<Tableau,String>("nom"));
        colmontant.setCellValueFactory(new PropertyValueFactory<Tableau,Integer>("montant"));
        coldate.setCellValueFactory(new PropertyValueFactory<Tableau,String>("date"));

        tv.setItems(list);

    }

    @javafx.fxml.FXML
    public void gestionClientsPage(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene newScene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(newScene);
        primaryStage.show();
    }

    @javafx.fxml.FXML
    public void gestionCommandesPage(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("commandes.fxml"));
        Scene newScene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(newScene);
        primaryStage.show();
    }

    @javafx.fxml.FXML
    public void tableauPage(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void gestionProduitsPage(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("produits.fxml"));
        Scene newScene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(newScene);
        primaryStage.show();
    }

    public void initialize(URL location, ResourceBundle resources) {
        showProduits();
    }
}
