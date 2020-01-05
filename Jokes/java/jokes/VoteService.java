package jokes;
 
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
 
@Service
public class VoteService {
    @Autowired
        private VoteRepository voteRepository;
    
 
     public Vote findByJokeId(@RequestParam Long id) {
        Vote vote = voteRepository.findByJokeId(id);
        return vote;
    }
     
     @Transactional
     public void save(@RequestParam Vote value) {
         voteRepository.save(value);
     }
     
     @Transactional
     public void vote(@PathVariable Long id, @RequestParam String value) {
 
        Vote vote = this.voteRepository.findByJokeId(id);
        if (vote == null) {
            vote = new Vote(id, 0, 0);
        }
        if ("up".equals(value)) {
            vote.setUpVotes(vote.getUpVotes() + 1);
        } else if ("down".equals(value)) {
            vote.setDownVotes(vote.getDownVotes() + 1);
        }
        voteRepository.save(vote);
     }
}