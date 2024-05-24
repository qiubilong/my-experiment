package org.example.剑指.t55_二叉树的深度;

import org.example.lang.tree.BinaryTree;
import org.example.lang.tree.TreeNode;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author chenxuegui
 * @since 2024/5/24
 * https://leetcode.cn/problems/maximum-depth-of-binary-tree/solutions/2361697/104-er-cha-shu-de-zui-da-shen-du-hou-xu-txzrx/
 * 使用层次遍历法求树的深度
 */
public class Solution_层次遍历 {
    public int maxDepth(TreeNode root) {
        int depth = 0;
        if(root == null){
            return depth;
        }
        List<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()){
            //当前层次节点
            List<TreeNode> queueTemp = new LinkedList<>();

            for (TreeNode node : queue) {
                if(node.left!=null){
                    queueTemp.add(node.left);
                }
                if(node.right != null){
                    queueTemp.add(node.right);
                }
            }

            depth ++;
            queue = queueTemp;
        }
        return depth;
    }


    public static void main(String[] args) {

        TreeNode root = BinaryTree.buildBinaryTree(new int[]{3,9,2,1,7},new int[]{9,3,1,2,7});
        System.out.println(BinaryTree.inOrder(root));

        Solution_层次遍历 solution = new Solution_层次遍历();
        System.out.println(solution.maxDepth(root));
        System.out.println(BinaryTree.levelOrder(root));
        System.out.println(BinaryTree.levelOrder2(root));

    }
}
