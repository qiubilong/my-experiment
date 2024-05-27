package org.example.ok.剑指.t55_二叉树的深度;

import org.example.lang.tree.BinaryTree;
import org.example.lang.tree.TreeNode;

/**
 * @author chenxuegui
 * @since 2024/5/23
 * https://leetcode.cn/problems/maximum-depth-of-binary-tree/
 */
public class Solution {

    public int maxDepth(TreeNode root) {
        if(root == null){
            return 0;
        }

        return  Math.max(maxDepth(root.left),maxDepth(root.right)) +1;
    }


    public static void main(String[] args) {


        TreeNode root = BinaryTree.buildBinaryTree(new int[]{3,9,20,15,7},new int[]{9,3,15,20,7});
        System.out.println(BinaryTree.inOrder(root));

        Solution solution = new Solution();
        System.out.println(solution.maxDepth(root));
    }
}
