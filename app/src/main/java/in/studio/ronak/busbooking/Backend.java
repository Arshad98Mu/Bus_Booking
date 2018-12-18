package in.studio.ronak.busbooking;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class Backend extends Application {
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("e2ec94aa7bdaeaeae3fe41a08351bf1ad6481db4")
                .clientKey("f75598324210eb71760c1462b1c5c04706c55873")
                .server("http://18.219.115.2:80/parse")
                .build()
        );

   /* ParseObject object = new ParseObject("svit");
    object.put("myNumber", "123");
    object.put("myString", "Ronak");

    object.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException ex) {
        if (ex == null) {
          Log.i("Parse Result", "Successful!");
        } else {

          Log.i("Parse Result", "Failed" + ex.toString());

        }
      }
    });*/


        //ParseUser.enableAutomaticUser();

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }
}
