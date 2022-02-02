
import java.util.Collections;
import java.util.List;

public class Pyramid {
    

    /**
     *  Builds 2d array with pyramid inside
     * @param inputNumbers a List with numbers
     * @return pyramid
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) throws CannotBuildPyramidException
    {
        if (inputNumbers == null || !isTriangular(inputNumbers.size()))
        {
            throw new CannotBuildPyramidException("The pyramid cannot be build with given input");
        }

        Collections.sort(inputNumbers);

        // Calculate number of rows and columns
        final int rows = calculateRows(inputNumbers.size());
        final int columns = rows * 2 - 1;

        int[][] pyramid = new int[rows][columns];

        // first position for inserting numbers starts from a center of the first row
        int firstPosition = columns / 2;

        int numberIndex = 0;

        // a variable for checking rows to calculate the positions of numbers in pyramid
        int rowChecker = rows;

        for (int i = 0; i < rows; i++)
        {
            for (int j = firstPosition; j <= rowChecker - 1; j += 2)
            {
                pyramid[i][j] = inputNumbers.get(numberIndex);

                numberIndex++;
            }

            rowChecker++;

            // move the first position for each row
            // from the center to the left 1 cell
            firstPosition--;
        }
        return pyramid;
    }


    /**
     *  Checks if a number is triangular.
     * @param number a number to check
     * @return true if the number is triangular, else false
     */
    private boolean isTriangular(int number)
    {
        // check if there are at least 3 points to make a triangle
        if (number < 3)
        {
            return false;
        }

        int sum = 0;

        for (int i = 1; sum <= number; i++)
        {
            sum = sum + i;

            if (sum == number)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates a number of rows for the pyramid
     * according to the size of List.
     * @param size of List
     * @return number of rows
     */
    private int calculateRows(int size)
    {
        int i = 0;
        int sum = 0;
        int temp;
        int rows = 0;

        while (sum <= size)
        {
            temp = sum;

            sum += i;

            rows = sum - temp;

            if (sum == size){
                break;
            }
            i++;
        }
        return rows;
    }
}
