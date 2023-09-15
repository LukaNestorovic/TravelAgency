package korisnik;

import aranzman.Aranzman;
import aranzman.Paket;
import interfejsi.Kreiranje;
import interfejsi.Serijalizacija;
import main.Aplikacija;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Scanner;

public class Klijent extends Korisnik implements Serijalizacija<Klijent, String>, Kreiranje {
    private Double raspolozivoStanje;

    public Klijent() {
    }

    public Klijent(String ime, String prezime, String korisnickoIme, String lozinka, Double raspolozivoStanje) {
        super(ime, prezime, korisnickoIme, lozinka);
        this.raspolozivoStanje = raspolozivoStanje;
    }

    public Double getRaspolozivoStanje() {
        return raspolozivoStanje;
    }

    public void setRaspolozivoStanje(Double raspolozivoStanje) {
        this.raspolozivoStanje = raspolozivoStanje;
    }

    @Override
    public Klijent izCSV(String korisnickoIme) {
        String csvFile = "data/klijenti.csv";
        String red;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((red = br.readLine()) != null){
                String[] podaci = red.split(",");
                if(podaci[2].equals(korisnickoIme)) {
                    Klijent klijent = new Klijent();
                    klijent.setIme(podaci[0]);
                    klijent.setPrezime(podaci[1]);
                    klijent.setKorisnickoIme(podaci[2]);
                    klijent.setLozinka(podaci[3]);
                    klijent.setRaspolozivoStanje(Double.parseDouble(podaci[4]));
                    return klijent;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void uCSV(Klijent klijent) {
        ArrayList<String> podaci = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("data/klijenti.csv"))) {
            String red;
            while ((red = br.readLine()) != null) {
                podaci.add(red);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuffer noviRed = new StringBuffer();
        noviRed.append(klijent.getIme());
        noviRed.append(",");
        noviRed.append(klijent.getPrezime());
        noviRed.append(",");
        noviRed.append(klijent.getKorisnickoIme());
        noviRed.append(",");
        noviRed.append(klijent.getLozinka());
        noviRed.append(",");
        noviRed.append(klijent.getRaspolozivoStanje());
        podaci.add(String.valueOf(noviRed));

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/klijenti.csv"))) {
            for (String red : podaci) {
                bw.write(red);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void izmeniRed(String csvFile, Klijent klijent) {
        ArrayList<String> redovi = new ArrayList<>();
        ArrayList<String> izmenjeni = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String red;
            while ((red = br.readLine()) != null) {
                redovi.add(red);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String red: redovi){
            String podaci[] = red.split(",");
            if(podaci[2].equals(korisnickoIme)){
                podaci[4] = String.valueOf(klijent.getRaspolozivoStanje());
            }
            StringBuffer noviRed = new StringBuffer();
            noviRed.append(podaci[0]);
            noviRed.append(",");
            noviRed.append(podaci[1]);
            noviRed.append(",");
            noviRed.append(podaci[2]);
            noviRed.append(",");
            noviRed.append(podaci[3]);
            noviRed.append(",");
            noviRed.append(podaci[4]);
            izmenjeni.add(String.valueOf(noviRed));
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile))) {
            for (String red : izmenjeni) {
                bw.write(red);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void meni(Klijent klijent) {
        System.out.println("0. Odjavi se");
        System.out.println("1. Uplati novac");
        System.out.println("2. Kreiraj paket");
        System.out.println("3. Zakupi paket");
        System.out.println("4. Otkaži paket");

        try (Scanner s = new Scanner(System.in)) {
            String izbor;
            while(true) {
                System.out.println("Molimo Vas unesite jednu od ponuđenih opcija:");
                izbor = s.nextLine();

                if(izbor.isEmpty()) {
                    System.out.println("Ovo polje ne sme ostati prazno!");
                    continue;
                }

                if(!(izbor.equals("0")) && !(izbor.equals("1")) && !(izbor.equals("2")) && !(izbor.equals("3")) && !(izbor.equals("4"))){
                    System.out.println("Greška!");
                    continue;
                }

                switch (izbor) {
                    case "0" -> {
                        System.out.println("Hvala Vam na korišćenju aplikacije! Do viđenja!");
                        Aplikacija.main(new String[]{});
                        return;
                    }
                    case "1" -> {
                        uplataNovca(klijent);
                        return;
                    }
                    case "2" -> {
                        kreirajPaket(klijent);
                        return;
                    }
                    case "3" -> {
                        zakupi(klijent);
                        return;
                    }
                    case "4" -> {
                        otkazi(klijent);
                        return;
                    }
                    default -> {
                        System.out.println("Greška! Molimo Vas unesite jednu od ponuđenih opcija:");
                        return;
                    }
                }
            }
        }
    }

    public void uplataNovca(Klijent klijent) {
        Scanner s = new Scanner(System.in);
        String kolicinaNovca = "";

        while(kolicinaNovca.isEmpty() || Double.parseDouble(kolicinaNovca) < 0) {
            System.out.println("Unesite količinu novca koju želite da uplatite:");
            kolicinaNovca = s.nextLine();

            if(kolicinaNovca.isEmpty()) {
                System.out.println("Greška! Ovo polje ne sme ostati prazno!");
                continue;
            }

            if(Double.parseDouble(kolicinaNovca) < 0) {
                System.out.println("Greška! Iznos novca ne sme biti negativan! Pokušajte ponovo:");
            }
        }

        klijent.setRaspolozivoStanje(klijent.getRaspolozivoStanje() + Double.parseDouble(kolicinaNovca));
        izmeniRed("data/klijenti.csv", klijent);
        meni(klijent);
        s.close();
    }

    public void zakupi(Klijent klijent){
        System.out.println("Izaberite jedan od paketa:");
        if(new Paket().sveIzCSV().isEmpty()){
            System.out.println("Nema paketa!");
            meni(klijent);
        }
        for(Paket paket : new Paket().sveIzCSV()){
            System.out.println(paket.getId());
            for (Aranzman aranzman : paket.getAranzmani()){
                System.out.println(aranzman.getSmestaj().getAdresa().getId() + "," + aranzman.getDatumPolaska() + "," + aranzman.getDatumPovratka());
            }
        }
        try(Scanner s = new Scanner(System.in)) {
            Integer id = Integer.parseInt(s.nextLine());
            Paket paket = new Paket().izCSV(id);
            Integer suma = paket.zakupi(0, klijent.getKorisnickoIme());
            if(suma <= klijent.getRaspolozivoStanje()) {
                System.out.println("Ukupan iznos iznosi: " + suma);
                System.out.println("Da li želite da nastavite? (1 za da, 2 za ne)");
                if(Integer.parseInt(s.nextLine()) == 1) {
                    paket.izmeniRed("data/paketi.csv", klijent, id);
                }
            }
            else System.out.println("Nemate dovoljno sredstava!");
            meni(klijent);
        }
    }

    public void otkazi(Klijent klijent) {
        System.out.println("Svi vasi paketi:");
        if(new Paket().sveIzCSV(klijent).isEmpty()){
            System.out.println("Nema paketa!");
            meni(klijent);
        }
        for(Paket paket : new Paket().sveIzCSV(klijent)){
            System.out.println(paket.getId());
            for (Aranzman aranzman : paket.getAranzmani()){
                System.out.println(aranzman.getSmestaj().getAdresa().getId() + "," + aranzman.getDatumPolaska() + "," + aranzman.getDatumPovratka());
            }
        }
        try(Scanner s = new Scanner(System.in)) {
            System.out.println("Izaberite paket koji želite da uklonite");
            Integer id = Integer.parseInt(s.nextLine());
            Paket paket = new Paket().izCSV(id);
            Integer suma = paket.suma(0, klijent.getKorisnickoIme());
            Klijent klijent1 = new Klijent().izCSV(klijent.getKorisnickoIme());
            klijent1.setRaspolozivoStanje(klijent1.getRaspolozivoStanje() + suma * paket.getProcenatPovrataNovca() / 100);
            klijent1.izmeniRed("data/klijenti.csv", klijent1);
            new Paket().otkaziPaket("data/paketi.csv", id);
            meni(klijent);
        }
    }

    public void kreirajPaket(Klijent klijent){
        new Paket().kreirajPaket(klijent, 2);
    }

    @Override
    public void kreirajFajl() {
        String nazivFajla = "data/klijenti.csv";

        File fajl = new File(nazivFajla);

        try {
            if (fajl.createNewFile()) {
                System.out.println("Fajl je uspešno kreiran.");
                Klijent klijent = new Klijent("Marko","Marković","mare","mare123",90164.0);
                klijent.uCSV(klijent);
                klijent = new Klijent("Petar","Petrović","pera","pera123",101924.0);
                klijent.uCSV(klijent);
                klijent = new Klijent("John","Doe","johndoe","johnny123",2020.0);
                klijent.uCSV(klijent);
            } else {
                System.out.println("Fajl već postoji.");
            }
        } catch (IOException e) {
            System.err.println("Došlo je do greške prilikom kreiranja fajla: " + e.getMessage());
        }
    }
}
