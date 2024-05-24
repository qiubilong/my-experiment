package org.example.lang.tree;

import lombok.Data;

/**
 * @author chenxuegui
 * @since 2024/5/23
 */
@Data
public class TreeNode {
    public Integer val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(Integer val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "val=" + val +
                '}';
    }
}
