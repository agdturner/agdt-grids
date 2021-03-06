/*
 * Copyright 2020 Andy Turner, University of Leeds.
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
package uk.ac.leeds.ccg.grids.d2.grid;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.hamcrest.Matchers;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.generic.io.Generic_Path;
import uk.ac.leeds.ccg.grids.core.Grids_Environment;
import uk.ac.leeds.ccg.grids.core.Grids_Strings;
import uk.ac.leeds.ccg.grids.d2.Grids_2D_ID_int;
import uk.ac.leeds.ccg.grids.d2.Grids_2D_ID_long;
import uk.ac.leeds.ccg.grids.d2.grid.d.Grids_GridDouble;
import uk.ac.leeds.ccg.grids.d2.grid.d.Grids_GridFactoryDouble;
import uk.ac.leeds.ccg.grids.d2.grid.i.Grids_GridFactoryInt;
import uk.ac.leeds.ccg.grids.process.Grids_Processor;

/**
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class Grids_GridTest {

    Generic_Environment env;
    Grids_Environment ge;
    Grids_Processor gp;

    public Grids_GridTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws Exception {
        Path dataDir = Paths.get(System.getProperty("user.home"),
                Grids_Strings.s_data);
        env = new Generic_Environment(new Generic_Defaults(
                Paths.get(dataDir.toString(), Grids_Strings.s_generic)));
        Generic_Path dir = new Generic_Path(dataDir);
        ge = new Grids_Environment(env, dir);
        gp = new Grids_Processor(ge);
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of isSameDimensionsAndChunks method, of class Grids_Processor.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testIsSameFrame() throws Exception {
        System.out.println("isSameFrame");
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        // Test 1
        Grids_Grid g0 = gfd.create(10, 10);
        Grids_Grid g1 = gfd.create(10, 10);
        boolean expResult = true;
        boolean result = g0.isSameDimensionsAndChunks(g1);
        assertEquals(expResult, result);
        // Test 2
        g0 = gfd.create(10, 10);
        g1 = gfd.create(10, 11);
        expResult = false;
        result = g0.isSameDimensionsAndChunks(g1);
        assertEquals(expResult, result);
        // Test 2
        Grids_GridFactoryInt gfi = gp.gridFactoryInt;
        g0 = gfd.create(10, 10);
        g1 = gfi.create(10, 10);
        expResult = true;
        result = g0.isSameDimensionsAndChunks(g1);
        assertEquals(expResult, result);
    }

    /**
     * Test of getCellDistance method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetCellDistance() throws Exception {
        System.out.println("getCellDistance");
        // Test 1
        BigDecimal distance = BigDecimal.TEN;
        BigDecimal expResult = BigDecimal.TEN;
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        Grids_Grid instance = gfd.create(10, 10);
        BigDecimal result = instance.getCellDistance(distance);
        assertEquals(expResult, result);
        // Test 2
        distance = BigDecimal.TEN;
        expResult = BigDecimal.valueOf(100);
        gfd = gp.gridFactoryDouble;
        BigDecimal xmin = BigDecimal.ZERO;
        BigDecimal xmax = BigDecimal.ONE;
        BigDecimal ymin = BigDecimal.ZERO;
        BigDecimal ymax = BigDecimal.ONE;
        BigDecimal cellSize = new BigDecimal("0.1");
        Grids_Dimensions dims = new Grids_Dimensions(xmin, xmax, ymin, ymax,
                cellSize);
        instance = gfd.create(10, 10, dims);
        result = instance.getCellDistance(distance);
        System.out.println("expResult=" + expResult.toPlainString());
        System.out.println("result=" + result.toPlainString());
        //assertEquals(expResult, result);
        assertThat(expResult, Matchers.comparesEqualTo(result));
    }

    /**
     * Test of getNCols method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetNCols() throws Exception {
        System.out.println("getNCols");
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        Grids_Grid instance = gfd.create(10, 10);
        long expResult = 10L;
        long result = instance.getNCols();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNRows method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetNRows() throws Exception {
        System.out.println("getNRows");
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        Grids_Grid instance = gfd.create(10, 10);
        long expResult = 10L;
        long result = instance.getNRows();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNChunkRows method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetNChunkRows() throws Exception {
        System.out.println("getNChunkRows");
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        Grids_Grid instance = gfd.create(1000, 1000);
        int expResult = 2;
        int result = instance.getNChunkRows();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNChunkCols method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetNChunkCols() throws Exception {
        System.out.println("getNChunkCols");
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        Grids_Grid instance = gfd.create(1000, 1000);
        int expResult = 2;
        int result = instance.getNChunkCols();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNChunks method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetNChunks() throws Exception {
        System.out.println("getNChunks");
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        Grids_Grid instance = gfd.create(1000, 1000);
        long expResult = 4;
        long result = instance.getNChunks();
        assertEquals(expResult, result);
    }

    /**
     * Test of getChunkNRows method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetChunkNRows_0args() throws Exception {
        System.out.println("getChunkNRows");
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        Grids_Grid instance = gfd.create(1000, 1000);
        int expResult = 512;
        int result = instance.getChunkNRows();
        assertEquals(expResult, result);
    }

    /**
     * Test of getChunkNRows method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetChunkNRows_int() throws Exception {
        System.out.println("getChunkNRows");
        // By default chunkNRows and chunkNCols are 512.
        int cr = 0;
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        Grids_Grid instance = gfd.create(1000, 1000);
        int expResult = 512;
        int result = instance.getChunkNRows(cr);
        assertEquals(expResult, result);
        cr = 1;
        expResult = 488;
        result = instance.getChunkNRows(cr);
        assertEquals(expResult, result);
    }

    /**
     * Test of getChunkNCols method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetChunkNCols_0args() throws Exception {
        System.out.println("getChunkNCols");
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        Grids_Grid instance = gfd.create(1000, 1000);
        int expResult = 512;
        int result = instance.getChunkNCols();
        assertEquals(expResult, result);
    }

    /**
     * Test of getChunkNCols method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetChunkNCols_int() throws Exception {
        System.out.println("getChunkNCols");
        // By default chunkNRows and chunkNCols are 512.
        int cc = 0;
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        Grids_Grid instance = gfd.create(1000, 1000);
        int expResult = 512;
        int result = instance.getChunkNCols(cc);
        assertEquals(expResult, result);
        cc = 1;
        expResult = 488;
        result = instance.getChunkNCols(cc);
        assertEquals(expResult, result);
    }

    /**
     * Test of getChunkNRowsFinalRowChunk method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetChunkNRowsFinalRowChunk() throws Exception {
        System.out.println("getChunkNRowsFinalRowChunk");
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        Grids_Grid instance = gfd.create(1000, 1000);
        int expResult = 488;
        int result = instance.getChunkNRowsFinalRowChunk();
        assertEquals(expResult, result);
    }

    /**
     * Test of getChunkNColsFinalColChunk method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetChunkNColsFinalColChunk() throws Exception {
        System.out.println("getChunkNColsFinalColChunk");
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        Grids_Grid instance = gfd.create(1000, 1000);
        int expResult = 488;
        int result = instance.getChunkNColsFinalColChunk();
        assertEquals(expResult, result);
    }

    /**
     * Test of getChunkNRows method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetChunkNRows_Grids_2D_ID_int() throws Exception {
        System.out.println("getChunkNRows");
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        Grids_Grid instance = gfd.create(1000, 1000);
        int expResult = 512;
        Grids_2D_ID_int i = new Grids_2D_ID_int(0, 0);
        int result = instance.getChunkNRows(i);
        assertEquals(expResult, result);
        // Test 2
        expResult = 488;
        i = new Grids_2D_ID_int(1, 0);
        result = instance.getChunkNRows(i);
        assertEquals(expResult, result);
        // Test 3
        expResult = 512;
        i = new Grids_2D_ID_int(0, 1);
        result = instance.getChunkNRows(i);
        assertEquals(expResult, result);
        // Test 4
        expResult = 488;
        i = new Grids_2D_ID_int(1, 1);
        result = instance.getChunkNRows(i);
        assertEquals(expResult, result);
    }

    /**
     * Test of getChunkNCols method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetChunkNCols_Grids_2D_ID_int() throws Exception {
        System.out.println("getChunkNCols");
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        Grids_Grid instance = gfd.create(1000, 1000);
        int expResult = 512;
        Grids_2D_ID_int i = new Grids_2D_ID_int(0, 0);
        int result = instance.getChunkNCols(i);
        assertEquals(expResult, result);
        // Test 2
        expResult = 512;
        i = new Grids_2D_ID_int(1, 0);
        result = instance.getChunkNCols(i);
        assertEquals(expResult, result);
        // Test 3
        expResult = 488;
        i = new Grids_2D_ID_int(0, 1);
        result = instance.getChunkNCols(i);
        assertEquals(expResult, result);
        // Test 4
        expResult = 488;
        i = new Grids_2D_ID_int(1, 1);
        result = instance.getChunkNCols(i);
        assertEquals(expResult, result);
    }

//    /**
//     * Test of getChunkIDs method, of class Grids_Grid.
//     */
//    @Test
//    public void testGetChunkIDs_7args() {
//        System.out.println("getChunkIDs");
//        BigDecimal distance = null;
//        BigDecimal x = null;
//        BigDecimal y = null;
//        long row = 0L;
//        long col = 0L;
//        int dp = 0;
//        RoundingMode rm = null;
//        Grids_Grid instance = null;
//        Set<Grids_2D_ID_int> expResult = null;
//        Set<Grids_2D_ID_int> result = instance.getChunkIDs(distance, x, y, row, col, dp, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getChunkIDs method, of class Grids_Grid.
//     */
//    @Test
//    public void testGetChunkIDs_4args_1() {
//        System.out.println("getChunkIDs");
//        long rowMin = 0L;
//        long rowMax = 0L;
//        long colMin = 0L;
//        long colMax = 0L;
//        Grids_Grid instance = null;
//        Set<Grids_2D_ID_int> expResult = null;
//        Set<Grids_2D_ID_int> result = instance.getChunkIDs(rowMin, rowMax, colMin, colMax);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getChunkIDs method, of class Grids_Grid.
//     */
//    @Test
//    public void testGetChunkIDs_4args_2() {
//        System.out.println("getChunkIDs");
//        int crMin = 0;
//        int crMax = 0;
//        int ccMin = 0;
//        int ccMax = 0;
//        Grids_Grid instance = null;
//        Set<Grids_2D_ID_int> expResult = null;
//        Set<Grids_2D_ID_int> result = instance.getChunkIDs(crMin, crMax, ccMin, ccMax);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    /**
     * Test of getChunkCol method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetChunkCol_BigDecimal() throws Exception {
        System.out.println("getChunkCol");
        BigDecimal x = BigDecimal.valueOf(0.5d);
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        Grids_Grid instance = gfd.create(1000, 5120);
        int expResult = 0;
        int result = instance.getChunkCol(x);
        assertEquals(expResult, result);
        // Test 2
        x = BigDecimal.valueOf(512.5d);
        expResult = 1;
        result = instance.getChunkCol(x);
        assertEquals(expResult, result);
        // Test 3
        x = BigDecimal.valueOf(1024.5d);
        expResult = 2;
        result = instance.getChunkCol(x);
        assertEquals(expResult, result);
        // Test 4
        x = BigDecimal.valueOf(5119.5d);
        expResult = 9;
        result = instance.getChunkCol(x);
        assertEquals(expResult, result);
        // Test 5
        x = BigDecimal.valueOf(5120.5d);
        expResult = 10;
        result = instance.getChunkCol(x);
        assertEquals(expResult, result);
        // Test 6
        x = BigDecimal.valueOf(-0.5d);
        expResult = -1;
        result = instance.getChunkCol(x);
        assertEquals(expResult, result);
    }

    /**
     * Test of getChunkCol method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetChunkCol_long() throws Exception {
        System.out.println("getChunkCol");
        long col = 0L;
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        long nrows = 1000;
        long ncols = 5120;
        Grids_Grid instance = gfd.create(nrows, ncols);
        int expResult = 0;
        int result = instance.getChunkCol(col);
        assertEquals(expResult, result);
        // Test 2
        col = -1L;
        expResult = -1;
        result = instance.getChunkCol(col);
        assertEquals(expResult, result);
        // Test 3
        col = -1L;
        expResult = -1;
        result = instance.getChunkCol(col);
        assertEquals(expResult, result);
        // Test 4
        col = -512L;
        expResult = -2;
        result = instance.getChunkCol(col);
        assertEquals(expResult, result);
        // Test 4
        col = 5119L;
        expResult = 9;
        result = instance.getChunkCol(col);
        assertEquals(expResult, result);
        // Test 4
        col = 5120L;
        expResult = 10;
        result = instance.getChunkCol(col);
        assertEquals(expResult, result);
    }

    /**
     * Test of getChunkCellCol method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetChunkCellCol_BigDecimal() throws Exception {
        System.out.println("getChunkCellCol");
        BigDecimal x = BigDecimal.ZERO;
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        long nrows = 1000;
        long ncols = 5120;
        Grids_Grid instance = gfd.create(nrows, ncols);
        int expResult = 0;
        int result = instance.getChunkCellCol(x);
        assertEquals(expResult, result);
        // Test 2
        x = BigDecimal.valueOf(-0.5);
        expResult = 511;
        result = instance.getChunkCellCol(x);
        assertEquals(expResult, result);
        // Test 3
        x = BigDecimal.valueOf(5120);
        expResult = 0;
        result = instance.getChunkCellCol(x);
        assertEquals(expResult, result);
        // Test 3
        x = new BigDecimal("5119.999999999999999999999999999");
        expResult = 511;
        result = instance.getChunkCellCol(x);
        assertEquals(expResult, result);
    }

    /**
     * Test of getChunkCellCol method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetChunkCellCol_long() throws Exception {
        System.out.println("getChunkCellCol");
        long col = 0L;
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        long nrows = 1000;
        long ncols = 5120;
        Grids_Grid instance = gfd.create(nrows, ncols);
        int expResult = 0;
        int result = instance.getChunkCellCol(col);
        assertEquals(expResult, result);
        // Test 2
        col = 1L;
        expResult = 1;
        result = instance.getChunkCellCol(col);
        assertEquals(expResult, result);
        // Test 3
        col = -1L;
        expResult = 511;
        result = instance.getChunkCellCol(col);
        assertEquals(expResult, result);
        // Test 4
        col = ncols;
        expResult = 0;
        result = instance.getChunkCellCol(col);
        assertEquals(expResult, result);
        // Test 5
        col = ncols - 1;
        expResult = 511;
        result = instance.getChunkCellCol(col);
        assertEquals(expResult, result);
    }

    /**
     * Test of getChunkCellRow method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetChunkCellRow_BigDecimal() throws Exception {
        System.out.println("getChunkCellRow");
        BigDecimal y = BigDecimal.ZERO;
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        int chunkNRows = gfd.chunkNRows;
        long nrows = 5120;
        long ncols = 1000;
        Grids_Grid instance = gfd.create(nrows, ncols);
        int expResult = 0;
        int result = instance.getChunkCellRow(y);
        assertEquals(expResult, result);
        // Test 2
        y = new BigDecimal("-0.5");
        expResult = chunkNRows - 1;
        result = instance.getChunkCellRow(y);
        assertEquals(expResult, result);
        // Test 3
        y = new BigDecimal("5120");
        expResult = 0;
        result = instance.getChunkCellRow(y);
        assertEquals(expResult, result);
        // Test 3
        y = new BigDecimal("5119.99999999999999");
        expResult = 511;
        result = instance.getChunkCellRow(y);
        assertEquals(expResult, result);
    }

    /**
     * Test of getChunkCellRow method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetChunkCellRow_long() throws Exception {
        System.out.println("getChunkCellRow");
        long row = 0L;
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        int chunkNRows = gfd.chunkNRows;
        long nrows = 5120;
        long ncols = 1000;
        Grids_Grid instance = gfd.create(nrows, ncols);
        int expResult = 0;
        int result = instance.getChunkCellRow(row);
        assertEquals(expResult, result);
        // Test 2
        row = -1L;
        expResult = chunkNRows - 1;
        result = instance.getChunkCellRow(row);
        assertEquals(expResult, result);
        // Test 3
        row = 5120L;
        expResult = 0;
        result = instance.getChunkCellRow(row);
        assertEquals(expResult, result);
        // Test 3
        row = 5119L;
        expResult = chunkNRows - 1;
        result = instance.getChunkCellRow(row);
        assertEquals(expResult, result);
    }

    /**
     * Test of getCol method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetCol_BigDecimal() throws Exception {
        System.out.println("getCol");
        BigDecimal x = BigDecimal.ZERO;
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        int chunkNRows = gfd.chunkNRows;
        long nrows = 5120;
        long ncols = 1000;
        Grids_Grid instance = gfd.create(nrows, ncols);
        long expResult = 0L;
        long result = instance.getCol(x);
        assertEquals(expResult, result);
        // Test 2
        x = new BigDecimal("5119.999999999999999");
        expResult = 5119L;
        result = instance.getCol(x);
        assertEquals(expResult, result);
        // Test 3
        x = new BigDecimal("5119.9999999999999999999999999999999999999999999999"
                + "9999999999999999999999999999999999999999999999999999999999");
        expResult = 5119L;
        result = instance.getCol(x);
        assertEquals(expResult, result);
    }

    /**
     * Test of getCol method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetCol_int_int() throws Exception {
        System.out.println("getCol");
        int cc = 0;
        int ccc = 0;
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        int chunkNRows = gfd.chunkNRows;
        int chunkNCols = gfd.chunkNCols;
        long nrows = 5120;
        long ncols = 1000;
        Grids_Grid instance = gfd.create(nrows, ncols);
        long expResult = 0L;
        long result = instance.getCol(cc, ccc);
        assertEquals(expResult, result);
        // Test 2
        cc = 1;
        ccc = 1;
        expResult = chunkNCols + 1;
        result = instance.getCol(cc, ccc);
        assertEquals(expResult, result);
    }

    /**
     * Test of getChunkRow method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetChunkRow_BigDecimal() throws Exception {
        System.out.println("getChunkRow");
        BigDecimal y = BigDecimal.ZERO;
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        int chunkNRows = gfd.chunkNRows;
        int chunkNCols = gfd.chunkNCols;
        long nrows = 5120;
        long ncols = 1000;
        Grids_Grid instance = gfd.create(nrows, ncols);
        int expResult = 0;
        int result = instance.getChunkRow(y);
        assertEquals(expResult, result);
        // Test 2
        y = new BigDecimal("-0.000000000000000000000000000000000000000000000000"
                + "000000000000000000000000000000000000000000000000000000000000"
                + "0000000000000000000000000000000000000000000000000000000001");
        expResult = -1;
        result = instance.getChunkRow(y);
        assertEquals(expResult, result);
    }

    /**
     * Test of getChunkRow method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetChunkRow_long() throws Exception {
        System.out.println("getChunkRow");
        long row = 0L;
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        int chunkNRows = gfd.chunkNRows;
        int chunkNCols = gfd.chunkNCols;
        long nrows = 5120;
        long ncols = 1000;
        Grids_Grid instance = gfd.create(nrows, ncols);
        int expResult = 0;
        int result = instance.getChunkRow(row);
        assertEquals(expResult, result);
        // Test 2
        row = -1;
        expResult = -1;
        result = instance.getChunkRow(row);
        assertEquals(expResult, result);
        // Test 3
        row = -1;
        expResult = -1;
        result = instance.getChunkRow(row);
        assertEquals(expResult, result);
        // Test 4
        row = nrows;
        expResult = 10;
        result = instance.getChunkRow(row);
        assertEquals(expResult, result);
    }

    /**
     * Test of getRow method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetRow_BigDecimal() throws Exception {
        System.out.println("getRow");
        BigDecimal y = BigDecimal.ZERO;
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        int chunkNRows = gfd.chunkNRows;
        int chunkNCols = gfd.chunkNCols;
        long nrows = 5120;
        long ncols = 1000;
        Grids_Grid instance = gfd.create(nrows, ncols);
        long expResult = 0L;
        long result = instance.getRow(y);
        assertEquals(expResult, result);
        // Test 2
        y = new BigDecimal("-0.000000000000000000000000000000000000000000000000"
                + "000000000000000000000000000000000000000000000000000000000000"
                + "0000000000000000000000000000000000000000000000000000000001");
        expResult = -1L;
        result = instance.getRow(y);
        assertEquals(expResult, result);
    }

    /**
     * Test of getRow method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetRow_int_int() throws Exception {
        System.out.println("getRow");
        int cr = 0;
        int ccr = 0;
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        int chunkNRows = gfd.chunkNRows;
        int chunkNCols = gfd.chunkNCols;
        long nrows = 5120;
        long ncols = 1000;
        Grids_Grid instance = gfd.create(nrows, ncols);
        long expResult = 0L;
        long result = instance.getRow(cr, ccr);
        assertEquals(expResult, result);
        // Test 2
        cr = 1;
        ccr = 1;
        expResult = chunkNRows + 1;
        result = instance.getRow(cr, ccr);
        assertEquals(expResult, result);
    }

    /**
     * Test of getCellID method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetCellID_long_long() throws Exception {
        System.out.println("getCellID");
        long row = 0L;
        long col = 0L;
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        long nrows = 512;
        long ncols = 100;
        Grids_Grid instance = gfd.create(nrows, ncols);
        Grids_2D_ID_long expResult = new Grids_2D_ID_long(row, col);
        Grids_2D_ID_long result = instance.getCellID(row, col);
        assertEquals(expResult, result);
    }

    /**
     * Test of getCellID method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetCellID_BigDecimal_BigDecimal() throws Exception {
        System.out.println("getCellID");
        BigDecimal x = BigDecimal.ZERO;
        BigDecimal y = BigDecimal.ZERO;
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        long nrows = 512;
        long ncols = 100;
        Grids_Grid instance = gfd.create(nrows, ncols);
        Grids_2D_ID_long expResult = new Grids_2D_ID_long(0L, 0L);
        Grids_2D_ID_long result = instance.getCellID(x, y);
        assertEquals(expResult, result);
    }

//    /**
//     * Test of getCellIDs method, of class Grids_Grid.
//     */
//    @Test
//    public void testGetCellIDs_5args_1() {
//        System.out.println("getCellIDs");
//        BigDecimal x = null;
//        BigDecimal y = null;
//        BigDecimal distance = null;
//        int dp = 0;
//        RoundingMode rm = null;
//        Grids_Grid instance = null;
//        Grids_2D_ID_long[] expResult = null;
//        Grids_2D_ID_long[] result = instance.getCellIDs(x, y, distance, dp, rm);
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getCellIDs method, of class Grids_Grid.
//     */
//    @Test
//    public void testGetCellIDs_5args_2() {
//        System.out.println("getCellIDs");
//        long row = 0L;
//        long col = 0L;
//        BigDecimal distance = null;
//        int dp = 0;
//        RoundingMode rm = null;
//        Grids_Grid instance = null;
//        Grids_2D_ID_long[] expResult = null;
//        Grids_2D_ID_long[] result = instance.getCellIDs(row, col, distance, dp, rm);
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getCellIDs method, of class Grids_Grid.
//     */
//    @Test
//    public void testGetCellIDs_7args() {
//        System.out.println("getCellIDs");
//        BigDecimal x = null;
//        BigDecimal y = null;
//        long row = 0L;
//        long col = 0L;
//        BigDecimal distance = null;
//        int dp = 0;
//        RoundingMode rm = null;
//        Grids_Grid instance = null;
//        Grids_2D_ID_long[] expResult = null;
//        Grids_2D_ID_long[] result = instance.getCellIDs(x, y, row, col, distance, dp, rm);
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getNearestCellID method, of class Grids_Grid.
//     */
//    @Test
//    public void testGetNearestCellID_BigDecimal_BigDecimal() {
//        System.out.println("getNearestCellID");
//        BigDecimal x = null;
//        BigDecimal y = null;
//        Grids_Grid instance = null;
//        Grids_2D_ID_long expResult = null;
//        Grids_2D_ID_long result = instance.getNearestCellID(x, y);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getNearestCellID method, of class Grids_Grid.
//     */
//    @Test
//    public void testGetNearestCellID_long_long() {
//        System.out.println("getNearestCellID");
//        long r = 0L;
//        long c = 0L;
//        Grids_Grid instance = null;
//        Grids_2D_ID_long expResult = null;
//        Grids_2D_ID_long result = instance.getNearestCellID(r, c);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getNearestCellID method, of class Grids_Grid.
//     */
//    @Test
//    public void testGetNearestCellID_4args() {
//        System.out.println("getNearestCellID");
//        BigDecimal x = null;
//        BigDecimal y = null;
//        long row = 0L;
//        long col = 0L;
//        Grids_Grid instance = null;
//        Grids_2D_ID_long expResult = null;
//        Grids_2D_ID_long result = instance.getNearestCellID(x, y, row, col);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    /**
     * Test of getHeight method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetHeight() throws Exception {
        System.out.println("getHeight");
        // By default chunkNRows and chunkNCols are 512 and cellSize is 1.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        long nrows = 512;
        long ncols = 100;
        Grids_Grid instance = gfd.create(nrows, ncols);
        BigDecimal expResult = BigDecimal.valueOf(nrows);
        BigDecimal result = instance.getHeight();
        assertEquals(expResult, result);
    }

    /**
     * Test of getWidth method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetWidth() throws Exception {
        System.out.println("getWidth");
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        long nrows = 512;
        long ncols = 100;
        Grids_Grid instance = gfd.create(nrows, ncols);
        BigDecimal expResult = BigDecimal.valueOf(ncols);
        BigDecimal result = instance.getWidth();
        assertEquals(expResult, result);
    }

    /**
     * Test of isSameDimensionsAndValues method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testIsSameDimensionsAndValues() throws Exception {
        System.out.println("isSameDimensionsAndValues");
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        long nrows = 512;
        long ncols = 100;
        Grids_Grid g = gfd.create(nrows, ncols);
        Grids_Grid instance = gfd.create(nrows, ncols);
        boolean expResult = true;
        boolean result = instance.isSameDimensionsAndValues(g);
        assertEquals(expResult, result);
        // Test 2
        gp.gridFactoryDouble.setChunkNCols(50);
        instance = gfd.create(nrows, ncols);
        result = instance.isSameDimensionsAndValues(g);
        expResult = true;
        assertEquals(expResult, result);
        // Test 3
        ((Grids_GridDouble) instance).setCell(0, 0, 10);
        result = instance.isSameDimensionsAndValues(g);
        expResult = false;
        assertEquals(expResult, result);
        // Test 3
        ((Grids_GridDouble) g).setCell(0, 0, 10);
        result = instance.isSameDimensionsAndValues(g);
        expResult = true;
        assertEquals(expResult, result);
    }

    /**
     * Test of isSameDimensions method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testIsSameDimensions() throws Exception {
        System.out.println("isSameDimensions");
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        long nrows = 512;
        long ncols = 100;
        Grids_Grid g = gfd.create(nrows, ncols);
        Grids_Grid instance = gfd.create(nrows, ncols);
        boolean expResult = true;
        boolean result = instance.isSameDimensionsAndValues(g);
        assertEquals(expResult, result);
        // Test 2
        gp.gridFactoryDouble.setChunkNCols(50);
        instance = gfd.create(nrows, ncols);
        result = instance.isSameDimensionsAndValues(g);
        assertEquals(expResult, result);
    }

    /**
     * Test of isSameDimensionsAndChunks method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testIsSameDimensionsAndChunks() throws Exception {
        System.out.println("isSameDimensionsAndChunks");
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        long nrows = 512;
        long ncols = 100;
        Grids_Grid g = gfd.create(nrows, ncols);
        Grids_Grid instance = gfd.create(nrows, ncols);
        boolean expResult = true;
        boolean result = instance.isSameDimensionsAndChunks(g);
        assertEquals(expResult, result);
        // Test 2
        gp.gridFactoryDouble.setChunkNCols(50);
        instance = gfd.create(nrows, ncols);
        result = instance.isSameDimensionsAndChunks(g);
        expResult = false;
        assertEquals(expResult, result);
    }

    /**
     * Test of isCoincident method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testIsCoincident() throws Exception {
        System.out.println("isCoincident");
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        long nrows = 512;
        long ncols = 100;
        Grids_Grid g = gfd.create(nrows, ncols);
        Grids_Grid instance = gfd.create(nrows, ncols);
        boolean expResult = true;
        boolean result = instance.isCoincident(g);
        assertEquals(expResult, result);
        // Test 2
        BigDecimal xmin = BigDecimal.valueOf(-1);
        BigDecimal xmax = BigDecimal.valueOf(99);
        BigDecimal ymin = BigDecimal.valueOf(-1);
        BigDecimal ymax = BigDecimal.valueOf(511);
        BigDecimal cellSize = new BigDecimal("1");
        Grids_Dimensions d = new Grids_Dimensions(xmin, xmax, ymin, ymax,
                cellSize);
        gp.gridFactoryDouble.setDimensions(d);
        g = gfd.create(nrows, ncols, d);
        expResult = true;
        result = instance.isCoincident(g);
        assertEquals(expResult, result);
    }

    /**
     * Test of isInGrid method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testIsInGrid_BigDecimal_BigDecimal() throws Exception {
        System.out.println("isInGrid");
        BigDecimal x = new BigDecimal("0.000000000000000000000000000000000001");
        BigDecimal y = new BigDecimal("0.000000000000000000000000000000000001");
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        long nrows = 1;
        long ncols = 1;
        Grids_Grid instance = gfd.create(nrows, ncols);
        boolean expResult = true;
        boolean result = instance.isInGrid(x, y);
        assertEquals(expResult, result);
    }

    /**
     * Test of isInGrid method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testIsInGrid_long_long() throws Exception {
        System.out.println("isInGrid");
        long r = 0L;
        long c = 0L;
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        long nrows = 1;
        long ncols = 1;
        Grids_Grid instance = gfd.create(nrows, ncols);
        boolean expResult = true;
        boolean result = instance.isInGrid(r, c);
        assertEquals(expResult, result);
    }

    /**
     * Test of isInGrid method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testIsInGrid_Grids_2D_ID_long() throws Exception {
        System.out.println("isInGrid");
        Grids_2D_ID_long i = new Grids_2D_ID_long(0L, 0L);
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        long nrows = 1;
        long ncols = 1;
        Grids_Grid instance = gfd.create(nrows, ncols);
        boolean expResult = true;
        boolean result = instance.isInGrid(i);
        assertEquals(expResult, result);
        // Test 2
        i = new Grids_2D_ID_long(1L, 0L);
        expResult = false;
        result = instance.isInGrid(i);
        assertEquals(expResult, result);
    }

    /**
     * Test of isInGrid method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testIsInGrid_Grids_2D_ID_int() throws Exception {
        System.out.println("isInGrid");
        Grids_2D_ID_int i = new Grids_2D_ID_int(0, 0);
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        long nrows = 1;
        long ncols = 1;
        Grids_Grid instance = gfd.create(nrows, ncols);
        boolean expResult = true;
        boolean result = instance.isInGrid(i);
        assertEquals(expResult, result);
        // Test 2
        i = new Grids_2D_ID_int(1, 0);
        expResult = false;
        result = instance.isInGrid(i);
        assertEquals(expResult, result);
    }

    /**
     * Test of isInGrid method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testIsInGrid_4args() throws Exception {
        System.out.println("isInGrid");
        int cr = 0;
        int cc = 0;
        int ccr = 0;
        int ccc = 0;
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        long nrows = 1;
        long ncols = 1;
        Grids_Grid instance = gfd.create(nrows, ncols);
        boolean expResult = true;
        boolean result = instance.isInGrid(cr, cc, ccr, ccc);
        assertEquals(expResult, result);
        // Test 2
        cr = 1;
        expResult = false;
        result = instance.isInGrid(cr, cc, ccr, ccc);
        assertEquals(expResult, result);
    }

    /**
     * Test of getCellX method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetCellX_long() throws Exception {
        System.out.println("getCellX");
        long col = 0L;
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        long nrows = 1;
        long ncols = 1;
        Grids_Grid instance = gfd.create(nrows, ncols);
        BigDecimal expResult = BigDecimal.valueOf(0.5d);
        BigDecimal result = instance.getCellX(col);
        //assertEquals(expResult, result);
        assertThat(expResult, Matchers.comparesEqualTo(result));
    }

    /**
     * Test of getCellX method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetCellX_Grids_2D_ID_long() throws Exception {
        System.out.println("getCellX");
        Grids_2D_ID_long i = new Grids_2D_ID_long(0L, 0L);
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        long nrows = 1;
        long ncols = 1;
        Grids_Grid instance = gfd.create(nrows, ncols);
        BigDecimal expResult = BigDecimal.valueOf(0.5d);
        BigDecimal result = instance.getCellX(i);
        //assertEquals(expResult, result);
        assertThat(expResult, Matchers.comparesEqualTo(result));
    }

    /**
     * Test of getCellY method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetCellY_long() throws Exception {
        System.out.println("getCellY");
        long row = 0L;
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        long nrows = 1;
        long ncols = 1;
        Grids_Grid instance = gfd.create(nrows, ncols);
        BigDecimal expResult = BigDecimal.valueOf(0.5d);
        BigDecimal result = instance.getCellY(row);
        //assertEquals(expResult, result);
        assertThat(expResult, Matchers.comparesEqualTo(result));
    }

    /**
     * Test of getCellY method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetCellY_Grids_2D_ID_long() throws Exception {
        System.out.println("getCellY");
        Grids_2D_ID_long i = new Grids_2D_ID_long(0L, 0L);
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        long nrows = 1;
        long ncols = 1;
        Grids_Grid instance = gfd.create(nrows, ncols);
        BigDecimal expResult = BigDecimal.valueOf(0.5d);
        BigDecimal result = instance.getCellY(i);
        //assertEquals(expResult, result);
        assertThat(expResult, Matchers.comparesEqualTo(result));
    }

    /**
     * Test of getCellBounds method, of class Grids_Grid.
     *
     * @throws Exception If encountered.
     */
    @Test
    public void testGetCellBounds() throws Exception {
        System.out.println("getCellBounds");
        long row = 0L;
        long col = 0L;
        // By default chunkNRows and chunkNCols are 512.
        Grids_GridFactoryDouble gfd = gp.gridFactoryDouble;
        long nrows = 1;
        long ncols = 1;
        Grids_Grid instance = gfd.create(nrows, ncols);
        BigDecimal[] expResult = new BigDecimal[4];
        expResult[0] = BigDecimal.ZERO;
        expResult[1] = BigDecimal.ZERO;
        expResult[2] = BigDecimal.ONE;
        expResult[3] = BigDecimal.ONE;
        BigDecimal[] result = instance.getCellBounds(row, col);
        //assertEquals(expResult, result);
        for (int i = 0; i < 4; i++) {
            assertThat(expResult[i], Matchers.comparesEqualTo(result[i]));
        }
    }
}
