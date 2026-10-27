public abstract class Account implements Printable {
    private static int counter = 1000;

    protected double balance;
    private String accountNumber;

    public Account(double initBalance) {
        balance = initBalance;
        counter++;
        accountNumber = "ACC-" + counter;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public boolean deposit(double amount) {
        if (amount > 0) {
            balance = balance + amount;
            return true;
        } else {
            return false;
        }
    }

    public abstract boolean withdraw(double amount);

    public abstract String getAccountType();

    @Override
    public void printInfo() {
        System.out.println(accountNumber + " (" + getAccountType() + ") : " + balance);
    }
}
