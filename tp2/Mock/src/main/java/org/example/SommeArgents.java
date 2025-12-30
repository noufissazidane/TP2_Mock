package org.example;

public class SommeArgents {
    private int quantite;
    private String unite;

    public SommeArgents(int quantite, String unite) {
        this.quantite = quantite;
        this.unite = unite;
    }

    public int getQuantite() { return quantite; }
    public String getUnite() { return unite; }

    public SommeArgents add(SommeArgents m) throws Exception {
        if (!m.getUnite().equals(this.getUnite())) {
            throw new Exception("Unités différentes !");
        }
        return new SommeArgents(getQuantite() + m.getQuantite(), getUnite());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SommeArgents)) return false;
        SommeArgents s = (SommeArgents) obj;
        return s.getQuantite() == quantite && s.getUnite().equals(unite);
    }
}