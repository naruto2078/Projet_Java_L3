package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class PageSuivEcouteur implements ActionListener{
	private JButton b;
	private TacheController tc;
	public PageSuivEcouteur (JButton b,TacheController tc)
	{
		this.b=b;
		this.tc=tc;
	}
	
	
	public void actionPerformed(ActionEvent e) 
	{
		
		tc.pagSuiv();
	}
}
