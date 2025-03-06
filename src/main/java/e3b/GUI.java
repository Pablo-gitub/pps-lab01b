package e3b;

import e3b.Logics;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

public class GUI extends JFrame {

    @Serial
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Pair<Integer,Integer>> buttons = new HashMap<>();
    private final Logics logics;

    public GUI(int size) {
        this.logics = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);

        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(BorderLayout.CENTER,panel);

        ActionListener leftClick = e -> {
            JButton button = (JButton) e.getSource();
            Pair<Integer, Integer> pos = buttons.get(button);
            boolean bombFound = logics.selectCell(pos.getX(), pos.getY());
            drawBoard();
            if (bombFound) {
                endGameLoss(button);
            } else if (logics.isVictory()) {
                endGameWin();
            }
        };

        MouseInputAdapter rightClick = new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    JButton button = (JButton) e.getSource();
                    Pair<Integer, Integer> pos = buttons.get(button);
                    logics.toggleFlag(pos.getX(), pos.getY());
                    drawBoard();
                }
            }
        };

        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                jb.addActionListener(leftClick);
                jb.addMouseListener(rightClick);
                this.buttons.put(jb,new Pair<>(i,j));
                panel.add(jb);
            }
        }
        this.drawBoard();
        this.setVisible(true);
    }

    private void endGameLoss(JButton bombButton) {
        // Reveal bombs on the board
        for (Map.Entry<JButton, Pair<Integer, Integer>> entry : buttons.entrySet()) {
            JButton button = entry.getKey();
            Pair<Integer, Integer> pos = entry.getValue();
            if (logics.isBomb(pos.getX(), pos.getY())) {
                button.setText("*");
            }
        }
        // Disable only the button that triggered the loss
        bombButton.setEnabled(false);
        JOptionPane.showMessageDialog(this, "You lost!!");
        // Optionally, exit the application: System.exit(0);
    }

    private void endGameWin() {
        JOptionPane.showMessageDialog(this, "You won!!");
        System.exit(0);
    }

    private void drawBoard() {
        for (Map.Entry<JButton, Pair<Integer, Integer>> entry : buttons.entrySet()) {
            JButton button = entry.getKey();
            Pair<Integer, Integer> pos = entry.getValue();
            button.setText(logics.getCellDisplayValue(pos.getX(), pos.getY()));
            button.setEnabled(logics.isCellEnabled(pos.getX(), pos.getY()));
        }
    }

}