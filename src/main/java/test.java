import org.opencompare.api.java.*;
import org.opencompare.api.java.impl.io.KMFJSONLoader;
import org.opencompare.api.java.io.PCMLoader;

import java.io.File;
import java.util.List;

/**
 * Created by MSI_Thomas on 19/09/2016.
 */
public class test {
    public static void main(String[] args) throws java.io.IOException {
        int i = 0;
        File pcmFile = new File("pcms/example.pcm");
        PCMLoader loader = new KMFJSONLoader();
        List<PCMContainer> pcmContainers = loader.load(pcmFile);
        for (PCMContainer pcmContainer : pcmContainers) {
            PCM pcm = pcmContainer.getPcm();
            for (Product product : pcm.getProducts()) {
                for (Feature feature : pcm.getConcreteFeatures()) {
                    // Find the cell corresponding to the current feature and product
                    Cell cell = product.findCell(feature);

                    // Get information contained in the cell
                    String content = cell.getContent();
                    String rawContent = cell.getRawContent();
                    Value interpretation = cell.getInterpretation();

                    // Print the content of the cell
                    System.out.println("(" + product.getPCM() + ", " + feature.getName() + ") = " + content);
                }
            }
            System.out.println("passé");
        }

    }
}
