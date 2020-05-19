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
        return accountRepository.findAll();

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



/*
    public void sendMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
*/


   // @Autowired
   // private JavaMailSender  javaMailSender ;



    @GetMapping(value = "/ExpiredLiat/{id}")
    public AccountLiatExpred userAccountLiatExpired(@PathVariable int id) throws MessagingException, GeneralSecurityException, UnsupportedEncodingException {

        Account accountExpiredOrNot = accountRepository.findById(id);
        AccountLiatExpred AccountLiatExpiredDetails = new AccountLiatExpred();
        if (accountExpiredOrNot.isLiatExpired()) {

            AccountLiatExpiredDetails.setNameUserAccountExpired(accountExpiredOrNot.getName());

            AccountLiatExpiredDetails.setLiatExpired(accountExpiredOrNot.getLiat());

            AccountLiatExpiredDetails.setUrlLinkdin(accountExpiredOrNot.getUrl());


/*
            final MimeMessage mail = javaMailSender.createMimeMessage();
            final MimeMessageHelper helper = new MimeMessageHelper( mail, true );
            helper.setTo( accountExpiredOrNot.getEmail() );
            helper.setSubject( "Notification" );
            helper.setText( "text/html", "haha" );
            javaMailSender.send( mail );
*/
         /*   SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo("hadhrisalim@gmail.com");
            msg.setSubject("Liat expired");
            msg.setText("trhr");
            this.javaMailSender.send(msg);*/
/////////////////////////////////////////////////////////////////////////////////////



            final String username = "hadhrisalim10@gmail.com";
            final String password = "FernandoFernando";

            Properties props = new Properties();

            //Properties props = (Properties)System.getProperties().clone();
            //props.put("mail.transport.protocol", "smtps");
           // props.put("mail.protocol.ssl.trust", "*");

             props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.localhost", "localhost");
            props.put("mail.protocol.ssl.trust", "*");
            props.put("mail.smtp.host", "smtp.gmail.com");//
            props.put("mail.smtp.port", "587");//
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.debug", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.user", "hadhrisalim10@gmail.com") ;//
            props.put("mail.smtp.password", "FernandoFernando") ;//


            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.socketFactory", sf);

          //  props.put("mail.smtps.auth", "true");
          //  props.put("mail.smtp.starttls.enable", "false");
           // props.put("mail.smtps.debug", "true");


           //  props.put("mail.smtp.ssl.enable", "false");


         // props.put("mail.smtp.debug", "true");


          //  props.put("mail.smtp.socketFactory.port", "465");
           // props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
           // props.put("mail.smtp.socketFactory.fallback", "false") ;

            // props.put("mail.smtp.starttls.enable", "true");
             //props.put("mail.smtps.ssl.enable", "true");
            //props.put("mail.smtp.starttls.required ", "true");




            //props.put("mail.debug", "true");



            //Session session = Session.getInstance(props) ;


            //props.put("mail.smtp.socketFactory.port", 465) ;
            //props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory") ;
           // props.put("mail.smtp.socketFactory.fallback", "false") ;

                    //////////////////////////


            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            session.setDebug(true) ;
           // Session session = Session.getDefaultInstance(props);


                Message message = new MimeMessage(session);
               // message.setRecipients(Message.RecipientType.TO,new InternetAddress(accountExpiredOrNot.getEmail()));
            /*  */   message.setFrom(new InternetAddress("hadhrisalim10@gmail.com"));//formBean.getString("fromEmail")
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(accountExpiredOrNot.getEmail())); ;

            message.setSubject("Liat expired");//formBean.getString(
                message.setText("Your liat ( "+ accountExpiredOrNot.getLiat() + " is expired!!");


            Transport.send(message);

            //  message.setContent("Your liat ( "+ accountExpiredOrNot.getLiat() + " is expired!!","text/html");
//,InternetAddress.parse(accountExpiredOrNot.getEmail()

              /*  Transport transport=session.getTransport();////////////
                transport.connect("smtp.gmail.com",465 ,"salim hadhri","FernandoFernando");
                transport.sendMessage(message,message.getAllRecipients() );
                transport.close();
*/
            // Transport.send(message, "hadhrisalim10@gmail.com", "FernandoFernando");
            //transport.connect("hadhrisalim10@gmail.com", "FernandoFernando");

           // InternetAddress.parse(accountExpiredOrNot.getEmail())
               // transport.sendMessage(message, InternetAddress.parse(accountExpiredOrNot.getEmail()));//(message);
               // System.out.println("Done");
            //  Transport transport=session.getTransport();
            //transport.connect();
            //  transport.sendMessage(message, InternetAddress.parse(accountExpiredOrNot.getEmail()));//(message);


            //Transport.send(message, "hadhrisalim10@gmail.com", "FernandoFernando");
            /*Transport transport=session.getTransport("smtp");
            try
            {
                System.out.println("Sending...");
                 //Connect to Amazon SES using the SMTP username and password you specified above.
                transport.connect("smtp.gmail.com",465,"hadhrisalim10@gmail.com","FernandoFernando");
                System.out.println("connected");

                // Send the email.
                transport.sendMessage(message, message.getAllRecipients());
                System.out.println("Email sent!");
            }
            catch (Exception ex) {
                System.out.println("The email was not sent.");
                System.out.println("Error message: " + ex.getMessage());
            }
           finally
            {
                // Close and terminate the connection.
                transport.close();
            }*/
        }

           else{


            AccountLiatExpiredDetails=null ;
        }
        return AccountLiatExpiredDetails ;

    }


}



///////////////////////////////////////////////////////////////////////////////////////////////////////


/* String  d_email = "hadhrisalim8@gmail.com";
          String   d_uname = "hadhrisalim8" ;

                    String      d_password = "DkPB7D+DkPB7D+";
            String    d_host = "smtp.gmail.com" ;
            String      d_port  = "465"; //465,587
            String      m_to = accountExpiredOrNot.getEmail() ;
            String      m_subject = "Testing" ;
            String     m_text = "This is a test.";

            Properties props = new Properties() ;
            props.put("mail.smtp.user", d_email) ;
            props.put("mail.smtp.host",d_host ) ;
            props.put("mail.smtps.port", d_port) ;
            props.put("mail.smtp.starttls.enable","true") ;
            props.put("mail.smtp.debug", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.socketFactory.port", d_port);
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.fallback", "false");





            SMTPAuthenticator auth = new SMTPAuthenticator();
            Session session = Session.getInstance(props, auth) ;
            session.setDebug(true);

            MimeMessage msg = new MimeMessage(session) ;
            msg.setText(m_text) ;
            msg.setSubject(m_subject) ;
            msg.setFrom(new InternetAddress(d_email)) ;
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(m_to)) ;

            Transport transport = session.getTransport("smtps");
            transport.connect(d_host, 465, d_uname, d_password);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();*/
/*   Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

            //  @Autowired
            //   private JavaMailSender mailSender;

    private static class SMTPAuthenticator extends Authenticator
    {
        public PasswordAuthentication getPasswordAuthentication()
        {
            return new PasswordAuthentication("hadhrisalim8@gmail.com", "DkPB7D+DkPB7D+");
        }
    }


          Properties props = new Properties() ;

            props.put("mail.transport.protocol", "smtps");
                    props.put("mail.smtps.host", "smtp.gmail.com");
                    props.put("mail.smtps.auth", "true");
                    props.put("mail.smtps.starttls.enable","true") ;
                    props.put("mail.smtps.debug","true")   ;
                    props.put("mail.smtps.port", "465");

                    // props.put("mail.smtps.socketFactory.fallback", "false");
                    //     props.put("mail.smtps.quitwait", "false");

                    props.put("mail.smtps.ssl.enable", "true");

                    //props.put("mail.smtps.socketFactory.port", "465");
                    //props.put("mail.smtps.socketFactory.class",
                    //"javax.net.ssl.SSLSocketFactory");

                    // MailSSLSocketFactory sf = new MailSSLSocketFactory();
                    //   sf.setTrustedHosts("smtp.gmail.com");
                    // props.put("mail.smtps.ssl.socketFactory", sf);
                    props.put("mail.smtps.ssl.checkserveridentity", "true");
                    props.put("mail.smtps.ssl.trust", "*");



                    Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("hadhrisalim8@gmail.com", "DkPB7D+DkPB7D+");
        }
        });
        session.setDebug(true);
        MimeMessage message = new MimeMessage(session);
        message.setSender(new InternetAddress("hadhrisalim8@gmail.com"));
        message.setSubject("liat expired");
        message.setContent("liat expired", "text/plain");
        //     if (accountExpiredOrNot.getEmail().indexOf(',') > 0)
        //      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(accountExpiredOrNot.getEmail()));
        //      else
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(accountExpiredOrNot.getEmail()));

        //  587
        //Transport transport = session.getTransport(session.getProvider("smtps"));
        Transport transport = session.getTransport("smtps");
        //transport.connect("hadhrisalim8@gmail.com","DkPB7D+DkPB7D+");
        transport.connect("smtp.gmail.com",465,"hadhrisalim8@gmail.com","DkPB7D+DkPB7D+");
        // transport.connect(d_host, 587, d_uname, d_password);
        transport.sendMessage(message,message.getAllRecipients());
        //InternetAddress.parse(accountExpiredOrNot.getEmail())
        transport.close();
//Transport.send(message);*/










/*

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////



    String  d_email ="hadhrisalim8@gmail.com" ;
    String     d_uname = "hadhrisalim8" ;
    String     d_password = "DkPB7D+DkPB7D+" ;
    String     d_host = "smtp.gmail.com" ;
    String     d_port  = "465"; //465,587
    String     m_to = accountExpiredOrNot.getEmail()    ;
    String     m_subject = "Liat expired"  ;
    String     m_text = "Your liat is expired" ;


    ///////////////////////////////////////////////////


    Properties propsSSL = new Properties();
            propsSSL.put("mail.transport.protocol", "smtp");
                    propsSSL.put("mail.smtp.host", "smtp.gmail.com");
                    propsSSL.put("mail.smtp.auth", "true");
                    propsSSL.put("mail.smtps.ssl.checkserveridentity", "false");
                    propsSSL.put("mail.smtps.ssl.trust", "*");
                    propsSSL.put("mail.smtp.port", "465");
                    propsSSL.put("mail.smtp.starttls.enable","true") ;
                    propsSSL.put("mail.smtps.socketFactory.class", "javax.net.ssl.SSLSocketFactory") ;
                    // propsSSL.put("mail.smtps.user", "hadhrisalim8@gmail.com");
                    // propsSSL.put("mail.smtps.password", "DkPB7D+DkPB7D+");
                    propsSSL.put("mail.smtps.socketFactory.port", "465") ;

                    MailSSLSocketFactory sf = new MailSSLSocketFactory();
                    sf.setTrustAllHosts(true);
                    propsSSL.put("mail.smtps.ssl.socketFactory", sf);
                    propsSSL.setProperty("mail.smtps.ssl.enable", "true");


            Properties props = new Properties() ;

            props.put("mail.smtp.user", d_email) ;
            props.put("mail.smtp.host", d_host) ;
            props.put("mail.smtp.port", d_port);
            props.put("mail.smtp.starttls.enable","true") ;
            props.put("mail.smtp.debug", "true");
            props.put("mail.smtp.auth", "true") ;
            props.put("mail.smtp.socketFactory.port", "465") ;
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory") ;
            props.put("mail.smtp.socketFactory.fallback", "false") ;
          //  props.put("mail.smtp.ssl.trust", "*");
           // props.put("mail.smtp.ssl.checkserveridentity", "true");
            props.put("mail.smtps.ssl.checkserveridentity", "false");

           // props.put("mail.smtp.ssl.enable", "true");
           // props.put("mail.transport.protocol", "smtp");

            MailSSLSocketFactory sf = new MailSSLSocketFactory();

            sf.setTrustAllHosts(true);

            props.setProperty("mail.smtp.ssl.enable", "true");
            props.setProperty("mail.protocol.ssl.trust", "smtp.gmail.com");
            props.put("mail.smtps.ssl.socketFactory", sf);
            props.setProperty("mail.smtps.port", "465");

          //  MailSSLSocketFactory sf = new MailSSLSocketFactory();
            //sf.setTrustedHosts("smtp.gmail.com");
            //props.put("mail.smtp.ssl.socketFactory", sf);

          //  MailSSLSocketFactory socketFactory= new MailSSLSocketFactory();
          //  socketFactory.setTrustedHosts("smtp.gmail.com");
           // props.put("mail.smtp.ssl.socketFactory", socketFactory);

           // MailSSLSocketFactory socketFactory = new MailSSLSocketFactory();
            //socketFactory.setTrustedHosts(d_host);
            //socketFactory.setTrustAllHosts(true);
            //props.put("mail.smtps.socketFactory", socketFactory);


            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustedHosts("smtp.gmail.com");
            props.put("mail.smtp.ssl.enable", "true");
            // also use following for additional safety
            props.put("mail.smtp.ssl.socketFactory", sf);
            props.put("mail.smtp.ssl.checkserveridentity", "true");

                    // props.put("mail.smtps.ssl.trust", "*");


                    // props.put("mail.smtps.ssl.trust", "*");
                    //props.put("mail.smtps.ssl.checkserveridentity", "true");

                    //props.put("mail.smtps.ssl.checkserveridentity", "false");
                    //props.put("mail.smtps.ssl.trust", "*");



                    //  SMTPAuthenticator  auth = new SMTPAuthenticator();
                    // Session session = Session.getInstance(props, auth) ;


                    Session session = Session.getInstance(propsSSL,
                    new javax.mail.Authenticator() {
protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("hadhrisalim8@gmail.com", "DkPB7D+DkPB7D+");
        }
        });

        session.setDebug(true);

        //  Session session = Session.getInstance(propsSSL);

        Message msg = new MimeMessage(session) ;
        msg.setText(m_text) ;
        msg.setSubject(m_subject) ;
        msg.setFrom(new InternetAddress("hadhrisalim8@gmail.com")) ;
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(accountExpiredOrNot.getEmail())) ;


        Transport transport = session.getTransport("smtp");
        //transport.connect("hadhrisalim8@gmail.com","DkPB7D+DkPB7D+");
        transport.connect("smtp.gmail.com",465,"hadhrisalim8@gmail.com","DkPB7D+DkPB7D+");
        // transport.connect(d_host, 587, d_uname, d_password);
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();

        //  Transport.send(msg);

        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////
        else{
*/


/*

          //  final String username = "hadhrisalim8@gmail.com";
          //  final String password = "DkPB7D+DkPB7D+";
        Properties props = new Properties();
          /*  props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtps.ssl.checkserveridentity", "true");
            props.put("mail.smtps.ssl.trust", "*");
            props.put("mail.smtp.host", "server.com");
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.protocol", "smtp");

            props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.host", "smtp.gmail.com");
                    props.put("mail.smtp.port", "465");
                    props.put("mail.smtp.protocol", "smtp");
                    props.put("mail.smtp.starttls.enable", "true");
                    ///
                    // props.put("mail.smtp.ssl.checkserveridentity", "true");
                    //  props.put("mail.smtp.ssl.trust", "*");
                    ///
                    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                    props.put("mail.debug", "true");
                    props.put("mail.smtp.socketFactory.port", "465");
                    props.put("mail.smtp.user", "hadhrisalim10@gmail.com");
                    props.put("mail.smtp.password", "FernandoFernando");




                    //Session session = Session.getDefaultInstance(props);


                    Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("hadhrisalim10@gmail.com", "DkPB7D+DkPB7D+");
        }
        });

        try {
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("hadhrisalim10@gmail.com"));

        msg.setRecipients(  Message.RecipientType.TO,
        InternetAddress.parse(accountExpiredOrNot.getEmail()));
        msg.setSubject("Liat expired");
        msg.setSentDate(new Date());
        // set plain text message
        msg.setText("your liat has been expired");

        Transport.send(msg);
        } catch (MessagingException e) {
        throw new RuntimeException(e);
        }*/