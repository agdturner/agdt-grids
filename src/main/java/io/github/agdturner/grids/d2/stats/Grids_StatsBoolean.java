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
package io.github.agdturner.grids.d2.stats;

import java.io.IOException;
import java.util.Iterator;
import io.github.agdturner.grids.core.Grids_2D_ID_int;
import io.github.agdturner.grids.core.Grids_Environment;
import io.github.agdturner.grids.d2.grid.b.Grids_GridBoolean;
import io.github.agdturner.grids.d2.grid.b.Grids_GridIteratorBoolean;
import java.math.BigInteger;

/**
 * Used by Grids_GridBinary instances to access statistics. This class is to be
 * instantiated for Grids_GridBinary that keep statistic fields up to date as
 * the underlying data is changed. (Keeping statistic fields up to date as the
 * underlying data is changed can be expensive! Second order statistics like the
 * standard deviation would always require going through all the data again if
 * the values have changed.)
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class Grids_StatsBoolean extends Grids_Stats {

    private static final long serialVersionUID = 1L;

    public Grids_StatsBoolean(Grids_Environment ge) {
        super(ge);
    }

    /**
     * @return true iff the stats are kept up to date as the underlying data
     * change.
     */
    @Override
    public boolean isUpdated() {
        return true;
    }

    /**
     *
     * @return (Grids_GridBoolean) grid
     */
    @Override
    public Grids_GridBoolean getGrid() {
        return (Grids_GridBoolean) grid;
    }

    /**
     * Updates by going through all values in grid.
     *
     * @throws java.lang.Exception If encountered.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    @Override
    public void update() throws IOException, Exception, ClassNotFoundException {
        env.checkAndMaybeFreeMemory();
        init();
        Grids_GridBoolean g = getGrid();
        boolean v;
        Grids_GridIteratorBoolean ite = g.iterator();
        while (ite.hasNext()) {
            v = ite.next();
            if (v) {
                n = n.add(BigInteger.ONE);
            }
        }
    }

    /**
     * @return The total number of {@code true} and {@code false} values in the
     * grid.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    @Override
    public BigInteger getN() throws IOException, Exception, ClassNotFoundException {
        BigInteger r = BigInteger.ZERO;
        Grids_GridBoolean g = getGrid();
        Iterator<Grids_2D_ID_int> ite = g.iterator().getGridIterator();
        while (ite.hasNext()) {
            r = r.add(BigInteger.valueOf(g.getChunk(ite.next()).getN()));
            env.checkAndMaybeFreeMemory();
        }
        return r;
    }
}
