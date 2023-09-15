package aranzman;

import interfejsi.Kreiranje;
import interfejsi.Serijalizacija;
import interfejsi.Zakupljivo;
import korisnik.Agent;
import korisnik.Klijent;
import korisnik.Korisnik;


import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static java.time.temporal.ChronoUnit.DAYS;

public class Paket implements Zakupljivo, Serijalizacija<Paket, Integer>, Kreiranje {
    private Integer id;
    private Double procenatPovrataNovca;
    private ArrayList<Aranzman> aranzmani;
    private Korisnik kreatorPaketa;
    private Klijent zakupljivac;

    public Paket() {
    }

    public Paket(Integer id, Double procenatPovrataNovca, ArrayList<Aranzman> aranzmani, Korisnik kreatorPaketa, Klijent zakupljivac) {
        this.id = id;
        this.procenatPovrataNovca = procenatPovrataNovca;
        this.aranzmani = aranzmani;
        this.kreatorPaketa = kreatorPaketa;
        this.zakupljivac = zakupljivac;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getProcenatPovrataNovca() {
        return procenatPovrataNovca;
    }

    public void setProcenatPovrataNovca(Double procenatPovrataNovca) {
        this.procenatPovrataNovca = procenatPovrataNovca;
    }

    public ArrayList<Aranzman> getAranzmani() {
        return aranzmani;
    }

    public void setAranzmani(ArrayList<Aranzman> aranzmani) {
        this.aranzmani = aranzmani;
    }

    public Korisnik getKreatorPaketa() {
        return kreatorPaketa;
    }

    public void setKreatorPaketa(Korisnik kreatorPaketa) {
        this.kreatorPaketa = kreatorPaketa;
    }

    public Klijent getZakupljivac() {
        return zakupljivac;
    }

    public void setZakupljivac(Klijent zakupljivac) {
        this.zakupljivac = zakupljivac;
    }
    @Override
    public void uCSV(Paket paket){
        ArrayList<String> podaci = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("data/paketi.csv"))) {
            String red;
            while ((red = br.readLine()) != null) {
                podaci.add(red);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuffer noviRed = new StringBuffer();
        noviRed.append(paket.getId());
        noviRed.append(",");
        noviRed.append(paket.getProcenatPovrataNovca());
        noviRed.append(",");
        for(Aranzman aranzman : paket.getAranzmani()){
            noviRed.append(aranzman.getId());
            noviRed.append(";");
        }
        noviRed.append(",");
        noviRed.append(paket.getKreatorPaketa().getKorisnickoIme());
        noviRed.append(",");
        if(paket.getZakupljivac() == null){
            noviRed.append("null");
        } else {
            noviRed.append(paket.getZakupljivac().getKorisnickoIme());
        }
        podaci.add(String.valueOf(noviRed));

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/paketi.csv"))) {
            for (String red : podaci) {
                bw.write(red);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Paket izCSV(Integer id) {
        String csvFile = "data/paketi.csv";
        String red;
        ArrayList<Aranzman> aranzmani = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((red = br.readLine()) != null){
                String[] podaci = red.split(",");
                if(Integer.parseInt(podaci[0]) == id) {
                    Paket paket = new Paket();
                    paket.setId(Integer.parseInt(podaci[0]));
                    paket.setProcenatPovrataNovca(Double.parseDouble(podaci[1]));
                    String[] data1 = podaci[2].split(";");
                    for(int i = 0; i < data1.length; i++){
                        aranzmani.add(new Aranzman().izCSV(Integer.parseInt(data1[i])));
                    }
                    paket.setAranzmani(aranzmani);
                    paket.setKreatorPaketa(new Agent().izCSV(podaci[3]));
                    if(paket.getKreatorPaketa() == null){
                        paket.setKreatorPaketa(new Klijent().izCSV(podaci[3]));
                    }
                    paket.setZakupljivac(new Klijent().izCSV(podaci[4]));
                    return paket;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public ArrayList<Paket> sveIzCSV() {
        String csvFile = "data/paketi.csv";
        String red;
        ArrayList<Paket> paketi = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((red = br.readLine()) != null){
                String[] podaci = red.split(",");
                if(podaci[4].equals("null")) {
                    Paket paket = new Paket();
                    paket.setId(Integer.parseInt(podaci[0]));
                    paket.setProcenatPovrataNovca(Double.parseDouble(podaci[1]));
                    String[] podaci1 = podaci[2].split(";");
                    ArrayList<Aranzman> aranzmani = new ArrayList<>();
                    for (int i = 0; i < podaci1.length; i++) {
                        aranzmani.add(new Aranzman().izCSV(Integer.parseInt(podaci1[i])));
                    }
                    paket.setAranzmani(aranzmani);
                    paket.setKreatorPaketa(new Agent().izCSV(podaci[3]));
                    if (paket.getKreatorPaketa() == null) {
                        paket.setKreatorPaketa(new Klijent().izCSV(podaci[3]));
                    }
                    paket.setZakupljivac(new Klijent().izCSV(podaci[4]));
                    paketi.add(paket);
                }
            }
            return paketi;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Paket> sveIzCSV(Agent agent) {
        String csvFile = "data/paketi.csv";
        String red;
        ArrayList<Paket> paketi = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((red = br.readLine()) != null){
                String[] podaci = red.split(",");
                if(podaci[3].equals(agent.getKorisnickoIme()) && podaci[4].equals("null")) {
                    Paket paket = new Paket();
                    paket.setId(Integer.parseInt(podaci[0]));
                    paket.setProcenatPovrataNovca(Double.parseDouble(podaci[1]));
                    String[] podaci1 = podaci[2].split(";");
                    ArrayList<Aranzman> aranzmani = new ArrayList<>();
                    for (int i = 0; i < podaci1.length; i++) {
                        aranzmani.add(new Aranzman().izCSV(Integer.parseInt(podaci1[i])));
                    }
                    paket.setAranzmani(aranzmani);
                    paket.setKreatorPaketa(agent);
                    paket.setZakupljivac(new Klijent().izCSV(podaci[4]));
                    paketi.add(paket);
                }
            }
            return paketi;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Paket> sveIzCSV(Klijent klijent) {
        String csvFile = "data/paketi.csv";
        String red;
        ArrayList<Paket> paketi = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((red = br.readLine()) != null){
                String[] podaci = red.split(",");
                if(podaci[4].equals(klijent.getKorisnickoIme())) {
                    Paket paket = new Paket();
                    paket.setId(Integer.parseInt(podaci[0]));
                    paket.setProcenatPovrataNovca(Double.parseDouble(podaci[1]));
                    String[] podaci1 = podaci[2].split(";");
                    ArrayList<Aranzman> aranzmani = new ArrayList<>();
                    for (int i = 0; i < podaci1.length; i++) {
                        aranzmani.add(new Aranzman().izCSV(Integer.parseInt(podaci1[i])));
                    }
                    paket.setAranzmani(aranzmani);
                    paket.setKreatorPaketa(new Agent().izCSV(podaci[3]));
                    if (paket.getKreatorPaketa() == null) {
                        paket.setKreatorPaketa(new Klijent().izCSV(podaci[3]));
                    }
                    paket.setZakupljivac(new Klijent().izCSV(podaci[4]));
                    paketi.add(paket);
                }
            }
            return paketi;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void izmeniRed(String csvFile, Klijent klijent, Integer id) {
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

        for (String red1: redovi){
            String podaci[] = red1.split(",");
            if(Integer.parseInt(podaci[0]) == id){
                podaci[4] = klijent.getKorisnickoIme();
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
            for (String linija : izmenjeni) {
                bw.write(linija);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void brisanje(Integer id) {
        ArrayList<String> redovi = new ArrayList<>();
        ArrayList<String> izmenjeni = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("data/paketi.csv"))) {
            String red;
            while ((red = br.readLine()) != null) {
                redovi.add(red);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String red : redovi) {
            String podaci[] = red.split(",");
            if (Integer.parseInt(podaci[0]) == id) {
                podaci = null;
            }
            if (podaci != null) {
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
                izmenjeni.add(String.valueOf(red));
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/paketi.csv"))) {
            for (String red : izmenjeni) {
                bw.write(red);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void otkaziPaket(String csvFile, Integer id) {
        ArrayList<String> lista = new ArrayList<>();
        ArrayList<String> izmenjeni = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String red;
            while ((red = br.readLine()) != null) {
                lista.add(red);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String red : lista){
            String podaci[] = red.split(",");
            if(Integer.parseInt(podaci[0]) == id){
                podaci[4] = "null";
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
            for (String linija : izmenjeni) {
                bw.write(linija);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void kreirajPaket(Korisnik korisnik, Integer l){
        try(Scanner s = new Scanner(System.in)) {
            Paket paket = new Paket();
            System.out.println("Kreiranje paketa:");
            System.out.println("Unesite id:");
            paket.setId(Integer.parseInt(s.nextLine()));
            if(l == 1) {
                System.out.println("Unesite procenat povrata novca:");
                paket.setProcenatPovrataNovca(Double.parseDouble(s.nextLine()));
            } else {
                paket.setProcenatPovrataNovca(80.0);
            }
            ArrayList<Aranzman> aranzmani = new ArrayList<>();
            System.out.println("Unesite aranzmane (0 za izlaz):");
            while(true){
                Integer id = Integer.parseInt(s.nextLine());
                if(id == 0){
                    break;
                }
                Aranzman aranzman = new Aranzman().izCSV(id);
                aranzmani.add(aranzman);
            }
            paket.setAranzmani(aranzmani);
            paket.setKreatorPaketa(korisnik);
            paket.setZakupljivac(null);
            new Paket().uCSV(paket);
            if(l == 1){
                Agent agent = new Agent().izCSV(korisnik.getKorisnickoIme());
                agent.meni(agent);
            } else {
                Klijent klijent = new Klijent().izCSV(korisnik.getKorisnickoIme());
                klijent.meni(klijent);
            }
        }
    }

    public Integer suma(Integer dani, String korisnickoIme) {
        Integer suma = 0;
        Klijent klijent = new Klijent().izCSV(korisnickoIme);
        Long dan;
        for(Aranzman aranzman : this.aranzmani){
            dan = DAYS.between(aranzman.getDatumPolaska(), aranzman.getDatumPovratka());
            suma += aranzman.getSmestaj().zakupi(Math.toIntExact(dan), korisnickoIme);
            suma += aranzman.getPrevoznoSredstvo().zakupi(1, korisnickoIme);
        }
        return suma;
    }

    @Override
    public Integer zakupi(Integer dani, String korisnickoIme) {
        Integer suma = 0;
        Klijent klijent = new Klijent().izCSV(korisnickoIme);
        Long dan;
        for(Aranzman aranzman : this.aranzmani){
            dan = DAYS.between(aranzman.getDatumPolaska(), aranzman.getDatumPovratka());
            suma += aranzman.getSmestaj().zakupi(Math.toIntExact(dan), korisnickoIme);
            suma += aranzman.getPrevoznoSredstvo().zakupi(1, korisnickoIme);
        }
        if(suma <= klijent.getRaspolozivoStanje()) {
            this.setZakupljivac(klijent);
            klijent.setRaspolozivoStanje(klijent.getRaspolozivoStanje() - suma);
            klijent.izmeniRed("data/klijenti.csv", klijent);
        }
        return suma;
    }

    @Override
    public void otkazi() {
        this.setZakupljivac(null);
    }

    @Override
    public void kreirajFajl() {
        String nazivFajla = "data/paketi.csv";

        File fajl = new File(nazivFajla);

        try {
            if (fajl.createNewFile()) {
                System.out.println("Fajl je uspešno kreiran.");
                ArrayList<Aranzman> aranzmani = new ArrayList<>();
                aranzmani.add(new Aranzman().izCSV(2));
                Paket paket = new Paket(10,80.0,aranzmani,new Agent().izCSV("ivan1"), null);
                paket.uCSV(paket);
                paket = new Paket(3,80.0,aranzmani,new Klijent().izCSV("pera"), null);
                paket.uCSV(paket);
            } else {
                System.out.println("Fajl već postoji.");
            }
        } catch (IOException e) {
            System.err.println("Došlo je do greške prilikom kreiranja fajla: " + e.getMessage());
        }
    }
}
