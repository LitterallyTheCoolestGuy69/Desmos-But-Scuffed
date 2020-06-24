import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Graph extends JFrame
{
	private static final Color beige = new Color(245, 245, 220);
	private Function[] fxs;
	private double xMin, yMin, xMax, yMax;
	private static final int offset = 30;
	private boolean[] functionsEnabled;
	private boolean displayBound = false;
	//private boolean[] pointsEnabled;
	//private Point[] points;
	//private Color[] pointColors;
	private String[] vars = new String[] {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","z"};

	private boolean[] varDefined = new boolean[]{false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false};
	private double[] varDefinitions = new double[24];
	
	//private Color[] colors = new Color[] {Color.red,Color.green,Color.blue,Color.yellow,Color.orange,Color.pink,Color.magenta};
	public Graph(Function fxs[], Color[] functionColors, double xMin, double yMin, double xMax, double yMax)
	{
		if(fxs != null)
			this.fxs = fxs;
		
		this.xMin = xMin;
		this.yMin = yMin;
		this.xMax = xMax;
		this.yMax = yMax;
		if(fxs != null)
		{
			for(int i = 0; i < fxs.length; i++)
			{
				fxs[i].setColor(functionColors[i]);
			}
		}
		if(fxs!= null)
		{
			functionsEnabled = new boolean[fxs.length];
			for(int i = 0; i < functionsEnabled.length; i++)
			{
				functionsEnabled[i] = true;
			}
		}
	}
	public void paint(Graphics g)
	{
		g.setColor(beige);
		g.fillRect(0, 0, 700, 500 + offset);
		
		g.setColor(Color.white);
		g.fillRect(0, 0, 500, 500 + offset);
		
		g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		
		if(fxs != null)
		{
			for(int i = 0; i < fxs.length; i++)
			{
				if(functionsEnabled[i])
					g.setColor(fxs[i].getColor());
				else
					g.setColor(Color.gray);
				g.drawString(fxs[i].toString(), 520, offset + 20 + 20*i);
			}
		}

		g.setColor(Color.black);
		int count2 = 0;
		for(int i = 0; i < vars.length; i++)
		{
			if(varDefined[i])
			{
				g.drawString(vars[i] + " = " + varDefinitions[i], 520, offset + 250 + 20*count2);
				count2++;
			}
		}
		g.setColor(Color.black);
		g.drawString("For list of commands - ?", 505, 450);
		/*
		g.setColor(Color.black);
		g.drawString("( " + xMin + " , " + yMax , 45, 45);
		g.drawString("( " + xMin + " , " + yMin , 45, 555);
		g.drawString("( " + xMax + " , " + yMax , 555, 555);
		g.drawString("( " + xMax + " , " + yMin , 555, 45);
		*/
		
		g.setColor(Color.black);
		int yAxis = -500, xAxis = -500;
		if(xMin < 0 && xMax > 0)
		{
			 yAxis = (int)(((-xMin) / ((xMax-xMin)/500)));
			 g.drawLine(yAxis, 0, yAxis, 500 + offset);
			 
		}
		g.setColor(Color.black);
		if(yMin < 0 && yMax > 0)
		{
			 xAxis = (500) - (int)(((-yMin) / ((yMax-yMin)/500)));
			 g.drawLine(0, xAxis + offset, 500, xAxis + offset);
		}
		
		if(displayBound)
		{
			 g.setColor(Color.blue);
			 g.drawLine(yAxis-3, 125 + offset, yAxis+3, 125 + offset);
			 g.drawString(""+(yMax - ((yMax-yMin)/4)), yAxis + 5, 129 + offset);
			 g.drawLine(yAxis-3, 250 + offset, yAxis+3, 250 + offset);
			 g.drawString(""+(yMax - ((yMax-yMin)/2)), yAxis + 5, 254 + offset);
			 g.drawLine(yAxis-3, 375 + offset, yAxis+3, 375 + offset);
			 g.drawString(""+(yMin + ((yMax-yMin)/4)), yAxis + 5, 379 + offset);
			 
			 //System.out.println("" + (125 - ((""+(xMin + ((xMax-xMin)/4))).length()*5)) + " jjjjbaited" + "   jjj" + (xMin + ((xMax-xMin)/4)));
			 g.drawLine(125, xAxis-3 + offset, 125, xAxis+3 + offset);
			 g.drawString(""+(xMin + ((xMax-xMin)/4)), 125 - ((""+(xMin + ((xMax-xMin)/4))).length()*3), xAxis + 15 + offset);
			 g.drawLine(250, xAxis-3 + offset, 250, xAxis+3 + offset);
			 g.drawString(""+(xMin + ((xMax-xMin)/2)), 250 - ((""+(xMin + ((xMax-xMin)/2))).length()*3), xAxis + 15 + offset);
			 g.drawLine(375, xAxis-3 + offset, 375, xAxis+3 + offset);
			 g.drawString(""+(xMax - ((xMax-xMin)/4)), 375 - ((""+(xMax - ((xMax-xMin)/4))).length()*3), xAxis + 15 + offset);
			 
		}
		if(fxs != null)
		{
			int count = 0;
			Function F;
			for(Function f: fxs)
			{
				F = f.replaceVars(vars, varDefined, varDefinitions);
				
				if(functionsEnabled[count])
				{	
					g.setColor(F.getColor());
					for(int i = 0; i < 500; i++)
					{	
						if(F.getValue((xMin + (((xMax-xMin) / 500.0) *  i  ))) != 3.141582)
						{
							g.drawLine(i  , (500 + offset) - (int)(((F.getValue((xMin + (((xMax-xMin) / 500.0) *  i  ))))/((yMax-yMin)/500))-(yMin/((yMax-yMin)/500))),
								       i+1, (500 + offset) - (int)(((F.getValue((xMin + (((xMax-xMin) / 500.0) * (i+1))))/((yMax-yMin)/500)))-(yMin/((yMax-yMin)/500))));
						}
					}
				}
				count++;
			}
		}
		
		if(displayBound)
		{
			
			/*
			g.setColor(Color.black);
			g.drawString(""+xMax,500,260+offset);
			g.drawString(""+(xMax/2),372,266+offset);
			g.drawLine(375,255+offset,375,245+offset);
			
			g.drawString(""+yMax,255,offset+15);
			g.drawString(""+(yMax/2),260,130+offset);
			g.drawLine(245, 125+offset, 255, 125+offset);
			*/
		}
		/* 
		if(points != null)
		{
			for(Function F: fxs)
			{
				g.setColor(F.getColor());
				for(int i = 0; i < 500; i++)
				{	
					g.drawLine(i  , (500 + offset) - (int)(((F.getValue((xMin + (((xMax-xMin) / 500.0) *  i  ))))/((yMax-yMin)/500))-(yMin/((yMax-yMin)/500))),
							   i+1, (500 + offset) - (int)(((F.getValue((xMin + (((xMax-xMin) / 500.0) * (i+1))))/((yMax-yMin)/500)))-(yMin/((yMax-yMin)/500))));
				}
			}
		}
		*/
		
		
	}
	public void addFunction(Function fx, Color c)
	{
		boolean validFunction = false;
		Function Fdummy = fx.replaceVars(vars, varDefined, varDefinitions);
		for(int i = 0; i < 1000; i++)
		{
			if(Fdummy.getValue(i) != 3.141582)
				validFunction = true;
			
		}
		if(validFunction)
		{
			fx.setColor(c);
			if(fxs != null)
			{
				Function[] fxs2 = fxs;
				fxs = new Function[fxs.length + 1];
				for(int i = 0; i < fxs2.length; i++)
				{
					fxs[i] = fxs2[i];
				}
				fxs[fxs.length-1] = fx;
			
				boolean[] enabled = functionsEnabled;
				functionsEnabled = new boolean[enabled.length + 1];
				for(int i = 0; i < enabled.length; i++)
				{
					functionsEnabled[i] = enabled[i];
				}
				functionsEnabled[functionsEnabled.length-1] = true;
				
				for(int i = 0; i < vars.length; i++)
				{
					if(fx.toString().contains(vars[i]) && !varDefined[i])
					{
						varDefinitions[i] = 1.0;
						varDefined[i] = true;
					}
				}
			}
			else
			{
				fxs = new Function[]{fx};
				functionsEnabled = new boolean[] {true};
			}
			update();
		}
	}
	
	public void addVar(String var, double val)
	{
		boolean valid = false;
		int varNum = 0;
		for(int i = 0; i < vars.length; i++)
		{
			if(vars[i].compareTo(var) == 0)
			{
				valid = true;
				varNum = i;
			}
		}
		if(valid)
		{
			varDefined[varNum] = true;
			varDefinitions[varNum] = val;
		}
		System.out.println("Lcheck" + val);
		update();
	}
	public void removeVar(String var)
	{
		for(int i = 0; i < vars.length; i++)
		{
			if(var == vars[i])
			{
				varDefined[i] = false;
				varDefinitions[i] = 1.0;
			}
		}
		update();
	}
	public void changeVar(String var, double newVal)
	{
		for(int i = 0; i < vars.length; i++)
		{
			if(vars[i] == var)
			{
				varDefinitions[i] = newVal;
			}
		}
		update();
	}
	
	public void update()
	{
		this.setSize(this.getWidth() + 1, this.getHeight());
		this.setSize(this.getWidth() - 1, this.getHeight());
	}
	
	//Bunch of commands past this point

	
	
	public void removeFunction(int x)
	{
		Function[] dummy = fxs;
		fxs = new Function[dummy.length - 1];
		int count = 0;
		for(int i = 0; i < dummy.length; i++)
		{
			if(i!=x)
			{
				fxs[count] = dummy[i];
				count++;
			}
		}
		
		boolean[] dummy2 = functionsEnabled;
		functionsEnabled = new boolean[dummy2.length - 1];
		count = 0;
		for(int i = 0; i < dummy2.length; i++)
		{
			if(i!=x)
			{
				functionsEnabled[count] = dummy2[i];
				count++;
			}
		}
		
		update();
	}
	
	public void disableFunction(int x)
	{
		functionsEnabled[x] = false;
		update();
	}
	public void enableFunction(int x)
	{
		functionsEnabled[x] = true;
		update();
	}
	
	
	
	
	public void changeFunction(int x, Function f)
	{
		f.setColor(fxs[x].getColor());
		fxs[x] = f;
		update();
	}
	
	
	public void setNewBounds(double x1, double y1, double x2, double y2)
	{
		xMin = x1;
		yMin = y1;
		xMax = x2;
		yMax = y2;
		update();
	}
	public void setLowerX(double x)
	{
		xMin = x;
		update();
	}
	public void setLowerY(double y)
	{
		yMin = y; 
		update();
	}
	public void setUpperX(double x)
	{
		xMax = x;
		update();
	}
	public void setUpperY(double y)
	{
		yMax = y; 
		update();
	}

	
	public void upOneFourth()
	{
		double x = (yMax - yMin)/4;
		yMax += x;
		yMin += x;
	}
	public void upOneHalf()
	{
		double x = (yMax - yMin)/2;
		yMax += x;
		yMin += x;
	}
	public void upOne()
	{
		double x = (yMax - yMin);
		yMax += x;
		yMin += x;
	}
	public void upByFactor(double factor)
	{
		double x = (yMax - yMin)*factor;
		yMax += x;
		yMin += x;
	}
	
	
	public void downOneFourth()
	{
		double x = (yMax - yMin)/4;
		yMax -= x;
		yMin -= x;
	}
	public void downOneHalf()
	{
		double x = (yMax - yMin)/2;
		yMax -= x;
		yMin -= x;
	}
	public void downOne()
	{
		double x = (yMax - yMin);
		yMax -= x;
		yMin -= x;
	}
	public void downByFactor(double factor)
	{
		double x = (yMax - yMin)*factor;
		yMax -= x;
		yMin -= x;
	}
	
	
	public void leftOneFourth()
	{
		double x = (xMax - xMin)/4;
		xMax -= x;
		xMin -= x;
	}
	public void leftOneHalf()
	{
		double x = (xMax - xMin)/2;
		xMax -= x;
		xMin -= x;
	}
	public void leftOne()
	{
		double x = (xMax - xMin);
		xMax -= x;
		xMin -= x;
	}
	public void leftByFactor(double factor)
	{
		double x = (xMax - xMin)*factor;
		xMax -= x;
		xMin -= x;
	}
	
	
	public void rightOneFourth()
	{
		double x = (xMax - xMin)/4;
		xMax += x;
		xMin += x;
	}
	public void rightOneHalf()
	{
		double x = (xMax - xMin)/2;
		xMax += x;
		xMin += x;
	}
	public void rightOne()
	{
		double x = (xMax - xMin);
		xMax += x;
		xMin += x;
	}
	public void rightByFactor(double factor)
	{
		double x = (xMax - xMin)*factor;
		xMax += x;
		xMin += x;
	}
	
	public void zoomIn2x()
	{
		xMin /= 2;
		yMin /= 2;
		xMax /= 2;
		yMax /= 2;
	}
	public void zoomIn10x()
	{
		xMin /= 10;
		yMin /= 10;
		xMax /= 10;
		yMax /= 10;
	}
	public void zoomInFactor(double factor)
	{
		xMin /= factor;
		yMin /= factor;
		xMax /= factor;
		yMax /= factor;
	}
	
	public void zoomOut2x()
	{
		xMin *= 2;
		yMin *= 2;
		xMax *= 2;
		yMax *= 2;
	}
	public void zoomOut10x()
	{
		xMin *= 10;
		yMin *= 10;
		xMax *= 10;
		yMax *= 10;
	}
	public void zoomOutFactor(double factor)
	{
		xMin *= factor;
		yMin *= factor;
		xMax *= factor;
		yMax *= factor;
	}
	public void goToOrigin()
	{
		double x = (xMax-xMin)/2, y = (yMax - yMin)/2;
		xMin = -x;
		yMin = -y;
		xMax = x;
		yMax = y;
	}
	
	public String[] getVars()
	{
		return vars;
	}
	public boolean[] getDefined()
	{
		return varDefined;
	}
	
	public void changeColor(int x, Color c)
	{
		fxs[x].setColor(c);
	}
	
	public void shrinkVertical()
	{
		yMin += (yMax-yMin)/4;
		yMax -= (yMax-yMin)/4;
	}
	public void shrinkHorizontal()
	{
		xMin += (xMax-xMin)/4;
		xMax -= (xMax-xMin)/4;
	}
	public void stretchVertical()
	{
		yMin -= (yMax-yMin)/2;
		yMax += (yMax-yMin)/2;
	}
	public void stretchHorizontal()
	{
		xMin -= (xMax-xMin)/2;
		xMax += (xMax-xMin)/2;
	}
	public void displayBounds()
	{
		displayBound = true;
	}
	public void hideBounds()
	{
		displayBound = false;
	}
	
	public String yIntercept(int x)
	{
		Function F = fxs[x].replaceVars(vars, varDefined, varDefinitions);
		if( F.getValue(0) != 3.141582)
		{
			return "" + F.getValue(0);
		}
		else
		{
			return "undefined";
		}
	}
	//public void addPoint()
}