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
package uk.ac.leeds.ccg.agdt.grids.core.chunk.d;

import uk.ac.leeds.ccg.agdt.grids.core.Grids_2D_ID_int;
import uk.ac.leeds.ccg.agdt.grids.core.grid.d.Grids_GridDouble;

/**
 * A factory for constructing Grids_ChunkDoubleMap instances.
*
 * @author Andy Turner
 * @version 1.0.0
 */
public class Grids_ChunkFactoryDoubleMap
        extends Grids_ChunkFactoryDouble {

    public Grids_ChunkFactoryDoubleMap() {
    }

    @Override
    public Grids_ChunkDoubleMap create(
            Grids_GridDouble g,
            Grids_2D_ID_int chunkID) {
        return new Grids_ChunkDoubleMap(g, chunkID);
    }

    @Override
    public Grids_ChunkDoubleMap create(
            Grids_ChunkDouble chunk,
            Grids_2D_ID_int chunkID) {
        return new Grids_ChunkDoubleMap(chunk, chunkID, 
                chunk.getGrid().getNoDataValue());
    }

    public Grids_ChunkDoubleMap create(
            Grids_ChunkDouble chunk,
            Grids_2D_ID_int chunkID,
            double defaultValue) {
        return new Grids_ChunkDoubleMap(chunk, chunkID, defaultValue);
    }

}