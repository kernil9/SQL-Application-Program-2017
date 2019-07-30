
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JTextField;

public class Controller implements ActionListener
{
	private Model model;
	private LinkedList<String> booksFounded = new LinkedList<String>();
	private float currentMin;
	private int year;
	private View view;
	private int customerID;
	public void actionPerformed(ActionEvent event)
	{
		String action = event.getActionCommand();
		Model model = this.getModel();
    	View view = this.getView();
    	if(action.equalsIgnoreCase("find customer"))
    	{
    		if(model != null)
    		{
    			boolean found = model.find_customer(view.getCid());
    			if(found)
    			{
    				customerID = view.getCid();
    				if(view.isNotFoundON())
    				{
    					view.custNotFoundOFF();
    				}
    				view.customerFound(model.getCustName(),model.getCustCity(),model.getCustID());
    				model.fetch_categories();
    				LinkedList<String> x = model.getCats();
    				String[] y = new String[x.size()];
    				for(int i=0;i<x.size();i++)
    				{
    					y[i] = x.get(i);
    				}
    				view.showCategories(y);
    				/*if(view.getChosenCat() != null)
    				{
    					view.bookTitleFieldON();
    					System.out.println(view.getBookText());
    				}*/
    			}
    			else 
        		{
        			view.custNotFoundON();
        		}
    		}
    	}
    	else if(action.equalsIgnoreCase("ok"))
    	{
    		if(view.getBookSelected()!= null && view.getCatChosen() != null && view.getBookText() != null)
    		{
    			
    			String copy = view.getBookSelected();
    			int year = Integer.parseInt(copy.replaceAll("\\D+","").substring(0, 4)); 
    			this.year = year;
    			float min = model.min_price(view.getBookText(), view.getCatChosen(), year);
    			currentMin = min;
    			view.bookSelected(min);
    		}
    		else if(view.getCatChosen()!= null && view.getBookText() != null && view.getBookSelected() == null)
    		{
				view.promptOneOn();
				booksFounded = model.find_book(view.getBookText(), view.getCatChosen());
    			view.findingBook(model.find_book(view.getBookText(), view.getCatChosen()));
    		}
    	}
    	else if(action.equalsIgnoreCase("proceed"))
    	{
    		if(view.getQ() == 0)
    		{
    			view.noQty();
    		}
    		else if(view.getQ() > 0)
    		{
    			view.lastPage(currentMin);
    		}
    	}
    	else if(action.equalsIgnoreCase("Buy"))
    	{
    		model.insert_purchase(customerID, model.getOfferedBy(), view.getBookText(), year, view.getQ());
    		view.exit();
    	}
    	else if(action.equalsIgnoreCase("back"))
    	{
    		if(view.getPhase() == 3)
    		{
    			view.back3to1();
    			currentMin = 0;
    			view.removeBooksFound(booksFounded);
    		}
    		else if(view.getPhase() == 2)
    		{
    			view.back2to1();
    			currentMin = 0;
    			view.removeBooksFound(booksFounded);
    		}
    	}
	}
	private Model getModel()
    {
	return this.model;
    }

    /**
     * Returns the view.
     * 
     * @return the view.
     */
    private View getView()
    {
	return this.view;
    }
    public void setModel(Model model)
    {
	this.model = model;
    }

    /**
     * Sets the view to the given view.
     * 
     * @param view the view.
     * @pre. view != null
     */
    public void setView(View view)
    {
	this.view = view;
    }

}

