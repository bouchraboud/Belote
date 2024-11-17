import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipeDAOImpl implements EquipeDAO {
    private final Connection con;

    // Constructeur pour initialiser la connexion
    public EquipeDAOImpl(Connection connection) {
        this.con = connection;
    }

    @Override
    public void addEquipe(Equipe equipe) {
        String sql = "INSERT INTO equipes (num_equipe, nom_j1, nom_j2, id_equipe) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, equipe.getNumEquipe());
            pstmt.setString(2, equipe.getNomJ1());
            pstmt.setString(3, equipe.getNomJ2());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // Propagation de l'exception avec un message détaillé
            throw new RuntimeException("Erreur lors de l'ajout de l'équipe : " + e.getMessage(), e);
        }
    }

    @Override
    public Equipe getEquipeById(int id) {
        Equipe equipe = null;
        String sql = "SELECT * FROM equipes WHERE id_equipe = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    equipe = new Equipe(rs.getInt("id_equipe"), rs.getInt("num_equipe"), rs.getString("nom_j1"), rs.getString("nom_j2"));
                }
            }
        } catch (SQLException e) {
            // Propagation de l'exception
            throw new RuntimeException("Erreur lors de la récupération de l'équipe : " + e.getMessage(), e);
        }
        return equipe;
    }

    @Override
    public List<Equipe> getAllEquipes(int tournoiId) {
        List<Equipe> equipes = new ArrayList<>();
        String sql = "SELECT * FROM equipes WHERE id_tournoi = ? ORDER BY num_equipe";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, tournoiId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Equipe equipe = new Equipe(rs.getInt("id_equipe"), rs.getInt("num_equipe"), rs.getString("nom_j1"), rs.getString("nom_j2"));
                    equipes.add(equipe);
                }
            }
        } catch (SQLException e) {
            // Propagation de l'exception
            throw new RuntimeException("Erreur lors de la récupération des équipes : " + e.getMessage(), e);
        }
        return equipes;
    }

    @Override
    public void updateEquipe(Equipe equipe) {
        String sql = "UPDATE equipes SET num_equipe = ?, nom_j1 = ?, nom_j2 = ? WHERE id_equipe = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, equipe.getNumEquipe());
            pstmt.setString(2, equipe.getNomJ1());
            pstmt.setString(3, equipe.getNomJ2());
            pstmt.setInt(4, equipe.getIdEquipe());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // Propagation de l'exception
            throw new RuntimeException("Erreur lors de la mise à jour de l'équipe : " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteEquipe(int id) {
        String sql = "DELETE FROM equipes WHERE id_equipe = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // Propagation de l'exception
            throw new RuntimeException("Erreur lors de la suppression de l'équipe : " + e.getMessage(), e);
        }
    }
}

