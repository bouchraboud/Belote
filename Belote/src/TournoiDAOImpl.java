import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TournoiDAOImpl implements TournoiDAO {

    private final Connection con;

    // Constructeur pour initialiser la connexion à la base de données
    public TournoiDAOImpl(Connection connection) {
        this.con = connection;
    }

    @Override
    public void addTournoi(Tournoi tournoi) {
        String sql = "INSERT INTO tournois (nom, statut, id) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, tournoi.getNom());
            pstmt.setInt(2, tournoi.getStatut());
            pstmt.setInt(3, tournoi.getIdTournoi());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du tournoi : " + e.getMessage());
        }
    }

    @Override
    public void createAndAddTournoi(String nom, int statut, int id) {
        // Cette méthode pourrait être une version simplifiée de addTournoi.
        Tournoi tournoi = new Tournoi(nom, statut, id);
        addTournoi(tournoi); // Appel de addTournoi pour ajouter le tournoi à la base de données.
    }

    @Override
    public Tournoi getTournoiById(int id) {
        Tournoi tournoi = null;
        String sql = "SELECT * FROM tournois WHERE id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    tournoi = new Tournoi(rs.getString("nom"), rs.getInt("statut"), rs.getInt("id"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du tournoi : " + e.getMessage());
        }
        return tournoi;
    }

    @Override
    public List<Tournoi> getAllTournois() {
        List<Tournoi> tournois = new ArrayList<>();
        String sql = "SELECT * FROM tournois";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Tournoi tournoi = new Tournoi(rs.getString("nom"), rs.getInt("statut"), rs.getInt("id"));
                    tournois.add(tournoi);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des tournois : " + e.getMessage());
        }
        return tournois;
    }

    @Override
    public void updateTournoi(Tournoi tournoi) {
        String sql = "UPDATE tournois SET nom = ?, statut = ? WHERE id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, tournoi.getNom());
            pstmt.setInt(2, tournoi.getStatut());
            pstmt.setInt(3, tournoi.getIdTournoi());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("Aucune mise à jour effectuée pour le tournoi avec id: " + tournoi.getIdTournoi());
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du tournoi : " + e.getMessage());
        }
    }

    @Override
    public void deleteTournoi(int id) {
        String sql = "DELETE FROM tournois WHERE id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("Aucun tournoi trouvé avec l'id: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du tournoi : " + e.getMessage());
        }
    }

    // Méthode d'importation du fichier SQL
    public void importSQL(Connection connection, File sqlFile) throws SQLException, FileNotFoundException {
        // Lecture du fichier SQL
        StringBuilder sqlQuery = new StringBuilder();
        try (Scanner scanner = new Scanner(sqlFile)) {
            while (scanner.hasNextLine()) {
                sqlQuery.append(scanner.nextLine()).append("\n");
            }
        }

        // Exécution de la requête SQL
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sqlQuery.toString());
        }
    }
}

