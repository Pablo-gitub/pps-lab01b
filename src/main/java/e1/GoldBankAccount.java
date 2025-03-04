package e1;

public class GoldBankAccount extends BaseBankAccount {
    private static final BankAccountRules GOLD = new BankAccountRules(0, 0, 500);

    public GoldBankAccount(CoreBankAccount base) {
        super(base, GOLD);
    }
}
