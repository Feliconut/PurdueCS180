/**
 * A class to build and organize coffee.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Xiaoyu Liu
 * @version March 2 2021
 */
public class Coffee
{
    private Producer producer;
    private String variety;
    private int cuppingScore;

    /**
     * Create a coffee instance by supplying the producer,
     * variety and cupping score of a coffee.
     */
    public Coffee(Producer producer, String variety, int cuppingScore)
    {
        this.producer = producer;
        this.variety = variety;
        this.cuppingScore = cuppingScore;

    }

    /**
     * Get producer of coffee.
     */
    public Producer getProducer()
    {
        return producer;
    }

    /**
     * Set producer of coffee.
     */
    public void setProducer(Producer producer)
    {
        this.producer = producer;
    }

    /**
     * Get variety of coffee.
     */
    public String getVariety()
    {
        return variety;
    }

    /**
     * Set variety of coffee.
     */
    public void setVariety(String variety)
    {
        this.variety = variety;
    }

    /**
     * Get cupping score of coffee.
     */
    public int getCuppingScore()
    {
        return cuppingScore;
    }

    /**
     * Set cupping score of coffee.
     */
    public void setCuppingScore(int cuppingScore)
    {
        this.cuppingScore = cuppingScore;
    }

    /**
     * calculate the price per pound of a coffee.
     */
    public double calculatePricePerPound()
    {
        double price = 1.21;
        if (cuppingScore >= 80)
        {
            switch (variety)
            {
                case "Gesha" -> price = 16.4;
                case "SL-28" -> price = 8.05;
                case "Pacamara" -> price = 7.5;
                case "Typica" -> price = 6.85;
                case "Caturra" -> price = 6.65;
                case "Bourbon" -> price = 6.6;
            }
        }
        return price;
    }

    /**
     * String expression of coffee.
     */
    public String toString()
    {
        return String.format("Coffee<producer=%s, variety=%s, cuppingScore=%d>", producer.toString(), variety, cuppingScore);
    }

}
