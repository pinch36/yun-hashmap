package com.yun.hashmap;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: __yun
 * @Date: 2024/09/30/7:26
 * @Description:
 */
public class HashMap<K, V> implements Map<K,V>{
    private NodeEntry<K, V>[] map;
    private final int DEFAULT_CAPACITY = 8;
    private final float DEFAULT_LOAD_FACTOR = 0.75F;
    private int seek;
    private int size = 0;
    private int capacity;
    private int[] listSize;
    private float loadFactor;

    public HashMap() {
        capacity = DEFAULT_CAPACITY;
        map = new NodeEntry[capacity];
        seek = capacity - 1;
        listSize = new int[capacity];
        loadFactor = DEFAULT_LOAD_FACTOR;
    }

    private int hash(Object o) {
        return o.hashCode();
    }

    public void put(K key, V value) {
        if (key == null || value == null) {
            throw new RuntimeException("键值不能为空..");
        }
        int position = hash(key) & seek;
        boolean put = put(map, key, value);
        if (put){
            listSize[position]++;
            size++;
        }
        if (size >= loadFactor * capacity || listSize[position] >= 8) {
            rehash();
        }
    }

    private boolean put(NodeEntry[] entries, K key, V value) {
        int position = hash(key) & seek;
        if (entries[position] == null) {
            entries[position] = new NodeEntry<>();
        }
        NodeEntry<K, V> cur = entries[position];
        return cur.insert(key,value);
    }

    public V get(K key) {
        if (key == null) {
            throw new RuntimeException("键不能为空..");
        }
        int position = hash(key) & seek;
        NodeEntry<K, V> cur = map[position];
        if (cur == null) {
            return null;
        }
        V value = cur.find(key);
        if (value == null){
            return null;
        }
        return value;
    }

    public int size() {
        return size;
    }

    public void delete(K key, V value) {
        if (key == null || value == null) {
            throw new RuntimeException("键值不能为空..");
        }
        int position = hash(key) & seek;
        NodeEntry<K, V> cur = map[position];
        if (cur == null){
            return;
        }
        boolean delete = cur.delete(key, value);
        if (delete){
            listSize[position]--;
            size--;
        }
    }

    private void rehash() {
        int newCapacity = capacity << 1;
        int newSeek = newCapacity - 1;
        int[] newListSize = new int[newCapacity];
        int newSize = 0;
        NodeEntry<K, V>[] kvEntry = new NodeEntry[newCapacity];
        for (int i = 0; i < capacity; i++) {
            if (map[i] == null) {
                continue;
            }
            NodeEntry<K, V> pre = map[i];
            while (pre.next != null) {
                NodeEntry<K, V> cur = pre.next;
                int position = hash(cur.key) & newSeek;
                if (kvEntry[position] == null) {
                    kvEntry[position] = new NodeEntry<>();
                }
                NodeEntry<K, V> listCur = kvEntry[position];
                while (listCur.next != null) {
                    listCur = listCur.next;
                }
                listCur.next = cur;
                pre = pre.next;
                newListSize[position]++;
                newSize++;
            }
        }
        map = kvEntry;
        capacity = newCapacity;
        size = newSize;
        seek = newSeek;
        listSize = newListSize;
    }
    public interface Entry<K, V>{
        boolean insert(K key,V value);
        boolean delete(K key,V value);
        V find(K key);
    }
    public class NodeEntry<K, V> implements Entry<K, V> {
        private K key;
        private V value;
        private NodeEntry<K, V> next;

        public NodeEntry() {
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public NodeEntry<K, V> getNext() {
            return next;
        }

        public void setNext(NodeEntry<K, V> next) {
            this.next = next;
        }

        public boolean insert(K key,V value) {
            NodeEntry cur = this;
            NodeEntry pre = cur;
            while (cur != null) {
                if (cur.key == key) {
                    cur.value = value;
                    return false;
                }
                pre = cur;
                cur = cur.next;
            }
            NodeEntry<K, V> kvEntry = new NodeEntry<>();
            kvEntry.key = key;
            kvEntry.value = value;
            pre.next = kvEntry;
            return true;
        }

        public V find(K key) {
            NodeEntry cur = this;
            NodeEntry pre = cur;
            while (cur != null) {
                if (cur.key == key){
                    return (V) cur.value;
                }
                pre = cur;
                cur = cur.next;
            }
            return (V) pre.value;
        }

        public boolean delete(K key, V value) {
            NodeEntry cur = this;
            NodeEntry<K, V> pre = cur;
            while (cur.key != key) {
                if (cur.next == null) {
                    return false;
                }
                pre = cur;
                cur = cur.next;
            }
            pre.next = cur.next;
            return true;
        }
    }

    public class TreeNode<K, V> extends NodeEntry<K, V> {
        private boolean red;
        private TreeNode<K, V> left;
        private TreeNode<K, V> right;
        private TreeNode<K, V> parent;

        public TreeNode() {
            red = true;
        }

        private TreeNode<K, V> root() {
            if (parent != null) {
                return parent.root();
            }
            return this;
        }

        private TreeNode<K, V> other() {
            if (parent.left == this) {
                return parent.right;
            }
            return parent.left;
        }

        public boolean insert(K key, V value) {
            TreeNode<K, V> root = root();
            int hash = hash(key);
            TreeNode<K, V> cur = root;
            TreeNode<K, V> pre = null;
            while (cur != null) {
                int curHash = hash(cur.getKey());
                if (hash == curHash) {
                    cur.setValue(value);
                    return false;
                }
                pre = cur;
                if (hash > curHash) {
                    cur = cur.right;
                } else {
                    cur = cur.left;
                }
            }
            TreeNode<K, V> kvTreeNode = new TreeNode();
            kvTreeNode.setKey(key);
            kvTreeNode.setValue(value);
            if (pre == null) {
                kvTreeNode.red = false;
                return true;
            }
            if (hash > hash(pre.getKey())) {
                pre.right = kvTreeNode;
            } else {
                pre.left = kvTreeNode;
            }
            kvTreeNode.parent = pre;
            //调整树到符合红黑树性质
            while (true) {
                if (!pre.red) {
                    return true;
                }
                if (pre.parent == null) {
                    pre.red = false;
                    return true;
                }
                if (pre.other().red) {
                    pre.red = false;
                    pre.other().red = false;
                    pre.parent.red = true;
                    pre = pre.parent.parent;
                    continue;
                }
                if (pre.left == kvTreeNode) {
                    TreeNode<K, V> temp = pre.parent;
                    pre.parent = temp.parent;
                    pre.right = temp;
                    temp.parent = pre;
                    temp.left = null;
                    pre.red = false;
                    temp.red = true;
                    return true;
                }
                if (pre.right == kvTreeNode) {
                    TreeNode<K, V> temp = pre.parent;
                    temp.left = pre.right;
                    pre.right.parent = temp;
                    pre.parent = pre.right;
                    pre.right.left = pre;
                    pre.right = null;
                    pre.parent = temp.parent;
                    pre.right = temp;
                    temp.parent = pre;
                    temp.left = null;
                    pre.red = false;
                    temp.red = true;
                    return true;
                }
            }
        }

        public void rotateLeft(TreeNode<K, V> node) {
            TreeNode<K, V> temp = node.parent;
            TreeNode<K, V> child = node.right;
            temp.left = child;
            child.parent = temp;
            node.right = null;
            node.parent = child;
            child.left = node;
        }

        public void rotateRight(TreeNode<K, V> node) {
            TreeNode<K, V> temp = node.parent;
            TreeNode<K, V> child = node.left;
            child.parent = temp;
//            temp.
        }

        public boolean delete(K key, V value) {
            TreeNode<K, V> root = root();
            int hash = hash(key);
            TreeNode<K, V> cur = root;
            TreeNode<K, V> pre = null;
            while (cur != null) {
                int curHash = hash(cur.getKey());
                if (hash == curHash) {
                    cur.setValue(value);
                    return false;
                }
                pre = cur;
                if (hash > curHash) {
                    cur = cur.right;
                } else {
                    cur = cur.left;
                }
            }
            return true;
        }
    }
}
