package gui.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import control.controller.Controller;
import control.events.Event;

public class CustomSwingUtils {
	
    public static void changeComponentSize(Component c, Dimension d) {
        c.setPreferredSize(d);
        c.setMaximumSize(d);
        c.setMinimumSize(d);
    }
    
    public static void setButtonStyle(JButton c) {
    	c.setAlignmentX(JButton.CENTER_ALIGNMENT);
        changeComponentSize(c, new Dimension(250, 50));
        c.setFont(new Font("Arial", Font.PLAIN, 18));
        c.setForeground(Color.WHITE);
        //c.setBackground(new Color(135, 135, 135));
        c.setBackground(Color.ORANGE);
        c.setBorder(new RoundedBorder(10)); //10 is the radius
        c.setFocusPainted(false);
    }
    
    public static void setButtonStyleNoBackground(JButton c) {
    	c.setAlignmentX(JButton.CENTER_ALIGNMENT);
        c.setFont(new Font("Arial", Font.PLAIN, 16));
        c.setForeground(Color.BLACK);
        c.setOpaque(false);
        c.setContentAreaFilled(false);
        c.setBorderPainted(false);
        c.setFocusPainted(false);
        c.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
    
    public static void setButtonStyleForm(JButton c) {
    	c.setAlignmentX(JButton.CENTER_ALIGNMENT);
        changeComponentSize(c, new Dimension(75, 30));
        c.setFont(new Font("Arial", Font.PLAIN, 14));
        c.setForeground(Color.WHITE);
        c.setBackground(new Color(37, 142, 184));
        c.setFocusPainted(false);
    }
    
    public static void setButtonStyleMainPanel(JButton c) {
    	c.setAlignmentX(JButton.CENTER_ALIGNMENT);
        changeComponentSize(c, new Dimension(325, 50));
        c.setFont(new Font("Arial", Font.PLAIN, 18));
        c.setForeground(Color.WHITE);
        c.setBackground(new Color(37, 142, 184));
        c.setFocusPainted(false);
    }
    
    public static void setStatStyle(JButton c) {
    	c.setAlignmentX(JButton.CENTER_ALIGNMENT);
        changeComponentSize(c, new Dimension(210, 30));
        c.setFont(new Font("Arial", Font.PLAIN, 16));
        c.setForeground(Color.WHITE);
        c.setBackground(new Color(37, 142, 184));
        c.setFocusPainted(false);
    }
    
    public static void setButtonStyleDialog(JButton c) {
    	c.setAlignmentX(JButton.CENTER_ALIGNMENT);
        changeComponentSize(c, new Dimension(120, 40));
        c.setFont(new Font("Arial", Font.BOLD, 16));
        c.setForeground(Color.WHITE);
        c.setBackground(new Color(37, 142, 184));
        c.setFocusPainted(false);
    }
    
    public static void setTitleStyle(JLabel l) {
        l.setFont(new Font("Arial", Font.BOLD, 18));
    }
    
    public static void setDataStyle(JLabel l) {
        l.setFont(new Font("Arial", Font.PLAIN, 16));
    }

    public static void setDialogTitleStyle(JLabel l) {
        l.setFont(new Font("Arial", Font.BOLD, 32));
    	l.setAlignmentX(JLabel.CENTER_ALIGNMENT);	
    }
    
    public static void setDialogLabelStyle(JLabel l) {
        l.setFont(new Font("Arial", Font.PLAIN, 22));
    	l.setAlignmentX(JLabel.CENTER_ALIGNMENT);	
    }
    
    
    
    

//    public static JPanel buildFooter(int event, JFrame window) {
//        JPanel panel = new JPanel();
//        JButton stats = new JButton("Mostrar Estadísticas");
//
//        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//        CustomSwingUtils.setStatStyle(stats);
//        
//        ActionListener statsListener = new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				Controller.getInstance().action(Event.SHOW_SCREEN, null);
//				window.dispose();
//			}
//			
//		};
//		stats.addActionListener(statsListener);
//        
//		panel.add(stats);
//        
//        return panel;
//    }

	public static JPanel buildTableWithModel(AbstractTableModel tableModel, Dimension size) {
		JPanel panel = new JPanel();
		JTable table = new JTable(tableModel);
		
		JScrollPane scrollPane = new JScrollPane(table);
		changeComponentSize(scrollPane, size);
		
		panel.add(scrollPane);
		
		return panel;
	}
	
	public static void addCtrlEventToButton(JButton btn, Event evnt, Object o) {
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.getInstance().action(evnt, o);//no siempre va a ser null,en alta y modificar y modificar sera transer
			}
		};
		btn.addActionListener(listener);
	}
    
}
