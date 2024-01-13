import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JOptionPane;

public class TTT1 extends JFrame implements ItemListener, ActionListener {
    int i, j, ii, jj, x, y, yesnull;
    int[][] a = { { 10, 1, 2, 3, 11 }, { 10, 1, 4, 7, 11 }, { 10, 1, 5, 9, 11 }, { 10, 2, 5, 8, 11 },
            { 10, 3, 5, 7, 11 }, { 10, 3, 6, 9, 11 }, { 10, 4, 5, 6, 11 }, { 10, 7, 8, 9, 11 } };
    int[][] a1 = { { 10, 1, 2, 3, 11 }, { 10, 1, 4, 7, 11 }, { 10, 1, 5, 9, 11 }, { 10, 2, 5, 8, 11 },
            { 10, 3, 5, 7, 11 }, { 10, 3, 6, 9, 11 }, { 10, 4, 5, 6, 11 }, { 10, 7, 8, 9, 11 } };

    boolean state, type, set;

    Icon ic1, ic2, icon, ic11, ic22;
    Checkbox c1, c2;
    JButton[] b = new JButton[9];
    JButton reset;

    private int playerScore, computerScore;
    public void showButton() {
        x = 10;
        y = 10;
        j = 0;

        for (i = 0; i <= 8; i++, x += 100, j++) {
            b[i] = new JButton();

            if (j == 3) {
                j = 0;
                y += 100;
                x = 10;
            }

            b[i].setBounds(x, y, 100, 100);
            add(b[i]);
            b[i].addActionListener(this);
        }

        reset = new JButton("RESET");
        reset.setBounds(100, 350, 100, 50);
        add(reset);
        reset.addActionListener(this);
    }

    public void check(int num1) {
        for (ii = 0; ii <= 7; ii++) {
            for (jj = 1; jj <= 3; jj++) {
                if (a[ii][jj] == num1) {
                    a[ii][4] = 11;
                }
            }
        }
    }

    public void complogic(int num) {
        for (i = 0; i <= 7; i++) {
            for (j = 1; j <= 3; j++) {
                if (a[i][j] == num) {
                    a[i][0] = 11;
                    a[i][4] = 10;
                }
            }
        }

        for (i = 0; i <= 7; i++) {
            set = true;

            if (a[i][4] == 10) {
                int count = 0;

                for (j = 1; j <= 3; j++) {
                    if (b[(a[i][j] - 1)].getIcon() != null) {
                        count++;
                    } else {
                        yesnull = a[i][j];
                    }
                }

                if (count == 2) {
                    b[yesnull - 1].setIcon(ic2);
                    this.check(yesnull);
                    set = false;
                    break;
                }
            } else if (a[i][0] == 10) {
                for (j = 1; j <= 3; j++) {
                    if (b[(a[i][j] - 1)].getIcon() == null) {
                        b[(a[i][j] - 1)].setIcon(ic2);
                        this.check(a[i][j]);
                        set = false;
                        break;
                    }
                }

                if (!set)
                    break;
            }

            if (!set)
                break;
        }
    }

    TTT1() {
        super("Edited By Firza Ramadhani - 154");

        CheckboxGroup cbg = new CheckboxGroup();
        c1 = new Checkbox("vs computer", cbg, false);
        c2 = new Checkbox("vs friend", cbg, false);
        c1.setBounds(120, 80, 100, 40);
        c2.setBounds(120, 150, 100, 40);
        add(c1);
        add(c2);
        c1.addItemListener(this);
        c2.addItemListener(this);

        state = true;
        type = true;
        set = true;
        ic1 = new ImageIcon("img1.png");
        ic2 = new ImageIcon("img2.png");
        ic11 = new ImageIcon("img1.png");
        ic22 = new ImageIcon("img2.png");

        setLayout(null);

        // Atur warna latar belakang
        getContentPane().setBackground(new Color(240, 240, 240, 255));

        // Tambahkan label untuk judul
        JLabel titleLabel = new JLabel("Tic Tac Toe");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(30, 20, 300, 30);
        add(titleLabel);

        setSize(330, 450);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void itemStateChanged(ItemEvent e) {
        if (c1.getState()) {
            type = false;
        } else if (c2.getState()) {
            type = true;
        }

        remove(c1);
        remove(c2);
        repaint(0, 0, 330, 450);
        showButton();
    }

    public void actionPerformed(ActionEvent e) {
        if (type) { // friend logic
            if (e.getSource() == reset) {
                for (i = 0; i <= 8; i++) {
                    b[i].setIcon(null);
                }
            } else {
                for (i = 0; i <= 8; i++) {
                    if (e.getSource() == b[i]) {
                        if (b[i].getIcon() == null) {
                            if (state) {
                                icon = ic2;
                                state = false;
                            } else {
                                icon = ic1;
                                state = true;
                            }
                            b[i].setIcon(icon);
                        }
                    }
                }
            }
            if (checkWin(ic11)) {
                playerScore++;
                JOptionPane.showMessageDialog(this, "Congratulations! You win!\nPlayer Score: " + playerScore + "\nComputer Score: " + computerScore);
                resetGame();
            } else if (checkWin(ic22)) {
                computerScore++;
                JOptionPane.showMessageDialog(this, "Computer wins! Try again.\nPlayer Score: " + playerScore + "\nComputer Score: " + computerScore);
                resetGame();
            } else if (isBoardFull()) {
                JOptionPane.showMessageDialog(this, "It's a draw!\nPlayer Score: " + playerScore + "\nComputer Score: " + computerScore);
                resetGame();
            }
        } else { // computer logic
            if (e.getSource() == reset) {
                for (i = 0; i <= 8; i++) {
                    b[i].setIcon(null);
                }
                for (i = 0; i <= 7; i++)
                    for (j = 0; j <= 4; j++)
                        a[i][j] = a1[i][j]; // resetting array
            } else {
                for (i = 0; i <= 8; i++) {
                    if (e.getSource() == b[i]) {
                        if (b[i].getIcon() == null) {
                            b[i].setIcon(ic1);
                            if (b[4].getIcon() == null) {
                                b[4].setIcon(ic2);
                                this.check(5);
                            } else {
                                this.complogic(i);
                            }
                        }
                    }
                }
            }
            if (checkWin(ic1)) {
                playerScore++;
                JOptionPane.showMessageDialog(this, "Congratulations! You win!\nPlayer Score: " + playerScore + "\nComputer Score: " + computerScore);
                resetGame();
            } else if (checkWin(ic2)) {
                computerScore++;
                JOptionPane.showMessageDialog(this, "Computer wins! Try again.\nPlayer Score: " + playerScore + "\nComputer Score: " + computerScore);
                resetGame();
            } else if (isBoardFull()) {
                JOptionPane.showMessageDialog(this, "It's a draw!\nPlayer Score: " + playerScore + "\nComputer Score: " + computerScore);
                resetGame();
            }
        }

        for (i = 0; i <= 7; i++) {
            Icon icon1 = b[(a[i][1] - 1)].getIcon();
            Icon icon2 = b[(a[i][2] - 1)].getIcon();
            Icon icon3 = b[(a[i][3] - 1)].getIcon();

            if ((icon1 == icon2) && (icon2 == icon3) && (icon1 != null)) {
                if (icon1 == ic1) {
                    b[(a[i][1] - 1)].setIcon(ic11);
                    b[(a[i][2] - 1)].setIcon(ic11);
                    b[(a[i][3] - 1)].setIcon(ic11);
                    JOptionPane.showMessageDialog(TTT1.this, "Achievement For You !!!");
                    break;
                } else if (icon1 == ic2) {
                    b[(a[i][1] - 1)].setIcon(ic22);
                    b[(a[i][2] - 1)].setIcon(ic22);
                    b[(a[i][3] - 1)].setIcon(ic22);
                    JOptionPane.showMessageDialog(TTT1.this, "Computer Win Try Again");
                    break;
                }
            }
        }
    }

    private boolean checkWin(Icon icon) {
        for (i = 0; i <= 7; i++) {
            Icon icon1 = b[(a[i][1] - 1)].getIcon();
            Icon icon2 = b[(a[i][2] - 1)].getIcon();
            Icon icon3 = b[(a[i][3] - 1)].getIcon();

            if ((icon1 == icon) && (icon2 == icon) && (icon3 == icon)) {
                return true;
            }
        }
        return false;
    }

    private boolean isBoardFull() {
        for (i = 0; i < b.length; i++) {
            if (b[i].getIcon() == null) {
                return false;
            }
        }
        return true;
    }

    private void resetGame() {
        for (i = 0; i <= 8; i++) {
            b[i].setIcon(null);
        }
        for (i = 0; i <= 7; i++)
            for (j = 0; j <= 4; j++)
                a[i][j] = a1[i][j]; // resetting array
    }

    public static void main(String[] args) {
        new TTT1();
    }
}