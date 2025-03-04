package e1;

public class SilverBankAccount extends BaseBankAccount {
    private static final BankAccountRules SILVER = new BankAccountRules(1, 0, 0);

    public SilverBankAccount(CoreBankAccount base) {
        super(base, SILVER);
    }
}
