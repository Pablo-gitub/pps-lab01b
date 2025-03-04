package e1;

public class BronzeBankAccount extends BaseBankAccount {
    private static final BankAccountRules BRONZE = new BankAccountRules(1, 100, 0);

    public BronzeBankAccount(CoreBankAccount base) {
        super(base, BRONZE);
    }
}
