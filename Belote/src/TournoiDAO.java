import java.util.List;

public interface TournoiDAO {
    void addTournoi(Tournoi tournoi);
    Tournoi getTournoiById(int id);
    List<Tournoi> getAllTournois();
    void updateTournoi(Tournoi tournoi);
    void deleteTournoi(int id);
}
