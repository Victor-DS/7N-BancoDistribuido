package sistemas.distribuidos.projeto.serverless.victor.model.output;

public class BankAccount {
	
	private int id;
	private double balance;
	private boolean isLocked;
	
	public BankAccount(int id, double balance, boolean isLocked) {
		super();
		this.id = id;
		this.balance = balance;
		this.isLocked = isLocked;
	}

	public BankAccount(int id) {
		super();
		this.id = id;
	}

	public BankAccount() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	
}
