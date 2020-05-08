package com.wsourcing.Services.Profiles.resource;



import ch.qos.logback.core.spi.AbstractComponentTracker;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoClients;
import com.sun.org.apache.xpath.internal.operations.Bool;
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
                    /*|| profilesRTNCategorie.get(i).getHeadline().contains("ingénieur")
                    || profilesRTNCategorie.get(i).getHeadline().contains("engineer")
                    || profilesRTNCategorie.get(i).getHeadline().contains("Engineer")  */) {

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
        if(profile.equals("Indefinie")){
            profilesRTNCProfile = profilesRTNCategorie ;
        }
///////////////////////////////////////////////////regex////////////////////////////////////////////////////

        Criteria criteria = new Criteria();

        List<String> words   = new ArrayList<>();

        words.add("IOS") ;
        words.add("Full") ;
        words.add("Stack") ;
        words.add("DBA") ;
        words.add("Data") ;
        words.add("scientist") ;
        words.add("developer") ;
        words.add("engineer") ;
        words.add("Manager") ;
        words.add("CSM") ;
        words.add("java") ;
        words.add("j2EE") ;
        words.add("J2EE") ;
        words.add("Java") ;
        words.add("JEE") ;
        words.add("jee") ;
        words.add("OCAJP8") ;
        words.add("Angular") ;
        words.add("Spring") ;
        words.add("chef") ;
        words.add("Chef") ;
        words.add("AngularJs") ;
        words.add("technique") ;
        words.add("DBA") ;
        words.add("Analyste") ;
        words.add("spring") ;



        List<Profile> profilesRTNCProfileTermes = new ArrayList<>();


      //  boolean existInProfile = false ;

        for (int q = 0; q < profilesRTNCProfile.size(); q++) {

            boolean existWord=false;

            for(int v =0;v<words.size();v++){

//String query1 = "$regex:(?=*." +words.get(v)+")" ;
          //  String query1 = "$regex:{ $in: [" +words.get(v) ;


//Pattern pattern = Pattern.compile(query1) ;
if(termes.equals(words.get(v))) {

   // { name: { $regex: /acme.*corp/i, $nin: [ 'acmeblahcorp' ] } }

//String pattern= "$regex: '"+termes+"', $nin: ['"+profilesRTNCProfile.get(q).getHeadline()+"']" ;

    criteria = (Criteria.where("_id").is(profilesRTNCProfile.get(q).getId()))
            .andOperator(Criteria.where("headline").is(profilesRTNCProfile.get(q).getHeadline()).regex(".*"+termes));

    Query query = new Query();
    query.addCriteria(criteria);

    List<Profile> profiles = sequenceGeneratorServiceProfile.getMongoOperations().find(query, Profile.class);

    if (profiles.size() != 0) {
        existWord = true;
    }
}
            }
            if(existWord){
                profilesRTNCProfileTermes.add(profilesRTNCProfile.get(q)) ;
            }
            }



        return profilesRTNCProfileTermes;

    }
}

