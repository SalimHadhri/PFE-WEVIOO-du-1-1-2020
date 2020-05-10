package com.wsourcing.Services.Accounts.resource;


import com.wsourcing.Services.Accounts.model.Account;
import com.wsourcing.Services.Accounts.repository.AccountRepository;
import com.wsourcing.Services.Accounts.repository.DatabaseSequenceRepository;
import com.wsourcing.Services.security.SecurityTokenConfig;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;


import com.wsourcing.Services.Accounts.exception.AccountNotFoundException;

import javax.validation.Valid;
import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/accounts")
public class AccountController {


  //  private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private DatabaseSequenceRepository databaseSequenceRepository;





    public AccountController() {
    }

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }





    @CrossOrigin()
    @PostMapping(value = "/addAccount")
    public void addAccount(@Valid @RequestBody Account account) throws AccountNotFoundException {
        if(account.getNb_scraping_actuel()> account.getNb_scraping_jour()){
            throw new AccountNotFoundException("The actual number scraping must not be greater than the number scraping of the day") ;
        }
        accountRepository.save(account);
    }



    @CrossOrigin(origins = "https://localhost:4200/**")
    @GetMapping(value = "/listAccounts")
    public  List<Account> getAllAccounts() {
       // LOGGER.info("findAll");
        return accountRepository.findAll();

    }

    @GetMapping(value = "/findAccount/{id}")
    public Account findAccountById(@PathVariable int id) throws AccountNotFoundException {
        Account account= accountRepository.findById(id);
        if (account==null) throw new AccountNotFoundException("the account with id "+id+" don't exist") ;
            return account ;
    }

    @DeleteMapping(value = "/deleteAccount/{id}")
    public void deleteAccountById(@PathVariable int id) throws AccountNotFoundException{
        Account account = accountRepository.findById(id);
        if (account==null) throw new AccountNotFoundException("the account with id "+id+" don't exist") ;
        accountRepository.deleteById((int) account.getId());
    }

    @PutMapping(value = "/updateAccount/{id}")
    public void updateAccount(@PathVariable int id, @RequestBody Account account) throws AccountNotFoundException{
        Account account1 = accountRepository.findById(id) ;
        if (account1 == null) {
            throw new AccountNotFoundException("the account with id "+id+" don't exist") ;
        } else {
            account.setId(account1.getId());
            accountRepository.save(account);
        }
    }


    @GetMapping(value = "/minAccount")
    public Account minAccount() {

        List<Account> AccFst = accountRepository.findAll();
        List<Account> AccEnMarche = new ArrayList<>();
        //accountRepository.deleteAll();
        for (int z = 0; z < AccFst.size(); z++) {
            if (AccFst.get(z).getEtat().equals("En Marche")) {
                AccEnMarche.add(AccFst.get(z));
            }
        }

        int sizeMarche = AccEnMarche.size();
        List<Account> AccMinActuelPositif = new ArrayList<>();
        for (int l = 0; l < sizeMarche; l++) {
            if (AccEnMarche.get(l).getNb_scraping_actuel() > 0) {
                AccMinActuelPositif.add(AccEnMarche.get(l));
            }
        }
        if (AccMinActuelPositif.size() != 0) {
            Account accMin = AccMinActuelPositif.get(0);
            int sizeMarche2 = AccMinActuelPositif.size();

            for (int l = 1; l < sizeMarche2; l++) {

                if (AccMinActuelPositif.get(l).getNb_scraping_jour() < accMin.getNb_scraping_jour()

                ) {
                    accMin = AccMinActuelPositif.get(l);
                }
            }
            for (int l = 1; l < sizeMarche2; l++) {

                if (AccMinActuelPositif.get(l).getNb_scraping_actuel() < accMin.getNb_scraping_actuel()

                ) {
                    accMin = AccMinActuelPositif.get(l);
                }
            }
            accMin.setNb_scraping_actuel(accMin.getNb_scraping_actuel() - 1);

            this.updateAccount((int) accMin.getId(), accMin);
            return accMin;
        }
        else
        {return null ;}
    }

    @GetMapping(value = "/nbrScrapingDone")
    public int nbrScrapingDone () {

        List<Account> accounts = accountRepository.findAll();
        int nbrDayScraping = 0 ;
        int nbrActualScraping = 0;

        for(int i=0 ; i<accounts.size();i++){

            nbrDayScraping = nbrDayScraping+accounts.get(i).getNb_scraping_jour();
            nbrActualScraping = nbrActualScraping + accounts.get(i).getNb_scraping_actuel();
        }
    return nbrDayScraping - nbrActualScraping ;
    }

    @GetMapping(value = "/nbrAccountsInWork")
    public int nbrAccountsInWork () {

        int nbrInWork = 0 ;
        List<Account> accounts = accountRepository.findAll();

        for(int i=0 ; i<accounts.size();i++){

           if(accounts.get(i).getEtat().equals("En Marche")){
               nbrInWork=nbrInWork+1 ;
           }
        }
    return nbrInWork;
    }


    @GetMapping(value = "/workingAccountsList")
    public  List<Account> workingAccountsList() {
        List<Account> accounts = accountRepository.findAll();
        List<Account> workingAccounts = new ArrayList<>();

        for (int i = 0 ; i<accounts.size();i++){
            if (accounts.get(i).getEtat().equals("En Marche")){
                workingAccounts.add(accounts.get(i));
            }

        }
        return workingAccounts;
    }


    @GetMapping(value = "/orderedNbrScrapingAccounts/{min}/{max}")
    public List<Account> orderedNbrScrapingAccounts(@PathVariable int min, @PathVariable int max) throws AccountNotFoundException{
    if(min==0){
         min=min+1 ;
        }
        if (min>max){
            throw new AccountNotFoundException(" the minimum of number scraping per day should not be greater then the maximum") ;
        }
        List<Account> accounts = accountRepository.findAll();
        List<Account> orderedAccounts = new ArrayList<>();

        for (int i = 0 ; i<accounts.size();i++){
            if(accounts.get(i).getNb_scraping_jour()>=min && accounts.get(i).getNb_scraping_jour()<=max){
                orderedAccounts.add(accounts.get(i)) ;
            }
        }
        return orderedAccounts ;
    }

    @PutMapping(value = "/updateStatus/{id}")
    public void updateStatus(@PathVariable int id) throws AccountNotFoundException {

        Account account = accountRepository.findById(id) ;
        if (account == null) {
            throw new AccountNotFoundException("the account with id "+id+" don't exist") ;
        }
        if(account.getEtat().equals("En Marche")){
            account.setEtat("En Arret");
        }
        else{
            account.setEtat("En Marche");
        }
        accountRepository.save(account);
    }

   //  List<Integer> ScrapingDays= new ArrayList<>(7);

    @Autowired
   private  SecurityTokenConfig securityTokenConfig ;

    public AccountController(AccountRepository accountRepository, SecurityTokenConfig securityTokenConfig) {
        this.accountRepository = accountRepository;
        this.securityTokenConfig = securityTokenConfig;
    }

    //int c =0;
    //NbrScrapingScheduled nbrScrapingScheduled=new NbrScrapingScheduled();

    @GetMapping(value = "/ScrapThiDay")
    public List<Integer> ScrapThiDay() {




        return securityTokenConfig.getScrapingDays();
       // nbrscrapin.ScrapEveryDay();
        //int newScrapedProfiles = nbrScrapingDone() ;
       //  z = nbrScrapingScheduled.ScrapEveryDay(c) ;
       // c=z ;
       // AccountController accountController=new AccountController() ;
       // int d = accountController.getD() ;
       // ScrapingDays.set(c,newScrapedProfiles) ;
        //One day
      //  nbrScrapingScheduled.ScrapEveryDay();
      //  return ScrapingDays ;

    }


}
