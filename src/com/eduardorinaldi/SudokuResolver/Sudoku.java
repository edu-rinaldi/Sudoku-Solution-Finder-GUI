package com.eduardorinaldi.SudokuResolver;

import javafx.geometry.Insets;
import javafx.util.Pair;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

public class Sudoku extends TileMap
{

    public static final int ROWS = 9, COLUMNS = 9, TILE_SIZE = 50, TILE_MAP_PADDING = 10,
            ROWS_SQRT = (int) Math.sqrt(ROWS), COLUMNS_SQRT = (int) Math.sqrt(COLUMNS);
    private int inserted;
    private Map<Integer, Set<Integer>> rows;
    private Map<Integer, Set<Integer>> columns;
    private Map<Pair<Integer,Integer>, Set<Integer>> blocks;

    public Sudoku()
    {
        super(ROWS, COLUMNS, TILE_SIZE);
        createSudokuGrid();
    }

    private void initMaps()
    {
        rows = new HashMap<>();
        IntStream.range(0, ROWS).forEachOrdered(i -> rows.put(i, new HashSet<>()));

        columns = new HashMap<>();
        IntStream.range(0, COLUMNS).forEachOrdered(i -> columns.put(i, new HashSet<>()));

        blocks = new HashMap<>();
        for(int i=0; i<ROWS_SQRT; i++)
            for(int j=0; j<COLUMNS_SQRT; j++)
                blocks.put(new Pair<>(i, j), new HashSet<>());

        for(int i=0; i<ROWS; i++)
            for(int j=0; j<COLUMNS; j++)
            {
                int tileValue = getTileValue(i, j);
                if (tileValue!=0) inserted++;
                rows.get(i).add(tileValue);
                columns.get(j).add(tileValue);
                blocks.get(getBlock(i, j)).add(tileValue);
            }
    }

    private Pair<Integer, Integer> getBlock(int i, int j) { return new Pair<>(i/ROWS_SQRT, j/COLUMNS_SQRT); }

    private void createSudokuGrid()
    {
        inserted = 0;
        initMaps();
        setPadding(new Insets(TILE_MAP_PADDING));

        for(int i=0; i<getRows(); i++)
            for (int j = 0; j < getColumns(); j++)
            {
                int v = 1, v1 = 1, v2 = 1, v3 = 1;
                if(j%3==2 && j<COLUMNS) v1 = 3;
                if(i%3==2 && i<ROWS) v2 = 3;
                Tile tile = getTile(i, j);
                tile.setBorder(v,v1,v2,v3);

                tile.textProperty().addListener((observableValue, oldval, newval) -> {
                    int tileRow = tile.getRow();
                    int tileColumn = tile.getColumn();
                    int value = newval.matches("[1-9]") ? Integer.parseInt(newval) : 0;

                    if(newval.equals("")) return;

                    if (oldval.matches("[1-9]"))
                    {
                        int oldvalue = Integer.parseInt(oldval);
                        rows.get(tileRow).remove(oldvalue);
                        columns.get(tileColumn).remove(oldvalue);
                        blocks.get(getBlock(tileRow, tileColumn)).remove(oldvalue);
                    }

                    if (newval.matches("[1-9]") && isValidPosition(value, tileRow, tileColumn))
                    {
                        int newvalue = Integer.parseInt(newval);
                        rows.get(tileRow).add(newvalue);
                        columns.get(tileColumn).add(newvalue);
                        blocks.get(getBlock(tileRow, tileColumn)).add(newvalue);
                    }
                    else tile.setText("");

                });
            }
    }

    public int getTileValue(int i, int j)
    {
        try { return Integer.parseInt(getTile(i, j).getText()); }
        catch (NumberFormatException e) { return 0; }
    }

    private boolean isValidPosition(int n, int i, int j)
    {
        if (n == 0) return false;
        if (rows.get(i).contains(n)) return false;
        if (columns.get(j).contains(n)) return false;
        if (blocks.get(getBlock(i, j)).contains(n)) return false;
        return true;
    }

    private Pair<Integer, Integer> getIJ(int i, int j)
    {
        return j < ROWS-1 ? new Pair<>(i, j+1) : new Pair<>(i+1, 0);
    }

    public boolean findSolution(int inserted, int i, int j)
    {
        if (i == 9) return true;
        if (getTileValue(i, j) != 0)
        {
            Pair<Integer, Integer> ij = getIJ(i,j);
            int newI = ij.getKey(); int newJ = ij.getValue();
            boolean sol = findSolution(inserted,newI, newJ);
            if (sol) return sol;
        }
        else
        {
            for(int n=1; n<=COLUMNS; n++)
            {
                if (isValidPosition(n, i, j))
                {
                    String copy = tileGrid[i][j].getText();
                    setValue(String.valueOf(n), i, j);
                    Pair<Integer, Integer> ij = getIJ(i,j);
                    int newI = ij.getKey(); int newJ = ij.getValue();
                    boolean sol = findSolution(inserted+1, newI, newJ);
                    if(sol) return sol;
                    removeValue(i,j);
                    setValue(copy, i, j);
                }
            }

        }
        return false;
    }

    public void setValue(String value, int tileRow, int tileColumn)
    {
        tileGrid[tileRow][tileColumn].setText(value);
        int val = getTileValue(tileRow, tileColumn);
        rows.get(tileRow).add(val);
        columns.get(tileColumn).add(val);
        blocks.get(getBlock(tileRow,tileColumn)).add(val);
    }

    public void removeValue(int tileRow, int tileColumn)
    {
        int n = getTileValue(tileRow, tileColumn);
        tileGrid[tileRow][tileColumn].setText("");
        rows.get(tileRow).remove(n);
        columns.get(tileColumn).remove(n);
        blocks.get(getBlock(tileRow,tileColumn)).remove(n);
    }

    public int getInserted() { return inserted; }
}
