package com.teamc2.travellingsalesbee;

import java.awt.Color;
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
import java.io.IOException;

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
	
	public CellDrag(String name, int width, int height) {
		super(name);
		this.width = width;
		this.height = height;
		transHandler = new TransferHandler() {
			public Transferable createTransferable(JComponent c) {
				return new CellDrag(getText(), height, height);
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
		source.startDrag(dGEvent, DragSource.DefaultMoveDrop, new CellDrag("Test", height, height), this);
		
	}

	@Override
	public void dragDropEnd(DragSourceDropEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Dropped");
		System.out.println(MouseInfo.getPointerInfo().getLocation());
		CellDrag droppedBtn = new CellDrag("draggable",width,height);
		//Need to amend the X/Y here for offset
		droppedBtn.setBounds((int) MouseInfo.getPointerInfo().getLocation().getX(), (int) MouseInfo.getPointerInfo().getLocation().getY(), width, height);
		droppedBtn.setPanel(panel);
		panel.add(droppedBtn);
		panel.revalidate();
		validate();
		panel.repaint();
		repaint();
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

}
