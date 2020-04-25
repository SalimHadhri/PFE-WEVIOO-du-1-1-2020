package com.wsourcing.Services.Searches.resource;


import com.wsourcing.Services.Accounts.exception.AccountNotFoundException;
import com.wsourcing.Services.Accounts.model.Account;
import com.wsourcing.Services.Searches.exception.SearchNotFoundException;
import com.wsourcing.Services.Searches.model.Search;
import com.wsourcing.Services.Searches.repository.DatabaseSequenceSearchRepository;
import com.wsourcing.Services.Searches.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/searches")
public class SearchController {


    @Autowired
    private SearchRepository searchRepository ;
    @Autowired
    private DatabaseSequenceSearchRepository databaseSequenceSearchRepository;

    public SearchController(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    @CrossOrigin()
    @PostMapping(value = "/addSearch")
    public void addSearch(@Valid @RequestBody Search search) {
        searchRepository.save(search);
    }



    @CrossOrigin(origins = "https://localhost:4200/**")
    @GetMapping(value = "/listSearches")
    public List<Search> getAllSearches() {
        // LOGGER.info("findAll");
        return searchRepository.findAll();

    }

    @GetMapping(value = "/findSearch/{id}")
    public Search findSearchById(@PathVariable int id) throws SearchNotFoundException {
        Search search= searchRepository.findById(id);
        if (search==null) throw new SearchNotFoundException("the search with id "+id+" don't exist") ;
        return search ;
    }

    @DeleteMapping(value = "/deleteSearch/{id}")
    public void deleteSearchById(@PathVariable int id) throws SearchNotFoundException{
        Search search = searchRepository.findById(id);
        if (search==null) throw new SearchNotFoundException("the search with id "+id+" don't exist") ;
        searchRepository.deleteById((int) search.getId());
    }

    @PutMapping(value = "/updateSearch/{id}")
    public void updateSearch(@PathVariable int id, @RequestBody Search search) throws SearchNotFoundException{
        Search search1 = searchRepository.findById(id) ;
        if (search1 == null) {
            throw new SearchNotFoundException("the search with id "+id+" don't exist") ;
        } else {
            search.setId(search1.getId());
            searchRepository.save(search);
        }
    }

    @PutMapping(value = "/StartOrStopSearch/{id}")
    public void StartOrStopSearch(@PathVariable int id) throws SearchNotFoundException {

        Search search = searchRepository.findById(id) ;
        if (search == null) {
            throw new SearchNotFoundException("the account with id "+id+" don't exist") ;
        }
        if(search.isHalted() == true){
            search.setHalted(false);
        }
        else{
            search.setHalted(true);
        }
        searchRepository.save(search);
    }

}
