package in.studio.ronak.busbooking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class Destination extends AppCompatActivity {
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
                        Toast.makeText(Destination.this, "logged out", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Destination.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
        final TextView textView = findViewById(R.id.booked);

        ParseUser user = ParseUser.getCurrentUser();
        String username = user.getUsername();
        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("user_book");
        query2.whereEqualTo("username",username);
        query2.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e==null){
                    if (objects.size()>0){
                        ParseObject object = objects.get(0);
                        String s = (String) object.get("status");
                        if (s.equals("1")){

                            final ArrayList<String> name = new ArrayList<>();
                            final ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,name);
                            final ListView listView = findViewById(R.id.destination);

                            ParseQuery<ParseObject> query = ParseQuery.getQuery("destination");
                            query.addAscendingOrder("place");



                            query.findInBackground(new FindCallback<ParseObject>() {
                                @Override
                                public void done(List<ParseObject> objects, ParseException e) {
                                    if (e==null){
                                        if (objects.size()>0){
                                            for (ParseObject object : objects) {
                                                name.add((String) object.get("place"));
                                            }

                                            listView.setAdapter(arrayAdapter);

                                        }
                                    } else {
                                        Toast.makeText(Destination.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Intent intent = new Intent(getApplicationContext(),Bus.class);
                                    intent.putExtra("place",name.get(i));
                                    startActivity(intent);
                                }
                            });




                        } else {

                            /*Intent intent = new Intent(getApplicationContext(),Booking.class);
                            startActivity(intent);*/

                            ParseUser user1 = ParseUser.getCurrentUser();
                            textView.setText("you are already booked"+"\n"+"busno:"+user1.get("bus_no")+"\n"+"seat_no"+user1.get("seat_no"));
                            textView.setAlpha(1);
                            Toast.makeText(Destination.this, "already booked", Toast.LENGTH_SHORT).show();


                        }
                    }
                }
            }
        });


    }
    public void onBackPressed(){

    }
}
