package com.wsourcing.Services.Profiles.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;


//Candidates profile which we will search on them with the adequate criteria
@Document(collection = "profiles")
public class Profile {

    //Used to auto-generate Profile id with long type in the database
    //mongodb id is a STRING type without auto-generate function
    @Transient
    public static final String SEQUENCE_NAME = "profiles_sequence";

    @Id
    private long id ;

    private Accomplishment accomplishments ;
    private Experience experiences ;
    private ArrayList<String> interests ;
    private PersonalInfo personal_info ;
    private ScrapedTime scraped_time ;
    private String search ;
    private List<Skill2> skills ;
    private String url;
    private String name;
    private String headline;
    private String company;
    private String school;
    private String location;
    private String summary;
    private String  image;
    private String followers;
    private String  email;
    private String phone;
    private String  connected;
    private ArrayList<String> websites ;
    private NewFeature new_features ;

    public Profile() {
    }

    public Profile(Accomplishment accomplishments, Experience experiences, ArrayList<String> interests, PersonalInfo personal_info, ScrapedTime scraped_time, String search, List<Skill2> skills, String url, String name, String headline, String company, String school, String location, String summary, String image, String followers, String email, String phone, String connected, ArrayList<String> websites, NewFeature new_features) {
        this.accomplishments = accomplishments;
        this.experiences = experiences;
        this.interests = interests;
        this.personal_info = personal_info;
        this.scraped_time = scraped_time;
        this.search = search;
        this.skills = skills;
        this.url = url;
        this.name = name;
        this.headline = headline;
        this.company = company;
        this.school = school;
        this.location = location;
        this.summary = summary;
        this.image = image;
        this.followers = followers;
        this.email = email;
        this.phone = phone;
        this.connected = connected;
        this.websites = websites;
        this.new_features = new_features;
    }

    public Profile(long id, Accomplishment accomplishments, Experience experiences, ArrayList<String> interests, PersonalInfo personal_info, ScrapedTime scraped_time, String search, List<Skill2> skills, String url, String name, String headline, String company, String school, String location, String summary, String image, String followers, String email, String phone, String connected, ArrayList<String> websites, NewFeature new_features) {
        this.id = id;
        this.accomplishments = accomplishments;
        this.experiences = experiences;
        this.interests = interests;
        this.personal_info = personal_info;
        this.scraped_time = scraped_time;
        this.search = search;
        this.skills = skills;
        this.url = url;
        this.name = name;
        this.headline = headline;
        this.company = company;
        this.school = school;
        this.location = location;
        this.summary = summary;
        this.image = image;
        this.followers = followers;
        this.email = email;
        this.phone = phone;
        this.connected = connected;
        this.websites = websites;
        this.new_features = new_features;
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

    public Accomplishment getAccomplishments() {
        return accomplishments;
    }

    public void setAccomplishments(Accomplishment accomplishments) {
        this.accomplishments = accomplishments;
    }

    public Experience getExperiences() {
        return experiences;
    }

    public void setExperiences(Experience experiences) {
        this.experiences = experiences;
    }

    public ArrayList<String> getInterests() {
        return interests;
    }

    public void setInterests(ArrayList<String> interests) {
        this.interests = interests;
    }

    public PersonalInfo getPersonal_info() {
        return personal_info;
    }

    public void setPersonal_info(PersonalInfo personal_info) {
        this.personal_info = personal_info;
    }

    public ScrapedTime getScraped_time() {
        return scraped_time;
    }

    public void setScraped_time(ScrapedTime scraped_time) {
        this.scraped_time = scraped_time;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public List<Skill2> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill2> skills) {
        this.skills = skills;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getConnected() {
        return connected;
    }

    public void setConnected(String connected) {
        this.connected = connected;
    }

    public ArrayList<String> getWebsites() {
        return websites;
    }

    public void setWebsites(ArrayList<String> websites) {
        this.websites = websites;
    }

    public NewFeature getNew_features() {
        return new_features;
    }

    public void setNew_features(NewFeature new_features) {
        this.new_features = new_features;
    }
}
