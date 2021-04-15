public class Country
{
    private String capital;
    private int numStates;
    private int population;

    public Country(String capital, int numStates, int population)
    {
        this.capital = capital;
        this.numStates = numStates;
        this.population = population;
    }

    public String getCapital()
    {
        return capital;
    }

    public void setCapital(String capital)
    {
        this.capital = capital;
    }

    public int getNumStates()
    {
        return numStates;
    }

    public void setNumStates(int numStates)
    {
        this.numStates = numStates;
    }

    public int getPopulation()
    {
        return population;
    }

    public void setPopulation(int population)
    {
        this.population = population;
    }
}