package pingaz.netledger.node.assertion;

/**
 * @author ping
 */
public class Assertions {
    public static <T> T isNullArgument(final String name, final T value) {
        if (value != null) {
            throw new IllegalArgumentException(name + " should be null");
        }
        return value;
    }
    public static <T> T isNull(final T value, final String message) {
        if (value != null) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }
    public static <T> T isNull(final T value, final String message, final Object ...params) {
        if (value != null) {
            throw new IllegalArgumentException(String.format(message, params));
        }
        return value;
    }

    public static <T> T notNull(final T value, final String message, final Object ...params) {
        if (value == null) {
            throw new IllegalArgumentException(String.format(message, params));
        }
        return value;
    }
    public static <T> T notNull(final T value, final String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }
    public static <T> T notNullArgument(final String name, final T value) {
        if (value == null) {
            throw new IllegalArgumentException(name + " can not be null");
        }
        return value;
    }
    public static void isTrue(final String name, final boolean condition) {
        if (!condition) {
            throw new IllegalStateException(name);
        }
    }
    public static void isTrueArgument(final String name, final boolean condition) {
        if (!condition) {
            throw new IllegalArgumentException("state should be: " + name);
        }
    }
}
