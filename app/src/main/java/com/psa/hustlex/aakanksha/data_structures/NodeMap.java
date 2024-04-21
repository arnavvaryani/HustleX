package com.psa.hustlex.aakanksha.data_structures;

import java.util.HashSet;
import java.util.Set;

/**
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public class NodeMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private Entry<K, V>[] entries;
    private int size;

    /**
     * Constructs an empty NodeMap with the default initial capacity (16) and default load factor (0.75).
     */
    @SuppressWarnings("unchecked")
    public NodeMap() {
        entries = new Entry[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old value is replaced.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @throws IllegalArgumentException if the key is null
     */
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        int index = getIndex(key);
        Entry<K, V> entry = new Entry<>(key, value);
        if (entries[index] == null) {
            entries[index] = entry;
            size++;
        } else {
            Entry<K, V> current = entries[index];
            while (current.next != null) {
                if (current.key.equals(key)) {
                    current.value = value;
                    return;
                }
                current = current.next;
            }
            if (current.key.equals(key)) {
                current.value = value;
            } else {
                current.next = entry;
                size++;
            }
        }
        if ((float) size / entries.length > LOAD_FACTOR) {
            resize();
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or null if this map contains no mapping for the key
     */
    public V get(K key) {
        int index = getIndex(key);
        Entry<K, V> entry = entries[index];
        while (entry != null) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
            entry = entry.next;
        }
        return null;
    }

    /**
     * Returns true if this map contains a mapping for the specified key.
     *
     * @param key key whose presence in this map is to be tested
     * @return true if this map contains a mapping for the specified key
     */
    public boolean containsKey(K key) {
        int index = getIndex(key);
        Entry<K, V> entry = entries[index];
        while (entry != null) {
            if (entry.key.equals(key)) {
                return true;
            }
            entry = entry.next;
        }
        return false;
    }

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of key-value mappings in this map
     */
    public int size() {
        return size;
    }

    /**
     * Computes the hash table bucket index for a given key.
     *
     * @param key the key to calculate the index for
     * @return the index of the bucket
     */
    private int getIndex(K key) {
        return Math.abs(key.hashCode()) % entries.length;
    }

    /**
     * Returns a Set view of the mappings contained in this map.
     *
     * @return a set view of the mappings contained in this map
     */
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entrySet = new HashSet<>();
        for (Entry<K, V> entry : entries) {
            while (entry != null) {
                entrySet.add(entry);
                entry = entry.next;
            }
        }
        return entrySet;
    }

    /**
     * Doubles the capacity of the hash table and rehashes all existing entries.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        Entry<K, V>[] newEntries = new Entry[entries.length * 2];
        for (Entry<K, V> entry : entries) {
            while (entry != null) {
                int index = getIndex(entry.key);
                Entry<K, V> next = entry.next;
                entry.next = newEntries[index];
                newEntries[index] = entry;
                entry = next;
            }
        }
        entries = newEntries;
    }

    /**
     * Static nested class used to represent a map entry.
     * Each entry in a map corresponds to a key-value pair and may link to another entry in the same bucket.
     */
    public static class Entry<K, V> {
        final K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
        public V getValue() {
            return value;
        }
    }
}

