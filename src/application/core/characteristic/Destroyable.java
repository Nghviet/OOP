package application.core.characteristic;

import application.core.GameEntity;

public interface Destroyable extends GameEntity {
    public void doDestroy();

    public boolean isDestroyed();
}
