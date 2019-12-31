package File;


import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

import javax.print.attribute.standard.Fidelity;
import javax.swing.JOptionPane;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import com.mpatric.mp3agic.Mp3File;

import Frame.Frame_Main;

public class File_Read{
	public static File[] songlist;
	public static String folderPath = "C:\\Users\\Son\\Desktop\\SonPlayer\\Data";
	public static File file;
	public static MP3File songfile;
	public static Mp3File songfile_image;
	
	
	public File_Read(){
		file = new File(folderPath);
		songlist = file.listFiles();
		songlist = sortFileList(songlist, COMPARETYPE_NAME);
		if(!file.isDirectory()){
			int answer = JOptionPane.showConfirmDialog(null, "System Error", "Error", JOptionPane.OK_OPTION);
			if(answer == 0) {
				System.exit(0);
			}
		}
		Table_Init();
	}
	public static void Table_Init() {
		for(File song : songlist) {
			try {
				songfile = (MP3File) AudioFileIO.read(song);
				Tag tag = songfile.getTag();
			    
				String artist = tag.getFirst(FieldKey.ARTIST).replace(" ","");
				String title = tag.getFirst(FieldKey.TITLE);
				String lyrics = tag.getFirst(FieldKey.LYRICS);
				
				//음악 트랙 시간 계산
				int timelength = songfile.getAudioHeader().getTrackLength();
				String minute = String.valueOf(timelength/60);
				String second;
				if(timelength%60<10) {
					second = 0 + String.valueOf(timelength%60);
				}else {
					second = String.valueOf(timelength%60);
				}
				String time = minute + ":" + second;
				//테이블에 입력
				Frame_Main.tablemodel.addRow(new Object[] {song.getName(), " " + artist + " - " + title, time, lyrics});
			} catch (Exception e) {
				System.out.println("Song File Get Failed");
			}
		}
	}

	public int COMPARETYPE_NAME = 0;

	public File[] sortFileList(File[] files, final int compareType) {

		Arrays.sort(files, new Comparator<Object>() {
			@Override
			public int compare(Object object1, Object object2) {

				String s1 = "";
				String s2 = "";

				if (compareType == COMPARETYPE_NAME) {
					try {
						MP3File sortmp3 = new MP3File();
						
						sortmp3 = (MP3File) AudioFileIO.read((File) object1);
						Tag tag = sortmp3.getTag();
						s1 = tag.getFirst(FieldKey.ARTIST);
						
						sortmp3 = (MP3File) AudioFileIO.read((File) object2);
						tag = sortmp3.getTag();
						s2 = tag.getFirst(FieldKey.ARTIST);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return s1.compareTo(s2);
			}
		});
		return files;
	}

}
