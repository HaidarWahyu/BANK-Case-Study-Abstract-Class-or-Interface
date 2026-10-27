public class BankDemo {
    public static void main(String[] args) {
        Bank bank = new Bank();

        bank.addCustomer("Haidar", "Rahman");
        bank.addCustomer("Budi", "Santoso");
        bank.addCustomer("Siti", "Aminah");

        bank.getCustomer(0).setAccount(new SavingsAccount(100000, 0.05));
        bank.getCustomer(0).setAccount(new CheckingAccount(500000, 200000));
        bank.getCustomer(1).setAccount(new SavingsAccount(250000, 0.03));
        bank.getCustomer(2).setAccount(new CheckingAccount(0, 100000));

        System.out.println("Welcome TO the Bank");
        System.out.println("Jumlah nasabah: " + bank.getNumOfCustomers());
        System.out.println();

        Customer c = bank.getCustomer(0);
        Account savings = c.getAccount(0);
        Account checking = c.getAccount(1);

        System.out.println("Nasabah             : " + c.getFirstName() + " " + c.getLastName());
        System.out.println("Rekening            : " + savings.getAccountType());
        System.out.println("Current Balance     : " + savings.getBalance());
        System.out.println();

        bank.doDeposit(savings, 50000);
        bank.doWithdraw(savings, 100000);
        bank.doWithdraw(savings, 1);

        System.out.println("Rekening            : " + checking.getAccountType());
        System.out.println("Current Balance     : " + checking.getBalance());
        System.out.println();

        bank.doWithdraw(checking, 600000);
        bank.doWithdraw(checking, 200000);

        bank.addInterestToAll();
        bank.printCustomers();
    }
}
