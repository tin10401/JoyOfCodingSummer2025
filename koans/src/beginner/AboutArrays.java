package beginner;

import com.sandwich.koan.Koan;

import java.util.Arrays;

import static com.sandwich.koan.constant.KoanConstants.__;
import static com.sandwich.util.Assert.assertEquals;

public class AboutArrays {

    @Koan
    public void arraysDoNotConsiderElementsWhenEvaluatingEquality() {
        // arrays utilize default object equality (A == {1} B == {1}, though A
        // and B contain the same thing, the container is not the same
        // referenced array instance...
        assertEquals(new int[]{1}.equals(new int[]{1}), false);
    }

    @Koan
    public void cloneEqualityIsNotRespected() { //!
        int[] original = new int[]{1};
        assertEquals(original.equals(original.clone()), false);
    }

    @Koan
    public void anArraysHashCodeMethodDoesNotConsiderElements() {
        int[] array0 = new int[]{0};
        int[] array1 = new int[]{0};
        assertEquals(Integer.valueOf(array0.hashCode()).equals(array1.hashCode()), false); // not equal!
        // TODO: ponder the consequences when arrays are used in Hash Collection implementations.
    }

    @Koan
    public void arraysHelperClassEqualsMethodConsidersElementsWhenDeterminingEquality() {
        int[] array0 = new int[]{0};
        int[] array1 = new int[]{0};
        assertEquals(Arrays.equals(array0, array1), true);    // whew - what most people assume
        // about equals in regard to arrays! (logical equality)
    }

    @Koan
    public void arraysHelperClassHashCodeMethodConsidersElementsWhenDeterminingHashCode() {
        int[] array0 = new int[]{0};
        int[] array1 = new int[]{0};
        // whew - what most people assume about hashCode in regard to arrays!
        assertEquals(Integer.valueOf(Arrays.hashCode(array0)).equals(Arrays.hashCode(array1)), true);
    }

    @Koan
    public void arraysAreMutable() {
        final boolean[] oneBoolean = new boolean[]{false};
        oneBoolean[0] = true;
        assertEquals(oneBoolean[0], true);
    }

    @Koan
    public void arraysAreIndexedAtZero() {
        int[] integers = new int[]{1, 2};
        assertEquals(integers[0], 1);
        assertEquals(integers[1], 2);
    }

    @Koan
    public void arrayIndexOutOfBounds() {
        int[] array = new int[]{1};
    }

    @Koan
    public void arrayLengthCanBeChecked() {
        assertEquals(new int[1].length, 1);
    }

}
