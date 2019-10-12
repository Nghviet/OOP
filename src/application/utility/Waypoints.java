package application.utility;

import java.util.ArrayList;
import java.util.List;

public class Waypoints {
    private List<Vector2> waypoints;

    public static Waypoints instance = new Waypoints();

    Waypoints() {
        waypoints = new ArrayList<>();
    }

    public Vector2 getIndex(int i) {
        return waypoints.get(i);
    }

    public int size() {
        return waypoints.size();
    }

    public void setWaypoints(List<Vector2> waypoints) {
        this.waypoints = waypoints;
    }
}
