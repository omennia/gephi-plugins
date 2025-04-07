/*
Copyright 2024 omennia 
Licensed under no license lol 
*/

package org.gephi.ui.filters.plugin.graph;

import javax.swing.JPanel;

/**
 * Interface for the Directed Ego Network filter UI.
 * 
 * @author omennia 
 */
public interface DirectedEgoUI {
    JPanel getPanel(org.gephi.filters.plugin.graph.DirectedEgoBuilder.DirectedEgoFilter directedEgoFilter);
}
