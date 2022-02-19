package com.wsourcing.Services.Accounts.resource;


import com.sun.mail.util.MailSSLSocketFactory;
import com.wsourcing.Services.Accounts.model.Account;
import com.wsourcing.Services.Accounts.model.AccountLiatExpred;
import com.wsourcing.Services.Accounts.repository.AccountRepository;
import com.wsourcing.Services.Accounts.repository.DatabaseSequenceRepository;
import com.wsourcing.Services.security.SecurityTokenConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.wsourcing.Services.Accounts.exception.AccountNotFoundException;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.*;

//Controller related to our Account class
@CrossOrigin()
@RestController
@RequestMapping("/accounts")
public class AccountController {



    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private DatabaseSequenceRepository databaseSequenceRepository;


    public AccountController() {
    }

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    //Add an Account to database
    @CrossOrigin()
    @PostMapping(value = "/addAccount")
    public void addAccount(@Valid @RequestBody Account account) throws AccountNotFoundException {
        if (account.getNb_scraping_actuel() > account.getNb_scraping_jour()) {
            throw new AccountNotFoundException("The actual number scraping must not be greater than the number scraping of the day");
        }
        accountRepository.save(account);
    }


    //Show all Accounts stored in our mongoDB database
    @CrossOrigin(origins = "https://localhost:4200/**")
    @GetMapping(value = "/listAccounts")
    public List<Account> getAllAccounts() {
        // LOGGER.info("findAll");
        List<Account> Accounts = accountRepository.findAll();
        List<Account> accountsLiatnotExpired =  new ArrayList<>();
        for (int i =0 ; i<Accounts.size();i++){

            if(!Accounts.get(i).isLiatExpired()){
                accountsLiatnotExpired.add(Accounts.get(i)) ;
            }
        }
        return accountsLiatnotExpired ;

    }

    //Find an Account from database according to his id
    @GetMapping(value = "/findAccount/{id}")
    public Account findAccountById(@PathVariable int id) throws AccountNotFoundException {
        Account account = accountRepository.findById(id);
        if (account == null) throw new AccountNotFoundException("the account with id " + id + " don't exist");
        return account;
    }

    //Delete an Account by his id
    @DeleteMapping(value = "/deleteAccount/{id}")
    public void deleteAccountById(@PathVariable int id) throws AccountNotFoundException {
        Account account = accountRepository.findById(id);
        if (account == null) throw new AccountNotFoundException("the account with id " + id + " don't exist");
        accountRepository.deleteById((int) account.getId());
    }

    // Update an Account with the id == id
    // New Account data are given on a form
    @PutMapping(value = "/updateAccount/{id}")
    public void updateAccount(@PathVariable int id, @RequestBody Account account) throws AccountNotFoundException {
        Account account1 = accountRepository.findById(id);
        if (account1 == null) {
            throw new AccountNotFoundException("the account with id " + id + " don't exist");
        } else {
            account.setId(account1.getId());
            accountRepository.save(account);
        }
    }


    /*Return : Account in work ( "En Marche" ) with these conditions:
    => positif nb_scraping_actuel
    => minimum nb_scraping_jour
    => minimum nb_scraping_actuel
    */

    //and then decrease the nb_scraping_actuel by one => Account used for scraping one time

    /*MECHANISM : choose the account to use for scraping which will finish first ( nb_scraping_actuel==0) in order
     to get it out of the list of accounts that are going to be used for scraping
     GOAL : The first account which finish his working to reduce the list of accounts that are going to be used for scraping
     more quickly and then the process of using accounts for scraping will be more effective since we will have another account to use
     for scraping more quickly and so higher probability of finding the profile suitable for our enterprise in terms of time
    */

    /*NB: the nb_scraping_actuel will begin equal to nb_scraping_jour and then decrease each time we will use it for scraping
    in our case each time our function  minAccount return this account */
    @GetMapping(value = "/minAccount")
    public Account minAccount() {

        List<Account> AccFst = accountRepository.findAll();
        List<Account> AccEnMarche = new ArrayList<>();
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
        } else {
            return null;
        }
    }

    //Number scraping done this day sum of nb_scraping_jour-nb_scraping_actuel
    @GetMapping(value = "/nbrScrapingDone")
    public int nbrScrapingDone() {

        List<Account> accounts = accountRepository.findAll();
        int nbrDayScraping = 0;
        int nbrActualScraping = 0;

        for (int i = 0; i < accounts.size(); i++) {

            nbrDayScraping = nbrDayScraping + accounts.get(i).getNb_scraping_jour();
            nbrActualScraping = nbrActualScraping + accounts.get(i).getNb_scraping_actuel();
        }
        return nbrDayScraping - nbrActualScraping;
    }

    //Number scraping to do sum of nb_scraping_actuel
    @GetMapping(value = "/nbrScrapingToDo")
    public int nbrScrapingToDo() {

        List<Account> accounts = accountRepository.findAll();
        int nbrActualScraping = 0;

        for (int i = 0; i < accounts.size(); i++) {

            nbrActualScraping = nbrActualScraping + accounts.get(i).getNb_scraping_actuel();
        }
        return nbrActualScraping;
    }

    //Number accounts with "En Marche" state
    @GetMapping(value = "/nbrAccountsInWork")
    public int nbrAccountsInWork() {

        int nbrInWork = 0;
        List<Account> accounts = accountRepository.findAll();

        for (int i = 0; i < accounts.size(); i++) {

            if (accounts.get(i).getEtat().equals("En Marche")) {
                nbrInWork = nbrInWork + 1;
            }
        }
        return nbrInWork;
    }


    //List of accounts with "En Marche" state
    @GetMapping(value = "/workingAccountsList")
    public List<Account> workingAccountsList() {
        List<Account> accounts = accountRepository.findAll();
        List<Account> workingAccounts = new ArrayList<>();

        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getEtat().equals("En Marche")) {
                workingAccounts.add(accounts.get(i));
            }

        }
        return workingAccounts;
    }


    //Return the list of accounts with the nb_scraping_jour between two value ( min and max in our case )
    @GetMapping(value = "/orderedNbrScrapingAccounts/{min}/{max}")
    public List<Account> orderedNbrScrapingAccounts(@PathVariable int min, @PathVariable int max) throws AccountNotFoundException {
        if (min == 0) {
            min = min + 1;
        }
        if (min > max) {
            throw new AccountNotFoundException(" the minimum of number scraping per day should not be greater then the maximum");
        }
        List<Account> accounts = accountRepository.findAll();
        List<Account> orderedAccounts = new ArrayList<>();

        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getNb_scraping_jour() >= min && accounts.get(i).getNb_scraping_jour() <= max) {
                orderedAccounts.add(accounts.get(i));
            }
        }
        return orderedAccounts;
    }

    //Update the state of the account with the id equal to id  : "En Marche" to "En Arret" or the opposite
    //It will be used for stopping the account with nb_scraping_actuel==0 meaning that this account can't scrap no more
    //Or it will be used for adding this account in the list of accounts effective for scraping
    @PutMapping(value = "/updateStatus/{id}")
    public void updateStatus(@PathVariable int id) throws AccountNotFoundException {

        Account account = accountRepository.findById(id);
        if (account == null) {
            throw new AccountNotFoundException("the account with id " + id + " don't exist");
        }
        if (account.getEtat().equals("En Marche")) {
            account.setEtat("En Arret");
        } else {
            account.setEtat("En Marche");
        }
        accountRepository.save(account);
    }


    @Autowired
    private SecurityTokenConfig securityTokenConfig;

    public AccountController(AccountRepository accountRepository, SecurityTokenConfig securityTokenConfig) {
        this.accountRepository = accountRepository;
        this.securityTokenConfig = securityTokenConfig;
    }


    /*
       This function make us follow the number of scraping done for each day for a week
       When the week is over, we begin a different follow for the new week
       We used this function to create a graph which represent the flow of the number scraping done per day for a week
       With the right data given to the cron, we choose the delay by which the instructions in the function are carried out
       In this example, for testing, I put the delay equal to 1 min.  Each 1 minute we have another calculation of the number of scraping done
        */
    @GetMapping(value = "/ScrapThiDay")
    public List<Integer> ScrapThiDay() {
        return securityTokenConfig.getScrapingDays();

    }


    //Function used to send an email from a predefined email to a destination
    public void sendMail (String toEmail , String subject , String body) throws GeneralSecurityException, MessagingException {

        final String username = "hadhrisalim10@gmail.com";
        final String password = "FernandoFernando";

        Properties props = new Properties();

        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.localhost", "localhost");
        props.put("mail.smtp.host", "smtp.gmail.com");//
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.user", "hadhrisalim10@gmail.com") ;
        props.put("mail.smtp.password", "FernandoFernando") ;


        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustedHosts(new String[]{"smtp.gmail.com"});
        props.put("mail.smtp.ssl.socketFactory", sf);

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        session.setDebug(true) ;

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("hadhrisalim10@gmail.com"));//formBean.getString("fromEmail")
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail)); ;
        message.setSubject(subject);//formBean.getString(
        message.setText(body);

        Transport.send(message);

    }



    //Check if the account with the id given in the URL is expired or not
    /*
    if it's expired (liatExpired==true):
    =>fullfill the class AccountLiatExpred with the account expired data and return it
    =>send an email to the person who has this account to WARN him that his linkdin account can't be used for scraping no more
     */
    @GetMapping(value = "/ExpiredLiat/{id}")
    public AccountLiatExpred userAccountLiatExpired(@PathVariable int id) throws MessagingException, GeneralSecurityException, UnsupportedEncodingException {

        Account accountExpiredOrNot = accountRepository.findById(id);
        AccountLiatExpred AccountLiatExpiredDetails = new AccountLiatExpred();
        if (accountExpiredOrNot.isLiatExpired()) {

            AccountLiatExpiredDetails.setNameUserAccountExpired(accountExpiredOrNot.getName());
            AccountLiatExpiredDetails.setLiatExpired(accountExpiredOrNot.getLiat());
            AccountLiatExpiredDetails.setUrlLinkdin(accountExpiredOrNot.getUrl());
            sendMail(accountExpiredOrNot.getEmail(),"LIAT EXPIRED","Your liat ( "+ accountExpiredOrNot.getLiat() + " ) is expired!!") ;

        }
           else{

            AccountLiatExpiredDetails=null ;
        }
        return AccountLiatExpiredDetails ;

    }

    //Set liat expired for the account with id==id to true
    //Send an email to the person detaining this account to tell him that his account is expired (no more usable for scraping)
    @PutMapping(value = "/liatToExpired/{id}")
    public void liatToExpired (@PathVariable int id) throws GeneralSecurityException, MessagingException {

        Account account = accountRepository.findById(id) ;
        account.setLiatExpired(true);
        sendMail(account.getEmail(),"Changed liat",account.getName()+",: your liat is expired !");
        updateAccount(id,account) ;
    }


}



