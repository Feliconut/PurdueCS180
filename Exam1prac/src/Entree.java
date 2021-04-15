public class Entree
{
    private String name;
    private int calories;
    private boolean vegetarian;
    private boolean vegan;

    public Entree(String name, int calories,boolean vegetarian, boolean vegan){
        this.name = name;
        this.calories = calories;
        this.vegetarian = vegetarian;
        this.vegan = vegan;
    }
    public Entree(Entree entree){
        this.name = entree.name;
        this.calories = entree.calories;
        this.vegetarian = entree.vegetarian;
        this.vegan = entree.vegan;
    }
    public Entree(){
        this.name = null;
        this.calories=0;
        this.vegetarian = false;
        this.vegan = false;
    }
    public String getName(){
        return name;
    }
    public int getCalories(){
        return calories;
    }
    public boolean isVegetarian(){
        return vegetarian;
    }
    public boolean isVegan(){
        return vegan;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setCalories(int calories){
        this.calories = calories;
    }
    public void setVegetarian(boolean vegetarian){
        this.vegetarian = vegetarian;
    }
    public void setVegan(boolean vegan){
        this.vegan = vegan;
    }
    public String toString(){
        return String.format("Entree<name=%s, calories=%d, vegetarian=%s, vegan=%s>",
                name, calories, String.valueOf(vegetarian),String.valueOf(vegan));
    }

}
