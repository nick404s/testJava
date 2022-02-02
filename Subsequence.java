

import java.util.List;

public class Subsequence {


    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y)
    {
        if (x == null || y == null)
        {
            return false;
        }

        int countIdenticalElements = 0;

        for(Object element: x )
        {
            for (Object anotherElement : y)
            {
                if (element.equals(anotherElement))
                {
                    countIdenticalElements++;
                    System.out.println("element: " + element + ", another element: " + anotherElement);
                }
            }
        }
        return x.size() == countIdenticalElements;
    }
}