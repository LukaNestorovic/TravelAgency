package turistickaAgencija;

import korisnik.Agent;
import korisnik.Klijent;

import java.util.ArrayList;

public class TuristickaAgencija {
    private String naziv;
    private ArrayList<Agent> agenti;
    private ArrayList<Klijent> klijenti;

    public TuristickaAgencija() {
    }

    public TuristickaAgencija(String naziv, ArrayList<Agent> agenti, ArrayList<Klijent> klijenti) {
        this.naziv = naziv;
        this.agenti = agenti;
        this.klijenti = klijenti;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public ArrayList<Agent> getAgenti() {
        return agenti;
    }

    public void setAgenti(ArrayList<Agent> agenti) {
        this.agenti = agenti;
    }

    public ArrayList<Klijent> getKlijenti() {
        return klijenti;
    }

    public void setKlijenti(ArrayList<Klijent> klijenti) {
        this.klijenti = klijenti;
    }
}
