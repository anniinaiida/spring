package reservations;
 
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
 
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
 
    @Query(value = "select a from Reservation a where a.reservationFrom >= :reservationFrom AND a.reservationTo <= :reservationTo", nativeQuery=true)
    List<Reservation> findOverlappingReservations(LocalDate reservationTo, LocalDate reservationFrom);
 
}