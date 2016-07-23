    // -------------------------------------------------------------------
    // Department of Electrical and Computer Engineering
    // University of Waterloo
    //
	// Group Number:205-02
    // Student Names: Lingyi Li, Bill Schoen, James Fong
    // User-ids:l352li, wschoen, jnkfong
    //
    // Assignment: <Lab3>
    // Submission Date: June 18th, 2015
    //
    // We declare that, other than the acknowledgments listed below,
    // this program is my original work.
    //
    // Acknowledgments:
    // Except for the materials provided in the assignment requirements. 
    // All the code is our own work.
    // -------------------------------------------------------------------
package ca.uwaterloo.lab4_205_02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.graphics.PointF;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements PositionListener {
	
	private LineGraphView graph;
	private MapView map;
	private SensorManager sensorManager;
    private SensorControl a;
    private Button btnReset;
    private Button btnCali;
 	private TextView accView;
 	private TextView stepView;
 	private TextView lowThreshView;
 	private TextView highThreshView;
 	private TextView directDisplay;
 	private RadioButton rdoRun;
 	private SeekBar sbFilterLvl;
 	private SeekBar sbLowThresh;
 	private SeekBar sbHighThresh;
 	private LinearLayout containLayout;
 	private TextView testView;
 	
 	private PointF startPt;
 	private PointF endPt;
 	private ArrayList<PointF> points = new ArrayList<PointF>();
 	private ArrayList<PointF> crtPoints = new ArrayList<PointF>();
 	private NavigationalMap navMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	LinearLayout layGraph;
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Retrieve TextViews that will appear on the application 
        accView = (TextView)findViewById(R.id.txtAcc);
        stepView = (TextView)findViewById(R.id.txtStepData);
        lowThreshView = (TextView)findViewById(R.id.txtLowThresh);
        highThreshView = (TextView)findViewById(R.id.txtHighThresh);
        directDisplay = (TextView)findViewById(R.id.DirDisplay);
        //Retrieve Button to reset step counter
        btnReset = (Button)findViewById(R.id.btnReset);
        btnCali = (Button)findViewById(R.id.btnCali);
        //Retrieve linear layout from application
        layGraph = (LinearLayout)findViewById(R.id.mainLayout);
        containLayout =(LinearLayout)findViewById(R.id.containLayout);
        //Declare graph for step counter display signal
        graph = new LineGraphView(getApplicationContext(),100, Arrays.asList("x", "y", "z"));
        graph.setVisibility(View.VISIBLE);
        map = new  MapView(getApplicationContext(), 1200, 800, 40, 30);
        registerForContextMenu(map);
        navMap =MapLoader.loadMap(getExternalFilesDir(null),"E2-3344.svg");
        map.setMap(navMap);
        map.addListener(this);
        
        addCrtPts();
        
        //Add graph view into the existing linear layout
        layGraph.addView(graph, 0);
        containLayout.addView(map,0);

      //TESTING
        testView = (TextView)findViewById(R.id.txtTest);
        
        //Initialize and declare accelerometer & magnetic sensors
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        a = new SensorControl(sensorManager,accView,stepView,lowThreshView, highThreshView, testView, graph, map, this, btnCali);
        
        //Retrieve SeekBar for accuracy change and override method to change accuracy level upon change 
        sbFilterLvl =(SeekBar)findViewById(R.id.sb_filterLvl);        
        sbFilterLvl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){    
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
							a.changeFilter(progress+1);				
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}       	
        });
        
        //Retrieve SeekBar for low threshold change and override method to change threshold level upon change 
        sbLowThresh = (SeekBar)findViewById(R.id.sb_LowThresh);
        sbLowThresh.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
					a.changeLowThresh((float)((progress+1.0)/10.0));				
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}       	
        });
        
        //Retrieve SeekBar for high threshold change and override method to change threshold level upon change 
        sbHighThresh = (SeekBar)findViewById(R.id.sb_HighThresh);
        sbHighThresh.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
					a.changeHighThresh((float)((progress+1.0)/10.0));				
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}       	
        });
        //Initial general threshold and accuracy on start
        sbFilterLvl.setProgress(99);
        sbHighThresh.setProgress(35);
    	sbLowThresh.setProgress(12);
    	
    	//Override Button method to reset step counter to 0
        btnReset.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				a.resetStep();
			}
		});
       
        //Retrieve Radio button and override methods to switch between running and walking
        rdoRun = (RadioButton)findViewById(R.id.rdoRun);
        rdoRun.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            	sbFilterLvl.setProgress(99);
                if(rdoRun.getText().equals("Running Mode")){
                	sbHighThresh.setProgress(86);
                	sbLowThresh.setProgress(14);
                	rdoRun.setChecked(false);
                	rdoRun.setText("Walking Mode");
                }
                else
                {
                	sbHighThresh.setProgress(35);
                	sbLowThresh.setProgress(12);
                	rdoRun.setChecked(false);
                	rdoRun.setText("Running Mode");
                }
            }	
        });
   	
        
    }
    @Override
    public  void  onCreateContextMenu(ContextMenu  menu , View v, ContextMenuInfo  menuInfo){
    	super.onCreateContextMenu(menu , v, menuInfo);
    	map.onCreateContextMenu(menu , v, menuInfo);
    	}
    @Override
    public  boolean  onContextItemSelected(MenuItem  item) {
    	return  super.onContextItemSelected(item) ||  map.onContextItemSelected(item);
    	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
	@Override
	public void originChanged(MapView source, PointF loc) {
		
		map.setUserPoint(loc);
		startPt = loc;
		Log.i("Coordinates", loc.x +" " + loc.y);
		setPoints();
		
	}
	@Override
	public void destinationChanged(MapView source, PointF dest) {
		endPt = dest;
		setPoints();
		
	}
	public void addCrtPts()
	{
		PointF temp = new PointF(20f,18.5f);
    	crtPoints.add(temp);
    	temp = new PointF(12f, 18.5f);
    	crtPoints.add(temp);
    	temp = new PointF(5f,18.5f);
    	crtPoints.add(temp);
	}
	public void setPoints()
	{
		float minS = 100;
		float minE = 100;
		PointF midS = new PointF();
		PointF midE = new PointF();
		if(endPt!=null && startPt!=null)
		{	
				
			if(navMap.calculateIntersections(startPt, endPt).size() > 0)
			{
				points.clear();
				for(int y = 0; y <crtPoints.size(); y++)
				{
					if(VectorUtils.distance(startPt,crtPoints.get(y)) <= minS &&
							navMap.calculateIntersections(startPt, crtPoints.get(y)).size() == 0)
					{
						minS = VectorUtils.distance(startPt,crtPoints.get(y));
						midS = crtPoints.get(y);
						
					}
					if(VectorUtils.distance(endPt,crtPoints.get(y)) <= minE &&
							navMap.calculateIntersections(endPt, crtPoints.get(y)).size() == 0)
					{
						minE = VectorUtils.distance(endPt,crtPoints.get(y));
						midE = crtPoints.get(y);
					}
				}
				if(midS.x!= 0 && midE.x != 0)
				{
					if(midS.x == midE.x && midS.y == midE.y)
					{
						points.clear();
						points.add(startPt);
						points.add(midE);
						points.add(endPt);
						map.setUserPath(points);
					}
					else
					{
						points.clear();
						points.add(startPt);
						points.add(midS);
						points.add(midE);
						points.add(endPt);
						map.setUserPath(points);
					}
				}
				else{
					points.clear();
					map.setUserPath(points);
				}
			}
			else
			{
				points.clear();
				points.add(startPt);
				points.add(endPt);
				map.setUserPath(points);
			}
				
			
		}
		updateDir();
	}
	public ArrayList<PointF> getPoints()
	{
		return points;
	}
	public PointF getStartPt()
	{
		return startPt;	
	}
	
	public void updateDir(){
		if(points.size() != 0 && startPt != null)
		{
			if(points.size()> 2)
			{
				String turn = "";
				float degrees = VectorUtils.angleBetween(points.get(1), points.get(0), points.get(2));
				if(degrees > 0)
				{
					turn = " right";
				}
				else if(degrees < 0)
				{
					turn = " left";
				}
				directDisplay.setText("Please walk forward for " + Math.round(VectorUtils.distance(startPt, points.get(1))/1.5) + 
						" steps and turn"+ turn + " at " + Math.abs(Math.round(Math.toDegrees(degrees))) + (char) 0x00B0);
			}
			else if(points.size() == 2)
			{
				if(VectorUtils.areEqual(points.get(0), points.get(1))){
					directDisplay.setText("You have arrived to your destination.");
				}
				else
				{
					double a = points.get(0).x - points.get(1).x;
					double b = points.get(0).y - points.get(1).y;
					
					directDisplay.setText("Please walk forward for " + Math.round(VectorUtils.distance(startPt, endPt)/1.5) + " steps at "
							+ Math.round(Math.toDegrees(Math.atan2(Math.abs(b),Math.abs(a)))) + (char) 0x00B0);
				}
			}
			
		}
	}
}


