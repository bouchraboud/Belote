import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public 	class Equipe{
	public int id;
	public int num;
	public String eq1;
	public String eq2;
	
	
	Equipe( int _id, int _num, String _eq1, String _eq2){
		id = _id;
		num = _num;
		eq1 = _eq1;
		eq2 = _eq2;
	}
	
	// Exemple d'insertion dans la base de données
    public void addEquipe() {
        String query = "INSERT INTO equipe (id, num, eq1, eq2) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, this.id);
            statement.setInt(2, this.num);
            statement.setString(3, this.eq1);
            statement.setString(4, this.eq2);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public int getNumEquipe() {
		return num;
	}
	public String getNomJ1() {
		return eq1;
	}
	public String getNomJ2() {
		return eq2;
	}
	public int getIdEquipe() {
		return id;
	}
}
