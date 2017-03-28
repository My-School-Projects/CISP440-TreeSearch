package com.mdorst;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args)
    {
        Node root = createTree("triangle_test.txt");

        System.out.println("Test case (BFS): " + largestSumBFS(root));

        System.out.println("Test case (DFS): " + largestSumDFS(root));

        root = createTree("triangle.txt");

        System.out.println("Final answer (BFS): " + largestSumBFS(root));

        System.out.println("Final answer (DFS): " + largestSumDFS(root));
    }

    static int largestSumBFS(Node node)
    {
        Queue<Node> q = new LinkedList<>();
        int maxCost = 0;
        node.pathCost = node.value;
        q.add(node);

        while(!q.isEmpty())
        {
            Node n = q.remove();

            if (n.right == null && n.left == null)
            {
                // this is a leaf node
                if (n.pathCost > maxCost)
                {
                    maxCost = n.pathCost;
                }
                continue;
            }
            if (n.right != null)
            {
                // calculate the cost of reaching n.right
                int cost = n.pathCost + n.right.value;
                // if there isn't already a higher cost path to n.right
                if (cost > n.right.pathCost)
                {
                    // then...
                    n.right.pathCost = cost;
                }
                q.add(n.right);
            }
            if (n.left != null)
            {
                // do the same for n.left...
                int cost = n.pathCost + n.left.value;
                if (cost > n.left.pathCost)
                {
                    n.left.pathCost = cost;
                }
                q.add(n.left);
            }
        }
        return maxCost;
    }

    static int largestSumDFS(Node node)
    {
        if (node.left == null && node.right == null)
        {
            return node.value;
        }
        if (node.left == null)
        {
            return largestSumDFS(node.right) + node.value;
        }
        if (node.right == null)
        {
            return largestSumDFS(node.left) + node.value;
        }
        return max(largestSumDFS(node.left), largestSumDFS(node.right)) + node.value;
    }

    static int max(int a, int b)
    {
        if (a > b)
            return a;
        else
            return b;
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
