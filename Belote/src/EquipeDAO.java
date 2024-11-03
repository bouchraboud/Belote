import java.util.List;

public interface EquipeDAO {
	
	// Ajoute une nouvelle équipe à la base de données.
    void addEquipe(Equipe equipe);
    
    
    // Récupère une équipe par son identifiant.
    Equipe getEquipeById(int id);
    
    
    // Récupère toutes les équipes d'un tournoi spécifique.
    List<Equipe> getAllEquipes(int tournoiId);
    
    
    // Met à jour les informations d'une équipe existante dans la base de données.
    void updateEquipe(Equipe equipe);
    
    
    // Supprime une équipe de la base de données en fonction de son identifiant.
    void deleteEquipe(int id);
}
