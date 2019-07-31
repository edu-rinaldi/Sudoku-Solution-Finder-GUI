package com.eduardorinaldi.SudokuResolver;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineJoin;


public class Tile extends TextField
{
    private int row, column;

    public Tile(String value, int size, int row, int column)
    {
        this.row = row;
        this.column = column;
        this.setPrefSize(size, size);
        this.setEditable(false);
        this.setBorder(1, 1, 1, 1);
        setAlignment(Pos.CENTER);
    }

    public void setBorder(int v, int v1, int v2, int v3)
    {
        this.setBorder(new Border(new BorderStroke(Color.BLACK,  BorderStrokeStyle.SOLID, null, new BorderWidths(v,v1,v2,v3))));
    }

    public int getColumn() { return column; }
    public int getRow() { return row; }
}
