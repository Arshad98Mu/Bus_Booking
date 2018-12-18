package in.studio.ronak.busbooking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class Booking extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.out){
            ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null){
                        Toast.makeText(Booking.this, "logged out", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Booking.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }



        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        Intent intent = getIntent();
        String bus_no = intent.getStringExtra("bus");
        String seat_no = intent.getStringExtra("seat");
        ParseUser user = ParseUser.getCurrentUser();
       // user.put("status","0");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("user_book");
        query.whereEqualTo("username",user.getUsername());

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null){
                    if (objects.size() > 0){
                        ParseObject object = objects.get(0);
                        object.put("status","0");
                        object.saveInBackground();
                    }
                }
            }
        });

        user.put("bus_no",bus_no);
        user.put("seat_no",seat_no);
        user.saveInBackground();

        TextView textView = findViewById(R.id.details);


        textView.setText("Bus booked"+" \n\n\n "+"bus no:"+user.get("bus_no")+"\n"+"seat no:"+user.get("seat_no"));

    }
    public void onBackPressed(){

    }



}
