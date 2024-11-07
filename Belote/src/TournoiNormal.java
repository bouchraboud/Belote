import java.sql.Statement;

public class TournoiNormal extends Tournoi {
    
    public TournoiNormal(String nt, Statement s) {
        super(nt, s);
        // Initialiser des comportements spécifiques au tournoi normal
        // Par exemple, définir un statut par défaut ou d'autres propriétés
        System.out.println("Tournoi Normal créé : " + nt);
    }

    // Comportements spécifiques pour un tournoi normal (si nécessaire)
}
