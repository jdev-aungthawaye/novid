package software.techbase.novid.component.ui.map.clustering;

import androidx.annotation.Nullable;

/**
 * Created by Wai Yan on 4/4/20.
 */
final class Preconditions {

    static <T> T checkNotNull(@Nullable T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }
}