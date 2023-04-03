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

public class ProduitController implements Initializable {
    @javafx.fxml.FXML
    private Button btnClient;
    @javafx.fxml.FXML
    private Button btnCommande;
    @javafx.fxml.FXML
    private Button btnCredit;
    @javafx.fxml.FXML
    private Button btnTableau;
    @javafx.fxml.FXML
    private Button btnProduit;
    @javafx.fxml.FXML
    private TextField nomProduit;
    @javafx.fxml.FXML
    private TextField prixProduit;
    @javafx.fxml.FXML
    private Button btnenregistrer;
    @javafx.fxml.FXML
    private Button btnSuprimer;
    @javafx.fxml.FXML
    private TableView<Produits> tvProduit;
    @javafx.fxml.FXML
    private TableColumn<Produits, String> colProduit;
    @javafx.fxml.FXML
    private TableColumn<Produits, Integer> colPrix;
    @javafx.fxml.FXML
    private TableColumn<Produits, String> colQuantite;
    @javafx.fxml.FXML
    private TextField nomProduitQ;
    @javafx.fxml.FXML
    private TextField nombre;
    @javafx.fxml.FXML
    private Button btnAjouter;
    @javafx.fxml.FXML
    private Button btnRetirer;

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

    public ObservableList<Produits> getProduitsList() {
        ObservableList<Produits> produitsList = FXCollections.observableArrayList();
        Connection conn = getConnexion();
        String query = "SELECT * from produit";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Produits produits;
            while (rs.next()) {
                produits = new Produits(rs.getString("nom"), rs.getInt("prix"),rs.getString("quantite"));
                produitsList.add(produits);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return produitsList;
    }

    public void showProduits() {
        ObservableList<Produits> list = getProduitsList();
        colProduit.setCellValueFactory(new PropertyValueFactory<Produits,String>("nom"));
        colPrix.setCellValueFactory(new PropertyValueFactory<Produits,Integer>("prix"));
        colQuantite.setCellValueFactory(new PropertyValueFactory<Produits,String>("quantite"));

        tvProduit.setItems(list);

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

    private void insertProduit() {
        String query = "INSERT INTO produit VALUES ('" + nomProduit.getText() + "','" + prixProduit.getText() + "','" + "0" + "')";
        executeQuery(query);
        showProduits();
    }

    private void deleteProduit() {
        String query = "DELETE FROM produit WHERE nom=" + nomProduit.getText() ;
        executeQuery(query);
        showProduits();
    }

    @FXML
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
    public void tableauPage(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("statistiques.fxml"));
        Scene newScene = new Scene(root);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(newScene);
        primaryStage.show();
    }

    @javafx.fxml.FXML
    public void gestionProduitsPage(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void enregistrerProduit(ActionEvent actionEvent) {
        if(actionEvent.getSource()== btnenregistrer) {
            insertProduit();
        }
    }

    @javafx.fxml.FXML
    public void suprimerProduit(ActionEvent actionEvent) {
        if(actionEvent.getSource()== btnSuprimer) {
            deleteProduit();
        }
    }

    private void updateQuantite() {
        String nom = nomProduitQ.getText();
        String quantite = nombre.getText();
        String query = "UPDATE produit SET quantite='" + quantite + "' WHERE nom='" + nom + "'";
        executeQuery(query);
        showProduits();
    }

    private void retirerQuantite() {
        String query = "UPDATE produit SET quantite=0 WHERE nom='" + nomProduitQ.getText() + "'";
        executeQuery(query);
        showProduits();
    }


    @javafx.fxml.FXML
    public void ajouterProduit(ActionEvent actionEvent) {
        if(actionEvent.getSource()== btnAjouter) {
            updateQuantite();
        }
    }

    @javafx.fxml.FXML
    public void retirerProduit(ActionEvent actionEvent) {
        if(actionEvent.getSource()== btnRetirer) {
            retirerQuantite();
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        showProduits();
    }
}
