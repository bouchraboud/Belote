
public 	class Equipe{
	Equipe( int _id, int _num, String _eq1, String _eq2){
		id = _id;
		num = _num;
		eq1 = _eq1;
		eq2 = _eq2;
	}
	public int id;
	public int num;
	public String eq1;
	public String eq2;
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