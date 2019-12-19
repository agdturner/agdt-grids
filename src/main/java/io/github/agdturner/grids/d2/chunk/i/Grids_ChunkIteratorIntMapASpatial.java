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
package io.github.agdturner.grids.d2.chunk.i;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import io.github.agdturner.grids.core.Grids_2D_ID_int;
import io.github.agdturner.grids.d2.chunk.i.Grids_ChunkIntMap.OffsetBitSet;
import io.github.agdturner.grids.d2.chunk.Grids_ChunkNumberMapASpatialIterator;

/**
 * For iterating through the values in a Grids_GridChunkIntMap instance. The
 * values are returned in no particular spatial order.
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class Grids_ChunkIteratorIntMapASpatial
        extends Grids_ChunkNumberMapASpatialIterator {

    private static final long serialVersionUID = 1L;

    protected int NumberOfDefaultValues;
    protected int NumberOfNoDataValues;

    protected int DefaultValue;
    protected Grids_ChunkIntMap.GridChunkIntMapData Data;
    protected TreeMap<Integer, OffsetBitSet> DataMapBitSet;
    protected Iterator<Integer> DataMapBitSetIte;
    protected int DataMapBitSetValue;
    protected TreeMap<Integer, HashSet<Grids_2D_ID_int>> DataMapHashSet;
    protected Iterator<Integer> DataMapHashSetIte;
    protected int DataMapHashSetValue;

    public Grids_ChunkIteratorIntMapASpatial(
            Grids_ChunkIntMap chunk) {
        super(chunk);
        Data = chunk.getData();
        DataMapBitSet = Data.DataMapBitSet;
        DataMapHashSet = Data.DataMapHashSet;
        DataMapBitSetNumberOfValues = 0;
        DataMapBitSetIte = DataMapBitSet.keySet().iterator();
        if (DataMapBitSetIte.hasNext()) {
            hasNext = true;
            DataMapBitSetValue = DataMapBitSetIte.next();
            DataMapBitSetNumberOfValues += DataMapBitSet.get(DataMapBitSetValue).bitSet.cardinality();
        }
        NumberOfNoDataValues -= DataMapBitSetNumberOfValues;
        DataMapBitSetIte = DataMapBitSet.keySet().iterator();
        DataMapBitSetIndex = 0;
        DataMapHashSetNumberOfValues = 0;
        DataMapHashSetIte = DataMapHashSet.keySet().iterator();
        if (DataMapHashSetIte.hasNext()) {
            hasNext = true;
            DataMapHashSetValue = DataMapHashSetIte.next();
            DataMapHashSetNumberOfValues += DataMapHashSet.get(DataMapHashSetValue).size();
        }
        NumberOfNoDataValues -= DataMapHashSetNumberOfValues;
        DataMapHashSetIte = DataMapHashSet.keySet().iterator();
        DataMapHashSetIndex = 0;
    }

    /**
     * Returns <tt>true</tt> if the iteration has more elements. (In other
     * words, returns <tt>true</tt> if <tt>next</tt> would return an element
     * rather than throwing an exception.)
     *
     * @return <tt>true</tt> if the iterator has more elements.
     */
    @Override
    public boolean hasNext() {
        return hasNext;
    }

    /**
     * Returns the next element in the iteration. First all the default values
     * are returned then all the values in DataMapBitSet, then all the values in
     * DataMapHashSet.
     *
     * @return the next element in the iteration or null.
     * @exception NoSuchElementException iteration has no more elements.
     */
    public Integer next() {
        if (hasNext) {
            if (DefaultValueIndex == NumberOfDefaultValues - 1) {
                if (DataMapBitSetIndex == DataMapBitSetNumberOfValues - 1) {
                    if (DataMapBitSetIte.hasNext()) {
                        DataMapBitSetValue = DataMapBitSetIte.next();
                        DataMapBitSetNumberOfValues = DataMapBitSet
                                .get(DataMapBitSetValue).bitSet.cardinality();
                        DataMapBitSetIndex = 0;
                        return DataMapBitSetValue;
                    } else {
                        if (DataMapHashSetIndex == DataMapHashSetNumberOfValues - 1) {
                            if (DataMapHashSetIte.hasNext()) {
                                DataMapHashSetValue = DataMapHashSetIte.next();
                                DataMapHashSetNumberOfValues = DataMapHashSet
                                        .get(DataMapHashSetValue).size();
                                DataMapHashSetIndex = 0;
                                return DataMapHashSetValue;
                            } else {
                                hasNext = false;
                                return null;
                            }
                        } else {
                            DataMapHashSetIndex++;
                            return DataMapHashSetValue;
                        }
                    }
                } else {
                    DataMapBitSetIndex++;
                    return DataMapBitSetValue;
                }
            } else {
                DefaultValueIndex++;
                return DefaultValue;
            }
        } else {
            return null;
        }
    }

}
