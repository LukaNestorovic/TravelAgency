package korisnik;

import adresa.Adresa;

import aranzman.Aranzman;

import aranzman.Paket;
import interfejsi.Kreiranje;
import interfejsi.Serijalizacija;
import main.Aplikacija;
import prevoznoSredstvo.*;
import smestaj.Apartman;
import smestaj.Hotel;
import smestaj.Smestaj;
import smestaj.TipPansiona;

import java.io.*;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Scanner;

public class Agent extends Korisnik implements Serijalizacija<Agent, String>, Kreiranje {
    public Agent() {
    }

    public Agent(String ime, String prezime, String korisnickoIme, String lozinka) {
        super(ime, prezime, korisnickoIme, lozinka);
    }

    @Override
    public Agent izCSV(String korisnickoIme) {
        String csvFile = "data/agenti.csv";
        String red;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((red = br.readLine()) != null){
                String[] podaci = red.split(",");
                if(podaci[2].equals(korisnickoIme)) {
                    Agent agent = new Agent();
                    agent.setIme(podaci[0]);
                    agent.setPrezime(podaci[1]);
                    agent.setKorisnickoIme(podaci[2]);
                    agent.setLozinka(podaci[3]);
                    return agent;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void uCSV(Agent agent) {
        ArrayList<String> podaci = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("data/agenti.csv"))) {
            String red;
            while ((red = br.readLine()) != null) {
                podaci.add(red);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuffer noviRed = new StringBuffer();
        noviRed.append(agent.getIme());
        noviRed.append(",");
        noviRed.append(agent.getPrezime());
        noviRed.append(",");
        noviRed.append(agent.getKorisnickoIme());
        noviRed.append(",");
        noviRed.append(agent.getLozinka());
        podaci.add(String.valueOf(noviRed));

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/agenti.csv"))) {
            for (String red : podaci) {
                bw.write(red);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void meni(Agent agent) {
        try (Scanner s = new Scanner(System.in)) {
            String izbor;

            System.out.println("0. Odjavi se");
            System.out.println("1. Kreiraj aranžman");
            System.out.println("2. Kreiraj paket");
            System.out.println("3. Obriši aranzman");
            System.out.println("4. Obrisi paket");

            while(true) {
                System.out.println("Unesite jednu od ponuđenih opcija:");
                izbor = s.nextLine();

                if(izbor.isEmpty()) {
                    System.out.println("Unos ne sme ostati prazan!");
                    continue;
                }

                if(!(izbor.equals("0")) && !(izbor.equals("1")) && !(izbor.equals("2")) && !(izbor.equals("3")) && !(izbor.equals("4"))){
                    System.out.println("Greška!");
                    continue;
                }

                switch (izbor) {
                    case "0" -> {
                        System.out.println("Hvala Vam na korišćenju aplikacije! Do viđenja!");
                        Aplikacija.main(null);
                        return;
                    }
                    case "1" -> {
                        kreirajAranzman(agent);
                        return;
                    }
                    case "2" -> {
                        kreirajPaket(agent);
                        return;
                    }
                    case "3" -> {
                        System.out.println("Izaberite aranžman koji želite da obrišete:");
                        new Aranzman().brisanje(Integer.parseInt(s.nextLine()));
                        meni(agent);
                        return;
                    }
                    case "4" -> {
                        brisanjePaketa(agent);
                        return;
                    }
                    default -> {
                        System.out.println("Pogrešan unos!");
                        return;
                    }
                }

            }
        }
    }

    public Smestaj kreirajSmestaj(Scanner s){
            while (true) {
                System.out.println("Izaberite tip smeštaja:");
                System.out.println("1. Apartman");
                System.out.println("2. Hotel");
                String izbor;
                izbor = s.nextLine();

                if (izbor.isEmpty()) {
                    System.out.println("Ovo polje ne sme ostati prazno!");
                    continue;
                }
                if (!(izbor.equals("0")) && !(izbor.equals("1")) && !(izbor.equals("2"))) {
                    System.out.println("Greška!");
                    continue;
                }

                switch (izbor){
                    case "1" -> {
                        Apartman apartman = new Apartman();
                        System.out.println("Unesite površinu:");
                        apartman.setPovrsina(Double.parseDouble(s.nextLine()));
                        System.out.println("Unesite broj kreveta:");
                        apartman.setBrojKreveta(Integer.parseInt(s.nextLine()));
                        System.out.println("Unesite tip pansiona");
                        apartman.setTipPansiona(TipPansiona.valueOf(s.nextLine()));
                        System.out.println("Unesite adresu:");
                        apartman.setAdresa(new Adresa().izCSV(Integer.parseInt(s.nextLine())));
                        new Apartman().uCSV(apartman);
                        return apartman;
                    }
                    case "2" -> {
                        Hotel hotel = new Hotel();
                        System.out.println("Unesite površinu:");
                        hotel.setPovrsina(Double.parseDouble(s.nextLine()));
                        System.out.println("Unesite broj kreveta:");
                        hotel.setBrojKreveta(Integer.parseInt(s.nextLine()));
                        System.out.println("Unesite tip pansiona");
                        hotel.setTipPansiona(TipPansiona.valueOf(s.nextLine()));
                        System.out.println("Unesite adresu:");
                        hotel.setAdresa(new Adresa().izCSV(Integer.parseInt(s.nextLine())));
                        System.out.println("Unesite broj zvezdica:");
                        hotel.setBrojZvezdica(Integer.parseInt(s.nextLine()));
                        new Hotel().uCSV(hotel);
                        return hotel;
                    }
                }
            }
    }

    public PrevoznoSredstvo kreirajPrevoz(Scanner s, Adresa odrediste){
            while (true) {
                System.out.println("Izaberite tip prevoza:");
                System.out.println("1. Avion");
                System.out.println("2. Voz");
                String izbor;
                izbor = s.nextLine();

                if (izbor.isEmpty()) {
                    System.out.println("Unos ne sme ostati prazan!");
                    continue;
                }
                if (!(izbor.equals("0")) && !(izbor.equals("1")) && !(izbor.equals("2"))) {
                    System.out.println("Greška!");
                    continue;
                }

                switch (izbor){
                    case "1" -> {
                        Avion avion = new Avion();
                        System.out.println("Unesite id:");
                        avion.setId(Integer.parseInt(s.nextLine()));
                        System.out.println("Da li prevoz uključuje obrok:");
                        avion.setObrok(Boolean.parseBoolean(s.nextLine()));
                        System.out.println("Unesite polaznu adresu:");
                        avion.setPolaznaAdresa(new Adresa().izCSV(Integer.parseInt(s.nextLine())));
                        avion.setOdredisnaAdresa(odrediste);
                        System.out.println("Unesite klasu mesta:");
                        avion.setKlasaAvionskogMesta(KlasaAvionskogMesta.valueOf(s.nextLine()));
                        new Avion().uCSV(avion);
                        return avion;
                    }
                    case "2" -> {
                        Voz voz = new Voz();
                        System.out.println("Unesite id:");
                        voz.setId(Integer.parseInt(s.nextLine()));
                        System.out.println("Da li prevoz uključuje obrok:");
                        voz.setObrok(Boolean.parseBoolean(s.nextLine()));
                        System.out.println("Unesite polaznu adresu:");
                        voz.setPolaznaAdresa(new Adresa().izCSV(Integer.parseInt(s.nextLine())));
                        voz.setOdredisnaAdresa(odrediste);
                        System.out.println("Unesite klasu mesta:");
                        voz.setKlasaVoznogMesta(KlasaVoznogMesta.valueOf(s.nextLine()));
                        new Voz().uCSV(voz);
                        return voz;
                    }
                }
            }
    }

    public void kreirajAranzman(Agent agent){
        try(Scanner s = new Scanner(System.in)) {
            Aranzman aranzman = new Aranzman();
            System.out.println("Kreiranje aranzmana:");
            System.out.println("Unesite id aranzmana:");
            aranzman.setId(Integer.parseInt(s.nextLine()));
            System.out.println("Da li je aranžman osiguran:");
            aranzman.setOsiguran(Boolean.parseBoolean(s.nextLine()));
            System.out.println("Da li je aranžman grupni:");
            aranzman.setGrupni(Boolean.parseBoolean(s.nextLine()));
            System.out.println("Unesite datum(YYYY-MM-DD) polaska:");
            aranzman.setDatumPolaska(LocalDate.parse(s.nextLine()));
            System.out.println("Unesite datum povratka:");
            aranzman.setDatumPovratka(LocalDate.parse(s.nextLine()));
            System.out.println("Da li želite da kreirate smeštaj?(1 za da, 2 za ne)");
            Integer izbor = Integer.parseInt(s.nextLine());
            if(izbor == 1){
                aranzman.setSmestaj(kreirajSmestaj(s));
            } else {
                System.out.println("Izaberite vrstu smestaja:");
                System.out.println("1. Apartman");
                System.out.println("2. Hotel");
                izbor = Integer.parseInt(s.nextLine());
                if(izbor == 1){
                    System.out.println("Izaberite apartman:");
                    aranzman.setSmestaj(new Apartman().izCSV(Integer.parseInt(s.nextLine())));
                } else {
                    System.out.println("Izaberite hotel:");
                    aranzman.setSmestaj(new Hotel().izCSV(Integer.parseInt(s.nextLine())));
                }
            }
            System.out.println("Da li želite da kreirate prevozno sredstvo?(1 za da, 2 za ne)");
            izbor = Integer.parseInt(s.nextLine());
            if(izbor == 1){
                aranzman.setPrevoznoSredstvo(kreirajPrevoz(s, aranzman.getSmestaj().getAdresa()));
            } else {
                System.out.println("Izaberite vrstu prevoza:");
                System.out.println("1.Avion");
                System.out.println("2.Voz");
                izbor = Integer.parseInt(s.nextLine());
                if(izbor == 1){
                    ArrayList<Avion> avioni = new Avion().sveIzCSV(aranzman.getSmestaj().getAdresa());
                    if(avioni.isEmpty()){
                        System.out.println("Nema aviona za ovo odredište!");
                    } else {
                        System.out.println("Izaberite avion:");
                        for (Avion avion : new Avion().sveIzCSV(aranzman.getSmestaj().getAdresa())) {
                            System.out.println(avion.getId() + "," + avion.getPolaznaAdresa() + "," + avion.getKlasaAvionskogMesta());
                        }
                        aranzman.setPrevoznoSredstvo(new Avion().izCSV(Integer.parseInt(s.nextLine())));
                    }
                } else {
                    ArrayList<Voz> vozovi = new Voz().sveIzCSV(aranzman.getSmestaj().getAdresa());
                    if(vozovi.isEmpty()) {
                        System.out.println("Nema voza za ovo odredište!");
                    } else {
                        System.out.println("Izaberite voz:");
                        for (Voz voz : vozovi) {
                            System.out.println(voz.getId() + "," + voz.getPolaznaAdresa() + "," + voz.getKlasaVoznogMesta());
                        }
                        aranzman.setPrevoznoSredstvo(new Voz().izCSV(Integer.parseInt(s.nextLine())));
                    }
                }
            }
            new Aranzman().uCSV(aranzman);
            meni(agent);
        }
    }

    public void kreirajPaket(Agent agent){
        new Paket().kreirajPaket(agent, 1);
    }

    public void brisanjePaketa(Agent agent) {
        System.out.println("Izaberite jedan od paketa:");
        if(new Paket().sveIzCSV(agent).isEmpty()){
            System.out.println("Nema paketa!");
            meni(agent);
        }
        for(Paket paket : new Paket().sveIzCSV(agent)){
            System.out.println(paket.getId());
            for (Aranzman aranzman : paket.getAranzmani()){
                System.out.println(aranzman.getSmestaj().getAdresa().getId() + "," + aranzman.getDatumPolaska() + "," + aranzman.getDatumPovratka());
            }
        }
        try(Scanner s = new Scanner(System.in)) {
            new Paket().brisanje(Integer.parseInt(s.nextLine()));
            meni(agent);
        }
    }

    @Override
    public void kreirajFajl() {
        String nazivFajla = "data/agenti.csv";

        File fajl = new File(nazivFajla);

        try {
            if (fajl.createNewFile()) {
                System.out.println("Fajl je uspešno kreiran.");
                Agent agent = new Agent("Ivan","Ivanović","ivan1","ivan123");
                agent.uCSV(agent);
            } else {
                System.out.println("Fajl već postoji.");
            }
        } catch (IOException e) {
            System.err.println("Došlo je do greške prilikom kreiranja fajla: " + e.getMessage());
        }
    }
}
