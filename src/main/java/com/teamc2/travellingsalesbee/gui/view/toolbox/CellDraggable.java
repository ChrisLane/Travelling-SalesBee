package com.teamc2.travellingsalesbee.gui.view.toolbox;

import com.teamc2.travellingsalesbee.algorithms.AlgorithmType;
import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.gui.data.cells.CellNode;
import com.teamc2.travellingsalesbee.gui.data.cells.CellOrigin;
import com.teamc2.travellingsalesbee.gui.data.cells.CellType;
import com.teamc2.travellingsalesbee.gui.view.map.PanelMap;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.io.IOException;

/**
 * A class to instantiate a drag and droppable cell used within the toolbox and panelmap
 * 
 * @author Melvyn Mathews (mxm499)
 * @author Bradley Rowe (bmr455)
 *
 */
public class CellDraggable extends JButton implements Transferable, DragSourceListener, DragGestureListener {

	private final DragSource source;
	private final TransferHandler transHandler;
	private final int width;
	private final int height;
	private final CellType type;
	private PanelMap panelMap;
	private Map map;
	private AlgorithmType algorithmType;
	private boolean onMap = false;
	private int prevX = 0;
	private int prevY = 0;
	private boolean fromMap;

	/**
	 * Create a new cell drag object
	 *
	 * @param width    Width of the cell
	 * @param height   Height of the cell
	 * @param type     Type of the cell
	 * @param panelMap The map panel
	 */
	public CellDraggable(int width, int height, CellType type, PanelMap panelMap, AlgorithmType algorithmType, boolean fromMap) {
		super();
		this.width = width;
		this.height = height;
		this.type = type;
		this.panelMap = panelMap;
		this.algorithmType = algorithmType;
		this.fromMap = fromMap;
		
		//Get the map so bounary new dropped cells can be added to the back-end map
		map = panelMap.getMap();
		
		transHandler = new TransferHandler() {
			public Transferable createTransferable(JComponent c) {
				return new CellDraggable(width, height, type, panelMap, algorithmType, true);
			}
		};
		
		setTransferHandler(transHandler);

		// Aesthetic code to style buttons correctly.
		setFocusPainted(false);
		setOpaque(false);
		setBorderPainted(false);
		setContentAreaFilled(false);

		
		//Drag event code
		source = new DragSource();
		source.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY, this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getTransferData(DataFlavor flavour) throws UnsupportedFlavorException, IOException {
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[]{new DataFlavor(CellDraggable.class, "JButton")};
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isDataFlavorSupported(DataFlavor flavour) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dragGestureRecognized(DragGestureEvent dGEvent) {
		source.startDrag(dGEvent, DragSource.DefaultMoveDrop, new CellDraggable(width, height, type, panelMap, algorithmType, true), this);
	}

	/**
	 * Method to drop a draggable cell in the map
	 *
	 * @param arg0 Drag event initiated by the user dragging a CellDraggable button.
	 */
	@Override
	public void dragDropEnd(DragSourceDropEvent arg0) {
		/**
		 * Try to place a cell in map but if a null pointer exception i.e. position doesn't exist
		 * in the back end map then catch this
		 */
		try {
		
			if (type.equals(CellType.ORIGIN)) {
				panelMap.deleteOldOrigin();
			}
			
			//Create the dropped Button
			CellDraggable droppedBtn = new CellDraggable(width, height, type, panelMap, algorithmType, true);
			//Set the image of the dropped button paying close attention to the CellType of Origin or Node
			droppedBtn.setIcon(new ImageIcon(getImage(type)));
			
			/**
			 * Add a listener to change the cursor image to what is being dragged
			 * so it indeed looks like it is being dragged
			 */
			droppedBtn.addChangeListener(evt -> {
				if (getModel().isPressed()) {
					ImageIcon img = new ImageIcon(getImage(type));
					setCursor(Toolkit.getDefaultToolkit().createCustomCursor(img.getImage(), new Point(0, 0), "c"));
				}
			});
			
			//Round by width and height to lock dropped buttons to conform with the grid
			int x = (int) Math.round((panelMap.getMousePosition().getX() - (width / 2)) / width) * width;
			int y = (int) (Math.round((panelMap.getMousePosition().getY() - (height / 2)) / height) * height);
			
			//Check if a cell already exists in the given position if so, delete the previous button
			panelMap.cellFull(x, y);

			// Add the cell to the map
			map.setCell(x, y, type);

			// Remove the previous location of the cell (if cell was dragged from one position on panel map to another) from the map
			if (onMap) map.clearCell(prevX, prevY);
			droppedBtn.onMap();
			droppedBtn.setPrevs(x, y);

			//Only add dropped button if its in panel map and below the overlying text area
			if (y >= height) {
				// Create a button instance at x, y position of the mouse relative to the panelMap with the width and height set above
				droppedBtn.setBounds(x, y, width, height);
				panelMap.add(droppedBtn);
				panelMap.setComponentZOrder(droppedBtn, 1);
			}

			panelMap.remove(this);
			panelMap.repaint();
		} catch (NullPointerException e) {
			//Only delete and disable a draggable cell when its come from map and not from toolbox.
			if (fromMap) {
				panelMap.clear();
				panelMap.getPathComponent().setStepNum(-1);
				panelMap.getPathComponent().repaint();
				// Deletion for when the cell is dragged off the map panelMap
				map.clearCell(getX(), getY());
				setEnabled(false);
				panelMap.remove(this);
				panelMap.repaint();
			}
		}
	}

	/**
	 * 
	 * @param x Previous X Coord
	 * @param y Previous Y Coord
	 */
	public void setPrevs(int x, int y) {
		prevX = x;
		prevY = y;
	}

	public void onMap() {
		onMap = true;
	}

	@Override
	public void dragEnter(DragSourceDragEvent arg0) {
	}

	@Override
	public void dragExit(DragSourceEvent arg0) {
	}

	@Override
	public void dragOver(DragSourceDragEvent arg0) {
	}

	@Override
	public void dropActionChanged(DragSourceDragEvent arg0) {
	}

	/**
	 * Get the image for this type of cell
	 *
	 * @param type Type of cell
	 * @return The cell image
	 */
	public Image getImage(CellType type) {
		Image img = null;
		switch (type) {
			case NODE:
				img = new CellNode().getImage(algorithmType);
				break;
			case ORIGIN:
				img = new CellOrigin().getImage(algorithmType);
				break;
			default:
				break;
		}
		if (img != null) {
			return img.getScaledInstance(width - 5, height - 5, Image.SCALE_SMOOTH);
		}
		return null;
	}

	/**
	 * Get the type of the cell
	 *
	 * @return Type of the cell
	 */
	public CellType getType() {
		return type;
	}

	/**
	 * Sets the image for a CellDraggable
	 * @param type The given CellType enum
	 */
	public void setImage(CellType type) {
		setIcon(new ImageIcon(getImage(type)));
	}

	/**
	 * Sets the algorithm type for a CellDragable
	 * @param type The given algorithm type enum 
	 */
	public void setAlgorithmType(AlgorithmType algorithmType) {
		this.algorithmType = algorithmType;
	}
}
