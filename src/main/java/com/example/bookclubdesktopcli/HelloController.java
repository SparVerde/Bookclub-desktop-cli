package com.example.bookclubdesktopcli;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.lang.reflect.Member;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

//Update controller from FXML on hello-view.fxml right click


public class HelloController {
    // db adattag kreálása:
    MemberDb db;

    @FXML
    private Label welcomeText;
    @FXML
    private TableView <Member>Table;
    @FXML
    private TableColumn <Member, Boolean>bannedOszlop;
    @FXML
    private TableColumn <Member, Integer>idOszlop;
    @FXML
    private TableColumn <Member, String>nameoszlop;
    @FXML
    private TableColumn <Member, String>genderOszlop;
    @FXML
    private TableColumn <Member, Date>birth_dateOszlop;

    @Deprecated
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void initialize(){
        idOszlop.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameoszlop.setCellValueFactory(new PropertyValueFactory<>("name"));
        genderOszlop.setCellValueFactory(new PropertyValueFactory<>("gender"));
        birth_dateOszlop.setCellValueFactory(new PropertyValueFactory<>("birth_date"));
        bannedOszlop.setCellValueFactory(new PropertyValueFactory<>("banned"));

        //beolvasás adatbázisba: új példány adatbázis létrehozásra + adattagot is kreálok: MemberDb db;
        //adatbázisból lista létrehozása, metódusa: tablazatFeltolt() + try, catch

        try {
           db = new MemberDb();


        }catch (Exception e){System.out.println(e);}
    }
    //tablazatFeltolt() metódus megírása: ArrayList létrehozása és feltöltése, előtte azonban kitöröljük a korábbi tartalmat
    public void tablazatFeltolt(){

        try {
            ArrayList<com.example.bookclubdesktopcli.Member> lista = new ArrayList<>();
            Table.getItems().clear();//táblázat azonosítóval tablazatId azonosított táblázat törlése
            for (com.example.bookclubdesktopcli.Member elem:lista
            ) {
                Table.getItems().add((Member) elem); //táblázat azonosítóval tablazatId azonosított táblázat feltöltése
            }

        }catch (Exception e){System.out.println(e);}
    }

    @FXML
    public void onUpdate(ActionEvent actionEvent) {
        int selectedIndex = Table.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1){
            System.out.println("A tiltáshoz előbb válasszon ki egy elemet a táblázatból");
            uzenetKiir("A tiltáshoz előbb válasszon ki egy elemet a táblázatból");
            return;
        }
        Member tiltas = Table.getSelectionModel().getSelectedItem();
        if (!megerositesKiir("Biztos hogy letiltja a tagot? "+tiltas.getName())){
            return;
        }
        try {
            db.bannedModosit(tiltas.getName());
            System.out.println("Sikeres tiltás");
            uzenetKiir("Sikeres tiltás");
            tablazatFeltolt();
        } catch (SQLException e) {
            hibaKiir(e, "Hiba a tiltás közben");
        }
    }

    private void hibaKiir(Exception e, String hibauzenet) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText(hibauzenet);
        //alert.show();
        alert.setContentText(e.getClass().toString());
        Timer alertTimer = new Timer();
        alertTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> alert.show());
            }
        }, 500);
    }

    private void uzenetKiir(String uzenet) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setContentText(uzenet);
        alert.getButtonTypes().add(ButtonType.OK);
        alert.show();
    }

    private boolean megerositesKiir(String uzenet){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Biztos?");
        alert.setHeaderText(uzenet);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }
}