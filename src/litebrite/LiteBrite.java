/**
 * @author Nghi Nguyen
 */
package litebrite;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author Nghi Nguyen
 */
public class LiteBrite extends Application {
	/**
	 * fields
	 */
	private int cellWidth;// holds width value of each cell
	private int numberOfRow;//holds total number of rows
	private int numberOfColumns;//holds total number of columns
	/**
	 * global variables
	 */
	final static ColorPicker colorPicker = new ColorPicker(); //create a color picker
    private GridPane grid;//holds the grid
	private Paint color =colorPicker.getValue();//holds value of color
    
	/**
	 * no-arg constructor
	 */
	public LiteBrite(){
		/**
		 * 50 rows, 50 columns, 12 px width (and height) of each cell
		 */
		this(50, 50, 12);
	}
	/**
	 * 
	 * @param numberOfRow - total number of rows
	 * @param numberOfColumns - total number of columns
	 * @param cellWidth - width value of each cell
	 */
	public LiteBrite(int numberOfRow, int numberOfColumns, int cellWidth){
		this.numberOfRow = numberOfRow;
		this.numberOfColumns = numberOfColumns;
		this.cellWidth = cellWidth;
	}
	/**
	 * set new color
	 * @param color - value of the color
	 */
    public void setColor(Paint color){
    	this.color = color;
    }
    /**
     * return current color
     * @return color
     */
    public Paint getColor(){
    	return color;
    }
    /**
     * set cell' width
     * @param cellWidth - width value of each cell
     */
    public void setCellWidth(int cellWidth){
    	this.cellWidth=cellWidth;
    }
    /**
     * get cell' width
     * @return cellwidth - width value of each cell
     */
    public int getCellWidth(){
    	return cellWidth;
    }
    /**
     * set default color for color picker
     * @param color
     */
    public void setColorPickerColor(Color color){
    	colorPicker.setValue(color);
    }
    @Override
    public void start(final Stage stage) throws Exception {
        
    	HBox rootScene = new HBox();// the root scene
    	rootScene.getStyleClass().add("scene-background");
    	
        
        grid = drawGridTable();// draw the table with data in the constructor
        VBox rightPane = new VBox(10); // VBox with spacing 10, contains colorpicker and reset button
   
        this.setColorPickerColor(Color.WHITE);// initialize default color for the color picker
        colorPicker.getStyleClass().add("button");// associate colorpicker with css .button
       
        
        //when use choose another color from color picker
        colorPicker.setOnAction(e ->{
        	//get the value of chosen color and set that color to color
        	this.setColor(colorPicker.getValue());
        	
        });
       
        
       
        rightPane.getStyleClass().add("right-pane");// associate rightPane with css .right-pane
        
        HBox resetButtonPane = new HBox(5); //HBox with spacing 5, inside rightPane and contain reset button
        Button resetButton = new Button("Reset");// reset button
        

        resetButton.getStyleClass().add("button");// associate colorpicker with css .button
        resetButton.setOnAction(e ->{
        	grid = resetGridPane(grid);
        	this.setColorPickerColor(Color.WHITE);//reset the colorpicker
        });
        resetButtonPane.getChildren().add(resetButton);// add resetButton on resetButtonPane
     
        resetButtonPane.setAlignment(Pos.CENTER);// align resetButton at the center of resetButtonPane
        
        
        rightPane.getChildren().addAll(colorPicker, resetButtonPane);// add colorpicker and resetButtonPane on rightPane
        rootScene.getChildren().addAll(grid, rightPane);// add grid and rightPane to rootSceen

        Scene scene = new Scene(rootScene);//create a scene using rootScene
        
        scene.getStylesheets().add(LiteBrite.class.getResource("resources/game.css").toExternalForm());//add the css file
        stage.setTitle("Lite Brite");//set title
        stage.setScene(scene);//set scene
        stage.show();//show the stage
    }

    

    public static void main(String[] arguments) {
        Application.launch(arguments);
        
    }
     public GridPane drawGridTable(){
    
    	 GridPane grid = new GridPane(); 
    	 
         grid.getStyleClass().add("game-grid");// associate grid with css .game-grid
         
         //add colums on the grid using numberOfColumns
         for(int i = 0; i < this.numberOfColumns; i++) {
             ColumnConstraints column = new ColumnConstraints(getCellWidth());
             grid.getColumnConstraints().add(column);
         }
         //add rows on the grid using numberOfRow
         for(int i = 0; i < numberOfRow; i++) {
             RowConstraints row = new RowConstraints(getCellWidth());
             grid.getRowConstraints().add(row);
         }
         // add responsive cell onto the grid
         for (int i = 0; i < numberOfRow; i++) {
             for (int j = 0; j < numberOfColumns; j++) {
            	//create a pane on each cell
                 Pane pane = new Pane();
                 //when the cell is clicked, add a rectangle with chosen color onto the cell
                 pane.setOnMouseReleased(e -> {
                	
                     pane.getChildren().add(Anims.getAtoms(color));
                     
                 });
                 
                 
                 pane.getStyleClass().add("game-grid-cell"); // associate each pane with css .game-grid-cell
                 if (i == 0) {
                     pane.getStyleClass().add("first-column");
                 }
                 if (j == 0) {
                     pane.getStyleClass().add("first-row");
                 }
                 //add cell to the grid
                 grid.add(pane, i, j);
               
             }
         }
         return grid;
     }
     /**
      * reset whole colored grid, used when reset button is clicked
      * @param grid - current playing grid
      * @return grid - grid with no color filled
      */
     public GridPane resetGridPane(GridPane grid){
    	 for (int i = 0; i < numberOfRow; i++) {
             for (int j = 0; j < numberOfColumns; j++) {
            	
                 Pane pane = new Pane();
                 pane.setOnMouseReleased(e -> {
                	
                     pane.getChildren().add(Anims.getAtoms(color));
                     
                 });
                 
                 pane.getStyleClass().add("game-grid-cell");
                 if (i == 0) {
                     pane.getStyleClass().add("first-column");
                 }
                 if (j == 0) {
                     pane.getStyleClass().add("first-row");
                 }
                 grid.add(pane, i, j);
               
             }
         }
    	 return grid;
     }
     
     public static class Anims {
    	 	/**
    	 	 * fill the chosen cell with chosen color
    	 	 * @param color - value of the chosen color
    	 	 * @return rectangle - the node will be put on the chosen cell of the grid with the chosen color from color picker
    	 	 */
    	    public static Node getAtoms(Paint color) {
    	        //TODO: Add code to create a colored 
    	    	LiteBrite liteBrite = new LiteBrite();
    	    	/*
    	    	 * create a rectangle start from (0,0) and has width and heigh of cellWidth -1 (-1 because of the border around the cell, which has insets of 1)
    	    	 */
    	    	Rectangle rectangle = new Rectangle(0,0,liteBrite.getCellWidth()-1,liteBrite.getCellWidth()-1);
    	    	rectangle.setFill(color);//fill the rectangle with chosen color
    	    	rectangle.setVisible(true);
    	        return rectangle;
    	    }
    	}
}

