package kr.ac.kookmin.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @University Kookmin University
 * @number 20062761
 * @author Taesung Jung
 */
public class Mobile1Activity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        final TextView textView = (TextView) findViewById(R.id.textView);
        
        Button textButton = new Button(this);
        textButton.setText("TextView Button");
        textButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				textView.setText("Clicked Button");
				
			}
		});
        
        Button toastButton = new Button(this);
        toastButton.setText("Toast Button");
        toastButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast toast = Toast.makeText(getApplicationContext(),
						   "Mobile 1", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
		});
        
        linearLayout.addView(textButton);
        linearLayout.addView(toastButton);
       
    }
}