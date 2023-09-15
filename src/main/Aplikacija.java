package main;

import adresa.Adresa;
import adresa.Drzava;
import adresa.Mesto;
import aranzman.Aranzman;
import aranzman.Paket;
import korisnik.Agent;
import korisnik.Klijent;
import prevoznoSredstvo.Avion;
import prevoznoSredstvo.Voz;
import smestaj.Apartman;
import smestaj.Hotel;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Aplikacija {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String korisnickoIme = "";
        String lozinka = "";
        Agent agent = null;
        Klijent klijent = null;
        new Drzava().kreirajFajl();
        new Mesto().kreirajFajl();
        new Adresa().kreirajFajl();
        new Agent().kreirajFajl();
        new Klijent().kreirajFajl();
        new Avion().kreirajFajl();
        new Voz().kreirajFajl();
        new Apartman().kreirajFajl();
        new Hotel().kreirajFajl();
        new Aranzman().kreirajFajl();
        new Paket().kreirajFajl();
        System.out.println("Prijava korisnika");

        while(korisnickoIme.isEmpty() || lozinka.isEmpty()) {
            System.out.println("Unesite korisničko ime:");
            korisnickoIme = s.nextLine();

            if(korisnickoIme.isEmpty()) {
                System.out.println("Ovo polje ne sme ostati prazno! Unesite korisničko ime:");
                korisnickoIme = s.nextLine();
                continue;
            }

            if(lozinka.isEmpty()) {
                System.out.println("Unesite lozinku:");
                lozinka = s.nextLine();
                continue;
            }
        }

        try{
            File f = new File("data/agenti.csv");
            s = new Scanner(f, StandardCharsets.UTF_8);
            String[] red;
            File f1 = new File("data/klijenti.csv");

            while(s.hasNextLine()) {
                red = s.nextLine().split(",");
                if (korisnickoIme.equals(red[2]) && lozinka.equals(red[3])) {
                    agent = new Agent().izCSV(korisnickoIme);
                }
            }
            if (agent == null){
                s = new Scanner(f1, StandardCharsets.UTF_8);
                String[] red1;
                while(s.hasNextLine()) {
                    red1 = s.nextLine().split(",");
                    if (korisnickoIme.equals(red1[2]) && lozinka.equals(red1[3])) {
                        klijent = new Klijent().izCSV(korisnickoIme);
                    }
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        if(klijent == null && agent == null){
            System.out.println("Pogresno korisnicko ime ili sifra!");
            main(args);
        }

        if(agent != null){
            agent.meni(agent);
        } else if (klijent != null) {
            klijent.meni(klijent);
        }
    }
}