/*
Copyright 2024 omennia
Licensed under no license lol 
*/

package org.gephi.ui.filters.plugin.graph;

import org.gephi.filters.plugin.graph.DirectedEgoBuilder.DirectedEgoFilter;
import org.openide.util.lookup.ServiceProvider;

/**
 * Implementation of DirectedEgoUI that provides the panel for the filter.
 * 
 * @author omennia 
 */
@ServiceProvider(service = DirectedEgoUI.class)
public class DirectedEgoUIImpl implements DirectedEgoUI {

    @Override
    public javax.swing.JPanel getPanel(DirectedEgoFilter directedEgoFilter) {
        DirectedEgoPanel panel = new DirectedEgoPanel();
        panel.setup(directedEgoFilter);
        return panel;
    }
}
