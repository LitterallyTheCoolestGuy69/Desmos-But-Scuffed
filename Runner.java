import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public class Runner extends JPanel implements ActionListener {
    protected JTextField textField;
    protected JTextArea textArea,fillerTextArea;
    private final static String newline = "\n";
    private static Graph g2;
    private boolean opened = false;
    private Color[] colors = new Color[] {Color.red, Color.green, Color.blue, Color.orange, Color.magenta};
    private String[] commands = new String[] {"To use a command, type the required input and replace \"input\" with your desired values",
			"List of Commands",
			"----------------",
			"Close this list: c",
			"Set lower x bound: com.MinX%input", //done 
            "Set lower y bound: com.MinY%input", //done
            "Set upper x bound: com.MaxX%input", //done
            "Set upper y bound: com.MaxY%input", //done
            "Set new bounds: com.bound%input%input%input%input  (do the inputs in this order lower x, lower y, upper x, upper y)", //done
            "Go to origin: com.o", //done
            "Display bounds: com.showBound",
            "Stop displaying bounds: com.hideBound",
            "Stretch graph horizontally x2: com.St<>",
            "Stretch graph vetically x2: com.St^v",
            "Shrink graph horizontally x2: com.Sh<>",
            "Shrink graph vetically x2: com.Sh^v",
            "Find yIntercept of a function: com.yInt%input    (input should be the number of the function)",
            "Move graph up by 1/4 graph height: com.^", //done <
            "Move graph up by 1/2 graph height: com.^*", //done <
            "Move graph up by one graph height: com.^!", //done <
            "Move graph up by a multiple of the graph height: com.^%input", //done
            "Move graph down by 1/4 graph height: com.v", //done <
            "Move graph down by 1/2 graph height: com.v*", //done <
            "Move graph down by one graph height: com.v!", //done <
            "Move graph down by a multiple of the graph height: com.v%input", //done
            "Move graph left by 1/4 graph width: com.<", //done <
            "Move graph left by 1/2 graph width: com.<*", //done <
            "Move graph left by one graph width: com.<!", //done <
            "Move graph left by a multiple of the graph width: com.<%input", //done
            "Move graph right by 1/4 graph width: com.>", //done <
            "Move graph right by 1/2 graph width: com.>*", //done <
            "Move graph right by one graph width: com.>!", //done <
            "Move graph right by a multiple of the graph width: com.>%input", //done
            "Zoom in graph x2: com.zi", //done <
            "Zoom in graph x10: com.zi!", //done <
            "Zoom in graph by factor: com.zi%input", //done
            "Zoom out graph x2: com.zo", //done <
            "Zoom out graph x10: com.zo!", //done <
            "Zoom out graph by factor: com.zo%input", //done
            "Remove variable definition: com.remove%input (input should be the letter of the variable)", //done
            "Delete function: com.del%input (for this command input should be the number of the function)", //done
            "Change function: com.edit#%input%input  (for this command, first input should be the number, and the second should be the edited function)", //done
            "Disable a function: com.disable%input", //done
            "Enable a function: com.enable%input", //done
            "Change color of a function to red: com.col/r%input", //done
            "Change color of a function to green: com.col/g%input", //done
            "Change color of a function to blue: com.col/blu%input", //done
            "Change color of a function to orange: com.col/o%input", //done
            "Change color of a function to black: com.col/bla%input", //done
            "Change color of a function to pink: com.col/p%input", //done
            "Change color of a function to specific rgb: com.edit/col%input%r/input%g/input%b/input"};
    public Runner() {
        //super(new GridBagLayout());
 
        textField = new JTextField(20);
        textField.addActionListener(this);
 
        fillerTextArea = new JTextArea(1,10);
        fillerTextArea.setEditable(false);
        
        textArea = new JTextArea(20, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        //Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        
                
       
        //c.gridwidth = 2;
        //c.anchor = GridBagConstraints.LAST_LINE_END;
        //c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        add(textField, c);
        textField.setLocation(0,100);
        
        
        c.gridx = 1;
        add(fillerTextArea,c);

        
        
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
        
    }
 
    public void actionPerformed(ActionEvent evt) {
        String text = textField.getText();
        
        //System.out.print("||||||||||"+text+"|||||||");
        
        if(text.compareTo("?") == 0)
        {
        	openTextArea();
        	//System.out.println("hello");
        }
        else if(opened && text.compareTo("c") == 0)
        	closeTextArea();
        /*
        else if(Reader.determineIntent(text) == "point")
        {
        	try
        	{
        		g2.addPoint(Double.parseDouble(text.replaceAll("(","").replaceAll(")","").replaceAll(" ","").split(",")[0]),
        				    Double.parseDouble(text.replaceAll("(","").replaceAll(")","").replaceAll(" ","").split(",")[1]));
        	}
        	catch(Exception e){}
        }
        */
        else if(Reader.determineIntent(text) == "var")
        {
        	//System.out.println("here");
        	String newVar = text.substring(0,text.indexOf('=')).replaceAll(" ", "");;
        	boolean valid = true;
        	double newVal = 0;
        	try
        	{
        		newVal = Double.parseDouble(text.substring(text.indexOf('=')+1));
        	}
        	catch(Exception e)
        	{
        		valid = false;
        	}
        	if(valid)
        	{
        		//System.out.println("here");
        		g2.addVar(newVar, newVal);
        	}
        }
        else if(Reader.determineIntent(text) == "function")
        {
        	String[] Vars = g2.getVars();
        	boolean[] defined = g2.getDefined();
        	String t = text;
        	for(int i = 0; i < Vars.length; i++)
        	{
        		if(text.contains(Vars[i]))
        		{
        			if(!defined[i])
        			{
        				g2.addVar(Vars[i], 1.0);
        			}
        		}
        	}
        	t = t.replaceAll("y","").replaceAll("=","");
        	Function f = new Function(t);
            shiftColors();
            g2.addFunction(f, colors[0]);
        }
        else if(text.contains("com"))
        {
        	String[] splitCommand = text.split("%");
        	if(splitCommand[0].compareTo(text) == 0)
        	{
        		if(splitCommand[0].compareTo("com.^") == 0)
        			g2.upOneFourth();
        		else if(splitCommand[0].compareTo("com.^*") == 0)
        			g2.upOneHalf();
        		else if(splitCommand[0].compareTo("com.^!") == 0)
        			g2.upOne();
        		else if(splitCommand[0].compareTo("com.v") == 0)
        			g2.downOneFourth();
        		else if(splitCommand[0].compareTo("com.v*") == 0)
        			g2.downOneHalf();
        		else if(splitCommand[0].compareTo("com.v!") == 0)
        			g2.downOne();
        		else if(splitCommand[0].compareTo("com.<") == 0)
        			g2.leftOneFourth();
        		else if(splitCommand[0].compareTo("com.<*") == 0)
        			g2.leftOneHalf();
        		else if(splitCommand[0].compareTo("com.<!") == 0)
        			g2.leftOne();
        		else if(splitCommand[0].compareTo("com.>") == 0)
        			g2.rightOneFourth();
        		else if(splitCommand[0].compareTo("com.>*") == 0)
        			g2.rightOneHalf();
        		else if(splitCommand[0].compareTo("com.>!") == 0)
        		    g2.rightOne();
        		else if(splitCommand[0].compareTo("com.zi") == 0)
        			g2.zoomIn2x();
        		else if(splitCommand[0].compareTo("com.zi!") == 0)
        			g2.zoomIn10x();
        		else if(splitCommand[0].compareTo("com.zo") == 0)
        			g2.zoomOut2x();
        		else if(splitCommand[0].compareTo("com.zo!") == 0)
        			g2.zoomOut10x();
        		else if(splitCommand[0].compareTo("com.o") == 0)
        			g2.goToOrigin();
        		else if(splitCommand[0].compareTo("com.showBound") == 0)
        			g2.displayBounds();
        		else if(splitCommand[0].compareTo("com.hideBound") == 0)
        			g2.hideBounds();
        		else if(splitCommand[0].compareTo("com.St<>") == 0)
        			g2.stretchHorizontal();
        		else if(splitCommand[0].compareTo("com.St^v") == 0)
        			g2.stretchVertical();
        		else if(splitCommand[0].compareTo("com.Sh<>") == 0)
        			g2.shrinkHorizontal();
        		else if(splitCommand[0].compareTo("com.Sh^v") == 0)
        			g2.shrinkVertical();
        	}
        	else
        	{
        		if(splitCommand[0].compareTo("com.MinX") == 0)
        		{
        			try{g2.setLowerX(Double.parseDouble(splitCommand[1]));}
        			catch(Exception e){}
        		}
        		else if(splitCommand[0].compareTo("com.MinY") == 0)
        		{
        			try{g2.setLowerY(Double.parseDouble(splitCommand[1]));}
    				catch(Exception e){}
        		}
        		else if(splitCommand[0].compareTo("com.MaxX") == 0)
        		{
        			try{g2.setUpperX(Double.parseDouble(splitCommand[1]));}
    				catch(Exception e){}
        		}
        		else if(splitCommand[0].compareTo("com.MaxY") == 0)
        		{
        			try{g2.setUpperY(Double.parseDouble(splitCommand[1]));}
    				catch(Exception e){}
        		}
        		else if(splitCommand[0].compareTo("com.bound") == 0)
        		{
        			try{g2.setNewBounds(Double.parseDouble(splitCommand[1]),
        					            Double.parseDouble(splitCommand[2]),
        					            Double.parseDouble(splitCommand[3]),
        					            Double.parseDouble(splitCommand[4]));}
    				catch(Exception e){}
        		}
        		else if(splitCommand[0].compareTo("com.^") == 0)
        		{
        			try{g2.upByFactor(Double.parseDouble(splitCommand[1]));}
        			catch(Exception e){}
        		}
        		else if(splitCommand[0].compareTo("com.<") == 0)
        		{
        			try{g2.leftByFactor(Double.parseDouble(splitCommand[1]));}
        			catch(Exception e){}
        		}
        		else if(splitCommand[0].compareTo("com.v") == 0)
        		{
        			try{g2.downByFactor(Double.parseDouble(splitCommand[1]));}
        			catch(Exception e){}
        		}
        		else if(splitCommand[0].compareTo("com.>") == 0)
        		{
        			try{g2.rightByFactor(Double.parseDouble(splitCommand[1]));}
        			catch(Exception e){}
        		}
        		else if(splitCommand[0].compareTo("com.zo") == 0)
        		{
        			try{g2.zoomOutFactor(Double.parseDouble(splitCommand[1]));}
        			catch(Exception e){}
        		}
        		else if(splitCommand[0].compareTo("com.zi") == 0)
        		{
        			try{g2.zoomInFactor(Double.parseDouble(splitCommand[1]));}
        			catch(Exception e){}
        		}
        		else if(splitCommand[0].compareTo("com.remove") == 0)
        		{
        			try{g2.removeVar(splitCommand[1]);}
        			catch(Exception e){}
        		}
        		else if(splitCommand[0].compareTo("com.del") == 0)
        		{
        			try{g2.removeFunction(Integer.parseInt(splitCommand[1]) - 1);}
        			catch(Exception e){}
        		}
        		else if(splitCommand[0].compareTo("com.edit#") == 0)
        		{
        			Function F = new Function(splitCommand[2]);
        			if(F.getValue(3) != 3.141582)
        			{
        				try{g2.changeFunction(Integer.parseInt(splitCommand[1]) - 1, F);}
        				catch(Exception e){}
        			}
        		}
        		else if(splitCommand[0].compareTo("com.disable") == 0)
        		{
        			try{g2.disableFunction(Integer.parseInt(splitCommand[1]) - 1);}
        			catch(Exception e){}
        		}
        		else if(splitCommand[0].compareTo("com.enable") == 0)
        		{
        			try{g2.enableFunction(Integer.parseInt(splitCommand[1]) - 1);}
        			catch(Exception e){}
        		}
        		
        		
        		else if(splitCommand[0].compareTo("com.col/r") == 0)
        		{
        			try{g2.changeColor(Integer.parseInt(splitCommand[1]) - 1, Color.red);}
        			catch(Exception e){}
        		}
        		else if(splitCommand[0].compareTo("com.col/g") == 0)
        		{
        			try{g2.changeColor(Integer.parseInt(splitCommand[1]) - 1, Color.green);}
        			catch(Exception e){}
        		}
        		else if(splitCommand[0].compareTo("com.col/blu") == 0)
        		{
        			try{g2.changeColor(Integer.parseInt(splitCommand[1]) - 1, Color.blue);}
        			catch(Exception e){}
        		}
        		else if(splitCommand[0].compareTo("com.col/o") == 0)
        		{
        			try{g2.changeColor(Integer.parseInt(splitCommand[1]) - 1, Color.orange);}
        			catch(Exception e){}
        		}
        		else if(splitCommand[0].compareTo("com.col/bla") == 0)
        		{
        			try{g2.changeColor(Integer.parseInt(splitCommand[1]) - 1, Color.black);}
        			catch(Exception e){}
        		}
        		else if(splitCommand[0].compareTo("com.col/p") == 0)
        		{
        			try{g2.changeColor(Integer.parseInt(splitCommand[1]) - 1, Color.pink);}
        			catch(Exception e){}
        		}
        		else if(splitCommand[0].compareTo("com.edit/col") == 0)
        		{
        			try{g2.changeColor(Integer.parseInt(splitCommand[1]) - 1, new Color(Integer.parseInt(splitCommand[2].substring(2)), 
        					                                                        Integer.parseInt(splitCommand[3].substring(2)), 
        					                                                        Integer.parseInt(splitCommand[3].substring(2))));}
        			catch(Exception e){}
        		}
        		else if(splitCommand[0].compareTo("com.yInt") == 0)
        		{	
        			try{display("y = " + g2.yIntercept(Integer.parseInt(splitCommand[1])) + "\n enter c to close");}
        			catch(Exception e){}
        		}
        	}
        }
        g2.update();
        textField.setVisible(true);
        textField.setFocusable(true);
        textField.isDisplayable();
        textField.grabFocus();
    }
 
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     * 
     * JFrame g2 = new Graph(fxs, functionColors, xMin, yMin, xMax, yMax);
		g2.setSize(700, 500);
		g2.setVisible(true);
		g2.addWindowListener( new WindowAdapter() 
	     {
	         @Override
	         public void windowClosing(WindowEvent we) 
	         {
	            g2.dispose();
	         }
	     });  
		g2.setLayout(new BorderLayout());
		g2.add(arg0, BorderLayout.EAST);
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
    	g2 = new Graph(null, null, -10, -10, 10, 10);
        g2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add contents to the window.
        g2.add(new Runner());
 
        //Display the window.
		g2.setSize(700, 535);
        g2.setVisible(true);
        
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    public void shiftColors()
    {
    	Color c = colors[0];
    	for(int i = 0; i < colors.length - 1; i++)
    	{
    		colors[i] = colors[i+1];
    	}
    	colors[colors.length-1] = c;
    }
    public void openTextArea()
    {
    	String s = "";
    	if(!opened)
    	{
    		for(int i = 0; i < commands.length; i++)
    		{
    			s += commands[i] + "\n";
    		}
    		textArea.setText(s);
    	}
    	g2.update();
        textArea.setVisible(true);
        textArea.setFocusable(true);
        textArea.isDisplayable();
        textArea.grabFocus();
    	opened = true;
    }
    public void closeTextArea()
    {
    	textArea.setText("");
        textArea.setVisible(false);
    	g2.update();
    	opened = false;
    	closeTextArea2();
    }
    public void closeTextArea2()
    {
    	textArea.setText("");
        textArea.setVisible(false);
        textArea.transferFocusBackward();
    	g2.update();
    	opened = false;
    }
    public void display(String s)
    {
    	textArea.setText(s);
        //textArea.setVisible(true);
        //textArea.setFocusable(true);
    	//textArea.grabFocus();
    	g2.update();
    	opened = true;
    }
}