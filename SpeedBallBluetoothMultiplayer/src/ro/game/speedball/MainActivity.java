package ro.game.speedball;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * The main activity is shown when application starts
 */
public class MainActivity extends Activity 
{
	private Button createGameButton;
	private Button joinGameButton;
	private Button optionsButton;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        createGameButton = (Button)findViewById(R.id.createGameButton);
        joinGameButton   = (Button)findViewById(R.id.joinGameButton);
        optionsButton    = (Button)findViewById(R.id.optionsButton);
        
        createGameButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				//goToActivity(CreateGameActivity.class);
			}
		});
        joinGameButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				goToActivity(JoinGameActivity.class);
			}
		});
        optionsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				goToActivity(OptionsActivity.class);	
			}
		});
    }
    
    private void goToActivity(Class<?> activityClass)
    {
    	Intent intent = new Intent(this, activityClass);
    	startActivity(intent);
    }
}
