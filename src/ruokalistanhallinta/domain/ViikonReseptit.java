
package ruokalistanhallinta.domain;

import java.util.ArrayList;
import java.util.List;

public class ViikonReseptit {
    
    private List<Resepti> reseptit;
    private final int viikonNumero;
    
    public ViikonReseptit(int viikonNumero) {
        this.viikonNumero = viikonNumero;
        reseptit = new ArrayList<>();
    }
    
    public void lisaaResepti(Resepti resepti) {
        if (reseptit.size() == 7) {
            return;
        }
        reseptit.add(resepti);
    }
    
    public List<Resepti> getReseptit() {
        return reseptit;
    }
    
    public int getViikonNumero() {
        return viikonNumero;
    }
    
    public boolean sisaltaa(Resepti resepti) {
        return reseptit.stream().anyMatch(r -> r.equals(resepti));
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Viikko ").append(viikonNumero).append(":\n");
        for (Resepti r : reseptit) {
            sb.append(r.toString()).append("\n");
        }
        return sb.toString();
    }
    
}
