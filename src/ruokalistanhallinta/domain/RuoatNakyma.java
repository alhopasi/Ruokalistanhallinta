package ruokalistanhallinta.domain;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RuoatNakyma {

    Logiikka logiikka;
    String listanJarjestys;

    public RuoatNakyma(Logiikka logiikka) {
        this.logiikka = logiikka;
    }

    public Parent getNakyma() {
        listanJarjestys = "nimi";

        GridPane asettelu = new GridPane();
        asettelu.setVgap(5);
        asettelu.setHgap(5);
        asettelu.setPadding(new Insets(5, 5, 5, 5));

        Button nimiNappi = new Button("Nimi");
        Button raakaAineNappi = new Button("Raaka-aine");
        Button tyyppiNappi = new Button("Tyyppi");

        HBox ylaValikko = new HBox(5);
        ylaValikko.getChildren().addAll(nimiNappi, raakaAineNappi, tyyppiNappi);
        ylaValikko.setSpacing(5);

        TextArea tekstikentta = new TextArea(logiikka.getReseptitNimenMukaan());
        tekstikentta.setPrefSize(350, 450);
        tekstikentta.setEditable(false);

        Label ruoanLisaysOhje = new Label("Lisää ruoka:");

        TextField lisaaTekstikentta = new TextField();

        Label raakaAineOhje = new Label("Raaka-aine:");

        RadioButton kalaNappi = new RadioButton("Kala");
        RadioButton kanaNappi = new RadioButton("Kana");
        RadioButton kasvisNappi = new RadioButton("Kasvis");
        RadioButton lehmaNappi = new RadioButton("Lehmä");
        RadioButton porsasNappi = new RadioButton("Porsas");

        ToggleGroup raakaAineetRyhma = new ToggleGroup();
        kalaNappi.setToggleGroup(raakaAineetRyhma);
        kanaNappi.setToggleGroup(raakaAineetRyhma);
        kasvisNappi.setToggleGroup(raakaAineetRyhma);
        lehmaNappi.setToggleGroup(raakaAineetRyhma);
        porsasNappi.setToggleGroup(raakaAineetRyhma);

        GridPane raakaAineidenValikko = new GridPane();
        raakaAineidenValikko.setHgap(5);
        raakaAineidenValikko.setVgap(5);

        raakaAineidenValikko.add(kalaNappi, 0, 0);
        raakaAineidenValikko.add(kanaNappi, 1, 0);
        raakaAineidenValikko.add(kasvisNappi, 2, 0);
        raakaAineidenValikko.add(lehmaNappi, 0, 1);
        raakaAineidenValikko.add(porsasNappi, 1, 1);

        Label tyyppiOhje = new Label("Tyyppi");

        RadioButton keittoNappi = new RadioButton("Keitto");
        RadioButton kiusausNappi = new RadioButton("Kiusaus");
        RadioButton pastaNappi = new RadioButton("Pasta");
        RadioButton perunaNappi = new RadioButton("Peruna");
        RadioButton riisiNappi = new RadioButton("Riisi");
        RadioButton muuNappi = new RadioButton("Muu");

        ToggleGroup tyyppiRyhma = new ToggleGroup();
        keittoNappi.setToggleGroup(tyyppiRyhma);
        kiusausNappi.setToggleGroup(tyyppiRyhma);
        pastaNappi.setToggleGroup(tyyppiRyhma);
        perunaNappi.setToggleGroup(tyyppiRyhma);
        riisiNappi.setToggleGroup(tyyppiRyhma);
        muuNappi.setToggleGroup(tyyppiRyhma);

        GridPane tyyppiValikko = new GridPane();
        tyyppiValikko.setHgap(5);
        tyyppiValikko.setVgap(5);

        tyyppiValikko.add(keittoNappi, 0, 0);
        tyyppiValikko.add(kiusausNappi, 1, 0);
        tyyppiValikko.add(pastaNappi, 2, 0);
        tyyppiValikko.add(perunaNappi, 0, 1);
        tyyppiValikko.add(riisiNappi, 1, 1);
        tyyppiValikko.add(muuNappi, 2, 1);

        HBox lisaaValikko = new HBox(5);
        Button lisaaNappi = new Button("Lisää");
        Label lisaaInfoTeksti = new Label("");
        lisaaValikko.getChildren().addAll(lisaaNappi, lisaaInfoTeksti);

        Label tyhjaRivi = new Label("");

        Label poistaTeksti = new Label("Poista ruoka:");

        TextField poistaTekstikentta = new TextField();

        HBox poistaNapinValikko = new HBox(5);
        Button poistaNappi = new Button("Poista");
        Label poistaInfoTeksti = new Label("");
        poistaNapinValikko.getChildren().addAll(poistaNappi, poistaInfoTeksti);

        VBox oikeaValikko = new VBox(5);
        oikeaValikko.getChildren().addAll(ruoanLisaysOhje, lisaaTekstikentta, raakaAineOhje, raakaAineidenValikko,
                tyyppiOhje, tyyppiValikko, lisaaValikko, tyhjaRivi, poistaTeksti, poistaTekstikentta, poistaNapinValikko);

        asettelu.add(ylaValikko, 0, 0);
        asettelu.add(tekstikentta, 0, 1);
        asettelu.add(oikeaValikko, 1, 1);

        nimiNappi.setOnAction((event) -> {
            tekstikentta.setText(logiikka.getReseptitNimenMukaan());
            listanJarjestys = "nimi";
        });
        raakaAineNappi.setOnAction((event) -> {
            tekstikentta.setText(logiikka.getReseptitRaakaAineenMukaan());
            listanJarjestys = "raakaAine";
        });
        tyyppiNappi.setOnAction((event) -> {
            tekstikentta.setText(logiikka.getReseptitTyypinMukaan());
            listanJarjestys = "tyyppi";
        });

        lisaaNappi.setOnAction((event) -> {
            String nimi = lisaaTekstikentta.getText();
            if (nimi == null) {
                lisaaInfoTeksti.setText("Ruoan nimi on liian lyhyt");
                return;
            }
            
            if (nimi.length() < 4) {
                lisaaInfoTeksti.setText("Ruoan nimi on liian lyhyt");
                return;
            }
            if (nimi.length() >= 25) {
                lisaaInfoTeksti.setText("Ruoan nimi on liian pitkä");
                return;
            }
            if (raakaAineetRyhma.getSelectedToggle() == null) {
                lisaaInfoTeksti.setText("Valitse raaka-aine");
                return;
            }
            if (tyyppiRyhma.getSelectedToggle() == null) {
                lisaaInfoTeksti.setText("Valitse tyyppi");
                return;
            }
            
            RadioButton valittuRaakaAine = (RadioButton) raakaAineetRyhma.getSelectedToggle();
            RadioButton valittuTyyppi = (RadioButton) tyyppiRyhma.getSelectedToggle();

            nimi = nimi.substring(0, 1).toUpperCase() + nimi.substring(1).toLowerCase();
            logiikka.lisaaResepti(new Resepti(nimi, RaakaAine.valueOf(valittuRaakaAine.getText().toUpperCase()), Tyyppi.valueOf(valittuTyyppi.getText().toUpperCase())));

            lisaaInfoTeksti.setText("Ruoka lisätty");
            poistaInfoTeksti.setText("");

            if (listanJarjestys.equals("nimi")) {
                tekstikentta.setText(logiikka.getReseptitNimenMukaan());
            } else if (listanJarjestys.equals("raakaAine")) {
                tekstikentta.setText(logiikka.getReseptitRaakaAineenMukaan());
            } else if (listanJarjestys.equals("tyyppi")) {
                tekstikentta.setText(logiikka.getReseptitTyypinMukaan());
            }
            
            logiikka.tallenna();
        });

        poistaNappi.setOnAction((event) -> {
            String nimi = poistaTekstikentta.getText();
            if (!logiikka.poistaResepti(nimi)) {
                poistaInfoTeksti.setText("Ruokaa ei löydy");
            } else {
                poistaInfoTeksti.setText("Ruoka poistettu");
                lisaaInfoTeksti.setText("");
                poistaTekstikentta.setText("");
                if (listanJarjestys.equals("nimi")) {
                    tekstikentta.setText(logiikka.getReseptitNimenMukaan());
                } else if (listanJarjestys.equals("raakaAine")) {
                    tekstikentta.setText(logiikka.getReseptitRaakaAineenMukaan());
                } else if (listanJarjestys.equals("tyyppi")) {
                    tekstikentta.setText(logiikka.getReseptitTyypinMukaan());
                }
            }
            
            logiikka.tallenna();
        });

        return asettelu;
    }

}
