package Frame;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import File.File_Control;
import File.File_Read;

public class Frame_JTableSet extends DefaultTableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column)
	{
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if(File_Control.filename.contains(Frame_Main.table_songlist.getValueAt(row, 0).toString())) {
				c.setForeground(new Color(204, 120, 0));
			}else c.setForeground(Color.BLACK);
		return c;
	}
}
