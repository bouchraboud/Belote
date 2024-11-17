import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MatchDAOImpl implements MatchDAO {
    private Connection conn;

    // Constructor to initialize the connection
    public MatchDAOImpl() {
        this.conn = DatabaseConnection.getInstance().getConnection();
    }

    // Implementation of the getMatchStats method to fetch match statistics for a specific tournament
    @Override
    public int[] getMatchStats(int tournoiId) {
        int[] stats = new int[2];  // [0] - total matches, [1] - finished matches
        String query = "SELECT count(*) as total, " +
                "(SELECT count(*) FROM matchs m2 WHERE m2.id_tournoi = m.id_tournoi AND m2.termine='oui') as termines " +
                "FROM matchs m WHERE m.id_tournoi=" + tournoiId + " GROUP BY id_tournoi";

        try (Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(query)) {

            if (rs.next()) {
                stats[0] = rs.getInt("total");    // Total matches
                stats[1] = rs.getInt("termines"); // Finished matches
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
        }

        return stats;  // Return the match statistics
    }

    public List<Vector<Object>> getTournamentResults(int tournoiId) throws SQLException {
        String query = """
        SELECT equipe,
               (SELECT nom_j1 FROM equipes e WHERE e.id_equipe = equipe AND e.id_tournoi = ?) AS joueur1,
               (SELECT nom_j2 FROM equipes e WHERE e.id_equipe = equipe AND e.id_tournoi = ?) AS joueur2,
               SUM(score) AS score,
               (SELECT COUNT(*) FROM matchs m
                WHERE (m.equipe1 = equipe AND m.score1 > m.score2 AND m.id_tournoi = ?)
                   OR (m.equipe2 = equipe AND m.score2 > m.score1)) AS matchs_gagnes,
               (SELECT COUNT(*) FROM matchs m
                WHERE m.equipe1 = equipe OR m.equipe2 = equipe) AS matchs_joues
        FROM (
            SELECT equipe1 AS equipe, score1 AS score FROM matchs WHERE id_tournoi = ?
            UNION
            SELECT equipe2 AS equipe, score2 AS score FROM matchs WHERE id_tournoi = ?
        ) AS subquery
        GROUP BY equipe
        ORDER BY matchs_gagnes DESC;
    """;

        List<Vector<Object>> results = new ArrayList<>();
        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            // Set the tournament ID (tournoiId) for all placeholders
            pstmt.setInt(1, tournoiId);
            pstmt.setInt(2, tournoiId);
            pstmt.setInt(3, tournoiId);
            pstmt.setInt(4, tournoiId);
            pstmt.setInt(5, tournoiId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Vector<Object> row = new Vector<>();
                    row.add(rs.getInt("equipe"));
                    row.add(rs.getString("joueur1"));
                    row.add(rs.getString("joueur2"));
                    row.add(rs.getInt("score"));
                    row.add(rs.getInt("matchs_gagnes"));
                    row.add(rs.getInt("matchs_joues"));
                    results.add(row);
                }
            }
        }
        return results;
    }


}
