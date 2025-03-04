package e1;

import java.util.Arrays;
import java.util.List;

public class SilverBankAccount implements BankAccount {

    private final CoreBankAccount base = new CoreBankAccount();
    private static final BankAccountRules GOLD = new BankAccountRules(0, 0, 500);
    private static final BankAccountRules BRONZE = new BankAccountRules(1, 100, 0);
    private static final List<BankAccountRules> TYPE = Arrays.asList(BRONZE, GOLD);
    private int selectedType;

    public int getBalance() {
        return base.getBalance();
    }

    public void deposit(int amount) {
        base.deposit(amount);
    }

    public void withdraw(int amount) {
        if (amount > TYPE.get(selectedType).getWithdrawApplicationFee()) {
            amount += TYPE.get(selectedType).getFee();
        }
        if (this.getBalance() + TYPE.get(selectedType).getOverdraftAmount() < amount){
            throw new IllegalStateException("Money exceeds overdraft amount");
        }
        base.withdraw(amount);
    }

    public int getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(int selectedType) {
        if(selectedType >= 0 && selectedType <= TYPE.size()) {
            this.selectedType = selectedType;
        } else {
            throw new IllegalStateException("Invalid selection");
        }
    }
}
