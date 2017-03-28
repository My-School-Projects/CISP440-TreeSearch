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
        if (value > n.value)
            return 1;
        if (value < n.value)
            return -1;
        else return 0;
    }
}
