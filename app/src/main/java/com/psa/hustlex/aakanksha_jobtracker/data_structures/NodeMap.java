package com.psa.hustlex.aakanksha_jobtracker.data_structures;

import java.util.HashSet;
import java.util.Set;

public class NodeMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private Entry<K, V>[] entries;
    private int size;

    @SuppressWarnings("unchecked")
    public NodeMap() {
        entries = new Entry[DEFAULT_CAPACITY];
        size = 0;
    }

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

    public int size() {
        return size;
    }

    private int getIndex(K key) {
        return Math.abs(key.hashCode()) % entries.length;
    }


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

