
public class TournoiFactory {
    public static Tournoi createTournoi(String nom, int statut, int id) {
        return new Tournoi(nom, statut, id);
    }
}
