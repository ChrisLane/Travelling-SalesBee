package com.teamc2.travellingsalesbee;

import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

@SuppressWarnings("serial")
public class CellDrag extends JButton implements Transferable, DragSourceListener, DragGestureListener {

	private DragSource source;
	private TransferHandler transHandler;
	private int width;
	private int height;
	private JPanel panel;
	private String type;
	
	public CellDrag(String name, int width, int height, String type) {
		super(name);
		this.width = width;
		this.height = height;
		this.type = type;
		transHandler = new TransferHandler() {
			public Transferable createTransferable(JComponent c) {
				return new CellDrag(getText(), width, height, type);
			}
		};
		
		setTransferHandler(transHandler);
		
		source = new DragSource();
		source.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY, this);
		
	}
	
	@Override
	public Object getTransferData(DataFlavor flavour) throws UnsupportedFlavorException, IOException {
		return this;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[]{new DataFlavor(CellDrag.class, "JButton")};
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavour) {
		return true;
	}

	@Override
	public void dragGestureRecognized(DragGestureEvent dGEvent) {
		// TODO Auto-generated method stub
		System.out.println("Drag Gesture Recognised");
		source.startDrag(dGEvent, DragSource.DefaultMoveDrop, new CellDrag("", width, height, type), this);
		
	}

	/**
	 * Method to place a draggable cell in the gridmap
	 * @param arg0. The Drag event initiated by the user dragging a CellDrag button.
	 */
	@Override
	public void dragDropEnd(DragSourceDropEvent arg0) {
		// TODO Auto-generated method stub
		try{
			CellDrag droppedBtn = new CellDrag("",width,height,type);
			droppedBtn.setIcon(new ImageIcon(getImage(type)));
			
			int x =  (int) Math.round((panel.getMousePosition().getX() - (width/2))/width)*width;
			int y = (int) Math.round((panel.getMousePosition().getY() - (height/2))/height)*height;
			
			//Create a button instance at x, y position of the mouse relative to the panel with the width and height set above
			droppedBtn.setBounds(x, y, width, height);
			droppedBtn.setPanel(panel);
			panel.add(droppedBtn);
			panel.remove(this);
			panel.revalidate();
			validate();
			panel.repaint();
			repaint();
		}catch(NullPointerException | IOException e){
			//Deletion for when the cell is dragged off the gridmap panel
			this.setEnabled(false);
			panel.remove(this);
			panel.revalidate();
			panel.repaint();
		}
		
	}

	@Override
	public void dragEnter(DragSourceDragEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Drag Entered");
	}

	@Override
	public void dragExit(DragSourceEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Drag exit");
		
	}

	@Override
	public void dragOver(DragSourceDragEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Drag Over");
		
	}

	@Override
	public void dropActionChanged(DragSourceDragEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void setPanel(JPanel panel){
		this.panel=panel;
	}
	
	public Image getImage(String type) throws IOException{
		String filepath;
		switch (type){
		case "FLOWER":	filepath = "target/classes/icons/Flower.png";
						break;
		case "HIVE":	filepath = "target/classes/icons/Hive.png";
						break;
		default: filepath = "";
		}
		Image img = ImageIO.read(new File(filepath));
		Image scaledImg = img.getScaledInstance( width, height,  java.awt.Image.SCALE_SMOOTH ) ;
		return scaledImg;
	}

}
