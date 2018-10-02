package ruokalistanhallinta.domain;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ViikotNakyma {

    Logiikka logiikka;

    public ViikotNakyma(Logiikka logiikka) {
        this.logiikka = logiikka;
    }

    public Parent getNakyma() {
        GridPane asettelu = new GridPane();
        asettelu.setVgap(5);
        asettelu.setHgap(5);
        asettelu.setPadding(new Insets(5, 5, 5, 5));

        TextArea tekstikentta = new TextArea(logiikka.toString());
        tekstikentta.setPrefSize(250, 450);
        tekstikentta.setEditable(false);

        HBox lisaaValikko = new HBox(5);
        Button lisaaNappi = new Button("Lisää viikko");
        Label lisaaInfoTeksti = new Label("");
        lisaaValikko.getChildren().addAll(lisaaNappi, lisaaInfoTeksti);

        Button poistaEkaNappi = new Button("Poista ensimmäinen viikko");
        Button poistaVikaNappi = new Button("Poista viimeinen viikko");

        VBox oikeaValikko = new VBox(5);
        oikeaValikko.getChildren().addAll(lisaaValikko, poistaEkaNappi, poistaVikaNappi);

        asettelu.add(tekstikentta, 0, 0);
        asettelu.add(oikeaValikko, 1, 0);
        
        lisaaNappi.setOnAction((event) -> {
            if (logiikka.getReseptit().size() >= 10) {
                logiikka.lisaaViikko();
                lisaaInfoTeksti.setText("Viikko lisätty");
                tekstikentta.setText(logiikka.toString());
                logiikka.tallenna();
            } else {
                lisaaInfoTeksti.setText("Ei tarpeeksi erilaisia ruokia.");
            }
        });
        
        poistaEkaNappi.setOnAction((event) -> {
            logiikka.poistaEnsimmainenViikko();
            tekstikentta.setText(logiikka.toString());
            lisaaInfoTeksti.setText("");
            logiikka.tallenna();
        });
        
        poistaVikaNappi.setOnAction((event) -> {
           logiikka.poistaViimeinenViikko();
           tekstikentta.setText(logiikka.toString());
           lisaaInfoTeksti.setText("");
           logiikka.tallenna();
        });

        return asettelu;
    }
}
