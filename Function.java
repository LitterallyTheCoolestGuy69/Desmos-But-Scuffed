import java.awt.Color;
import java.util.ArrayList;
public class Function 
{
	String eq;
	Color c;
	public Function(String eq)
	{
		this.eq = eq;
	}
	public double getValue(double x)
	{
		String EQ = eq, concat;
		EQ = EQ.replaceAll(" ", "");
		ArrayList <Character> split;
		int A,B;
		for(int i = 0; i < 10; i++)
		{
			concat = "";
			if(EQ.contains(""+i+"x"))
			{
				A = EQ.indexOf(""+i+"x");
				concat = ""+i;
				B = 0;
				for(int j = A - 1;  j!=-1 && (EQ.charAt(j) == '0' || EQ.charAt(j) == '1' || EQ.charAt(j) == '2' || EQ.charAt(j) == '3' ||
											 EQ.charAt(j) == '4' || EQ.charAt(j) == '5' || EQ.charAt(j) == '6' || EQ.charAt(j) == '7' ||
											 EQ.charAt(j) == '8' || EQ.charAt(j) == '9' || EQ.charAt(j) == '.') ; j--)
				{
					concat = "" + EQ.charAt(j) + concat;
					B=j;
				}
				if(B != 0)
				{
					if(EQ.charAt(B) != '(' || EQ.charAt(A+2) != ')')
					{
						EQ = EQ.replaceAll(concat+"x", "("+concat+"*x)");
					}
					else
					{
						EQ = EQ.replaceAll(concat+"x", concat+"*x");
					}
				}
				else
				{
					EQ = EQ.replaceAll(concat+"x", "("+concat+"*x)");
				}
			}
		}
		
		split = new ArrayList<Character> ();
		for(int i = 0; i < EQ.length(); i++)
		{
			split.add(EQ.charAt(i));
			//System.out.print("hamborger>>"+i+split);
		}
		for(int i = 0; i < split.size() - 1; i++)
		{
			for(int j = 0; j < 10; j++)
			{
				if(split.get(i) == (""+j).charAt(0) && split.get(i+1) == '(')
				{
					split.add(i+1,'*');
				}
			}
		}
		for(int i = 0; i < split.size() - 1; i++)
		{
			if(split.get(i) == 'x' && split.get(i+1) == '(')
			{
				split.add(i+1,'*');
			}
			if(split.get(i) == ')' && split.get(i+1) == 'x')
			{
				split.add(i+1,'*');
			}
		}
		String s = "";
		for(int i = 0; i < split.size(); i++)
		{
			s = s+split.get(i);
		}
		EQ = s;
		
		//System.out.println("yeet skeet kill me " + EQ);
		System.out.println(EQ);
		return Reader.read(EQ.replaceAll("x",""+x));

	}
	public String toString()
	{
		return "y = " + eq;
	}
	public void setColor(Color c)
	{
		this.c = c;
	}
	public Color getColor()
	{
		return c;
	}
	public Function replaceVars(String[] vars, boolean[] defined, double[] definitions)
	{
		String s = eq;
		s = s.replaceAll(" ","");
		String concat;
		int A = 0, B;
		for(int i = 0; i < vars.length; i++)
		{
			for(int x = 0; x < 10; x++)
			{
				concat = "";
				//s = s.replaceAll(""+x+vars[i],""+x+"*"+vars[i]);
				if(s.contains(""+x+vars[i]))
				{
					A = s.indexOf(""+x+vars[i]);
					concat = ""+x;
					B = 0;
					if(A - 1 != -1)
					{
						B = A - 1;
					}
					for(int j = A - 1; j!= -1 && (s.charAt(j) == '0' || s.charAt(j) == '1' || s.charAt(j) == '2' || s.charAt(j) == '3' ||
										         s.charAt(j) == '4' || s.charAt(j) == '5' || s.charAt(j) == '6' || s.charAt(j) == '7' ||
										         s.charAt(j) == '8' || s.charAt(j) == '9' || s.charAt(j) == '.')  ; j--)
					{
						concat = "" + s.charAt(j) + concat;
						B = j;
					}
					if(B != 0)
					{
						//System.out.println("b != 0 >> s: " + s + "     concat: " + concat);
						int o = 0;
						concat = concat + "*" + vars[i];
						boolean isNotVar = false;
						String[] vars2 = new String[vars.length+1];
						for(int g = 0; g < vars.length; g++)
							vars2[g] = vars[g];
						vars2[vars2.length-1] = "x";
						while(!isNotVar)
						{
							isNotVar = true;
							if(A+2+o == s.length())
							{
								break;
							}
							for(String c: vars2)
							{
								if((""+s.charAt(A+2+o)).compareTo(c)==0)
								{
									isNotVar = false;
								}
							}
							if(!isNotVar)
							{
								concat = concat + "*" + s.charAt(A+2+o);
								o++;
							}
						}
						//s = s.replaceAll(s.substring(B,A+2+o),"("+concat+")");

						if(s.charAt(A+1+o) != ')' || s.charAt(B) != '(')
							s = s.replaceAll(s.substring(B+1,A+2+o),"("+concat+")");
						else
							s = s.replaceAll(s.substring(B+1,A+2+o),concat);
					}
					else
					{
						//System.out.println("b == 0 >> s: " + s + "     concat: " + concat);
						int o = 0;
						concat = concat + "*" + vars[i];
						boolean isNotVar = false;
						String[] vars2 = new String[vars.length+1];
						for(int g = 0; g < vars.length; g++)
							vars2[g] = vars[g];
						vars2[vars2.length-1] = "x";
						while(!isNotVar)
						{
							
							isNotVar = true;
							if(A+2+o == s.length())
							{
								break;
							}
							for(String c: vars2)
							{
								if((""+s.charAt(A+2+o)).compareTo(c)==0)
								{
									isNotVar = false;
								}
							}
							if(!isNotVar)
							{
								concat = concat + "*" + s.charAt(A+2+o);
								o++;
							}
						}
						//s = s.replaceAll(s.substring(B,A+2+o),"("+concat+")");

						if(s.charAt(A+1+o) != ')' || s.charAt(B) != '(')
							s = s.replaceAll(s.substring(B,A+2+o),"("+concat+")");
						else
							s = s.replaceAll(s.substring(B,A+2+o),concat);
					}
				}
			}
		}
		
		//System.out.println("end s: " + s);
		
		for(int i = 0; i < vars.length; i++)
		{
			if(defined[i])
			{
				s = s.replaceAll(vars[i],""+definitions[i]);
			}
		}
		Function f = new Function(s);
		f.setColor(c);
		return f;
	}
}