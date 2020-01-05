package jokes;
 
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
 
@Controller
public class JokeController {
 
    @Autowired
    private JokeService jokeService;
    
    @Autowired
    private VoteService voteService;
 
    @GetMapping("/jokes")
    public String getRandom(Model model) {
        Joke joke = jokeService.getRandomJoke();
        model.addAttribute("joke", joke);
 
        Long jokeId = joke.getId();
 
        this.voteService.findByJokeId(jokeId);
        
        if (this.voteService.findByJokeId(jokeId) == null) {
            Vote v = new Vote(jokeId, 0, 0);
            this.voteService.save(v);
        }
        model.addAttribute("vote", this.voteService.findByJokeId(jokeId));
        return "jokes";
    }
 
    @Transactional
    @PostMapping("/jokes/{id}/vote")
    public String vote(@PathVariable Long id, @RequestParam String value) {
        this.voteService.vote(id, value);
        return "redirect:/jokes";
    }
 
}