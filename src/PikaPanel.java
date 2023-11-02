

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PikaPanel extends JPanel {

	private static final int FRAME1 = 1200, FRAME2 = 600;
	private static final Color BACKGROUND = new Color(204, 204, 204);
	private BufferedImage myImage;
	private Graphics myBuffer;

	private Timer t;
	private Ball ball;
	private Ball Leftpika, Rightpika;
	private ImageIcon leftPika, RightPika, PikaBall;
	private int PikaWidth, PikaHeight;
	private int PikaRadius = 40, BallRadius;
	private int leftPika_x, leftPika_y, leftPika_dy;
	private int RightPika_x, RightPika_y, RightPika_dy;

	int x = 0;
	private int left_hits = 0, right_hits;
	private int left_score = 0, right_score = 0;
	double angle = 0;
	double distance = 0;
	private boolean key_right = false, key_left = false, key_up = false, key_down = false, key_W = false, key_A = false,
			key_S = false, key_D = false;

	private int N = 160;
	private int sunshine_x = N / 2, sunshine_y = N / 2; // center
	private int x1, y1; // endpoint for each ray
	private int size = 70; // length of each ray
	private int r1 = 60, r2 = 55; // radius of the sun
	
	int cloud_x1 = 100, cloud_x2 = 120, cloud_x3 = 160; 

	public PikaPanel() {
		myImage = new BufferedImage(FRAME1, FRAME2, BufferedImage.TYPE_INT_RGB);
		myBuffer = myImage.getGraphics();
		myBuffer.setColor(BACKGROUND);
		myBuffer.fillRect(0, 0, FRAME1, FRAME2);

		myBuffer.setColor(Color.BLUE);
		myBuffer.fillRect(590, FRAME2 - 300, 20, 300);
		
		leftPika = new ImageIcon(Runnable.class.getClass().getResource("/left.png"));
		RightPika = new ImageIcon(Runnable.class.getClass().getResource("/right.png"));
		PikaBall = new ImageIcon(Runnable.class.getClass().getResource("/PikaBall.png"));

		PikaHeight = leftPika.getIconHeight();
		PikaWidth = leftPika.getIconWidth();
		BallRadius = PikaBall.getIconWidth() / 2;

		leftPika_x = 0;
		leftPika_y = FRAME2 - PikaHeight;
		RightPika_x = FRAME1 - PikaWidth;
		RightPika_y = FRAME2 - PikaHeight;

		Leftpika = new Ball(leftPika_x + 155, leftPika_y + 90, 0, 0, 40, Color.blue);		
		Rightpika = new Ball(RightPika_x + (PikaWidth - 155), RightPika_y + 90, 0, 0, 40, Color.blue);
		ball = new Ball(leftPika_x + 155 + 0, 100, 0, 2, 35, Color.yellow);

		t = new Timer(5, new Listener());
		t.start();

		addKeyListener(new Key());
		setFocusable(true);
	}

	public void paintComponent(Graphics g) {
		g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
	}

	private class Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			myBuffer.setColor(BACKGROUND); // cover the
			myBuffer.fillRect(0, 0, FRAME1, FRAME2); // old ball

			myBuffer.setColor(Color.BLUE);
			myBuffer.fillRect(590, FRAME2 - 300, 20, 300);

			myBuffer.setColor(Color.YELLOW);
			myBuffer.fillOval(30, 30, 100, 100);

			// µeÂÅ¤Ñ¥Õ¶³
			myBuffer.setColor(Color.WHITE);
			myBuffer.fillOval(100, 100, 200, 50);
			myBuffer.fillOval(120, 80, 70, 60);
			myBuffer.fillOval(160, 60, 100, 70);

			myBuffer.fillOval(400, 100, 200, 50);
			myBuffer.fillOval(420, 80, 70, 60);
			myBuffer.fillOval(460, 60, 100, 70);

			myBuffer.fillOval(700, 100, 200, 50);
			myBuffer.fillOval(720, 80, 70, 60);
			myBuffer.fillOval(760, 60, 100, 70);

			myBuffer.fillOval(1000, 100, 200, 50);
			myBuffer.fillOval(1020, 80, 70, 60);
			myBuffer.fillOval(1060, 60, 100, 70);

			myBuffer.setColor(Color.YELLOW);

			for (int angle = 0; angle <= 360; angle += 20) {
				x1 = (int) (sunshine_x + size * Math.cos(angle * Math.PI / 180));
				y1 = (int) (sunshine_y + size * Math.sin(angle * Math.PI / 180));
				myBuffer.drawLine(sunshine_x, sunshine_y, x1, y1);
			}

			Pikamotion();
			Leftpika.setX(leftPika_x + 155);
			Leftpika.setY(leftPika_y + 90);
			Rightpika.setX(RightPika_x + (PikaWidth - 155));
			Rightpika.setY(RightPika_y + 90);

			myBuffer.drawImage(leftPika.getImage(), leftPika_x, leftPika_y, null);
			myBuffer.setColor(Color.GREEN);
//			myBuffer.fillOval((int)Leftpika.getX()-40,(int) Leftpika.getY()-40, 80, 80);

			myBuffer.drawImage(RightPika.getImage(), RightPika_x, RightPika_y, null);
//			myBuffer.fillOval((int)Rightpika.getX()-40, (int)Rightpika.getY()-40, 80, 80);

			ball = collide(ball, Leftpika);
			ball = collide(ball, Rightpika);
			move1(FRAME1, FRAME2);

			myBuffer.drawImage(PikaBall.getImage(), (int) ball.getX() - BallRadius, (int) ball.getY() - BallRadius,
					null);

			myBuffer.setColor(Color.BLACK);
			myBuffer.setFont(new Font("Monospaced", Font.BOLD, 30));
			myBuffer.drawString("Points: " + left_hits, 25, 40);
			myBuffer.drawString("Points: " + right_hits, 1050, 40);
			myBuffer.drawString("Score: " + left_score, 25, 80);
			myBuffer.drawString("Score: " + right_score, 1050, 80);

			champion();
			repaint();

		}

		private void Pikamotion() {
			if (key_left == true && RightPika_x >= 615)
				RightPika_x -= 3;
			if (key_right == true && RightPika_x <= FRAME1 - PikaWidth + 70)
				RightPika_x += 3;
			if (RightPika_y <= 50)
				RightPika_dy *= -1;
			else if (RightPika_y > FRAME2 - PikaHeight) {
				key_up = false;
				RightPika_dy = 0;
				RightPika_y = FRAME2 - PikaHeight;
			}
			RightPika_y += RightPika_dy;

			if (key_A == true && leftPika_x >= -70)
				leftPika_x -= 3;
			if (key_D == true && leftPika_x <= 585 - PikaWidth)
				leftPika_x += 3;
			if (leftPika_y <= 50)
				leftPika_dy *= -1;
			else if (leftPika_y > FRAME2 - PikaHeight) {
				key_W = false;
				leftPika_dy = 0;
				leftPika_y = FRAME2 - PikaHeight;
			}
			leftPika_y += leftPika_dy;
		}
	}

	private class Key extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				key_left = true;
				key_right = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				key_right = true;
				key_left = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_UP && RightPika_y > FRAME2 - PikaHeight - 15) {
				key_up = true;
				RightPika_dy = -3;
			}
			if (e.getKeyCode() == KeyEvent.VK_A) {
				key_A = true;
				key_D = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_D) {
				key_D = true;
				key_A = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_W && leftPika_y > FRAME2 - PikaHeight - 15) {
				key_W = true;
				leftPika_dy = -3;
			}
		}

		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT)
				key_left = false;
			if (e.getKeyCode() == KeyEvent.VK_RIGHT)
				key_right = false;
			if (e.getKeyCode() == KeyEvent.VK_A)
				key_A = false;
			if (e.getKeyCode() == KeyEvent.VK_D)
				key_D = false;
		}
	}

	
	public void move1(double rightEdge, double bottomEdge) {
		  ball.setX(ball.getX() + ball.getdx()); // x = x + dx
		  ball.setY(ball.getY() + ball.getdy());

		  // check for left & right edge bounces
		  if (ball.getX() >= rightEdge - 35) // hits the right edge
		  {
		   ball.setX(rightEdge - 35);
		   ball.setdx(-1*ball.getdx());
		  } else if (ball.getX() <= 35) {
		   ball.setX(35);
		   ball.setdx(-1*ball.getdx());
		  }
		  
		  if (ball.getY() >= bottomEdge - 35) // hits the bottom edge
		  {

		   ball.setdx(0);
		   ball.setdy(2);
		   
		   leftPika_x = 0;
		   leftPika_y = FRAME2 - PikaHeight;
		   RightPika_x = FRAME1 - PikaWidth;
		   RightPika_y = FRAME2 - PikaHeight;
		   if(ball.getX()<600) {
		    right_hits++;
		    ball.setX(RightPika_x + (PikaWidth - 155));
		    ball.setY(100);
		   }
		   else {
		    left_hits++;
		    ball.setX(leftPika_x + 155+35);
		    ball.setY(100);
		   }
		   ball.setdx(0);
		   ball.setdy(2);
		  } else if (ball.getY() <= 35) {
		   ball.setY(35);
		   ball.setdy(-1*ball.getdy());
		  }
		  
		  if(590-40<ball.getX() && ball.getX()<610+40) {
		   if(ball.getY()>300-40 && ball.getY()<300-35)ball.setdy(-1*ball.getdy());
		   
		   if(ball.getY()>300-35)ball.setdx(-1*ball.getdx());
		   
		  }
		  
		  
		 }
	private Ball collide(Ball b, Ball pika) {
		double d = distance(b.getX(), b.getY(), pika.getX(), pika.getY());
		angle = Math.asin(((double) (b.getY() - pika.getY())) / d);
		if (d <= (b.getRadius() + pika.getRadius())) {
			if (b.getX() - pika.getX() > 0)
				b.setdx(3 * Math.cos(angle));
			else
				b.setdx(-4 * Math.cos(angle));
				b.setdy(4 * Math.sin(angle));
		}
		return b;
	}

	private double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

	private void champion() {
		if(right_hits == 5 ) {
			  right_hits = 0;
			  left_hits = 0;
			  right_score++;
			  x=800;
		  }
		else if(left_hits == 5) {
			 right_hits = 0;
			 left_hits = 0;
			 left_score++;
			 x=150;
		}
		if(right_score == 2 || left_score == 2) {
			myBuffer.setFont(new Font("Monospaced", Font.BOLD, 100));
			myBuffer.drawString("WIN¡I", x, 300);
			t.stop();
		}
	}
}

