package org.example.lang.tree;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author chenxuegui
 * @since 2024/5/23
 * https://blog.csdn.net/IAMZTDSF/article/details/123575617
 * 根据前序遍历和中序遍历重构二叉树
 * https://leetcode.cn/problems/zhong-jian-er-cha-shu-lcof/description/?spm=a2c6h.12873639.article-detail.7.6d9f3bc6NRp7NS&favorite=xb9nqhhg
 */
public class BinaryTree {

    //根据前序遍历和中序遍历重构二叉树
    public static TreeNode buildBinaryTree(int[] preOrder, int[] inOrder){
        if(preOrder.length==0 || inOrder.length ==0){
            return null;
        }
        if(preOrder.length!=inOrder.length){
            throw new IllegalArgumentException();
        }
        //保存中序遍历对应索引，用于定位左右子树
        Map<Integer,Integer> inOrderIndexMap = new HashMap<>();
        for (int i = 0; i < inOrder.length; i++) {
            inOrderIndexMap.put(inOrder[i],i);
        }

        return buildBinaryTreeDo(preOrder,0,preOrder.length-1, inOrderIndexMap, 0,inOrder.length-1);
    }

    private static TreeNode buildBinaryTreeDo(int[] preOrder, int preOrderLeft, int preOrderRight, Map<Integer,Integer> inOrderIndexMap, int inOrderLeft, int inOrderRight){
        if(preOrderLeft > preOrderRight || inOrderLeft > inOrderRight){
            return null;
        }
        int rootVal = preOrder[preOrderLeft];
        TreeNode root = new TreeNode(rootVal);
        int rootIndex = inOrderIndexMap.get(rootVal);

        root.left = buildBinaryTreeDo(preOrder,preOrderLeft+1,rootIndex - inOrderLeft + preOrderLeft, inOrderIndexMap, inOrderLeft,rootIndex-1);
        root.right = buildBinaryTreeDo(preOrder,rootIndex - inOrderLeft + preOrderLeft+1,preOrderRight,inOrderIndexMap, rootIndex+1,inOrderRight);

        return root;
    }

    /** 层次遍历 */
    public static List<Integer> levelOrder(TreeNode root){
        List<Integer> result = new LinkedList<>();
        if(root == null){
            return result;
        }
        LinkedBlockingQueue<TreeNode> queue = new LinkedBlockingQueue<>();
        queue.add(root);
        while (!queue.isEmpty()){

            TreeNode node = queue.poll();
            result.add(node.val);
            if(node.left!=null){
                queue.add(node.left);
            }
            if(node.right != null){
                queue.add(node.right);
            }
        }
        return result;
    }
    public static List<List<Integer>> levelOrder2(TreeNode root) {
        List<List<Integer>> result = new LinkedList<>();
        if(root == null){
            return result;
        }
        List<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            List<TreeNode> levelQueue = new LinkedList<>();
            List<Integer> levelList = new ArrayList<>();

            for (TreeNode node : queue) {
                levelList.add(node.val);

                if(node.left != null){
                    levelQueue.add(node.left);
                }
                if(node.right != null){
                    levelQueue.add(node.right);
                }
            }
            queue = levelQueue;
            result.add(levelList);
        }
        return result;
    }

    public List<List<Integer>> levelOrder3(TreeNode root) {
        List<List<Integer>> result = new LinkedList<>();
        if(root == null){
            return result;
        }
        LinkedBlockingQueue<TreeNode> queue = new LinkedBlockingQueue<>();
        queue.add(root);
        while (!queue.isEmpty()){
            LinkedBlockingQueue<TreeNode> levelQueue = new LinkedBlockingQueue<>();
            List<Integer> levelList = new ArrayList<>();
            while (!queue.isEmpty()){
                TreeNode node = queue.poll();
                levelList.add(node.val);

                if(node.left != null){
                    levelQueue.add(node.left);
                }
                if(node.right != null){
                    levelQueue.add(node.right);
                }
            }
            queue.addAll(levelQueue);
            result.add(levelList);
        }
        return result;
    }

    /** 前序遍历  */
    public static List<Integer> preOrder(TreeNode root){
        List<Integer> result = new ArrayList<>();
        preOrderDo(root,result);
        return result;
    }
    private static void preOrderDo(TreeNode root,List<Integer> result){
        if(root == null){
            return;
        }
        result.add(root.getVal());
        preOrderDo(root.left,result);
        preOrderDo(root.right,result);
    }

    /** 中序遍历 */
    public static List<Integer> inOrder(TreeNode root){
        List<Integer> result = new ArrayList<>();
        inOrderDo(root,result);
        return result;
    }
    private static void inOrderDo(TreeNode root,List<Integer> result){
        if(root == null){
            return;
        }
        inOrderDo(root.left,result);
        result.add(root.getVal());
        inOrderDo(root.right,result);
    }


    public static void main(String[] args) {
        int[] preOrder = new int[]{1,2,4,7,3,5,6,8};
        int[] inOrder  = new int[]{4,7,2,1,5,3,8,6};

        TreeNode treeNode = buildBinaryTree(preOrder, inOrder);

        System.out.println(preOrder(treeNode));
    }
}
