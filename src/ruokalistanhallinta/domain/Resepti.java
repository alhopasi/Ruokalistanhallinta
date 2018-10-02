
package ruokalistanhallinta.domain;

import java.util.Objects;

public class Resepti {
    
    private final String nimi;
    private final RaakaAine raakaAine;
    private final Tyyppi tyyppi;
    
    public Resepti(String nimi, RaakaAine raakaAine, Tyyppi tyyppi) {
        this.nimi = nimi;
        this.raakaAine = raakaAine;
        this.tyyppi = tyyppi;
    }
    
    public String getNimi() {
        return nimi;
    }
    
    public RaakaAine getRaakaAine() {
        return raakaAine;
    }

    public Tyyppi getTyyppi() {
        return tyyppi;
    }
    
    @Override
    public String toString() {
        return nimi;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.nimi);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Resepti other = (Resepti) obj;
        if (!Objects.equals(this.nimi, other.nimi)) {
            return false;
        }
        return true;
    }
    
    
}
