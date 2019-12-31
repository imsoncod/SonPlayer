package Frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class Frame_Infor extends JFrame implements WindowListener {
	public static int Frame_Infor_Status = 0;
	public Frame_Infor(int x, int y) {
		setTitle("Information");
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/WindowIcon.jpg")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(x, y, 322, 303);
		getContentPane().setLayout(null);
		setResizable(false);
		addWindowListener(this);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 316, 47);
		panel.setBackground(Color.WHITE);
		panel.setBorder(new LineBorder(new Color(204,120,0)));
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Update History");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setFont(new Font("양재매화체S",Font.PLAIN,30));
		lblNewLabel.setForeground(new Color(204, 120, 0));
		lblNewLabel.setBounds(60, 5, 200, 37);
		panel.add(lblNewLabel);
		
		JTextArea txt_history = new JTextArea();
		txt_history.setLineWrap(true);
		txt_history.setEditable(false);
		txt_history.setCaretPosition(0);
		
		File updatefile = new File("C:\\Users\\Son\\Desktop\\SonPlayer\\UpdateHistory.txt");
		try {
			FileInputStream fileStream = new FileInputStream(updatefile);
			byte[] readbuffer = new byte[fileStream.available()];
			while(fileStream.read(readbuffer) != -1) {
				
			}
			txt_history.setText(new String(readbuffer));
		} catch (Exception e) {
			txt_history.setText("업데이트 정보를 가져올 수 없습니다.");
		}
		
		JScrollPane sc = new JScrollPane();
		sc.setBorder(null);
		sc.setBounds(0, 47, 316, 228);
		sc.setViewportView(txt_history);
		getContentPane().add(sc);
		
		setVisible(true);
		Frame_Infor_Status = 1;
		
	}
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosed(WindowEvent e) {
		Frame_Infor_Status = 0;
		
	}
	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
