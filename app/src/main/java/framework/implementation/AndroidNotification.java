package framework.implementation;

import framework.Image;
import framework.Notification;

/**
 * Created by BEN on 17/12/2015.
 */
public class AndroidNotification implements Notification {

    private static int inc = 0;
    private int id;
    private String contentTitle;
    private String contentText;
    private Image icon;

    public AndroidNotification() {
        id = inc;
        inc++;
    }

    public AndroidNotification(String title, String desc) {
        id = inc;
        contentTitle = title;
        contentText = desc;
        inc++;
    }

    public AndroidNotification(String title, String desc, Image icon) {
        id = inc;
        contentTitle = title;
        contentText = desc;
        this.icon = icon;
        inc++;
    }

    @Override
    public void setSmallIcon(Image icon) {
        this.icon = icon;
    }

    @Override
    public void setContentTitle(String title) {
        contentTitle = title;
    }

    @Override
    public void setContentText(String text) {
        contentText = text;
    }

    @Override
    public Image getSmallIcon() {
        return icon;
    }

    @Override
    public String getContentTitle() {
        return contentTitle;
    }

    @Override
    public String getContentText() {
        return contentText;
    }

    @Override
    public void cancel() {

    }

    @Override
    public void cancelAll() {

    }

    @Override
    public void setAutoCancel() {

    }
}
