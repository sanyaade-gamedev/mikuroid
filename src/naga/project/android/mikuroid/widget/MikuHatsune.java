package naga.project.android.mikuroid.widget;

public class MikuHatsune extends WidgetCharacter {

    public static MikuHatsune getInstance() {
        if (null == MikuHatsune.instance) {
            MikuHatsune.instance = new MikuHatsune();
        }
        
        return MikuHatsune.instance;
    }

    private MikuHatsune() {
        super("MikuHatsune");
    }

    private static MikuHatsune instance;

}
