package devmobile.hearc.ch.notifium;

public class ListAlertItem {

    private String name;
    private boolean isChecked;

    public ListAlertItem(String name, boolean isChecked) {
        this.name = name;
        this.isChecked = isChecked;
    }

    public String getName() {
        return name;
    }

    public boolean isChecked() {
        return isChecked;
    }
}
