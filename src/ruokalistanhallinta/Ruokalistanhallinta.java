
package ruokalistanhallinta;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import ruokalistanhallinta.domain.Logiikka;
import ruokalistanhallinta.domain.RuoatNakyma;
import ruokalistanhallinta.domain.ViikotNakyma;

public class Ruokalistanhallinta extends Application {

    Logiikka logiikka;
    RuoatNakyma ruoatNakyma;
    ViikotNakyma viikotNakyma;
    
    @Override
    public void init() {
        logiikka = new Logiikka();
        logiikka.lataa();
        ruoatNakyma = new RuoatNakyma(logiikka);
        viikotNakyma = new ViikotNakyma(logiikka);
    }
    
    @Override
    public void start(Stage stage) {

        Button valikonRuoatNappi = new Button("Ruoat");
        Button valikonViikotNappi = new Button("Viikot");
        
        HBox valikko = new HBox();
        valikko.setSpacing(5);
        valikko.setPadding(new Insets(5, 5, 5, 5));
        
        valikko.getChildren().addAll(valikonRuoatNappi, valikonViikotNappi);
        
        BorderPane asettelu = new BorderPane();
        asettelu.setTop(valikko);
        
        valikonRuoatNappi.setOnAction((event) -> asettelu.setCenter(ruoatNakyma.getNakyma()));
        valikonViikotNappi.setOnAction((event) -> asettelu.setCenter(viikotNakyma.getNakyma()));

        Scene nakyma = new Scene(asettelu);
        
        stage.setTitle("Ruokalistanhallinta");
        stage.setHeight(500);
        stage.setWidth(600);
        stage.setScene(nakyma);
        stage.show();
        
        
        
    }
    
    public static void main(String[] args) {
        
        Logiikka logiikka = new Logiikka();
        /*logiikka.lisaaResepti(new Resepti("Kanakastike", RaakaAine.KANA, Tyyppi.RIISI));
        logiikka.lisaaResepti(new Resepti("Tortilla", RaakaAine.LEHMÄ, Tyyppi.MUU));
        logiikka.lisaaResepti(new Resepti("Jauhelihapihvit", RaakaAine.LEHMÄ, Tyyppi.PERUNA));
        logiikka.lisaaResepti(new Resepti("PossuWok", RaakaAine.PORSAS, Tyyppi.PASTA));
        logiikka.lisaaResepti(new Resepti("Siskonmakkarakeitto", RaakaAine.PORSAS, Tyyppi.KEITTO));
        logiikka.lisaaResepti(new Resepti("Linssikeitto", RaakaAine.KASVIS, Tyyppi.KEITTO));
        logiikka.lisaaResepti(new Resepti("Linssipihvit", RaakaAine.KASVIS, Tyyppi.PERUNA));
        
        logiikka.lisaaResepti(new Resepti("Meetvurstikiusaus", RaakaAine.PORSAS, Tyyppi.KIUSAUS));
        logiikka.lisaaResepti(new Resepti("Jauhelihakeitto", RaakaAine.LEHMÄ, Tyyppi.KEITTO));
        logiikka.lisaaResepti(new Resepti("Hampurilaiset", RaakaAine.LEHMÄ, Tyyppi.MUU));
        logiikka.lisaaResepti(new Resepti("Kasvishampurilaiset", RaakaAine.KASVIS, Tyyppi.MUU));
        logiikka.lisaaResepti(new Resepti("Kanasiipiä", RaakaAine.KANA, Tyyppi.PERUNA));
        logiikka.lisaaResepti(new Resepti("Uunilohi", RaakaAine.KALA, Tyyppi.PERUNA));
        logiikka.lisaaResepti(new Resepti("Kalakeitto", RaakaAine.KALA, Tyyppi.KEITTO));
        
        logiikka.lisaaResepti(new Resepti("Kalapyörykät riisillä", RaakaAine.KALA, Tyyppi.RIISI));
        logiikka.lisaaResepti(new Resepti("Kalapyörykät perunalla", RaakaAine.KALA, Tyyppi.PERUNA));
        logiikka.lisaaResepti(new Resepti("Kalapyörykät pastalla", RaakaAine.KALA, Tyyppi.PASTA));
        logiikka.lisaaResepti(new Resepti("Pasta Carbonara", RaakaAine.LEHMÄ, Tyyppi.PASTA));
        logiikka.lisaaResepti(new Resepti("Kanaa grillissä", RaakaAine.KANA, Tyyppi.MUU));
        logiikka.lisaaResepti(new Resepti("Kanakiusaus", RaakaAine.KANA, Tyyppi.KIUSAUS));
        logiikka.lisaaResepti(new Resepti("Kasviskiusaus", RaakaAine.KASVIS, Tyyppi.KIUSAUS));
        
        logiikka.lisaaResepti(new Resepti("Kanatortilla", RaakaAine.KANA, Tyyppi.MUU));
        logiikka.lisaaResepti(new Resepti("Kanakeitto", RaakaAine.KANA, Tyyppi.KEITTO));
        logiikka.lisaaResepti(new Resepti("Risotto", RaakaAine.LEHMÄ, Tyyppi.RIISI));
        
        logiikka.lisaaViikko();
        logiikka.lisaaViikko();
        logiikka.lisaaViikko();
        logiikka.lisaaViikko();
        logiikka.lisaaViikko();
        logiikka.lisaaViikko();*/
        
        
        launch(Ruokalistanhallinta.class);
    }
    
}
