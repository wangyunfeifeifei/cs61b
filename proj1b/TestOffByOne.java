import org.junit.Test;

import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testEqualChars() {
        assertTrue(offByOne.equalChars('y', 'x'));
        assertFalse(offByOne.equalChars('x', 'x'));
        assertTrue(offByOne.equalChars('x', 'y'));
        assertFalse(offByOne.equalChars('X', 'y'));
        assertTrue(offByOne.equalChars('&', '%'));
    }
}
