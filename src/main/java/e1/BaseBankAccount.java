package e1;

public abstract class BaseBankAccount implements BankAccount {
    private final BankAccountRules rules;
    private final CoreBankAccount base;

    protected BaseBankAccount(CoreBankAccount base, BankAccountRules rules) {
        this.base = base;
        this.rules = rules;
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
        if (amount >= rules.getWithdrawApplicationFee()) {
            amount += rules.getFee();
        }
        if (this.getBalance() + rules.getOverdraftAmount() < amount) {
            throw new IllegalStateException("Money exceeds overdraft amount");
        }
        base.withdraw(amount);
    }
}
