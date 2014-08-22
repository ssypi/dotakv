package kalamies.dota;

import org.apache.commons.cli.*;
import org.apache.commons.lang3.CharSet;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KvMerger {

    public static void main(String[] args) throws ParseException, IOException {
        KvMerger merger = new KvMerger();
        Options options = new Options();
        options.addOption("i", true, "Path to input folder");
        options.addOption("o", true, "Path to output file");

//        Option input = OptionBuilder.

        CommandLineParser parser = new GnuParser();
        CommandLine cmd = parser.parse(options, args);

        String inputPath = cmd.getOptionValue("i");
        String outputPath = cmd.getOptionValue("o");

        System.out.println(inputPath);
        System.out.println(outputPath);

        if (inputPath == null) {
            System.out.println("Invalid arguments, need input path.");
        } else {
            if (outputPath == null) {
                outputPath = inputPath + "/output.txt";
            }
            merger.MergeInFolder(inputPath, outputPath);
        }
    }

    public void MergeInFolder(String pathToFolder, String outputPath) throws IOException {

        List<Path> fileNames = Files.list(Paths.get(pathToFolder))
                .filter(file -> file.toString().endsWith(".txt") || file.toString().endsWith(".kv"))
                .filter(file -> !file.toString().contains(outputPath))
                .sorted()
                .collect(Collectors.toList());

        final String[] all = {"// Auto-generated, all changes will be overwritten.\n\n"};

        fileNames.forEach(path -> {
            try {
                String content = new String(Files.readAllBytes(path));
                all[0] = all[0] + "\n" + content;
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        boolean outputExists = Files.exists(Paths.get(outputPath));
        if (outputExists) {
            System.out.println("Output file " + outputPath + " already exists, ovewriting.");
        }
        Files.write(Paths.get(outputPath), all[0].getBytes());
    }
}
