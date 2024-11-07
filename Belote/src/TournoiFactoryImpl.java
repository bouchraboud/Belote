import java.sql.Statement;

public class TournoiFactoryImpl implements TournoiFactory {

    @Override
    public Tournoi createTournoi(String nt, Statement st, String type) {
        if (type.equals("normal")) {
            return new TournoiNormal(nt, st);  // Crée un tournoi normal
        } else if (type.equals("amical")) {
            return new TournoiAmical(nt, st);  // Crée un tournoi amical
        } else {
            return new Tournoi(nt, st);  // Crée un tournoi générique si le type n'est pas reconnu
        }
    }
}

