/*
 * Copyright 2017 huangjinfu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.hjf.ds;

/**
 * Binary Search Tree.
 *
 * @author huangjinfu
 */
public class BST<Key extends Comparable<Key>, Value> {

    private Node root;

    /**
     * Put a key-value into this bst.
     *
     * @param key   must not be null.
     * @param value
     */
    public void put(Key key, Value value) {
        if (key == null) {
            throw new IllegalArgumentException("Key must not be null");
        }

        root = internalPut(root, key, value);
    }

    /**
     * Get the value of this key.
     *
     * @param key must not be null.
     * @return
     */
    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("Key must not be null");
        }

        Node node = internalGet(root, key);
        return node == null ? null : node.value;
    }

    /**
     * Update the value of this key.
     *
     * @param key   must not be null.
     * @param value
     */
    public void update(Key key, Value value) {
        if (key == null) {
            throw new IllegalArgumentException("Key must not be null");
        }

        Node node = internalGet(root, key);
        if (node != null) {
            node.value = value;
        }
    }

    /**
     * Delete this key and it's value.
     *
     * @param key must not be null.
     */
    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("Key must not be null");
        }

        root = internalDelete(root, key);
    }

    /**
     * Return size of this bst.
     *
     * @return
     */
    public int size() {
        return internalSize(root);
    }

    private Node internalPut(Node node, Key key, Value value) {
        if (node == null) {
            return new Node(key, value);
        }

        if (key.compareTo(node.key) < 0) {
            node.left = internalPut(node.left, key, value);
        } else if (key.compareTo(node.key) > 0) {
            node.right = internalPut(node.right, key, value);
        } else {
            node.value = value;
        }

        node.nodeCount = internalSize(node.left) + internalSize(node.right) + 1;

        return node;
    }

    private Node internalGet(Node node, Key key) {
        if (node == null) {
            return null;
        }

        if (key.compareTo(node.key) < 0) {
            return internalGet(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            return internalGet(node.right, key);
        } else {
            return node;
        }
    }

    private Node internalDelete(Node node, Key key) {
        if (node == null) {
            return null;
        }

        if (key.compareTo(node.key) < 0) {
            node.left = internalDelete(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            node.right = internalDelete(node.right, key);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            Node deletedNode = node;
            node = min(node.right);
            node.left = deletedNode.left;
            node.right = deleteMin(deletedNode.right);
        }

        node.nodeCount = internalSize(node.left) + internalSize(node.right) + 1;

        return node;
    }

    private Node min(Node node) {
        if (node == null) {
            return null;
        }
        if (node.left == null) {
            return node;
        }
        return min(node.left);
    }

    private Node deleteMin(Node node) {
        if (node == null) {
            return null;
        }
        if (node.left == null) {
            return node.right;
        }

        node = deleteMin(node.left);

        node.nodeCount = internalSize(node.left) + internalSize(node.right) + 1;

        return node;
    }

    private int internalSize(Node node) {
        if (node == null) {
            return 0;
        }
        return node.nodeCount;
    }

    private class Node {
        Key key;
        Value value;

        int nodeCount;
        Node left;
        Node right;

        public Node(Key key, Value value) {
            this.key = key;
            this.value = value;
            this.nodeCount = 1;
        }
    }

}
