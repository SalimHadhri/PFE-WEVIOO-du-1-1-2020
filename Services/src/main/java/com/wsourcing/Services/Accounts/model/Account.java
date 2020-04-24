package com.wsourcing.Services.Accounts.model;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;




@Document(collection = "accounts")
public class Account {



    @Transient
    public static final String SEQUENCE_NAME = "accounts_sequence";

    @Id
    private long id ;

    @Length(min=3,max=10,message = "ici votre message")
    private String name;
    private String email;
    private String url;
    private String liat;
    private String etat;
    private int nb_scraping_jour;
    private int nb_scraping_actuel;


    public Account() {
    }


    public Account(String name, String email, String url, String liat, String etat, int nb_scraping_jour, int nb_scraping_actuel) {
        this.name = name;
        this.email = email;
        this.url = url;
        this.liat = liat;
        this.etat = etat;
        this.nb_scraping_jour = nb_scraping_jour;
        this.nb_scraping_actuel = nb_scraping_actuel;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", url='" + url + '\'' +
                ", li_at='" + liat + '\'' +
                ", etat='" + etat + '\'' +
                ", nb_scraping_jour=" + nb_scraping_jour +
                ", nb_scraping_actuel=" + nb_scraping_actuel +
                '}';
    }

    public Account(int id, String name, String email, String url, String liat, String etat, int nb_scraping_jour, int nb_scraping_actuel) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.url = url;
        this.liat = liat;
        this.etat = etat;
        this.nb_scraping_jour = nb_scraping_jour;
        this.nb_scraping_actuel = nb_scraping_actuel;
    }

    public static String getSequenceName() {
        return SEQUENCE_NAME;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLiat() {
        return liat;
    }

    public void setLiat(String liat) {
        this.liat = liat;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public int getNb_scraping_jour() {
        return nb_scraping_jour;
    }

    public void setNb_scraping_jour(int nb_scraping_jour) {
        this.nb_scraping_jour = nb_scraping_jour;
    }

    public int getNb_scraping_actuel() {
        return nb_scraping_actuel;
    }

    public void setNb_scraping_actuel(int nb_scraping_actuel) {
        this.nb_scraping_actuel = nb_scraping_actuel;
    }


}
