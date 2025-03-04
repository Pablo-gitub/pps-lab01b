package e1;

public class BronzeBankAccount implements BankAccount {

    private static final BankAccountRules BRONZE = new BankAccountRules(1, 100, 0);
    private final CoreBankAccount base;

    public BronzeBankAccount(CoreBankAccount base) {
        this.base = base;
    }

    @Override
    public int getBalance() {
        return base.getBalance();
    }

    @Override
    public void deposit(int amount) {
        base.deposit(amount);
    }

    @Override
    public void withdraw(int amount) {
        if (amount >= BRONZE.getWithdrawApplicationFee()) {
            amount += BRONZE.getFee();
        }
        if (this.getBalance() + BRONZE.getOverdraftAmount() < amount){
            throw new IllegalStateException("Money exceeds overdraft amount");
        }
        base.withdraw(amount);
    }
}
