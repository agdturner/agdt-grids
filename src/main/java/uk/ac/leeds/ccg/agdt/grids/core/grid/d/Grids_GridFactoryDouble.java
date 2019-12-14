/*
 * Copyright 2019 Andy Turner, University of Leeds.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.leeds.ccg.agdt.grids.core.grid.d;

import uk.ac.leeds.ccg.agdt.grids.core.chunk.d.Grids_ChunkFactoryDouble;
import java.io.IOException;
import java.io.ObjectInputStream;
import uk.ac.leeds.ccg.agdt.generic.io.Generic_Path;
import uk.ac.leeds.ccg.agdt.grids.core.Grids_Dimensions;
import uk.ac.leeds.ccg.agdt.grids.core.Grids_Environment;
import uk.ac.leeds.ccg.agdt.grids.core.chunk.d.Grids_ChunkFactoryDoubleSinglet;
import uk.ac.leeds.ccg.agdt.grids.core.grid.Grids_Grid;
import uk.ac.leeds.ccg.agdt.grids.core.grid.Grids_GridFactory;
import uk.ac.leeds.ccg.agdt.grids.core.stats.Grids_StatsDouble;
import uk.ac.leeds.ccg.agdt.grids.core.stats.Grids_StatsNotUpdatedDouble;

/**
 * A factory for constructing Grids_GridDouble instances.
*
 * @author Andy Turner
 * @version 1.0.0
 */
public class Grids_GridFactoryDouble extends Grids_GridFactory {

    private static final long serialVersionUID = 1L;

    /**
     * The NoDataValue for creating chunks.
     */
    protected double NoDataValue;

    public Grids_ChunkFactoryDoubleSinglet GridChunkDoubleFactory;
//    public Grids_GridChunkDoubleMapFactory ChunkDoubleMapFactory;
//    public Grids_GridChunkDoubleArrayFactory ChunkDoubleArrayFactory;
    public Grids_ChunkFactoryDouble DefaultGridChunkDoubleFactory;

    public Grids_StatsDouble Stats;

    protected Grids_GridFactoryDouble() {
    }

    /**
     * Creates a new Grids_GridDoubleFactory.
     *
     * @param ge
     * @param gridChunkDoubleFactory
     * @param defaultGridChunkDoubleFactory
     * @param chunkNRows The number of rows chunks have by default.
     * @param chunkNCols The number of columns chunks have by default.
     */
    public Grids_GridFactoryDouble(Grids_Environment ge,
            Grids_ChunkFactoryDoubleSinglet gridChunkDoubleFactory,
            Grids_ChunkFactoryDouble defaultGridChunkDoubleFactory,
            int chunkNRows, int chunkNCols) {
        super(ge, chunkNRows, chunkNCols, null);
        GridChunkDoubleFactory = gridChunkDoubleFactory;
        DefaultGridChunkDoubleFactory = defaultGridChunkDoubleFactory;
        Stats = new Grids_StatsNotUpdatedDouble(ge);
        NoDataValue = -Double.MAX_VALUE;
    }

    /**
     * Creates a new Grids_GridDoubleFactory.
     *
     * @param ge
     * @param gridChunkDoubleFactory
     * @param defaultGridChunkDoubleFactory
     * @param noDataValue
     * @param chunkNRows The number of rows chunks have by default.
     * @param chunkNCols The number of columns chunks have by default.
     * @param dimensions
     * @param stats
     */
    public Grids_GridFactoryDouble(Grids_Environment ge,
            Grids_ChunkFactoryDoubleSinglet gridChunkDoubleFactory,
            Grids_ChunkFactoryDouble defaultGridChunkDoubleFactory,
            double noDataValue, int chunkNRows, int chunkNCols,
            Grids_Dimensions dimensions, Grids_StatsDouble stats) {
        super(ge, chunkNRows, chunkNCols, dimensions);
        GridChunkDoubleFactory = gridChunkDoubleFactory;
        DefaultGridChunkDoubleFactory = defaultGridChunkDoubleFactory;
        Stats = stats;
        NoDataValue = noDataValue;
    }

    /**
     * Set DefaultGridChunkDoubleFactory to cf.
     *
     * @param cf
     */
    public void setDefaultChunkFactory(
            Grids_ChunkFactoryDouble cf) {
        DefaultGridChunkDoubleFactory = cf;
    }

    /**
     * Returns NoDataValue.
     *
     * @return
     */
    public double getNoDataValue() {
        return NoDataValue;
    }

    /**
     * Sets NoDataValue to noDataValue.
     *
     * @param noDataValue
     */
    public void setNoDataValue(double noDataValue) {
        NoDataValue = noDataValue;
    }

    /////////////////////////
    // Create from scratch //
    /////////////////////////
    /**
     * Returns A new Grids_GridDouble with all values as NoDataValues.
     *
     * @param dir The Directory to be used for storing grid.
     * @param nRows The number of rows in the grid.
     * @param nCols The number of columns in the grid.
     * @param dimensions The xmin, ymin, xmax, ymax, cellsize.
     * @return
     */
    @Override
    public Grids_GridDouble create(Generic_Path dir, long nRows, long nCols,
            Grids_Dimensions dimensions) throws IOException, ClassNotFoundException {
        return create(new Grids_StatsNotUpdatedDouble(env), dir,
                GridChunkDoubleFactory, nRows, nCols, dimensions);
    }

    /**
     * @param stats The type of Grids_StatsDouble to accompany the returned
 grid.
     * @param dir The Directory to be used for storing grid.
     * @param cf The preferred Grids_ChunkFactoryDouble for creating
 chunks that the constructed Grid is to be made of.
     * @param nRows The number of rows in the grid.
     * @param nCols The number of columns in the grid.
     * @param dimensions The xmin, ymin, xmax, ymax, cellsize.
     * @return A new Grids_GridDouble grid with all values as NoDataValues.
     */
    public Grids_GridDouble create(Grids_StatsDouble stats, Generic_Path dir,
            Grids_ChunkFactoryDouble cf, long nRows, long nCols,
            Grids_Dimensions dimensions) throws IOException, 
            ClassNotFoundException {
        return new Grids_GridDouble(getStats(stats), dir, cf, ChunkNRows,
                ChunkNCols, nRows, nCols, dimensions, NoDataValue, env);
    }

    //////////////////////////////////////////////////////
    // Create from an existing Grids_AbstractGridNumber //
    //////////////////////////////////////////////////////
    /**
     * @param dir The Directory to be used for storing the grid.
     * @param g The Grids_AbstractGridNumber from which values are used.
     * @param startRow The topmost row index of g.
     * @param startCol The leftmost column index of g.
     * @param endRow The bottom row index of g.
     * @param endCol The rightmost column index of g.
     * @return A new Grids_GridDouble with all values taken from g.
     */
    @Override
    public Grids_GridDouble create(Generic_Path dir, Grids_Grid g,
            long startRow, long startCol, long endRow, long endCol) throws IOException, ClassNotFoundException {
        return create(new Grids_StatsNotUpdatedDouble(env), dir, g,
                DefaultGridChunkDoubleFactory, startRow, startCol, endRow,
                endCol);
    }

    /**
     * @param stats The type of Grids_StatsDouble to accompany the returned
 grid.
     * @param dir The directory to be used for storing the grid.
     * @param cf The preferred Grids_ChunkFactoryDouble for creating
 chunks that the constructed Grid is to be made of.
     * @param g The Grids_AbstractGridNumber from which grid values are used.
     * @param startRow The topmost row index of g.
     * @param startCol The leftmost column index of g.
     * @param endRow The bottom row index of g.
     * @param endCol The rightmost column index of g.
     * @return A new Grids_GridDouble with all values taken from g.
     */
    public Grids_GridDouble create(Grids_StatsDouble stats, Generic_Path dir,
            Grids_Grid g, Grids_ChunkFactoryDouble cf,
            long startRow, long startCol, long endRow, long endCol) 
            throws IOException, ClassNotFoundException {
        return new Grids_GridDouble(getStats(stats), dir, g, cf, ChunkNRows,
                ChunkNCols, startRow, startCol, endRow, endCol, NoDataValue);
    }

    ////////////////////////
    // Create from a File //
    ////////////////////////
    /**
     * @param dir The Directory to be used for storing the grid.
     * @param gridFile Either a directory, or a formatted File with a specific
     * extension containing the data and information about the grid to be
     * constructed.
     * @param startRow The topmost row index of the grid stored as gridFile.
     * @param startCol The leftmost column index of the grid stored as gridFile.
     * @param endRow The bottom row index of the grid stored as gridFile.
     * @param endCol The rightmost column index of the grid stored as gridFile.
     * @return A new Grids_GridDouble with values obtained from gridFile.
     */
    @Override
    public Grids_GridDouble create(Generic_Path dir, Generic_Path gridFile, long startRow,
            long startCol, long endRow, long endCol)throws IOException, ClassNotFoundException {
        return create(new Grids_StatsNotUpdatedDouble(env), dir,
                gridFile, DefaultGridChunkDoubleFactory, startRow, startCol,
                endRow, endCol);
    }

    /**
     * @param stats The type of Grids_StatsDouble to accompany the returned
 grid.
     * @param dir The directory to be used for storing the grid.
     * @param gridFile Either a directory, or a formatted File with a specific
     * extension containing the data and information about the grid to be
     * constructed.
     * @param cf The preferred factory for creating chunks that the constructed
     * Grid is to be made of.
     * @param startRow The topmost row index of the grid stored as gridFile.
     * @param startCol The leftmost column index of the grid stored as gridFile.
     * @param endRow The bottom row index of the grid stored as gridFile.
     * @param endCol The rightmost column index of the grid stored as gridFile.
     * @return A new Grids_GridDouble with values obtained from gridFile.
     */
    public Grids_GridDouble create(Grids_StatsDouble stats, Generic_Path dir,
            Generic_Path gridFile, Grids_ChunkFactoryDouble cf,
            long startRow, long startCol, long endRow, long endCol) 
            throws IOException, ClassNotFoundException {
        return new Grids_GridDouble(getStats(stats), dir, gridFile, cf,
                ChunkNRows, ChunkNCols, startRow, startCol, endRow, endCol,
                NoDataValue, env);
    }

    /**
     * @param dir The directory to be used for storing the grid.
     * @param gridFile Either a directory, or a formatted File with a specific
     * extension containing the data and information about the grid to be
     * returned.
     * @return A new Grids_GridDouble with values obtained from gridFile.
     */
    @Override
    public Grids_GridDouble create(Generic_Path dir, Generic_Path gridFile) throws IOException, ClassNotFoundException {
        return new Grids_GridDouble(env, dir, gridFile);
    }

    /////////////////////////
    // Create from a cache //
    /////////////////////////
    /**
     * @param dir The Directory to be used for storing the grid.
     * @param gridFile A file containing the data to be used in construction.
     * @param ois The ObjectInputStream to construct from.
     * @return A new Grids_GridDouble with values obtained from gridFile.
     */
    public @Override
    Grids_GridDouble create(Generic_Path dir, Generic_Path gridFile, ObjectInputStream ois) throws IOException, ClassNotFoundException {
        return new Grids_GridDouble(dir, gridFile, ois, env);
    }

    /**
     * @param stats
     * @return A new Grids_StatsDouble of the same type for use.
     */
    private Grids_StatsDouble getStats(Grids_StatsDouble stats) {
        if (stats instanceof Grids_StatsNotUpdatedDouble) {
            return new Grids_StatsNotUpdatedDouble(env);
        } else {
            return new Grids_StatsDouble(env);
        }
    }
}