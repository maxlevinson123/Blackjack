import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JButton;
import java.awt.BorderLayout;

class DrawPanel extends JPanel implements MouseListener {

    private Deck d;
    private ArrayList<Card> dealerCards;
    private ArrayList<Card> playerCards;
    boolean lost = false;
    boolean won = false;

    public DrawPanel() {

        d = new Deck();
        dealerCards = new ArrayList<>();
        playerCards = new ArrayList<>();

        dealerCards.add(d.getRandomCard());
        dealerCards.add(d.getRandomCard());
        playerCards.add(d.getRandomCard());
        playerCards.add(d.getRandomCard());




        this.addMouseListener(this);

        JButton newGameButton = new JButton("New Game");
        JButton hitButton = new JButton("Hit");
        JButton standButton = new JButton("Stand");


        newGameButton.addActionListener(e -> {
            d = new Deck();
            dealerCards = new ArrayList<>();
            playerCards = new ArrayList<>();

            dealerCards.add(d.getRandomCard());
            dealerCards.add(d.getRandomCard());
            playerCards.add(d.getRandomCard());
            playerCards.add(d.getRandomCard());
            won = false;
            lost = false;
        });

        hitButton.addActionListener(e -> {
            if (won || lost){
                return;
            }
            playerCards.add(d.getRandomCard());
            if (getHandPoints(playerCards) > 21) {
                lost = true;
            }
        });

        standButton.addActionListener(e -> {
            if (won || lost) {
                return;
            }
            while (getHandPoints(dealerCards) < 17) {
                dealerCards.add(d.getRandomCard());
            }
            if (getHandPoints(dealerCards) > 21) {
                won = true;
            }
            else {
                if (getHandPoints(playerCards) < getHandPoints(dealerCards)) lost = true;
                else {
                    won = true;
                }
            }
        });


        this.setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(newGameButton);
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 50;
        int y = 10;
        for (int i = 0; i < dealerCards.size(); i++) {
            g.drawImage(dealerCards.get(i).getImage(), x, y, null);
            x += 80;
        }
        x = 50;
        y += 200;
        for (int i = 0; i < playerCards.size(); i++) {
            g.drawImage(playerCards.get(i).getImage(), x, y, null);
            x += 80;
        }
        x = 50;
        g.drawString("Blackjack", 230, 10);
        g.drawString("Dealer", 10, 40);
        g.drawString("Player", 10, 240);
        if (lost) {
            g.drawString("Dealer Wins!", x, y + 120);
        }
        if (won) {
            g.drawString("You Win!", x, y + 120);
        }
    }

    public void mousePressed(MouseEvent e){ }

    private static int getPoints(Card c) {
        int p = 0;
        String value = c.getValue();
        if (value.equals("A")) p = 0;
        if (value.equals("02")) p = 2;
        if (value.equals("03")) p = 3;
        if (value.equals("04")) p = 4;
        if (value.equals("05")) p = 5;
        if (value.equals("06")) p = 6;
        if (value.equals("07")) p = 7;
        if (value.equals("08")) p = 8;
        if (value.equals("09")) p = 9;
        if (value.equals("10")) p = 10;
        if (value.equals("J")) p = 10;
        if (value.equals("Q")) p = 10;
        if (value.equals("K")) p = 10;
        return p;
    }

    private static int getHandPoints(ArrayList<Card> hand) {
        int total = 0;
        int aces = 0;

        // sums points and checks for aces
        for (Card c : hand) {
            total += getPoints(c);
            if (c.getValue().equals("A")) {
                aces ++;
            }
        }

        // Ace logic
        for (int i = 0; i < aces; i++) {
            total += 1;
        }
        if (aces > 0 && total + 10 <= 21) {
            total += 10;
        }

        return total;

    }

    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}