package attachments;

import java.util.Random;

public class AttachmentAssets {

    final static String focusURLs[] = {
            "http://dailypersonalitydevelopment.com/wp-content/uploads/2015/03/The-Importance-Of-Focus-in-Yoga-And-Life-733x440.jpg",
            "https://assets.entrepreneur.com/content/16x9/822/20150408183930-focus-distance-view-startup-marketing.jpeg",
            "http://mindfulhappiness.org/wp-content/uploads/2015/01/2015-MindfulHappiness_WhatisMindfulness.jpg",
            "http://what-buddha-said.net/Pics/Buddha.blue.star.jpg",
            "http://vijayagrawal.net/wp-content/uploads/concentration.jpg"
    };

    final static String breakURLs[] = {
            "https://i.ytimg.com/vi/pC2xKOj-FSM/hqdefault.jpg",
            "http://www.adweek.com/socialtimes/files/2014/10/keyboard-break.jpg",
            "http://afterschoolcentre.org/wp-content/uploads/2015/09/Ade5.jpg",
            "http://www.silentjourney.com/blog/wp-content/uploads/2013/11/Time-For-A-Break.jpg",
            "http://www.hercampus.com/sites/default/files/2015/10/12/Break.jpg"
    };

    public static String getFocusAttachment() {
        Random r = new Random();
        return focusURLs[r.nextInt(focusURLs.length)];
    }

    public static String getBreakAttachment() {
        Random r = new Random();
        return breakURLs[r.nextInt(breakURLs.length)];
    }

}
