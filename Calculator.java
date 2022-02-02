

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Calculator {

    private String infixExpression;
    private final Deque<Character> stackInfix;
    private final List<String> postfixList;

    public Calculator() {

        stackInfix = new ArrayDeque<>();

        postfixList = new ArrayList<>();

    }


    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
   public String evaluate(String statement){

       if (statement.isEmpty() || !checkParenthesis(statement) || !checkDuplicates(statement))
       {
           return null;
       }

       infixExpression = statement;

       convertExpression();

       return calculateResult().toString();
   }


    /**
     * Checks for duplicate operators and dots in the expression.
     * @param expression an expression to check
     * @return true if there is no duplicates, false otherwise
     */
   private boolean checkDuplicates(String expression){

       char next;

       String operators = "+-*/.";

       for (int i = 0; i < expression.length()-1 ; i++)
       {
               next = expression.charAt(i + 1);

           for (int j = 0; j < operators.length(); j++)
           {
               if (operators.charAt(j) == (expression.charAt(i))){

                   if (next == expression.charAt(i)){
                       return false;
                   }
               }
           }
       }
       return true;
   }

    /**
     * Checks the matching parenthesis.
     * @param expression an expression to check
     * @return true if the parenthesis match, false otherwise
     */
    private boolean checkParenthesis(String expression){
        int stack = 0;
        for (int i = 0; i < expression.length(); ++i) {
            char c = expression.charAt(i);
            if(c == '(')
                ++stack;
            else if(c == ')'){
                --stack;
                if(stack < 0)
                    return false;
            }
        }
        return stack == 0;
    }


    /**
     * Converts the infix expression to postfix.
     */
    private void convertExpression() {
        // Temporary string to hold the number
        StringBuilder temp = new StringBuilder();

        for(int i = 0; i != infixExpression.length(); ++i)
        {
            if(Character.isDigit(infixExpression.charAt(i)))
            {
                /* If we encounter a digit, read all digit next to it and append to temp
                 * until we encounter an operator.
                 */
                temp.append(infixExpression.charAt(i));

                while((i+1) != infixExpression.length() && (Character.isDigit(infixExpression.charAt(i+1))
                            || infixExpression.charAt(i+1) == '.'))
                {
                    temp.append(infixExpression.charAt(++i));
                }

                postfixList.add(temp.toString());
                temp.delete(0, temp.length());
            }
            else
            {
                inputToStack(infixExpression.charAt(i));
            }
        }
        clearStack();
    }

    /**
     * Saves input to stack.
     * @param input a character to save
     */
    private void inputToStack(char input)
    {
        if(stackInfix.isEmpty() || input == '(')
            stackInfix.addLast(input);
        else
        {
            if(input == ')')
            {
                while(!stackInfix.getLast().equals('('))
                {
                    postfixList.add(stackInfix.removeLast().toString());
                }
                stackInfix.removeLast();
            }
            else
            {
                if(stackInfix.getLast().equals('('))
                {
                    stackInfix.addLast(input);
                } else
                {
                    while(!stackInfix.isEmpty() && !stackInfix.getLast().equals('(') &&
                            getPrecedence(input) <= getPrecedence(stackInfix.getLast()))
                    {
                        postfixList.add(stackInfix.removeLast().toString());
                    }
                    stackInfix.addLast(input);
                }
            }
        }
    }

    /**
     * Checks for precedence of operators.
     * @param operator operator to check
     * @return a number of precedence
     */
    private int getPrecedence(char operator)
    {
        if (operator == '+' || operator == '-')
        {
            return 1;
        }
        else if (operator == '*' || operator == '/')
        {
            return 2;
        } else
        {
            return 0;
        }
    }

    /**
     * Removes value from infix stack and adds it to postfix list.
     */
    private void clearStack()
    {
        while(!stackInfix.isEmpty())
        {
            postfixList.add(stackInfix.removeLast().toString());
        }
    }


    /**
     * Calculates result of expression.
     * @return a rounded result of calculations or null if divide by 0.
     */
    private BigDecimal calculateResult() {
        List<String> expression = postfixList;

        Deque<Double> resultStack = new ArrayDeque<>();

        for(int i = 0; i != expression.size(); ++i)
        {
            if(Character.isDigit(expression.get(i).charAt(0)))
            {
                resultStack.addLast(Double.parseDouble(expression.get(i)));
            }
            else
            {
                double tempResult = 0;
                double temp;

                switch(expression.get(i))
                {
                    case "+": temp = resultStack.removeLast();
                        tempResult = resultStack.removeLast() + temp;
                        break;

                    case "-": temp = resultStack.removeLast();
                        tempResult = resultStack.removeLast() - temp;
                        break;

                    case "*": temp = resultStack.removeLast();
                        tempResult = resultStack.removeLast() * temp;
                        break;

                    case "/": temp = resultStack.removeLast();
                        if (temp == 0)
                        {
                            return null;
                        }
                        tempResult = resultStack.removeLast() / temp;
                        break;
                }
                resultStack.addLast(tempResult);
            }
        }
        return BigDecimal.valueOf(resultStack.removeLast()).setScale(4, RoundingMode.HALF_UP); // round to 4 significant numbers
    }
}
