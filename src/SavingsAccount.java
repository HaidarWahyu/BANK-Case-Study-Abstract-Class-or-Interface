public class SavingsAccount extends Account implements InterestBearing {
    private double interestRate;

    public SavingsAccount(double initBalance, double interestRate) {
        super(initBalance);
        if (interestRate > MAX_RATE) {
            this.interestRate = MAX_RATE;
        } else {
            this.interestRate = interestRate;
        }
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance = balance - amount;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getAccountType() {
        return "Tabungan";
    }

    @Override
    public double getInterestRate() {
        return interestRate;
    }

    @Override
    public void addInterest() {
        balance = balance + (balance * interestRate);
    }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("   " + interestSummary());
    }
}
