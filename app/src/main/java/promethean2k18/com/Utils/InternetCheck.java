package promethean2k18.com.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetCheck {

    Context context;
    NetworkInfo info;
    ConnectivityManager connectivityManager;
    boolean isNetworkactive;

    public InternetCheck(Context context) {
        this.context = context;
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public boolean isIsInternetAvailable() {
        info = connectivityManager.getActiveNetworkInfo();
        isNetworkactive = info != null && info.isConnectedOrConnecting();
        return isNetworkactive;
    }
}
/*

41+84+588+400+235+800+70+260+300+220+
150+70+300+50+100+200+210+200+100+30+
140+60+180+35+90+55+18+210+210+75+280+200+20+30
= 6011 lines of Java Code

 */