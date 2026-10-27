# Bank - Abstract Class & Interface

**Name:** Haidar
**NIM:** F1D022410114
**Course:** Pemrograman Berorientasi Objek (PBO), Informatics Engineering, Universitas Mataram
**Topic:** PBO 7 & 8 - Abstract Class and Interface (with Inheritance and Polymorphism)

This project updates the earlier Bank exercise (Account, Customer, Bank, BankDemo). `Account` is no longer a plain class: it became an **abstract class**, and two kinds of account now extend it. Two **interfaces** describe extra capabilities.

## Project Structure

```
BankAbstract/
├── Printable.java        (interface)
├── InterestBearing.java  (interface)
├── Account.java          (abstract class, implements Printable)
├── SavingsAccount.java   (extends Account, implements InterestBearing)
├── CheckingAccount.java  (extends Account)
├── Customer.java         (holds Account[])
├── Bank.java             (holds Customer[], all transaction/printing logic)
└── BankDemo.java         (main)
```

## Sample Output

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

## OOP Concepts

### 1. Abstract Class - `Account`

An abstract class is a _partially complete_ class: it has real state and real behaviour, plus a list of requirements for its subclasses. It cannot be instantiated, so `new Account(...)` is now a compile error.

```java
public abstract class Account implements Printable {
    protected double balance;
    private String accountNumber;

    public Account(double initBalance) { ... }

    public boolean deposit(double amount) { ... }

    public abstract boolean withdraw(double amount);
    public abstract String getAccountType();
}
```

- **Concrete members** (`balance`, constructor, `deposit`, `getBalance`, `printInfo`) are written once and shared by every account type.
- **Abstract methods** (`withdraw`, `getAccountType`) have no body (`;` only). Every account must follow this rule, but each type decides _how_.
- Why `withdraw` is abstract: a savings account only allows withdrawal up to its balance, while a checking account may go into overdraft. Same rule ("you can withdraw"), different implementation.
- `balance` is `protected` (not `private`) so subclasses can change it inside their own `withdraw`.

### 2. Abstract Method & Concrete Subclass

A concrete subclass must implement **every** inherited abstract method, otherwise it must be declared abstract itself.

```java
public class SavingsAccount extends Account implements InterestBearing {
    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) { ... }
    }
    @Override
    public String getAccountType() { return "Tabungan"; }
}

public class CheckingAccount extends Account {
    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && balance + overdraftLimit >= amount) { ... }
    }
    @Override
    public String getAccountType() { return "Giro"; }
}
```

### 3. Inheritance

`SavingsAccount` and `CheckingAccount` both use `extends Account` (single inheritance: one parent only). They inherit `deposit()`, `getBalance()`, `getAccountNumber()` and the constructor logic without rewriting them. Each subclass calls the parent constructor with `super(initBalance)` and then sets up its own field (`interestRate` / `overdraftLimit`).

### 4. Interface

An interface is a contract about _what_ a class can do, never _how_.

```java
public interface Printable {
    void printInfo();
}

public interface InterestBearing {
    double MAX_RATE = 0.10;
    double getInterestRate();
    void addInterest();
    default String interestSummary() {
        return "Bunga " + (getInterestRate() * 100) + "% per periode";
    }
}
```

- Interface members are implicitly `public`; implementing methods must also be `public`.
- `MAX_RATE` is a constant (implicitly `public static final`). `SavingsAccount` uses it to cap the interest rate.
- `interestSummary()` is a **default method** (Java 8): it has a body, and implementing classes can use it as is or override it. `SavingsAccount` uses it as is.
- `Printable` is implemented by the abstract class `Account`, so both account types get `printInfo()` and may override it.
- Only `SavingsAccount` implements `InterestBearing`: interest is a _capability_ (can-do) that `CheckingAccount` does not have.

### 5. Abstract Class vs Interface (why both are used here)

|                    | `Account` (abstract class)             | `InterestBearing` (interface)         |
| ------------------ | -------------------------------------- | ------------------------------------- |
| Relationship       | is-a (a savings account IS an account) | can-do (an account CAN earn interest) |
| State              | has `balance`, `accountNumber`         | constants only                        |
| Constructor        | yes, called via `super(...)`           | never                                 |
| How many per class | only one (`extends`)                   | any number (`implements`)             |

### 6. Polymorphism

One reference type, many behaviours. `Customer` stores `Account[]` and `Bank` methods take `Account`, yet the real objects are `SavingsAccount` or `CheckingAccount`. Java picks the correct method at runtime.

```java
public void doWithdraw(Account acc, double amount) {
    if (acc.withdraw(amount)) { ... }
}
```

- `bank.doWithdraw(savings, 1)` runs the savings rule; `bank.doWithdraw(checking, 600000)` runs the overdraft rule. `Bank` never needs to know which type it received.
- `printCustomers()` calls `printInfo()` on each account, and each type prints its own extra line (interest rate or overdraft limit).
- Adding a new account type later (e.g. `FixedDepositAccount`) requires **no change** in `Bank` or `Customer`.

### 7. Interface Type, `instanceof` and Casting

```java
if (acc instanceof InterestBearing) {
    InterestBearing ib = (InterestBearing) acc;
    ib.addInterest();
}
```

In `addInterestToAll()`, `Bank` loops over all accounts through the `Account` type and only adds interest to those that signed the `InterestBearing` contract. A `SavingsAccount` object is many types at once: `SavingsAccount`, `Account`, `Printable`, `InterestBearing`, and `Object`.

### 8. Method Overriding with `super`

`SavingsAccount.printInfo()` and `CheckingAccount.printInfo()` override the version in `Account`, call `super.printInfo()` to reuse the common line, then add their own detail.

## Changes from the Previous Version

| Before                                   | Now                                                                        |
| ---------------------------------------- | -------------------------------------------------------------------------- |
| `Account` was one concrete class         | `Account` is abstract; `SavingsAccount` and `CheckingAccount` extend it    |
| `new Account(100000)`                    | `new SavingsAccount(100000, 0.05)` / `new CheckingAccount(500000, 200000)` |
| `withdraw` had one fixed rule            | `withdraw` is abstract, each subclass defines its own rule                 |
| No interfaces                            | `Printable` and `InterestBearing`                                          |
| `printCustomers()` printed balances only | `printCustomers()` calls polymorphic `printInfo()`                         |
| -                                        | New `Bank.addInterestToAll()` using `instanceof`                           |
| `Customer.java`                          | Unchanged (it already stores `Account`, so it works with subclasses)       |

## Summary

| Concept                   | Where it is used                                                                            |
| ------------------------- | ------------------------------------------------------------------------------------------- |
| Abstract class            | `Account`                                                                                   |
| Abstract method           | `Account.withdraw()`, `Account.getAccountType()`                                            |
| Inheritance               | `SavingsAccount` / `CheckingAccount` extends `Account`                                      |
| Interface                 | `Printable`, `InterestBearing`                                                              |
| Multiple types per object | `SavingsAccount extends Account implements InterestBearing` (and `Printable` via `Account`) |
| Constant in interface     | `InterestBearing.MAX_RATE`                                                                  |
| Default method            | `InterestBearing.interestSummary()`                                                         |
| Polymorphism              | `Bank.doWithdraw(Account, ...)`, `Bank.printCustomers()`, `Account[]` in `Customer`         |
| `instanceof` + casting    | `Bank.addInterestToAll()`                                                                   |
| Overriding + `super`      | `printInfo()` in both subclasses                                                            |
