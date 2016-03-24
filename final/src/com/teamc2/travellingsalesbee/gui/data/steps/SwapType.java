package com.teamc2.travellingsalesbee.gui.data.steps;

/**
 * Types of swap
 *
 * @author Bradley Rowe (bmr455)
 */
public enum SwapType {
	BEST, //Fed into an experimental path which is the best path up to now.
	INSPECTED, //Fed into a new experimental path which is being traversed.
	ACCEPTED, //Fed into a new experimental path which is accepted by the bee.
	REJECTED //Fed into a new experimental path which is not accepted by the bee.
}