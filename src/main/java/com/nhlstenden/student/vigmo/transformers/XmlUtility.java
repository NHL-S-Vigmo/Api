package com.nhlstenden.student.vigmo.transformers;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.*;

public class XmlUtility {
        public static List<Node> asList(NodeList n) {
        return n.getLength()==0?
                Collections.emptyList(): new NodeListWrapper(n);
    }

    static final class NodeListWrapper extends AbstractList<Node>
            implements RandomAccess {
        private final NodeList list;
        NodeListWrapper(NodeList l) {
            list=l;
        }
        public Node get(int index) {
            return list.item(index);
        }
        public int size() {
            return list.getLength();
        }
    }
}
