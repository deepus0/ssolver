package au.com.deepus;

import au.com.deepus.mapper.SudokuMapper;
import au.com.deepus.solver.StandardSudokuSolver;
import au.com.deepus.solver.SudokuSolver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

    private final String SAMPLE_ONE_MISSING = "794350268532648719816279435127495683348716592965823147273964851481532976659187324";
    private final String SAMPLE_ONE_MISSING_SOL = "794351268532648719816279435127495683348716592965823147273964851481532976659187324";

    private final String SAMPLE_TWO_MISSING = "794351200532648719816279435127495683348716592965823147273964851481532976659187324";
    private final String SAMPLE_TWO_MISSING_SOL = "794351268532648719816279435127495683348716592965823147273964851481532976659187324";

    private final String SAMPLE_3_MISSING = "794351268532648719816279435127495683348716592965823147273964000481532000659187000";
    private final String SAMPLE_3_MISSING_SOL = "794351268532648719816279435127495683348716592965823147273964851481532976659187324";

    /**
     * 18th October Sudoku.com.au
     **/
    private final String SAMPLE_EASY_1 = "004351200502600709800200030100400080308000502060003007070004001401002906009187300";
    private final String SAMPLE_EASY_1_SOL = "794351268532648719816279435127495683348716592965823147273964851481532976659187324";

    private final String SAMPLE_EASY_2 = "007006020600457001980003056002001040560000072090700300830600014700924008040100900";
    private final String SAMPLE_EASY_2_SOL = "157896423623457891984213756372581649568349172491762385839675214716924538245138967";

    private final String SAMPLE_EASY_3 = "170830000806000530902070040400006800067502910009700003010020709053000401000058062";
    private final String SAMPLE_EASY_3_SOL = "175834296846219537932675148421396875367582914589741623618423759253967481794158362";

    private final String SAMPLE_EASY_4 = "020460017800003000046090820618005700009000300007800465065030270000100004390087050";
    private final String SAMPLE_EASY_4_SOL = "923468517871523649546791823618345792459672381237819465165934278782156934394287156";

    private final String SAMPLE_MEDIUM_1 = "630000059008350014000060000049006000006000900000400860000030000890012700310000026";
    private final String SAMPLE_MEDIUM_1_SOL = "637841259928357614451269387249186573586723941173495862762534198894612735315978426";

    private final String SAMPLE_MEDIUM_2 = "504000903700000008003209600060472090000000000070158030001306500600000009409000801";
    private final String SAMPLE_MEDIUM_2_SOL = "524687913796531248813249657368472195145963782972158436281396574657814329439725861";

    private final String SAMPLE_MEDIUM_3 = "704000103830000076000603000001702300000405000006809200000501000910000054308000609";
    private final String SAMPLE_MEDIUM_3_SOL = "764958123839124576125673498491762385283415967576839241647591832912386754358247619";

    private final String SAMPLE_MEDIUM_4 = "92.....36...8.2....83...25.1..5.7..8...6.4...2..3.1..9.64...97....4.9...87.....25";
    private final String SAMPLE_MEDIUM_4_SOL = "921745836657832194483916257196527348738694512245381769364258971512479683879163425";

    private final String SAMPLE_HARD_1 = "004600000600020003590300600000009078003000200850400000007005014900060002000001900";
    private final String SAMPLE_HARD_1_SOL = "734658129618927543592314687126539478473186295859472361367295814941863752285741936";

    private final String SAMPLE_HARD_2 = "500006380080050900401000002000700028000000000160008000300000204009070010052400006";
    private final String SAMPLE_HARD_2_SOL = "527946381683251947491387652935764128278139465164528739316895274849672513752413896";

    private final String SAMPLE_HARD_3 = "040000580006207000000000036900010000007306400000090001180000000000908600035000070";
    private final String SAMPLE_HARD_3_SOL = "743169582856237194291845736928514367517386429364792851189673245472958613635421978";

    private final String SAMPLE_HARD_4 = "570000040000302009028000000003080000400709006000030800000000970900104000060000025";
    private final String SAMPLE_HARD_4_SOL = "579618243146352789328947561213586497485729316697431852831265974952174638764893125";

    private final String SAMPLE_HARD_5 = "050004000308020506600380071007500398002000400185009700240057009809040607000100030";
    private final String SAMPLE_HARD_5_SOL = "751964823398721546624385971467512398932876415185439762243657189819243657576198234";

    private final String SAMPLE_HARD_6 = "000006030405080907180720005642500700001000800007002354900043076304010209020800000";
    private final String SAMPLE_HARD_6_SOL = "279456138465381927183729645642538791531974862897162354918243576354617289726895413";

    private final String SAMPLE_HARD_7 = "003080076890035002157400000001063000032000690000890100000002957600150034240070800";
    private final String SAMPLE_HARD_7_SOL = "423981576896735412157426389981263745732514698564897123318642957679158234245379861";

    private final String SAMPLE_WIKI = "530070000600195000098000060800060003400803001700020006060000280000419005000080079";
    private final String SAMPLE_WIKI_ANTI_BRUTE = "000000000000003085001020000000507000004000100090000000500000073002010000000040009";
    private final String SAMPLE_X_WING = "1.....569492.561.8.561.924...964.8.1.64.1....218.356.4.4.5...169.5.614.2621.....5";
    private final String SAMPLE_Y_WING = "900240000050690231020050090090700320002935607070002900069020073510079062207086009";
    private final String SAMPLE_Y_WING_2 = "900040000000600031020000090000700020002935600070002000060000073510090000000080009";
    private final String SAMPLE_XYZ_WING = "090001700500200008000030200070004960200060005069700030008090000700003009003800040";

    final SudokuMapper sudokuMapper = new SudokuMapper();
    final SudokuSolver solver = new StandardSudokuSolver();

    @Test
    void runRedundancyTests() {
        compareGrids(SAMPLE_ONE_MISSING, SAMPLE_ONE_MISSING_SOL);
        compareGrids(SAMPLE_TWO_MISSING, SAMPLE_TWO_MISSING_SOL);
        compareGrids(SAMPLE_3_MISSING, SAMPLE_3_MISSING_SOL);
    }

    @Test
    void runEasy1SudokuTest() {
        compareGrids(SAMPLE_EASY_1, SAMPLE_EASY_1_SOL);
    }

    @Test
    void runEasy2SudokuTest() {
        compareGrids(SAMPLE_EASY_2, SAMPLE_EASY_2_SOL);
    }

    @Test
    void runEasy3SudokuTest() {
        compareGrids(SAMPLE_EASY_3, SAMPLE_EASY_3_SOL);
    }

    @Test
    void runEasy4SudokuTest() {
        compareGrids(SAMPLE_EASY_4, SAMPLE_EASY_4_SOL);
    }

    @Test
    void runMedium1SudokuTest() {
        compareGrids(SAMPLE_MEDIUM_1, SAMPLE_MEDIUM_1_SOL);
    }

    @Test
    void runMedium2SudokuTest() {
        compareGrids(SAMPLE_MEDIUM_2, SAMPLE_MEDIUM_2_SOL);
    }

    @Test
    void runMedium3SudokuTest() {
        compareGrids(SAMPLE_MEDIUM_3, SAMPLE_MEDIUM_3_SOL);
    }

    @Test
    void runMedium4SudokuTest() {
        compareGrids(SAMPLE_MEDIUM_4, SAMPLE_MEDIUM_4_SOL);
    }

    @Test
    void runHard1SudokuTests() {
        compareGrids(SAMPLE_HARD_1, SAMPLE_HARD_1_SOL);
    }

    @Test
    void runHard2SudokuTests() {
        compareGrids(SAMPLE_HARD_2, SAMPLE_HARD_2_SOL);
    }

    @Test
    void runHard3SudokuTests() {
        compareGrids(SAMPLE_HARD_3, SAMPLE_HARD_3_SOL);
    }

    @Test
    void runHard4SudokuTests() {
        compareGrids(SAMPLE_HARD_4, SAMPLE_HARD_4_SOL);
    }

    @Test
    void runHard5SudokuTests() {
        compareGrids(SAMPLE_HARD_5, SAMPLE_HARD_5_SOL);
    }

    @Test
    void runHard6SudokuTests() {
        compareGrids(SAMPLE_HARD_6, SAMPLE_HARD_6_SOL);
    }

    @Test
    void runHard7SudokuTests() {
        compareGrids(SAMPLE_HARD_7, SAMPLE_HARD_7_SOL);
    }

    @Test
    void runWikiSudokuTest() {
        solveGrid(SAMPLE_WIKI);
        solveGrid(SAMPLE_WIKI_ANTI_BRUTE);
    }

    @Test
    void runXWingSudokuTest() {
        solveGrid(SAMPLE_X_WING);
    }
//
//    @Test
//    void runYWingSudokuTest() {
//        solveGrid(SAMPLE_Y_WING);
//    }
//
//    @Test
//    void runXYZWingSudokuTest() {
//        solveGrid(SAMPLE_XYZ_WING);
//    }
//
//    @Test
//    void runYWing2SudokuTest() {
//        solveGrid(SAMPLE_Y_WING_2);
//    }

    void solveGrid(String unsolved) {
        var unsolvedGrid = sudokuMapper.mapStandardSudokuGrid(unsolved);
        System.out.println(unsolvedGrid);
        var attemptSolve = solver.solve(unsolvedGrid);
        if (!attemptSolve.isSolved()) {
            attemptSolve.getSteps().forEach(System.out::println);
        }
        System.out.println(attemptSolve);
        assertTrue(attemptSolve.isSolved());
        System.out.println("Took " + attemptSolve.getIterationCount() + " iterations");
        attemptSolve.getSteps().forEach(System.out::println);
    }

    void compareGrids(String unsolved, String solved) {
        var unsolvedGrid = sudokuMapper.mapStandardSudokuGrid(unsolved);
        var solvedGrid = sudokuMapper.mapStandardSudokuGrid(solved);
        System.out.println(unsolvedGrid);

        var attemptSolve = solver.solve(unsolvedGrid);
        if (!attemptSolve.isSolved()) {
            System.out.println(attemptSolve);
            attemptSolve.getSteps().forEach(System.out::println);
        } else {
            System.out.println("Took " + attemptSolve.getIterationCount() + " iterations");
        }
        assertTrue(attemptSolve.isSolved());
        assertEquals(attemptSolve, solvedGrid);
    }
}
