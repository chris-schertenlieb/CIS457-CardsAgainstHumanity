import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class CAHgui extends JFrame {

	private JPanel contentPane;
	private JTextField serverName;
	private JTextField serverPortNum;
	private JTextField textField_2;
	private JTextField userName;
	Border border;
	JTextArea hand1;
	JTextArea hand2;
	JTextArea hand3;
	JTextArea hand4;
	JTextArea hand5;
	JTextArea hand6;
	JTextArea played1;
	JTextArea played2;
	JTextArea played3;
	JTextArea prompt;
	JTextArea score;
	
	private String selected;
	private JTextField myPortNum;
	private JLabel lblScore;
	private JTextArea textArea;
	
	private boolean myTurn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CAHgui frame = new CAHgui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CAHgui() {
		setBackground(new Color(240, 240, 240));
		setTitle("Cards Against Humanity");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1100, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Connect");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//String serverN = serverName.getText();
				//String sPort = serverPortNum.getText();
				//String myPort = myPortNum.getText();
				
				//To do: call connect in the client
			}
		});
		btnNewButton.setBounds(597, 10, 129, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblServerName = new JLabel("Server Name:");
		lblServerName.setBounds(10, 11, 91, 14);
		contentPane.add(lblServerName);
		
		serverName = new JTextField();
		serverName.setBounds(91, 11, 156, 20);
		contentPane.add(serverName);
		serverName.setColumns(10);
		
		JLabel lblPortNumber = new JLabel("Port Number:");
		lblPortNumber.setBounds(257, 14, 84, 14);
		contentPane.add(lblPortNumber);
		
		serverPortNum = new JTextField();
		serverPortNum.setBounds(336, 11, 62, 20);
		contentPane.add(serverPortNum);
		serverPortNum.setColumns(10);

		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//String username = userName.getText();
				//To do: give Username to client
				
				//refreshHand();
				//refreshHand should also set the first card Tzar
			}
		});
		btnPlay.setBounds(945, 10, 129, 23);
		contentPane.add(btnPlay);
		

		JLabel lblUsername = new JLabel("UserName:");
		lblUsername.setBounds(755, 16, 71, 11);
		contentPane.add(lblUsername);
		
		userName = new JTextField();
		userName.setBounds(821, 11, 114, 20);
		contentPane.add(userName);
		userName.setColumns(10);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//To do: tell client to close connection
				System.exit(0);
			}
		});
		btnQuit.setBounds(467, 627, 150, 23);
		contentPane.add(btnQuit);
		
		JLabel lblYourHand = new JLabel("Your Hand");
		lblYourHand.setBounds(478, 53, 62, 28);
		contentPane.add(lblYourHand);
		
		JLabel lblMyPortNumber = new JLabel("My Port Number:");
		lblMyPortNumber.setBounds(408, 11, 98, 19);
		contentPane.add(lblMyPortNumber);
		
		myPortNum = new JTextField();
		myPortNum.setBounds(501, 10, 86, 20);
		contentPane.add(myPortNum);
		myPortNum.setColumns(10);
		
		/*--------------------------users hand-------------------------------*/
		hand1 = new JTextArea();
		hand1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//if(myTurn == false)
				JOptionPane.showMessageDialog(null, "Its not your turn!");
			}
		});
		hand1.setWrapStyleWord(true);
		hand1.setEditable(false);
		hand1.setForeground(Color.BLACK);
		hand1.setBackground(Color.WHITE);
		hand1.setLineWrap(true);
		hand1.setBounds(45, 81, 143, 155);
		border = BorderFactory.createLineBorder(Color.BLACK);
		hand1.setBorder(BorderFactory.createCompoundBorder(border,
	            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		contentPane.add(hand1);
		
		hand2 = new JTextArea();
		hand2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//if(myTurn == false)
				JOptionPane.showMessageDialog(null, "Its not your turn!");
			}
		});
		hand2.setWrapStyleWord(true);
		hand2.setLineWrap(true);
		hand2.setForeground(Color.BLACK);
		hand2.setEditable(false);
		hand2.setBackground(Color.WHITE);
		hand2.setBounds(198, 81, 143, 155);
		contentPane.add(hand2);
		border = BorderFactory.createLineBorder(Color.BLACK);
		hand2.setBorder(BorderFactory.createCompoundBorder(border,
	            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
	    
	    hand3 = new JTextArea();
	    hand3.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent e) {
	    		//if(myTurn == false)
	    		JOptionPane.showMessageDialog(null, "Its not your turn!");
	    	}
	    });
	    hand3.setWrapStyleWord(true);
	    hand3.setLineWrap(true);
	    hand3.setForeground(Color.BLACK);
	    hand3.setEditable(false);
	    hand3.setBorder(BorderFactory.createCompoundBorder(border,
	    	            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
	    hand3.setBackground(Color.WHITE);
	    hand3.setBounds(351, 81, 143, 155);
	    contentPane.add(hand3);
	    
	    hand4 = new JTextArea();
	    hand4.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent e) {
	    		//if(myTurn == false)
	    		JOptionPane.showMessageDialog(null, "Its not your turn!");
	    	}
	    });
	    hand4.setWrapStyleWord(true);
	    hand4.setLineWrap(true);
	    hand4.setForeground(Color.BLACK);
	    hand4.setEditable(false);
	    hand4.setBorder(BorderFactory.createCompoundBorder(border,
	    	            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
	    hand4.setBackground(Color.WHITE);
	    hand4.setBounds(508, 81, 143, 155);
	    contentPane.add(hand4);
	    
	    hand5 = new JTextArea();
	    hand5.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent e) {
	    		//if(myTurn == false)
	    		JOptionPane.showMessageDialog(null, "Its not your turn!");
	    	}
	    });
	    hand5.setWrapStyleWord(true);
	    hand5.setLineWrap(true);
	    hand5.setForeground(Color.BLACK);
	    hand5.setEditable(false);
	    hand5.setBorder(BorderFactory.createCompoundBorder(border,
	    	            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
	    hand5.setBackground(Color.WHITE);
	    hand5.setBounds(661, 81, 143, 155);
	    contentPane.add(hand5);
	    
	    hand6 = new JTextArea();
	    hand6.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent e) {
	    		JOptionPane.showMessageDialog(null, "Its not your turn!");
	    	}
	    });
	    hand6.setWrapStyleWord(true);
	    hand6.setLineWrap(true);
	    hand6.setForeground(Color.BLACK);
	    hand6.setEditable(false);
	    hand6.setBorder(BorderFactory.createCompoundBorder(border,
	    	            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
	    hand6.setBackground(Color.WHITE);
	    hand6.setBounds(821, 81, 143, 155);
	    contentPane.add(hand6);
	    
	    /*-------------------------------------------------------------*/
	    
	    /*-------------------all cards played that turn-------------------*/
	    
	    JLabel lblWhiteCardsPlayed = new JLabel("White Cards Played");
	    lblWhiteCardsPlayed.setBounds(224, 269, 114, 14);
	    contentPane.add(lblWhiteCardsPlayed);
	    
	    played2 = new JTextArea();
	    played2.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent e) {
	    		JOptionPane.showMessageDialog(null, "Its not your turn!");
	    	}
	    });
	    played2.setWrapStyleWord(true);
	    played2.setLineWrap(true);
	    played2.setForeground(Color.BLACK);
	    played2.setEditable(false);
	    played2.setBorder(BorderFactory.createCompoundBorder(border,
	    	            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
	    played2.setBackground(Color.WHITE);
	    played2.setBounds(198, 294, 143, 155);
	    contentPane.add(played2);
	    
	    played1 = new JTextArea();
	    played1.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent e) {
	    		JOptionPane.showMessageDialog(null, "Its not your turn!");
	    	}
	    });
	    played1.setWrapStyleWord(true);
	    played1.setLineWrap(true);
	    played1.setForeground(Color.BLACK);
	    played1.setEditable(false);
	    played1.setBorder(BorderFactory.createCompoundBorder(border,
	    	    	            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
	    played1.setBackground(Color.WHITE);
	    played1.setBounds(45, 294, 143, 155);
	    contentPane.add(played1);
	    
	    played3 = new JTextArea();
	    played3.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent e) {
	    		JOptionPane.showMessageDialog(null, "Its not your turn!");
	    	}
	    });
	    played3.setWrapStyleWord(true);
	    played3.setLineWrap(true);
	    played3.setForeground(Color.BLACK);
	    played3.setEditable(false);
	    played3.setBorder(BorderFactory.createCompoundBorder(border,
	    	    	            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
	    played3.setBackground(Color.WHITE);
	    played3.setBounds(351, 294, 143, 155);
	    contentPane.add(played3);
	    
	    /*-----------------------------------------------------------------------*/
	    
	    
	    /*----------------------Prompt Card-------------------------------------*/
	    
	    JLabel lblPromt = new JLabel("Prompt");
	    lblPromt.setBounds(680, 269, 46, 14);
	    contentPane.add(lblPromt);
	    
	    prompt = new JTextArea();
	    prompt.setWrapStyleWord(true);
	    prompt.setLineWrap(true);
	    prompt.setForeground(Color.WHITE);
	    prompt.setEditable(false);
	    prompt.setBackground(Color.BLACK);
	    prompt.setBounds(627, 294, 143, 155);
	    contentPane.add(prompt);
	    
	    /*----------------------------------------------------------------------*/
	    
	    
	    /*--------------------------Running score card--------------------------*/
	    
	    lblScore = new JLabel("Score:");
	    lblScore.setBounds(936, 269, 46, 14);
	    contentPane.add(lblScore);
	    
	    score = new JTextArea();
	    score.setLineWrap(true);
	    score.setForeground(Color.BLACK);
	    score.setEditable(false);
	    score.setBorder(BorderFactory.createCompoundBorder(border,
	    	    	            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
	    score.setBackground(Color.WHITE);
	    score.setBounds(883, 294, 143, 155);
	    contentPane.add(score);
		
	}
	
	private void refreshHand(){
		
		//To do: get a new card and put in empty slot
		hand1.setText("1");
		hand2.setText("2");
		hand3.setText("3");
		hand4.setText("4");
		hand5.setText("5");
		hand6.setText("6");
		played1.setText("1");
		played2.setText("2");
		played3.setText("3");
		prompt.setText("????????????");
		//myTurn = client.getMyTurn();
		
	}
}
