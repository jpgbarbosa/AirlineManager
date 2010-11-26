package backOffice;

import java.util.Date;
import java.util.Vector;
import org.prevayler.*;

import common.Operator;

public class OperatorManager {
	/* The list of all operators in the system */
	private Vector<Operator> operatorList;
	private Prevayler prevayler;
	public Prevayler getPrevayler() {
		return prevayler;
	}
	/* Constructor */
	@SuppressWarnings("unchecked")
	public OperatorManager(){
		super();
		
		try {
			prevayler = PrevaylerFactory.createPrevayler(new Vector<Operator>(), "OperatorsList");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Something went really bad!");
			System.exit(0);
		} 
		operatorList=(Vector <Operator>) (prevayler.prevalentSystem());
		
		//operatorList = new Vector<Client>();
	}
	
	/**
	 * 
	 * Write Transactions
	 */
	
	/* Adds a new operator to the system after checking if it already exists */
	public void addOperator(Operator operator){
		prevayler.execute(new addOperator(operator));
		//operatorList.add(operator);
		
	}
	
	/* Deletes an operator from the system */
	public void removeOperator(Operator operator){
		prevayler.execute(new removeOperator(operator));
		//operatorList.remove(operator);
	}
	
	/* Tries to register a new operator */
	public String registerOperator(String comp, String name, String addr, String phone, String mail,String password){
		Operator op = searchOperator(name);
		/* We haven't found any operator in the list. */
		if(op == null){
			addOperator(new Operator(comp, name, addr, phone, mail, password));
			return "Registration was successful";
		}
		return "Registration was unsuccessful";
	}
	
	/**
	 * 
	 * Read Transactions
	 */
	
	/* Search operator to avoid operators with the same login name */
	public Operator searchOperator(String name){
		for(int i = 0; i < operatorList.size(); i++){
			if(operatorList.get(i).getName().equals(name)){
				return (Operator) operatorList.get(i);
			}
		}
		
		return null;
	}
	
	
	
	/* Tries to authenticate the operator */
	public String loginOperator(String name, String password){
		Operator op = searchOperator(name);
		
		/* The operator was found and has introduced a correct password. */
		if(op != null && op.getPassword().equals(password)){
			return "Login successful";
		}
		return "Login was unsuccessful";
	}

	public Vector<Operator> getOperatorList() {
		return operatorList;
	}
	
	
}


class addOperator implements Transaction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Operator operator;
	


	public addOperator(Operator operator){
		this.operator=operator;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeOn(Object arg0, Date arg1) {
		
		((Vector<Operator>)arg0).add(operator);
	}
	
	
}

class removeOperator implements Transaction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Operator operator;
	


	public removeOperator(Operator operator){
		this.operator=operator;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeOn(Object arg0, Date arg1) {
		
		((Vector<Operator>)arg0).remove(operator);
	}
	
	
}
