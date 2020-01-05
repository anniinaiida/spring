package reservations;
 
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
 
@Controller
public class DefaultController {
 
    @Autowired
    private AccountRepository accountRepository;
 
    @Autowired
    private SecurityConfiguration securityConfiguration;
 
    @PostConstruct
    @Transactional
    public void init() {
 
        Account a1 = new Account();
        Account a2 = new Account();
 
        a1.setUsername("account1");
        a1.setPassword(securityConfiguration.passwordEncoder().encode("salasana"));
 
        a2.setUsername("account2");
        a2.setPassword(securityConfiguration.passwordEncoder().encode("salasana"));
 
        if (accountRepository.findByUsername(a1.getUsername()) == null) {
            accountRepository.save(a1);
        }
 
        if (accountRepository.findByUsername(a2.getUsername()) == null) {
            accountRepository.save(a2);
        }
 
    }
 
    @GetMapping("*")
    public String handleDefault() {
        return "redirect:/reservations";
    }
}