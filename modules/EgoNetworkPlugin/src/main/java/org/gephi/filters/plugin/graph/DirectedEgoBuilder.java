/*
Copyright 2024 omennia
Licensed under no license lol 
*/

package org.gephi.filters.plugin.graph;

import javax.swing.Icon;
import javax.swing.JPanel;
import org.gephi.filters.api.FilterLibrary;
import org.gephi.filters.spi.Category;
import org.gephi.filters.spi.ComplexFilter;
import org.gephi.filters.spi.Filter;
import org.gephi.filters.spi.FilterBuilder;
import org.gephi.filters.spi.FilterProperty;
import org.gephi.graph.api.DirectedGraph;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.Node;
import org.gephi.project.api.Workspace;
import org.gephi.ui.filters.plugin.graph.DirectedEgoUI; // Added this
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Builder for the Directed Ego Network filter.
 * This filter extends the concept of ego networks by only considering outgoing edges.
 * 
 * @author omennia 
 */
@ServiceProvider(service = FilterBuilder.class)
public class DirectedEgoBuilder implements FilterBuilder {

    @Override
    public Category getCategory() {
        return FilterLibrary.TOPOLOGY;
    }

    @Override
    public String getName() {
        // return NbBundle.getMessage(DirectedEgoBuilder.class, "DirectedEgoBuilder.name");
        // TODO: Use the bundle strategy, gotta figure it out later..
        return "Directed Ego Network";
    }

    @Override
    public Icon getIcon() {
        return null;
    }

    @Override
    public String getDescription() {
//        return NbBundle.getMessage(DirectedEgoBuilder.class, "DirectedEgoBuilder.description");
        // TODO: Use the bundle strategy, gotta figure it out later..
        return "Select a node's downstream network up to a given depth";
    }

    @Override
    public Filter getFilter(Workspace workspace) {
        return new DirectedEgoFilter();
    }

    @Override
    public JPanel getPanel(Filter filter) {
        // DirectedEgoUI ui = Lookup.getDefault().lookup(DirectedEgoUI.class);
        DirectedEgoUI ui = Lookup.getDefault().lookup(org.gephi.ui.filters.plugin.graph.DirectedEgoUI.class);
        if (ui != null) {
            return ui.getPanel((DirectedEgoFilter) filter);
        }
        return null;
    }

    @Override
    public void destroy(Filter filter) {
    }

    public static class DirectedEgoFilter implements ComplexFilter {

        private String pattern = "";
        private boolean self = true;
        private int depth = 1;
        private boolean onlyDownstream = false;

        @Override
        public Graph filter(Graph graph) {
            String str = pattern.toLowerCase();

            List<Node> nodes = new ArrayList<>();
            for (Node n : graph.getNodes()) {
                if (n.getId().toString().toLowerCase().equals(str)) {
                    nodes.add(n);
                } else if ((n.getLabel() != null) && n.getLabel().toLowerCase().equals(str)) {
                    nodes.add(n);
                }
            }

            Set<Node> result = new HashSet<>();

            Set<Node> neighbours = new HashSet<>(nodes);

            for (int i = 0; i < depth; i++) {
                boolean newNodes = false;
                Node[] nei = neighbours.toArray(new Node[0]);
                neighbours.clear();
                for (Node n : nei) {
                    if (onlyDownstream && graph instanceof DirectedGraph) {
                        DirectedGraph directedGraph = (DirectedGraph) graph;
                        for (Edge edge : directedGraph.getOutEdges(n)) {
                            Node neighbor = graph.getOpposite(n, edge);
                            if (!result.contains(neighbor)) {
                                neighbours.add(neighbor);
                                newNodes = result.add(neighbor) || newNodes;
                            }
                        }
                    } else {
                        // If not a directed graph, fall back to regular neighbors
                        for (Node neighbor : graph.getNeighbors(n)) {
                            if (!result.contains(neighbor)) {
                                neighbours.add(neighbor);
                                newNodes = result.add(neighbor) || newNodes;
                            }
                        }
                    }
                }
                if (!newNodes || neighbours.isEmpty()) {
                    break;
                }
            }

            if (self) {
                result.addAll(nodes);
            } else {
                result.removeAll(nodes);
            }

            for (Node node : graph.getNodes().toArray()) {
                if (!result.contains(node)) {
                    graph.removeNode(node);
                }
            }

            return graph;
        }

        @Override
        public String getName()
        {
//            return NbBundle.getMessage(DirectedEgoBuilder.class, "DirectedEgoBuilder.name");
            // TODO: Use the bundle, but gotta solve it later
            return "Directed Ego Network";
        }

        @Override
        public FilterProperty[] getProperties() {
            try {
                return new FilterProperty[] {
                    FilterProperty.createProperty(this, String.class, "pattern"),
                    FilterProperty.createProperty(this, Integer.class, "depth"),
                    FilterProperty.createProperty(this, Boolean.class, "self"),
                    FilterProperty.createProperty(this, Boolean.class, "onlyDownstream")};
            } catch (NoSuchMethodException ex) {
                Exceptions.printStackTrace(ex);
            }
            return new FilterProperty[0];
        }

        public String getPattern() {
            return pattern;
        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }

        public Integer getDepth() {
            return depth;
        }

        public void setDepth(Integer depth) {
            this.depth = depth;
        }

        public boolean isSelf() {
            return self;
        }

        public void setSelf(boolean self) {
            this.self = self;
        }

        public boolean isOnlyDownstream() {
            return onlyDownstream;
        }

        public void setOnlyDownstream(boolean onlyDownstream) {
            this.onlyDownstream = onlyDownstream;
        }
    }
}
