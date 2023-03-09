package asciimirror;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static String mirrorLine(String line) {
        final var mirrorMappings = Map.ofEntries(
                Map.entry('<', '>'),
                Map.entry('>', '<'),
                Map.entry('(', ')'),
                Map.entry(')', '('),
                Map.entry('[', ']'),
                Map.entry(']', '['),
                Map.entry('{', '{'),
                Map.entry('}', '{'),
                Map.entry('/', '\\'),
                Map.entry('\\', '/')
        );
        return new StringBuilder(line).reverse().codePoints()
                .mapToObj(c -> (char) c)
                .map(c -> mirrorMappings.getOrDefault(c, c))
                .map(Object::toString)
                .collect(Collectors.joining());
    }
    public static void main(String[] args) {
        final var scanner = new Scanner(System.in);
        System.out.println("Input the file path:");
        final var pathString = scanner.nextLine();
        try {
            final var filePath = Path.of(pathString);
            try (Stream<String> lines = Files.lines(filePath)) {
                List<String> lineList = new ArrayList<>();
                lines.forEach(lineList::add);
                int maxLength = lineList.stream()
                        .mapToInt(String::length)
                        .max()
                        .orElse(0);
                final var formatString = String.format("%%-%ds | %%%ds\n", maxLength, maxLength);
                lineList.forEach(line -> System.out.printf(formatString, line, mirrorLine(line)));
            } catch (IOException ioe) {
                System.out.println("File not found");
            }
        } catch (InvalidPathException ipe) {
            System.out.println("File not found");
        }
    }
}