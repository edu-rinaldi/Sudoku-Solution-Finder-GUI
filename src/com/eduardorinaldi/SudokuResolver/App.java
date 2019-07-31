package com.eduardorinaldi.SudokuResolver;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application
{

    public static final String VERSION = "v0.1";
    public static final String APP_NAME = "Sudoku Resolver"+" "+VERSION;

    @Override
    public void start(Stage stage) throws Exception
    {
        stage.setTitle(APP_NAME);
        Sudoku sudoku = new Sudoku();
        Scene mainScene = new Scene(sudoku);
        mainScene.setOnKeyPressed(e->{
            if (e.isControlDown()) sudoku.setEditable(!sudoku.isEditable());
            if (e.isAltDown()) sudoku.findSolution(sudoku.getInserted(),0,0);
        });
        stage.setScene(mainScene);
        stage.show();
    }


}
