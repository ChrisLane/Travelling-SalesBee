package com.teamc2.travellingsalesbee.gui.data.cells;

import com.teamc2.travellingsalesbee.gui.data.Map;
import com.teamc2.travellingsalesbee.algorithms.AlgorithmType;
import com.teamc2.travellingsalesbee.gui.view.PanelMap;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.io.IOException;

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

	/**
	 * Create a new cell drag object
	 *
	 * @param width    Width of the cell
	 * @param height   Height of the cell
	 * @param type     Type of the cell
	 * @param panelMap The map panel
	 */
	public CellDraggable(int width, int height, CellType type, PanelMap panelMap, AlgorithmType algorithmType) {
		super();
		this.width = width;
		this.height = height;
		this.type = type;
		this.panelMap = panelMap;
		this.algorithmType = algorithmType;
		map = panelMap.getMap();
		transHandler = new TransferHandler() {
			public Transferable createTransferable(JComponent c) {
				return new CellDraggable(width, height, type, panelMap, algorithmType);
			}
		};

		// Aesthetic code to style buttons correctly.
		setFocusPainted(false);
		setOpaque(false);
		setBorderPainted(false);
		setContentAreaFilled(false);

		setTransferHandler(transHandler);

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
		source.startDrag(dGEvent, DragSource.DefaultMoveDrop, new CellDraggable(width, height, type, panelMap, algorithmType), this);
	}

	/**
	 * Method to place a draggable cell in the map
	 *
	 * @param arg0 Drag event initiated by the user dragging a CellDraggable button.
	 */
	@Override
	public void dragDropEnd(DragSourceDropEvent arg0) {
		try {
			panelMap.grabFocus();
			//Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			//setCursor(defaultCursor);
			if (type.equals(CellType.ORIGIN)) {
				panelMap.deleteOldHive();
			}
			CellDraggable droppedBtn = new CellDraggable(width, height, type, panelMap, algorithmType);
			droppedBtn.setIcon(new ImageIcon(getImage(type)));
			droppedBtn.addChangeListener(evt -> {
				if (getModel().isPressed()) {
					ImageIcon img = new ImageIcon(getImage(type));
					setCursor(Toolkit.getDefaultToolkit().createCustomCursor(img.getImage(), new Point(0, 0), "c"));
				}
			});

			int x = (int) Math.round((panelMap.getMousePosition().getX() - (width / 2)) / width) * width;
			int y = (int) Math.round((panelMap.getMousePosition().getY() - (height / 2)) / height) * height;
			panelMap.cellFull(x,y);

			// Add the cell to the map
			map.setCell(x, y, type);

			// Remove the previous location of the cell (if there is one) from the map
			if (onMap) map.clearCell(prevX, prevY);
			droppedBtn.onMap();
			droppedBtn.setPrevs(x,y);
			
			// Create a button instance at x, y position of the mouse relative to the panelMap with the width and height set above
			droppedBtn.setBounds(x, y, width, height);
			panelMap.add(droppedBtn);
			panelMap.setComponentZOrder(droppedBtn, 0);
			panelMap.remove(this);
			panelMap.repaint();
		} catch (NullPointerException e) {
			// Deletion for when the cell is dragged off the map panelMap
			map.clearCell(getX(), getY());
			setEnabled(false);
			panelMap.remove(this);
			//panelMap.revalidate();
			panelMap.repaint();
		}
	}

	public void setPrevs(int x, int y) {
		prevX = x;
		prevY = y;
	}

	public void onMap() {
		onMap = true;
	}

	@Override
	public void dragEnter(DragSourceDragEvent arg0) {
		//ImageIcon img = new ImageIcon(getImage(type));
		//setCursor(Toolkit.getDefaultToolkit().createCustomCursor(img.getImage(), new Point(0,0), "c"));
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

	public void setImage(CellType type) {
		this.setIcon(new ImageIcon(getImage(type)));
	}

	public void setAlgorithmType(AlgorithmType type) {
		algorithmType = type;
	}
}
