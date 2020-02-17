package com.sensiblemetrics.api.iterables;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.sensiblemetrics.api.iterables.AssertionUtils.checkThrows;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("UNIT")
class IteratorFlattener2Test {

    @Test
    void test_iterableFlattener_whenPassed_validInput() {
        // given
        final List<Integer> firstList = new ArrayList<>();
        firstList.add(1);
        firstList.add(3);
        firstList.add(5);

        final List<Integer> secondList = new ArrayList<>();
        secondList.add(2);
        secondList.add(4);
        secondList.add(6);

        final List<Iterator<Integer>> source = new ArrayList<>();
        source.add(firstList.iterator());
        source.add(secondList.iterator());

        // when
        final IteratorFlattener2<Integer> iteratorFlattener = new IteratorFlattener2<>(source.iterator());

        // then
        assertTrue(iteratorFlattener.hasNext());
        assertThat(iteratorFlattener.next(), is(equalTo(1)));

        assertTrue(iteratorFlattener.hasNext());
        assertThat(iteratorFlattener.next(), is(equalTo(2)));
    }

    @Test
    void test_iterableFlattener_whenPassed_nullableInput() {
        // given
        final Iterator<Iterator<Integer>> source = null;

        final String errorMessage = "Root iterator should not be null";

        // when
        final NullPointerException thrown = checkThrows(NullPointerException.class, () -> new IteratorFlattener2<>(source));

        // then
        assertThat(thrown.getMessage(), is(equalTo(errorMessage)));
    }
}