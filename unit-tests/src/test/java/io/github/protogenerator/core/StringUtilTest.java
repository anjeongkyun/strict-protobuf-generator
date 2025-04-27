package io.github.protogenerator.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StringUtilTest {
    @Test
    @DisplayName("returns capitalized string when first character is lowercase")
    void sut_returns_capitalized_string_when_first_char_is_lowercase() {
        // Act
        String actual = StringUtil.capitalize("hello");

        // Assert
        assertEquals("Hello", actual);
    }

    @Test
    @DisplayName("returns same string when first character is already uppercase")
    void sut_returns_same_string_when_first_char_is_uppercase() {
        // Act
        String actual = StringUtil.capitalize("Hello");

        // Assert
        assertEquals("Hello", actual);
    }

    @Test
    @DisplayName("returns empty string when input is empty")
    void sut_returns_empty_string_when_input_is_empty() {
        // Act
        String actual = StringUtil.capitalize("");

        // Assert
        assertEquals("", actual);
    }

    @Test
    @DisplayName("returns null when input is null")
    void sut_returns_null_when_input_is_null() {
        // Act
        String actual = StringUtil.capitalize(null);

        // Assert
        assertNull(actual);
    }

    @Test
    @DisplayName("capitalizes string with only one character")
    void sut_returns_capitalized_one_char_string() {
        // Act
        String actual = StringUtil.capitalize("a");

        // Assert
        assertEquals("A", actual);
    }
}
