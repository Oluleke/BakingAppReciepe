package rainmekka.andela.com.bakingreciepeapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import rainmekka.andela.com.bakingreciepeapp.R;

public class NoConnectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_error);

        String strtext = getIntent().getStringExtra(ReciepeListActivity.ERROR_MESSAGE_TEXT);

        TextView txtNoConnection = (TextView) findViewById(R.id.txt_no_connection);

        txtNoConnection.setText(strtext);
    }
}
