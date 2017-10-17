package witty.wittybus.supportfiles;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

import witty.wittybus.R;

public class Alert
{
    Context context;
    String title,message;
    Activity activity;
    public Alert(Context context,String title, String message, final Activity activity)
    {
        this.context    = context;
        this.title      = title;
        this.message    = message;
        this.activity   = activity;
    }
    public void showNetworkAlert()
    {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setIcon(R.mipmap.alert);
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which) {
                context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            }
        });
        alertDialog.setNegativeButton("Quit", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                activity.finish();
                System.exit(0);
            }
        });
        alertDialog.show();
    }
}
