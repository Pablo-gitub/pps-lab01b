package e3b;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import java.io.Serial;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

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
            final Pair<Integer,Integer> pos = buttons.get(bt);// call the logic here to tell it that cell at 'pos' has been seleced
            logics.leftClick(pos);
            if (logics.isGameOver()) {
                quitGame();
                JOptionPane.showMessageDialog(this, "You lost!!");
            } else if (logics.isVictory()) {
                quitGame();
                JOptionPane.showMessageDialog(this, "You won!!");
                System.exit(0);
            }
            drawBoard();
        };

        MouseInputListener onRightClick = new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                final JButton bt = (JButton)e.getSource();
                if (bt.isEnabled()){
                    final Pair<Integer,Integer> pos = buttons.get(bt);
                    // call the logic here to put/remove a flag
                    logics.rightClick(pos);
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
    }

    private void drawBoard() {
        for (var entry: this.buttons.entrySet()) {
            JButton jb = entry.getKey();
            Pair<Integer, Integer> pos = entry.getValue();
            jb.setText(logics.stringCell(pos.getX(), pos.getY()));
            jb.setEnabled(logics.cellState(pos.getX(), pos.getY()));
        }
    }

}