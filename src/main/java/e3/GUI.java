package e3;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import java.io.Serial;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.List;

public class GUI extends JFrame {

    @Serial
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton,Pair<Integer,Integer>> buttons = new HashMap<>();
    private final Logics logics;

    public GUI(int size) {
        this.logics = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);

        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(BorderLayout.CENTER,panel);

        ActionListener onClick = (e)->{
            final JButton bt = (JButton)e.getSource();
            final Pair<Integer,Integer> pos = buttons.get(bt);
            logics.hit(pos.getX(), pos.getY());
            boolean aMineWasFound = logics.getGame().isABomb(pos); // call the logic here to tell it that cell at 'pos' has been seleced
            if (aMineWasFound) {
                quitGame();
                JOptionPane.showMessageDialog(this, "You lost!!");
            } else {
                drawBoard();
            }
            boolean isThereVictory = logics.getGame().winGame();// call the logic here to ask if there is victory
            if (isThereVictory){
                quitGame();
                JOptionPane.showMessageDialog(this, "You won!!");
                System.exit(0);
            }
        };

        MouseInputListener onRightClick = new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                final JButton bt = (JButton)e.getSource();
                if (bt.isEnabled()){
                    final Pair<Integer,Integer> pos = buttons.get(bt);
                    logics.rightClick(pos.getX(),pos.getY());
                }
                drawBoard();
            }
        };

        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                jb.addActionListener(onClick);
                jb.addMouseListener(onRightClick);
                this.buttons.put(jb,new Pair<>(i,j));
                panel.add(jb);
            }
        }
        this.drawBoard();
        this.setVisible(true);
    }

    private void quitGame() {
        this.drawBoard();

        List<Pair<Integer,Integer>> bombs = this.logics.getGame().getBombs();
        for (var entry: this.buttons.entrySet()) {
            Pair<Integer, Integer> pos = entry.getValue();
            JButton bt = entry.getKey();

            if (bombs.contains(pos)) {
                bt.setText("*");
            }
        }
    }

    private void drawBoard() {
        List<Pair<Integer,Integer>> disableElement = this.logics.getDisableElement();
        for (var entry: this.buttons.entrySet()) {
            Pair<Integer, Integer> pos = entry.getValue();
            JButton bt = entry.getKey();
            if(this.logics.getGame().getFlags().contains(pos)){
                bt.setText("F");
            } else if (disableElement.contains(pos)) {
                if(this.logics.getGame().getBombs().contains(pos)){
                    bt.setText("*");
                    bt.setEnabled(false);
                } else {
                    int closeBombs = this.logics.getGame().closeBombs(pos);
                    bt.setText(closeBombs == 0 ? "" : String.valueOf(closeBombs));
                    bt.setEnabled(false);
                }
            }
        }
    }

}