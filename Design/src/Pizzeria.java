public abstract class Pizzeria {
    SimpleFabriqueDePizzas fabrique;

    public Pizzeria(SimpleFabriqueDePizzas fabrique) {
        this.fabrique = fabrique;
    }

    public Pizza commanderPizza(String type) {
        Pizza pizza = fabrique.commanderPizza(type);
        return pizza;
    }
}
