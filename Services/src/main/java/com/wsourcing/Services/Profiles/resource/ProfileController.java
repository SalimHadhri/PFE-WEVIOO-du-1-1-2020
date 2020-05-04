package com.wsourcing.Services.Profiles.resource;



import com.sun.org.apache.xpath.internal.operations.Bool;
import com.wsourcing.Services.Accounts.model.Account;
import com.wsourcing.Services.Profiles.exception.ProfileNotFoundException;
import com.wsourcing.Services.Profiles.model.Profile;
import com.wsourcing.Services.Profiles.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Profile> SearchAll(@PathVariable int min, @PathVariable int max, @PathVariable int tunisia,@PathVariable int categorie,@PathVariable String profile) {


        List<Profile> profilesRange = experienceRange(min, max);
        List<Profile> profilesRTunisia = new ArrayList<>();
        for (int i = 0; i < profilesRange.size(); i++) {
            if (profilesRange.get(i).getNew_features().getInTunisia() == tunisia) {
                profilesRTunisia.add(profilesRange.get(i));
            }
        }
        List<Profile> profilesRTNCategorie = new ArrayList<>();

        if(categorie==0){
            profilesRTNCategorie= profilesRTunisia ;
        }
        else{


        for (int i = 0; i < profilesRTunisia.size(); i++) {
            if (profilesRTunisia.get(i).getNew_features().getCateg_seniority() == categorie) {
                profilesRTNCategorie.add(profilesRTunisia.get(i));
            }
        }
        }

        List<Profile> profilesRTNCProIngenieur = new ArrayList<>();
        List<Profile> profilesRTNCProDeveloper = new ArrayList<>();
        List<Profile> profilesRTNCProManagerTechnique = new ArrayList<>();
        List<Profile> profilesRTNCProChefDeProjet = new ArrayList<>();
        List<Profile> profilesRTNCProReferencTechnique = new ArrayList<>();

        for(int i=0; i<profilesRTNCategorie.size();i++) {
            if (profilesRTNCategorie.get(i).getHeadline().contains("Ingénieur") || profilesRTNCategorie.get(i).getHeadline().contains("ingénieur")
                    || profilesRTNCategorie.get(i).getHeadline().contains("engineer")
                    || profilesRTNCategorie.get(i).getHeadline().contains("Engineer")) {

                profilesRTNCProIngenieur.add(profilesRTNCategorie.get(i));
            }
        }

            for(int j=0; j<profilesRTNCategorie.size();j++){
                if(profilesRTNCategorie.get(j).getHeadline().contains("Développeur") || profilesRTNCategorie.get(j).getHeadline().contains("développeur")
                        || profilesRTNCategorie.get(j).getHeadline().contains("developer")||profilesRTNCategorie.get(j).getHeadline().contains("Developer")){

                    profilesRTNCProDeveloper.add(profilesRTNCategorie.get(j)) ;
                }
        }
            for(int e=0; e<profilesRTNCategorie.size();e++){
                if(profilesRTNCategorie.get(e).getHeadline().contains("Technical manager") || profilesRTNCategorie.get(e).getHeadline().contains("technical manager")
                        || profilesRTNCategorie.get(e).getHeadline().contains("Technical Manager")
                        || profilesRTNCategorie.get(e).getHeadline().contains("Manageur technique")||profilesRTNCategorie.get(e).getHeadline().contains("manageur technique")
                        || profilesRTNCategorie.get(e).getHeadline().contains("Manageur Technique")){

                    profilesRTNCProManagerTechnique.add(profilesRTNCategorie.get(e)) ;
                }
            }
            for(int f=0; f<profilesRTNCategorie.size();f++){
                if(profilesRTNCategorie.get(f).getHeadline().contains("Chef de projet") || profilesRTNCategorie.get(f).getHeadline().contains("chef de projet")
                        || profilesRTNCategorie.get(f).getHeadline().contains("Chef de Projet")
                        || profilesRTNCategorie.get(f).getHeadline().contains("Project manager")||profilesRTNCategorie.get(f).getHeadline().contains("project manager")
                        || profilesRTNCategorie.get(f).getHeadline().contains("Project Manager")){

                    profilesRTNCProChefDeProjet.add(profilesRTNCategorie.get(f)) ;
                }
            }
            for(int g=0; g<profilesRTNCategorie.size();g++){
                if(profilesRTNCategorie.get(g).getHeadline().contains("Référent technique") || profilesRTNCategorie.get(g).getHeadline().contains("référent technique")
                        ||profilesRTNCategorie.get(g).getHeadline().contains("Référent Technique")
                        || profilesRTNCategorie.get(g).getHeadline().contains("Technical referent")||profilesRTNCategorie.get(g).getHeadline().contains("technical referent")
                        ||profilesRTNCategorie.get(g).getHeadline().contains("Technical Referent")){

                    profilesRTNCProReferencTechnique.add(profilesRTNCategorie.get(g)) ;
                }
            }
            List<Profile> profilesRTNCProfile = new ArrayList<>();
            if (profile.equals("Engineer")){
                profilesRTNCProfile=profilesRTNCProIngenieur ;
            }
            if (profile.equals("Developer")){
                profilesRTNCProfile=profilesRTNCProDeveloper ;
            }
            if (profile.equals("Technical Manager")){
                profilesRTNCProfile=profilesRTNCProManagerTechnique ;
            }
            if (profile.equals("Project Manager")){
                profilesRTNCProfile=profilesRTNCProChefDeProjet ;
            }
            if (profile.equals("Technical Referent")){
                profilesRTNCProfile=profilesRTNCProReferencTechnique ;
            }


        return profilesRTNCProfile;
    }
}



