## Keycloak Configuration as Code

This is a command line application that generates a plain configuration file based on a JSON template.
Variables within the template are replaced with their corresponding value at run-time.
Examples of variable substitution are:

```
Base64 Decoder:        ${base64Decoder:SGVsbG9Xb3JsZCE=}
Base64 Encoder:        ${base64Encoder:HelloWorld!}
Java Constant:         ${const:java.awt.event.KeyEvent.VK_ESCAPE}
Date:                  ${date:yyyy-MM-dd}
DNS:                   ${dns:address|apache.org}
Environment Variable:  ${env:USERNAME}
File Content:          ${file:UTF-8:src/test/resources/document.properties}
Java:                  ${java:version}
Localhost:             ${localhost:canonical-name}
Properties File:       ${properties:src/test/resources/document.properties::mykey}
Resource Bundle:       ${resourceBundle:org.example.testResourceBundleLookup:mykey}
Script:                ${script:javascript:3 + 4}
System Property:       ${sys:user.dir}
URL Decoder:           ${urlDecoder:Hello%20World%21}
URL Encoder:           ${urlEncoder:Hello World!}
URL Content (HTTP):    ${url:UTF-8:http://www.apache.org}
URL Content (HTTPS):   ${url:UTF-8:https://www.apache.org}
URL Content (File):    ${url:UTF-8:file:///${sys:user.dir}/src/test/resources/document.properties}
XML XPath:             ${xml:src/test/resources/document.xml:/root/path/to/node}
```

See [Apache Common StringSubstitutor documentation](https://commons.apache.org/proper/commons-text/apidocs/org/apache/commons/text/StringSubstitutor.html) for more information and advanced usage.

### Use this project

Visit the [releases](/releases) page and download the binary.

### Compile, test and run this project

```
# Generate JAR files
./gradlew assemble

# Generate a native image
./gradlew nativeImage

# Test and run
./gradlew test --info
./gradlew run --args="-i path/to/template"
```
