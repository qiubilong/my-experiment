package org.example.剑指.t54_二叉搜索树第K小元素;

import org.example.lang.tree.BinaryTree;
import org.example.lang.tree.TreeNode;

/**
 * @author chenxuegui
 * @since 2024/5/23
 *
 * https://leetcode.cn/problems/kth-smallest-element-in-a-bst/solutions/2361685/230-er-cha-sou-suo-shu-zhong-di-k-xiao-d-n3he/
 */
public class Solution {

    private TreeNode target;
    private int index;

    public int kthSmallest(TreeNode root, int k) {
        this.index = k;
        kthSmallestDo(root);
        if(target == null){
            return -1;
        }
        return target.val;
    }

    /**
     * 中序遍历时，输出序列是递增序列
     */
    public void kthSmallestDo(TreeNode root) {
        if(root == null || this.index<=0){
            return ;
        }
        kthSmallestDo(root.left);

        if(this.index ==1){
            target = root;
        }
        this.index --;

        kthSmallestDo(root.right);
    }



    public static void main(String[] args) {
        TreeNode root = BinaryTree.buildBinaryTree(new int[]{3,1,2,4},new int[]{1,2,3,4});
        System.out.println(BinaryTree.inOrder(root));

        Solution solution = new Solution();
        solution.kthSmallest(root,1);
        solution.kthSmallest(root,2);
        solution.kthSmallest(root,3);
    }
}
