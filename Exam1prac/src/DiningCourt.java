/**
 * A framework to track dining court.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Xiaoyu Liu
 * @version Feburary 2, 2021
 */
public class DiningCourt
{
    private Entree firstEntree;
    private Entree secondEntree;
    private Entree thirdEntree;

    /**
     * A method to create dining court.
     *
     * <p>Purdue University -- CS18000 -- Spring 2021</p>
     *
     */
    public DiningCourt(Entree firstEntree, Entree secondEntree, Entree thirdEntree){
        this.firstEntree = firstEntree;
        this.secondEntree = secondEntree;
        this.thirdEntree = thirdEntree;
    }
    public DiningCourt(DiningCourt diningCourt){
        this.firstEntree = diningCourt.firstEntree;
        this.secondEntree = diningCourt.secondEntree;
        this.thirdEntree = diningCourt.thirdEntree;
    }
    public DiningCourt(){
        this.firstEntree=null;
        this.secondEntree=null;
        this.thirdEntree=null;
    }
    public Entree getFirstEntree(){
        return firstEntree;
    }
    public Entree getSecondEntree(){
        return secondEntree;
    }
    public Entree getThirdEntree(){
        return thirdEntree;
    }
    public void setFirstEntree(Entree firstEntree){
        this.firstEntree = firstEntree;
    }
    public void setSecondEntree(Entree secondEntree){
        this.secondEntree = secondEntree;
    }
    public void setThirdEntree(Entree thirdEntree){
        this.thirdEntree = thirdEntree;
    }
    public Entree getLowestCalorieEntree(){
        boolean lt12 = firstEntree.getCalories() > secondEntree.getCalories();
        boolean lt23 = secondEntree.getCalories()> thirdEntree.getCalories();
        boolean lt13 = firstEntree.getCalories()> thirdEntree.getCalories();
        if (lt12){
            if (lt23){
                return firstEntree;
            }else{
                return lt13?firstEntree:thirdEntree;
            }
        }else{
            if(lt23){
                return secondEntree;
            }else{
                return thirdEntree;
            }

        }
    }
    public Entree getHighestCalorieEntree(){
        boolean lt12 = firstEntree.getCalories() < secondEntree.getCalories();
        boolean lt23 = secondEntree.getCalories()< thirdEntree.getCalories();
        boolean lt13 = firstEntree.getCalories()< thirdEntree.getCalories();
        if (lt12){
            if (lt23){
                return firstEntree;
            }else{
                return lt13?firstEntree:thirdEntree;
            }
        }else{
            if(lt23){
                return secondEntree;
            }else{
                return thirdEntree;
            }

        }
    }
    public void printVegetarianEntrees(){
        final String outstr = "Option %d: %s";
        final String outstrnothing = "No vegetarian options are available :(";
        int i = 0;
        if (firstEntree.isVegetarian()){
            System.out.println(String.format(outstr, i, firstEntree.getName()));
            i++;
        }
        if (secondEntree.isVegetarian()){
            System.out.println(String.format(outstr, i, secondEntree.getName()));
            i++;
        }
        if (thirdEntree.isVegetarian()){
            System.out.println(String.format(outstr, i, thirdEntree.getName()));
            i++;
        }
        if (i == 0){
            System.out.println(outstrnothing);
        }
    }
    public void printVeganEntrees(){
        final String outstr = "Option %d: %s";
        final String outstrnothing = "No vegan options are available :(";
        int i = 0;
        if (firstEntree.isVegan()){
            System.out.println(String.format(outstr, i, firstEntree.getName()));
            i++;
        }
        if (secondEntree.isVegan()){
            System.out.println(String.format(outstr, i, secondEntree.getName()));
            i++;
        }
        if (thirdEntree.isVegan()){
            System.out.println(String.format(outstr, i, thirdEntree.getName()));
            i++;
        }
        if (i == 0){
            System.out.println(outstrnothing);
        }
    }
    public String toString(){
        return String.format("DiningCourt<firstEntree=%s, secondEntree=%s, thirdEntree=%s>",
                firstEntree.toString(),
                secondEntree.toString(),
                thirdEntree.toString());
    }
}
