package File;


import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

import Frame.Frame_JTableSet;
import Frame.Frame_Main;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class File_Control extends Thread{
	public static Player player;
	public static String songname;
	public static int play_state = 0;
	private Mp3File songfile_image;
	public static javax.swing.Timer timer;
	public static int autoPlay = 0;
	public static int last_selectedNum;
	public static Thread thread;
	public static String filename;
	
	@Override
	public void run() {
		play();
	}
	
	//음악 재생 메소드
	public void play() {
		Frame_Main.label_songImage.setBorder(null);
		for(int i=0; i<File_Read.songlist.length; i++) {
			if(File_Read.songlist[i].toString().contains(Frame_Main.table_songlist.getValueAt(Frame_Main.table_songlist.getSelectedRow(), 0).toString())) {
				last_selectedNum = i;
				break;
			}
		}
		Frame_Main.label_songTitle.setText(Frame_Main.table_songlist.getValueAt(Frame_Main.table_songlist.getSelectedRow(), 1).toString());
		Frame_Main.label_endTime.setText(Frame_Main.table_songlist.getValueAt(Frame_Main.table_songlist.getSelectedRow(), 2).toString());
		filename = File_Read.folderPath + "\\" + Frame_Main.table_songlist.getValueAt(Frame_Main.table_songlist.getSelectedRow(), 0).toString();
		Frame_JTableSet FJS = new Frame_JTableSet();
		Frame_Main.table_songlist.setDefaultRenderer(Object.class, FJS);
		Frame_Main.table_songlist.repaint();
		try {
			try {
				//사진 이미지 추출
				songfile_image = new Mp3File(filename);
				if (songfile_image.hasId3v2Tag()){
					ID3v2 id3v2tag = songfile_image.getId3v2Tag();
					byte[] imageData = id3v2tag.getAlbumImage();
					//이미지 데이터가 존재할 경우
					if (imageData!=null){
						BufferedImage songimg = ImageIO.read(new ByteArrayInputStream(imageData));
						ImageIcon icon = new ImageIcon(songimg);
						Image temp_image = icon.getImage();
						Image temp_image2 = temp_image.getScaledInstance(220, 210, Image.SCALE_AREA_AVERAGING);
						ImageIcon result_icon = new ImageIcon(temp_image2);
						Frame_Main.label_songImage.setIcon(result_icon);
					}
				}
			} catch (Exception e) {
				ImageIcon ImageError = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/default.png")));
				Frame_Main.label_songImage.setIcon(ImageError);
				Frame_Main.label_songImage.setBorder(new LineBorder(new Color(255,102,0) ,2));
			}
//			음악 재생
			BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(filename));
			player = new Player(buffer);		
			
			int t2 = Integer.valueOf(Frame_Main.table_songlist.getValueAt(Frame_Main.table_songlist.getSelectedRow(), 2).toString().substring(0, 1))*60+
					Integer.valueOf(Frame_Main.table_songlist.getValueAt(Frame_Main.table_songlist.getSelectedRow(), 2).toString().substring(2, 4));
			//시간 계산
			timer = new javax.swing.Timer(1000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int t1 = player.getPosition()/1000;
					Frame_Main.bar_songTime.setValue(t1);
					Frame_Main.bar_songTime.setMaximum(t2);
					int minute=t1/60;
					if((t1%60)<10) {
						Frame_Main.label_startTime.setText(minute + ":0"+t1%60);
					}else {
						Frame_Main.label_startTime.setText(minute + ":"+t1%60);
					}
					if(t1 == t2) {
						autoPlay = 0;
					}
				}
			});timer.start();			
			
			play_state = 1;
			player.play();
			
			timer.stop();
			
			if(autoPlay==-1) {
				return;
			}else if(autoPlay==0){ //순서대로 재생
					if(Frame_Main.isLoop){ //한곡반복재생
						Frame_Main.table_songlist.changeSelection(last_selectedNum, 0, false, false);
					}else {
						Frame_Main.table_songlist.changeSelection(last_selectedNum+1, 0, false, false);
						if(File_Control.last_selectedNum+1 >= Frame_Main.table_songlist.getRowCount()) {
							Frame_Main.table_songlist.changeSelection(0, 0, false, false);
						}
					}
				thread = new File_Control();
				thread.start();
			}
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found Exception");
		} catch (JavaLayerException e) {
			System.out.println("Java Layer Exception");
		}
		
	}
		
	//음악 중지 메소드
	public void pause() {
		play_state=0;
		player.close();
		thread.interrupt();
	}
}
