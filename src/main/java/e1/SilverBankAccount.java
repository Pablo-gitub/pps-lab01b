package e1;

public class SilverBankAccount implements BankAccount {
    private static final BankAccountRules SILVER = new BankAccountRules(1, 0, 0);
    private final CoreBankAccount base;

    public SilverBankAccount(CoreBankAccount base) {
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
        if (amount >= SILVER.getWithdrawApplicationFee()) {
            amount += SILVER.getFee();
        }
        if (this.getBalance() + SILVER.getOverdraftAmount() < amount){
            throw new IllegalStateException("Money exceeds overdraft amount");
        }
        base.withdraw(amount);
    }
}
