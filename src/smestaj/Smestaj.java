package smestaj;

import adresa.Adresa;
import interfejsi.Zakupljivo;


public abstract class Smestaj implements Zakupljivo {
    protected Double povrsina;
    protected Integer brojKreveta;
    protected TipPansiona tipPansiona;
    protected Adresa adresa;

    public Smestaj() {
    }

    public Smestaj(Double povrsina, Integer brojKreveta, TipPansiona tipPansiona, Adresa adresa) {
        this.povrsina = povrsina;
        this.brojKreveta = brojKreveta;
        this.tipPansiona = tipPansiona;
        this.adresa = adresa;
    }

    public double getPovrsina() {
        return povrsina;
    }

    public void setPovrsina(double povrsina) {
        this.povrsina = povrsina;
    }

    public int getBrojKreveta() {
        return brojKreveta;
    }

    public void setBrojKreveta(int brojKreveta) {
        this.brojKreveta = brojKreveta;
    }

    public TipPansiona getTipPansiona() {
        return tipPansiona;
    }

    public void setTipPansiona(TipPansiona tipPansiona) {
        this.tipPansiona = tipPansiona;
    }

    public Adresa getAdresa() {
        return adresa;
    }

    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
    }
}
