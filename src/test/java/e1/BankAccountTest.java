package e1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankAccountTest {

    private SilverBankAccount bronzeAccount;
    private SilverBankAccount goldAccount;

    @BeforeEach
    void init(){
        this.bronzeAccount = new SilverBankAccount();
        this.bronzeAccount.setSelectedType(0); //Useless by default is set Bronze account
        this.goldAccount = new SilverBankAccount();
        this.goldAccount.setSelectedType(1);
    }

    @Test
    public void testInitiallyEmpty() {
        assertEquals(0, this.bronzeAccount.getBalance());
    }

    @Test
    public void testCanDeposit() {
        this.bronzeAccount.deposit(1000);
        assertEquals(1000, this.bronzeAccount.getBalance());
    }

    @Test
    public void testCanWithdraw() {
        this.bronzeAccount.deposit(1000);
        this.bronzeAccount.withdraw(200);
        assertEquals(799, this.bronzeAccount.getBalance());
    }

    @Test
    public void testCannotWithdrawMoreThanAvailable(){
        this.bronzeAccount.deposit(1000);
        assertThrows(IllegalStateException.class, () -> this.bronzeAccount.withdraw(1200));
    }

    @Test
    public void testGoldAmount() {
        this.goldAccount.setSelectedType(1);
        this.goldAccount.deposit(1000);
        assertEquals(1000, this.goldAccount.getBalance());
    }

    @Test
    public void testGoldAmountWithdrawOverdraftAmount() {
        this.goldAccount.setSelectedType(1);
        this.goldAccount.deposit(1000);
        this.goldAccount.withdraw(1200);
        assertEquals(-200, this.goldAccount.getBalance());
    }

    @Test
    public void testGoldAmountWithdrawOverOverdraftAmount() {
        this.goldAccount.setSelectedType(1);
        this.goldAccount.deposit(1000);
        Exception exception = assertThrows(IllegalStateException.class, ()->{
            this.goldAccount.withdraw(1501);
        });
        assertEquals("Money exceeds overdraft amount", exception.getMessage());
    }

}
