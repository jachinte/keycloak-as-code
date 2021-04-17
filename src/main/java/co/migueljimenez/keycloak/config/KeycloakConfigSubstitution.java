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
import java.nio.file.Files;
import org.apache.commons.text.StringSubstitutor;

/**
 * A class for variable substitution within a Keycloak configuration (JSON) file.
 * @author Miguel Jimenez
 * @version $Id$
 * @since 0.2.0
 */
public final class KeycloakConfigSubstitution {

    /**
     * An object for String substitution.
     */
    private final StringSubstitutor substitution;

    /**
     * The keycloak configuration template.
     */
    private final File configuration;

    /**
     * Default constructor.
     * @param config The keycloak configuration template
     */
    public KeycloakConfigSubstitution(final File config) {
        // TODO Externalize these variables as command switches
        this.substitution = StringSubstitutor.createInterpolator()
            .setEnableSubstitutionInVariables(true)
            .setEnableUndefinedVariableException(true);
        this.configuration = config;
    }

    /**
     * Substitutes all variables and returns the result.
     * @return The plain configuration
     * @throws IOException If the file does not exist or cannot be read
     */
    public String substitute() throws IOException {
        final String content = Files.readString(this.configuration.toPath());
        return this.substitution.replace(content);
    }

}
