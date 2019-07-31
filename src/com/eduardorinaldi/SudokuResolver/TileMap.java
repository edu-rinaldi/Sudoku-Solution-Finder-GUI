package com.eduardorinaldi.SudokuResolver;

import javafx.scene.layout.GridPane;

import java.util.stream.IntStream;

public class TileMap extends GridPane
{

    private int rows;
    private int columns;
    protected Tile[][] tileGrid;

    public TileMap(int rows, int columns, int tileSize)
    {
        this.rows = rows;
        this.columns = columns;
        this.tileGrid = new Tile[rows][columns];
        for(int i=0; i<rows; i++)
            for(int j=0; j<columns; j++)
            {
                Tile tile = new Tile("", tileSize, i, j);
                this.tileGrid[i][j] = tile;
                this.add(tile, j, i);
            }
    }

    public Tile getTile(int row, int column) {return tileGrid[row][column]; }

    public boolean isEditable()
    {
        return IntStream.range(0, rows)
                .noneMatch(i -> IntStream.range(0, columns)
                        .anyMatch(j -> !tileGrid[i][j].isEditable()));
    }

    public void setEditable(boolean b)
    {
        for(int i = 0; i< rows; i++)
            for(int j = 0; j< columns; j++)
                tileGrid[i][j].setEditable(b);
    }

    public int getRows() {return rows;}
    public int getColumns() { return columns; }

}
