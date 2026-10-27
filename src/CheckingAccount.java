public class CheckingAccount extends Account {
    private double overdraftLimit;

    public CheckingAccount(double initBalance, double overdraftLimit) {
        super(initBalance);
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && balance + overdraftLimit >= amount) {
            balance = balance - amount;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getAccountType() {
        return "Giro";
    }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("   Batas overdraft : " + overdraftLimit);
    }
}
