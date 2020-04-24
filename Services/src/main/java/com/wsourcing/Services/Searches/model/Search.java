package com.wsourcing.Services.Searches.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "searches")
public class Search {

    @Transient
    public static final String SEQUENCE_NAME = "searches_sequence";

    @Id
    private long id ;

    private String searchName;
    private ArrayList<String> mandatorySkills;
    private ArrayList<String> optionalSkills;
    private boolean isHalted ;
    private int totalURLsFound ;
    private int totalNewUrls ;
    private int totalDistinctURLs;
    private int totalProfilesScraped ;
    private int urgency ;

    public Search() {
    }

    public Search(long id, String searchName, ArrayList<String> mandatorySkills, ArrayList<String> optionalSkills, boolean isHalted, int totalURLsFound, int totalNewUrls, int totalDistinctURLs, int totalProfilesScraped, int urgency) {
        this.id = id;
        this.searchName = searchName;
        this.mandatorySkills = mandatorySkills;
        this.optionalSkills = optionalSkills;
        this.isHalted = isHalted;
        this.totalURLsFound = totalURLsFound;
        this.totalNewUrls = totalNewUrls;
        this.totalDistinctURLs = totalDistinctURLs;
        this.totalProfilesScraped = totalProfilesScraped;
        this.urgency = urgency;
    }

    public Search(String searchName, ArrayList<String> mandatorySkills, ArrayList<String> optionalSkills, boolean isHalted, int totalURLsFound, int totalNewUrls, int totalDistinctURLs, int totalProfilesScraped, int urgency) {
        this.searchName = searchName;
        this.mandatorySkills = mandatorySkills;
        this.optionalSkills = optionalSkills;
        this.isHalted = isHalted;
        this.totalURLsFound = totalURLsFound;
        this.totalNewUrls = totalNewUrls;
        this.totalDistinctURLs = totalDistinctURLs;
        this.totalProfilesScraped = totalProfilesScraped;
        this.urgency = urgency;
    }

    public ArrayList<String> getMandatorySkills() {
        return mandatorySkills;
    }

    public void setMandatorySkills(ArrayList<String> mandatorySkills) {
        this.mandatorySkills = mandatorySkills;
    }

    public ArrayList<String> getOptionalSkills() {
        return optionalSkills;
    }

    public void setOptionalSkills(ArrayList<String> optionalSkills) {
        this.optionalSkills = optionalSkills;
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

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }


    public boolean isHalted() {
        return isHalted;
    }

    public void setHalted(boolean halted) {
        isHalted = halted;
    }

    public int getTotalURLsFound() {
        return totalURLsFound;
    }

    public void setTotalURLsFound(int totalURLsFound) {
        this.totalURLsFound = totalURLsFound;
    }

    public int getTotalNewUrls() {
        return totalNewUrls;
    }

    public void setTotalNewUrls(int totalNewUrls) {
        this.totalNewUrls = totalNewUrls;
    }

    public int getTotalDistinctURLs() {
        return totalDistinctURLs;
    }

    public void setTotalDistinctURLs(int totalDistinctURLs) {
        this.totalDistinctURLs = totalDistinctURLs;
    }

    public int getTotalProfilesScraped() {
        return totalProfilesScraped;
    }

    public void setTotalProfilesScraped(int totalProfilesScraped) {
        this.totalProfilesScraped = totalProfilesScraped;
    }

    public int getUrgency() {
        return urgency;
    }

    public void setUrgency(int urgency) {
        this.urgency = urgency;
    }
}
