package backOffice;

import java.util.Vector;

import common.Client;
import common.Operator;

public class OperatorManager {
	/* The list of all operators in the system */
	private Vector<Client> operatorList;
	
	/* Constructor */
	public OperatorManager(){
		operatorList = new Vector<Client>();
	}
	
	/* Adds a new operator to the system after checking if it already exists */
	public void addOperator(Operator operator){
		operatorList.add(operator);
		
	}
	
	/* Deletes an operator from the system */
	public void removeOperator(Operator operator){
		operatorList.remove(operator);
	}
	
	/* Search operator to avoid operators with the same login name */
	public Operator searchOperator(String name){
		for(int i = 0; i < operatorList.size(); i++){
			if(operatorList.get(i).getName().equals(name)){
				return (Operator) operatorList.get(i);
			}
		}
		
		return null;
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
	
	/* Tries to authenticate the operator */
	public String loginOperator(String name, String password){
		Operator op = searchOperator(name);
		
		/* The operator was found and has introduced a correct password. */
		if(op != null && op.getPassword().equals(password)){
			return "Login successful";
		}
		return "Login was unsuccessful";
	}

	public Vector<Client> getOperatorList() {
		return operatorList;
	}

	public void setOperatorList(Vector<Client> operatorList) {
		this.operatorList = operatorList;
	}	
	
}