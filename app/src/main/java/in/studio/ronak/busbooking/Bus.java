package in.studio.ronak.busbooking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class Bus extends AppCompatActivity {
  //  int x = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);
        final ListView listView = findViewById(R.id.bus);
        final ArrayList<String> b_name = new ArrayList<>();
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,b_name);
        Intent intent = getIntent();
        String seat = String.valueOf(0);
        String place = intent.getStringExtra("place");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("bus");
        query.whereEqualTo("place",place);
        query.whereGreaterThan("total_seat",seat);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e==null){
                    if (objects.size()>0){
                        for (ParseObject object : objects) {
                            b_name.add((String) object.get("bus_no"));
                        }

                    } else {
                        b_name.add("no bus available");
                        Toast.makeText(Bus.this, "no bus available", Toast.LENGTH_LONG).show();
                    }
                    listView.setAdapter(arrayAdapter);
                }

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ParseQuery<ParseObject> query1 = ParseQuery.getQuery("bus");
                query1.whereEqualTo("bus_no",b_name.get(i)) ;



                query1.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> objects, ParseException eg) {
                        if (eg == null && objects.size() > 0) {
                            ParseObject seat = objects.get(0);
                            String s = (String) seat.get("total_seat");
                            int x = Integer.parseInt(s);
                            Intent intent = new Intent(getApplicationContext(),Booking.class);
                            intent.putExtra("bus",(String)seat.get("bus_no"));
                            intent.putExtra("seat",String.valueOf(x));

                            startActivity(intent);

                            x--;
                            /*Intent intent1 = new Intent(getApplicationContext(),Booking.class);
                            intent1.putExtra("seat",String.valueOf(x));*/

                            s = String.valueOf(x);
                            seat.put("total_seat", s);

                            //Grillen.put("Rechnung", false);
                            seat.saveInBackground(new SaveCallback() {
                                public void done(ParseException e) {
                                    if (e == null) {
                                        //success, saved!
                                        Log.d("MyApp", "Successfully saved!");
                                    } else {
                                        //fail to save!
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }else {
                            //fail to get!!
                            eg.printStackTrace();
                        }
                    }
                });




               /* query1.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        object.put("total seat",String.valueOf(3));
                        object.saveInBackground();
                    }
                });*/





              /*  Intent intent = new Intent(getApplicationContext(),Booking.class);
                intent.putExtra("bus",b_name.get(i));
                intent.putExtra("seat",String.valueOf(X[0]));

                startActivity(intent);*/
            }
        });

    }
}
