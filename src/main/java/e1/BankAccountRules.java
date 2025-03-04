package e1;

public class BankAccountRules {
    private final int fee;
    private final int withdrawApplicationFee;
    private final int overdraftAmount;


    public BankAccountRules(int fee, int withdrawAmountFee, int overdraftAmount) {
        this.fee = fee;
        this.withdrawApplicationFee = withdrawAmountFee;
        this.overdraftAmount = overdraftAmount;
    }

    public int getWithdrawApplicationFee() {
        return withdrawApplicationFee;
    }

    public int getFee() {
        return fee;
    }

    public int getOverdraftAmount() {
        return overdraftAmount;
    }
}
