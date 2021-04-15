import java.util.Arrays;

/**
 * A class that generates a 2-D char array representing an 'X'
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Purdue CS 
 * @version January 19, 2021
 */
public class DrawX {

    public static void main(String[] args) {

        DrawX sample = new DrawX(4);

        char[][] testArray = sample.generateArray();

        for (int i = 0; i < testArray.length; i++) {
            System.out.println(Arrays.toString(testArray[i]));
        }
    }

    private int size;

    public DrawX(int size) {
        this.size = size;
    }

    public char[][] generateArray() {

        char[][] xArray = new char[size][size];

        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                if (i==j || (i+j==size-1)){
                    xArray[i][j]='*';
                }else{
                    xArray[i][j]=' ';
                }
            }
        }

        return xArray;
    }
}