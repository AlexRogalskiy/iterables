package com.sensiblemetrics.api.iterables;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Continuous {@link Iterator} implementation
 *
 * @param <E> type of configurable iterable item
 */
public class IteratorFlattener2<E> implements Iterator<E> {
    /**
     * Current {@link Iterator} instance
     */
    private Iterator<E> current;
    /**
     * Root {@link Iterator} instance
     */
    private Iterator<Iterator<E>> root;
    /**
     * Removable flag
     */
    private boolean removable = false;

    /**
     * Default iterator flattener constructor by input {@link Iterator} instance
     *
     * @param root - initial input {@link Iterator} instance to initialize by
     */
    public IteratorFlattener2(final Iterator<Iterator<E>> root) {
        Objects.requireNonNull(root, "Root iterator should not be null");
        this.root = root;
    }

    /**
     * Returns next {@link Iterator} instance
     *
     * @return next {@link Iterator} instance
     */
    private Iterator<E> findNext() {
        while (this.root.hasNext()) {
            this.current = this.root.next();
            if (this.current.hasNext()) {
                return this.current;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see Iterator
     */
    @Override
    public boolean hasNext() {
        if (Objects.isNull(this.current) || !this.current.hasNext()) {
            this.current = this.findNext();
        }
        return (Objects.nonNull(this.current) && this.current.hasNext());
    }

    /**
     * {@inheritDoc}
     *
     * @see Iterator
     */
    @Override
    public E next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException("No next element");
        }
        this.removable = true;
        return this.current.next();
    }

    /**
     * {@inheritDoc}
     *
     * @see Iterator
     */
    @Override
    public void remove() {
        if (!this.removable) {
            throw new IllegalStateException("No elements to remove");
        }
        if (Objects.nonNull(this.current)) {
            this.current.remove();
        }
        this.removable = false;
    }
}
