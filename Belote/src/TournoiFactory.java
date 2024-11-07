import java.sql.Statement;

public interface TournoiFactory {

    /**
     * Crée un objet Tournoi basé sur le type de tournoi spécifié.
     * 
     * @param nt le nom du tournoi
     * @param st l'objet Statement pour exécuter des requêtes SQL
     * @param type Le type de tournoi à créer (par exemple, "normal" ou "amical")
     * @return Un objet Tournoi spécifique au type demandé
     */
    Tournoi createTournoi(String nt, Statement st, String type);
}
