package ruokalistanhallinta.domain;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Logiikka {

    private List<ViikonReseptit> viikot;
    private Map<String, Resepti> reseptit;

    public Logiikka() {
        viikot = new ArrayList<>();
        reseptit = new HashMap<>();
    }

    public void lisaaResepti(Resepti resepti) {
        reseptit.put(resepti.getNimi().toLowerCase(), resepti);
    }

    public boolean poistaResepti(String resepti) {
        if (reseptit.containsKey(resepti.toLowerCase())) {
            reseptit.remove(resepti.toLowerCase());
            return true;
        }
        return false;
    }

    public void lisaaViikko() {
        if (viikot.size() == 6) {
            poistaEnsimmainenViikko();
        }

        try {
            viikot.add(arvoReseptit());
        } catch (Exception e) {
            System.out.println("Ei tarpeeksi erilaisia reseptejä seuraavan viikon arpomiseen.");
        }
    }
    
    public Set<String> getReseptit() {
        return reseptit.keySet();
    }

    public void poistaEnsimmainenViikko() {
        if (viikot.isEmpty()) return;
        viikot.remove(0);
    }

    public void poistaViimeinenViikko() {
        if (viikot.isEmpty()) return;
        viikot.remove(viikot.size() - 1);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ViikonReseptit viikonReseptit : viikot) {
            sb.append(viikonReseptit.toString()).append("\n");
        }
        return sb.toString();
    }

    private ViikonReseptit arvoReseptit() throws Exception {
        ViikonReseptit uusi;
        if (viikot.isEmpty()) {
            uusi = new ViikonReseptit(1);
        } else {
            uusi = new ViikonReseptit(viikot.get(viikot.size() - 1).getViikonNumero() + 1);
        }
        while (uusi.getReseptit().size() < 7) {

            Resepti resepti = haeSatunnainenReseptiRaakaAineenMaaranMukaan(uusi, 0);
            if (resepti == null) {
                resepti = haeSatunnainenReseptiRaakaAineenMaaranMukaan(uusi, 1);
            }
            if (resepti == null) {
                resepti = haeSatunnainenReseptiRaakaAineenMaaranMukaan(uusi, 2);
            }
            uusi.lisaaResepti(resepti);

        }
        return uusi;
    }

    private Resepti haeSatunnainenReseptiRaakaAineenMaaranMukaan(ViikonReseptit viikonReseptit, int maara) throws Exception {
        List<Resepti> resepteja = reseptit.values().stream()
                .filter(r -> montakoReseptiaListallaSisaltaaRaakaAinetta(viikonReseptit, r.getRaakaAine()) == maara)
                .filter(r -> montakoReseptiaListallaSisaltaaTyyppia(viikonReseptit, r.getTyyppi()) <= 2)
                .filter(r -> !onkoEdellinenRuokaSamaaRaakaAinettaTaiTyyppia(viikonReseptit, r.getRaakaAine(), r.getTyyppi()))
                .filter(r -> !onkoReseptiKahdellaEdellisellaViikolla(r))
                .filter(r -> !viikonReseptit.sisaltaa(r))
                .collect(Collectors.toCollection(ArrayList::new));

        if (!resepteja.isEmpty()) {
            Collections.shuffle(resepteja);
            return resepteja.get(0);

        } else {
            return null;
        }
    }

    private int montakoReseptiaListallaSisaltaaTyyppia(ViikonReseptit viikonResepti, Tyyppi tyyppi) {
        return (int) viikonResepti.getReseptit().stream().filter(r -> r.getTyyppi() == tyyppi).count();
    }

    private int montakoReseptiaListallaSisaltaaRaakaAinetta(ViikonReseptit viikonResepti, RaakaAine raakaAine) {
        return (int) viikonResepti.getReseptit().stream().filter(r -> r.getRaakaAine() == raakaAine).count();
    }

    private boolean onkoReseptiKahdellaEdellisellaViikolla(Resepti resepti) {
        if (viikot.size() < 1) {
            return false;
        }
        if (viikot.size() >= 1) {
            boolean sisaltaa = viikot.get(viikot.size() - 1).sisaltaa(resepti);
            if (sisaltaa) {
                return true;
            }
        }
        if (viikot.size() >= 2) {
            return viikot.get(viikot.size() - 2).sisaltaa(resepti);
        }
        return false;
    }

    private boolean onkoEdellinenRuokaSamaaRaakaAinettaTaiTyyppia(ViikonReseptit viikonResepti, RaakaAine raakaAine, Tyyppi tyyppi) {
        if (viikonResepti.getReseptit().isEmpty() && viikot.isEmpty()) {
            return false;
        }
        
        if (viikonResepti.getReseptit().isEmpty()) {
            List<Resepti> edelliset = viikot.get(viikot.size() - 1).getReseptit();
            Resepti edellinen = edelliset.get(edelliset.size() - 1);
            if (edellinen.getRaakaAine() == raakaAine || edellinen.getTyyppi() == tyyppi) {
                return true;
            }
            return false;
        }
        
        return viikonResepti.getReseptit().get(viikonResepti.getReseptit().size() - 1).getTyyppi() == tyyppi || viikonResepti.getReseptit().get(viikonResepti.getReseptit().size() - 1).getRaakaAine() == raakaAine;
    }

    public void tallenna() {
        try {
            tallennaRuokalistat();
            tallennaReseptit();
        } catch (Exception e) {
            System.out.println("Virhe tallennettaessa: " + e.getMessage());
        }

    }

    private void tallennaRuokalistat() throws Exception {
        try (PrintWriter kirjoittaja = new PrintWriter("ruokalista.txt")) {
            for (ViikonReseptit viikonReseptit : viikot) {
                kirjoittaja.print(viikonReseptit.getViikonNumero());
                for (Resepti resepti : viikonReseptit.getReseptit()) {
                    kirjoittaja.print(":" + resepti.getNimi());
                }
                kirjoittaja.println();
            }
            kirjoittaja.close();
        }
    }

    private void tallennaReseptit() throws Exception {
        try (PrintWriter kirjoittaja = new PrintWriter("reseptit.txt")) {
            for (Resepti resepti : reseptit.values()) {
                kirjoittaja.println(resepti.getNimi() + ":" + resepti.getRaakaAine() + ":" + resepti.getTyyppi());
            }
            kirjoittaja.close();
        }
    }

    public void lataa() {
        try {
            lataaReseptit();
            lataaRuokalistat();
        } catch (Exception e) {
            System.out.println("Virhe ladattaessa tiedostoa: " + e.getMessage());
        }
    }

    private void lataaRuokalistat() throws Exception {
        try (Scanner tiedostonLukija = new Scanner(new File("ruokalista.txt"))) {
            while (tiedostonLukija.hasNextLine()) {
                String[] palat = tiedostonLukija.nextLine().split(":");
                ViikonReseptit uusi = new ViikonReseptit(Integer.parseInt(palat[0]));
                for (int i = 1; i <= 7; i++) {
                    uusi.lisaaResepti(haeResepti(palat[i]));
                }
                viikot.add(uusi);
            }
            tiedostonLukija.close();
        }
    }
    
    private Resepti haeResepti(String nimi) {
        Resepti palautettava = reseptit.get(nimi.toLowerCase());
        if (palautettava == null) {
            return new Resepti(nimi, RaakaAine.KALA, Tyyppi.MUU);
        }
        return palautettava;
//        return reseptit.get(nimi.toLowerCase());
    }

    private void lataaReseptit() throws Exception {
        try (Scanner tiedostonLukija = new Scanner(new File("reseptit.txt"))) {
            while (tiedostonLukija.hasNextLine()) {
                String[] palat = tiedostonLukija.nextLine().split(":");
                lisaaResepti(new Resepti(palat[0], RaakaAine.valueOf(palat[1].toUpperCase()), Tyyppi.valueOf(palat[2].toUpperCase())));
            }
            tiedostonLukija.close();
        }
    }

    public String getReseptitNimenMukaan() {
        if (reseptit.isEmpty()) return "";
        
        StringBuilder sb = new StringBuilder();
        reseptit.keySet().stream().sorted().forEach(reseptinNimi -> {
            Resepti resepti = haeResepti(reseptinNimi);
            sb.append(resepti.getNimi())
                    .append(" : ")
                    .append(resepti.getRaakaAine())
                    .append(" : ")
                    .append(resepti.getTyyppi())
                    .append("\n");
        });
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

    public String getReseptitRaakaAineenMukaan() {
        if (reseptit.isEmpty()) return "";
        
        StringBuilder sb = new StringBuilder();
        reseptit.keySet().stream().sorted((res1, res2) -> {
            int tulos = reseptit.get(res1).getRaakaAine().compareTo(reseptit.get(res2).getRaakaAine());
            if (tulos == 0) {
                return res1.compareTo(res2);
            } else {
                return tulos;
            }
        })
                .forEach(reseptinNimi -> {
                    Resepti resepti = haeResepti(reseptinNimi);
                    sb.append(resepti.getRaakaAine())
                            .append(" : ")
                            .append(resepti.getNimi())
                            .append(" : ")
                            .append(resepti.getTyyppi())
                            .append("\n");
                });
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

    public String getReseptitTyypinMukaan() {
        if (reseptit.isEmpty()) return "";
        
        StringBuilder sb = new StringBuilder();
        reseptit.keySet().stream().sorted((res1, res2) -> {
            int tulos = reseptit.get(res1).getTyyppi().compareTo(reseptit.get(res2).getTyyppi());
            if (tulos == 0) {
                return res1.compareTo(res2);
            } else {
                return tulos;
            }
        })
                .forEach(reseptinNimi -> {
                    Resepti resepti = haeResepti(reseptinNimi);
                    sb.append(resepti.getTyyppi())
                            .append(" : ")
                            .append(resepti.getNimi())
                            .append(" : ")
                            .append(resepti.getRaakaAine())
                            .append("\n");
                });
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }
}
