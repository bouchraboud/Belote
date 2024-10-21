
public class PizzaPoivrons extends Pizza {
    @Override
    public void preparer() {
        System.out.println("Préparation de la pizza aux poivrons.");
    }

    @Override
    public void cuire() {
        System.out.println("Cuisson de la pizza aux poivrons.");
    }

    @Override
    public void couper() {
        System.out.println("Coupe de la pizza aux poivrons.");
    }

    @Override
    public void emballer() {
        System.out.println("Emballage de la pizza aux poivrons.");
    }
}