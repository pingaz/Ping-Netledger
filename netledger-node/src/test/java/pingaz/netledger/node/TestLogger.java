package pingaz.netledger.node;

import org.junit.jupiter.api.*;

import java.util.logging.Logger;

/**
 * @author Pingaz
 */

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public interface TestLogger {
    static final Logger LOG = Logger.getLogger(TestLogger.class.getName());

    @BeforeAll
    default void beforeAllTests() {
        LOG.info("Before all tests");
    }

    @AfterAll
    default void afterAllTests() {
        LOG.info("After all tests");
    }

    @BeforeEach
    default void beforeEachTest(TestInfo testInfo) {
        LOG.info(() -> String.format("About to execute [%s]",
                testInfo.getDisplayName()));
    }

    @AfterEach
    default void afterEachTest(TestInfo testInfo) {
        LOG.info(() -> String.format("Finished executing [%s]",
                testInfo.getDisplayName()));
    }
}
