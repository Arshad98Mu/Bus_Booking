package in.studio.ronak.busbooking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {
    EditText username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

    }

    public void clicked(View view){


        ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e==null){
                    Toast.makeText(MainActivity.this, "login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),Destination.class);
                    startActivity(intent);
                    //Intent intent = new Intent(MainActivity.this,ProfileScreen.class);
                    //startActivity(intent);
                }   else {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
