public class Main {
    public static void main(String[] args) {
        SimpleFabriqueDePizzas fabriqueOReilly = new FabriqueDePizzasOReilly();
        
        Pizzeria pizzeriaBrest = new PizzeriaBrest(fabriqueOReilly);
        Pizzeria pizzeriaStrasbourg = new PizzeriaStrasbourg(fabriqueOReilly);

   
        Pizza pizzaFromageBrest = pizzeriaBrest.commanderPizza("fromage");

        Pizza pizzaGrecqueStrasbourg = pizzeriaStrasbourg.commanderPizza("grecque");
        
        Pizza pizzaPoivronsBrest = pizzeriaBrest.commanderPizza("poivrons");
    }
}


