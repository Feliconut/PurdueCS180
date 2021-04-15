public class test
{
    public static void main(String[] args){
        Country country = new Country("United States", 50, 328000000);
        System.out.println("Capital: " + country.getCapital());
        System.out.println("Number of States: " + country.getNumStates());
        System.out.println("Population: " + country.getPopulation());
        country.setCapital("Germany");
        country.setNumStates(16);
        country.setPopulation(83000000);
        System.out.println("New Capital: " + country.getCapital());
        System.out.println("New Number of States: " + country.getNumStates());
        System.out.println("New Population: " + country.getPopulation());
    }
}
