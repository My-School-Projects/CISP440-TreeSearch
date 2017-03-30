package com.mdorst;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args)
    {
        Node root = createTree("triangle_test.txt");

        System.out.println("Test case (BFS): " + smallestSumBFS(root));

        System.out.println("Test case (DFS): " + smallestSumDFS(root));

        root = createTree("triangle.txt");

        System.out.println("Final answer (BFS): " + smallestSumBFS(root));

        System.out.println("Final answer (DFS): " + smallestSumDFS(root));

        root = createTree("big_triangle.txt");

        System.out.println("Big triangle (BFS): " + smallestSumBFS(root));

        resetCosts(root);

        System.out.println("Big triangle (DFS): " + smallestSumDFS(root));
    }

    static int smallestSumBFS(Node node)
    {
        Queue<Node> q = new PriorityQueue<>();

        node.pathCost = node.value;
        q.add(node);

        while(!q.isEmpty())
        {
            Node n = q.remove();

            if (n.right == null && n.left == null)
            {
                // this is a leaf node
                // the first path traversed to a leaf node will
                // be the one with the smallest sum.
                return n.pathCost;
            }
            if (n.right != null)
            {
                // calculate the cost of reaching n.right
                int cost = n.pathCost + n.right.value;
                // if there isn't already a lower (non-zero) cost path to n.right
                if (cost < n.right.pathCost || n.right.pathCost == 0)
                {
                    // then...
                    n.right.pathCost = cost;
                    q.add(n.right);
                }
            }
            if (n.left != null)
            {
                // do the same for n.left...
                int cost = n.pathCost + n.left.value;
                if (cost < n.left.pathCost || n.left.pathCost == 0)
                {
                    n.left.pathCost = cost;
                    q.add(n.left);
                }
            }
        }
        // should never be reached
        return -1;
    }

    static int smallestSumDFS(Node node)
    {
        if (node.left == null && node.right == null)
        {
            return node.pathCost = node.value;
        }
        if (node.left.pathCost == 0)
        {
            node.left.pathCost = smallestSumDFS(node.left);
        }
        if (node.right.pathCost == 0)
        {
            node.right.pathCost = smallestSumDFS(node.right);
        }
        return node.pathCost = min(node.left.pathCost, node.right.pathCost) + node.value;
    }

    static int min(int a, int b)
    {
        if (a < b)
            return a;
        else
            return b;
    }

    static void resetCosts(Node node)
    {
        node.pathCost = 0;
        if (node.left != null && node.left.pathCost != 0)
        {
            resetCosts(node.left);
        }
        if (node.right != null && node.right.pathCost != 0)
        {
            resetCosts(node.right);
        }
    }

    static Node createTree(String path)
    {
        try (Scanner scanner = new Scanner(new File(path)))
        {
            Node root = new Node(scanner.nextInt());
            List<Node> row_above = new ArrayList<>(1);
            row_above.add(0, root);
            List<Node> current_row;

            for (int row_size = 2; scanner.hasNextInt(); row_size++)
            {
                current_row = new ArrayList<>(row_size);

                for (int i = 0; i < row_size; i++)
                {
                    current_row.add(i, new Node(scanner.nextInt()));
                }

                for (int i = 0; i < row_size-1; i++)
                {
                    row_above.get(i).left = current_row.get(i);
                    row_above.get(i).right = current_row.get(i+1);
                }
                row_above = current_row;
            }

            return root;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new Node(0);
        }
    }
}
