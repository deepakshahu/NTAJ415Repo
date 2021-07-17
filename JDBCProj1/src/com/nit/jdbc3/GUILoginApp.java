package com.nit.jdbc3;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUILoginApp extends JFrame implements ActionListener, WindowListener {
	/**
	 * Launch the application
	 */
	private static final long serialVersionUID = 1L;
	private static final String LOGIN_QUERY="SELECT COUNT(*) FROM IRCTC_TAB WHERE UNAME=? AND PWD=?";
	private JLabel luser,lpass;
	private JTextField tuser,tpass;
	private JButton bSubmit;
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public static void main(String[] args) {
		System.out.println("GUIScrollFrameTest.main() (start)");
		new GUILoginApp();  //anonymous object
		System.out.println("End of main method");
	}

	//Constructor
	public GUILoginApp() {
		System.out.println("GUIScrollFrameTest:: 0-param constuctor(start)");
		setTitle("GUIFrontEnd-Scroll Frame");
		setSize(300,300);
		setLayout(new FlowLayout());

		//Add components
		luser = new JLabel("Username");
		add(luser);
		tuser = new JTextField(10);
		add(tuser);

		lpass = new JLabel("Password");
		add(lpass);
		tpass = new JTextField(10);
		add(tpass);

		bSubmit = new JButton("Submit");
		add(bSubmit);

		//Register and activate ActionListener for all the 4 button
		bSubmit.addActionListener(this);

		//Add WindowListener to frame
		this.addWindowListener(this);

		//Disable editing on Text boxes
		tuser.setEditable(true);
		tpass.setEditable(true);

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //the closing of frame window will terminates the application.
		initializeJDBC();
		System.out.println("GUIScrollFrameTest:: 0-param constuctor(end)");
	}

	private void initializeJDBC() {
		String s1=tuser.getText();  
        String s2=tpass.getText();  
		try {
			//Establish the Connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");

			//Create PreparedStatement object
			ps = con.prepareStatement(LOGIN_QUERY,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			ps.setString(1,s1);
			ps.setString(2,s2);
			
			//Prepare Scrollable RS object
			rs = ps.executeQuery();
		}//try
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		System.out.println("GUIScrollFrameTest.actionPerformed");
		if(ae.getSource()==bSubmit) {
			System.out.println("Submit button is clicked");
			try {
				if(rs!=null) {
					rs.next();  //move the cursor to first record from BFR
					int count=rs.getInt(1);  //get first cal value of that first record
					//Process the ResultSet
					if(count==0)
						System.out.println("Invalid credentials");
					else
						System.out.println("Valid credentials");
				}//if
			}//try
			catch(SQLException se) {
				se.printStackTrace();
			}//catch
		}//if
	}//actionPerformed

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.out.println("GUIScrollFrameTest.windowClosing()");
		try {
			if(rs!=null)
				rs.close();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		try {
			if(ps!=null)
				ps.close();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		try {
			if(con!=null)
				con.close();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}
}//class