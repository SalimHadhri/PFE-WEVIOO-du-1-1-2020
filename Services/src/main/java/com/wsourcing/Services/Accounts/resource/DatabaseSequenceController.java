package com.wsourcing.Services.Accounts.resource;


import com.wsourcing.Services.Accounts.repository.DatabaseSequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


//Controller related to our DatabaseSequence class
@CrossOrigin()
@RestController
@RequestMapping("/sequence")
public class DatabaseSequenceController {


    @Autowired
    private DatabaseSequenceRepository databaseSequenceRepository ;

    public DatabaseSequenceController(DatabaseSequenceRepository databaseSequenceRepository) {
        this.databaseSequenceRepository = databaseSequenceRepository;
    }

    @DeleteMapping(value="/deleteAllSequences")
    public String deleteAllSequences(){
        databaseSequenceRepository.deleteAll();
        return "All sequences deleted" ;
    }


}
