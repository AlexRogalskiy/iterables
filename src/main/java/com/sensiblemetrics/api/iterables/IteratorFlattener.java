package com.sensiblemetrics.api.iterables;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Continuous {@link Iterator} implementation
 *
 * @param <E> type of configurable iterable item
 */
public class IteratorFlattener<E> implements Iterator<E> {

    /**
     * Iterator node implementation
     *
     * @param <T> type of configurable iterable item
     */
    private static final class IteratorNode<T> {
        private final Iterator<T> head;
        private IteratorNode<T> tail;

        /**
         * Default iterator helper constructor by input {@link Iterator} instance
         *
         * @param head - initial input {@link Iterator} instance to initialize by
         */
        IteratorNode(final Iterator<T> head) {
            this.head = head;
        }
    }

    /**
     * Default current {@link IteratorNode}
     */
    private IteratorNode<E> current;
    /**
     * Default last {@link IteratorNode}
     */
    private IteratorNode<E> last;
    /**
     * Default next calculated flag
     */
    private boolean nextCalculated = false;

    /**
     * Default iterator flattener constructor by input {@link Iterator} instance
     *
     * @param root - initial input {@link Iterator} instance to initialize by
     */
    public IteratorFlattener(final Iterator<Iterator<E>> root) {
        Objects.requireNonNull(root, "Root iterator should not be null");

        this.current = this.last = root.hasNext() ? new IteratorNode<>(root.next()) : null;
        while (root.hasNext()) {
            this.last = this.last.tail = new IteratorNode<>(root.next());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see Iterator
     */
    @Override
    public boolean hasNext() {
        if (this.nextCalculated) {
            return Objects.nonNull(this.current);
        }
        this.nextCalculated = true;
        while (true) {
            if (this.current.head.hasNext()) {
                return true;
            }
            this.current = this.current.tail;
            if (Objects.isNull(this.current)) {
                this.last = null;
                return false;
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see Iterator
     */
    @Override
    public E next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        this.nextCalculated = false;
        return this.current.head.next();
    }

    /**
     * {@inheritDoc}
     *
     * @see Iterator
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove");
    }
}
