package io.github.wangwang11112222.stringutility;

/**
 * Contract for a mutable string container that supports word counting,
 * Affine-cipher encryption over an alphanumeric alphabet, and in-place
 * expansion of digits into their English names.
 *
 * <p>Implementations hold a single "current string". It starts out
 * uninitialized (null) and is set through {@link #setString(String)}.
 * Every query method below operates on that current string.
 */
public interface MyStringInterface {

    /**
     * Returns the current string.
     *
     * @return the current string, or {@code null} if it has never been set
     */
    String getString();

    /**
     * Replaces the current string.
     *
     * @param string the value to store
     * @throws IllegalArgumentException if {@code string} is empty, or if it
     *                                  contains no letter and no digit
     */
    void setString(String string);

    /**
     * Counts alphabetic words in the current string. An alphabetic word is a
     * maximal run of characters in {@code [a-zA-Z]}; digits, punctuation and
     * whitespace all act as separators.
     *
     * <p>Examples:
     * <ul>
     *   <li>{@code "My numbers are 11, 96, and thirteen"} yields 5</li>
     *   <li>{@code "i#love 2 pr00gram."} yields 4 ("i", "love", "pr", "gram")</li>
     * </ul>
     *
     * @return the number of alphabetic words
     * @throws NullPointerException if the current string is null
     */
    int countAlphabeticWords();

    /**
     * Encrypts the current string with an Affine cipher over a 62-symbol
     * alphabet and returns the result. The current string is left unchanged.
     *
     * <p>The alphabet maps {@code '0'..'9'} to 0..9, {@code 'A'..'Z'} to 10..35
     * and {@code 'a'..'z'} to 36..61. A character with value {@code x} becomes
     * the character whose value is {@code (arg1 * x + arg2) % 62}. Characters
     * outside the alphabet pass through untouched.
     *
     * <p>Example: encrypting {@code "Cat & 5 DogS"} with {@code arg1 = 5} and
     * {@code arg2 = 3} produces {@code "1xU & S 65RJ"}, because 'C' (12) maps to
     * {@code (12 * 5 + 3) % 62 == 1} which is '1', 'a' (36) maps to
     * {@code (36 * 5 + 3) % 62 == 59} which is 'x', and the spaces and '&' are
     * copied verbatim.
     *
     * @param arg1 multiplicative key; must be in [0, 62) and coprime to 62
     * @param arg2 additive key; must be in [1, 62)
     * @return the encrypted string
     * @throws NullPointerException     if the current string is null
     * @throws IllegalArgumentException if either key violates its range or
     *                                  coprimality constraint
     */
    String encrypt(int arg1, int arg2);

    /**
     * Rewrites the current string in place, replacing each digit found between
     * {@code firstPosition} and {@code finalPosition} (both inclusive, 1-based)
     * with its English name: "Zero", "One", ... "Nine". Characters outside that
     * window, and non-digit characters inside it, are left alone.
     *
     * <p>Examples:
     * <ul>
     *   <li>{@code "abc416d"} with the window 2..7 becomes {@code "abcFourOneSixd"}</li>
     *   <li>{@code "I'd b3tt3r put s0me d161ts in this 5tr1n6, right?"} with the
     *       window 17..23 becomes
     *       {@code "I'd b3tt3r put sZerome dOneSix1ts in this 5tr1n6, right?"}</li>
     * </ul>
     *
     * @param firstPosition 1-based index of the first character to consider
     * @param finalPosition 1-based index of the last character to consider
     * @throws NullPointerException        if the current string is null
     * @throws IllegalArgumentException    if {@code firstPosition < 1} or
     *                                     {@code firstPosition > finalPosition}
     * @throws MyIndexOutOfBoundsException if {@code finalPosition} exceeds the
     *                                     length of the current string
     */
    void convertDigitsToNamesInSubstring(int firstPosition, int finalPosition);
}
