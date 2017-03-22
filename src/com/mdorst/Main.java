package com.mdorst;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args)
    {
        Node root = createTree("triangle_test.txt");

        System.out.println("Test case: " + largestSum(root));

        root = createTree("triangle.txt");

        System.out.println("Final answer: " + largestSum(root));
    }

    static int largestSum(Node node)
    {
        if (node.left == null && node.right == null)
        {
            return node.value;
        }
        if (node.left == null)
        {
            return largestSum(node.right) + node.value;
        }
        if (node.right == null)
        {
            return largestSum(node.left) + node.value;
        }
        return max(largestSum(node.left), largestSum(node.right)) + node.value;
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
