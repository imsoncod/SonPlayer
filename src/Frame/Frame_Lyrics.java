package Frame;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Frame_Lyrics extends JFrame implements WindowListener{
	public static int Frame_Lyrics_Status = 0;

	public Frame_Lyrics(String lyrics, int x, int y) {
		setTitle("Lyrics");
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/WindowIcon.jpg")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(x, y, 300, 700);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout(0, 0));
		addWindowListener(this);
		
		JTextPane txt_lyrics = new JTextPane();
		StyledDocument doc = txt_lyrics.getStyledDocument(); 
		SimpleAttributeSet center = new SimpleAttributeSet(); 
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER); 
		doc.setParagraphAttributes(0, doc.getLength(), center, false); 
		
		txt_lyrics.setEditable(false);
		txt_lyrics.setText(lyrics);
		if(txt_lyrics.getText().isEmpty()) {
			txt_lyrics.setText("가사를 지원하지 않습니다.");
		}
		txt_lyrics.setCaretPosition(0);
		
		JScrollPane sc = new JScrollPane();
		sc.setViewportView(txt_lyrics);
		add(sc);
		
		setVisible(true);
		Frame_Lyrics_Status = 1;
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		Frame_Lyrics_Status = 0;
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
