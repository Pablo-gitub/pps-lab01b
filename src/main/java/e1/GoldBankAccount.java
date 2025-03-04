package e1;

public class GoldBankAccount implements BankAccount{
    private static final BankAccountRules GOLD = new BankAccountRules(0, 0, 500);
    private final CoreBankAccount base;

    public GoldBankAccount(CoreBankAccount base) {
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
        if (amount >= GOLD.getWithdrawApplicationFee()) {
            amount += GOLD.getFee();
        }
        if (this.getBalance() + GOLD.getOverdraftAmount() < amount){
            throw new IllegalStateException("Money exceeds overdraft amount");
        }
        base.withdraw(amount);
    }
}
