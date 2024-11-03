
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TournoiDAOImpl implements TournoiDAO {
    private final Connection con;

    public TournoiDAOImpl(Connection connection) {
        this.con = connection;
    }

	@Override
    public void addTournoi(Tournoi tournoi) {
        String sql = "INSERT INTO tournois (nom_tournoi, statut) VALUES (?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, tournoi.getNom());
            pstmt.setInt(2, tournoi.getStatut());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding tournoi: " + e.getMessage());
        }
    }

    @Override
    public Tournoi getTournoiById(int id) {
        Tournoi tournoi = null;
        String sql = "SELECT * FROM tournois WHERE id_tournoi = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                tournoi = new Tournoi(rs.getString("nom_tournoi"), rs.getInt("statut"), rs.getInt("id_tournoi"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving tournoi: " + e.getMessage());
        }
        return tournoi;
    }

    @Override
    public List<Tournoi> getAllTournois() {
        List<Tournoi> tournois = new ArrayList<>();
        String sql = "SELECT * FROM tournois";
        try (PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Tournoi tournoi = new Tournoi(rs.getString("nom_tournoi"), rs.getInt("statut"), rs.getInt("id_tournoi"));
                tournois.add(tournoi);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving all tournois: " + e.getMessage());
        }
        return tournois;
    }

    @Override
    public void updateTournoi(Tournoi tournoi) {
        String sql = "UPDATE tournois SET statut = ? WHERE id_tournoi = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, tournoi.getStatut());
            pstmt.setInt(2, tournoi.getIdTournoi());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating tournoi: " + e.getMessage());
        }
    }

    @Override
    public void deleteTournoi(int id) {
        String sql = "DELETE FROM tournois WHERE id_tournoi = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting tournoi: " + e.getMessage());
        }
    }
 // Method to import SQL commands from a file
    public void importSQL(Connection conn, File in) throws SQLException, FileNotFoundException {
        Scanner s = new Scanner(in);
        s.useDelimiter("(;(\r)?\n)|(--\n)");
        Statement st = null;
        try {
            st = conn.createStatement();
            while (s.hasNext()) {
                String line = s.next();
                if (line.startsWith("/*!") && line.endsWith("*/")) {
                    int i = line.indexOf(' ');
                    line = line.substring(i + 1, line.length() - " */".length());
                }

                if (line.trim().length() > 0) {
                    st.execute(line);
                }
            }
        } finally {
            if (st != null) st.close();
            s.close(); // Close the scanner here
        }
    }
}


