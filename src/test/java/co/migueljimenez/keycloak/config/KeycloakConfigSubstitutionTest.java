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

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Tests {@link KeycloakConfigSubstitution}.
 * @author Miguel Jimenez
 * @version $Id$
 * @since 0.2.0
 */
final class KeycloakConfigSubstitutionTest {

    @ParameterizedTest
    @ValueSource(strings = {
        "src/test/resources/import-files/realm-with-users.json",
        "src/test/resources/import-files/realm-with-clients.json"
    })
    void testSimpleTemplate(final String path) throws IOException {
        final File file = new File(path);
        final KeycloakConfigSubstitution substitution =
            new KeycloakConfigSubstitution(file);
        final String plain = substitution.substitute();
        Assertions.assertTrue(
            plain.contains("\"HelloWorld!\""),
            "Plain file should be a plain value"
        );
    }

}
