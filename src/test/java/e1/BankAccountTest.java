package e1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankAccountTest {

    private SilverBankAccount silverAccount;
    private BankAccount goldAccount;
    private BankAccount bronzeAccount;

    @BeforeEach
    void init(){
        this.silverAccount = new SilverBankAccount(); //Useless by default is set Bronze account
        this.goldAccount = new GoldBankAccount(new CoreBankAccount());
        this.bronzeAccount = new BronzeBankAccount(new CoreBankAccount());
    }

    @Test
    public void testInitiallyEmpty() {
        assertEquals(0, this.silverAccount.getBalance());
    }

    @Test
    public void testCanDeposit() {
        this.silverAccount.deposit(1000);
        assertEquals(1000, this.silverAccount.getBalance());
    }

    @Test
    public void testCanWithdraw() {
        this.silverAccount.deposit(1000);
        this.silverAccount.withdraw(200);
        assertEquals(799, this.silverAccount.getBalance());
    }

    @Test
    public void testCannotWithdrawMoreThanAvailable(){
        this.silverAccount.deposit(1000);
        assertThrows(IllegalStateException.class, () -> this.silverAccount.withdraw(1200));
    }

    @Test
    public void testGoldAmount() {;
        this.goldAccount.deposit(1000);
        assertEquals(1000, this.goldAccount.getBalance());
    }

    @Test
    public void testGoldAmountWithdrawOverdraftAmount() {
        this.goldAccount.deposit(1000);
        this.goldAccount.withdraw(1200);
        assertEquals(-200, this.goldAccount.getBalance());
    }

    @Test
    public void testGoldAmountWithdrawOverOverdraftAmount() {
        this.goldAccount.deposit(1000);
        Exception exception = assertThrows(IllegalStateException.class, ()->{
            this.goldAccount.withdraw(1501);
        });
        assertEquals("Money exceeds overdraft amount", exception.getMessage());
    }

    @Test
    public void testBronzeAmount() {
        this.bronzeAccount.deposit(1000);
        assertEquals(1000, this.bronzeAccount.getBalance());
    }

    @Test
    public void testBronzeWithdrawOverdraftAmount() {
        this.bronzeAccount.deposit(1000);
        Exception exception = assertThrows(IllegalStateException.class, ()->{
            this.bronzeAccount.withdraw(1001);
        });
        assertEquals("Money exceeds overdraft amount", exception.getMessage());
    }

    @Test
    public void testBronzeWithdrawWithoutFee() {
        this.bronzeAccount.deposit(1000);
        this.bronzeAccount.withdraw(99);
        assertEquals(901, this.bronzeAccount.getBalance());
    }

    @Test
    public void testBronzeWithdrawWithFee() {
        this.bronzeAccount.deposit(1000);
        this.bronzeAccount.withdraw(100);
        assertEquals(899, this.bronzeAccount.getBalance());
    }

}
