import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

class CarRace extends JFrame {
    private JLabel[] cars;
    private Timer timer;
    private Random random;
    private boolean raceFinished;

    public CarRace() {
        setTitle("Car Race");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(null);

        cars = new JLabel[5];
        random = new Random();
        raceFinished = false;

        for (int i = 0; i < cars.length; i++) {
            Image carImage = loadImage("car" + (i + 1) + ".png");
            if (carImage != null) {
                Image scaledImage = carImage.getScaledInstance(100, 50, Image.SCALE_SMOOTH);
                ImageIcon carIcon = new ImageIcon(scaledImage);
                cars[i] = new JLabel(carIcon);
                cars[i].setBounds(10, 50 + i * 100, carIcon.getIconWidth(), carIcon.getIconHeight());
                add(cars[i]);
            } else {
                System.err.println("Image for car" + (i + 1) + " not found!");
            }
        }

        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!raceFinished) {
                    for (int i = 0; i < cars.length; i++) {
                        if (cars[i] != null) {
                            int move = random.nextInt(5) + 1;
                            cars[i].setLocation(cars[i].getX() + move, cars[i].getY());
                            if (cars[i].getX() >= getWidth() - cars[i].getWidth()) {
                                raceFinished = true;
                                timer.stop();
                                showWinner(i + 1);
                                break;
                            }
                        }
                    }
                }
            }
        });

        new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.start();
            }
        }).start();
    }

    private Image loadImage(String path) {
        try {
            return ImageIO.read(new File("src/" + path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void showWinner(int carNumber) {
        JLabel winnerLabel = new JLabel("Car " + carNumber + " wins!");
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 36));
        winnerLabel.setForeground(Color.RED);
        winnerLabel.setBounds(getWidth() / 2 - 150, getHeight() / 2 - 50, 300, 100);
        add(winnerLabel);
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CarRace().setVisible(true);
            }
        });
    }
}
