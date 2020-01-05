package reservations;
 
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
 
@Controller
public class ReservationController {
 
    @Autowired
    private AccountRepository accountRepository;
 
    @Autowired
    private ReservationRepository reservationRepository;
 
    @GetMapping("/reservations")
    public String home(Model model) {
        model.addAttribute("reservations", reservationRepository.findAll());
 
        return "reservations";
    }
 
    @PostMapping("/reservations")
    @Transactional
    public String addReservation(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate reservationFrom,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate reservationTo) {
        if (reservationFrom.isAfter(reservationTo)) {
            return "redirect:/reservations";
        }
        
        System.out.println(reservationRepository.findOverlappingReservations(reservationTo, reservationFrom));
 
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account user = accountRepository.findByUsername(username);
 
        Reservation reservation = new Reservation();
 
        reservation.setReservationFrom(reservationFrom);
        reservation.setReservationTo(reservationTo);
        reservation.setUser(user);
 
        reservationRepository.save(reservation);
 
        return "redirect:/reservations";
    }
 
}