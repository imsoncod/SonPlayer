package Frame;



import java.awt.AWTException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.w3c.dom.css.RGBColor;

import File.File_Control;
import File.File_Read;
import java.awt.SystemColor;
import javax.swing.UIManager;

public class Frame_Main extends JFrame implements MouseListener, ActionListener, KeyListener, ChangeListener, FocusListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5069990119138619374L;
	private JPanel panel_left, panel_right, panel_north, panel_south;
	public static JLabel label_songImage, label_volumeImage, label_startTime, label_endTime, label_songTitle, label_searchImage, label_list;
	public static JSlider slider_songTime, slider_volume;
	public static JProgressBar bar_songTime;
	private JTextField txt_search;
	private JButton btn_pre, btn_next, btn_playpause, btn_repeat, btn_up, btn_down, btn_lyrics, btn_logo;
	public static JTable table_songlist;
	public static DefaultTableModel tablemodel;
	private DefaultTableCellRenderer tablesort;
	private String attribute[] = {"파일명","곡   명","시간","가사"};
	private Object tuple[][] = {};
	private MenuItem menu_open, menu_infor, menu_exit;
	private TrayIcon trayIcon;
	public static boolean isLoop = false;
	File_Control fc = new File_Control();

	public Frame_Main() {
		setBackground(Color.WHITE);
		setTitle("SonPlayer");
		setBounds(100, 100, 633, 457);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/WindowIcon.jpg")));
		getContentPane().setBackground(Color.WHITE);
		
		
		Frame_Main_Set();	
		setTray();
		setSongListTable();
		
		new File_Read();
		setVisible(true);
	}
	
	private void setTray() {
        PopupMenu trayMenu = new PopupMenu();
        trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/WindowIcon.jpg")));
        SystemTray tray = SystemTray.getSystemTray();
       
        menu_open = new MenuItem("　열기");
        menu_open.setFont(new Font(null, Font.BOLD, 12));
        menu_infor = new MenuItem("　정보");
        menu_infor.setFont(new Font(null, Font.PLAIN, 12));
        menu_exit = new MenuItem("　종료");
        menu_exit.setFont(new Font(null, Font.PLAIN, 12));
        
        menu_open.addActionListener(this);
        menu_infor.addActionListener(this);
        menu_exit.addActionListener(this);
       
        trayMenu.add(menu_open);
        trayMenu.add(menu_infor);
        trayMenu.addSeparator();
        trayMenu.add(menu_exit);
       
        trayIcon.setPopupMenu(trayMenu);
        trayIcon.addMouseListener(this);
        trayIcon.setToolTip("SonPlayer");
       
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
		
	}

	private void setSongListTable() {
		tablemodel = new DefaultTableModel(tuple, attribute) {
			public boolean isCellEditable(int tuple, int attribute) {
				return false;
			}
		};

		table_songlist = new JTable(tablemodel);
		table_songlist.setBorder(new LineBorder(null, 0, true));
		table_songlist.addMouseListener(this);
		table_songlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_songlist.setRowHeight(40);
		table_songlist.getTableHeader().setBackground(new Color(204,120,0));
		table_songlist.getTableHeader().setReorderingAllowed(false);
		table_songlist.getTableHeader().setResizingAllowed(false);
		table_songlist.getTableHeader().setForeground(Color.WHITE);
		table_songlist.getTableHeader().setFont(new Font("맑은 고딕", Font.BOLD, 13));
		table_songlist.setFocusable(false);
		table_songlist.setRowSelectionAllowed(true);
		table_songlist.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		table_songlist.setSelectionBackground(new Color(255,255,230));
		
		table_songlist.getColumnModel().getColumn(0).setWidth(0);
		table_songlist.getColumnModel().getColumn(0).setMinWidth(0);
		table_songlist.getColumnModel().getColumn(0).setMaxWidth(0);
		table_songlist.getColumnModel().getColumn(1).setPreferredWidth(300);
		table_songlist.getColumnModel().getColumn(2).setPreferredWidth(45);
		table_songlist.getColumnModel().getColumn(3).setWidth(0);
		table_songlist.getColumnModel().getColumn(3).setMinWidth(0);
		table_songlist.getColumnModel().getColumn(3).setMaxWidth(0);
		
		table_songlist.addKeyListener(this);
		
		tablesort = new DefaultTableCellRenderer();
		tablesort.setHorizontalAlignment(tablesort.CENTER);
		
		table_songlist.getColumnModel().getColumn(0).setCellRenderer(tablesort);
		table_songlist.getColumnModel().getColumn(2).setCellRenderer(tablesort);	

		JScrollPane sc = new JScrollPane(table_songlist);
		sc.setBounds(12, 41, 333, 339);
		sc.getViewport().setBackground(Color.WHITE);
		sc.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		panel_right.add(sc);

	}

	private void Frame_Main_Set() {
		panel_left = new JPanel();
		panel_left.setBounds(0, 0, 271, 430);
		getContentPane().add(panel_left);
		panel_left.setLayout(null);
		panel_left.setBackground(Color.WHITE);
		
		label_songImage = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/default.png"))));
		label_songImage.setHorizontalAlignment(SwingConstants.CENTER);
		label_songImage.setBounds(26, 10, 220, 210);
		label_songImage.setBorder(new LineBorder(new Color(255,102,0) ,2));
		panel_left.add(label_songImage);
	
		label_songTitle = new JLabel("더블클릭하여 음악 재생");
		label_songTitle.setHorizontalAlignment(SwingConstants.CENTER);
		label_songTitle.setBounds(12, 230, 247, 28);
		label_songTitle.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		panel_left.add(label_songTitle);
		
		bar_songTime = new JProgressBar();
		bar_songTime.setForeground(new Color(204, 120, 0));
		bar_songTime.setBackground(new Color(248, 248, 255));
		bar_songTime.setBounds(12, 267, 247, 8);
		bar_songTime.setBorder(new LineBorder(Color.WHITE));
		panel_left.add(bar_songTime);
		
		label_startTime = new JLabel("0:00");
		label_startTime.setBounds(12, 282, 57, 15);
		panel_left.add(label_startTime);
		
		label_endTime = new JLabel("4:15");
		label_endTime.setHorizontalAlignment(SwingConstants.RIGHT);
		label_endTime.setBounds(202, 282, 57, 15);
		panel_left.add(label_endTime);
		
		btn_pre = new JButton("◀◀");
		btn_pre.setBounds(35, 310, 50, 33);
		btn_pre.setBackground(Color.WHITE);
		btn_pre.setContentAreaFilled(false);
		btn_pre.setFont(new Font("양재매화체S",Font.PLAIN,23));
		btn_pre.setForeground(new Color(204, 120, 0));
		btn_pre.setFocusPainted(false);
		btn_pre.setBorder(null);
		btn_pre.setCursor(new Cursor(Cursor.HAND_CURSOR));
		panel_left.add(btn_pre);
		btn_pre.addActionListener(this);
		
		btn_next = new JButton("▶▶");
		btn_next.setBounds(183, 310, 50, 33);
		btn_next.setBackground(Color.WHITE);
		btn_next.setContentAreaFilled(false);
		btn_next.setFont(new Font("양재매화체S",Font.PLAIN,23));
		btn_next.setForeground(new Color(204, 120, 0));
		btn_next.setFocusPainted(false);
		btn_next.setBorder(null);
		btn_next.setCursor(new Cursor(Cursor.HAND_CURSOR));
		panel_left.add(btn_next);
		btn_next.addActionListener(this);
		
		btn_playpause = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/playButton.PNG"))));
		btn_playpause.setBounds(106, 300, 57, 52);
		btn_playpause.setBackground(Color.WHITE);
		btn_playpause.setContentAreaFilled(false);
		btn_playpause.setFocusPainted(false);
		btn_playpause.setBorder(null);
		btn_playpause.setCursor(new Cursor(Cursor.HAND_CURSOR));
		panel_left.add(btn_playpause);
		btn_playpause.addActionListener(this);
		
		slider_volume = new JSlider();
		slider_volume.setBackground(Color.WHITE);
		slider_volume.setBounds(62, 381, 139, 26);
		slider_volume.setForeground(new Color(204,120,0));
		panel_left.add(slider_volume);
		slider_volume.addChangeListener(this);
		
		label_volumeImage = new JLabel("Vol");
		label_volumeImage.setForeground(new Color(204,120,0));
		label_volumeImage.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		label_volumeImage.setHorizontalAlignment(SwingConstants.CENTER);
		label_volumeImage.setBounds(25, 376, 30, 35);
		panel_left.add(label_volumeImage);
		
		btn_repeat = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/loopButton2.PNG"))));
		btn_repeat.setBackground(Color.WHITE);
		btn_repeat.setContentAreaFilled(false);
		btn_repeat.setFocusPainted(false);
		btn_repeat.setBorder(null);
		btn_repeat.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn_repeat.setBounds(213, 376, 40, 33);
		panel_left.add(btn_repeat);
		btn_repeat.addActionListener(this);
		
		JPanel label_empty3 = new JPanel();
		label_empty3.setBounds(271, 0, 1, 420);
		label_empty3.setBackground(new Color(204, 120, 0));
		getContentPane().add(label_empty3);
		
		panel_right = new JPanel();
		panel_right.setBounds(272, 0, 357, 430);
		getContentPane().add(panel_right);
		panel_right.setLayout(null);
		panel_right.setBackground(Color.WHITE);
		
		panel_north = new JPanel();
		panel_north.setBorder(new LineBorder(Color.WHITE, 1, true));
		panel_north.setBounds(12, 10, 333, 31);
		panel_right.add(panel_north);
		panel_north.setLayout(null);
		panel_north.setBackground(Color.WHITE);
		
		label_list = new JLabel("Play List");
		label_list.setBounds(0, 0, 90, 31);
		label_list.setFont(new Font("양재매화체S",Font.PLAIN,22));
		label_list.setForeground(new Color(204, 120, 0));
		panel_north.add(label_list);
		
		txt_search = new JTextField(" 가수, 제목 검색");
		txt_search.setBounds(158, 5, 173, 21);
		txt_search.setBorder(new LineBorder(Color.BLACK));
		txt_search.setForeground(new Color(128,128,128));
		panel_north.add(txt_search);
		txt_search.addKeyListener(this);
		txt_search.addFocusListener(this);
		
		label_searchImage = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/search.PNG"))));
		label_searchImage.setBounds(132, 2, 25, 25);
		panel_north.add(label_searchImage);
		
		panel_south = new JPanel();
		panel_south.setBorder(new LineBorder(Color.WHITE, 1, true));
		panel_south.setBounds(12, 380, 333, 35);
		panel_right.add(panel_south);
		panel_south.setLayout(null);
		panel_south.setBackground(Color.WHITE);
		
		JPanel label_empty1 = new JPanel();
		label_empty1.setBounds(87, 9, 1, 25);
		label_empty1.setBackground(new Color(204, 120, 0));
		panel_south.add(label_empty1);
		
		btn_up = new JButton("▲");
		btn_up.setBounds(0, 5, 30, 30);
		btn_up.setBackground(Color.WHITE);
		btn_up.setFont(new Font("양재매화체S",Font.PLAIN,25));
		btn_up.setForeground(new Color(204, 120, 0));
		btn_up.setContentAreaFilled(false);
		btn_up.setFocusPainted(false);
		btn_up.setBorder(null);
		btn_up.setCursor(new Cursor(Cursor.HAND_CURSOR));
		panel_south.add(btn_up);
		btn_up.addActionListener(this);
		
		btn_down = new JButton("▼");
		btn_down.setBounds(42, 5, 30, 30);
		btn_down.setBackground(Color.WHITE);
		btn_down.setFont(new Font("양재매화체S",Font.PLAIN,25));
		btn_down.setForeground(new Color(204, 120, 0));
		btn_down.setContentAreaFilled(false);
		btn_down.setFocusPainted(false);
		btn_down.setBorder(null);
		btn_down.setCursor(new Cursor(Cursor.HAND_CURSOR));
		panel_south.add(btn_down);
		btn_down.addActionListener(this);
		
		btn_lyrics = new JButton("Lyrics");
		btn_lyrics.setBounds(102, 5, 70, 30);
		btn_lyrics.setBackground(Color.WHITE);
		btn_lyrics.setFont(new Font("양재매화체S",Font.PLAIN,25));
		btn_lyrics.setForeground(new Color(204, 120, 0));
		btn_lyrics.setContentAreaFilled(false);
		btn_lyrics.setFocusPainted(false);
		btn_lyrics.setBorder(null);
		btn_lyrics.setCursor(new Cursor(Cursor.HAND_CURSOR));
		panel_south.add(btn_lyrics);
		btn_lyrics.addActionListener(this);
		
		JPanel label_empty2 = new JPanel();
		label_empty2.setBounds(185, 9, 1, 25);
		label_empty2.setBackground(new Color(204, 120, 0));
		panel_south.add(label_empty2);
		
		btn_logo = new JButton("Son Player");
		btn_logo.setBounds(195, 5, 137, 30);
		btn_logo.setBackground(Color.WHITE);
		btn_logo.setFont(new Font("양재매화체S",Font.PLAIN,25));
		btn_logo.setForeground(new Color(204, 120, 0));
		btn_logo.setContentAreaFilled(false);
		btn_logo.setFocusPainted(false);
		btn_logo.setBorder(null);
		btn_logo.setCursor(new Cursor(Cursor.HAND_CURSOR));
		panel_south.add(btn_logo);
		btn_logo.addActionListener(this);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object action = e.getSource();
		if(action == btn_pre) {
			File_Control.autoPlay = -1;
			try {
				fc.pause();
			} catch (Exception e2) {
				return;
			}
			System.out.println(File_Control.last_selectedNum);
			table_songlist.changeSelection(File_Control.last_selectedNum-1, 0, false, false);
			if(File_Control.last_selectedNum-1 < 0) {
				table_songlist.changeSelection(table_songlist.getRowCount()-1, 0, false, false);
			}
			File_Control.thread = new File_Control();
			File_Control.thread.start();
		}
		if(action == btn_playpause) {
			//제작예정
		}
		if(action == btn_next) {
			File_Control.autoPlay = -1;
			try {
				fc.pause();
			} catch (Exception e2) {
				return;
			}
			System.out.println(File_Control.last_selectedNum);
			table_songlist.changeSelection(File_Control.last_selectedNum+1, 0, false, false);
			if(File_Control.last_selectedNum+1 >= table_songlist.getRowCount()) {
				table_songlist.changeSelection(0, 0, false, false);
			}
			File_Control.thread = new File_Control();
			File_Control.thread.start();
		}
		if(action == btn_repeat) {
			if(isLoop == false) {
				isLoop = true;
				btn_repeat.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/loopButton.PNG"))));
			}else {
				isLoop = false;
				btn_repeat.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/loopButton2.PNG"))));
			}
		}
		
		if(action == btn_up) {
			if(table_songlist.getSelectedRow()-1 < 0) {
				return;
			}
			File temp;
			temp = File_Read.songlist[table_songlist.getSelectedRow()-1];
			File_Read.songlist[table_songlist.getSelectedRow()-1] = File_Read.songlist[table_songlist.getSelectedRow()];
			File_Read.songlist[table_songlist.getSelectedRow()] = temp;
			String temp1 = table_songlist.getValueAt(table_songlist.getSelectedRow()-1, 0).toString();
			String temp2 = table_songlist.getValueAt(table_songlist.getSelectedRow()-1, 1).toString();
			String temp3 = table_songlist.getValueAt(table_songlist.getSelectedRow()-1, 2).toString();
			String temp4 = table_songlist.getValueAt(table_songlist.getSelectedRow()-1, 3).toString();
			tablemodel.setValueAt(table_songlist.getValueAt(table_songlist.getSelectedRow(), 0) ,table_songlist.getSelectedRow()-1, 0);
			tablemodel.setValueAt(table_songlist.getValueAt(table_songlist.getSelectedRow(), 1) ,table_songlist.getSelectedRow()-1, 1);
			tablemodel.setValueAt(table_songlist.getValueAt(table_songlist.getSelectedRow(), 2) ,table_songlist.getSelectedRow()-1, 2);
			tablemodel.setValueAt(table_songlist.getValueAt(table_songlist.getSelectedRow(), 3) ,table_songlist.getSelectedRow()-1, 3);
			tablemodel.setValueAt(temp1, table_songlist.getSelectedRow(), 0);
			tablemodel.setValueAt(temp2, table_songlist.getSelectedRow(), 1);
			tablemodel.setValueAt(temp3, table_songlist.getSelectedRow(), 2);
			tablemodel.setValueAt(temp4, table_songlist.getSelectedRow(), 3);
			table_songlist.changeSelection(table_songlist.getSelectedRow()-1, 0, false, false);
		}
		if(action == btn_down) {
			if(table_songlist.getSelectedRow()+1 >= table_songlist.getRowCount()) {
				return;
			}
			File temp;
			temp = File_Read.songlist[table_songlist.getSelectedRow()+1];
			File_Read.songlist[table_songlist.getSelectedRow()+1] = File_Read.songlist[table_songlist.getSelectedRow()];
			File_Read.songlist[table_songlist.getSelectedRow()] = temp;
			String temp1 = table_songlist.getValueAt(table_songlist.getSelectedRow()+1, 0).toString();
			String temp2 = table_songlist.getValueAt(table_songlist.getSelectedRow()+1, 1).toString();
			String temp3 = table_songlist.getValueAt(table_songlist.getSelectedRow()+1, 2).toString();
			String temp4 = table_songlist.getValueAt(table_songlist.getSelectedRow()+1, 3).toString();
			tablemodel.setValueAt(table_songlist.getValueAt(table_songlist.getSelectedRow(), 0) ,table_songlist.getSelectedRow()+1, 0);
			tablemodel.setValueAt(table_songlist.getValueAt(table_songlist.getSelectedRow(), 1) ,table_songlist.getSelectedRow()+1, 1);
			tablemodel.setValueAt(table_songlist.getValueAt(table_songlist.getSelectedRow(), 2) ,table_songlist.getSelectedRow()+1, 2);
			tablemodel.setValueAt(table_songlist.getValueAt(table_songlist.getSelectedRow(), 3) ,table_songlist.getSelectedRow()+1, 3);
			tablemodel.setValueAt(temp1, table_songlist.getSelectedRow(), 0);
			tablemodel.setValueAt(temp2, table_songlist.getSelectedRow(), 1);
			tablemodel.setValueAt(temp3, table_songlist.getSelectedRow(), 2);
			tablemodel.setValueAt(temp4, table_songlist.getSelectedRow(), 3);
			table_songlist.changeSelection(table_songlist.getSelectedRow()+1, 0, false, false);
		}
		if(action == btn_lyrics) {
			try {
				if(Frame_Lyrics.Frame_Lyrics_Status == 0) {
					String lyrics = table_songlist.getValueAt(table_songlist.getSelectedRow(), 3).toString();
					new Frame_Lyrics(lyrics, getX()+150, getY());
				}
		} catch (Exception e2) {
			return;
			}
		}
		if(action == btn_logo) {
			if(Frame_Infor.Frame_Infor_Status == 0) {
				new Frame_Infor(getX()+150, getY()+80);
			}
		}
		
		
		if(action == menu_open) {
			setVisible(true);
		}
		if(action == menu_infor) {
			if(Frame_Infor.Frame_Infor_Status == 0) {
				new Frame_Infor(getX()+150, getY()+80);
			}
		}
		if(action == menu_exit) {
			System.exit(0);
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == table_songlist) {
			if(e.getClickCount()==2) {
				File_Control.autoPlay = -1;
				File_Control.thread = new File_Control();
				if(File_Control.play_state==1) { //재생중일때
					fc.pause();
					File_Control.thread.start();
				}else {
					File_Control.thread.start();
				}
			}
		}
		if(e.getSource() == trayIcon) {
			if(e.getClickCount() == 2) {
				setVisible(true);
			}
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void keyPressed(KeyEvent e) {
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getSource() == txt_search) {
			tablemodel.setNumRows(0);
			File_Read.Table_Init();
			int count = table_songlist.getRowCount();
			for(int i=count-1; i>=0; i--) {
				if(String.valueOf(table_songlist.getValueAt(i, 1)).toLowerCase().contains(txt_search.getText().toLowerCase())==false) {
					tablemodel.removeRow(i);
				}
			}
		}

	}
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		
	}

	@Override
	public void focusGained(FocusEvent e) {
		if(txt_search.getText().equals(" 가수, 제목 검색")) {
			txt_search.setText(null);
			txt_search.setForeground(Color.BLACK);
		}	
	}

	@Override
	public void focusLost(FocusEvent e) {
		if(txt_search.getText().equals("")) {
			txt_search.setText(" 가수, 제목 검색");
			txt_search.setForeground(new Color(128,128,128));
		}	
	}
}
