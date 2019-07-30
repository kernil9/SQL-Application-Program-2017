

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class View extends JFrame
{
	private JPanel buttons = new JPanel();
	private JFrame view = new JFrame("Application Program 3421");
	private int qnty = 0;
	private int booksFoundSize = 0;
	String[] z = {""};
	JLabel custDisplay = new JLabel("");
	JLabel minPriceDisplay = new JLabel("");
	JLabel qtyPrompt = new JLabel("");
	JLabel NoqtyPrompt = new JLabel("");
	JLabel askingPrice = new JLabel("");
	JLabel exit = new JLabel("");
	final JComboBox<String> cb = new JComboBox<String>(z);
    JComboBox<String> booksFound = new JComboBox<String>(z);
	private JTextField field = new JTextField(10);
	private JTextField qty = new JTextField(10);
	private JTextField bookTitle = new JTextField(10);
	private int cid = 0;
	private Object chosenCat = null;
	private JButton x = new JButton("Find Customer");
	private JButton buy = new JButton("Buy");
	private JButton back = new JButton("Back");
	private JButton ok = new JButton("Ok");
	private JButton proceed = new JButton("Proceed");
	private JButton findBook = new JButton("Find Book");
	private JLabel custNotFound = new JLabel("Not found, Please try again");
	private JLabel chooseAcat = new JLabel("Please choose a category");
	JLabel prompt = new JLabel("Please choose a category and type book title then press ok");
	JLabel prompt1 = new JLabel("Book not found please put in a new combination and hit ok");
	JLabel prompt2 = new JLabel("Please choose a book from the list and enter quantity wanted");
	JLabel lbl = new JLabel("Enter your ID");
	private Object bookText = null;
	private Object bookSelected = null;
	private int phase = 1;
	public View(Controller controller)
	{
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setSize(400, 400);
		buttons.add(lbl,BorderLayout.NORTH);	
		buttons.add(field,BorderLayout.CENTER);
		buttons.add(x, BorderLayout.SOUTH);
		x.setActionCommand("Find Customer");
		x.addActionListener(controller);
		ok.setActionCommand("ok");
		ok.addActionListener(controller);
		buy.addActionListener(controller);
		buy.setActionCommand("buy");
		back.addActionListener(controller);
		back.setActionCommand("back");
		view.add(buttons);
		cb.setVisible(true);
	    buttons.add(cb);
	    buttons.add(ok);
	    ok.setVisible(true);
	    buttons.add(bookTitle);
	    bookTitle.setVisible(false);
	    buttons.add(findBook);
	    findBook.setVisible(false);
	    buttons.add(booksFound);
	    booksFound.setVisible(false);
	    buttons.add(custNotFound);
	    buttons.add(chooseAcat);
	    proceed.setActionCommand("proceed");
	    proceed.addActionListener(controller);
	    chooseAcat.setVisible(false);
	    custNotFound.setVisible(false);
		view.setVisible(true);
		field.setActionCommand("field");
		//buttons.add(back);
		//buttons.add(buy);
		buy.setVisible(false);
		back.setVisible(false);
		buttons.add(qty);
		qty.setVisible(false);
	  
	/**
	 * @return the field
	 */
		qty.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				qnty = Integer.parseInt(qty.getText());
			}
		});
	field.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae)
		{
			cid = Integer.parseInt(field.getText());
		}
	});
	
	cb.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent ae)
		{
			chosenCat = cb.getSelectedItem();
		}
	});
	
	booksFound.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent ae)
		{
			if(booksFound != null)
			{
				bookSelected = booksFound.getSelectedItem();
			}
		}
	});

	bookTitle.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae)
		{
			bookText = bookTitle.getText();
		}
	});
	
	}
	public int getQ()
	{
		return qnty;
	}
	public String getBookSelected()
	{
		if(bookSelected != null)
		return bookSelected.toString();
		else return null;
	}
	public String getCatChosen()
	{
		if(chosenCat != null)
		return chosenCat.toString();
		else return null;
	}
	public JTextField getField() {
		return field;
	}
	
	public String getBookText()
	{
		if(bookText != null)
		return bookText.toString();
		else return null;
	}
	/**
	 * @param field the field to set
	 */
	public void setField(JTextField field) {
		this.field = field;
	}
	public void showCategories(String[] x)
	{
		
			for(int i = 0; i < x.length;i++)
				cb.addItem(x[i]);
		
		
	}
	public int getCid()
	{
		return cid;
	}
	
	public Object getChosenCat()
	{
		if(chosenCat != null)
		{
			return chosenCat.toString();
		}
		else
		{
			return null;
		}
	}
	/*
	 * Removes the find customer button
	 */
	public void customerFound(String z, String y, int i)
	{
		x.setVisible(false);
	    custDisplay = new JLabel("Customer " + i
	    		+" found \n Name: " + z 
	    		+" \n City: " + y);
	    
	    buttons.add(custDisplay);
	    buttons.add(prompt);
	    custDisplay.setVisible(true);
	    prompt.setVisible(true);
	    field.setVisible(false);
	    lbl.setVisible(false);
	    bookTitle.setVisible(true);
		
	}
	
	public void custNotFoundON()
	{
		custNotFound.setVisible(true);
	}
	
	public void custNotFoundOFF()
	{
		custNotFound.setVisible(false);
	}
	
	public boolean isNotFoundON()
	{
		return custNotFound.isVisible();
	}
	public void findingBook(LinkedList<String> booksFound)
	{
		if(booksFound.size()<=0)
		{
			buttons.add(prompt1);
			
			prompt1.setVisible(true);
			prompt.setVisible(false);
		}
		else
		{
			cb.setVisible(false);
			bookTitle.setVisible(false);
			buttons.add(prompt2);
			prompt2.setVisible(true);
			prompt.setVisible(false);
			setBooksFound(booksFound);
			this.booksFound.setVisible(true);
		}	
	}
	public void promptOneOn()
	{
		if(prompt1.isVisible())
		{
			prompt1.setVisible(false);
		}
	}
	public void bookSelected(float min)
	{
		booksFound.setVisible(false);
		prompt1.setVisible(false);
		prompt2.setVisible(false);
		ok.setVisible(false);
		qty.setVisible(true);
		minPriceDisplay = new JLabel("The minimum price for this book is " + min);
		qtyPrompt = new JLabel("Please enter the quantity");
		buttons.add(minPriceDisplay);
		buttons.add(qtyPrompt);
		buttons.add(proceed);
		buttons.add(buy);
		buttons.add(back);
		proceed.setVisible(true);
		back.setVisible(true);
		minPriceDisplay.setVisible(true);
		qtyPrompt.setVisible(true);
		//booksFound.removeAllItems();
		phase = 2;
	}
	
	public void noQty()
	{
		qtyPrompt.setVisible(false);
		NoqtyPrompt = new JLabel("No quantity entered please enter a quantity");
		buttons.add(NoqtyPrompt);
		NoqtyPrompt.setVisible(true);
	}
	public void lastPage(float min)
	{
		if(NoqtyPrompt.isVisible())
		{
			NoqtyPrompt.setVisible(false);
		}
		qtyPrompt.setVisible(false);
		proceed.setVisible(false);
		qty.setVisible(false);
		minPriceDisplay.setVisible(false);
		askingPrice = new JLabel("The asking price for " + qnty + " copies of "+bookText+" is " + (min * qnty));
		buttons.add(askingPrice);
		
		askingPrice.setVisible(true);
		buy.setVisible(true);
		phase = 3;
	}
	public void back2to1()
	{
		booksFound.setVisible(false);
		proceed.setVisible(false);
		back.setVisible(false);
		minPriceDisplay.setVisible(false);
		qtyPrompt.setVisible(false);
		prompt1.setVisible(false);
		prompt2.setVisible(false);
		qty.setVisible(false);
		qnty = 0;
		cb.setVisible(true);
		ok.setVisible(true);
		bookTitle.setVisible(true);
		prompt.setVisible(true);
		bookSelected = null;
		phase = 1;
	}
	public void back3to1()
	{
		bookText = null;
		chosenCat = null;
		buy.setVisible(false);
		back.setVisible(false);
		cb.setVisible(true);
		ok.setVisible(true);
		bookTitle.setVisible(true);
		prompt.setVisible(true);
		askingPrice.setVisible(false);
		qnty = 0;
		bookSelected = null;
		phase = 1;
	}
	public void exit()
	{
		askingPrice.setVisible(false);
		buy.setVisible(false);
		back.setVisible(false);
		exit = new JLabel("Thank you for your purchase the database has been updated");
		buttons.add(exit);
		exit.setVisible(true);
	}
	public int getPhase()
	{
		return phase;
	}
	public void bookTitleFieldON()
	{
		bookTitle.setVisible(true);
	}
	
	public void enterIDtextOff()
	{
		lbl.setVisible(false);
		field.setVisible(false);
	}
	
	public void setBooksFound(LinkedList<String> z)
	{
		if(z != null)
		{
			for(int i=0;i<z.size();i++)
			{
				booksFound.addItem(z.get(i));
			}
		}
	}
	
	public void removeBooksFound(LinkedList<String> z)
	{
		if(z != null)
		{
			for(int i=0;i<z.size();i++)
			{
				booksFound.removeItem(z.get(i));
			}
			bookSelected = null;
		}
		else
		{
			System.out.println("NO");
		}
	}
}
