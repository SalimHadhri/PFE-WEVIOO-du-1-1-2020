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



    @GetMapping(value = "/SearchAll/{min}/{max}/{tunisia}")
    public List<Profile> SearchAll(@PathVariable int min, @PathVariable int max, @PathVariable int tunisia) {


        List<Profile> profilesRange = experienceRange(min, max);
        List<Profile> profilesTunisia = new ArrayList<>();
        for (int i = 0; i < profilesRange.size(); i++) {
            if (profilesRange.get(i).getNew_features().getInTunisia() == tunisia) {
                profilesTunisia.add(profilesRange.get(i));
            }
        }
        return profilesTunisia;
    }
}



