import java.util.ArrayList;
public class Reader 
{
	public static double read(String eq)
	{
		int count = 0, maxCount = 0, deepestPos = 0, deepestExit = 0; 
		double dummy, dummy2, solution = 0;
		ArrayList<Double> numbers;
		String deepest, stack, operation, firstHalf, secondHalf;
		ArrayList<String> splitDeepest;
		boolean valid, notFound, operationNeeded = true, alreadySolved = true;
		
		try {dummy2 = Double.parseDouble(eq);}
		catch(Exception e){alreadySolved = false;}
		
		if(alreadySolved)
		{
			return Double.parseDouble(eq);
		}
		

		for(int i = 1; i < eq.length(); i++)
		{
			if(eq.charAt(i) == '(' && eq.charAt(i-1) == ')')
			{
				eq = eq.substring(0,i) + "*" + eq.substring(i);
			}
		}
	
		for(int i = 0; i < eq.length(); i++)
		{
			if(eq.charAt(i) == '(')count++;
			if(eq.charAt(i) == ')')count--;
			if(count==0 && (eq.charAt(i) == '+' ||
					        eq.charAt(i) == '-' ||
					        eq.charAt(i) == '*' ||
					        eq.charAt(i) == '/' ||
					        eq.charAt(i) == '^' ))
			{
				eq = "(" + eq + ")";
				count += 1;
			}
		}
		
		while(eq.charAt(0) == '(' && operationNeeded)
		{
			//eq = eq.replaceAll("--","+");
			//if()
			for(int i = 1; i < eq.length(); i++)
			{
				if(eq.charAt(i) == '(' && eq.charAt(i-1) == ')')
				{
					eq = eq.substring(0,i) + "*" + eq.substring(i);
				}
			}
			count = 0;
			maxCount = 0;
			for(int i = 0; i < eq.length(); i++)
			{
				if(eq.charAt(i) == '(') //count increases whenever it sees an open parenthesis
					count++;
				else if(eq.charAt(i) == ')') //count decreases whenever it sees a closed parenthesis
					count--;
				if(count > maxCount) //maxCount tracks how many layers of parentheses there are at the deepest level
					maxCount = count;
			}
		
			count = 0;
			notFound = true;
			for(int i = 0; i < eq.length() && notFound; i++)
			{
				if(eq.charAt(i) == '(') //count increases whenever it sees an open parenthesis 
					count++;
				else if(eq.charAt(i) == ')') //count decreases whenever it sees a closed parenthesis
					count--;
				if(count == maxCount) //if the parentheses are at the deepest level, record the position of the parenthesis
				{
					notFound = false;
					deepestPos = i;
				}
			}
			count = 0;
			while(eq.charAt(deepestPos + count) != ')') //starting at the deepest position, count increases until it finds the closed parenthesis and records the position
			{
				count++;
			}
			deepestExit = count + deepestPos;
			
			deepest = eq.substring(deepestPos + 1, deepestExit); //deepest is a substring that only includes the innermost parenthesis' contents
			splitDeepest = new ArrayList<String> (); //splitDeepest will break up the phrase into its numbers and operation(s)
			stack = "";
			for(int i = 0; i < deepest.length(); i++) //This loop goes char by char checking for an operation,
			{
				if(deepest.charAt(i) == '+') //And if one is found,
				{
					splitDeepest.add(stack); //it adds the previous chars to the array list
					splitDeepest.add("+"); //as well as the operation that it found
					stack = ""; //and resets the variable tracking what the previous chars are
				}
				else if(deepest.charAt(i) == '-')
				{
					if(i == 0)
						stack += deepest.charAt(i);
					else if(deepest.charAt(i-1) == '+')
						stack += deepest.charAt(i);
					else if(deepest.charAt(i-1) == '-')
						stack += deepest.charAt(i);
					else if(deepest.charAt(i-1) == '*')
						stack += deepest.charAt(i);
					else if(deepest.charAt(i-1) == '/')
						stack += deepest.charAt(i);
					else if(deepest.charAt(i-1) == '^')
						stack += deepest.charAt(i);
					else
					{
						splitDeepest.add(stack);
						splitDeepest.add("-");
						stack = "";
					}
				}
				else if(deepest.charAt(i) == '/')
				{
					splitDeepest.add(stack);
					splitDeepest.add("/");
					stack = "";
				}
				else if(deepest.charAt(i) == '*')
				{
					splitDeepest.add(stack);
					splitDeepest.add("*");
					stack = "";
				}
				else if(deepest.charAt(i) == '^')
				{
					splitDeepest.add(stack);
					splitDeepest.add("^");
					stack = "";
				}
				else //otherwise
				{
					stack += deepest.charAt(i); //it adds the char to the variable that tracks it
				}
			}
			splitDeepest.add(stack); //this ensures that the last term is also added, 
			//because there will not be an operation at the end of the phrase and it would not get added otherwise
		
			
			
			valid = true;
			operation = "first";
			numbers = new ArrayList<Double> ();
			for(int i = 0; i < splitDeepest.size(); i++) //This looped try-catch ensures that if there is more than one operation, that it is either all addition or all multiplication
			{
				try
				{
					dummy = Double.parseDouble(splitDeepest.get(i)); 
					numbers.add(dummy); //It also separates the numbers from the operations
				}
				catch(Exception e)
				{
					if(operation == "first")
					{
						operation = splitDeepest.get(i);
					}
					else if(operation != splitDeepest.get(i))
					{
						valid = false;
					}
				}
			}
		
			
			
			solution = 0;
			if(!valid)
			{
				yell();
				break;
			}
			if(operation == "+") //this giant if statement performs the required operation(s)
			{
				for(int i = 0; i < numbers.size(); i++)
				{
					solution += numbers.get(i);
				}
			}
			else if(operation == "-")
			{
				solution = numbers.get(0) - numbers.get(1);
			}
			else if(operation == "/")
				try{solution = numbers.get(0) / numbers.get(1);}
				catch(Exception e){return 3.142582;}
			else if(operation == "*")
			{
				solution = 1;
				for(int i = 0; i < numbers.size(); i++)
				{
					solution *= numbers.get(i);
				}	
			}
			else if(operation == "^")
				solution = Math.pow(numbers.get(0), numbers.get(1));
			
			firstHalf = eq.substring(0,deepestPos);//substitutes the parentheses with the solution that is found
			secondHalf = eq.substring(deepestExit+1);
			if(firstHalf != "" && secondHalf != "") //checks that there is still something left to add or if its done
			{
				eq = firstHalf + solution + secondHalf;
			}
			//System.out.print(" " + solution + "$$$$$$");
		}
		double x;
		try
		{
			x = Double.parseDouble(eq);
			//System.out.println(solution + "//////");
		}
		catch(Exception e)
		{
			x = 3.141582;
			//System.out.print(solution + "|/////-////|");
		}
		return x;
	}
	public static void yell()
	{
		
	}
	public static String determineIntent(String input)
	{
		if(input.contains(","))
		{
			return "point";
		}
		else if(!input.contains("x"))
		{
			if(input.contains("="))
			{
				if(input.contains("y"))
				{
					return "function";
				}
				else
				{
					return "var";
				}
			}
		}
		else
			return "function";
		return input;
	}
}