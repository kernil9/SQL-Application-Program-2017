
public class App 
{
	public static void main(String[] args) 
	{
		Controller c = new Controller();
		View v = new View(c);
		Model m = new Model();
		c.setModel(m);
		c.setView(v);
	}
}
