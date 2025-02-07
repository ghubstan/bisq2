/*
 * This file is part of Bisq.
 *
 * Bisq is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * Bisq is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Bisq. If not, see <http://www.gnu.org/licenses/>.
 */

package bisq;

import bisq.common.util.FileUtils;
import bisq.common.util.OsUtils;
import bisq.tor.Constants;
import bisq.tor.Tor;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;


public class TorIntegrationTest {
    private static final Logger log = LoggerFactory.getLogger(TorIntegrationTest.class);

    @Test
    public void testShutdownDuringStartup() {
        String torDirPath = OsUtils.getUserDataDir() + "/TorifyIntegrationTest";
        File versionFile = new File(torDirPath + "/" + Constants.VERSION);
        try {
            FileUtils.deleteFileOrDirectory(torDirPath);
        } catch (IOException e) {
            log.error("Could not delete " + torDirPath, e);
            throw new RuntimeException(e);
        }
        assertFalse(versionFile.exists());
        Tor tor = Tor.getTor(torDirPath);
        new Thread(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException ignore) {
            }
            tor.shutdown();

        }).start();
        Thread mainThread = Thread.currentThread();
        tor.startAsync(Executors.newSingleThreadExecutor())
                .exceptionally(throwable -> {
                    assertFalse(versionFile.exists());
                    mainThread.interrupt();
                    return null;
                })
                .thenAccept(result -> {
                    if (result == null) {
                        return;
                    }
                    fail();
                });
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignore) {
        }
    }
}
