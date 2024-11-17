import java.sql.*;
import java.util.List;

public class TournoiService {

    private TournoiDAO tournoiDAO;

    public TournoiService(Connection connection) {
        this.tournoiDAO = new TournoiDAOImpl();
    }

    public void createNewTournoi(String nom, int statut, int id) {
        tournoiDAO.creerTournoi(nom, statut, id);
    }

    public Tournoi getTournoiById(int id) {
        return tournoiDAO.getTournoiById(id);
    }

    public void updateTournoiInfo(Tournoi tournoi) {
        tournoiDAO.updateTournoi(tournoi);
    }

    public void removeTournoi(int id) {
        tournoiDAO.deleteTournoi(id);
    }

    public List<Tournoi> getAllTournois() {
        return tournoiDAO.getAllTournois();
    }
}
