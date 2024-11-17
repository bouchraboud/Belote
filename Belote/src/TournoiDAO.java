import java.sql.Connection;
import java.util.List;

public interface TournoiDAO {
    void addTournoi(Tournoi tournoi);
    public void creerTournoi(String nom, int statut, int id);
    Tournoi getTournoiById(int id);
    List<Tournoi> getAllTournois();
    void updateTournoi(Tournoi tournoi);
    void deleteTournoi(int id);
}
