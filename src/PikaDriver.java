

import javax.swing.JFrame;

public class PikaDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JFrame frame = new JFrame("Pika");
		frame.setSize(1220, 640);
		frame.setLocation(50, 50);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new PikaPanel());
        frame.setVisible(true);

	}

}
