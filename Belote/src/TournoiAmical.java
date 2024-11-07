import java.sql.Statement;

public class TournoiAmical extends Tournoi {

    public TournoiAmical(String nt, Statement s) {
        super(nt, s);
        // Initialiser des comportements spécifiques au tournoi amical
        // Par exemple, définir des règles moins strictes ou d'autres propriétés
        System.out.println("Tournoi Amical créé : " + nt);
    }

    // Comportements spécifiques pour un tournoi amical (si nécessaire)
}
