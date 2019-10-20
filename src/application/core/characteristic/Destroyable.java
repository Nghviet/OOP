package application.core.characteristic;

import application.core.GameEntity;

public interface Destroyable extends GameEntity {
    void doDestroy();

    boolean isDestroyed();
}
