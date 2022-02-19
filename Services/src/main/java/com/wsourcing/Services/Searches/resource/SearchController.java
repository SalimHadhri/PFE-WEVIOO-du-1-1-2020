package com.wsourcing.Services.Searches.resource;


import com.wsourcing.Services.Searches.exception.SearchNotFoundException;
import com.wsourcing.Services.Searches.model.Search;
import com.wsourcing.Services.Searches.repository.DatabaseSequenceSearchRepository;
import com.wsourcing.Services.Searches.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


//Controller related to our Search class
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

    //Add a Search to database
    @CrossOrigin()
    @PostMapping(value = "/addSearch")
    public void addSearch(@Valid @RequestBody Search search) {
        searchRepository.save(search);
    }


    //Show all Searchs stored in our mongoDB database
    @CrossOrigin(origins = "https://localhost:4200/**")
    @GetMapping(value = "/listSearches")
    public List<Search> getAllSearches() {
        // LOGGER.info("findAll");
        return searchRepository.findAll();

    }

    //Find a Search from database according to his id
    @GetMapping(value = "/findSearch/{id}")
    public Search findSearchById(@PathVariable int id) throws SearchNotFoundException {
        Search search= searchRepository.findById(id);
        if (search==null) throw new SearchNotFoundException("the search with id "+id+" don't exist") ;
        return search ;
    }

    //Delete a Search by his id
    @DeleteMapping(value = "/deleteSearch/{id}")
    public void deleteSearchById(@PathVariable int id) throws SearchNotFoundException{
        Search search = searchRepository.findById(id);
        if (search==null) throw new SearchNotFoundException("the search with id "+id+" don't exist") ;
        searchRepository.deleteById((int) search.getId());
    }

    // Update a Search with the id == id
    // New Search data are given on a form
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

    //Start or stop the search on this organism
    //make isHalted true or false by reversing his actual state
    @PutMapping(value = "/StartOrStopSearch/{id}")
    public void StartOrStopSearch(@PathVariable int id) throws SearchNotFoundException {

        Search search = searchRepository.findById(id) ;
        if (search == null) {
            throw new SearchNotFoundException("the search with id "+id+" don't exist") ;
        }
        if(search.isHalted()){
            search.setHalted(false);
        }
        else{
            search.setHalted(true);
        }
        searchRepository.save(search);
    }

    //Return the list of the searches for this organism
    public List<Search> findSearchList(String organisme){

        List<Search> searches = searchRepository.findAll();
        List<Search> seachesOrganisme = new ArrayList<>();

        for (int i =0 ; i<searches.size();i++){

            if ( searches.get(i).getOrganisme().equals(organisme)){
                seachesOrganisme.add(searches.get(i));
            }
        }
        return seachesOrganisme ;
    }

    //Return the prioritised search and not halted for an organism depending on his urgency data
    @GetMapping(value = "/findPrioritisedSearch/{organisme}")
    public Search findPrioritisedSearch(@PathVariable String organisme) throws SearchNotFoundException {

        List<Search> searches = this.findSearchList(organisme) ;

        if (searches == null) {
            throw new SearchNotFoundException("No searches for this organism") ;
        }

        List<Search> seachesInWork = new ArrayList<>();

        for (int i =0 ; i<searches.size();i++){

            if (!searches.get(i).isHalted()){
                seachesInWork.add(searches.get(i)) ;
            }
        }

        Search search = seachesInWork.get(0);
        int urgency = seachesInWork.get(0).getUrgency() ;

        for (int i =1 ; i<seachesInWork.size();i++){

            if (seachesInWork.get(i).getUrgency()>urgency){
                urgency= seachesInWork.get(i).getUrgency() ;
                search= seachesInWork.get(i) ;
            }
        }

        return search ;
    }
}
