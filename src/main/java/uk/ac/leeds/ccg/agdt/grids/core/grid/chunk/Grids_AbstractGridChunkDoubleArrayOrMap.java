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
package uk.ac.leeds.ccg.agdt.grids.core.grid.chunk;

import uk.ac.leeds.ccg.agdt.grids.core.Grids_2D_ID_int;
import uk.ac.leeds.ccg.agdt.grids.core.grid.Grids_GridDouble;

/**
 *
*
 * @author Andy Turner
 * @version 1.0.0
 */
public abstract class Grids_AbstractGridChunkDoubleArrayOrMap 
        extends Grids_AbstractGridChunkDouble {
    
    protected Grids_AbstractGridChunkDoubleArrayOrMap() {
    }

    protected Grids_AbstractGridChunkDoubleArrayOrMap(Grids_GridDouble g, 
            Grids_2D_ID_int chunkID) {
        super(g, chunkID);
    }

    /**
     * Returns a Grids_GridChunkDoubleArrayOrMapIterator for iterating over the
     * cells in this.
     *
     * @return
     */
    @Override
    public abstract Grids_GridChunkDoubleArrayOrMapIterator iterator();
    
}