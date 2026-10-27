# Bank

**Name:** Haidar Wahyu Yasari
**NIM:** F1D02410114

This is the Bank exercise updated with abstract class, interface, inheritance and polymorphism. `Account` is now abstract, and there are two types of account (Savings and Checking).

## Files

```
BankAbstract/
├── Printable.java        (interface)
├── InterestBearing.java  (interface)
├── Account.java          (abstract class)
├── SavingsAccount.java   (extends Account)
├── CheckingAccount.java  (extends Account)
├── Customer.java
├── Bank.java
└── BankDemo.java
```

## Output

```
Welcome TO the Bank
Jumlah nasabah: 3

Nasabah             : Haidar Rahman
Rekening            : Tabungan
Current Balance     : 100000.0

Deposit 50000.0     : berhasil
Current Balance     : 150000.0

Withdraw 100000.0    : berhasil
Current Balance     : 50000.0

Withdraw 1.0    : berhasil
Current Balance     : 49999.0

Rekening            : Giro
Current Balance     : 500000.0

Withdraw 600000.0    : berhasil
Current Balance     : -100000.0

Withdraw 200000.0    : gagal (saldo kurang)
Current Balance     : -100000.0

=== Tambah Bunga ===
ACC-1001 : bunga ditambahkan, saldo 52498.95
ACC-1002 : tidak berbunga
ACC-1003 : bunga ditambahkan, saldo 257500.0
ACC-1004 : tidak berbunga

=== Daftar Nasabah ===
1. Haidar Rahman
   ACC-1001 (Tabungan) : 52498.95
   Bunga 5.0% per periode
   ACC-1002 (Giro) : -100000.0
   Batas overdraft : 200000.0
2. Budi Santoso
   ACC-1003 (Tabungan) : 257500.0
   Bunga 3.0% per periode
3. Siti Aminah
   ACC-1004 (Giro) : 0.0
   Batas overdraft : 100000.0
```

## Explanation

**1. Abstract class** - `Account` is abstract so we can't do `new Account()`. It has normal methods (`deposit`) and abstract methods (no body) that the subclasses must write.

```java
public abstract class Account implements Printable {
    public abstract boolean withdraw(double amount);
    public abstract String getAccountType();
}
```

`withdraw` is abstract because each account has a different rule: Savings can't go below 0, Checking can go into overdraft.

**2. Inheritance** - `SavingsAccount` and `CheckingAccount` use `extends Account`, so they get `deposit()` and `getBalance()` without rewriting them. They call `super(initBalance)` in the constructor.

```java
public class SavingsAccount extends Account implements InterestBearing
public class CheckingAccount extends Account
```

**3. Interface** - an interface only says what a class can do, not how. Both methods are public automatically.

```java
public interface Printable {
    void printInfo();
}

public interface InterestBearing {
    double MAX_RATE = 0.10;
    double getInterestRate();
    void addInterest();
    default String interestSummary() { ... }
}
```

- `Printable` is implemented by `Account`, so every account can print its info.
- `InterestBearing` is only for `SavingsAccount` (Checking has no interest).
- `MAX_RATE` is a constant, `interestSummary()` is a default method (it has a body).

**4. Polymorphism** - `Bank` and `Customer` only use the type `Account`, but the real object can be Savings or Checking. Java calls the right `withdraw()` automatically.

```java
public void doWithdraw(Account acc, double amount) {
    if (acc.withdraw(amount)) { ... }
}
```

`printInfo()` also works this way: each account type prints its own extra line.

**5. instanceof** - in `addInterestToAll()`, only accounts that implement `InterestBearing` get interest.

```java
if (acc instanceof InterestBearing) {
    InterestBearing ib = (InterestBearing) acc;
    ib.addInterest();
}
```

## Summary

| Concept | Used in |
|---|---|
| Abstract class | `Account` |
| Abstract method | `withdraw()`, `getAccountType()` |
| Inheritance | `SavingsAccount`, `CheckingAccount` extends `Account` |
| Interface | `Printable`, `InterestBearing` |
| Polymorphism | `Bank.doWithdraw()`, `printCustomers()` |
| instanceof | `Bank.addInterestToAll()` |
