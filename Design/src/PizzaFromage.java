public class PizzaFromage extends Pizza {
    @Override
    public void preparer() {
        System.out.println("Préparation de la pizza au fromage.");
    }

    @Override
    public void cuire() {
        System.out.println("Cuisson de la pizza au fromage.");
    }

    @Override
    public void couper() {
        System.out.println("Coupe de la pizza au fromage.");
    }

    @Override
    public void emballer() {
        System.out.println("Emballage de la pizza au fromage.");
    }
}

