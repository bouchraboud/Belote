import java.sql.*;
import java.util.List;

public class EquipeService {

    private EquipeDAO equipeDAO;

    // Constructeur pour initialiser l'instance de EquipeDAO
    public EquipeService(Connection connection) {
        this.equipeDAO = new EquipeDAOImpl(connection);
    }

    // Méthode pour créer et ajouter une nouvelle équipe
    public void createNewEquipe(String nomJ1, String nomJ2, int numEquipe, int tournoiId) {
        Equipe equipe = new Equipe(numEquipe, tournoiId, nomJ2, nomJ1); // Création de l'objet Equipe
        equipeDAO.addEquipe(equipe); // Appel de addEquipe pour ajouter l'équipe à la base de données
    }

    // Méthode pour récupérer une équipe par son ID
    public Equipe getEquipeById(int id) {
        return equipeDAO.getEquipeById(id);
    }

    // Méthode pour récupérer toutes les équipes d'un tournoi spécifique
    public List<Equipe> getAllEquipes(int tournoiId) {
        return equipeDAO.getAllEquipes(tournoiId); // Passer tournoiId pour récupérer les équipes liées au tournoi
    }

    // Méthode pour mettre à jour les informations d'une équipe
    public void updateEquipeInfo(Equipe equipe) {
        equipeDAO.updateEquipe(equipe);
    }

    // Méthode pour supprimer une équipe par son ID
    public void removeEquipe(int id) {
        equipeDAO.deleteEquipe(id);
    }
}

