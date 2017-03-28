package com.mdorst;

class Node implements Comparable<Node> {
    Node left, right;
    int value, pathCost;
    Node(int value)
    {
        this.value = value;
    }

    @Override
    public int compareTo(Node n) {
        return pathCost - n.pathCost;
    }
}
