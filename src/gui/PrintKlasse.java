package gui;

import dto.KlimatogramDto;
import dto.ToetsDto;
import dto.VraagDto;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

/**
 * <b>OPMERKING:</b>
 * Deze klasse is een werkende printklasse. Door tijdsgebrek hebben we ons niet
 * bezig gehouden met alles mooi in klassen op te delen, alles staat in deze
 * klasse en we zijn er ons van bewust dat er heel veel herhalende code is. Toch
 * hebben we deze laten staan omdat printen een mooie meerwaarde biedt aan het
 * project.
 *
 * @author Gebruiker
 */
public class PrintKlasse {

    static final int MARGE = 60;
    static final float HOOGTE = 841.8898f;
    static final float BREEDTE = 595.27563f;
    static final int FONTSIZE = 12;
    static final int FONTSIZETITEL = 20;
    static final int FONTSIZEBESCHRIJVING = 16;
    static final float LEADINGTITEL = 1f * FONTSIZETITEL;
    static final float LEADINGBESCHRIJVING = 1f * FONTSIZEBESCHRIJVING;
    static final float LEADING = 1.5f * FONTSIZE;
    static float positieHoogte = HOOGTE - MARGE;
    static boolean heeftDeterminatietabel = false, heeftKaart = false;

    public static void printDto(ToetsDto dto) {
        //pdf is 841.8898 hoog en 595.27563 breed
        positieHoogte = HOOGTE - MARGE;

        javafx.application.Platform.runLater(new Runnable() {

            @Override
            public void run() {
                Stage stage = new Stage();
                ToetsDto toets = dto;

                PDDocument document = new PDDocument();
                try {
                    maakVoorblad(document, toets);
                    for (int i = 0; i < toets.getVragen().size(); i++) {
                        VraagDto vraag = toets.getVragen().get(i);
                        if (vraag.isGraadEenVraag()) {
                            heeftDeterminatietabel = true;
                            positieHoogte = HOOGTE - MARGE;
                            maakGraadEenVraag(i + 1, document, vraag);
                        }
                        if (vraag.isDeterminatieVraag()) {
                            heeftDeterminatietabel = true;
                            positieHoogte = HOOGTE - MARGE;
                            maakGraadTweeVraag(i + 1, document, vraag);
                        }
                        if (vraag.isGraadDrieVraag()) {
                            heeftKaart = true;
                            positieHoogte = HOOGTE - MARGE;
                            maakGraadDrieVraag(i + 1, document, vraag);
                        }
                    }

                    if (heeftDeterminatietabel) {
                        PrintBoomPanel boom = new PrintBoomPanel();
                        Pane pane = boom.teken(toets.getDeterminatietabel());
                        pane.setStyle("-fx-background-color:rgba(0,0,0,0)");
                        Scene scene = new Scene(pane);
                        stage.setScene(scene);
                        PDPage page = new PDPage(PDPage.PAGE_SIZE_A4);
                        document.addPage(page);
                        PDPageContentStream contentStream = new PDPageContentStream(document, page);
                        stage.show();
                        WritableImage wim2 = new WritableImage((int) stage.getWidth(), (int) stage.getHeight());
                        scene.snapshot(wim2);
                        stage.close();
                        BufferedImage img2 = SwingFXUtils.fromFXImage(wim2, null);
                        PDXObjectImage image = new PDJpeg(document, img2);
                        int width, height;
                        if (stage.getWidth() > (595 * 1.33333)) {
                            width = 595;
                        } else {
                            width = (int) (stage.getWidth() * 0.75);
                        }
                        if (stage.getHeight() > (840 * 1.33333)) {
                            height = 841;
                        } else {
                            height = (int) (stage.getHeight() * 0.75);
                        }
                        contentStream.drawXObject(image, 0, HOOGTE - height, width, height);
                        contentStream.close();
                    }

                    if (heeftKaart) {
                        PDPage page = new PDPage(PDPage.PAGE_SIZE_A4);
                        document.addPage(page);
                        PDPageContentStream contentStream = new PDPageContentStream(document, page);

                        PDJpeg image = new PDJpeg(document, PrintKlasse.class.getResourceAsStream("/content/images/worldmap.jpg"));
                        AffineTransform at = new AffineTransform(BREEDTE - 2 * MARGE, 0, 0, HOOGTE - 2 * MARGE, MARGE + (BREEDTE - 2 * MARGE), MARGE);
                        at.rotate(Math.toRadians(90));
                        contentStream.drawXObject(image, at);
                        contentStream.close();
                    }
                    FileChooser fileChooser = new FileChooser();

                    //Set extension filter
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF bestanden (*.pdf)", "*.pdf");
                    fileChooser.getExtensionFilters().add(extFilter);

                    //Show save file dialog
                    File file = fileChooser.showSaveDialog(stage);

                    if (file != null) {
                        document.save(file);
                    }

                    document.close();
                } catch (IOException | COSVisitorException e) {
                    e.printStackTrace();
                } finally {
                    stage.close();
                }
            }
        });
    }

    private static void maakVoorblad(PDDocument document, ToetsDto toets) throws IOException {
        PDPage page = new PDPage(PDPage.PAGE_SIZE_A4);
        document.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        PDFont fontTitel = PDType1Font.HELVETICA_OBLIQUE;
        String titel = toets.getTitel().get();
        List<String> lines = new ArrayList<>();
        int lastSpace = -1;
        while (titel.length() > 0) {
            int spaceIndex = titel.indexOf(' ', lastSpace + 1);
            if (spaceIndex < 0) {
                lines.add(titel);
                titel = "";
            } else {
                String subString = titel.substring(0, spaceIndex);
                float size = FONTSIZETITEL * fontTitel.getStringWidth(subString) / 1000;
                if (size > (BREEDTE - 2 * MARGE)) {
                    if (lastSpace < 0) {
                        lastSpace = spaceIndex;
                    }
                    subString = titel.substring(0, lastSpace);
                    lines.add(subString);
                    titel = titel.substring(lastSpace).trim();
                    lastSpace = -1;
                } else {
                    lastSpace = spaceIndex;
                }
            }
        }

        for (String line : lines) {
            contentStream.beginText();
            contentStream.setFont(fontTitel, FONTSIZETITEL);
            float x = (page.getMediaBox().getWidth() - fontTitel.getStringWidth(line) / 1000 * FONTSIZETITEL) / 2;
            positieHoogte -= LEADINGTITEL;
            contentStream.moveTextPositionByAmount(x, positieHoogte);
            contentStream.drawString(line);
            contentStream.endText();
        }

        positieHoogte -= 5;

        if (!(toets.getBeschrijving() == null || toets.getBeschrijving().get().isEmpty())) {
            PDFont fontBeschrijving = PDType1Font.HELVETICA_OBLIQUE;
            String beschrijving = toets.getBeschrijving().get();
            lines = new ArrayList<>();
            lastSpace = -1;
            while (beschrijving.length() > 0) {
                int spaceIndex = beschrijving.indexOf(' ', lastSpace + 1);
                if (spaceIndex < 0) {
                    lines.add(beschrijving);
                    beschrijving = "";
                } else {
                    String subString = beschrijving.substring(0, spaceIndex);
                    float size = FONTSIZEBESCHRIJVING * fontBeschrijving.getStringWidth(subString) / 1000;
                    if (size > (BREEDTE - 2 * MARGE)) {
                        if (lastSpace < 0) {
                            lastSpace = spaceIndex;
                        }
                        subString = beschrijving.substring(0, lastSpace);
                        lines.add(subString);
                        beschrijving = beschrijving.substring(lastSpace).trim();
                        lastSpace = -1;
                    } else {
                        lastSpace = spaceIndex;
                    }
                }
            }

            for (String line : lines) {
                contentStream.beginText();
                contentStream.setFont(fontBeschrijving, FONTSIZEBESCHRIJVING);
                float x = (page.getMediaBox().getWidth() - fontBeschrijving.getStringWidth(line) / 1000 * FONTSIZEBESCHRIJVING) / 2;
                positieHoogte -= LEADINGBESCHRIJVING;
                contentStream.moveTextPositionByAmount(x, positieHoogte);
                contentStream.drawString(line);
                contentStream.endText();
            }
        }

        if (!(toets.getAanvang() == null && toets.getEind() == null)) {
            PDFont fontTijdstip = PDType1Font.HELVETICA_OBLIQUE;
            String tijdstip = String.format("Start: %d/%d/%d - %d:%d, Eind: %d/%d/%d - %d:%d", toets.getEind().get(Calendar.DAY_OF_MONTH), (toets.getEind().get(Calendar.MONTH) + 1), toets.getEind().get(Calendar.YEAR), toets.getEind().get(Calendar.HOUR_OF_DAY), toets.getEind().get(Calendar.MINUTE), toets.getEind().get(Calendar.DAY_OF_MONTH), (toets.getEind().get(Calendar.MONTH) + 1), toets.getEind().get(Calendar.YEAR), toets.getEind().get(Calendar.HOUR_OF_DAY), toets.getEind().get(Calendar.MINUTE));

            contentStream.beginText();
            contentStream.setFont(fontTijdstip, FONTSIZE);
            float x = (page.getMediaBox().getWidth() - fontTijdstip.getStringWidth(tijdstip) / 1000 * FONTSIZE) / 2;
            positieHoogte -= LEADING;
            contentStream.moveTextPositionByAmount(x, positieHoogte);
            contentStream.drawString(tijdstip);
            contentStream.endText();
        }

        PDFont fontVoorblad = PDType1Font.HELVETICA;

        int vraagNummer = 1;
        List<String> vragen = new ArrayList<>();
        for (VraagDto v : toets.getVragen()) {
            vragen.add("Vraag " + vraagNummer + ":                   /" + v.getPuntenTeVerdienen());
            vraagNummer++;
        }
        vragen.add("Totaal:                   /" + toets.getAantalPuntenTeBehalen());

        for (String vraag : vragen) {
            lines = new ArrayList<>();
            lastSpace = -1;
            while (vraag.length() > 0) {
                int spaceIndex = vraag.indexOf(' ', lastSpace + 1);
                if (spaceIndex < 0) {
                    lines.add(vraag);
                    vraag = "";
                } else {
                    String subString = vraag.substring(0, spaceIndex);
                    float size = FONTSIZE * fontVoorblad.getStringWidth(subString) / 1000;
                    if (size > (BREEDTE - 2 * MARGE)) {
                        if (lastSpace < 0) {
                            lastSpace = spaceIndex;
                        }
                        subString = vraag.substring(0, lastSpace);
                        lines.add(subString);
                        vraag = vraag.substring(lastSpace).trim();
                        lastSpace = -1;
                    } else {
                        lastSpace = spaceIndex;
                    }
                }
            }

            for (String line : lines) {
                if (positieHoogte < MARGE + LEADING) {
                    contentStream.close();
                    page = new PDPage(PDPage.PAGE_SIZE_A4);
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    positieHoogte = HOOGTE - MARGE;
                }
                contentStream.beginText();
                contentStream.setFont(fontVoorblad, FONTSIZE);
                float x = MARGE;
                positieHoogte -= LEADING;
                contentStream.moveTextPositionByAmount(x, positieHoogte);
                contentStream.drawString(line);
                contentStream.endText();
            }
        }

        contentStream.close();
    }

    private static void maakGraadEenVraag(int i, PDDocument document, VraagDto vraag) throws IOException {
        PDPage pagina = new PDPage(PDPage.PAGE_SIZE_A4);
        document.addPage(pagina);
        PDPageContentStream stream = new PDPageContentStream(document, pagina);

        PDFont fontVraag = PDType1Font.HELVETICA;
        String vraagBeschrijving = i + ". " + vraag.getBeschrijving() + " (op " + vraag.getPuntenTeVerdienen() + " punten)";
        List<String> lines = new ArrayList<>();
        int lastSpace = -1;
        while (vraagBeschrijving.length() > 0) {
            int spaceIndex = vraagBeschrijving.indexOf(' ', lastSpace + 1);
            if (spaceIndex < 0) {
                lines.add(vraagBeschrijving);
                vraagBeschrijving = "";
            } else {
                String subString = vraagBeschrijving.substring(0, spaceIndex);
                float size = FONTSIZE * fontVraag.getStringWidth(subString) / 1000;
                if (size > (BREEDTE - 2 * MARGE)) {
                    if (lastSpace < 0) {
                        lastSpace = spaceIndex;
                    }
                    subString = vraagBeschrijving.substring(0, lastSpace);
                    lines.add(subString);
                    vraagBeschrijving = vraagBeschrijving.substring(lastSpace).trim();
                    lastSpace = -1;
                } else {
                    lastSpace = spaceIndex;
                }
            }
        }
        for (String line : lines) {
            stream.beginText();
            stream.setFont(fontVraag, FONTSIZE);
            float x = MARGE;
            positieHoogte -= LEADING;
            stream.moveTextPositionByAmount(x, positieHoogte);
            stream.drawString(line);
            stream.endText();
        }
        positieHoogte -= LEADING / 2;

        KlimatogramDto klimatogram = vraag.getKlimatogrammen().get(0);
        String locatieLand = klimatogram.getLocatie();
        lines = new ArrayList<>();
        lastSpace = -1;
        while (locatieLand.length() > 0) {
            int spaceIndex = locatieLand.indexOf(' ', lastSpace + 1);
            if (spaceIndex < 0) {
                lines.add(locatieLand);
                locatieLand = "";
            } else {
                String subString = locatieLand.substring(0, spaceIndex);
                float size = FONTSIZE * fontVraag.getStringWidth(subString) / 1000;
                if (size > (BREEDTE - 2 * MARGE)) {
                    if (lastSpace < 0) {
                        lastSpace = spaceIndex;
                    }
                    subString = locatieLand.substring(0, lastSpace);
                    lines.add(subString);
                    locatieLand = locatieLand.substring(lastSpace).trim();
                    lastSpace = -1;
                } else {
                    lastSpace = spaceIndex;
                }
            }
        }
        for (String line : lines) {
            stream.beginText();
            stream.setFont(fontVraag, FONTSIZE);
            float x = (pagina.getMediaBox().getWidth() - fontVraag.getStringWidth(line) / 1000 * FONTSIZE) / 2;
            positieHoogte -= LEADING;
            stream.moveTextPositionByAmount(x, positieHoogte);
            stream.drawString(line);
            stream.endText();
        }

        String periode = "Klimatologische gemiddelden " + klimatogram.getBeginJaar() + " - " + klimatogram.getEindJaar();
        lines = new ArrayList<>();
        lastSpace = -1;
        while (periode.length() > 0) {
            int spaceIndex = periode.indexOf(' ', lastSpace + 1);
            if (spaceIndex < 0) {
                lines.add(periode);
                periode = "";
            } else {
                String subString = periode.substring(0, spaceIndex);
                float size = FONTSIZE * fontVraag.getStringWidth(subString) / 1000;
                if (size > (BREEDTE - 2 * MARGE)) {
                    if (lastSpace < 0) {
                        lastSpace = spaceIndex;
                    }
                    subString = periode.substring(0, lastSpace);
                    lines.add(subString);
                    periode = periode.substring(lastSpace).trim();
                    lastSpace = -1;
                } else {
                    lastSpace = spaceIndex;
                }
            }
        }
        for (String line : lines) {
            stream.beginText();
            stream.setFont(fontVraag, FONTSIZE);
            float x = (pagina.getMediaBox().getWidth() - fontVraag.getStringWidth(line) / 1000 * FONTSIZE) / 2;
            positieHoogte -= LEADING;
            stream.moveTextPositionByAmount(x, positieHoogte);
            stream.drawString(line);
            stream.endText();
        }

        Stage stage = new Stage();
        StackPane pnl = new KlimatogramGrafiek().createChart(klimatogram);
        pnl.setPadding(new Insets(0, 50, 0, 0));
        pnl.setStyle("-fx-background-color:rgba(0,0,0,0)");
        Scene scene = new Scene(pnl, (int) (BREEDTE - 2 * MARGE), 250);
        stage.setScene(scene);
        scene.getStylesheets().add("/gui/styles.css");
        WritableImage wim = new WritableImage((int) (BREEDTE - 2 * MARGE), 250);
        scene.snapshot(wim);

        BufferedImage img = SwingFXUtils.fromFXImage(wim, null);
        PDXObjectImage image = new PDJpeg(document, img);
        positieHoogte -= LEADING / 2;
        positieHoogte -= image.getHeight();
        stream.drawXObject(image, MARGE, positieHoogte, BREEDTE - 2 * MARGE, 250);

        maakTabelMetGegevens(stream, klimatogram);

        List<String> subvragen = new ArrayList<>(vraag.getSubvragen());
        for (int j = 0; j < subvragen.size(); j++) {
            String vr = i + "." + (j + 1) + ". " + subvragen.get(j);
            subvragen.set(j, vr);
        }

        for (String subVraag : vraag.getSubvragen()) {
            lines = new ArrayList<>();
            lastSpace = -1;
            while (subVraag.length() > 0) {
                int spaceIndex = subVraag.indexOf(' ', lastSpace + 1);
                if (spaceIndex < 0) {
                    lines.add(subVraag);
                    subVraag = "";
                } else {
                    String subString = subVraag.substring(0, spaceIndex);
                    float size = FONTSIZE * fontVraag.getStringWidth(subString) / 1000;
                    if (size > (BREEDTE - 2 * MARGE)) {
                        if (lastSpace < 0) {
                            lastSpace = spaceIndex;
                        }
                        subString = subVraag.substring(0, lastSpace);
                        lines.add(subString);
                        subVraag = subVraag.substring(lastSpace).trim();
                        lastSpace = -1;
                    } else {
                        lastSpace = spaceIndex;
                    }
                }
            }

            for (String line : lines) {
                if (positieHoogte < MARGE + LEADING) {
                    stream.close();
                    pagina = new PDPage(PDPage.PAGE_SIZE_A4);
                    document.addPage(pagina);
                    stream = new PDPageContentStream(document, pagina);
                    positieHoogte = HOOGTE - MARGE;
                }
                stream.beginText();
                stream.setFont(fontVraag, FONTSIZE);
                float x = MARGE;
                positieHoogte -= LEADING;
                stream.moveTextPositionByAmount(x, positieHoogte);
                stream.drawString(line);
                positieHoogte -= LEADING;
                stream.endText();
                stream.drawLine(MARGE, positieHoogte, BREEDTE - MARGE, positieHoogte);
            }
        }

        lines = new ArrayList<>();

        lines.add(i + "." + (subvragen.size() + 1) + ". Wat is het klimaattype?");
        lines.add(i + "." + (subvragen.size() + 2) + ". Wat is het vegetatietype?");

        for (String line : lines) {
            if (positieHoogte < MARGE + LEADING) {
                stream.close();
                pagina = new PDPage(PDPage.PAGE_SIZE_A4);
                document.addPage(pagina);
                stream = new PDPageContentStream(document, pagina);
                positieHoogte = HOOGTE - MARGE;
            }
            stream.beginText();
            stream.setFont(fontVraag, FONTSIZE);
            float x = MARGE;
            positieHoogte -= LEADING;
            stream.moveTextPositionByAmount(x, positieHoogte);
            stream.drawString(line);
            positieHoogte -= LEADING;
            stream.endText();
            stream.drawLine(MARGE, positieHoogte, BREEDTE - MARGE, positieHoogte);
        }
        stream.close();

    }

    private static void maakGraadTweeVraag(int i, PDDocument document, VraagDto vraag) throws IOException {
        PDPage pagina = new PDPage(PDPage.PAGE_SIZE_A4);
        document.addPage(pagina);
        PDPageContentStream stream = new PDPageContentStream(document, pagina);

        PDFont fontVraag = PDType1Font.HELVETICA;
        String vraagBeschrijving = i + ". " + vraag.getBeschrijving() + " (op " + vraag.getPuntenTeVerdienen() + " punten)";
        List<String> lines = new ArrayList<>();
        int lastSpace = -1;
        while (vraagBeschrijving.length() > 0) {
            int spaceIndex = vraagBeschrijving.indexOf(' ', lastSpace + 1);
            if (spaceIndex < 0) {
                lines.add(vraagBeschrijving);
                vraagBeschrijving = "";
            } else {
                String subString = vraagBeschrijving.substring(0, spaceIndex);
                float size = FONTSIZE * fontVraag.getStringWidth(subString) / 1000;
                if (size > (BREEDTE - 2 * MARGE)) {
                    if (lastSpace < 0) {
                        lastSpace = spaceIndex;
                    }
                    subString = vraagBeschrijving.substring(0, lastSpace);
                    lines.add(subString);
                    vraagBeschrijving = vraagBeschrijving.substring(lastSpace).trim();
                    lastSpace = -1;
                } else {
                    lastSpace = spaceIndex;
                }
            }
        }
        for (String line : lines) {
            stream.beginText();
            stream.setFont(fontVraag, FONTSIZE);
            float x = MARGE;
            positieHoogte -= LEADING;
            stream.moveTextPositionByAmount(x, positieHoogte);
            stream.drawString(line);
            stream.endText();
        }
        positieHoogte -= LEADING / 2;

        KlimatogramDto klimatogram = vraag.getKlimatogrammen().get(0);
        String locatieLand = klimatogram.getLocatie();
        lines = new ArrayList<>();
        lastSpace = -1;
        while (locatieLand.length() > 0) {
            int spaceIndex = locatieLand.indexOf(' ', lastSpace + 1);
            if (spaceIndex < 0) {
                lines.add(locatieLand);
                locatieLand = "";
            } else {
                String subString = locatieLand.substring(0, spaceIndex);
                float size = FONTSIZE * fontVraag.getStringWidth(subString) / 1000;
                if (size > (BREEDTE - 2 * MARGE)) {
                    if (lastSpace < 0) {
                        lastSpace = spaceIndex;
                    }
                    subString = locatieLand.substring(0, lastSpace);
                    lines.add(subString);
                    locatieLand = locatieLand.substring(lastSpace).trim();
                    lastSpace = -1;
                } else {
                    lastSpace = spaceIndex;
                }
            }
        }
        for (String line : lines) {
            stream.beginText();
            stream.setFont(fontVraag, FONTSIZE);
            float x = (pagina.getMediaBox().getWidth() - fontVraag.getStringWidth(line) / 1000 * FONTSIZE) / 2;
            positieHoogte -= LEADING;
            stream.moveTextPositionByAmount(x, positieHoogte);
            stream.drawString(line);
            stream.endText();
        }

        String periode = "Klimatologische gemiddelden " + klimatogram.getBeginJaar() + " - " + klimatogram.getEindJaar();
        lines = new ArrayList<>();
        lastSpace = -1;
        while (periode.length() > 0) {
            int spaceIndex = periode.indexOf(' ', lastSpace + 1);
            if (spaceIndex < 0) {
                lines.add(periode);
                periode = "";
            } else {
                String subString = periode.substring(0, spaceIndex);
                float size = FONTSIZE * fontVraag.getStringWidth(subString) / 1000;
                if (size > (BREEDTE - 2 * MARGE)) {
                    if (lastSpace < 0) {
                        lastSpace = spaceIndex;
                    }
                    subString = periode.substring(0, lastSpace);
                    lines.add(subString);
                    periode = periode.substring(lastSpace).trim();
                    lastSpace = -1;
                } else {
                    lastSpace = spaceIndex;
                }
            }
        }
        for (String line : lines) {
            stream.beginText();
            stream.setFont(fontVraag, FONTSIZE);
            float x = (pagina.getMediaBox().getWidth() - fontVraag.getStringWidth(line) / 1000 * FONTSIZE) / 2;
            positieHoogte -= LEADING;
            stream.moveTextPositionByAmount(x, positieHoogte);
            stream.drawString(line);
            stream.endText();
        }

        Stage stage = new Stage();
        StackPane pnl = new KlimatogramGrafiek().createChart(klimatogram);
        pnl.setPadding(new Insets(0, 50, 0, 0));
        pnl.setStyle("-fx-background-color:rgba(0,0,0,0)");
        Scene scene = new Scene(pnl, (int) (BREEDTE - 2 * MARGE), 250);
        stage.setScene(scene);
        scene.getStylesheets().add("/gui/styles.css");
        WritableImage wim = new WritableImage((int) (BREEDTE - 2 * MARGE), 250);
        scene.snapshot(wim);

        BufferedImage img = SwingFXUtils.fromFXImage(wim, null);
        PDXObjectImage image = new PDJpeg(document, img);
        positieHoogte -= LEADING / 2;
        positieHoogte -= image.getHeight();
        stream.drawXObject(image, MARGE, positieHoogte, BREEDTE - 2 * MARGE, 250);

        maakTabelMetGegevens(stream, klimatogram);

        lines = new ArrayList<>();

        lines.add(i + "." + ("1. Wat is het klimaattype?"));
        lines.add(i + "." + ("2. Wat is het vegetatietype?"));

        for (String line : lines) {
            stream.beginText();
            stream.setFont(fontVraag, FONTSIZE);
            float x = MARGE;
            positieHoogte -= LEADING;
            stream.moveTextPositionByAmount(x, positieHoogte);
            stream.drawString(line);
            positieHoogte -= LEADING;
            stream.endText();
            stream.drawLine(MARGE, positieHoogte, BREEDTE - MARGE, positieHoogte);
        }
        stream.close();
    }

    private static void maakGraadDrieVraag(int i, PDDocument document, VraagDto vraag) throws IOException {
        PDPage pagina = new PDPage(PDPage.PAGE_SIZE_A4);
        document.addPage(pagina);
        PDPageContentStream stream = new PDPageContentStream(document, pagina);

        PDFont fontVraag = PDType1Font.HELVETICA;
        String vraagBeschrijving = i + ". " + vraag.getBeschrijving() + " (op " + vraag.getPuntenTeVerdienen() + " punten)";
        List<String> lines = new ArrayList<>();
        int lastSpace = -1;
        while (vraagBeschrijving.length() > 0) {
            int spaceIndex = vraagBeschrijving.indexOf(' ', lastSpace + 1);
            if (spaceIndex < 0) {
                lines.add(vraagBeschrijving);
                vraagBeschrijving = "";
            } else {
                String subString = vraagBeschrijving.substring(0, spaceIndex);
                float size = FONTSIZE * fontVraag.getStringWidth(subString) / 1000;
                if (size > (BREEDTE - 2 * MARGE)) {
                    if (lastSpace < 0) {
                        lastSpace = spaceIndex;
                    }
                    subString = vraagBeschrijving.substring(0, lastSpace);
                    lines.add(subString);
                    vraagBeschrijving = vraagBeschrijving.substring(lastSpace).trim();
                    lastSpace = -1;
                } else {
                    lastSpace = spaceIndex;
                }
            }
        }
        for (String line : lines) {
            stream.beginText();
            stream.setFont(fontVraag, FONTSIZE);
            float x = MARGE;
            positieHoogte -= LEADING;
            stream.moveTextPositionByAmount(x, positieHoogte);
            stream.drawString(line);
            stream.endText();
        }
        positieHoogte -= LEADING / 2;

        int count = 0;
        for (KlimatogramDto klimatogram : vraag.getKlimatogrammen()) {
            if (count % 2 == 0 && count != 0) {
                stream.close();
                pagina = new PDPage(PDPage.PAGE_SIZE_A4);
                document.addPage(pagina);
                stream = new PDPageContentStream(document, pagina);
                positieHoogte = HOOGTE - MARGE;
            }
            String klim = "Klimatogram " + (count + 1);
            lines = new ArrayList<>();
            lastSpace = -1;
            while (klim.length() > 0) {
                int spaceIndex = klim.indexOf(' ', lastSpace + 1);
                if (spaceIndex < 0) {
                    lines.add(klim);
                    klim = "";
                } else {
                    String subString = klim.substring(0, spaceIndex);
                    float size = FONTSIZE * fontVraag.getStringWidth(subString) / 1000;
                    if (size > (BREEDTE - 2 * MARGE)) {
                        if (lastSpace < 0) {
                            lastSpace = spaceIndex;
                        }
                        subString = klim.substring(0, lastSpace);
                        lines.add(subString);
                        klim = klim.substring(lastSpace).trim();
                        lastSpace = -1;
                    } else {
                        lastSpace = spaceIndex;
                    }
                }
            }
            for (String line : lines) {
                stream.beginText();
                stream.setFont(fontVraag, FONTSIZE);
                float x = (pagina.getMediaBox().getWidth() - fontVraag.getStringWidth(line) / 1000 * FONTSIZE) / 2;
                positieHoogte -= LEADING;
                stream.moveTextPositionByAmount(x, positieHoogte);
                stream.drawString(line);
                stream.endText();
            }

            String periode = "Klimatologische gemiddelden " + klimatogram.getBeginJaar() + " - " + klimatogram.getEindJaar();
            lines = new ArrayList<>();
            lastSpace = -1;
            while (periode.length() > 0) {
                int spaceIndex = periode.indexOf(' ', lastSpace + 1);
                if (spaceIndex < 0) {
                    lines.add(periode);
                    periode = "";
                } else {
                    String subString = periode.substring(0, spaceIndex);
                    float size = FONTSIZE * fontVraag.getStringWidth(subString) / 1000;
                    if (size > (BREEDTE - 2 * MARGE)) {
                        if (lastSpace < 0) {
                            lastSpace = spaceIndex;
                        }
                        subString = periode.substring(0, lastSpace);
                        lines.add(subString);
                        periode = periode.substring(lastSpace).trim();
                        lastSpace = -1;
                    } else {
                        lastSpace = spaceIndex;
                    }
                }
            }
            for (String line : lines) {
                stream.beginText();
                stream.setFont(fontVraag, FONTSIZE);
                float x = (pagina.getMediaBox().getWidth() - fontVraag.getStringWidth(line) / 1000 * FONTSIZE) / 2;
                positieHoogte -= LEADING;
                stream.moveTextPositionByAmount(x, positieHoogte);
                stream.drawString(line);
                stream.endText();
            }

            Stage stage = new Stage();
            StackPane pnl = new KlimatogramGrafiek().createChart(klimatogram);
            pnl.setPadding(new Insets(0, 50, 0, 0));
            pnl.setStyle("-fx-background-color:rgba(0,0,0,0)");
            Scene scene = new Scene(pnl, (int) (BREEDTE - 2 * MARGE), 250);
            stage.setScene(scene);
            scene.getStylesheets().add("/gui/styles.css");
            WritableImage wim = new WritableImage((int) (BREEDTE - 2 * MARGE), 250);
            scene.snapshot(wim);

            BufferedImage img = SwingFXUtils.fromFXImage(wim, null);
            PDXObjectImage image = new PDJpeg(document, img);
            positieHoogte -= LEADING / 2;
            positieHoogte -= image.getHeight();
            stream.drawXObject(image, MARGE, positieHoogte, BREEDTE - 2 * MARGE, 250);

            maakTabelMetGegevens(stream, klimatogram);
            count++;
        }
        stream.close();
    }

    private static void maakTabelMetGegevens(PDPageContentStream contentStream, KlimatogramDto klimatogram) throws IOException {
        Object[][] content
                = {{"", "Jan", "Feb", "Maa", "Apr", "Mei", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"},
                {"T(°C)", "", "", "", "", "", "", "", "", "", "", "", ""},
                {"mmN", "", "", "", "", "", "", "", "", "", "", "", ""}};
        for (int i = 1; i < content.length; i++) {
            for (int j = 1; j < content[i].length; j++) {
                if (i == 1) {
                    content[i][j] = klimatogram.maanden.get(j - 1).getTemperatuur();
                }
                if (i == 2) {
                    content[i][j] = klimatogram.maanden.get(j - 1).getNeerslag();
                }
            }
        }
        int rows = content.length;
        int cols = content[0].length;
        float rowHeight = 20f;
        float tableWidth = BREEDTE - 2 * MARGE;
        float tableHeight = rowHeight * rows;
        float colWidth = tableWidth / (float) cols;
        float cellMargin = 5f;

        float nexty = positieHoogte;
        for (int i = 0; i <= rows; i++) {
            contentStream.drawLine(MARGE, nexty, MARGE + tableWidth, nexty);
            nexty -= rowHeight;
        }

        float nextx = MARGE;
        for (int i = 0; i <= cols; i++) {
            contentStream.drawLine(nextx, positieHoogte, nextx, positieHoogte - tableHeight);
            nextx += colWidth;
        }

        float textx = MARGE + cellMargin;
        float texty = positieHoogte - 15;
        for (int i = 0; i < content.length; i++) {
            for (int j = 0; j < content[i].length; j++) {
                String text = String.valueOf(content[i][j]);
                contentStream.beginText();
                contentStream.moveTextPositionByAmount(textx, texty);
                contentStream.drawString(text);
                contentStream.endText();
                textx += colWidth;
            }
            texty -= rowHeight;
            textx = MARGE + cellMargin;
        }
        positieHoogte -= rowHeight * 3;
    }
}
