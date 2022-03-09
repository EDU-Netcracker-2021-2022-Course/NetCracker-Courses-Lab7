package enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum OperationType {

    PUSH_CASH,
    PULL_CASH;

    private static final List<OperationType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();

    public static List<OperationType> getValues() {
        return VALUES;
    }

    public static int getSize() {
        return SIZE;
    }
}
