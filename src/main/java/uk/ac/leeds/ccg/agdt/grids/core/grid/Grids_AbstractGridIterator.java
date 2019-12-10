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
package uk.ac.leeds.ccg.agdt.grids.core.grid;

import java.util.Iterator;
import java.util.NoSuchElementException;
import uk.ac.leeds.ccg.agdt.grids.core.Grids_2D_ID_int;
import uk.ac.leeds.ccg.agdt.grids.core.grid.chunk.Grids_AbstractGridChunk;
import uk.ac.leeds.ccg.agdt.grids.utilities.Grids_AbstractIterator;

/**
 * For iterating through the values in a Grid. The values are returned chunk by
 * chunk in row major order. The values within each chunk are also returned in
 * row major order.
*
 * @author Andy Turner
 * @version 1.0.0
 */
public abstract class Grids_AbstractGridIterator
        extends Grids_AbstractIterator {

    protected Grids_AbstractGrid Grid;
    protected Grids_AbstractGridChunk Chunk;
    protected Grids_2D_ID_int ChunkID;
//    protected Iterator<Grids_AbstractGridChunk> GridIterator;
    protected Iterator<Grids_2D_ID_int> GridIterator;
    protected Grids_AbstractIterator ChunkIterator;

    protected Grids_AbstractGridIterator() {
    }

    public Grids_AbstractGridIterator(Grids_AbstractGrid grid) {
        super(grid.env);
        Grid = grid;
    }

    protected abstract void initChunkIterator();

    public abstract Grids_AbstractGrid getGrid();

    public Iterator<Grids_2D_ID_int> getGridIterator() {
        return GridIterator;
    }

    public Grids_AbstractIterator getChunkIterator() {
        return ChunkIterator;
    }

    /**
     * ChunkIterator
     *
     * @param chunk
     * @return
     */
    public abstract Grids_AbstractIterator getChunkIterator(
            Grids_AbstractGridChunk chunk);

    /**
     * Returns <tt>true</tt> if the iteration has more elements. (In other
     * words, returns <tt>true</tt> if <tt>next</tt> would return an element
     * rather than throwing an exception.)
     *
     * @return <tt>true</tt> if the iterator has more elements.
     */
    @Override
    public boolean hasNext() {
        if (ChunkIterator.hasNext()) {
            return true;
        } else {
            if (GridIterator.hasNext()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration.
     * @exception NoSuchElementException iteration has no more elements.
     */
    @Override
    public Object next() {
        if (ChunkIterator.hasNext()) {
            return ChunkIterator.next();
        } else {
            if (GridIterator.hasNext()) {
                env.removeFromNotToCache(Grid, ChunkID);
                ChunkID = (Grids_2D_ID_int) GridIterator.next();
                Chunk = (Grids_AbstractGridChunk) Grid.chunkIDChunkMap.get(ChunkID);
                if (Chunk == null) {
                    Grid.loadIntoCacheChunk(ChunkID);
                }
                Chunk = (Grids_AbstractGridChunk) Grid.chunkIDChunkMap.get(ChunkID);
                env.addToNotToCache(Grid, ChunkID);
                ChunkIterator = getChunkIterator(Chunk);
                if (ChunkIterator.hasNext()) {
                    return ChunkIterator.next();
                }
            }
        }
        throw new NoSuchElementException();
    }

    /**
     *
     * @return Chunk.ChunkID
     */
    public Grids_2D_ID_int getChunkID() {
        return ChunkID;
    }

    /**
     * throw new UnsupportedOperationException();
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}