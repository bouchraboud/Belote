import java.util.List;

public interface EquipeDAO {
    void addEquipe(Equipe equipe);
    Equipe getEquipeById(int id);
    List<Equipe> getAllEquipes(int tournoiId);
    void updateEquipe(Equipe equipe);
    void deleteEquipe(int id);
}
