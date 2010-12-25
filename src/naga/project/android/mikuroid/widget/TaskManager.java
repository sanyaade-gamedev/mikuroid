package naga.project.android.mikuroid.widget;

/**
 * Task manager class.
 * 
 * @author reciente
 * 
 */
public class TaskManager {

    void Destroy() {
    }

    /**
     * Get singleton instance.
     * 
     * @return
     */
    public static TaskManager getInstance() {
        if (null == TaskManager.instance) {
            TaskManager.instance = new TaskManager();
        }

        return TaskManager.instance;
    }

    private static TaskManager instance;

}
