package com.example.essalaf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class CommandesController implements Initializable {

        @FXML
        private Button addbtn;

        @FXML
        private Button btnClient;

        @FXML
        private Button btnCommande;

        @FXML
        private Button btnProduit;

        @FXML
        private Button btnTableau;

        @FXML
        private TextField client;

        @FXML
        private TableColumn<Commandes, String> colClient;

        @FXML
        private TableColumn<Commandes, Integer> colCredit;

        @FXML
        private TableColumn<Commandes, String> colDate;

        @FXML
        private TableColumn<Commandes, String> colEtat;

        @FXML
        private TableColumn<Commandes, Integer> colMontant;

        @FXML
        private TableColumn<Commandes, Integer> colPaye;

        @FXML
        private TableColumn<Commandes, Integer> colnum;

        @FXML
        private TextField credit;

        @FXML
        private TextField date;

        @FXML
        private Button deleteBtn;

        @FXML
        private TextField etat;

        @FXML
        private TextField montant;

        @FXML
        private TextField numero;

        @FXML
        private TextField paye;

        @FXML
        private TableView<Commandes> tvCommande;

        @FXML
        private Button updateBtn;


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

    public ObservableList<Commandes> getCommandesList() {
        ObservableList<Commandes> commandesList = FXCollections.observableArrayList();
        Connection conn = getConnexion();
        String query = "SELECT * from commande";
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Commandes commandes;
            while (rs.next()) {
                commandes = new Commandes(rs.getInt("num"), rs.getString("client"),rs.getString("date"),rs.getInt("montant"),rs.getInt("paye"),rs.getInt("credit"),rs.getString("etat"));
                commandesList.add(commandes);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return commandesList;
    }


    public void showCommandes() {
        ObservableList<Commandes> list = getCommandesList();
        colnum.setCellValueFactory(new PropertyValueFactory<Commandes,Integer>("num"));
        colClient.setCellValueFactory(new PropertyValueFactory<Commandes,String>("client"));
        colDate.setCellValueFactory(new PropertyValueFactory<Commandes,String>("date"));
        colMontant.setCellValueFactory(new PropertyValueFactory<Commandes,Integer>("montant"));
        colPaye.setCellValueFactory(new PropertyValueFactory<Commandes,Integer>("paye"));
        colCredit.setCellValueFactory(new PropertyValueFactory<Commandes,Integer>("credit"));
        colEtat.setCellValueFactory(new PropertyValueFactory<Commandes,String>("etat"));


        tvCommande.setItems(list);

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

    private void insertCommande() {
        String query = "INSERT INTO commande VALUES ('" + numero.getText() + "','" + client.getText() + "','" + date.getText() + "','" + montant.getText() +"','" + paye.getText() +"','" + credit.getText() +"','" + etat.getText() + "')";
        executeQuery(query);
        showCommandes();
    }

    private void updateCommande() {
        String query = "UPDATE commande SET paye='" + paye.getText() + "', credit ='" + credit.getText() + "', etat ='" + etat.getText() + "' WHERE num='" + numero.getText() + "'";
        executeQuery(query);
        showCommandes();
    }

    private void deleteCommandes() {
        String query = "DELETE FROM commande WHERE num=" + numero.getText() ;
        executeQuery(query);
        showCommandes();
    }

        @FXML
        void AddCommande(ActionEvent event) {
            if(event.getSource()== addbtn) {
                insertCommande();
            }
        }

        @FXML
        void updatecommande(ActionEvent event) {
            if(event.getSource()== updateBtn) {
                updateCommande();
            }
        }

        @FXML
        void deleteCommande(ActionEvent event) {
            if(event.getSource()== deleteBtn) {
                deleteCommandes();
            }
        }

        @FXML
        void gestionClientsPage(ActionEvent event) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            Scene newScene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(newScene);
            primaryStage.show();
        }

        @FXML
        void gestionCommandesPage(ActionEvent event) throws IOException {

        }

        @FXML
        void gestionProduitsPage(ActionEvent event) throws IOException {

            Parent root = FXMLLoader.load(getClass().getResource("produits.fxml"));
            Scene newScene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(newScene);
            primaryStage.show();
        }

        @FXML
        void tableauPage(ActionEvent event) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("statistiques.fxml"));
            Scene newScene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(newScene);
            primaryStage.show();
        }



    public void initialize(URL location, ResourceBundle resources) {
        showCommandes();
    }

    }

