package com.wsourcing.Services.Profiles.resource;



import ch.qos.logback.core.spi.AbstractComponentTracker;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoClients;
import com.wsourcing.Services.Accounts.model.Account;
import com.wsourcing.Services.Profiles.exception.ProfileNotFoundException;
import com.wsourcing.Services.Profiles.model.Profile;
import com.wsourcing.Services.Profiles.model.Skill2;
import com.wsourcing.Services.Profiles.repository.ProfileRepository;
import com.wsourcing.Services.Profiles.service.SequenceGeneratorServiceProfile;

import org.apache.catalina.Manager;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@CrossOrigin()
@RestController
@RequestMapping("/profiles")
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private SequenceGeneratorServiceProfile sequenceGeneratorServiceProfile;

    public ProfileController(ProfileRepository profileRepository, SequenceGeneratorServiceProfile sequenceGeneratorServiceProfile) {
        this.profileRepository = profileRepository;
        this.sequenceGeneratorServiceProfile = sequenceGeneratorServiceProfile;
    }

    //private MongoOperations mongoOperations;

    // @Autowired
    // public ProfileController(MongoOperations mongoOperations) {
    //   this.mongoOperations = mongoOperations;
    //
    // @Autowired
    // private MongoTemplate mongoTemplate;

    //private MongoOperations mongoOperations;


    public ProfileController() {
    }

    @CrossOrigin()
    @PostMapping(value = "/addProfile")
    public void addAccount(@Valid @RequestBody Profile profile) {

        profileRepository.save(profile);
    }

    @CrossOrigin(origins = "https://localhost:4200/**")
    @GetMapping(value = "/listProfiles")
    public List<Profile> getAllProfiles() {
        // LOGGER.info("findAll");
        return profileRepository.findAll();

    }

    @GetMapping(value = "/findProfile/{id}")
    public Profile findProfileById(@PathVariable int id) throws ProfileNotFoundException {
        Profile profile = profileRepository.findById(id);
        if (profile == null) throw new ProfileNotFoundException("the profile with id " + id + " don't exist");
        return profile;
    }

    @PutMapping(value = "/updateProfile/{id}")
    public void updateProfile(@PathVariable int id, @RequestBody Profile profile) throws ProfileNotFoundException {
        Profile profile1 = profileRepository.findById(id);
        if (profile1 == null) {
            throw new ProfileNotFoundException("the profile with id " + id + " don't exist");
        } else {
            profile.setId(profile1.getId());
            profileRepository.save(profile);
        }
    }

    @DeleteMapping(value = "/deleteProfile/{id}")
    public void deleteProfileById(@PathVariable int id) throws ProfileNotFoundException {
        Profile profile = profileRepository.findById(id);
        if (profile == null) throw new ProfileNotFoundException("the profile with id " + id + " don't exist");
        profileRepository.deleteById((int) profile.getId());
    }

    public int nbrJob(Profile profile) {
        return profile.getExperiences().getJobs().size();
    }

    public List<Profile> experienceRange(int min, int max) throws ProfileNotFoundException {
        List<Profile> orderedProfiles = new ArrayList<>();
        List<Profile> profiles = profileRepository.findAll();

        if (min == 0 && max == 0) {
            orderedProfiles = profiles;
        } else {


            if (min == 0) {
                min = min + 1;
            }
            if (min > max) {
                throw new ProfileNotFoundException(" the range of experiences does not match");
            }


            for (int i = 0; i < profiles.size(); i++) {
                if (nbrJob(profiles.get(i)) >= min && nbrJob(profiles.get(i)) <= max) {
                    orderedProfiles.add(profiles.get(i));
                }
            }
        }
        return orderedProfiles;
    }


    // private static final Log log = LogFactory.getLog(ProfileController.class);


//@Autowired
//MongoOperations mongoOperations ;


    @GetMapping(value = "/SearchAll/{min}/{max}/{tunisia}/{categorie}/{profile}/{termes}")
    public List<Profile> SearchAll(@PathVariable int min, @PathVariable int max, @PathVariable int tunisia, @PathVariable int categorie
            , @PathVariable String profile, @PathVariable String termes) throws UnknownHostException {

        //mongoOperations=new MongoTemplate(MongoClients.create(), "wsourcingServices");

        List<Profile> profilesRange = experienceRange(min, max);
        List<Profile> profilesRTunisia = new ArrayList<>();
        for (int i = 0; i < profilesRange.size(); i++) {
            if (profilesRange.get(i).getNew_features().getInTunisia() == tunisia) {
                profilesRTunisia.add(profilesRange.get(i));
            }
        }
        List<Profile> profilesRTNCategorie = new ArrayList<>();

        if (categorie == 0) {
            profilesRTNCategorie = profilesRTunisia;
        } else {


            for (int i = 0; i < profilesRTunisia.size(); i++) {
                if (profilesRTunisia.get(i).getNew_features().getCateg_seniority() == categorie) {
                    profilesRTNCategorie.add(profilesRTunisia.get(i));
                }
            }
        }

        List<Profile> profilesRTNCProTechnicalLead = new ArrayList<>();
        List<Profile> profilesRTNCProJavaJee = new ArrayList<>();
        List<Profile> profilesRTNCProDupral = new ArrayList<>();
        List<Profile> profilesRTNCProProductOwner = new ArrayList<>();
        List<Profile> profilesRTNCProWebBackEnd = new ArrayList<>();
        List<Profile> profilesRTNCProFullStackJs = new ArrayList<>();
        List<Profile> profilesRTNCProEmbarqueMiddleWare = new ArrayList<>();
        List<Profile> profilesRTNCProPhpSymphony = new ArrayList<>();


        for (int i = 0; i < profilesRTNCategorie.size(); i++) {
            if (profilesRTNCategorie.get(i).getNew_features().getFinal_best_profile().contains("Technical Lead/ Architecte JEE")
) {

                profilesRTNCProTechnicalLead.add(profilesRTNCategorie.get(i));
            }
        }

        for (int j = 0; j < profilesRTNCategorie.size(); j++) {
            if (profilesRTNCategorie.get(j).getNew_features().getFinal_best_profile().contains("JAVA/JEE")
) {

                profilesRTNCProJavaJee.add(profilesRTNCategorie.get(j));
            }
        }
        for (int e = 0; e < profilesRTNCategorie.size(); e++) {
            if (profilesRTNCategorie.get(e).getNew_features().getFinal_best_profile().contains("DRUPAL")
) {

                profilesRTNCProDupral.add(profilesRTNCategorie.get(e));
            }
        }
        for (int f = 0; f < profilesRTNCategorie.size(); f++) {
            if (profilesRTNCategorie.get(f).getNew_features().getFinal_best_profile().contains("Product Owner")
) {

                profilesRTNCProProductOwner.add(profilesRTNCategorie.get(f));
            }
        }
        for (int g = 0; g < profilesRTNCategorie.size(); g++) {
            if (profilesRTNCategorie.get(g).getNew_features().getFinal_best_profile().contains("Web Back-End")
) {

                profilesRTNCProWebBackEnd.add(profilesRTNCategorie.get(g));
            }
        }
        for (int x = 0; x < profilesRTNCategorie.size(); x++) {
            if (profilesRTNCategorie.get(x).getNew_features().getFinal_best_profile().contains("FullStack JS")
                    ) {

                profilesRTNCProFullStackJs.add(profilesRTNCategorie.get(x));
            }
        }
        for (int y = 0; y < profilesRTNCategorie.size(); y++) {
            if (profilesRTNCategorie.get(y).getNew_features().getFinal_best_profile().contains("Embarqué Middleware")
                    ) {

                profilesRTNCProEmbarqueMiddleWare.add(profilesRTNCategorie.get(y));
            }
        }
        for (int z = 0; z < profilesRTNCategorie.size(); z++) {
            if (profilesRTNCategorie.get(z).getNew_features().getFinal_best_profile().contains("PHP/Symfony")
                  ) {

                profilesRTNCProPhpSymphony.add(profilesRTNCategorie.get(z));
            }
        }

        List<Profile> profilesRTNCProfile = new ArrayList<>();

        //  String profile1 =profile;

        //if (profile.contains("/")){
        // String profile1=profile.replace(" ","/") ;
        // }

        if (profile.equals("Technical Lead  Architecte JEE")) {
            profilesRTNCProfile = profilesRTNCProTechnicalLead;
        }
        if (profile.equals("JAVA JEE")) {
            profilesRTNCProfile = profilesRTNCProJavaJee;
        }
        if (profile.equals("DRUPAL")) {
            profilesRTNCProfile = profilesRTNCProDupral;
        }
        if (profile.equals("Product Owner")) {
            profilesRTNCProfile = profilesRTNCProProductOwner;
        }
        if (profile.equals("Web Back-End")) {
            profilesRTNCProfile = profilesRTNCProWebBackEnd;
        }
        if (profile.equals("FullStack JS")) {
            profilesRTNCProfile = profilesRTNCProFullStackJs;
        }
       if (profile.equals("Embarque Middleware")) {
            profilesRTNCProfile = profilesRTNCProEmbarqueMiddleWare;
        }
        if (profile.equals("PHP Symfony")) {
            profilesRTNCProfile = profilesRTNCProPhpSymphony;
        }
        if (profile.equals("Indefinie")) {
            profilesRTNCProfile = profilesRTNCategorie;
        }
///////////////////////////////////////////////////regex////////////////////////////////////////////////////

        Criteria criteria = new Criteria();
        Criteria criteria1 = new Criteria();


        List<Profile> profilesRTNCProfileTermes = new ArrayList<>();

        if (termes.equals("Indefinie")) {
            profilesRTNCProfileTermes = profilesRTNCProfile;

        } else {


            //  boolean existInProfile = false ;

            for (int q = 0; q < profilesRTNCProfile.size(); q++) {

                boolean existWord = false;


                criteria = (Criteria.where("_id").is(profilesRTNCProfile.get(q).getId()))
                        .andOperator(Criteria.where("headline").is(profilesRTNCProfile.get(q).getHeadline()).regex(".*" + termes));

                criteria1 = (Criteria.where("_id").is(profilesRTNCProfile.get(q).getId()))
                        .andOperator(Criteria.where("summary").is(profilesRTNCProfile.get(q).getSummary()).regex(".*" + termes));

                Query query = new Query();
                query.addCriteria(criteria);

                Query query1 = new Query();
                query1.addCriteria(criteria1);

                List<Profile> profileQuery = sequenceGeneratorServiceProfile.getMongoOperations().find(query, Profile.class);
                List<Profile> profileQuery1 = sequenceGeneratorServiceProfile.getMongoOperations().find(query1, Profile.class);

                if (profileQuery.size() != 0 || profileQuery1.size() != 0) {
                    existWord = true;
                }


                if (existWord) {
                    profilesRTNCProfileTermes.add(profilesRTNCProfile.get(q));
                }


            }
        }
        return profilesRTNCProfileTermes;

    }


/*
    public  String charTo(int i, String name){
        String nameReturn ;
        if(i<=name.length()){


        }

    }*/

    @GetMapping(value = "/SearchName/{name}")
    public List<Profile> SearchName(@PathVariable String name) {
        List<Profile> profilesNames = new ArrayList<>();

        List<Profile> profiles = profileRepository.findAll();
        Criteria criteria = new Criteria();


        if (name.equals("Indefinie")) {
            profilesNames = profiles;

        } else {

            for (int q = 0; q < profiles.size(); q++) {

                boolean existWord = false;


                criteria = (Criteria.where("_id").is(profiles.get(q).getId()))
                        .andOperator(Criteria.where("name").is(profiles.get(q).getName()).regex(".*" + name));

                Query query = new Query();
                query.addCriteria(criteria);

                List<Profile> profileQuery = sequenceGeneratorServiceProfile.getMongoOperations().find(query, Profile.class);


                if (profileQuery.size() != 0) {
                    existWord = true;
                }


                if (existWord) {
                    profilesNames.add(profiles.get(q));
                }
            }
        }
        return profilesNames;
    }


    @GetMapping(value = "/SearchSimilarProfiles/{profile}/{termes}")
    public List<Profile> SearchSimilarProfiles(@PathVariable String profile, @PathVariable String termes) {

        List<Profile> allProfiles = profileRepository.findAll();


        List<Profile> profilesRTNCProTechnicalLead = new ArrayList<>();
        List<Profile> profilesRTNCProJavaJee = new ArrayList<>();
        List<Profile> profilesRTNCProDupral = new ArrayList<>();
        List<Profile> profilesRTNCProProductOwner = new ArrayList<>();
        List<Profile> profilesRTNCProWebBackEnd = new ArrayList<>();
        List<Profile> profilesRTNCProFullStackJs = new ArrayList<>();
        List<Profile> profilesRTNCProEmbarqueMiddleWare = new ArrayList<>();
        List<Profile> profilesRTNCProPhpSymphony = new ArrayList<>();


        for (int i = 0; i < allProfiles.size(); i++) {
            if (allProfiles.get(i).getNew_features().getFinal_best_profile().contains("Technical Lead/ Architecte JEE")
) {

                profilesRTNCProTechnicalLead.add(allProfiles.get(i));
            }
        }

        for (int j = 0; j < allProfiles.size(); j++) {
            if (allProfiles.get(j).getNew_features().getFinal_best_profile().contains("JAVA/JEE")
) {

                profilesRTNCProJavaJee.add(allProfiles.get(j));
            }
        }
        for (int e = 0; e < allProfiles.size(); e++) {
            if (allProfiles.get(e).getNew_features().getFinal_best_profile().contains("DRUPAL")
                  ) {

                profilesRTNCProDupral.add(allProfiles.get(e));
            }
        }
        for (int f = 0; f < allProfiles.size(); f++) {
            if (allProfiles.get(f).getNew_features().getFinal_best_profile().contains("Product Owner")
) {

                profilesRTNCProProductOwner.add(allProfiles.get(f));
            }
        }
        for (int g = 0; g < allProfiles.size(); g++) {
            if (allProfiles.get(g).getNew_features().getFinal_best_profile().contains("Web Back-End")
) {

                profilesRTNCProWebBackEnd.add(allProfiles.get(g));
            }
        }
       for (int x = 0; x < allProfiles.size(); x++) {
            if (allProfiles.get(x).getNew_features().getFinal_best_profile().contains("FullStack JS")
) {

                profilesRTNCProFullStackJs.add(allProfiles.get(x));
            }
        }
        for (int y = 0; y < allProfiles.size(); y++) {
            if (allProfiles.get(y).getNew_features().getFinal_best_profile().contains("Embarqué Middleware")
) {

                profilesRTNCProEmbarqueMiddleWare.add(allProfiles.get(y));
            }
        }
       for (int z = 0; z < allProfiles.size(); z++) {
            if (allProfiles.get(z).getNew_features().getFinal_best_profile().contains("PHP/Symfony")
) {

                profilesRTNCProPhpSymphony.add(allProfiles.get(z));
            }
        }

        List<Profile> chosenPofile = new ArrayList<>();


        if (profile.equals("Technical Lead  Architecte JEE")) {
            chosenPofile = profilesRTNCProTechnicalLead;
        }
        if (profile.equals("JAVA JEE")) {
            chosenPofile = profilesRTNCProJavaJee;
        }
        if (profile.equals("DRUPAL")) {
            chosenPofile = profilesRTNCProDupral;
        }
        if (profile.equals("Product Owner")) {
            chosenPofile = profilesRTNCProProductOwner;
        }
        if (profile.equals("Web Back-End")) {
            chosenPofile = profilesRTNCProWebBackEnd;
        }
        if (profile.equals("FullStack JS")) {
            chosenPofile = profilesRTNCProFullStackJs;
        }
        if (profile.equals("Embarque Middleware")) {
            chosenPofile = profilesRTNCProEmbarqueMiddleWare;
        }
        if (profile.equals("PHP Symfony")) {
            chosenPofile = profilesRTNCProPhpSymphony;
        }
        if (profile.equals("Indefinie")) {
            chosenPofile = allProfiles;
        }
///////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////

        Criteria criteria = new Criteria();
        Criteria criteria1 = new Criteria();

        List<Profile> chosenProfileTermes = new ArrayList<>();

        if (termes.equals("Indefinie")) {
            chosenProfileTermes = chosenPofile;

        } else {


            //  boolean existInProfile = false ;

            for (int q = 0; q < chosenPofile.size(); q++) {

                boolean existWord = false;


                criteria = (Criteria.where("_id").is(chosenPofile.get(q).getId()))
                        .andOperator(Criteria.where("headline").is(chosenPofile.get(q).getHeadline()).regex(".*" + termes));

                criteria1 = (Criteria.where("_id").is(chosenPofile.get(q).getId()))
                        .andOperator(Criteria.where("summary").is(chosenPofile.get(q).getSummary()).regex(".*" + termes));

                Query query = new Query();
                query.addCriteria(criteria);

                Query query1 = new Query();
                query1.addCriteria(criteria1);

                List<Profile> profileQuery = sequenceGeneratorServiceProfile.getMongoOperations().find(query, Profile.class);
                List<Profile> profileQuery1 = sequenceGeneratorServiceProfile.getMongoOperations().find(query1, Profile.class);

                if (profileQuery.size() != 0 || profileQuery1.size() != 0) {
                    existWord = true;
                }


                if (existWord) {
                    chosenProfileTermes.add(chosenPofile.get(q));
                }


            }
        }

        return chosenProfileTermes ;
    }

}

