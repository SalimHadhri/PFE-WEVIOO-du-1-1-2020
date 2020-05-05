package com.wsourcing.Services.Profiles.resource;



import ch.qos.logback.core.spi.AbstractComponentTracker;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.wsourcing.Services.Accounts.model.Account;
import com.wsourcing.Services.Profiles.exception.ProfileNotFoundException;
import com.wsourcing.Services.Profiles.model.Profile;
import com.wsourcing.Services.Profiles.model.Skill2;
import com.wsourcing.Services.Profiles.repository.ProfileRepository;
import io.micrometer.core.instrument.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/profiles")
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepository;

    public ProfileController(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
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
        if (min == 0) {
            min = min + 1;
        }
        if (min > max) {
            throw new ProfileNotFoundException(" the range of experiences does not match");
        }
        List<Profile> profiles = profileRepository.findAll();
        List<Profile> orderedProfiles = new ArrayList<>();

        for (int i = 0; i < profiles.size(); i++) {
            if (nbrJob(profiles.get(i)) >= min && nbrJob(profiles.get(i)) <= max) {
                orderedProfiles.add(profiles.get(i));
            }
        }
        return orderedProfiles;
    }


    @GetMapping(value = "/SearchAll/{min}/{max}/{tunisia}/{categorie}/{profile}")
    public List<Profile> SearchAll(@PathVariable int min, @PathVariable int max, @PathVariable int tunisia, @PathVariable int categorie
            , @PathVariable String profile) {


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
                    /*|| profilesRTNCategorie.get(i).getHeadline().contains("ingénieur")
                    || profilesRTNCategorie.get(i).getHeadline().contains("engineer")
                    || profilesRTNCategorie.get(i).getHeadline().contains("Engineer")  */)  {

                profilesRTNCProTechnicalLead.add(profilesRTNCategorie.get(i));
            }
        }

        for (int j = 0; j < profilesRTNCategorie.size(); j++) {
            if (profilesRTNCategorie.get(j).getNew_features().getFinal_best_profile().contains("JAVA/JEE")
                   /* || profilesRTNCategorie.get(j).getHeadline().contains("développeur")
                    || profilesRTNCategorie.get(j).getHeadline().contains("developer") ||
                    profilesRTNCategorie.get(j).getHeadline().contains("Developer")*/) {

                profilesRTNCProJavaJee.add(profilesRTNCategorie.get(j));
            }
        }
        for (int e = 0; e < profilesRTNCategorie.size(); e++) {
            if (profilesRTNCategorie.get(e).getNew_features().getFinal_best_profile().contains("DRUPAL")
                    /*|| profilesRTNCategorie.get(e).getHeadline().contains("technical manager")
                    || profilesRTNCategorie.get(e).getHeadline().contains("Technical Manager")
                    || profilesRTNCategorie.get(e).getHeadline().contains("Manageur technique") || profilesRTNCategorie.get(e).getHeadline().contains("manageur technique")
                    || profilesRTNCategorie.get(e).getHeadline().contains("Manageur Technique")*/) {

                profilesRTNCProDupral.add(profilesRTNCategorie.get(e));
            }
        }
        for (int f = 0; f < profilesRTNCategorie.size(); f++) {
            if (profilesRTNCategorie.get(f).getNew_features().getFinal_best_profile().contains("Product Owner")
                    /*|| profilesRTNCategorie.get(f).getHeadline().contains("chef de projet")
                    || profilesRTNCategorie.get(f).getHeadline().contains("Chef de Projet")
                    || profilesRTNCategorie.get(f).getHeadline().contains("Project manager") || profilesRTNCategorie.get(f).getHeadline().contains("project manager")
                    || profilesRTNCategorie.get(f).getHeadline().contains("Project Manager")*/) {

                profilesRTNCProProductOwner.add(profilesRTNCategorie.get(f));
            }
        }
        for (int g = 0; g < profilesRTNCategorie.size(); g++) {
            if (profilesRTNCategorie.get(g).getNew_features().getFinal_best_profile().contains("Web Back-End")
                    /*|| profilesRTNCategorie.get(g).getHeadline().contains("référent technique")
                    || profilesRTNCategorie.get(g).getHeadline().contains("Référent Technique")
                    || profilesRTNCategorie.get(g).getHeadline().contains("Technical referent") || profilesRTNCategorie.get(g).getHeadline().contains("technical referent")
                    || profilesRTNCategorie.get(g).getHeadline().contains("Technical Referent")*/) {

                profilesRTNCProWebBackEnd.add(profilesRTNCategorie.get(g));
            }
        }
        for (int x = 0; x < profilesRTNCategorie.size(); x++) {
            if (profilesRTNCategorie.get(x).getNew_features().getFinal_best_profile().contains("FullStack JS")
                    /*|| profilesRTNCategorie.get(g).getHeadline().contains("référent technique")
                    || profilesRTNCategorie.get(g).getHeadline().contains("Référent Technique")
                    || profilesRTNCategorie.get(g).getHeadline().contains("Technical referent") || profilesRTNCategorie.get(g).getHeadline().contains("technical referent")
                    || profilesRTNCategorie.get(g).getHeadline().contains("Technical Referent")*/) {

                profilesRTNCProFullStackJs.add(profilesRTNCategorie.get(x));
            }
        }
        for (int y = 0; y < profilesRTNCategorie.size(); y++) {
            if (profilesRTNCategorie.get(y).getNew_features().getFinal_best_profile().contains("Embarqué Middleware")
                    /*|| profilesRTNCategorie.get(g).getHeadline().contains("référent technique")
                    || profilesRTNCategorie.get(g).getHeadline().contains("Référent Technique")
                    || profilesRTNCategorie.get(g).getHeadline().contains("Technical referent") || profilesRTNCategorie.get(g).getHeadline().contains("technical referent")
                    || profilesRTNCategorie.get(g).getHeadline().contains("Technical Referent")*/) {

                profilesRTNCProEmbarqueMiddleWare.add(profilesRTNCategorie.get(y));
            }
        }
        for (int z = 0; z < profilesRTNCategorie.size(); z++) {
            if (profilesRTNCategorie.get(z).getNew_features().getFinal_best_profile().contains("PHP/Symfony")
                    /*|| profilesRTNCategorie.get(g).getHeadline().contains("référent technique")
                    || profilesRTNCategorie.get(g).getHeadline().contains("Référent Technique")
                    || profilesRTNCategorie.get(g).getHeadline().contains("Technical referent") || profilesRTNCategorie.get(g).getHeadline().contains("technical referent")
                    || profilesRTNCategorie.get(g).getHeadline().contains("Technical Referent")*/) {

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
        if (profile.equals("Embarqué Middleware")) {
            profilesRTNCProfile = profilesRTNCProEmbarqueMiddleWare;
        }
        if (profile.equals("PHP Symfony")) {
            profilesRTNCProfile = profilesRTNCProPhpSymphony;
        }
/*
        for(int i=0;i<profilesRTNCProfile.size();i++){

            List<Skill2> skill2s = profilesRTNCProfile.get(i).getSkills() ;
                for(int j=0;j<skill2s.size();j++){
                    Query query = new Query();
                    query.addCriteria(Criteria.where(skill2s.get(j).getName()).regex(skill1));
                    profilesRTNCProfileSkill1.add(profilesRTNCProfile.get(i)) ;
                }
            String X = profilesRTNCProfile.get(i).getSkills().
            Query query = new Query();
            query.addCriteria(Criteria.where(X).regex(skill1));

        }*/

      /*  List<Profile> profilesRTNCProfileSkill1 = new ArrayList<>();
        for (int i = 0; i < profilesRTNCProfile.size(); i++) {
            List<Skill2> skill2s = profilesRTNCProfile.get(i).getSkills();
           // boolean exist = false;

            for (int j = 0; j < skill2s.size(); j++) {
                Query query = new Query();
                query.addCriteria(Criteria.where(skill2s.get(j).getName()).regex(skill1));
                
            }*/
            // Query profile1 = query.addCriteria(Criteria.where(skill2s.get(j).getName()).regex(skill1));
            //.getClass().cast(profilesRTNCProfile.get(i)) ;

            //  Skill2 skill2 = new Skill2() ;
            //   skill2.setName(skill1);
            //  query.addCriteria(Criteria.where(profilesRTNCProfile.get(i).getSkills()).regex(skill2) ) ;
            // if(query.addCriteria(Criteria.where(profilesRTNCProfile.get(i).getSkills().get(0).getName()).regex(skill1)){
            //   exist=true ;
            // }
            //  }
            // if (exist==true){
            //    profilesRTNCProfileSkill1.add(profilesRTNCProfile.get(i)) ;
            // }
            // }
            //  Query query = new Query();
            //  query.addCriteria(Criteria.where("a").regex(skill1));

        //}

        return profilesRTNCProfile ;

    }

}

