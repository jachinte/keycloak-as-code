/*
 * Copyright 2021 Miguel Jimenez.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package co.migueljimenez.keycloak.config;

import io.micronaut.configuration.picocli.PicocliRunner;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

/**
 * The keycloak-as-code command.
 * @author Miguel Jimenez
 * @version $Id$
 * @since 0.1.0
 */
@CommandLine.Command(
    name = "keycloak-as-code",
    description = "Prints out plain configuration files from a JSON template",
    mixinStandardHelpOptions = true,
    version = "0.2.0"
)
@RequiredArgsConstructor
public final class KeycloakAsCodeCommand implements Runnable {

    /**
     * The logger.
     */
    private static final Logger LOGGER =
        LoggerFactory.getLogger(KeycloakAsCodeCommand.class);

    /**
     * File not found error code.
     */
    private static final int FILE_NOT_FOUND = 1;

    /**
     * Unknown error code.
     */
    private static final int UNKNOWN_ERROR = 2;

    /**
     * A path to the input configuration file (a JSON template).
     */
    @CommandLine.Option(
        names = {"-i", "--input"},
        description = "A path to the input template (JSON)",
        required = true
    )
    private String input;

    /**
     * A path to the output file (optional).
     */
    @CommandLine.Option(
        names = {"-o", "--output"},
        description = "A path to the output file"
    )
    private String output;

    /**
     * The main entry point.
     * @param args The application arguments
     */
    public static void main(final String... args) {
        PicocliRunner.run(KeycloakAsCodeCommand.class, args);
    }

    @Override
    public void run() {
        final File file = new File(this.input);
        if (!file.exists()) {
            KeycloakAsCodeCommand.LOGGER.error("The input files does not exist");
            System.exit(KeycloakAsCodeCommand.FILE_NOT_FOUND);
        }
        try {
            final String plain = new KeycloakConfigSubstitution(file)
                .substitute();
            if (this.output != null && this.output.isBlank()) {
                Files.writeString(
                    new File(this.output).toPath(),
                    plain,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.TRUNCATE_EXISTING
                );
            } else {
                KeycloakAsCodeCommand.LOGGER.info(plain);
            }
        } catch (final IOException exception) {
            KeycloakAsCodeCommand.LOGGER.error(
                "An error occurred when performing the substitution: {}",
                exception.getLocalizedMessage()
            );
            System.exit(KeycloakAsCodeCommand.UNKNOWN_ERROR);
        }
    }
}
