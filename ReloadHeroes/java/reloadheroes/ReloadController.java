package reloadheroes;
 
import java.util.UUID;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
 
@Controller
public class ReloadController {
 
    @Autowired
    private ReloadStatusRepository reloadStatusRepository;
 
    @Autowired
    private HttpSession session;
 
    @RequestMapping("*")
    public String reload(Model model) {
        ReloadStatus reload = (ReloadStatus) session.getAttribute("status");
        
        if (reload == null) {
            reload = new ReloadStatus();
            reload.setName(session.getId());
            reload.setReloads(1);
        } else {    
            int reloads = reloadStatusRepository.findByName(session.getId()).getReloads();
            reloads++;
            reload.setReloads(reloads);     
        }
       
        reloadStatusRepository.save(reload);
        session.setAttribute("status", reload);
        
        model.addAttribute("status", reload);
        
        Pageable pageable = PageRequest.of(0, 5, Sort.by("reloads").descending());
 
        model.addAttribute("scores", reloadStatusRepository.findAll(pageable));
        return "index";
    }
}