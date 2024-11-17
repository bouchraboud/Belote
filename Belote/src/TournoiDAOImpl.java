import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.*;

public class TournoiDAOImpl implements TournoiDAO {

    private final Connection con;

    // Constructeur pour initialiser la connexion via le Singleton
    public TournoiDAOImpl() {
        this.con = DatabaseConnection.getInstance().getConnection();
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
        String sql = "SELECT id, nom_tournoi, statut FROM tournois";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Assuming Tournoi(String name, int status, int id)
                    Tournoi tournoi = new Tournoi(
                            rs.getString("nom_tournoi"),
                            rs.getInt("statut"),
                            rs.getInt("id")
                    );
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
    public void deleteTournoi(int idTournoi) {
        String sql = "DELETE FROM tournois WHERE id = ?";
        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, idTournoi);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("Aucun tournoi trouvé avec l'ID : " + idTournoi);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du tournoi : " + e.getMessage());
        }
    }

    @Override
    public void creerTournoi(String name, int status, int id) {
        String sql = "INSERT INTO tournois (id, nom_tournoi, statut) VALUES (?, ?, ?)";
        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            // Affectation des valeurs aux paramètres
            pstmt.setInt(1, id);        // ID du tournoi
            pstmt.setString(2, name);   // Nom du tournoi
            pstmt.setInt(3, status);    // Statut du tournoi
            pstmt.executeUpdate();
            System.out.println("Tournoi créé avec succès : " + name);
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création du tournoi : " + e.getMessage());
        }
    }
    public int getTournoiIdByName(String name) {
        String sql = "SELECT id FROM tournois WHERE nom_tournoi = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'ID du tournoi : " + e.getMessage());
        }
        return -1; // Retourne -1 si aucun tournoi trouvé
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


    public Vector<Vector<Object>> getTournoiTours(int tournamentId) throws SQLException {
        Vector<Vector<Object>> toursData = new Vector<Vector<Object>>();
        String query = "SELECT num_tour, count(*) as tmatchs, " +
                "(SELECT count(*) FROM matchs m2 WHERE m2.id_tournoi = m.id_tournoi AND m2.num_tour = m.num_tour AND m2.termine = 'oui') as termines " +
                "FROM matchs m WHERE m.id_tournoi = ? GROUP BY m.num_tour, m.id_tournoi";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, tournamentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Vector<Object> row = new Vector<Object>();
                row.add(rs.getInt("num_tour"));
                row.add(rs.getInt("tmatchs"));
                row.add(rs.getString("termines"));
                toursData.add(row);
            }
        }
        return toursData;
    }

    public boolean canAddTour(int tournamentId) throws SQLException {
        // You can implement additional checks if needed
        return true;
    }




}