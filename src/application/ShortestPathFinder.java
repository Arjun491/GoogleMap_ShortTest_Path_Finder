/** This Application class interact with Google Map Api  and provid the user 
 * Interface 
 * 
 * @author UCSD MOOC development team
 * 
 @author Arjun Singh and Royali 

 * 
 */
package application;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
//In this class ,  LinkList, ArrayList, List and Arrays are used 
import java.util.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos; // for setting geographic positions
import javafx.scene.Scene;
// GUI COMPONENTS //

import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import application.controllers.FetchController;
import application.controllers.RouteController;
import application.services.GeneralService;
import application.services.RouteService;
import gmapsfx.GoogleMapView;
import gmapsfx.MapComponentInitializedListener;
import gmapsfx.javascript.object.GoogleMap;
import gmapsfx.javascript.object.LatLong;
import gmapsfx.javascript.object.MapOptions;
import gmapsfx.javascript.object.MapTypeIdEnum;


/*
 * 
 * ShortestPathFinder.java is the main class to run this application

 * */
public class ShortestPathFinder extends Application implements MapComponentInitializedListener {
	  protected BorderPane borderPane;
	  protected Stage primaryStage;
	protected GoogleMapView googlemapComponent;
	protected GoogleMap map;



  // for fetch button and margin  CONSTANTS
	 private static final double FETCH_COMPONENT_WIDTH = 200.0; // defined width for fetch button
	 // margin //
  private static final double MARGIN_VALUE = 10; 
 
  public static void main(String args[]){
     launch(args);
  }


	@Override
  public void start(Stage primaryStage) throws Exception {
    this.primaryStage = primaryStage;

		// a main container for the map that holds all the components like buttons and radioButtons
    borderPane = new BorderPane();

        // set up map
    googlemapComponent = new GoogleMapView(); // intialize the GoogleMapView
		System.out.println("GoogleMapView created ....... ");
		googlemapComponent.addMapInitializedListener(this);
		System.out.println("Now we added map initialized listener into the Google Map View class ");
		// initialize tabs for data fetching and route controls
    Tab routeTab = new Tab("FetchDataRouting");

    // create components for fetch tab
    Button fetchBtn = new Button("Fetch Map Data.. ");
    Button displayBtn = new Button("Get Intersections.. ");
    TextField txtField = new TextField();
    ComboBox<DataSet> comBoxSet = new ComboBox<DataSet>();
// a event listener called set on mouse pressed  as a anonymous class 
    comBoxSet.setOnMousePressed( e -> {
    	comBoxSet.requestFocus();
    });

    
    HBox fetchedControls = getBottomBox(txtField, fetchBtn);
// inside container ....//
    VBox fetchedBox = getFetchBox(displayBtn, comBoxSet); //


    // create components for fetch tab
    Button mapRouteBtn = new Button("Show Route");
    Button hideRouteButton = new Button("Hide Route");
    Button resetButton = new Button("Reset");
    Button visualizationButton = new Button("Start Visualization");
    Image sImage = new Image(MarkerManager.startURL); // image for start  point  in the graph 
    // ref:https://www.laco.eu/wp-content/themes/laco/assets/img/icons/google-maps-icons/icon-map-marker.png
   
    Image dImage = new Image(MarkerManager.stopURL); // image for destination point
    // image ref:
    CLabel<geography.GeographicPoint> startLabel = 
    		new CLabel<geography.GeographicPoint>("Empty.", 
    											  new ImageView(sImage), null);
    List<CLabel<geography.GeographicPoint>> stopLabels = 
    		new ArrayList<CLabel<geography.GeographicPoint>>();
    for (int i = 0; i < RouteController.MAXSTOPS; i++) {
        stopLabels.add(new CLabel<geography.GeographicPoint>("Empty.", 
		  		 new ImageView(dImage), null));
    }
    
    //TODO -- hot fix
    startLabel.setMinWidth(180);
    for (Label stopLabel : stopLabels) {
        stopLabel.setMinWidth(180);
    }
//        startLabel.setWrapText(true);
//        endLabel.setWrapText(true);
    Button startButton = new Button("Start");
    List<Button> stopButtons = new ArrayList<Button>();
    for (int i = 0; i < RouteController.MAXSTOPS; i++) {
    	stopButtons.add(new Button("Stop " + (i+1)));
    	//stopButtons.get(i).setId(Integer.toString(i));
    }
    // Radio buttons for selecting search algorithm
    final ToggleGroup group = new ToggleGroup();

    List<RadioButton> searchOptions = setupToggle(group);


    // Select and marker managers for route choosing and marker display/visuals
    // should only be one instance (singleton)
    SelectManager manager = new SelectManager();
    MarkerManager markerManager = new MarkerManager();
    markerManager.setSelectManager(manager);
    manager.setMarkerManager(markerManager);
    markerManager.setVisButton(visualizationButton);

    // create components for route tab
    CLabel<geography.GeographicPoint> pointLabel = 
    		new CLabel<geography.GeographicPoint>("No point Selected.", null);
    CLabel<String> routeDistLabel = 
    		new CLabel<String>("No route distance yet.", null);
    CLabel<String> routeTimeLabel = 
    		new CLabel<String>("No route time yet.", null);
    manager.setRouteDistLabel(routeDistLabel);
    manager.setRouteTimeLabel(routeTimeLabel);
    manager.setPointLabel(pointLabel);
    manager.setStartLabel(startLabel);
    manager.setStopLabels(stopLabels);
    setupRouteTab(routeTab, fetchedBox, routeDistLabel, routeTimeLabel,
    			  startLabel, stopLabels, 
    			  pointLabel, mapRouteBtn, hideRouteButton,
        		  resetButton, visualizationButton, startButton, 
        		  stopButtons, searchOptions);

        // add tabs to pane, give no option to close
		TabPane tp = new TabPane(routeTab);
    tp.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

    // initialize Services and controllers after map is loaded
    System.out.println("initialize services after map is fully loaded");
    googlemapComponent.addMapReadyListener(() -> {
        GeneralService gs = new GeneralService(googlemapComponent, manager, markerManager);
        RouteService rs = new RouteService(googlemapComponent, manager, markerManager);
        //System.out.println("in map ready : " + this.getClass());
        // initialize controllers
				new RouteController(rs, mapRouteBtn, hideRouteButton, resetButton, startButton, stopButtons, 
									group, searchOptions, visualizationButton,
									startLabel, stopLabels, pointLabel, 
									manager, markerManager);
        new FetchController(gs, rs, txtField, fetchBtn, comBoxSet, displayBtn);
    });

		// add components to border pane
    borderPane.setBottom(fetchedControls);
    borderPane.setCenter(googlemapComponent);
    borderPane.setRight(tp);


		Scene scene = new Scene(borderPane);
	System.out.println("setting css styling now .....");
    scene.getStylesheets().add("html/routing.css");
		primaryStage.setScene(scene);
		primaryStage.show();

			
	}


	@Override
	public void mapInitialized() {

		LatLong center = new LatLong(32.8810, -117.2380);

		// set map options
		MapOptions options = new MapOptions();
		System.out.println("created map options::");
		// this is for map Feature like zoom in and out , street view ..
		options.center(center).mapMarker(false).mapType(MapTypeIdEnum.ROADMAP).mapTypeControl(true)
		       .overviewMapControl(false)
		       .panControl(true)
		       .rotateControl(true)
		       .scaleControl(true)
		       .streetViewControl(true)
		       .zoom(14)
		       .zoomControl(true);

		
		System.out.println("creating map now....");
		map = googlemapComponent.createMap(options);
        setupJSAlerts(googlemapComponent.getWebView());
		System.out.println("mapInitialized done");
			
	}


	// SETTING UP THE VIEW

  private HBox getBottomBox(TextField tf, Button fetchButton) {
    HBox box = new HBox();
    tf.setPrefWidth(FETCH_COMPONENT_WIDTH);
    box.getChildren().add(tf);
    fetchButton.setPrefWidth(FETCH_COMPONENT_WIDTH);
    box.getChildren().add(fetchButton);
    return box;
  }
  // setting fetch tab, button  and a display button
	
  private VBox getFetchBox(Button displayButton, ComboBox<DataSet> cb) {
  
  	VBox v = new VBox();
    HBox h = new HBox();


// implemented by ucsd developer team 
    HBox intersectionControls = new HBox();
//       
    cb.setPrefWidth(FETCH_COMPONENT_WIDTH);
    intersectionControls.getChildren().add(cb);
    displayButton.setPrefWidth(FETCH_COMPONENT_WIDTH);
    intersectionControls.getChildren().add(displayButton);

    h.getChildren().add(v);
    v.getChildren().add(new Label("Choose map file: "));
    v.getChildren().add(intersectionControls);

    //v.setSpacing(MARGIN_VAL);
    return v;
  }

 // setting layout of controls and tab 
  private void setupRouteTab(Tab routeTab, VBox fetchBox,
		  					 Label routeDistLabel, Label routeTimeLabel, 
		  					 Label startLabel, 
		  					 List<CLabel<geography.GeographicPoint>> stopLabels, 
		  					 Label pointLabel, Button showButton, Button hideButton, 
		  					 Button resetButton, Button vButton, 
		  					 Button startButton, List<Button> stopButtons, 
		  					 List<RadioButton> searchOptions) {

	// to set tab layout
	    HBox h = new HBox();
		// inside container 
      VBox v = new VBox();
      h.getChildren().add(v);



      VBox selectLeft = new VBox();


      selectLeft.getChildren().add(startLabel);
      HBox startBox = new HBox();
      startBox.getChildren().add(startLabel);
      startBox.getChildren().add(startButton);
      startBox.setSpacing(20);

      VBox stopBoxes = new VBox();
      for (int i = 0; i < RouteController.MAXSTOPS; i++) {
    	  
    	  HBox stopBox = new HBox();
          stopBox.getChildren().add(stopLabels.get(i));
          stopBox.getChildren().add(stopButtons.get(i));
          stopBox.setSpacing(20);
          
          stopBoxes.getChildren().add(stopBox);
      }

      VBox markerBox = new VBox();
      Label markerLabel = new Label("Selected Marker: ");
      markerBox.getChildren().add(markerLabel);
      markerBox.getChildren().add(pointLabel);
      
      VBox routeInfoBox = new VBox();
      Label routeInfoLabel = new Label("Route Information: ");
      routeInfoBox.getChildren().add(routeInfoLabel);
      routeInfoBox.getChildren().add(routeDistLabel);
      routeInfoBox.getChildren().add(routeTimeLabel);
      

      VBox.setMargin(routeInfoLabel, new Insets(MARGIN_VALUE,MARGIN_VALUE,MARGIN_VALUE,MARGIN_VALUE));
      VBox.setMargin(routeDistLabel, new Insets(MARGIN_VALUE,MARGIN_VALUE,MARGIN_VALUE,MARGIN_VALUE));
      VBox.setMargin(routeTimeLabel, new Insets(MARGIN_VALUE,MARGIN_VALUE,MARGIN_VALUE,MARGIN_VALUE));
      VBox.setMargin(markerLabel, new Insets(MARGIN_VALUE,MARGIN_VALUE,MARGIN_VALUE,MARGIN_VALUE));
      VBox.setMargin(pointLabel, new Insets(0,MARGIN_VALUE,MARGIN_VALUE,MARGIN_VALUE));
      VBox.setMargin(fetchBox, new Insets(0,0,MARGIN_VALUE*2,0));

      HBox showHideBox = new HBox();
      showHideBox.getChildren().add(showButton);
      showHideBox.getChildren().add(hideButton);
      showHideBox.setSpacing(2*MARGIN_VALUE);

      v.getChildren().add(fetchBox);
      v.getChildren().add(new Label("Start Position: "));
      v.getChildren().add(startBox);
      v.getChildren().add(new Label("Stops: "));
      v.getChildren().add(stopBoxes);
      v.getChildren().add(showHideBox);
      for (RadioButton rb : searchOptions) {
      	v.getChildren().add(rb);
      }
      v.getChildren().add(vButton);
      VBox.setMargin(showHideBox, new Insets(MARGIN_VALUE,MARGIN_VALUE,MARGIN_VALUE,MARGIN_VALUE));
      VBox.setMargin(vButton, new Insets(MARGIN_VALUE,MARGIN_VALUE,MARGIN_VALUE,MARGIN_VALUE));
      vButton.setDisable(true);
      v.getChildren().add(markerBox);
      v.getChildren().add(routeInfoBox);
      //v.getChildren().add(resetButton);


      routeTab.setContent(h);


  }

  private void setupJSAlerts(WebView webView) {
    webView.getEngine().setOnAlert( e -> {
        Stage popup = new Stage();
        popup.initOwner(primaryStage);
        popup.initStyle(StageStyle.UTILITY);
        popup.initModality(Modality.WINDOW_MODAL);

        StackPane content = new StackPane();
        content.getChildren().setAll(
          new Label(e.getData())
        );
        content.setPrefSize(200, 100);

        popup.setScene(new Scene(content));
        popup.showAndWait();
    });
  }

	private LinkedList<RadioButton> setupToggle(ToggleGroup group) {

		  RadioButton rbB = new RadioButton("BFS");
		  rbB.setUserData("BFS");

	  // Use Dijkstra as default data structure for map //
	  RadioButton rbD = new RadioButton("Dijkstra");
	  rbD.setUserData("Dijkstra");
	  rbD.setSelected(true);

	


	  rbB.setToggleGroup(group);
	  rbD.setToggleGroup(group);
	

	  return new LinkedList<RadioButton>(Arrays.asList(rbB, rbD) );
	}

// @author ucsd
// here all the alert and dialog box will show up

	public void showLoadStage(Stage loadStage, String text) {
		loadStage.initModality(Modality.APPLICATION_MODAL);
		loadStage.initOwner(primaryStage);
	  VBox loadVBox = new VBox(20);
	  loadVBox.setAlignment(Pos.CENTER);
	  Text tNode = new Text(text);
	  tNode.setFont(new Font(16));
	  loadVBox.getChildren().add(new HBox());
	  loadVBox.getChildren().add(tNode);
	  loadVBox.getChildren().add(new HBox());
	  Scene loadScene = new Scene(loadVBox, 300, 200);
	  loadStage.setScene(loadScene);
	  loadStage.show();
	}

	public static void showInfoAlert(String header, String content) {
	  Alert alert = getInfoAlert(header, content);
		alert.showAndWait();
	}

	public static Alert getInfoAlert(String header, String content) {
	  Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText(header);
		alert.setContentText(content);
		return alert;
	}

	public static void showErrorAlert(String header, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("File Name Error");
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}


}
