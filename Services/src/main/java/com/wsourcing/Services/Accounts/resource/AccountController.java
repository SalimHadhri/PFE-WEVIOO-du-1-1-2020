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
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.*;

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
        if (account.getNb_scraping_actuel() > account.getNb_scraping_jour()) {
            throw new AccountNotFoundException("The actual number scraping must not be greater than the number scraping of the day");
        }
        accountRepository.save(account);
    }


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

    @GetMapping(value = "/findAccount/{id}")
    public Account findAccountById(@PathVariable int id) throws AccountNotFoundException {
        Account account = accountRepository.findById(id);
        if (account == null) throw new AccountNotFoundException("the account with id " + id + " don't exist");
        return account;
    }

    @DeleteMapping(value = "/deleteAccount/{id}")
    public void deleteAccountById(@PathVariable int id) throws AccountNotFoundException {
        Account account = accountRepository.findById(id);
        if (account == null) throw new AccountNotFoundException("the account with id " + id + " don't exist");
        accountRepository.deleteById((int) account.getId());
    }

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
        } else {
            return null;
        }
    }

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

    @GetMapping(value = "/nbrScrapingToDo")
    public int nbrScrapingToDo() {

        List<Account> accounts = accountRepository.findAll();
        int nbrActualScraping = 0;

        for (int i = 0; i < accounts.size(); i++) {

            nbrActualScraping = nbrActualScraping + accounts.get(i).getNb_scraping_actuel();
        }
        return nbrActualScraping;
    }

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

    //  List<Integer> ScrapingDays= new ArrayList<>(7);
///////////////////////////////////////////////////////////
    @Autowired
    private SecurityTokenConfig securityTokenConfig;

    ///////////////////////////////////////////////////////////
    public AccountController(AccountRepository accountRepository, SecurityTokenConfig securityTokenConfig) {
        this.accountRepository = accountRepository;
        this.securityTokenConfig = securityTokenConfig;
    }

    //int c =0;
    //NbrScrapingScheduled nbrScrapingScheduled=new NbrScrapingScheduled();

    @GetMapping(value = "/ScrapThiDay")
    public List<Integer> ScrapThiDay() {
        return securityTokenConfig.getScrapingDays();

    }



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

    @PutMapping(value = "/liatToExpired/{id}")
    public void liatToExpired (@PathVariable int id) throws GeneralSecurityException, MessagingException {

        Account account = accountRepository.findById(id) ;
        account.setLiatExpired(true);
        sendMail(account.getEmail(),"Change your liat",account.getName()+", your liat is expired. Please change it!");

        updateAccount(id,account) ;
    }


}



