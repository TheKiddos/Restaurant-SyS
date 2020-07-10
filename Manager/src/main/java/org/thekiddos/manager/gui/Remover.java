package org.thekiddos.manager.gui;

import javafx.scene.Node;

/**
 * Marks a parent node that can remove it's children
 */
public interface Remover {
    /**
     * Remove the specified node from the parent (Remover)
     * @param node The node to removes
     */
    void remove( Node node );
}
