public class Bank {
    private Customer[] customers;
    private int numberOfCustomers;

    public Bank() {
        customers = new Customer[10];
        numberOfCustomers = 0;
    }

    public void addCustomer(String f, String l) {
        customers[numberOfCustomers] = new Customer(f, l);
        numberOfCustomers++;
    }

    public int getNumOfCustomers() {
        return numberOfCustomers;
    }

    public Customer getCustomer(int index) {
        return customers[index];
    }

    public void doDeposit(Account acc, double amount) {
        if (acc.deposit(amount)) {
            System.out.println("Deposit " + amount + "     : berhasil");
        } else {
            System.out.println("Deposit " + amount + "     : gagal");
        }
        System.out.println("Current Balance     : " + acc.getBalance());
        System.out.println();
    }

    public void doWithdraw(Account acc, double amount) {
        if (acc.withdraw(amount)) {
            System.out.println("Withdraw " + amount + "    : berhasil");
        } else {
            System.out.println("Withdraw " + amount + "    : gagal (saldo kurang)");
        }
        System.out.println("Current Balance     : " + acc.getBalance());
        System.out.println();
    }

    public void addInterestToAll() {
        System.out.println("=== Tambah Bunga ===");
        for (int i = 0; i < numberOfCustomers; i++) {
            Customer cust = customers[i];
            for (int j = 0; j < cust.getNumOfAccounts(); j++) {
                Account acc = cust.getAccount(j);
                if (acc instanceof InterestBearing) {
                    InterestBearing ib = (InterestBearing) acc;
                    ib.addInterest();
                    System.out.println(acc.getAccountNumber() + " : bunga ditambahkan, saldo " + acc.getBalance());
                } else {
                    System.out.println(acc.getAccountNumber() + " : tidak berbunga");
                }
            }
        }
        System.out.println();
    }

    public void printCustomers() {
        System.out.println("=== Daftar Nasabah ===");
        for (int i = 0; i < numberOfCustomers; i++) {
            Customer cust = customers[i];
            System.out.println((i + 1) + ". " + cust.getFirstName() + " " + cust.getLastName());
            for (int j = 0; j < cust.getNumOfAccounts(); j++) {
                System.out.print("   ");
                cust.getAccount(j).printInfo();
            }
        }
    }
}
