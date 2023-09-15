package prevoznoSredstvo;

import adresa.Adresa;
import interfejsi.Zakupljivo;


public abstract class PrevoznoSredstvo implements Zakupljivo {
    protected Integer id;
    protected Boolean obrok;
    protected Adresa polaznaAdresa;
    protected Adresa odredisnaAdresa;

    public PrevoznoSredstvo() {
    }

    public PrevoznoSredstvo(Integer id, Boolean obrok, Adresa polaznaAdresa, Adresa odredisnaAdresa) {
        this.id = id;
        this.obrok = obrok;
        this.polaznaAdresa = polaznaAdresa;
        this.odredisnaAdresa = odredisnaAdresa;
    }

    public boolean isObrok() {
        return obrok;
    }

    public void setObrok(boolean obrok) {
        this.obrok = obrok;
    }

    public Adresa getPolaznaAdresa() {
        return polaznaAdresa;
    }

    public void setPolaznaAdresa(Adresa polaznaAdresa) {
        this.polaznaAdresa = polaznaAdresa;
    }

    public Adresa getOdredisnaAdresa() {
        return odredisnaAdresa;
    }

    public void setOdredisnaAdresa(Adresa odredisnaAdresa) {
        this.odredisnaAdresa = odredisnaAdresa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
